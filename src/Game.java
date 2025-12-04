import java.util.*;

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
        RoomKitchen kitchen = new RoomKitchen();
        RoomLivingQuarters roomLivingQuarters = new RoomLivingQuarters();
        RoomGreenhouse roomGreenhouse = new RoomGreenhouse();
        RoomRoyalChamber roomRoyalChamber = new RoomRoyalChamber();

        // connect rooms (consistent keys used in GameDatabase saves)
        kitchen.addExit("east", roomLivingQuarters);
        roomLivingQuarters.addExit("west", kitchen);
        roomLivingQuarters.addExit("south", roomGreenhouse);
        roomGreenhouse.addExit("north", roomLivingQuarters);
        roomGreenhouse.addExit("west", roomRoyalChamber);
        roomRoyalChamber.addExit("east", roomGreenhouse);

        rooms.put("RoomKitchen", kitchen);
        rooms.put("RoomOne", roomLivingQuarters);
        rooms.put("RoomTwo", roomGreenhouse);
        rooms.put("RoomThree", roomRoyalChamber);

        // place sample items (kept in constructors for rooms)
    }

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

    private void handleInput(CommandParser.ParsedCommand cmd) {
        CommandType type = cmd.getType();
        String noun = cmd.getNoun();

        switch (type) {
            case GO -> {
                if (noun == null) {
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
                if (noun == null) {
                    System.out.println("Take what?");
                    return;
                }
                Item item = currentRoom.getItem(noun);
                if (item == null) {
                    System.out.println("You don't see that item here.");
                } else {
                    player.takeItem(item);
                    currentRoom.removeItem(item);
                    System.out.println("You take the " + item.getName() + ".");
                    GameDatabase.log(player.getName(), "Took " + item.getName());
                }
            }
            case INVENTORY -> {
                player.showInventory();
                GameDatabase.log(player.getName(), "Checked inventory");
            }
            case LOOK -> {
                lookAround();
                GameDatabase.log(player.getName(), "Looked around");
            }
            case EXAMINE -> {
                if (noun == null) {
                    System.out.println("Examine what?");
                    return;
                }
                examine(noun);
                GameDatabase.log(player.getName(), "Examined " + noun);
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
                if (noun == null) {
                    System.out.println("Use what?");
                    return;
                }
                useItem(noun);
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

    private void lookAround() {
        System.out.println(currentRoom.getDescription());

        if (!currentRoom.getItems().isEmpty()) {
            System.out.println("Items here:");
            for (Item item : currentRoom.getItems()) {
                System.out.println(" - " + item.getName());
            }
        } else {
            System.out.println("No items visible here.");
        }

        List<String> exits = currentRoom.getExitNames();
        if (!exits.isEmpty()) {
            System.out.println("Exits here:");
            for (String ex : exits) System.out.println(" - " + ex);
        } else {
            System.out.println("No exits visible.");
        }
    }

    private void examine(String noun) {
        // check items in room first
        Item roomItem = currentRoom.getItem(noun);
        if (roomItem != null) {
            System.out.println(roomItem.getDescription());
            return;
        }
        // check inventory
        for (Item it : player.getInventory()) {
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

