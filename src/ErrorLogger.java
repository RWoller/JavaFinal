import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

// *** FileWriter ****
public class ErrorLogger {
    private static final String LOG_FILE = "error_log.txt";

    // Method to log an error message to file
    public static void logError(String message) {
        try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
            String timestamp = LocalDateTime.now().toString();
            writer.write("[" + timestamp + "] ERROR: " + message + "\n");
        } catch (IOException e) {
            System.err.println("Could not write to error log file: " + e.getMessage());
        }
    }
}