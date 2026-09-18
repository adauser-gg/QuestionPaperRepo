/**
 * QuestionPaper
 * Object-Oriented Programming: Encapsulated entity class representing a question paper.
 */
public class QuestionPaper {
    
    // Encapsulation: Private attributes
    private int id;
    private String subjectCode;
    private int year;
    private String tags;

    /**
     * Constructor to initialize a QuestionPaper object.
     */
    public QuestionPaper(int id, String subjectCode, int year, String tags) {
        this.id = id;
        this.subjectCode = subjectCode;
        this.year = year;
        this.tags = tags;
    }

    // Encapsulation: Public Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    // Polymorphism: Overriding the Object's toString method
    @Override
    public String toString() {
        return String.format("ID: %d | Subject: %s | Year: %d | Tags: %s", id, subjectCode, year, tags);
    }
}
