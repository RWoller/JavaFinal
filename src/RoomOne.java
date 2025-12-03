public class RoomOne extends Room {

    // This displays using an abstract class and polymorphism using the "Enter" Method
    public RoomOne() {
        // Create your room here
        super("RoomOne", "Love how this room looks");

        // Using the ** Generic Class **  Create a Generic Container holding an Item
        Item note = new Item("Note", "A note says 'Exit is West'.", true);
        Container<Item> box = new Container<Item>("Small Box", "A heavy wooden box", note);

        // Items in this room is the box itself
        addItem(box.getContents()); // Add the box object to the room
    }

    @Override
    public void enter(Player player) {
        System.out.println(getDescription());
    }
}
