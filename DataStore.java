package east.Admin;

import east.Student.StudentData;

import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class DataStore {
    private static final List<StudentData> students = new ArrayList<>();
    private static final List<TeacherData> teachers = new ArrayList<>();
    private static final List<SubjectData> subjects = new ArrayList<>();
    private static final String SUBJECTS_FILE = "subjects.dat";
    private static final String STUDENTS_FILE = "students.dat";
    private static final String TEACHERS_FILE = "teachers.dat";

    static {
        loadSubjectsFromFile();
        loadStudentsFromFile();
        loadTeachersFromFile();
        // Removed the automatic removal of student with ID "s29"
        saveTeachersToFile();
        saveSubjectsToFile();
        saveStudentsToFile();
    }

    public static void addStudent(StudentData student) {
        students.add(student);
        saveStudentsToFile();
    }

    public static List<StudentData> getStudents() {
        return new ArrayList<>(students);
    }

    public static void deleteStudent(String studentId) {
        students.removeIf(student -> student.getStudentId() != null && student.getStudentId().equals(studentId));
        saveStudentsToFile();
    }

    public static void updateStudentId(String name, String email, String studentId) {
        // Validate that studentId contains only digits
        if (!studentId.matches("^[0-9]+$")) {
            throw new IllegalArgumentException("Student ID must contain only numbers.");
        }
        for (StudentData student : students) {
            if (student.getName().equals(name) && student.getEmail().equals(email)) {
                student.setStudentId(studentId);
                break;
            }
        }
        saveStudentsToFile();
    }

    public static void updateStudent(StudentData updatedStudent) {
        for (int i = 0; i < students.size(); i++) {
            StudentData student = students.get(i);
            if (student.getEmail().equals(updatedStudent.getEmail()) && 
                student.getName().equals(updatedStudent.getName())) {
                students.set(i, updatedStudent);
                saveStudentsToFile();
                break;
            }
        }
    }

    public static void addTeacher(TeacherData teacher) {
        teachers.add(teacher);
        saveTeachersToFile();
    }

    public static List<TeacherData> getTeachers() {
        return new ArrayList<>(teachers);
    }

    public static boolean doesTeacherExist(String teacherId) {
        return teachers.stream().anyMatch(teacher -> teacher.id.equals(teacherId));
    }

    public static void deleteTeacher(String teacherId) {
        teachers.removeIf(teacher -> teacher.id.equals(teacherId));
        saveTeachersToFile();
    }

    public static void addSubject(SubjectData subject) {
        subjects.add(subject);
        saveSubjectsToFile();
    }

    public static List<SubjectData> getSubjects() {
        return new ArrayList<>(subjects);
    }

    public static boolean doesSubjectExist(int edp) {
        return subjects.stream().anyMatch(subject -> subject.getEdp() == edp);
    }

    public static void deleteSubject(int edp) {
        subjects.removeIf(subject -> subject.getEdp() == edp);
        saveSubjectsToFile();
    }

    private static void saveSubjectsToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SUBJECTS_FILE))) {
            oos.writeObject(subjects);
        } catch (IOException e) {
            System.err.println("Error saving subjects to file: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private static void loadSubjectsFromFile() {
        File file = new File(SUBJECTS_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SUBJECTS_FILE))) {
                Object obj = ois.readObject();
                if (obj instanceof List) {
                    subjects.clear();
                    subjects.addAll((List<SubjectData>) obj);
                }
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error loading subjects from file: " + e.getMessage());
            }
        }
    }

    public static void saveStudentsToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(STUDENTS_FILE))) {
            oos.writeObject(students);
        } catch (IOException e) {
            System.err.println("Error saving students to file: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private static void loadStudentsFromFile() {
        File file = new File(STUDENTS_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(STUDENTS_FILE))) {
                Object obj = ois.readObject();
                if (obj instanceof List) {
                    students.clear();
                    students.addAll((List<StudentData>) obj);
                }
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error loading students from file: " + e.getMessage());
            }
        }
    }

    private static void saveTeachersToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(TEACHERS_FILE))) {
            oos.writeObject(teachers);
        } catch (IOException e) {
            System.err.println("Error saving teachers to file: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private static void loadTeachersFromFile() {
        File file = new File(TEACHERS_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(TEACHERS_FILE))) {
                Object obj = ois.readObject();
                if (obj instanceof List) {
                    teachers.clear();
                    teachers.addAll((List<TeacherData>) obj);
                }
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error loading teachers from file: " + e.getMessage());
            }
        }
    }

    public static class TeacherData implements Serializable {
        private static final long serialVersionUID = 1L;
        public final String id;
        public final String name;
        public final String password;
        public final Integer subjectEdp;

        public TeacherData(String id, String name, String password, Integer subjectEdp) {
            this.id = id;
            this.name = name;
            this.password = password;
            this.subjectEdp = subjectEdp;
        }

        public Integer getSubjectEdp() {
            return subjectEdp;
        }
    }

    public static class SubjectData implements Serializable {
        private static final long serialVersionUID = 1L;
        private final int edp;
        private final int yearLevel;
        private final String semester;
        private final String subjectName;
        private final String type;
        private final int units;
        private final String days;
        private final String time;
        private final String room;
        private final String course;

        public SubjectData(int edp, int yearLevel, String semester, String subjectName, String type, int units,
                           String days, String time, String room, String course) {
            this.edp = edp;
            this.yearLevel = yearLevel;
            this.semester = semester;
            this.subjectName = subjectName;
            this.type = type;
            this.units = units;
            this.days = days;
            this.time = time;
            this.room = room;
            this.course = course;
        }

        public int getEdp() {
            return edp;
        }

        public int getYearLevel() {
            return yearLevel;
        }

        public String getSemester() {
            return semester;
        }

        public String getSubjectName() {
            return subjectName;
        }

        public String getType() {
            return type;
        }

        public int getUnits() {
            return units;
        }

        public String getDays() {
            return days;
        }

        public String getTime() {
            return time;
        }

        public String getRoom() {
            return room;
        }

        public String getCourse() {
            return course;
        }
    }
}