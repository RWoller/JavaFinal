import java.util.ArrayList;
import java.util.List;

public class Player extends NPC {
    private Room currentRoom;

    public Player(String name, Room startingRoom) {
        super(name);
        this.currentRoom = startingRoom;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void move(String direction) {
        Room nextRoom = currentRoom.getExit(direction);
        if (nextRoom != null) {
            currentRoom = nextRoom;
            System.out.println("You move " + direction + ".");
        } else {
            System.out.println("You can’t go that way.");
        }
    }

    public void takeItem(RoomObject item) {
        if (item != null) {
            inventory.add(item);
            System.out.println("You picked up the " + item.getName() + ".");
        } else {
            System.out.println("There’s no item here.");
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
}
