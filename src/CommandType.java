// Enumerated list of commands used by the parser and game
public enum CommandType {
    GO(true, "Move in a direction (e.g. 'go north')"),
    USE(true, "Use an item (e.g. 'use key')"),
    INVENTORY(false, "Check your inventory"),
    TAKE(true, "Take an item (e.g. 'take sword')"),
    TALK(true, "Talk to someone (e.g. 'talk guard')"),
    EXAMINE(true, "Examine an object (e.g. 'examine statue')"),
    ATTACK(true, "Attack an enemy (e.g. 'attack goblin')"),
    LOOK(false, "Look around the current room"),
    SAVE(false, "Save the game"),
    LOAD(false, "Load the game"),
    HELP(false, "Show help information"),
    EXIT(false, "Quit the game"),
    UNKNOWN(false, "Unknown command");

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
        if (input == null) return UNKNOWN;
        String normalized = input.trim().replace(" ", "_").toUpperCase();
        try {
            return CommandType.valueOf(normalized);
        } catch (IllegalArgumentException e) {
            // map some common synonyms
            switch (normalized) {
                case "L": case "LOOKAROUND": case "LOOK-AROUND": return LOOK;
                case "INV": case "INVENTORY": return INVENTORY;
                case "EXAM": case "EXAMINE": return EXAMINE;
                case "QUIT": case "EXIT": return EXIT;
                case "HELP": return HELP;
                case "SAVE": return SAVE;
                case "LOAD": return LOAD;
                case "GO": return GO;
                case "TAKE": case "GET": return TAKE;
                case "TALK": return TALK;
                case "USE": return USE;
                default: return UNKNOWN;
            }
        }
    }
}


