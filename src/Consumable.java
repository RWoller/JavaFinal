public class Consumable extends Item {
    /**
     * Generic interactable Item class. Is consumed upon use.
     *
     * @param name        the object's name as it will appear in game dialog
     * @param description a detailed description of the object
     * @param examine     how the object appears when you take a closer look
     * @param use         what happens when you use the object, defaults to nothing if null
     * @param visible     if the object can be seen without special interaction
     * @param takable     if the object can be put into the player's inventory
     */
    public Consumable(String name, String description, String examine, String use, boolean visible, boolean takable) {
        super(name, description, examine, use, visible, takable);
    }

    @Override
    public boolean use(){
        System.out.println(use);
        return true;
    }
}
