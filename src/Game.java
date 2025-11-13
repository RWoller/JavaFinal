import java.util.*;

public class Game {
    private Player player;
    private Map<String, Room> rooms;
    private boolean running = true;

    public Game() {
        setupWorld();
    }

    private void setupWorld() {
        Room kitchen = new Room("Kitchen", "You see a deep sink and dusty cupboards. There's a cookbook that looks well-used.");
        Room whatever1 = new Room("Whatever1", "Write whatever");
        Room whatever2 = new Room("Whatever2", "Write whatever");
        Room whatever3 = new Room("Whatever3", "Maybe this could be the exit?");

        kitchen.addExit("east", whatever1);
        whatever1.addExit("west", kitchen);
        whatever1.addExit("south", whatever2);
        whatever2.addExit("north", whatever1);
        whatever2.addExit("west", whatever3);
        whatever3.addExit("east", whatever2);

        kitchen.addItem(new Item("Cookbook", "Greased up cookbook with a bunch of notes written on it"));
        kitchen.addItem(new Item("Soap", "Cuts through grease and grime."));
        kitchen.addItem(new Item("Sink", "Old but still has running water"));
        kitchen.addItem(new Item("Rag", "Old but still absorbent."));
        whatever1.addItem(new Item("Item1", "Item1"));
        whatever2.addItem(new Item("Item2", "Item2"));
        whatever3.addItem(new Item("Item3", "Item3"));

        rooms = new HashMap<>();
        rooms.put("Kitchen", kitchen);
        rooms.put("Whatever1", whatever1);
        rooms.put("Whatever2", whatever2);
        rooms.put("Whatever3", whatever3);

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
                    }
                }
            }
            case INVENTORY -> player.showInventory();
            case LOOK_AROUND -> lookAround();
            case EXAMINE -> examine(noun);
            case USE -> System.out.println("Use is not yet implemented.");
            case EXIT -> {
                running = false;
                System.out.println("Exiting game...");
            }
            default -> System.out.println("Unknown command.");
        }
    }

    private void lookAround() {
        Room r = player.getCurrentRoom();
        System.out.println(r.getDescription());

        if (!r.getItems().isEmpty()) {
            System.out.println("Items here:");
            for (Item item : r.getItems()) {
                System.out.println(" - " + item.getName());
            }
        }

        if (!r.getExitNames().isEmpty()) {
            System.out.println("Exits:");
            for (String exit : r.getExitNames()) {
                System.out.println(" - " + exit);
            }
        }
    }

    private void examine(String noun) {
        Room r = player.getCurrentRoom();

        if (noun == null) {
            System.out.println("Examine what?");
            return;
        }

        if (noun.equalsIgnoreCase("sink")) {
            System.out.println("The old sink still runs. Maybe it could clean something.");
        } else if (noun.equalsIgnoreCase("soap")) {
            System.out.println("The soap looks good enough to clean grime and gunk.");
        } else if (noun.equalsIgnoreCase("rag")) {
            System.out.println("The rag looks old, but still absorbent.");
        } else {
            for (Item it : player.getInventory()) {
                if (it.getName().equalsIgnoreCase(noun)) {
                    System.out.println(it.getDescription());
                    return;
                }
            }
            System.out.println("You don't see anything special about that.");
        }
    }

    public SaveData toSaveData() {
        SaveData sd = new SaveData();
        sd.playerName = player.getName();
        sd.health = player.getHealth();
        sd.level = 1;
        sd.currentRoom = player.getCurrentRoom().getName();
        return sd;
    }

    public static Game fromSave(SaveData save) {
        Game g = new Game();
        Room startRoom = g.rooms.getOrDefault(save.currentRoom, g.rooms.get("Kitchen"));
        g.player = new Player(save.playerName, startRoom);
        g.player.takeDamage(100 - save.health);
        return g;
    }
}


