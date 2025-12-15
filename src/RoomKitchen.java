public class RoomKitchen extends Room {
    public RoomKitchen() {
        super("RoomKitchen", "Kitchen", "A neglected kitchen. There is a locked cupboard and a smell of old soup.");
        addItem(new Item("Gunky Key", "An old brass key, slightly tarnished."));
        addItem(new Item("Soap", "Cuts through grease and grime."));
        addItem(new Item("Rag", "Old but still absorbent."));
    }

    @Override
    public void enter(Player player) {
        System.out.println("You enter the Kitchen. " + getDescription());
    }

    @Override
    public String getHint() {
        return "Maybe a key will open the locked cupboard.";
    }
}
