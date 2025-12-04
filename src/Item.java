/**
 * Immutable item model.
 */
public class Item {
    private final String name;
    private final String description;

    public Item(String name, String description) {
        this.name = name == null ? "Unnamed" : name;
        this.description = description == null ? "" : description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
