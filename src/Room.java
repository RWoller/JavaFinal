import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.*;
import java.util.stream.Collectors;

public class Room {
    private String name;
    private String description;
    private String onEnter;
    private List<Exit> exits = new ArrayList<>(); // e.g. "north" -> another Room
    private List<RoomObject> objects = new ArrayList<>();
    private List<NPC> npcs = new ArrayList<>();

    public Room(String name, String description, String onEnter) {
        this.name = name;
        this.description = description;
        this.onEnter = onEnter;
    }

    public void addExit(String direction, Door door, Room destination) {
        Exit exit = new Exit(direction, door, destination);
        exits.add(exit);
    }

    public void addItem(RoomObject object) {
        objects.add(object);
    }

    public void addNPC(NPC npc) {
        npcs.add(npc);
    }

    public Room getExit(String direction) {
        for (Exit exit : exits) {
            if (exit.getDirection().equals(direction)) {
                return exit.leadsTo;
            }
        }
        return null;
    }

    public String getExitDirection(Door door) {
        for (Exit exit : exits) {
            if (exit.getDoor() == door) {
                return exit.getDirection();
            }
        }
        return null;
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


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getOnEnter() {
        return onEnter;
    }

    public Set<String> getExitNames() {
        return exits.stream().map(Exit::getDirection).collect(Collectors.toSet());
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

