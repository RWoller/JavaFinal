// Simple parser: splits input into verb + optional noun (rest of the line)
public class CommandParser {
    public static ParsedCommand parse(String input) {
        if (input == null) return new ParsedCommand(CommandType.UNKNOWN, null);
        String trimmed = input.trim();
        if (trimmed.isEmpty()) return new ParsedCommand(CommandType.UNKNOWN, null);

        String[] parts = trimmed.split("\\s+", 2);
        CommandType type = CommandType.fromString(parts[0]);
        String noun = parts.length > 1 ? parts[1].trim() : null;

        return new ParsedCommand(type, noun);
    }

    public static class ParsedCommand {
        private final CommandType type;
        private final String noun;

        public ParsedCommand(CommandType type, String noun) {
            this.type = type == null ? CommandType.UNKNOWN : type;
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

