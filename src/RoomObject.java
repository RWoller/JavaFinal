// Abstract Class

public abstract class RoomObject {

    protected String name;
    protected String description;
    protected boolean visible;

    // Constructor
    public RoomObject(String name, String description, boolean visible) {
        this.name = name;
        this.description = description;
        this.visible = visible;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    // Whether the object should show up when we print room items
    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
