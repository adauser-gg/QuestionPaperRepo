# Command-Line Question Paper & Repository Manager

## Project Overview
This project is a robust, 100% Command-Line Interface (CLI) application. It acts as a digital repository manager where administrators can store and manage university question papers, and students can search and filter them. The project strictly avoids any Graphical User Interface (GUI) frameworks (like Swing or JavaFX) to ensure lightweight, headless execution.

## Syllabus Alignment & Technical Implementation
This project is explicitly designed to demonstrate proficiency in Core Java concepts. Key architectural patterns and implementations can be found in the following locations:

1. **Object-Oriented Programming (OOP)**
   - *Location*: `src/User.java`, `src/QuestionPaper.java`
   - *Implementation*: `User.java` is an abstract base class demonstrating **Encapsulation** (protected fields) and **Polymorphism** (abstract methods). The `AdminUser` and `StudentUser` classes demonstrate **Inheritance** by utilizing `extends` and calling parent constructors with `super()`. 

2. **Exception Handling**
   - *Location*: `src/Main.java`, `src/DatabaseManager.java`
   - *Implementation*: Uses custom exceptions (`InvalidInputException`, `DatabaseConnectionException`) and comprehensive `try-catch-finally` blocks to prevent the application from crashing during invalid terminal inputs or database failures.

3. **Collections Framework**
   - *Location*: `src/RepositoryService.java`
   - *Implementation*: Utilizes `ArrayList` to store records fetched from the database, and `HashMap` to index and group question papers by Subject Code, demonstrating in-memory sorting and filtering without continuously querying the database.

4. **Multithreading**
   - *Location*: `src/AsyncLogger.java`
   - *Implementation*: Implements the `Runnable` interface to spawn a background thread. This allows the application to asynchronously log system actions without blocking the main terminal execution loop.

5. **Java I/O Streams**
   - *Location*: `src/AsyncLogger.java`
   - *Implementation*: Utilizes `FileWriter` and `PrintWriter` to append user actions and system events to a persistent log file (`data/system.log`).

6. **Database Connectivity (JDBC)**
   - *Location*: `src/DatabaseManager.java`
   - *Implementation*: Uses the `java.sql` package and SQLite driver to perform full CRUD operations (Create, Read, Update, Delete). It uses `PreparedStatement` to safely inject variables and prevent SQL injection.

---

## Complete Setup & Execution Instructions
Assume a clean environment. Please follow these step-by-step instructions to set up, configure, and run the project.

### 1. Environment Setup
- You must have **Java Development Kit (JDK) 11** or higher installed. Verify this by running `java -version` in your terminal.
- Ensure your terminal (Windows CMD, PowerShell, or Linux/Mac Bash) has the `java` and `javac` commands available in the system PATH.

### 2. Dependency Installation
This project relies on SQLite for the database.
1. Download the SQLite JDBC driver (e.g., `sqlite-jdbc-3.42.0.0.jar`).
2. Create a folder named `lib` in the root of the project directory.
3. Place the downloaded `.jar` file inside the `lib` directory. *(Note: The driver is already included in this repository).*

### 3. Compilation
Before running the code, it must be compiled into bytecode.
1. Open your terminal and navigate to the root directory of this project:
   ```bash
   cd path/to/QuestionPaperRepo
   ```
2. Create a `bin` directory to hold the compiled files:
   ```bash
   mkdir bin
   ```
3. Compile all Java source files from the `src` directory:
   ```bash
   javac -d bin src/*.java
   ```

### 4. Execution
The project runs entirely in the command line. You must include both the `bin` directory and the SQLite JDBC driver in your classpath.

**For Windows (CMD/PowerShell):**
```bash
java -cp "bin;lib/sqlite-jdbc.jar" Main
```

**For Linux/Mac (Bash/Zsh):**
```bash
java -cp "bin:lib/sqlite-jdbc.jar" Main
```

### 5. Application Usage
Upon successful execution, the terminal will display the Main Menu. The database (`data/database.db`) and log file (`data/system.log`) will be automatically generated.
- **Login as Admin (Option 1)**: Allows you to "Add a new question paper" or "Delete a question paper" using standard terminal prompts.
- **Login as Student (Option 2)**: Allows you to search for papers by tags or years, utilizing the Collections framework to filter results.

> **Note**: As you navigate the terminal, check the `data/system.log` file. You will see that the background thread is actively writing your actions using Java I/O Streams.
