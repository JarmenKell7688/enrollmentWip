package east.Student;

import java.io.Serializable;

public class StudentData implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String email;
    private String password;
    private String course;
    private int yearLevel;
    private String studentId;

    public StudentData(String name, String email, String password, String course, int yearLevel) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.course = course;
        this.yearLevel = yearLevel;
        this.studentId = null;
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
}