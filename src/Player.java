import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private Room currentRoom;
    private List<Item> inventory;

    public Player(String name, Room startingRoom) {
        this.name = name;
        this.currentRoom = startingRoom;
        this.inventory = new ArrayList<>();
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

    public void takeItem(String itemName) {
        Item item = currentRoom.removeItem(itemName);
        if (item != null) {
            inventory.add(item);
            System.out.println("You picked up the " + itemName + ".");
        } else {
            System.out.println("There’s no " + itemName + " here.");
        }
    }

    public void showInventory() {
        if (inventory.isEmpty()) {
            System.out.println("You have nothing.");
        } else {
            System.out.println("You are carrying:");
            for (Item item : inventory) {
                System.out.println(" - " + item.getName());
            }
        }
    }
}
