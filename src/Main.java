import java.util.Scanner;

public class Main {
    // One Scanner and one DatabaseManager for the whole program
    private static final Scanner scanner = new Scanner(System.in);
    private static final DatabaseManager dbManager = new DatabaseManager();
    private static Game currentGame = null;

    public static void main(String[] args) {
        while (true) {
            printMainMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                // New Game
                case "1" -> {
                    // Ask player name
                    System.out.print("Enter your character's name: ");
                    String name = scanner.nextLine().trim();

                    // Create a new game world
                    currentGame = new Game(dbManager, scanner);

                    // Set player name on the Player object
                    currentGame.getPlayer().setName(name);

                    // Start the game loop
                    currentGame.run();
                }

                // Load Game
                case "2" -> {
                    SaveData loaded = Savepoint.load();
                    if (loaded == null) {
                        System.out.println(Strings.get("load.none"));
                    } else {
                        currentGame = Game.fromSave(loaded, dbManager, scanner);
                        System.out.println(Strings.get("load.welcome", loaded.playerName));
                        if (loaded.savedAt != null) {
                            System.out.println(Strings.get("load.time", loaded.savedAt));
                        }
                        currentGame.run();
                    }
                }

                // Save Game
                case "3" -> {
                    if (currentGame == null) {
                        System.out.println(Strings.get("save.noGame"));
                    } else {
                        boolean ok = Savepoint.save(currentGame.toSaveData());
                        System.out.println(ok ? Strings.get("save.success") : Strings.get("save.failed"));
                    }
                }

                // Delete Saved Game
                case "4" -> {
                    boolean deleted = Savepoint.deleteSave();
                    if (deleted) {
                        System.out.println("Saved game deleted.");
                    } else {
                        System.out.println("No saved game to delete.");
                    }
                }

                // View escape records from JDBC database
                case "5" -> dbManager.displayEscapeRecords();

                // Exit program
                case "6" -> {
                    System.out.println("Goodbye!");
                    return;
                }

                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void printMainMenu() {
        // Localization / ResourceBundle
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
