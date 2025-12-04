import java.util.ArrayList;
import java.util.List;

public class Shelf extends RoomObject implements Interactable {
    private List<RoomObject> contents = new ArrayList<>();
    private String examine;
    public Shelf(String name, String description, String examine, boolean visible) {
        super(name, description, visible);
        this.examine = examine;
    }


    public void addItem(RoomObject item) {
        contents.add(item);
    }


    @Override
    public void examine() {
        System.out.println(examine);
        System.out.print("On the " + name + " you see: ");
        contents.forEach(i -> {
            System.out.print(i.getName() + " ");
            i.setVisible(true);
        });
    }

    @Override
    public boolean use() {
        System.out.println("The " + name + " is unimpressed.");
        return false;
    }

    @Override
    public boolean take() {
        System.out.println("You can't take the " + name + ".");
    }
}
