import java.util.Scanner;

/**
 * Entry point for the Fractured Memo adventure game.
 * Handles the main menu and game startup.
 */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static Game currentGame = null;

    public static void main(String[] args) {
        // Initialize database connection and ensure logs table exists
        GameDatabase.init();

        System.out.println("== Welcome to Fractured Memo ==");

        while (true) {
            printMainMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    startNewGame();
                    break;
                case "2":
                    loadGame();
                    break;
                case "3":
                    System.out.println("Exiting game. Goodbye!");
                    GameDatabase.log("System", "Exited game from main menu");
                    return; // end program
                default:
                    System.out.println("Invalid option. Please choose 1, 2, or 3.");
                    break;
            }
        }
    }

    /** Prints the main menu options */
    private static void printMainMenu() {
        System.out.println("\nMain Menu:");
        System.out.println("1. New Game");
        System.out.println("2. Load Game");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");
    }

    /** Starts a new game session */
    private static void startNewGame() {
        System.out.print("Enter your character's name: ");
        String name = scanner.nextLine().trim();

        currentGame = new Game(); // Game constructor sets up world
        GameDatabase.log(name, "Started new game");

        currentGame.run(); // Starts the game loop
    }

    /** Placeholder for future save/load functionality */
    private static void loadGame() {
        System.out.println("Load Game feature is under construction.");
        GameDatabase.log("System", "Attempted to load game");
        // TODO: implement save/load later
    }
}
