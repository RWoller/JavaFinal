import java.io.*; // Correct import, removed invalid "import.io.*"
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Savepoint {
    private static final String SAVE_FILE = "savegame.txt";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Save the current game state to a text file
    public static boolean save(SaveData data) {

        String now = LocalDateTime.now().format(FORMATTER);
        data.savedAt = now;

        try (Writer w = new OutputStreamWriter(new FileOutputStream(SAVE_FILE), StandardCharsets.UTF_8)) {
            w.write("playerName=" + data.playerName + "\n");
            w.write("health=" + data.health + "\n");
            w.write("level=" + data.level + "\n");
            w.write("currentRoom=" + data.currentRoom + "\n"); // use room name, not description
            w.write("savedAt=" + data.savedAt + "\n"); // To add the timestamp
            return true;
        } catch (IOException e) {
            System.out.println("Error saving: " + e.getMessage());
            return false;
        }
    }

    // Load the saved game state from the text file
    public static SaveData load() {
        File f = new File(SAVE_FILE);
        if (!f.exists()) return null;

        SaveData sd = new SaveData();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) { // fixed "While" typo
                int idx = line.indexOf('=');
                if (idx <= 0) continue;
                String key = line.substring(0, idx).trim();
                String value = line.substring(idx + 1).trim();

                switch (key) {
                    case "playerName" -> sd.playerName = value;
                    case "health" -> sd.health = parseIntSafe(value, 100);
                    case "level" -> sd.level = parseIntSafe(value, 1);
                    case "currentRoom" -> sd.currentRoom = value;
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading: " + e.getMessage());
            return null;
        }

        return sd.playerName == null ? null : sd;
    }

    // Helper to safely parse integers
    private static int parseIntSafe(String s, int fallback) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return fallback;
        }
    }
}
