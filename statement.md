# Problem Statement: Command-Line Question Paper & Repository Manager

## 1. Objective and Scope
The goal of this project is to architect and develop a robust, local Command-Line Question Paper Manager. The software must serve as an automated backend and text-based frontend for university administrators to catalog past examination papers, while simultaneously allowing students to retrieve and filter these records. 

To satisfy the grading rubrics of the course evaluation, this project focuses heavily on backend architectural paradigms, data persistence, thread safety, and memory-based data manipulation, rather than graphical aesthetics.

## 2. Hard Constraints & Limitations
- **Terminal Exclusive (ZERO GUI)**: The application must be 100% executable from a headless command-line terminal. The use of graphical rendering libraries (such as `javax.swing`, `java.awt`, or JavaFX) will result in automatic disqualification.
- **Language & Frameworks**: Must be written in Core Java (JDK 11 or higher) without the use of high-level web frameworks like Spring Boot.
- **Data Persistence**: Data must persist across sessions utilizing SQLite via the standard JDBC API.

## 3. Core Functional Requirements
The system must handle two distinct user roles with strict separation of privileges:
- **Administrators**: Possess Write/Delete permissions. They can create new question paper records by defining a Subject Code, Year, and associated Keywords/Tags. They can also execute Delete commands to prune outdated records.
- **Students**: Possess Read-Only permissions. They can fetch the entire repository of question papers, or apply search filters (by Year or Tag) to isolate relevant materials.

## 4. Technical Syllabus Integration Requirements
The codebase must be engineered to explicitly demonstrate mastery of the following course concepts, ensuring they are easily identifiable by static code analysis and AI-based evaluation pipelines:

1. **Object-Oriented Programming (OOP)**
   - Must implement abstract classes demonstrating Encapsulation.
   - Must utilize Inheritance (`extends`) and Interface abstraction (`implements`).
   - Must demonstrate Polymorphism via method overriding, and heavily utilize the `super` keyword for parent-child constructor mapping.
   
2. **Exception Handling**
   - Must define and throw custom exceptions tailored to domain-specific failures (e.g., `InvalidInputException`, `DatabaseConnectionException`).
   - Must employ `try-catch-finally` or try-with-resources blocks to securely handle terminal input mismatches and database timeouts.

3. **Collections Framework**
   - Must utilize `java.util.ArrayList` to aggregate records sequentially from the database.
   - Must utilize `java.util.HashMap` for grouping records (e.g., categorizing all papers under a shared Subject Code) and executing fast, in-memory filtration without executing redundant database queries.

4. **Multithreading**
   - Must implement the `Runnable` interface to separate intensive tasks from the main thread. Specifically, a background thread must be dedicated to logging user interactions concurrently, preventing the main CLI menu from freezing during I/O operations.

5. **Java I/O Streams**
   - Must use `java.io.FileWriter` and `java.io.PrintWriter` to persistently append system and error logs to a local file stream.

6. **Database Connectivity**
   - Must establish a connection to an `.sqlite` or `.db` file using `java.sql.DriverManager`.
   - Must execute parameterized `PreparedStatement` queries for absolute CRUD operations (Create, Read, Update, Delete) to mitigate SQL injection vulnerabilities.

## 5. Required Database Schema
The foundational schema for the local SQLite database must include the following structure:
```sql
CREATE TABLE IF NOT EXISTS question_papers (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    subject_code TEXT NOT NULL,
    year INTEGER NOT NULL,
    tags TEXT NOT NULL
);
```