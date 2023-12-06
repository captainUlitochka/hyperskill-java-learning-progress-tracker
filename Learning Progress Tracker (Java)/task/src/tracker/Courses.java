package tracker;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

public enum Courses {
    JAVA("Java", 600),
    DSA("DSA", 400),
    DB("Databases", 480),
    SPRING("Spring", 550);

    private final String courseName;
    private final int courseMaxPoints;
    private int submissions = 0;
    private boolean isCompletedByStudent = false;
    final HashMap<Integer, Integer> courseScores = new HashMap<>();

    Courses(String name, int points) {
        this.courseName = name;
        this.courseMaxPoints = points;
    }

    public boolean isCompletedByStudent() {
        return isCompletedByStudent;
    }

    void setCompletedByStudent() {
        isCompletedByStudent = true;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCourseMaxPoints() {
        return courseMaxPoints;
    }

    public int getSubmissionsCount() {
        return submissions;
    }

    public int getStudentScore(int studentId) {
        return courseScores.getOrDefault(studentId, 0);
    }

    public int getStudentsCount() {
        return courseScores.size();
    }
    public double getAverageGrade() {
        return courseScores
                .values()
                .stream()
                .mapToInt(integer -> integer)
                .average()
                .orElse(0);
    }

    public BigDecimal getCourseCompletionByStudent(int studentId) {
        int courseProgress = courseScores.getOrDefault(studentId, 0);

        return BigDecimal.valueOf((double) courseProgress / courseMaxPoints).setScale(3, RoundingMode.HALF_UP).scaleByPowerOfTen(2);
    }

    public void setStudentScore(int studentId, int score) {
        if (score > 0) {
            if (!courseScores.containsKey(studentId)) {
                courseScores.put(studentId, score);
            } else {
                courseScores.put(studentId, courseScores.get(studentId) + score);
            }
            submissions++;
        }
    }

}
