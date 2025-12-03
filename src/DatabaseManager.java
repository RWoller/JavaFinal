import org.apache.derby.jdbc.EmbeddedDataSource;

import java.sql.*;
import java.time.LocalDateTime;

public class DatabaseManager {

    // Configure
    private static final String DATABASE_NAME = "GameDB";
    private static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    private static final String SCORE_TABLE = "GAME_RECORDS";

    private final EmbeddedDataSource dataSource;

    public DatabaseManager() {
        this.dataSource = new EmbeddedDataSource();
        this.dataSource.setDatabaseName(DATABASE_NAME);
        // Create table if it doesnt exist
        this.dataSource.setCreateDatabase("create");

        initializeDatabaseSchema();
    }

    private void initializeDatabaseSchema() {
        String createSQL =
                // ** Create table ***
                "CREATE TABLE " + SCORE_TABLE + " (" +
                        "PLAYER_NAME VARCHAR(50) NOT NULL," +
                        "DATE_TIME TIMESTAMP NOT NULL" + // Only storing player and time (completion record)
                        ")";

        // **** Proper open and close of database ****
        try (Connection conn = dataSource.getConnection()) {
            dropTableIfExists(conn);

            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(createSQL);

                // Debugging
                // System.out.println("Game records table initialized.");
            }
        } catch (SQLException e) {
            // Check if the error is due to the table already existing
            if (!"X0Y32".equals(e.getSQLState())) {
                System.err.println("Database Initialization Error: " + e.getMessage());
            }
        }
    }

    private void dropTableIfExists(Connection conn) {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DROP TABLE " + SCORE_TABLE);
        } catch (SQLException e) {
            if (!"42Y55".equals(e.getSQLState())) {
                System.err.println("Error trying to drop table: " + e.getMessage());
            }
        }
    }

    // *** Create Insert ***
    public void recordEscape(String name) {
        String insertSQL = "INSERT INTO " + SCORE_TABLE + " (PLAYER_NAME, DATE_TIME) VALUES (?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(insertSQL)) {

            // Set auto-commit to true so the insert is saved immediately
            conn.setAutoCommit(true);

            // Set player name and time
            ps.setString(1, name);
            ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));

            ps.executeUpdate();
            System.out.println("-Escape successfully recorded in the database!");

        } catch (SQLException e) {
            System.err.println("Error recording escape: " + e.getMessage());
        }
    }

    // *** Read ***
    public void displayEscapeRecords() {
        String selectSQL =
                "SELECT PLAYER_NAME, DATE_TIME FROM " +
                        SCORE_TABLE +
                        " ORDER BY DATE_TIME DESC";

        System.out.println("\n===== PREVIOUS ESCAPE RECORDS =====");

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {

            int rank = 1;
            while (rs.next()) {
                // Read the player name and timestamp
                String name = rs.getString("PLAYER_NAME");
                String timestamp = rs.getString("DATE_TIME");

                System.out.printf("%d. %s escaped on %s\n", rank++, name, timestamp);
            }
            if (rank == 1) {
                System.out.println("No escapes recorded yet.");
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving records: " + e.getMessage());
        }
    }

    // *** Delete ***
    public void deleteOldestRecord() {
        // Finds the oldest record and deletes it
        String deleteSQL = "DELETE FROM " + SCORE_TABLE + " WHERE DATE_TIME = (SELECT MIN(DATE_TIME) FROM " + SCORE_TABLE + ")";

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {

            int rowsAffected = stmt.executeUpdate(deleteSQL);
            if (rowsAffected > 0) {
                System.out.println("Escape record deleted.");
            }

        } catch (SQLException e) {
            System.err.println("Error deleting oldest record: " + e.getMessage());
        }
    }
}