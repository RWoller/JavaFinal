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
    public boolean use() {
        if (locked) {
            System.out.println("The " + name + " won't budge. Maybe there’s a key?");
        } else {
            System.out.println("The door is already open.");
        }
        return false;
    }

    @Override
    public boolean take() {
        System.out.println("Nice try! The " + name + " remains stuck to the wall");
        return false;
    }

    public String getKeyId() {
        return keyId;
    }

    public void unlock() {
        locked = false;
    }

    public void lock() {
        locked = true;
    }
}
