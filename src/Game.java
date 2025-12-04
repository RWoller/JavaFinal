import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Game {
    private Player player;
    private Map<String, Room> rooms;
    private boolean running = true;

    public Game() {
        GameDatabase.init(); // Initialize database
        setupWorld();
    }

    private void setupWorld() {
        Room kitchen = new RoomKitchen();
        Room roomOne = new RoomOne();
        Room roomTwo = new RoomTwo();
        Room roomThree = new RoomThree();

        kitchen.addExit("east", roomOne);
        roomOne.addExit("west", kitchen);
        roomOne.addExit("south", roomTwo);
        roomTwo.addExit("north", roomOne);
        roomTwo.addExit("west", roomThree);
        roomThree.addExit("east", roomTwo);

        rooms = new HashMap<>();
        rooms.put("Kitchen", kitchen);
        rooms.put("RoomOne", roomOne);
        rooms.put("RoomTwo", roomTwo);
        rooms.put("RoomThree", roomThree);

        player = new Player("Hero", kitchen);
    }

    public void run() {
        System.out.println("You open your eyes in a strange mansion... Type 'help' for a list of commands.");
        try (Scanner scanner = new Scanner(System.in)) {
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

    private void handleInput(CommandParser.ParsedCommand cmd) {
        CommandType type = cmd.getType();
        String noun = cmd.getNoun();

        switch (type) {
            case GO -> {
                if (noun == null) {
                    System.out.println("Go where?");
                } else {
                    player.move(noun);
                    GameDatabase.log(player.getName(), "Moved " + noun);
                }
            }
            case TAKE -> {
                if (noun == null) {
                    System.out.println("Take what?");
                } else {
                    Room room = player.getCurrentRoom();
                    Item item = room.getItem(noun);
                    if (item == null) {
                        System.out.println("You don't see that item here.");
                    } else {
                        player.takeItem(item);
                        room.removeItem(item);
                        GameDatabase.log(player.getName(), "Took " + item.getName());
                    }
                }
            }
            case INVENTORY -> {
                player.showInventory();
                GameDatabase.log(player.getName(), "Checked inventory");
            }
            case LOOK_AROUND -> {
                LookAround();
                GameDatabase.log(player.getName(), "Looked around");
            }
            case EXAMINE -> {
                examine(noun);
                GameDatabase.log(player.getName(), "Examined " + noun);
            }
            case EXIT -> {
                running = false;
                System.out.println("Exiting game...");
                GameDatabase.log(player.getName(), "Exited game");
            }
            default -> System.out.println("Unknown command.");
        }
    }

    private void LookAround() {
        Room r = player.getCurrentRoom();
        System.out.println(r.getDescription());

        if (!r.getItems().isEmpty()) {
            System.out.println("Items here:");
            for (Item item : r.getItems()) {
                System.out.println(item.getName());
            }
        }

        if (!r.getExitNames().isEmpty()) {
            System.out.println("Exits here:");
            for (String name : r.getExitNames()) {
                System.out.println(name);
            }
        }
    }

    private void examine(String noun) {
        Room r = player.getCurrentRoom();

        if (noun.equalsIgnoreCase("sink")) {
            System.out.println("The old sink still runs. Maybe it could clean something.");
            return;
        }
        if (noun.equalsIgnoreCase("soap")) {
            System.out.println("The soap looks good enough to clean grime and gunk.");
            return;
        }
        if (noun.equalsIgnoreCase("rag")) {
            System.out.println("The rag looks old, but still absorbent.");
            return;
        }

        for (Item it : player.getInventory()) {
            if (it.getName().equalsIgnoreCase(noun)) {
                System.out.println(it.getDescription());
                return;
            }
        }
        System.out.println("You don't see that here.");
    }

    private void use(String noun) {
        Room r = player.getCurrentRoom();

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
                    player.takeItem(new Item("Clean Key", "A shiny key that should fit the lock."));
                    System.out.println("You scrub the gunk away. You now have a Clean Key.");
                } else {
                    System.out.println("You fumble the key. Try again.");
                }
            } else {
                System.out.println("You rinse the key, but the gunk stays. Maybe use soap or a rag with it.");
            }
            return;
        }

        if (noun.equalsIgnoreCase("key") || noun.equalsIgnoreCase("clean key")) {
            if (!player.hasItem("Clean Key")) {
                System.out.println("The key is still too dirty, or you don't have the cleaned key.");
                AlarmSystem.getInstance().ActivateAlarm("Someone is messing with the door without the right key.");
                return;
            }
            if (!(r instanceof RoomThree)) {
                System.out.println("There's nothing here that this key fits.");
                return;
            }
            System.out.println("You insert the Clean Key into the Heavy Door. It turns with a heavy click.");
            System.out.println("The door opens. You step outside. You escaped the mansion!");
            running = false;
            return;
        }

        System.out.println("Nothing happens.");
    }
}
