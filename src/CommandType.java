// Enumerated list of commands used by the parser and game
public enum CommandType {
    GO(Strings.get("go_name"),true, Strings.get("go_desc")),
    USE(Strings.get("use_name"),true, Strings.get("use_desc")),
    INVENTORY(Strings.get("inventory_name"),false, Strings.get("inventory_desc")),
    TAKE(Strings.get("take_name"),true, Strings.get("take_desc")),
    TALK(Strings.get("talk_name"),true, Strings.get("talk_desc")),
    EXAMINE(Strings.get("examine_name"),true, Strings.get("examine_desc")),
    LOOK(Strings.get("look_name"),false, Strings.get("look_desc")),
    SAVE(Strings.get("save_name"),false, Strings.get("save_desc")),
    LOAD(Strings.get("load_name"),false, Strings.get("load_desc")),
    HELP(Strings.get("help_name"),false, Strings.get("help_desc")),
    EXIT(Strings.get("exit_name"),false, Strings.get("exit_desc")),
    UNKNOWN(Strings.get("unknown_name"),false, Strings.get("unknown_desc")),;

    private final String name;
    private final boolean requiresNoun;
    private final String description;

    CommandType(String name, boolean requiresNoun, String description) {
        this.name = name;
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


