import java.util.*;

/**
 * Abstract room base class. Subclasses should provide key, name, description and entry behavior.
 */
public abstract class Room {
    private final String key; // unique identifier used in saves (e.g., "RoomKitchen")
    private final String name;
    private final String description;
    private final Map<String, Room> exits = new LinkedHashMap<>();
    private final List<Item> items = new ArrayList<>();
    private final List<NPC> npcs = new ArrayList<>();

    protected Room(String key, String name, String description) {
        this.key = key;
        this.name = name;
        this.description = description;
    }

    public String getKey() { return key; }

    public String getName() { return name; }

    public String getDescription() { return description; }

    public void addExit(String direction, Room room) {
        if (direction == null || room == null) return;
        exits.put(direction.toLowerCase(), room);
    }

    public Room getExit(String directionOrName) {
        if (directionOrName == null) return null;
        // try direct direction match first
        Room r = exits.get(directionOrName.toLowerCase());
        if (r != null) return r;
        // try to match by room name (case-insensitive)
        for (Room rr : exits.values()) {
            if (rr.getName().equalsIgnoreCase(directionOrName)) return rr;
        }
        return null;
    }

    public List<String> getExitNames() {
        List<String> list = new ArrayList<>();
        for (Map.Entry<String, Room> e : exits.entrySet()) {
            list.add(e.getKey() + " -> " + e.getValue().getName());
        }
        return list;
    }

    public void addItem(Item item) {
        if (item != null) items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }

    public Item getItem(String name) {
        if (name == null) return null;
        for (Item it : items) if (it.getName().equalsIgnoreCase(name)) return it;
        return null;
    }

    public void addNpc(NPC npc) {
        if (npc != null) npcs.add(npc);
    }

    public NPC getNpcByName(String name) {
        if (name == null) return null;
        for (NPC n : npcs) if (n.getName().equalsIgnoreCase(name)) return n;
        return null;
    }

    public List<NPC> getNpcs() {
        return Collections.unmodifiableList(npcs);
    }

    /**
     * Called when a player enters the room.
     */
    public abstract void enter(Player player);

    /**
     * Optional hint for help system to show room-specific help.
     */
    public String getHint() {
        return null;
    }
}
