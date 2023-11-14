package tracker;

import java.util.HashMap;

public enum Courses {
    JAVA("Java"),
    DSA("Data Structures and Algorithms"),
    DB("Databases"),
    Spring("Spring");

    private final String courseName;
    private int submissions = 0;
    HashMap<Integer, Integer> courseScores = new HashMap<>();

    Courses(String name) {
        this.courseName = name;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getStudentScore(int studentId) {
        if (courseScores.containsKey(studentId)) {
            return courseScores.get(studentId);
        } else return 0;
    }

    public void setStudentScore(Student student, int score) {
        int key = student.getId();
        if (!courseScores.containsKey(key)) {
            courseScores.put(key, score);
        } else {
            courseScores.put(key, courseScores.get(key) + score);
        }
        submissions++;
    }
}
