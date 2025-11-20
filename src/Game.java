import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Game {
    private Player player;
    private Map<String, Room> rooms;
    private boolean running = true;

    public Game() {

        setupWorld();
    }

    private void setupWorld() {
        /// TODO: Change this and use embedded strings
        // Create rooms for our game
        Room kitchen = new RoomKitchen();
        Room roomOne = new RoomOne();
        Room roomTwo = new RoomTwo();
        Room roomThree = new RoomThree();

        // Connect all the rooms
        kitchen.addExit("east", roomOne);
        roomOne.addExit("west", kitchen);

        roomOne.addExit("south", roomTwo);
        roomTwo.addExit("north", roomOne);

        roomTwo.addExit("west", roomThree);
        roomThree.addExit("east", roomTwo); // Player can finish game through this room

        // Add all rooms to a hashmap
        rooms = new HashMap<>();
        rooms.put("Kitchen", kitchen);
        rooms.put("RoomOne", roomOne);
        rooms.put("RoomTwo", roomTwo);
        rooms.put("RoomThree", roomThree);

        // Create new player instance
        /// TODO: Maybe add a way to name the player on game start
        player = new Player("Hero", kitchen);
    }

    // Main game loop, prompts for text input
    public void run() {
        System.out.println("You open your eyes in a strange mansion. . . Type 'help' for a list of commands.");

        try(Scanner scanner = new Scanner(System.in)) {
            while (running) {
                System.out.print("> ");
                String input = scanner.nextLine();
                CommandParser.ParsedCommand command = CommandParser.parse(input);

                if (command.getType() == null) {
                    System.out.println("Invalid command. Try 'help' for a list of commands.");
                    continue;
                }
                handleInput(command);
            }
        }
        System.out.println("Thanks for playing!");
    }

    // Runs input string through command parser to interpret verb/noun combos and run commands with them
    private void handleInput(CommandParser.ParsedCommand cmd) {
        CommandType type = cmd.getType();
        String noun = cmd.getNoun();

        if (type == null) {
            System.out.println("I don't understand that command.");
            return;
        }

        switch (type) {
            case GO -> {
                if (noun == null) {
                    System.out.println("Go where?");
                } else {
                    player.move(noun); // e.g., move("north")
                }
            }
            case TAKE -> {
                if (noun == null) {
                    System.out.println("Take what?");
                } else {
                    // variables to locate and hold item associated with room
                    Room room = player.getCurrentRoom();
                    Item item =  room.getItem(noun);

                    if (item == null) {
                        System.out.println("You don't see that item here.");
                    } else {
                        if (item.isCanTake()){
                            player.takeItem(item);
                            room.removeItem(item);
                        } else {
                            System.out.println("This is not an item that you can take.");
                        }

                    }
                }
            }
            case INVENTORY -> player.showInventory();

            case LOOK_AROUND -> LookAround();

            case EXAMINE -> {
                if (noun == null) {
                    System.out.println("Examine what?");
                } else  {
                    examine(noun);
                }
            }

            case USE -> {
                if (noun == null) {
                    System.out.println("Use what?");
                } else  {
                    use(noun); // ToDo: Create Use method
                }
            }

            case EXIT -> {
                running = false;
                System.out.println("Goodbye!");
            }

            case HELP -> {
                // Simple help text for the player
                System.out.println("Available commands:");
                System.out.println("- go <direction>   (north, south, east, west)");
                System.out.println("- take <item>");
                System.out.println("- inventory");
                System.out.println("- look or look around");
                System.out.println("- examine <item>");
                System.out.println("- use <item>");
                System.out.println("- help");
                System.out.println("- exit");
            }

            case SAVE -> {
                toSaveData();
                System.out.println("Saved data!");
            }

            case LOAD -> {
            }
            default -> System.out.println("That command isn't implemented yet.");
        }
    }

    // Look Method : This method will print a description of rooms and the items contained in it
    private void LookAround(){
        Room r =  player.getCurrentRoom();
        System.out.println(r.getDescription());

        // Shows all the items available in the room
        if (!r.getItems().isEmpty()) {
            System.out.println("Items here: ");
            for (Item item : r.getItems()) {
                System.out.println(item.getName());
            }
            System.out.println();
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

        // Kitchen - Examine cookbook and add key to inventory
        if (noun.equalsIgnoreCase("cookbook")) {
            System.out.println("You open the cookbook and find a sticky, gunk-covered key inside.");

            // Only give the key one time
            if (!player.hasItem("Gunky Key") && !player.hasItem("Clean Key")) {
                Item gunkyKey = new Item("Gunky Key", "A metal key caked in gunk. It won't fit into a keyhole yet.", true);
                player.takeItem(gunkyKey);
                System.out.println("You take the Gunky Key.");
            } else {
                System.out.println("You've already taken the key.");
            }
            return;
        }

        // Kitchen - Examine sink
        if (noun.equalsIgnoreCase("sink")) {
            System.out.println("The old sink still runs. Maybe it could clean something.");
            return;
        }

        // Kitchen - Examine soap
        if (noun.equalsIgnoreCase("soap")) {
            System.out.println("The soap looks good enough to clean grime and gunk.");
            return;
        }

        // Kitchen - Examine rag
        if (noun.equalsIgnoreCase("rag")) {
            System.out.println("The rag looks old, but still absorbent.");
            return;
        }

        // Exit room - Examine heavy door
        if (noun.equalsIgnoreCase("heavy door")) {
            System.out.println("The Heavy Door has a narrow keyhole. A properly cleaned key might fit.");
            return;
        }

        // If it's in the current room, show that description
        Item here = r.getItem(noun);
        if (here != null) {
            System.out.println(here.getDescription());
            return;
        }

        // Or if it's in your inventory, show that description
        for (Item it : player.getInventory()) {
            if (it.getName().equalsIgnoreCase(noun)) {
                System.out.println(it.getDescription());
                return;
            }
        }

        System.out.println("You don't notice anything special.");
    }


    // use method for player to use their items
    private void use(String noun) {
        Room r = player.getCurrentRoom();

        // Use sink to clean the Gunky Key if the player has the rag or soap
        if (noun.equalsIgnoreCase("sink")) {
            boolean hasGunkyKey = player.hasItem("Gunky Key");
            boolean hasSoap = player.hasItem("Soap");
            boolean hasRag = player.hasItem("Rag");

            if (!hasGunkyKey) {
                System.out.println("You splash some water around. Nothing important happens.");
                return;
            }

            if (hasSoap || hasRag) {
                if (player.removeItemByName("Gunky Key")) {
                    System.out.println("You scrub the gunk away. You now have a Clean Key.");
                    player.takeItem(new Item("Clean Key", "A shiny key that should fit the lock.", true));
                } else {
                    System.out.println("You fumble the key. Try again.");
                }
            } else {
                System.out.println("You rinse the key, but the gunk stays. Maybe use soap or a rag with it.");
            }
            return;
        }

        // Use key or use door
        if (noun.equalsIgnoreCase("key") || noun.equalsIgnoreCase("clean key") || noun.equalsIgnoreCase("door")) {

            // Ensure that the key can only be used in the last room
            if (!(r instanceof RoomThree)) {
                System.out.println("There's nothing here that this key fits.");
                return;
            }

            // If the player does not have the clean key, the alarm will activate and they will not be able to unlock door
            if (!player.hasItem("Clean Key")) {
                System.out.println("The key is still too dirty, or you don't have the cleaned key.");
                // To trigger alarm if they try to force it
                AlarmSystem.getInstance().ActivateAlarm("Someone is messing with the door without the right key.");
                return;
            }

            // Ensure that the alarm is deactivated before the door can be opened.
            if (AlarmSystem.getInstance().isAlarmActive()){
                System.out.println("You cannot open the door with the alarm on. Deactivate alarm with keypad and try again");
                return;
            }
            System.out.println("You insert the Clean Key into the Heavy Door. It turns with a heavy click.");
            System.out.println("The door opens. You step outside. You escaped the mansion!");
            running = false;
            return;
        }

        if (noun.equalsIgnoreCase("alarm") || noun.equalsIgnoreCase("alarm keypad")) {
            AlarmSystem status = AlarmSystem.getInstance();
            if (status.isAlarmActive()) {
                status.DeactivateAlarm();
                (System.out).println("Alarm has been deactivated.");
            } else {
                status.ActivateAlarm(" you used the keypad");
                System.out.println("Alarm has been activated.");
            }
            return;
        }

        System.out.println("Nothing happens.");
    }

    public SaveData toSaveData() {
        SaveData sd = new SaveData();
        sd.playerName = player.getName();
        sd.health = player.getHealth();
        sd.level = 1;
        sd.currentRoom = player.getCurrentRoom().getName();
        return sd;
    }


    public static Game fromSave(SaveData save) {
        Game g = new Game();
        Room startRoom = g.rooms.getOrDefault(save.currentRoom, g.rooms.get("Kitchen"));
        g.player = new Player(save.playerName, startRoom);
        g.player.takeDamage(100 - save.health);
        return g;
    }
}
