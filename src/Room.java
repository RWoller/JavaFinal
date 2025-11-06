import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.*;

public class Room {
    private String name;
    private String description;
    private Map<String, Room> exits = new HashMap<>(); // e.g. "north" -> another Room
    private List<Item> items = new ArrayList<>();
    private List<String> npcs = new ArrayList<>();

    public Room(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void addExit(String direction, Room destination) {
        exits.put(direction.toLowerCase(), destination);
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void addNPC(String npc) {
        npcs.add(npc.toLowerCase());
    }

    public Room getExit(String direction) {
        return exits.get(direction.toLowerCase());
    }

    public Item getItem(String itemName) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }

    public boolean hasNPC(String name) {
        return npcs.contains(name.toLowerCase());
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public String getDescription() {
        return description;
    }

    public Set<String> getExitNames() {
        return exits.keySet();
    }

    public List<Item> getItems() {
        return items;
    }

    public List<String> getNPCs() {
        return npcs;
    }
}

