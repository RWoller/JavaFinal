import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.*;
import java.util.stream.Collectors;

public class Room {
    private String name;
    private String description;
    private Map<String, Room> exits = new HashMap<>(); // e.g. "north" -> another Room
    private List<RoomObject> objects = new ArrayList<>();
    private List<NPC> npcs = new ArrayList<>();

    public Room(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void addExit(String direction, Room destination) {
        exits.put(direction.toLowerCase(), destination);
    }

    public void addItem(RoomObject object) {
        objects.add(object);
    }

    public void addNPC(NPC npc) {
        npcs.add(npc);
    }

    public Room getExit(String direction) {
        return exits.get(direction.toLowerCase());
    }

    public RoomObject getItem(String itemName) {
        for (RoomObject object : objects) {
            if (object.getName().equalsIgnoreCase(itemName)) {
                return object;
            }
        }
        return null;
    }

    public List<RoomObject> getVisibleItems() {
        return objects.stream().filter(RoomObject::isVisible).toList();
    }

    public boolean hasNPC(String name) {
        return npcs.contains(name.toLowerCase());
    }

    public void removeItem(RoomObject object) {
        objects.remove(object);
    }

    public String getDescription() {
        return description;
    }

    public Set<String> getExitNames() {
        return exits.keySet();
    }

    public List<RoomObject> getObjects() {
        return objects;
    }

    public List<NPC> getNPCs() {
        return npcs;
    }

    public List<String> getAllNounTargets() {
        List<String> all = new ArrayList<>();

        for (RoomObject object : objects) { all.add(object.getName().toLowerCase()); }
        for (NPC npc : npcs) { all.add(npc.getName().toLowerCase()); }

        return all;
    }

    public class Exit {
        private String direction;
        private Door door;
        private Room leadsTo;

        public Exit(String direction, Door door, Room leadsTo) {
            this.direction = direction;
            this.door = door;
            this.leadsTo = leadsTo;
        }

        public String getDirection() {
            return direction;
        }

        public Door getDoor() {
            return door;
        }

        public Room getDestination() {
            return leadsTo;
        }
    }
}

