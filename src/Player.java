import java.util.ArrayList;
import java.util.List;

/**
 * Player extends NPC to reuse inventory/health, and tracks current room and a room key.
 */
public class Player extends NPC {
    private Room currentRoom;
    private String currentRoomKey; // used for saving/loading

    public Player(String name, Room startingRoom) {
        super(name);
        this.currentRoom = startingRoom;
        this.currentRoomKey = startingRoom == null ? "RoomKitchen" : startingRoom.getKey();
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public String getCurrentRoomKey() {
        return currentRoomKey;
    }

    public void moveTo(Room room) {
        if (room != null) {
            this.currentRoom = room;
            this.currentRoomKey = room.getKey();
            System.out.println("You move to " + room.getName() + ".");
        }
    }

    public void takeItem(Item item) {
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
            for (Item item : inventory) System.out.println(" - " + item.getName());
        }
    }

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
