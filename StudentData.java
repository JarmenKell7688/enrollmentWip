package east.Student;

import java.io.Serializable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.*;

public class StudentData implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String email;
    private String password;
    private String course;
    private int yearLevel;
    private String studentId;
    private List<Integer> enrolledSubjectEdps;
    private Map<Integer, Double> grades;

    public StudentData(String name, String email, String password, String course, int yearLevel) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.course = course;
        this.yearLevel = yearLevel;
        this.studentId = null;
        this.enrolledSubjectEdps = new ArrayList<>(); // Ensure non-null
        this.grades = new HashMap<>(); // Ensure non-null
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCourse() {
        return course;
    }

    public int getYearLevel() {
        return yearLevel;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public List<Integer> getEnrolledSubjectEdps() {
        return new ArrayList<>(enrolledSubjectEdps); // Safe copy
    }

    public void addSubjectEdp(int edp) {
        if (!enrolledSubjectEdps.contains(edp)) {
            enrolledSubjectEdps.add(edp);
        }
    }

    public Map<Integer, Double> getGrades() {
        return new HashMap<>(grades); // Safe copy
    }

    public void setGrade(int edp, double grade) {
        grades.put(edp, grade);
    }

    // Handle deserialization to ensure fields are initialized
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        if (enrolledSubjectEdps == null) {
            enrolledSubjectEdps = new ArrayList<>();
        }
        if (grades == null) {
            grades = new HashMap<>();
        }
    }
}