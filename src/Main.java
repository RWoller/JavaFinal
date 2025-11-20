
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static Game currentGame = null;

    public static void main(String[] args) {
        while (true) {
            printMainMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    currentGame = new Game();
                    currentGame.run();
                    break;
                case "2":
                    SaveData loaded = Savepoint.load();
                    if (loaded == null) {
                        System.out.println("No save found or load failed.");
                    } else {
                        currentGame = Game.fromSave(loaded);
                        System.out.println("Loaded game for: " + loaded.playerName);
                        currentGame.run();
                    }
                    break;
                case "3":
                    if (currentGame == null) {
                        System.out.println("No current game to save. Start a new game first.");
                    } else {
                        boolean ok = Savepoint.save(currentGame.toSaveData());
                        System.out.println(ok ? "Game saved successfully!" : "Save failed.");
                    }
                    break;
                case "4":
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void printMainMenu() {
        System.out.println("\n== Welcome to Fractured Memo ==");
        System.out.println("1. New Game");
        System.out.println("2. Load Game");
        System.out.println("3. Save Game");
        System.out.println("4. Exit");
        System.out.print("Choose an option: ");
    }
}
