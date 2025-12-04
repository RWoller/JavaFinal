import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;

public class GameLogger {
    private static final String URL = "jdbc:sqlite:C:/sqlite/game.db"; // full path to Connect to the Database file.

    public static void log(String playerName, String action) {
        try (Connection conn = DriverManager.getConnection(URL)){
            String sql = "INSERT INTO log(timestamp, playerName, action) VALUES(?,?,?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, LocalDateTime.now().toString());
                ps.setString(2, playerName);
                ps.setString(3, action);
                ps.executeUpdate();
                System.out.println("Logged: " + action);
            }
        }
        catch (Exception e) {
            System.out.println("Logging Action Error: " + e.getMessage());
        }
    }

}
