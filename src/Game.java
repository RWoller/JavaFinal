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
        Room start = new Room("Start", "You are in a small clearing with a path to the north.");
        Room forest = new Room("Forest", "Tall trees surround you. It’s quiet.");

        // Adding exits to our rooms
        start.addExit("north", forest);
        forest.addExit("south", start);

        // Add all rooms to a hashmap
        rooms = new HashMap<>();
        rooms.put("start", start);
        rooms.put("forest", forest);

        // Create new player instance
        /// TODO: Maybe add a way to name the player on game start
        player = new Player("Hero", start);
    }

    // Main game loop, prompts for text input
    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Adventure!");

        while (running) {
            System.out.print("> ");
            String input = scanner.nextLine();
            handleInput(input);
        }
        scanner.close();
    }

    // Runs input string through command parser to interpret verb/noun combos and run commands with them
    private void handleInput(String input) {
        CommandParser.ParsedCommand cmd = CommandParser.parse(input);
        CommandType type = cmd.getType();

        if (type == null) {
            System.out.println("I don't understand that command.");
            return;
        }

        String noun = cmd.getNoun();

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
                    player.takeItem(noun);
                }
            }
            case INVENTORY -> player.showInventory();
            case LOOK_AROUND -> System.out.println(player.getCurrentRoom().getDescription());
            case EXIT -> {
                running = false;
                System.out.println("Goodbye!");
            }
            default -> System.out.println("That command isn't implemented yet.");
        }
    }
}
