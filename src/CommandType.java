public enum CommandType {
    GO,
    TAKE,
    INVENTORY,
    LOOK_AROUND,
    EXAMINE,
    USE,
    EXIT,
    HELP;   // added HELP so we can support the help command

    // Converts the first word of the input into a command type
    public static CommandType fromString(String verb) {
        if (verb == null) {
            return null;
        }

        verb = verb.toLowerCase().trim();

        switch (verb) {
            case "go":
            case "move":
                return GO;

            case "take":
            case "get":
            case "grab":
                return TAKE;

            case "inventory":
            case "inv":
            case "i":
                return INVENTORY;

            // "look" and "look around" are both treated as LOOK_AROUND
            case "look":
                return LOOK_AROUND;

            case "examine":
            case "inspect":
                return EXAMINE;

            case "use":
                return USE;

            case "exit":
            case "quit":
                return EXIT;

            case "help":
                return HELP;

            default:
                return null;
        }
    }
}
