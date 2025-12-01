import java.util.List;

public class CommandParser {
    public static ParsedCommand parse(String input, List<String> nounTargets) {
        if (input == null || input.isBlank()) {
            return new ParsedCommand(null, null, null);
        }

        // split into VERB + optional NOUN
        String[] parts = input.trim().split("\\s+", 2);

        // VERB
        CommandType type = CommandType.fromString(parts[0].toLowerCase());

        // Uninterpreted noun string, may be null, may contain multiple words
        String rawNouns = (parts.length > 1) ? parts[1].trim() : null;

        // Parse noun input against list of valid targets returning up to two strings
        String[] resolvedNouns = resolveNouns(rawNouns, nounTargets);
        String noun1 = resolvedNouns[0];
        String noun2 = resolvedNouns[1];

        if (noun2 == null || noun2.isBlank()) {
            return new ParsedCommand(type, noun1);
        } else {
            return new ParsedCommand(type, noun1, noun2);
        }
    }

    // resolves noun output against list of valid targets
    private static String[] resolveNouns(String rawInput, List<String> nounTargets) {
        // if noun input is null return nulls
        if (rawInput == null) return new String[]{null, null};

        rawInput = rawInput.toLowerCase().trim();
        String noun1 = null, noun2 = null;

        // check against noun list for first noun and then remove from beginning of string
        for (String t : nounTargets) {
            if (rawInput.startsWith(t)) {
                noun1 = t;
                rawInput = rawInput.substring(t.length()).trim();
                break;
            }
        }

        // check noun list for second noun
        if (!rawInput.isEmpty()) {
            for (String t : nounTargets) {
                if (rawInput.startsWith(t)) {
                    noun2 = t;
                    break;
                }
            }
        }

        // return list of results
        return new String[]{noun1, noun2};
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
