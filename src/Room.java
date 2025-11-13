import java.util.*;

public class Room {
    private String name;
    private String description;
    private Map<String, Room> exits = new HashMap<>();
    private List<Item> items = new ArrayList<>();
    private List<String> npcs = new ArrayList<>();

    public Room(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name; // ✅ Added to support saving/loading by room name
    }

    public String getDescription() {
        return description;
    }

    public void addExit(String direction, Room destination) {
        exits.put(direction.toLowerCase(), destination);
    }

    public Room getExit(String direction) {
        return exits.get(direction.toLowerCase());
    }

    public Set<String> getExitNames() {
        return exits.keySet();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public Item getItem(String itemName) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public List<Item> getItems() {
        return items;
    }

    public void addNPC(String npc) {
        npcs.add(npc.toLowerCase());
    }

    public boolean hasNPC(String name) {
        return npcs.contains(name.toLowerCase());
    }

    public List<String> getNPCs() {
        return npcs;
    }
}
