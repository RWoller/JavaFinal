import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Quick test to verify DB connectivity.
 */
public class DBTest {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:game.db"; // relative path in project folder

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                System.out.println("Connected to SQLite database!");
            } else {
                System.out.println("Connection returned null.");
            }
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }
}

