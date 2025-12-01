import java.util.ArrayList;
import java.util.List;

public class Chest extends RoomObject implements Interactable {
    private boolean open;
    private boolean locked;
    String chestKey;
    private List<RoomObject> contents = new ArrayList<>();

    public boolean isOpen(){
        return open;
    }

    public boolean isLocked(){
        return locked;
    }

    public Chest(String name, String description, boolean visible, String keyId) {
        super(name, description, visible);
        chestKey = keyId;

        // if a key was provided, lock it with that key, otherwise unlock
        locked = chestKey != null;
    }

    public void addItem(RoomObject item) {
        contents.add(item);
    }

    @Override
    public void examine() {
        if (!open) {
            System.out.println("An old wooden chest with a rusty latch.");
        } else if (contents.isEmpty()) {
            System.out.println("The chest is open and empty.");
        } else {
            System.out.print("Inside the chest you see: ");
            contents.forEach(i -> {
                System.out.print(i.getName() + " ");
                i.setVisible(true);
            });
            System.out.println();
        }
    }

    @Override
    public boolean use() {
        if (!open && !locked) {
            open = true;
            System.out.println("You open the chest.");
            // Reveal its contents in the room
            for (RoomObject item : contents) {
                item.setVisible(true);
                System.out.println("You find a " + item.getName() + " inside.");
            }
        } else if (!open && locked) {
            System.out.println("The " + name + " is locked tight and doesn't budge.");
        } else {
            System.out.println("The chest is already open.");
        }
        return false;
    }

    @Override
    public boolean take() {
        System.out.println("Nice try, but you can't take that with you.");
        return false;
    }
}
