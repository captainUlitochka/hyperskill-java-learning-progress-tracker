package tracker;

import java.math.BigDecimal;
public class StudentScoreByCourse {
    private int studentId;
    private int score;
    private BigDecimal percentOfCompletion;

    public StudentScoreByCourse(int studentId, int score, BigDecimal percentOfCompletion) {
        this.studentId = studentId;
        this.score = score;
        this.percentOfCompletion = percentOfCompletion;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getScore() {
        return score;
    }

    public BigDecimal getPercentOfCompletion() {
        return percentOfCompletion;
    }
}
