public class Item extends RoomObject implements Interactable{
    protected final String examine;
    protected final String use;

    /**
     * Generic interactable Item class.
     * @param name the object's name as it will appear in game dialog
     * @param description a detailed description of the object
     * @param examine how the object appears when you take a closer look
     * @param use what happens when you use the object, defaults to nothing if null
     * @param visible if the object can be seen without special interaction
     * @param takable if the object can be put into the player's inventory
     */
    public Item(String name, String description, String examine, String use, boolean visible, boolean takable) {
        super(name, description, visible);
        this.examine = examine;
        this.use = use;
        this.visible = visible;
        this.takable = takable;
    }

    /**
     *
     */
    @Override
    public void examine() {
        System.out.println(examine);
    }

    /**
     *
     */
    @Override
    public boolean use() {
        if (use != null) {
            System.out.println(use);
        } else {
            System.out.println(name + " does nothing.");
        }
        return false;
    }

    /**
     * @return
     */
    @Override
    public boolean take() {
        if (takable) {
            System.out.println("You put the " + name + " into your inventory.");
            return true;
        } else {
            System.out.println("You have no need for that.");
            return false;
        }
    }
}
