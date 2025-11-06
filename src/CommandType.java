// Enumerated list of some simple commands along with a description
public enum CommandType {
    GO(true, "Move in a direction (e.g. 'go north')"),
    USE(true, "Use an item (e.g. 'use key')"),
    INVENTORY(false, "Check your inventory"),
    TAKE(true, "Take an item (e.g. 'take sword')"),
    TALK(true, "Talk to someone (e.g. 'talk guard')"),
    EXAMINE(true, "Examine an object (e.g. 'examine statue')"),
    ATTACK(true, "Attack an enemy (e.g. 'attack goblin')"),
    LOOK_AROUND(false, "Look around the current room"),
    EXIT(false, "Quit the game");

    private final boolean requiresNoun;
    private final String description;

    CommandType(boolean requiresNoun, String description) {
        this.requiresNoun = requiresNoun;
        this.description = description;
    }

    public boolean requiresNoun() {
        return requiresNoun;
    }

    public String getDescription() {
        return description;
    }

    public static CommandType fromString(String input) {
        String normalized = input.trim().replace(" ", "_").toUpperCase();
        try {
            return CommandType.valueOf(normalized);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}

