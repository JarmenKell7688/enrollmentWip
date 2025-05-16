package east.Student;

public class StudentTrappings {
	
    // Checks if student name contains letters only (no numbers)
    public boolean isNameValid(String name) {
        return name.matches("^[a-zA-Z\\s]+$");
    }
}
