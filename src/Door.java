public class Door extends RoomObject implements Interactable {
    private boolean locked;
    private String keyId;

    public Door(String name, String description, String keyId, boolean visible, boolean locked) {
        super(name, description, visible);
        this.locked = locked;
        this.keyId = keyId;
    }

    @Override
    public void examine() {
        System.out.println("You see a " + name + ". It’s " + (locked ? "locked." : "unlocked."));
    }

    @Override
    public void use() {
        if (locked) {
            System.out.println("The " + name + " won't budge. Maybe there’s a key?");
        } else {
            System.out.println("You open the " + name + " and step through.");
        }
    }

    @Override
    public void take() {
        System.out.println("Nice try! The " + name + " remains stuck to the wall");
    }

    @Override
    public void interact(RoomObject roomObject) {

    }
}
