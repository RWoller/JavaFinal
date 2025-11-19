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

    // Moves the player in a given direction (north, south, east, west, etc.)
    public void move(String direction) {
        if (direction == null || direction.isBlank()) {
            System.out.println("Go where?");
            return;
        }

        direction = direction.toLowerCase().trim();

        Room nextRoom = currentRoom.getExit(direction);
        if (nextRoom == null) {
            System.out.println("You can't go that way.");
        } else {
            currentRoom = nextRoom;
            // When entering a new room, call its enter() method so it shows the description
            currentRoom.enter(this);
        }
    }


    public void takeItem(Item item) {
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
            for (Item item : inventory) {
                System.out.println(" - " + item.getName());
            }
        }
    }

    // To check if player has an item by name
    public boolean hasItem(String name) {
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    // To remove item from inventory by name
    public boolean removeItemByName(String name) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getName().equalsIgnoreCase(name)) {
                inventory.remove(i);
                return true;
            }
        }
        return false;
    }
}
