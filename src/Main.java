import java.util.Scanner;

/**
 * Entry point: main menu and starting the game.
 */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        GameDatabase.init();

        System.out.println(Strings.get("welcome"));
        while (true) {
            printMainMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> startNewGame();
                case "2" -> loadGame();
                case "3" -> {
                    System.out.println(Strings.get("end"));
                    GameDatabase.log("System", "Exited game from main menu");
                    return;
                }
                default -> System.out.println(Strings.get("menu_error"));
            }
        }
    }

    private static void printMainMenu() {
        System.out.println(Strings.get("menu"));
        System.out.println("1. " + Strings.get("new_game"));
        System.out.println("2. " + Strings.get("load_game"));
        System.out.println("3. " + Strings.get("exit_game"));
        System.out.print(Strings.get("choose_option"));
    }

    private static void startNewGame() {
        System.out.print(Strings.get("enter_name"));
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) name = Strings.get("default_name");
        Game game = new Game();
        // name the player
        game.setPlayer(new Player(name, game.getRoom("RoomKitchen")));
        game.setCurrentRoom(game.getPlayer().getCurrentRoom());
        GameDatabase.log(name, "Started new game");
        game.run();
    }

    private static void loadGame() {
        System.out.print(Strings.get("load_message"));
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println(Strings.get("load_no_name"));
            return;
        }
        SaveData sd = GameDatabase.loadGame(name);
        if (sd == null) {
            System.out.println(Strings.get("load_no_save", name));
            GameDatabase.log("System", "Attempted to load non-existent save: " + name);
            return;
        }
        Game game = new Game(sd);
        GameDatabase.log(name, Strings.get("load_success"));
        game.run();
    }
}

