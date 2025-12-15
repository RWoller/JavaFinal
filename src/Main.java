import java.util.Scanner;

/**
 * Entry point: main menu and starting the game.
 */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        GameDatabase.init();

        System.out.println("== Welcome to Fractured Memo ==");
        while (true) {
            printMainMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> startNewGame();
                case "2" -> loadGame();
                case "3" -> {
                    System.out.println("Exiting game. Goodbye!");
                    GameDatabase.log("System", "Exited game from main menu");
                    return;
                }
                default -> System.out.println("Invalid option. Please choose 1, 2, or 3.");
            }
        }
    }

    private static void printMainMenu() {
        System.out.println("\nMain Menu:");
        System.out.println("1. New Game");
        System.out.println("2. Load Game (by player name)");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");
    }

    private static void startNewGame() {
        System.out.print("Enter your character's name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) name = "Hero";
        Game game = new Game();
        // name the player
        game.setPlayer(new Player(name, game.getRoom("RoomKitchen")));
        game.setCurrentRoom(game.getPlayer().getCurrentRoom());
        GameDatabase.log(name, "Started new game");
        game.run();
    }

    private static void loadGame() {
        System.out.print("Enter player name to load: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("No player name entered.");
            return;
        }
        SaveData sd = GameDatabase.loadGame(name);
        if (sd == null) {
            System.out.println("No saved game found for '" + name + "'.");
            GameDatabase.log("System", "Attempted to load non-existent save: " + name);
            return;
        }
        Game game = new Game(sd);
        GameDatabase.log(name, "Loaded game");
        game.run();
    }
}

