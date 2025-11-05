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
            System.out.println(player.getCurrentRoom().getDescription());
            System.out.print("> ");
            String input = scanner.nextLine();
            handleCommand(input.trim().toLowerCase());
        }
        scanner.close();
    }

    private void handleCommand(String input) {
        if (input.equals("quit")) {
            running = false;
            System.out.println("Thanks for playing!");
            return;
        } else if (input.startsWith("go ")) {
            String direction = input.substring(3);
            player.move(direction);
        } else if (input.equals("look")) {
            System.out.println(player.getCurrentRoom().getDescription());
        } else {
            System.out.println("I don't understand that command.");
        }
    }
}
