import org.apache.logging.log4j.core.tools.picocli.CommandLine;

import java.util.HashMap;
import java.util.List;
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
        Room kitchen = new Room("Kitchen", "You see a deep sink and and dusty cupboards. Theres a cookbook that looks well-used");
        Room whatever1 = new Room("Whatever1,", "Write whatever");
        Room whatever2 = new Room("Whatever2", "Write whatever");
        Room whatever3 = new Room("Whatever3", "Maybe this could be the exit?");

        // Connect all the rooms
        kitchen.addExit("east", whatever1);
        whatever1.addExit("west", kitchen);

        whatever1.addExit("south", whatever2);
        whatever2.addExit("north", whatever1);

        whatever2.addExit("west", whatever3);
        whatever3.addExit("east", whatever2); // Player can finish game through this room

        // Add items to kitchen
        kitchen.addItem(new Item("Cookbook", "Greased up cookbook with a bunch of notes written on it", true));
        kitchen.addItem(new Item("Soap", "Cuts through grease and grime.", true));
        kitchen.addItem(new Item("Sink", "Old but still has running water", true));
        kitchen.addItem(new Item("Rag", "Old but still absorbent.", true));

        // Stubs for adding items is different rooms.
        whatever1.addItem(new Item("Item1", "Item1", true));
        whatever2.addItem(new Item("Item2", "Item2", true));
        whatever3.addItem(new Item("Item3", "Item3", true));


        // Add all rooms to a hashmap
        rooms = new HashMap<>();
        rooms.put("Kitchen", kitchen);

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
                    RoomObject item =  room.getItem(noun);

                    if (item == null) {
                        System.out.println("You don't see that item here.");
                    } else {
                        player.takeItem(item);
                        room.removeItem(item);
                    }
                }
            }
            case INVENTORY -> player.showInventory();

            case LOOK_AROUND -> LookAround();

            case EXAMINE -> {
                if (noun == null) {
                    System.out.println("Examine what?");
                } else  {
                    Room room = player.getCurrentRoom(); // ToDo: change to examine room?
                }
            }

            case USE -> {
                if (noun == null) {
                    System.out.println("Use what?");
                } else  {
                    Room room = player.getCurrentRoom(); // ToDo: Create Use method
                }
            }

            case EXIT -> {
                running = false;
                System.out.println("Goodbye!");
            }
            default -> System.out.println("That command isn't implemented yet.");
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

        // Kitchen - Examine sink
        if (noun.equalsIgnoreCase("sink")) {
            System.out.println("The old sink still runs. Maybe it could clean something.");
            return;
        }

        if (noun.equalsIgnoreCase("soap")) {
            System.out.println("The soap looks good enough to clean grime and gunk");
        }

        // Kitchen - Examine rag
        if (noun.equalsIgnoreCase("rag")) {
            System.out.println("The rag looks old, but still absorbent.");
        }

        // Or if it's in your inventory, show that description
        for (RoomObject it : player.getInventory()) {
            if (it.getName().equalsIgnoreCase(noun)) {
                System.out.println(it.getDescription());
                return;
            }
        }
        System.out.println();
    }

}
