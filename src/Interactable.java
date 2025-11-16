public interface Interactable {
    // Player looks at the object
    void examine();

    // Player uses the object
    void use();

    // Player attempts to pick it up
    void take();

    // Player interacts using another object
    void interact(RoomObject roomObject);
}
