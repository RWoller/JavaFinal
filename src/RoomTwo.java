public class RoomTwo extends Room {

    // This displays using an abstract class and polymorphism using the "Enter" Method
    public RoomTwo() {
        // Create your room here
        super("RoomOne", "Create your room");

        // Create you Items here
        addItem(new Item("ItemOne", "Create your Item"));

    }

    @Override
    public void enter(Player player) {
        System.out.println(getDescription());
    }
}