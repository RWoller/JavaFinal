// ** Generic Class **
public class Container<T> extends RoomObject {
    // The contents field will take the specific type defined when the object is created
    private T contents;

    public Container(String name, String description, T contents) {
        // Call the RoomObject constructor to set name/description/visible
        super(name, description, true);
        this.contents = contents;
    }

    // Getter for whatever is inside the container
    public T getContents() {
        return contents;
    }
}
