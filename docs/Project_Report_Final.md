# COVER PAGE

**Project Title**: Command-Line Question Paper & Repository Manager
**Course**: Programming in Java
**Submitted By**: [Your Name/ID]
**Date**: [Current Date]

---
*Page Break*
---

# INDEX
1. Introduction
2. Problem Statement
3. Functional Requirements
4. Non-functional Requirements
5. System Architecture
6. Design Diagrams
7. Design Decisions & Rationale
8. Implementation Details
9. Screenshots / Results
10. Testing Approach
11. Challenges Faced
12. Learnings & Key Takeaways
13. Future Enhancements
14. References

---
*Page Break*
---

## 1. Introduction
The "Command-Line Question Paper & Repository Manager" is a comprehensive, terminal-based Java application designed to digitize and manage university examination papers. The project provides an automated backend and text-based frontend for administrators to catalog past papers and for students to search and retrieve these records. Built entirely in Core Java (JDK 11+), the software prioritizes robust backend architecture, strict object-oriented design, data persistence, and thread-safe operations over graphical aesthetics.

## 2. Problem Statement
University students frequently struggle to locate and filter past examination papers relevant to their specific coursework, while administrators lack a lightweight, secure mechanism to catalog these records without relying on bloated graphical software. 

The objective of this project is to build a 100% headless (ZERO GUI), command-line executable application that resolves this by providing a centralized database repository. The system must enforce role-based access control, ensuring administrators have exclusive write privileges, while students have fast, memory-optimized read and search capabilities.

## 3. Functional Requirements
- **Authentication & Roles**: The system must provide distinct login pathways for "Admin" and "Student" roles.
- **Repository Management (Admin)**: Administrators must be able to add new question papers (specifying Subject Code, Year, and Tags) and delete outdated papers via ID.
- **Data Retrieval (Student)**: Students must be able to view all papers, search by Year, search by Tag, and view all papers dynamically grouped by Subject Code.
- **Persistent Logging**: Every action taken by any user must be logged to a local system file automatically.

## 4. Non-functional Requirements
- **Performance**: The system must utilize memory-based filtering (Collections) to prevent database bottlenecks during complex queries.
- **Concurrency**: The logging mechanism must operate asynchronously on a separate thread to prevent terminal blocking.
- **Reliability**: The system must handle all unexpected terminal inputs gracefully without crashing, utilizing comprehensive exception handling.
- **Persistence**: Data must be stored in a resilient, local SQLite database that persists across system reboots.

## 5. System Architecture
The application follows a modular, layered architecture:
- **Presentation Layer**: Handled entirely via standard I/O (terminal) in `Main.java`, parsing commands and routing them to services.
- **Business Logic Layer**: Comprises `User.java`, `RepositoryService.java`, and `AsyncLogger.java`. This layer manages roles, thread spawning, and in-memory data manipulation using Java Collections.
- **Data Access Layer**: Comprises `DatabaseManager.java`, securely routing CRUD operations to the SQLite engine via the JDBC API.

## 6. Design Diagrams

### Use Case Diagram
- **Admin**: Login -> Add Paper, Delete Paper, View Papers, Logout.
- **Student**: Login -> View Papers, Search by Tag, Search by Year, Group by Subject, Logout.
*(Note: Visual Use Case Diagram can be generated externally if required)*

### Workflow Diagram
1. Application Start -> Initialize Database -> Spawn Logger Thread.
2. User Selects Role -> Present Role-Specific Menu.
3. User Inputs Command -> Execute Business Logic / Database Query.
4. Return Output to Terminal -> Loop back to Menu.

### Sequence Diagram (Adding a Paper)
`Admin -> Main (Input) -> DatabaseManager (addQuestionPaper) -> SQLite (INSERT) -> AsyncLogger (log) -> Main (Success Output)`.

### Class/Component Diagram
- **User (Abstract)**: Base class defining `username`, `role`, and abstract `displayMenu()`.
- **AdminUser / StudentUser**: Extends `User`, overriding menu displays.
- **QuestionPaper**: Entity class encapsulating properties (id, subjectCode, year, tags).
- **DatabaseManager**: Static class handling JDBC `Connection`, `PreparedStatement`, and `ResultSet`.
- **RepositoryService**: Manipulates `List` and `Map` collections.
- **AsyncLogger**: Implements `Runnable` for file I/O threading.

### ER Diagram
- **Entity**: `question_papers`
- **Attributes**: `id` (PK, Integer), `subject_code` (Text), `year` (Integer), `tags` (Text).

## 7. Design Decisions & Rationale
- **Zero GUI**: Elected to build a strict CLI to fulfill execution constraints and focus entirely on core algorithm design and backend optimization.
- **SQLite over MySQL**: Selected SQLite for embedded, zero-configuration data persistence, ensuring the project is highly portable and does not require the evaluator to set up a local database server.
- **HashMap Grouping**: Chose to retrieve all records once and group them via `HashMap` in `RepositoryService.java` rather than executing multiple `SELECT...GROUP BY` SQL queries, reducing database load.
- **Asynchronous Logging**: Decided to run `AsyncLogger` on a separate thread implementing `Runnable`. Terminal I/O is notoriously slow; pushing file I/O to a background thread prevents the CLI from freezing.

## 8. Implementation Details
The project is explicitly mapped to syllabus requirements:
1. **OOP**: Polymorphism via `displayMenu()`; Inheritance via `AdminUser extends User`; Encapsulation via private fields in `QuestionPaper`.
2. **Exceptions**: Custom `InvalidInputException` and `DatabaseConnectionException` utilized alongside robust `try-catch` blocks.
3. **Collections**: Extensive use of `ArrayList` for record sequences and `HashMap` for subject categorization.
4. **Multithreading**: `AsyncLogger` utilizes `Thread` and `Runnable` for concurrent logging.
5. **Java I/O**: Implementation of `FileWriter` and `PrintWriter` for appending to `system.log`.
6. **JDBC**: `DriverManager` and `PreparedStatement` interfaces executing native SQL queries.

## 9. Screenshots / Results

### Main Menu & Admin Login
`[INSERT SCREENSHOT HERE: Showing the terminal booting up, the main menu, and an Admin successfully logging in and adding a paper.]`

### Student Search Functionality
`[INSERT SCREENSHOT HERE: Showing the student menu and a successful search filter by Year or Tag.]`

### Background Logger File
`[INSERT SCREENSHOT HERE: Showing the contents of data/system.log with timestamped actions.]`

## 10. Testing Approach
- **Manual CLI Testing**: Edge-case inputs (e.g., typing strings when an integer year is requested) were tested to verify exception handling.
- **Concurrency Testing**: Multiple rapid inputs were executed to ensure the background logging thread did not encounter race conditions or block the terminal.
- **Database Persistence**: The application was forcibly closed and rebooted to confirm data retrieval from the SQLite file remained intact.

## 11. Challenges Faced
- **Thread Synchronization**: Ensuring the background logger thread cleanly opened and closed the file stream without throwing concurrent modification exceptions.
- **Input Mismatch Errors**: Standard `Scanner` objects frequently break when reading mixed data types (integers followed by strings). This required strategic buffer flushing within catch blocks to maintain the terminal loop.

## 12. Learnings & Key Takeaways
- Gained a deep understanding of standard JDBC architecture and the critical importance of `PreparedStatement` over raw string concatenation.
- Mastered the integration of Multithreading with File I/O, recognizing the performance benefits of asynchronous tasks.
- Strengthened proficiency in Object-Oriented design, specifically abstraction and polymorphism for role-based systems.

## 13. Future Enhancements
- **Encryption**: Implement hashing (e.g., BCrypt) for authenticated user logins instead of basic role selection.
- **Network Architecture**: Migrate the local SQLite database to a cloud-based PostgreSQL instance and build a REST API wrapper.
- **Advanced Search**: Implement a full-text search algorithm for tags utilizing fuzzy matching.

## 14. References
1. Oracle Java 11 Documentation (https://docs.oracle.com/en/java/javase/11/)
2. SQLite JDBC Driver Repository (https://github.com/xerial/sqlite-jdbc)
3. Course Syllabus: "Programming in Java" - VITyarthi
