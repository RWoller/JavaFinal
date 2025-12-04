/**
 * Convenience wrapper — forwards logs to GameDatabase.log.
 * Kept for compatibility with older code.
 */
public class GameLogger {
    public static void log(String playerName, String action) {
        GameDatabase.log(playerName, action);
    }
}
