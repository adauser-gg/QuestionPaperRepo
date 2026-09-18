import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

/**
 * AsyncLogger
 * Multithreading: Implements Runnable for a background logging task.
 * Java I/O Streams: Uses FileWriter to append to a system log file.
 */
public class AsyncLogger implements Runnable {
    
    private final String logMessage;
    private static final String LOG_FILE = "data/system.log";

    public AsyncLogger(String logMessage) {
        // Object-Oriented Programming (Encapsulation): Private field set via constructor
        this.logMessage = logMessage;
    }

    // Multithreading: overriding the run method of Runnable interface
    @Override
    public void run() {
        // Exception Handling: try-catch block for file I/O operations
        FileWriter fileWriter = null;
        PrintWriter printWriter = null;
        try {
            // Java I/O Streams: FileWriter used in append mode (true)
            fileWriter = new FileWriter(LOG_FILE, true);
            printWriter = new PrintWriter(fileWriter);
            
            String timestamp = LocalDateTime.now().toString();
            printWriter.println("[" + timestamp + "] " + logMessage);
            
        } catch (IOException e) {
            System.err.println("Failed to write to log file: " + e.getMessage());
        } finally {
            // Exception Handling: finally block to ensure resources are closed
            try {
                if (printWriter != null) {
                    printWriter.close();
                }
                if (fileWriter != null) {
                    fileWriter.close();
                }
            } catch (IOException ex) {
                System.err.println("Failed to close FileWriter: " + ex.getMessage());
            }
        }
    }
    
    /**
     * Helper method to easily log messages asynchronously.
     * @param message the message to log
     */
    public static void log(String message) {
        Thread loggerThread = new Thread(new AsyncLogger(message));
        loggerThread.start();
    }
}
