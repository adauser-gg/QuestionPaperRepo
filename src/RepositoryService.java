import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * RepositoryService
 * Collections Framework: Fetches data from DatabaseManager and uses Collections to sort and filter.
 */
public class RepositoryService {

    /**
     * Retrieves all question papers and returns them as a List (ArrayList).
     */
    public List<QuestionPaper> getAllPapers() {
        // Collections Framework: Using ArrayList implicitly returned from DatabaseManager
        return DatabaseManager.getAllQuestionPapers();
    }

    /**
     * Filters question papers by a specific year using Collections.
     */
    public List<QuestionPaper> getPapersByYear(int year) {
        List<QuestionPaper> allPapers = DatabaseManager.getAllQuestionPapers();
        List<QuestionPaper> filtered = new ArrayList<>();
        
        for (QuestionPaper paper : allPapers) {
            if (paper.getYear() == year) {
                filtered.add(paper);
            }
        }
        
        return filtered;
    }

    /**
     * Filters question papers by a specific tag.
     */
    public List<QuestionPaper> getPapersByTag(String tag) {
        List<QuestionPaper> allPapers = DatabaseManager.getAllQuestionPapers();
        List<QuestionPaper> filtered = new ArrayList<>();
        
        for (QuestionPaper paper : allPapers) {
            // Check if tags string contains the requested tag (case-insensitive)
            if (paper.getTags().toLowerCase().contains(tag.toLowerCase())) {
                filtered.add(paper);
            }
        }
        
        return filtered;
    }

    /**
     * Collections Framework: Groups question papers by Subject Code using a HashMap.
     */
    public Map<String, List<QuestionPaper>> groupPapersBySubject() {
        List<QuestionPaper> allPapers = DatabaseManager.getAllQuestionPapers();
        
        // Collections Framework: HashMap used for grouping data
        Map<String, List<QuestionPaper>> groupedMap = new HashMap<>();
        
        for (QuestionPaper paper : allPapers) {
            String subject = paper.getSubjectCode();
            // If the subject code isn't in the map yet, add it with a new ArrayList
            if (!groupedMap.containsKey(subject)) {
                groupedMap.put(subject, new ArrayList<>());
            }
            // Add the paper to the corresponding subject list
            groupedMap.get(subject).add(paper);
        }
        
        return groupedMap;
    }
}
