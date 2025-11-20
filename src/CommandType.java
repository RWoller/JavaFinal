public enum CommandType {
    GO,
    TAKE,
    INVENTORY,
    LOOK_AROUND,
    EXAMINE,
    USE,
    EXIT,
    HELP,   // added HELP so we can support the help command
    SAVE,
    LOAD;

    // Converts the first word of the input into a command type
    public static CommandType fromString(String verb) {
        if (verb == null) {
            return null;
        }

        verb = verb.toLowerCase().trim();

        return switch (verb) {
            case "go", "move" -> GO;
            case "take", "get", "grab" -> TAKE;
            case "inventory", "inv", "i" -> INVENTORY;

            // "look" and "look around" are both treated as LOOK_AROUND
            case "look" -> LOOK_AROUND;
            case "examine", "inspect" -> EXAMINE;
            case "use" -> USE;
            case "exit", "quit" -> EXIT;
            case "help" -> HELP;
            case "save" -> SAVE;
            default -> null;
        };
    }
}
