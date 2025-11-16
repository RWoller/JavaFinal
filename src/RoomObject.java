/// Abstract Class for a RoomObject
public abstract class RoomObject {
    protected String name;
    protected String description;
    protected boolean visible;

    public RoomObject(String name, String description, boolean visible) {
        this.name = name;
        this.visible = visible;
        this.description = description;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public boolean isVisible() {
        return visible;
    }
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
