# Project Report: Command-Line Question Paper & Repository Manager

## 1. Introduction
The "Command-Line Question Paper & Repository Manager" is a robust, terminal-based Java application designed to digitalize the storage, retrieval, and filtering of university examination papers. Built entirely in Core Java (JDK 11+), it prioritizes backend architecture, data persistence, and thread-safe operations over graphical aesthetics.

## 2. System Architecture & Tech Stack
- **Language**: Core Java (JDK 11+)
- **Database**: SQLite
- **Database API**: JDBC (`java.sql`)
- **User Interface**: Command-Line Interface (CLI)
- **Architecture**: Modular Object-Oriented design separating entity models, database access objects, and core service logic.

## 3. Core Features
- **Role-Based Access Control**: Strict segregation of capabilities between `Admin` (Write/Delete) and `Student` (Read/Search).
- **Persistent Storage**: Data is seamlessly written to an `.sqlite` database, preserving repository contents across system reboots.
- **Asynchronous Logging**: All system actions are continuously logged to a local file in the background without freezing the primary terminal loop.
- **In-Memory Filtering**: Large datasets are processed using Collections to prevent database bottlenecking during complex searches.

## 4. Implementation of Key Concepts

### 4.1 Object-Oriented Programming (OOP)
The system heavily models real-world roles utilizing Java's OOP pillars:
- **Encapsulation**: Models like `QuestionPaper` utilize private state fields manipulated exclusively through public getters and setters.
- **Inheritance & Polymorphism**: An abstract base `User` class dictates standard methods (like `displayMenu()`), which are overridden uniquely by child classes `AdminUser` and `StudentUser`. The `super()` keyword actively binds child objects to their parent constructor.

### 4.2 Exception Handling
The program ensures zero fatal crashes during terminal interactions:
- Implements custom exception classes (e.g., `InvalidInputException`).
- Employs resilient `try-catch-finally` and try-with-resources blocks to securely handle database connections and user input mismatches (like typing text when an integer is requested).

### 4.3 Collections Framework
The application executes advanced memory-based data manipulation:
- Extracts database rows into `ArrayList<QuestionPaper>` objects for sequential reading.
- Leverages `HashMap<String, List<QuestionPaper>>` to efficiently group papers by their subject code dynamically.

### 4.4 Multithreading & Concurrency
- Implements the `Runnable` interface in an `AsyncLogger` class.
- When actions occur (e.g., a student searching for a paper), a background thread is spawned to handle the heavy I/O operation of appending to a log file, keeping the user interface completely responsive.

### 4.5 Java I/O Streams
- Persistent auditing is maintained utilizing `FileWriter` and `PrintWriter` classes, which continuously append data to a `data/system.log` document, handling all `IOException` edge cases natively.

### 4.6 Database Connectivity (JDBC)
- Establishes persistent sessions with a lightweight SQLite engine utilizing `DriverManager`.
- Executes strict CRUD (Create, Read, Update, Delete) tasks using `PreparedStatement` interfaces to inject dynamic data safely, entirely avoiding SQL injection risks.

## 5. Conclusion
This project successfully demonstrates the integration of complex Java paradigms into a functional, production-ready backend application. By strictly adhering to command-line constraints, the architecture serves as a pure representation of algorithmic logic, data safety, and object-oriented design.
