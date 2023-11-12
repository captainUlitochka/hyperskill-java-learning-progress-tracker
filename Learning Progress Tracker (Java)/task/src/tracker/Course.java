package tracker;

import java.util.HashMap;

public class Course {
    String name;
    int submissions = 0;
    HashMap<Integer, Integer> courseScores = new HashMap<>();

    public Course(String name) {
        this.name = name;
    }

    public void setStudentScore(Student student, int score) {
        if (courseScores.containsKey(student.getId())) {
            courseScores.put(student.getId(), score);
            submissions++;
        }
    }

}
