import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

// Exception Handling: Custom exception for invalid inputs
class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
    }
}

/**
 * Main
 * Entry point with a terminal-based while loop and switch menu for Admin and Student roles.
 * Handles invalid terminal inputs gracefully.
 */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final RepositoryService repoService = new RepositoryService();

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("  Command-Line Question Paper & Repository Manager");
        System.out.println("==================================================");
        
        try {
            DatabaseManager.initializeDatabase();
            AsyncLogger.log("Application started.");
        } catch (DatabaseConnectionException e) {
            System.err.println("Critical Error: " + e.getMessage());
            System.exit(1);
        }

        boolean running = true;
        while (running) {
            System.out.println("\n--- Main Login ---");
            System.out.println("1. Login as Admin");
            System.out.println("2. Login as Student");
            System.out.println("3. Exit");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine();
            
            try {
                switch (choice) {
                    case "1":
                        // Polymorphism in action: treating AdminUser as User
                        User admin = new AdminUser("admin");
                        AsyncLogger.log("Admin logged in.");
                        handleUserSession(admin);
                        break;
                    case "2":
                        // Polymorphism in action: treating StudentUser as User
                        User student = new StudentUser("student");
                        AsyncLogger.log("Student logged in.");
                        handleUserSession(student);
                        break;
                    case "3":
                        running = false;
                        System.out.println("Exiting application. Goodbye!");
                        AsyncLogger.log("Application exited.");
                        break;
                    default:
                        // Exception Handling: Throwing custom exception
                        throw new InvalidInputException("Invalid choice. Please enter 1, 2, or 3.");
                }
            } catch (InvalidInputException e) {
                // Exception Handling: Graceful handling of invalid terminal inputs
                System.out.println("Error: " + e.getMessage());
            }
        }
        
        scanner.close();
    }

    /**
     * Handles the session based on the User's role (Admin or Student).
     */
    private static void handleUserSession(User user) {
        boolean sessionActive = true;
        
        while (sessionActive) {
            user.displayMenu(); // Polymorphic method call
            String choice = scanner.nextLine();
            
            try {
                if (user instanceof AdminUser) {
                    sessionActive = processAdminMenu(choice);
                } else if (user instanceof StudentUser) {
                    sessionActive = processStudentMenu(choice);
                }
            } catch (Exception e) {
                System.out.println("An error occurred during operation: " + e.getMessage());
                // Flush the scanner buffer in case of input mismatch
                if (e instanceof InputMismatchException) {
                    scanner.nextLine();
                }
            }
        }
    }

    private static boolean processAdminMenu(String choice) {
        switch (choice) {
            case "1":
                displayPapers(repoService.getAllPapers());
                return true;
            case "2":
                System.out.print("Enter Subject Code: ");
                String subjectCode = scanner.nextLine();
                
                System.out.print("Enter Year: ");
                int year = 0;
                try {
                    year = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                } catch (InputMismatchException e) {
                    System.out.println("Error: Year must be a valid integer.");
                    scanner.nextLine(); // Consume newline
                    return true;
                }
                
                System.out.print("Enter Tags (comma-separated): ");
                String tags = scanner.nextLine();
                
                DatabaseManager.addQuestionPaper(subjectCode, year, tags);
                System.out.println("Question paper added successfully.");
                return true;
            case "3":
                System.out.print("Enter ID of the paper to delete: ");
                int id = 0;
                try {
                    id = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                } catch (InputMismatchException e) {
                    System.out.println("Error: ID must be a valid integer.");
                    scanner.nextLine(); // Consume newline
                    return true;
                }
                
                DatabaseManager.deleteQuestionPaper(id);
                return true;
            case "4":
                System.out.println("Logging out Admin...");
                return false; // End session
            default:
                System.out.println("Invalid option. Please try again.");
                return true;
        }
    }

    private static boolean processStudentMenu(String choice) {
        switch (choice) {
            case "1":
                displayPapers(repoService.getAllPapers());
                return true;
            case "2":
                System.out.print("Enter Year to search: ");
                int year = 0;
                try {
                    year = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                } catch (InputMismatchException e) {
                    System.out.println("Error: Year must be a valid integer.");
                    scanner.nextLine(); // Consume newline
                    return true;
                }
                displayPapers(repoService.getPapersByYear(year));
                AsyncLogger.log("Student searched for papers in year " + year);
                return true;
            case "3":
                System.out.print("Enter Tag to search: ");
                String tag = scanner.nextLine();
                displayPapers(repoService.getPapersByTag(tag));
                AsyncLogger.log("Student searched for papers with tag '" + tag + "'");
                return true;
            case "4":
                Map<String, List<QuestionPaper>> grouped = repoService.groupPapersBySubject();
                if (grouped.isEmpty()) {
                    System.out.println("No question papers found.");
                } else {
                    for (Map.Entry<String, List<QuestionPaper>> entry : grouped.entrySet()) {
                        System.out.println("\nSubject: " + entry.getKey());
                        for (QuestionPaper p : entry.getValue()) {
                            System.out.println("  " + p.toString());
                        }
                    }
                }
                AsyncLogger.log("Student grouped papers by subject.");
                return true;
            case "5":
                System.out.println("Logging out Student...");
                return false; // End session
            default:
                System.out.println("Invalid option. Please try again.");
                return true;
        }
    }

    /**
     * Helper method to display a list of papers.
     */
    private static void displayPapers(List<QuestionPaper> papers) {
        if (papers.isEmpty()) {
            System.out.println("No question papers found.");
        } else {
            System.out.println("\n--- Question Papers ---");
            for (QuestionPaper paper : papers) {
                System.out.println(paper.toString());
            }
        }
    }
}
