package tracker;

import java.util.HashMap;

public enum Courses {
    JAVA("Java"),
    DSA("Data Structures and Algorithms"),
    DB("Databases"),
    SPRING("Spring");

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
        return courseScores.getOrDefault(studentId, 0);
    }

    public void setStudentScore(int studentId, int score) {
        if (!courseScores.containsKey(studentId)) {
            courseScores.put(studentId, score);
        } else {
            courseScores.put(studentId, courseScores.get(studentId) + score);
        }
        submissions++;
    }
}
