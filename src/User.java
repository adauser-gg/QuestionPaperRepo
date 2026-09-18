/**
 * User
 * Object-Oriented Programming: Abstract base class demonstrating Inheritance and Polymorphism.
 */
public abstract class User {
    // Encapsulation: Protected attributes, accessible to subclasses
    protected String username;
    protected String role;

    public User(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    // Polymorphism: Abstract method to be overridden by subclasses
    public abstract void displayMenu();
}

/**
 * AdminUser
 * Object-Oriented Programming: Inheritance (extends User)
 */
class AdminUser extends User {

    public AdminUser(String username) {
        // OOP: Using super keyword to invoke parent constructor
        super(username, "Admin");
    }

    // Polymorphism: Overriding the abstract method
    @Override
    public void displayMenu() {
        System.out.println("\n--- Admin Menu ---");
        System.out.println("1. View all question papers");
        System.out.println("2. Add new question paper");
        System.out.println("3. Delete a question paper");
        System.out.println("4. Logout");
        System.out.print("Select an option: ");
    }
}

/**
 * StudentUser
 * Object-Oriented Programming: Inheritance (extends User)
 */
class StudentUser extends User {

    public StudentUser(String username) {
        // OOP: Using super keyword to invoke parent constructor
        super(username, "Student");
    }

    // Polymorphism: Overriding the abstract method
    @Override
    public void displayMenu() {
        System.out.println("\n--- Student Menu ---");
        System.out.println("1. View all question papers");
        System.out.println("2. Search papers by Year");
        System.out.println("3. Search papers by Tag");
        System.out.println("4. Group papers by Subject");
        System.out.println("5. Logout");
        System.out.print("Select an option: ");
    }
}
