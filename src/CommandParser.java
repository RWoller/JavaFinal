public class CommandParser {
    public static ParsedCommand parse(String input) {
        if (input == null || input.isBlank()) {
            return new ParsedCommand(null, null);
        }

        // split into VERB + optional NOUN
        String[] parts = input.trim().split("\\s+");

        // VERB
        CommandType type = CommandType.fromString(parts[0]);

        // NOUNS
        String noun1 = (parts.length > 1) ? parts[1] : null;
        String noun2 = (parts.length > 2) ? parts[2] : null;

        if (noun2 == null || noun2.isBlank()) {
            return new ParsedCommand(type, noun1);
        } else {
            return new ParsedCommand(type, noun1, noun2);
        }
    }

    // nested static helper class to hold parsed command data
    public static class ParsedCommand {
        private final CommandType type;
        private final String noun;
        private final String noun2;

        public ParsedCommand(CommandType type, String noun) {
            this.type = type;
            this.noun = noun;
            this.noun2 = null;
        }

        public ParsedCommand(CommandType type, String noun, String noun2) {
            this.type = type;
            this.noun = noun;
            this.noun2 = noun2;
        }

        public CommandType getType() {
            return type;
        }

        public String getNoun1() {
            return noun;
        }
        public String getNoun2() { return noun2;}
    }
}
