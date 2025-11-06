public class CommandParser {
    public static ParsedCommand parse(String input) {
        if (input == null || input.isBlank()) {
            return new ParsedCommand(null, null);
        }

        // split into VERB + optional NOUN
        String[] parts = input.trim().split("\\s+", 2);
        CommandType type = CommandType.fromString(parts[0]);

        String noun = (parts.length > 1) ? parts[1].trim() : null;
        return new ParsedCommand(type, noun);
    }

    // nested static helper class to hold parsed command data
    public static class ParsedCommand {
        private final CommandType type;
        private final String noun;

        public ParsedCommand(CommandType type, String noun) {
            this.type = type;
            this.noun = noun;
        }

        public CommandType getType() {
            return type;
        }

        public String getNoun() {
            return noun;
        }
    }
}
