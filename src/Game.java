import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Stream;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

/**
 * Core game loop and state manager.
 * Uses GameDatabase for saves and logs.
 * Uses Singleton Class Pattern
 */
public class Game {
    private Player player;
    private final Map<String, Room> rooms = new HashMap<>();
    private Room currentRoom;
    private boolean running = true;
    private final Scanner scanner = new Scanner(System.in);
    private static Game game;

    //
    private Game() {
        GameDatabase.init(); // ensure DB and tables exist
        setupWorld();
        // default start in kitchen
        player = new Player("Hero", rooms.get("RoomKitchen"));
        currentRoom = player.getCurrentRoom();
    }

    // 2.3 Example of Singleton Pattern
    public static Game getGame() {
        if (game == null) {
            game = new Game();
        }
        return game;
    }

    public static Game getGame(SaveData sd){
        game = new Game(sd);
        return game;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Room getRoom(String name) {
        return rooms.get(name);
    }

    public void setCurrentRoom(Room room) {
        this.currentRoom = room;
    }

    private Game(SaveData data) {
        GameDatabase.init();
        setupWorld();
        Room start = rooms.getOrDefault(data.currentRoom, rooms.get("RoomKitchen"));
        player = new Player(data.playerName, start);
        currentRoom = player.getCurrentRoom();
        if (data.inventory != null && !data.inventory.isBlank()) {
            for (String name : data.inventory.split(",")) {
                player.takeItem(new Item(name.trim(), "Restored item"));
            }
        }
    }

    private void setupWorld() {
        RoomKitchen kitchen = new RoomKitchen();
        RoomLivingQuarters roomLivingQuarters = new RoomLivingQuarters();
        RoomGreenhouse roomGreenhouse = new RoomGreenhouse();
        RoomRoyalChamber roomRoyalChamber = new RoomRoyalChamber();

        // connect rooms (consistent keys used in GameDatabase saves)
        kitchen.addExit(Strings.get("east"), roomLivingQuarters);
        roomLivingQuarters.addExit(Strings.get("west"), kitchen);
        roomLivingQuarters.addExit(Strings.get("south"), roomGreenhouse);
        roomGreenhouse.addExit(Strings.get("north"), roomLivingQuarters);
        roomGreenhouse.addExit(Strings.get("west"), roomRoyalChamber);
        roomRoyalChamber.addExit(Strings.get("east"), roomGreenhouse);

        rooms.put("RoomKitchen", kitchen);
        rooms.put("RoomOne", roomLivingQuarters);
        rooms.put("RoomTwo", roomGreenhouse);
        rooms.put("RoomThree", roomRoyalChamber);

        // place sample items (kept in constructors for rooms)
    }

    public void run() {
        System.out.println(Strings.get("intro"));
        logToFile("Game started");
        if (currentRoom != null) currentRoom.enter(player);

        while (running) {
            System.out.print("> ");
            String input;
            try {
                input = scanner.nextLine();
                logToFile("Command entered: " + input);
            } catch (NoSuchElementException | IllegalStateException e) {
                logToFile("Error: " + e.getMessage());
                // input closed — exit gracefully
                running = false;
                break;
            }

            CommandParser.ParsedCommand command = CommandParser.parse(input);
            if (command.getType() == CommandType.UNKNOWN) {
                System.out.println(Strings.get("unknown_command"));
                continue;
            }

            handleInput(command);
        }

        System.out.println(Strings.get("end"));
    }

    private void handleInput(CommandParser.ParsedCommand cmd) {
        CommandType type = cmd.getType();
        String noun = cmd.getNoun();

        switch (type) {
            case GO -> {
                if (noun == null) {
                    System.out.println(Strings.get("go_no_direction"));
                } else {
                    Room next = currentRoom.getExit(noun);
                    if (next == null) {
                        System.out.println(Strings.get("go_invalid_direction"));
                    } else {
                        player.moveTo(next);
                        currentRoom = player.getCurrentRoom();
                        currentRoom.enter(player);
                        GameDatabase.log(player.getName(), "Moved to " + currentRoom.getName());
                    }
                }
            }
            case TAKE -> {
                if (noun == null) {
                    System.out.println(Strings.get("take_no_object"));
                    return;
                }
                Item item = currentRoom.getItem(noun);
                if (item == null) {
                    System.out.println(Strings.get("take_invalid_object"));
                } else {
                    player.takeItem(item);
                    currentRoom.removeItem(item);
                    System.out.println(Strings.get("take_success", item.getName()));
                    GameDatabase.log(player.getName(), "Took " + item.getName());
                }
            }
            case INVENTORY -> {
                player.showInventory();
                GameDatabase.log(player.getName(), "Checked inventory");
            }
            case LOOK -> {
                lookAround();
                GameDatabase.log(player.getName(), "Looked around");
            }
            case EXAMINE -> {
                if (noun == null) {
                    System.out.println(Strings.get("examine_no_target"));
                    return;
                }
                examine(noun);
                GameDatabase.log(player.getName(), "Examined " + noun);
            }
            case TALK -> {
                if (noun == null) {
                    System.out.println(Strings.get("talk_no_target"));
                    return;
                }
                NPC npc = currentRoom.getNpcByName(noun);
                if (npc == null) {
                    System.out.println(Strings.get("talk_invalid_target"));
                } else {
                    System.out.println(Strings.get("talk_to_npc", npc.getName(), npc.speak()));
                    GameDatabase.log(player.getName(), "Talked with " + npc.getName());
                }
            }
            case USE -> {
                if (noun == null) {
                    System.out.println(Strings.get("use_no_target"));
                    return;
                }
                useItem(noun);
            }
            case SAVE -> {
                GameDatabase.saveGame(player);
                System.out.println(Strings.get("save_success", player.getName()));
            }
            case LOAD -> {
                SaveData sd = GameDatabase.loadGame(player.getName());
                if (sd == null) {
                    System.out.println(Strings.get("load_no_save", player.getName()));
                } else {
                    // reconstruct player & room
                    Room start = rooms.getOrDefault(sd.currentRoom, rooms.get("RoomKitchen"));
                    player = new Player(sd.playerName, start);
                    currentRoom = player.getCurrentRoom();
                    if (sd.inventory != null && !sd.inventory.isBlank()) {
                        for (String it : sd.inventory.split(",")) player.takeItem(new Item(it.trim(), "Restored item"));
                    }
                    System.out.println(Strings.get("load_success"));
                    currentRoom.enter(player);
                }
            }
            case HELP -> {
                showHelp();
            }
            case EXIT -> {
                running = false;
                System.out.println(Strings.get("game_exit"));
                GameDatabase.log(player.getName(), "Exited game");
            }
            default -> System.out.println(Strings.get("unknown_command"));
        }
    }

    private void lookAround() {
        System.out.println(currentRoom.getDescription());

        if (!currentRoom.getItems().isEmpty()) {
            System.out.println(Strings.get("look_success"));
            for (Item item : currentRoom.getItems()) {
                System.out.println(" - " + item.getName());
            }
        } else {
            System.out.println(Strings.get("look_no_items"));
        }

        List<String> exits = currentRoom.getExitNames();
        if (!exits.isEmpty()) {
            System.out.println(Strings.get("exits_success"));
            for (String ex : exits) System.out.println(" - " + ex);
        } else {
            System.out.println(Strings.get("no_exits"));
        }
    }

    private void examine(String noun) {
        // check items in room first
        Item roomItem = currentRoom.getItem(noun);
        if (roomItem != null) {
            System.out.println(roomItem.getDescription());
            return;
        }
        // check inventory
        for (Item it : player.getInventory()) {
            if (it.getName().equalsIgnoreCase(noun)) {
                System.out.println(it.getDescription());
                return;
            }
        }
        // check special objects by name
        if (noun.equalsIgnoreCase(Strings.get("sink_name"))) {
            System.out.println(Strings.get("sink_examine"));
            return;
        }
        System.out.println(Strings.get("examine_invalid_target"));
    }

    private void useItem(String noun) {
        if (!player.hasItem(noun)) {
            System.out.println(Strings.get("use_invalid_target"));
            return;
        }
        // sample: using brass key in kitchen opens a cupboard and reveals a coin
        if (noun.equalsIgnoreCase(Strings.get("brass_key_name")) || noun.equalsIgnoreCase("portrait_key")) {
            if (currentRoom instanceof RoomKitchen) {
                if (!player.hasItem(Strings.get("ancient_coin_name"))) {
                    Item coin = new Item(Strings.get("ancient_coin_name"), Strings.get("ancient_coin_desc"));
                    player.takeItem(coin);
                    System.out.println(Strings.get("brass_key_use_success"));
                    GameDatabase.log(player.getName(), "Used key and found Ancient Coin");
                } else {
                    System.out.println(Strings.get("brass_key_use_already_found"));
                }
            } else {
                System.out.println(Strings.get("brass_key_use_failure"));
            }
            return;
        } else if (noun.equalsIgnoreCase(Strings.get("rapier_name"))) {
            if (player.hasItem(Strings.get("rapier_name"))){
                if(currentRoom instanceof RoomRoyalChamber) {
                    System.out.println(Strings.get("rapier_use_success"));
                    GameDatabase.log(player.getName(), "Completed Game");
                    running = false;
                } else {
                    System.out.println(Strings.get("rapier_use_failure"));
                }
            }
        }
        System.out.println(Strings.get("use_failure"));
    }

    /**
     * HELP option 3: full help + dynamic listing of exits and items
     */
    private void showHelp() {
        // Print command descriptions
        Stream.of(CommandType.values())
                .forEach(type -> System.out.println(type.getDescription()));

        // dynamic information:
        System.out.println(Strings.get("exits_success"));
        List<String> exits = currentRoom.getExitNames();
        if (exits.isEmpty()) {
            System.out.println(Strings.get("none"));
        } else {
            for (String e : exits) System.out.println(" - " + e);
        }
        System.out.println();
        System.out.println(Strings.get("look_success"));
        if (currentRoom.getItems().isEmpty()) {
            System.out.println(Strings.get("none"));
        } else {
            for (Item it : currentRoom.getItems()) System.out.println(" - " + it.getName());
        }
        System.out.println();
        System.out.println(Strings.get("inventory"));
        if (player.getInventory().isEmpty()) {
            System.out.println(Strings.get("empty"));
        } else {
            for (Item it : player.getInventory()) System.out.println(" - " + it.getName());
        }
        System.out.println(Strings.get("end_help"));
    }

    //5.1 - Use of Date and times
    //8.2 - File Writer Example that logs user commands and errors
    private void logToFile(String msg){
        try(FileWriter writer = new FileWriter("game_log.txt", true)){
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
            writer.write("[" + timestamp + "]" + msg + System.lineSeparator());
        }catch(IOException e){
            System.err.println("Logging failed");
        }
    }
}

