import org.apache.logging.log4j.core.tools.picocli.CommandLine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Core game loop and state manager.
 * Uses GameDatabase for saves and logs.
 */
public class Game {
    private Player player;
    private final Map<String, Room> rooms = new HashMap<>();
    private Room currentRoom;
    private boolean running = true;
    private final Scanner scanner = new Scanner(System.in);

    public Game() {
        GameDatabase.init(); // ensure DB and tables exist
        setupWorld();
        // default start in kitchen
        player = new Player("Hero", rooms.get("RoomKitchen"));
        currentRoom = player.getCurrentRoom();
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Room getRoom(String name) {
        return rooms.get(name);
    }

    public void setCurrentRoom(Room room) {
        this.currentRoom = room;
    }


    public Game(SaveData data) {
        GameDatabase.init();
        setupWorld();
        Room start = rooms.getOrDefault(data.currentRoom, rooms.get("RoomKitchen"));
        player = new Player(data.playerName, start);
        currentRoom = player.getCurrentRoom();
        if (data.inventory != null && !data.inventory.isBlank()) {
            for (String name : data.inventory.split(",")) {
                player.takeItem(new Item(name.trim(), "Restored item"));
            }
        }
    }

    private void setupWorld() {
        /// TODO: Change this and use embedded strings
        // Create rooms for our game
        Room dungeon = new Room(Strings.get("dungeon_name"), Strings.get("dungeon_desc"), Strings.get("dungeon_onEnter"));
        Door dungeonDoor = new Door(Strings.get("dungeon_door"),
                Strings.get("dungeon_door_desc"),
                "dungeon key", true, true);
        Key dungeonKey = new Key(Strings.get("dungeon_key"),
                Strings.get("dungeon_key_desc"),
                Strings.get("dungeon_key_examine"),
                null,false, "dungeon key");
        Shelf dungeonShelf = new Shelf(Strings.get("dungeon_shelf"),
                Strings.get("dungeon_shelf_desc"),
                Strings.get("dungeon_shelf_examine"), true);
        Item dungeonBed = new Item(Strings.get("dungeon_bed"),
                Strings.get("dungeon bed_desc"),
                Strings.get("dungeon_bed_examine"),
                Strings.get("dungeon_bed_use"),
                true, false);
        Item dungeonFood = new Item(Strings.get("dungeon_food"),
                Strings.get("dungeon_food_desc"),
                Strings.get("dungeon_food_examine"),
                Strings.get("dungeon_food_use"),
                true, true);
        dungeon.addItem(dungeonBed);
        dungeon.addItem(dungeonFood);
        dungeon.addItem(dungeonShelf);
        dungeon.addItem(dungeonDoor);
        dungeon.addItem(dungeonKey);
        dungeonShelf.addItem(dungeonFood);
        dungeonShelf.addItem(dungeonKey);

        Room hallway = new Room(Strings.get("hallway_name"),
                Strings.get("hallway_desc"),
                Strings.get("hallway_onEnter"));
        RoomKitchen kitchen = new RoomKitchen();
        RoomLivingQuarters roomLivingQuarters = new RoomLivingQuarters();
        RoomGreenhouse roomGreenhouse = new RoomGreenhouse();
        RoomRoyalChamber roomRoyalChamber = new RoomRoyalChamber();

        // Connecting all of the rooms
        dungeon.addExit("north", dungeonDoor, hallway);
        // connect rooms (consistent keys used in GameDatabase saves)
        kitchen.addExit("east", roomLivingQuarters);
        roomLivingQuarters.addExit("west", kitchen);
        roomLivingQuarters.addExit("south", roomGreenhouse);
        roomGreenhouse.addExit("north", roomLivingQuarters);
        roomGreenhouse.addExit("west", roomRoyalChamber);
        roomRoyalChamber.addExit("east", roomGreenhouse);

        roomTwo.addExit("west", roomThree);
        roomThree.addExit("east", roomTwo); // Player can finish game through this room

        // Add all rooms to a hashmap
        rooms = new HashMap<>();
        rooms.put("RoomKitchen", kitchen);
        rooms.put("RoomOne", roomLivingQuarters);
        rooms.put("RoomTwo", roomGreenhouse);
        rooms.put("RoomThree", roomRoyalChamber);

        // Create new player instance
        /// TODO: Maybe add a way to name the player on game start
        player = new Player("Hero", kitchen);
    }

    // Main game loop, prompts for text input
    public void run() {
        System.out.println("You open your eyes in a strange mansion... Type 'help' for a list of commands.");
        if (currentRoom != null) currentRoom.enter(player);

        while (running) {
            System.out.print("> ");
            String input;
            try {
                input = scanner.nextLine();
            } catch (NoSuchElementException | IllegalStateException e) {
                // input closed — exit gracefully
                running = false;
                break;
            }

            CommandParser.ParsedCommand command = CommandParser.parse(input);
            if (command.getType() == CommandType.UNKNOWN) {
                System.out.println("I don't understand that. Type 'help' for commands.");
                continue;
            }

            handleInput(command);
        }

        System.out.println("Thanks for playing!");
    }

    // Runs input string through command parser to interpret verb/noun combos and run commands with them
    private void handleInput(CommandParser.ParsedCommand cmd) {
        CommandType type = cmd.getType();
        String noun1 = cmd.getNoun1();
        String noun2 = cmd.getNoun2();
        Room room = player.getCurrentRoom();

        switch (type) {
            case GO -> {
                if (noun1 == null) {
                    System.out.println("Go where? (type a direction from the exits list or an exit name)");
                } else {
                    Room next = currentRoom.getExit(noun);
                    if (next == null) {
                        System.out.println("You can't go that way.");
                    } else {
                        player.moveTo(next);
                        currentRoom = player.getCurrentRoom();
                        currentRoom.enter(player);
                        GameDatabase.log(player.getName(), "Moved to " + currentRoom.getName());
                    }
                }
            }
            case TAKE -> {
                if (noun1 == null) {
                    System.out.println("Take what?");
                } else {
                    // variables to locate and hold item associated with room
                    RoomObject item =  room.getItem(noun1);

                    if (item == null) {
                        System.out.println("You don't see that item here.");
                    } else {
                        player.takeItem(item);
                        room.removeItem(item);
                        GameDatabase.log(player.getName(), "Took " + item.getName());
                    }
                }
            }
            case INVENTORY -> {
                player.showInventory();
                GameDatabase.log(player.getName(), "Checked inventory");
            }

            case LOOK_AROUND -> {
                LookAround();
                GameDatabase.log(player.getName(), "Looked around");
            }

            case EXAMINE -> {
                if (noun1 == null) {
                    System.out.println("Examine what?");
                } else  {
                    examine(noun);
                    GameDatabase.log(player.getName(), "Examined " + noun);
                }
            }
            case TALK -> {
                if (noun == null) {
                    System.out.println("Talk to whom?");
                    return;
                }
                NPC npc = currentRoom.getNpcByName(noun);
                if (npc == null) {
                    System.out.println("There's no one by that name here.");
                } else {
                    System.out.println(npc.getName() + " says: \"" + npc.speak() + "\"");
                    GameDatabase.log(player.getName(), "Talked with " + npc.getName());
                }
            }
            case USE -> {
                if (noun1 == null) {
                    System.out.println("Use what?");
                } else  {
                    if (noun2 == null) {
                        System.out.println("Use " + noun1 + " on what?");
                    } else {
                        // find objects
                        RoomObject object1 = room.getItem(noun1);
                        RoomObject object2 = room.getItem(noun2);

                        // inventory fallback when item 1 is in inventory
                        if (object1 == null) { object1 = player.getInventoryItem(noun1); }

                        if (object1 == null) {
                            System.out.println("You don't have or see a '" + noun1 + "'.");
                            return;
                        }

                        if (object2 == null) {
                            System.out.println("You don't see a '" + noun2 + "' here.");
                            return;
                        }

                        player.use(object1, object2);
                    }
                }
            }
            case SAVE -> {
                GameDatabase.saveGame(player);
                System.out.println("Game saved for player '" + player.getName() + "'.");
            }
            case LOAD -> {
                SaveData sd = GameDatabase.loadGame(player.getName());
                if (sd == null) {
                    System.out.println("No saved game found for player '" + player.getName() + "'.");
                } else {
                    // reconstruct player & room
                    Room start = rooms.getOrDefault(sd.currentRoom, rooms.get("RoomKitchen"));
                    player = new Player(sd.playerName, start);
                    currentRoom = player.getCurrentRoom();
                    if (sd.inventory != null && !sd.inventory.isBlank()) {
                        for (String it : sd.inventory.split(",")) player.takeItem(new Item(it.trim(), "Restored item"));
                    }
                    System.out.println("Game loaded.");
                    currentRoom.enter(player);
                }
            }
            case HELP -> {
                showHelp();
            }
            case EXIT -> {
                running = false;
                System.out.println("Exiting game...");
                GameDatabase.log(player.getName(), "Exited game");
            }
            default -> System.out.println("Unknown command.");
        }
    }

    // Look Method : This method will print a description of rooms and the items contained in it
    private void LookAround(){
        Room r =  player.getCurrentRoom();
        List<RoomObject> visibleItems = r.getVisibleItems();
        System.out.println(r.getDescription());

        // Shows all the items available in the room
        if (!visibleItems.isEmpty()) {
            System.out.println("Items here: ");
            for (RoomObject item : visibleItems) {
                System.out.println(" - " + item.getName());
            }
        } else {
            System.out.println("No items visible here.");
        }

        // Shows all the exits in the room
        if (!r.getExitNames().isEmpty()) {
            System.out.println("Exits here: ");
            for (String name : r.getExitNames()) {
                System.out.println(name);
            }
            System.out.println();
        }
    }

    // Examine method used for the player to examine objects
    private void examine(String noun) {
        Room r = player.getCurrentRoom();
        List<RoomObject> visibleItems = r.getVisibleItems();

        for (RoomObject item : visibleItems) {
            if (item.getName().equalsIgnoreCase(noun)) {
                String description = item.getDescription();
                System.out.println(description);
                return;
            }
        }
        // Or if it's in your inventory, show that description
        for (RoomObject it : player.getInventory()) {
            if (it.getName().equalsIgnoreCase(noun)) {
                System.out.println(it.getDescription());
                return;
            }
        }
        // check special objects by name
        if (noun.equalsIgnoreCase("sink")) {
            System.out.println("The old sink still runs. Maybe it could clean something.");
            return;
        }
        System.out.println("You don't see that here.");
    }

    private void useItem(String noun) {
        if (!player.hasItem(noun)) {
            System.out.println("You don't have that item.");
            return;
        }
        // sample: using brass key in kitchen opens a cupboard and reveals a coin
        if (noun.equalsIgnoreCase("Brass Key") || noun.equalsIgnoreCase("portrait_key")) {
            if (currentRoom instanceof RoomKitchen) {
                if (!player.hasItem("Ancient Coin")) {
                    Item coin = new Item("Ancient Coin", "An engraved coin with strange symbols.");
                    player.takeItem(coin);
                    System.out.println("You used the key and found an Ancient Coin inside a cupboard!");
                    GameDatabase.log(player.getName(), "Used key and found Ancient Coin");
                } else {
                    System.out.println("You already found the coin here.");
                }
            } else {
                System.out.println("You use the key but nothing happens here.");
            }
            return;
        }
        System.out.println("You try to use it, but nothing important happens.");
    }

    /**
     * HELP option 3: full help + dynamic listing of exits and items
     */
    private void showHelp() {
        System.out.println();
        System.out.println("=== HELP ===");
        System.out.println("Commands:");
        System.out.println(" go [direction|room]   — Move to an available exit");
        System.out.println(" look                  — Examine the current room");
        System.out.println(" examine [object]      — Examine an object or item");
        System.out.println(" take [item]           — Pick up an item in the room");
        System.out.println(" use [item]            — Use an item from your inventory");
        System.out.println(" talk [npc]            — Talk to someone in the room");
        System.out.println(" inventory             — List items you're carrying");
        System.out.println(" save                  — Save your game to the database");
        System.out.println(" load                  — Load your saved game from the database");
        System.out.println(" help                  — Show this help menu");
        System.out.println(" exit                  — Quit the game");
        System.out.println();
        // dynamic information:
        System.out.println("Exits from here:");
        List<String> exits = currentRoom.getExitNames();
        if (exits.isEmpty()) {
            System.out.println(" (none)");
        } else {
            for (String e : exits) System.out.println(" - " + e);
        }
        System.out.println();
        System.out.println("Items in this room:");
        if (currentRoom.getItems().isEmpty()) {
            System.out.println(" (none)");
        } else {
            for (Item it : currentRoom.getItems()) System.out.println(" - " + it.getName());
        }
        System.out.println();
        System.out.println("Your inventory:");
        if (player.getInventory().isEmpty()) {
            System.out.println(" (empty)");
        } else {
            for (Item it : player.getInventory()) System.out.println(" - " + it.getName());
        }
        System.out.println("=== END HELP ===");
        System.out.println();
    }
}

