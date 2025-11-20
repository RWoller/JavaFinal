public class RoomThree extends Room {

    // This displays using an abstract class and polymorphism using the "Enter" Method
    public RoomThree() {
        // Create your room here
        super("RoomThree", "You see a heavy door that looks like the way out.");

        // Create your Items here
        addItem(new Item("door", "A large heavy door with a narrow keyhole. This might be the way out.", false));
        addItem(new Item("alarm keypad", "To activate/deactivate the alarm system", false));
    }

    @Override
    public void enter(Player player) {
        System.out.println(getDescription());
    }
}
