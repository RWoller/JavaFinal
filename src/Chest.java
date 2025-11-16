import java.util.ArrayList;
import java.util.List;

public class Chest extends RoomObject implements Interactable {
    private boolean open;
    private List<RoomObject> contents = new ArrayList<>();

    public boolean isOpen(){
        return open;
    }

    public Chest(String name, String description, boolean visible) {
        super(name, description, visible);
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
            contents.forEach(i -> System.out.print(i.getName() + " "));
            System.out.println();
        }
    }

    @Override
    public void use() {
        if (!open) {
            open = true;
            System.out.println("You open the chest.");
            // Reveal its contents in the room
            for (RoomObject item : contents) {
                item.setVisible(true);
                System.out.println("You find a " + item.getName() + " inside.");
            }
        } else {
            System.out.println("The chest is already open.");
        }
    }

    @Override
    public void take() {

    }

    @Override
    public void interact(RoomObject roomObject) {

    }
}
