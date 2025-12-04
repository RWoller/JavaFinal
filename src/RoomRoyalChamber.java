public class RoomRoyalChamber extends Room {
    public RoomRoyalChamber() {
        super("RoomRoyalChamber", "Royal Chamber", "It looks like it belongs to an overlord with a throne in the center with a special artifact near it.");
        addItem(new Item("Heir Sealed Rapier", "A classy heirloom sword with detailed engravings on it."));
    }

    @Override
    public void enter(Player player) {
        System.out.println("You discover the Secret Chamber. " + getDescription());
    }

    @Override
    public String getHint() {
        return "The rapier might be part of a bigger puzzle with mysteries waiting to be uncovered.";
    }
}
