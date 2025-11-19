public class RoomOne extends Room {

    // This displays using an abstract class and polymorphism using the "Enter" Method
    public RoomOne() {
        // Create your room here
        super("RoomOne", "Love how this room looks");

        // Create you Items here
        addItem(new Item("ItemOne", "Create your Item", true));

    }

    @Override
    public void enter(Player player) {
        System.out.println(getDescription());
    }
}
