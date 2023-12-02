package tracker;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

public enum Courses {
    JAVA("Java", 600),
    DSA("Data Structures and Algorithms", 400),
    DB("Databases", 480),
    SPRING("Spring", 550);

    private final String courseName;
    private final int courseMaxPoints;
    private int submissions = 0;
    HashMap<Integer, Integer> courseScores = new HashMap<>();

    Courses(String name, int points) {
        this.courseName = name;
        this.courseMaxPoints = points;
    }

    public String getCourseName() {
        return courseName;
    }
    public int getCourseMaxPoints() { return courseMaxPoints; }

    public int getSubmissions() {
        return submissions;
    }

    public int getStudentScore(int studentId) {
        return courseScores.getOrDefault(studentId, 0);
    }

    public BigDecimal getCourseCompletionByStudent(int studentId) {
        int courseProgress = courseScores.getOrDefault(studentId, 0);

        return new BigDecimal((double) courseProgress / courseMaxPoints).setScale(3, RoundingMode.HALF_UP).scaleByPowerOfTen(2);
        //return (double) (courseProgress * 100) / courseMaxPoints; //TODO: это ещё не проверено
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
