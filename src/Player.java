import java.util.ArrayList;
import java.util.List;

/**
 * Player extends NPC to reuse inventory/health, and tracks current room and a room key.
 */
public class Player extends NPC {
    private Room currentRoom;

    public Player(String name, Room startingRoom) {
        super(name);
        this.currentRoom = startingRoom;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    // Moves the player in a given direction (north, south, east, west, etc.)
    public void move(String direction) {
        direction = direction.toLowerCase().trim();
        Room nextRoom = currentRoom.getExit(direction);
        if (nextRoom != null) {
            currentRoom = nextRoom;
            System.out.println("You move " + direction + ".");
            System.out.println("You enter " + currentRoom.getName() + ".");
            System.out.println(currentRoom.getOnEnter());
        } else {
            System.out.println("You can’t go that way.");
        }
    }

    public void takeItem(RoomObject item) {
        if (item != null) {
            inventory.add(item);
            System.out.println("You picked up the " + item.getName() + ".");
        }
    }

    public void showInventory() {
        if (inventory.isEmpty()) {
            System.out.println("You have nothing.");
        } else {
            System.out.println("You are carrying:");
            for (RoomObject item : inventory) {
                System.out.println(" - " + item.getName());
            }
        }
    }

    // To check if player has an item by name
    public boolean hasItem(String name) {
        if (name == null) return false;
        for (Item it : inventory) {
            if (it.getName().equalsIgnoreCase(name)) return true;
        }
        return false;
    }

    public List<Item> getInventory() {
        return new ArrayList<>(inventory);
    }
}
