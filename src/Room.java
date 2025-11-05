import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Room {
    private String name;
    private String description;
    private Map<String, Room> exits;
    private List<Item> items;

    public Room(String name, String description) {
        this.name = name;
        this.description = description;
        this.exits = new HashMap<>();
        this.items = new ArrayList<>();
    }

    public void addExit(String direction, Room room) {
        exits.put(direction, room);
    }

    public Room getExit(String direction) {
        return exits.get(direction);
    }

    public String getDescription() {
        StringBuilder sb = new StringBuilder(description);
        if (!items.isEmpty()) {
            sb.append("\nYou see: ");
            for (Item item : items) {
                sb.append(item.getName()).append(" ");
            }
        }
        if (!exits.isEmpty()) {
            sb.append("\nExits: ").append(String.join(", ", exits.keySet()));
        }
        return sb.toString();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public Item removeItem(String itemName) {
        for (Item i : items) {
            if (i.getName().equalsIgnoreCase(itemName)) {
                items.remove(i);
                return i;
            }
        }
        return null;
    }
}
