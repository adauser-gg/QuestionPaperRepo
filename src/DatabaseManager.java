import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

// Exception Handling: Custom Exception
class DatabaseConnectionException extends Exception {
    public DatabaseConnectionException(String message) {
        // Object-Oriented Programming: Using super to call parent constructor
        super(message);
    }
}

/**
 * DatabaseManager
 * Database Connectivity: Handles JDBC SQLite connection and strictly implements CRUD operations.
 */
public class DatabaseManager {
    
    private static final String DB_URL = "jdbc:sqlite:data/database.db";

    /**
     * Initializes the database connection and creates the necessary table.
     */
    public static void initializeDatabase() throws DatabaseConnectionException {
        // Ensure data directory exists
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }

        // Exception Handling: try-catch-finally used implicitly with try-with-resources
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            
            if (conn != null) {
                // Database Connectivity: Execute SQL statements
                String sql = "CREATE TABLE IF NOT EXISTS question_papers (" +
                             "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                             "subject_code TEXT NOT NULL," +
                             "year INTEGER NOT NULL," +
                             "tags TEXT NOT NULL" +
                             ");";
                stmt.execute(sql);
                AsyncLogger.log("Database initialized successfully.");
            }
        } catch (SQLException e) {
            // Exception Handling: Wrapping SQL exception into a custom exception
            throw new DatabaseConnectionException("Failed to initialize database: " + e.getMessage());
        }
    }

    /**
     * CRUD: Create operation. Adds a new question paper.
     */
    public static void addQuestionPaper(String subjectCode, int year, String tags) {
        String sql = "INSERT INTO question_papers(subject_code, year, tags) VALUES(?,?,?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            // Database Connectivity: Using PreparedStatement to prevent SQL injection
            pstmt.setString(1, subjectCode);
            pstmt.setInt(2, year);
            pstmt.setString(3, tags);
            pstmt.executeUpdate();
            
            AsyncLogger.log("Added Question Paper: " + subjectCode + " (" + year + ")");
        } catch (SQLException e) {
            System.err.println("Error adding question paper: " + e.getMessage());
        }
    }

    /**
     * CRUD: Read operation. Retrieves all question papers from the database.
     */
    public static List<QuestionPaper> getAllQuestionPapers() {
        List<QuestionPaper> papers = new ArrayList<>();
        String sql = "SELECT * FROM question_papers";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            // Database Connectivity: Iterating through ResultSet
            while (rs.next()) {
                QuestionPaper paper = new QuestionPaper(
                    rs.getInt("id"),
                    rs.getString("subject_code"),
                    rs.getInt("year"),
                    rs.getString("tags")
                );
                papers.add(paper);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching question papers: " + e.getMessage());
        }
        
        return papers;
    }

    /**
     * CRUD: Update operation. Updates the tags of a specific question paper.
     */
    public static void updateQuestionPaperTags(int id, String newTags) {
        String sql = "UPDATE question_papers SET tags = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, newTags);
            pstmt.setInt(2, id);
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                AsyncLogger.log("Updated Question Paper ID " + id + " with new tags.");
            } else {
                System.out.println("No question paper found with ID: " + id);
            }
        } catch (SQLException e) {
            System.err.println("Error updating question paper: " + e.getMessage());
        }
    }

    /**
     * CRUD: Delete operation. Deletes a specific question paper by ID.
     */
    public static void deleteQuestionPaper(int id) {
        String sql = "DELETE FROM question_papers WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                AsyncLogger.log("Deleted Question Paper ID " + id);
            } else {
                System.out.println("No question paper found with ID: " + id);
            }
        } catch (SQLException e) {
            System.err.println("Error deleting question paper: " + e.getMessage());
        }
    }
}
