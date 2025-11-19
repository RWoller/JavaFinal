public class Item {

    // **Immutable** Once these are set with constructor, they don't change
    private final String name;
    private final String description;
    private final boolean canTake;

    public Item(String name, String description, boolean canTake) {
        this.name = name;
        this.description = description;
        this.canTake = canTake;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCanTake() { return canTake; }
}
