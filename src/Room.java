import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// ** Abstract **  Base room type. Other rooms extend this.
public abstract class Room {
    private String name;
    private String description;
    private Map<String, Room> exits = new HashMap<>();
    private List<RoomObject> items = new ArrayList<>();

    public Room(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    // Player can use a simple string like "Move north"
    public void addExit(String direction, Room room) {
        exits.put(direction.toLowerCase(), room);
    }

    public Room getExit(String direction) {
        if (direction == null) return null;
        return exits.get(direction.toLowerCase());
    }

    public List<String> getExitNames() {
        return new ArrayList<>(exits.keySet());
    }

    public void addItem(RoomObject object) {
        items.add(object);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public List<RoomObject> getItems() {
        return items;
    }

    // Find an item in this room by name
    public RoomObject getItem(String name) {
        for (RoomObject obj : items) {
            if (obj.getName().equalsIgnoreCase(name)) {
                return obj;
            }
        }
        return null;
    }


    // ** Base to Polymorphism **  Each room can customize what happens when you enter
    public abstract void enter(Player player);
}
