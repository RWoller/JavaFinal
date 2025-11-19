public class RoomKitchen extends Room{

    public RoomKitchen() {
        super("Kitchen", "You see a deep sink and dusty cupboards. There's a greasy old cookbook on the counter.");
        addItem(new Item("Cookbook", "Greased up cookbook with a bunch of notes written on it.", true));
        addItem(new Item("Soap", "Cuts through grease and grime.", true));
        addItem(new Item("Sink", "Old but still has running water.", false));
        addItem(new Item("Rag", "Old but still absorbent.", true));
    }

    // ** Polymorphism ** What happens when the player enters the room
    @Override
    public void enter(Player player) {
        System.out.println(getDescription());
    }
}
