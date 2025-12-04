import java.sql.*;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * Database-only save & logging system using SQLite (file: game.db).
 * Creates two tables: logs and save_data.
 */
public class GameDatabase {
    private static final String URL = "jdbc:sqlite:game.db";

    public static void init() {
        try (Connection conn = DriverManager.getConnection(URL)) {
            String logSql = """
                CREATE TABLE IF NOT EXISTS logs (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    timestamp TEXT NOT NULL,
                    playerName TEXT,
                    action TEXT NOT NULL
                )
                """;
            conn.createStatement().execute(logSql);

            String saveSql = """
                CREATE TABLE IF NOT EXISTS save_data (
                    playerName TEXT PRIMARY KEY,
                    currentRoom TEXT,
                    inventory TEXT
                )
                """;
            conn.createStatement().execute(saveSql);
        } catch (SQLException e) {
            System.out.println("Error initializing database: " + e.getMessage());
        }
    }

    public static void log(String playerName, String action) {
        String sql = "INSERT INTO logs(timestamp, playerName, action) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, LocalDateTime.now().toString());
            ps.setString(2, playerName);
            ps.setString(3, action);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error logging action: " + e.getMessage());
        }
    }

    public static void saveGame(Player player) {
        String inventory = player.getInventory().stream()
                .map(Item::getName)
                .collect(Collectors.joining(","));
        String sql = "REPLACE INTO save_data(playerName, currentRoom, inventory) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, player.getName());
            ps.setString(2, player.getCurrentRoomKey()); // consistent key for rooms
            ps.setString(3, inventory);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error when trying to save your game: " + e.getMessage());
        }
    }

    public static SaveData loadGame(String playerName) {
        String sql = "SELECT currentRoom, inventory FROM save_data WHERE playerName = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, playerName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String room = rs.getString("currentRoom");
                    String inventory = rs.getString("inventory");
                    return new SaveData(playerName, room, inventory == null ? "" : inventory);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error loading game: " + e.getMessage());
        }
        return null;
    }
}
