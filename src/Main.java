
import java.util.ResourceBundle;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static Game currentGame = null;

    public static void main(String[] args) {
        while (true) {
            printMainMenu();
            DatabaseManager dbManager = new DatabaseManager();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    // Prompt user for name and create the Player instance
                    System.out.print("Enter your character's name: ");
                    String name = scanner.nextLine().trim();

                    // Create the world to get the starting room "Kitchen

                    // The simplest way to get the starting room:
                    // Create a temporary, minimal Game instance just to get the starting room.
                    Game tempGame = new Game(null, dbManager, scanner);
                    Room startingRoom = tempGame.getRoom("Kitchen");

                    // Create the player with the chosen name and starting room
                    Player newPlayer = new Player(name, startingRoom);

                    // Create the game instance with the player and database manager
                    currentGame = new Game(newPlayer, dbManager, scanner);
                    currentGame.run();
                    break;
                case "2":
                    SaveData loaded = Savepoint.load();
                    if (loaded == null) {
                        System.out.println(Strings.get("load.none"));
                    } else {
                        currentGame = Game.fromSave(loaded, dbManager, scanner);
                        System.out.println(Strings.get("load.welcome", loaded.playerName));
                        currentGame.run();
                    }
                    break;
                case "3":
                    if (currentGame == null) {
                        System.out.println(Strings.get("save.noGame"));
                    } else {
                        boolean ok = Savepoint.save(currentGame.toSaveData());
                        System.out.println(ok ? Strings.get("save.success") : Strings.get("save.failed"));
                    }
                    break;
                case "4":
                    System.out.println("Goodbye!");
                    break;
                case "5": // Display records
                    dbManager.displayEscapeRecords();
                    break;
                case "6": // Exit
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void printMainMenu() {
        // ** String Localization **
        System.out.println("\n" + Strings.get("welcome.title"));
        System.out.println(Strings.get("menu.newGame"));
        System.out.println(Strings.get("menu.loadGame"));
        System.out.println(Strings.get("menu.saveGame"));
        System.out.println(Strings.get("menu.deleteSave"));
        System.out.println("5. View Escape Records");
        System.out.println("6. Exit");
        System.out.print(Strings.get("menu.choose"));
    }
}
