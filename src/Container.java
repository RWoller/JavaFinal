// ** Generic Class **
public class Container<T> extends RoomObject {
    private String name;
    // The contents field will take the specific type defined when the object is created
    private T contents;

    public Container(String name, String description, T contents) {
        super(name, description, true);

        this.contents = contents;
    }

    // Getter
    public T getContents() {
        return contents;
    }

    public String getName() {
        return name;
    }
}