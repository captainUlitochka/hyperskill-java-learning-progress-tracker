package tracker;

import java.util.HashMap;

public enum Courses {
    JAVA("Java"),
    DSA("Data Structures and Algorithms"),
    DB("Databases"),
    SPRING("Spring");

    private final String courseName;
    private int submissions = 0;
    private final static int JAVA_MAX_POINTS = 600;
    private final static int DSA_MAX_POINTS = 400;
    private final static int DB_MAX_POINTS = 480;
    private final static int SPRING_MAX_POINTS = 550;
    HashMap<Integer, Integer> courseScores = new HashMap<>();

    Courses(String name) {
        this.courseName = name;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getSubmissions() {
        return submissions;
    }

    public int getStudentScore(int studentId) {
        return courseScores.getOrDefault(studentId, 0);
    }

    public int getStudentsCount() {
        return courseScores.size();
    }

    public void setStudentScore(int studentId, int score) {
        if (score > 0) {
            if (!courseScores.containsKey(studentId)) {
                courseScores.put(studentId, score);
            } else {
                courseScores.put(studentId, courseScores.get(studentId) + score);
            }
        }
    }

    double getAverageScore() {
        return courseScores.values().stream().mapToDouble(Integer::doubleValue).average().orElse(0);
    }
}
