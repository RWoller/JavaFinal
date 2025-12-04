import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * Handles database connection and logging for the adventure game.
 */
public class GameDatabase {
    private static final String URL = "jdbc:sqlite:C:/sqlite/game.db"; // adjust path if needed

    public static void init() {
        try (Connection conn = DriverManager.getConnection(URL)) {
            String sql = """
                CREATE TABLE IF NOT EXISTS logs (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    timestamp TEXT NOT NULL,
                    playerName TEXT,
                    action TEXT NOT NULL
                )
            """;
            conn.createStatement().execute(sql);
        } catch (SQLException e) {
            System.out.println("Error initializing database: " + e.getMessage());
        }
    }

    public static void log(String playerName, String action) {
        try (Connection conn = DriverManager.getConnection(URL)) {
            String sql = "INSERT INTO logs(timestamp, playerName, action) VALUES(?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, LocalDateTime.now().toString());
                ps.setString(2, playerName);
                ps.setString(3, action);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error logging action: " + e.getMessage());
        }
    }
}

