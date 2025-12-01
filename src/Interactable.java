public interface Interactable {
    // Player looks at the object
    void examine();

    // Player uses the object
    default boolean use(){
        return false;
    }

    // Player attempts to pick it up
    default boolean take(){
        return false;
    }
}
