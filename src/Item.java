public class Item extends RoomObject implements Comparable<Item> {

    private boolean canTake;

    public Item(String name, String description, boolean visible) {
        super(name, description, visible);
        this.canTake = visible;
    }

    public boolean isCanTake() { return canTake; }

    // Required method from Comparable class
    @Override
    public int compareTo(Item o) {
        return this.name.compareTo(o.name);
    }

    // Override
    @Override
    public String toString() {
        return this.name + ": " + this.description;
    }
}
