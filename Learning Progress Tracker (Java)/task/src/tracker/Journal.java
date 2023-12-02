package tracker;

import java.util.*;

public class Journal {
    private int nextStudentId;
    private final List<Student> studentList;
    private final List<Courses> coursesList;
    private static final int DEFAULT_STUDENT_ID = 10000;

    public List<Student> getStudentList() {
        return studentList;
    }

    public Journal() {
        studentList = new ArrayList<>();
        coursesList = new ArrayList<>();
        coursesList.addAll(List.of(Courses.values()));
        nextStudentId = DEFAULT_STUDENT_ID;
    }

    boolean addStudent(String input) {
        try {
            Student student = new Student(input);
            if (isEmailUnique(student.getEmail())) {
                student.setId(nextStudentId);
                studentList.add(student);
                nextStudentId++;
                System.out.println(Messages.ADD_SUCCESSFUL.getMessage());
                return true;
            } else System.out.println(Messages.EMAIL_EXISTS.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    boolean addPoints(String input) {
        String[] submission = input.split(" ");
        int pointCounter = 1;
        String pointsRegex = "^[0-9]+ +[0-9]+ +[0-9]+ +[0-9]+ +[0-9]+$";
        String studentId = submission[0];
        if (getStudentById(studentId) != null) {
            if (input.matches(pointsRegex)) {
                for (Courses c : coursesList) {
                    c.setStudentScore(
                            Integer.parseInt(studentId),
                            Integer.parseInt(submission[pointCounter]));
                    pointCounter++;
                }
                return true;
            } else System.out.println(Messages.INCORRECT_POINTS_FORMAT.getMessage());
        }
        return false;
    }

    String getStudentScores(String input) {
        if (getStudentById(input) != null) {
            int id = Integer.parseInt(input);
            return String.format((Messages.STUDENT_DATA.getMessage()) + "%n",
                    input,
                    coursesList.get(0).getStudentScore(id),
                    coursesList.get(1).getStudentScore(id),
                    coursesList.get(2).getStudentScore(id),
                    coursesList.get(3).getStudentScore(id));
        }
        return null;
    }

    private Student getStudentById(String input) {
        String idRegex = "^[0-9]*$";
        if (input.matches(idRegex)) {
            for (Student s : studentList) {
                if (s.getId() == Integer.parseInt(input)) {
                    return s;
                }
            }
        }
        System.out.printf((Messages.INCORRECT_STUDENT_ID.getMessage()) + "%n", input);
        return null;
    }

    private boolean isEmailUnique(String email) {
        if (!studentList.isEmpty()) {
            return studentList.stream().noneMatch(s -> s.getEmail().equals(email));
        }
        return true;
    }

    /*
    Удалю в следующем коммите, пока нужно ради примера:
    String getPopularCourse() {
        Courses result = coursesList.stream()
                .filter(x -> x.getStudentsCount() > 0)
                .max(Comparator.comparing(Courses::getStudentsCount))
                .orElse(null);
        if (result == null) {
            return Messages.NO_DATA.getMessage();
        }
        return result.getCourseName();
    }
     */

    String getMostPopularCourse() {
        StringBuilder result = new StringBuilder();
        coursesList.sort(Comparator.comparing(Courses::getStudentsCount).reversed());
        return getCourseStatisticsString(result);
    }

    String getLeastPopularCourse() {
        StringBuilder result = new StringBuilder();
        coursesList.sort(Comparator.comparing(Courses::getStudentsCount));
        return getCourseStatisticsString(result);
    }

    private String getCourseStatisticsString(StringBuilder result) {
        result.append(coursesList.get(0).getCourseName());
        for (int i = 0; i < coursesList.size() - 1; i++) {
            if (coursesList.get(i).getStudentsCount() == coursesList.get(i + 1).getStudentsCount()) {
                result
                        .append(", ")
                        .append(coursesList.get(i + 1).getCourseName())
                        .append("\n");
            }
        }
        return result.toString();
    }

    String getCompletionState(String courseName) {
        StringBuilder result = new StringBuilder();
        for (Courses c :
                coursesList) {
            if (c.getCourseName().toLowerCase().equals(courseName)) {
                for (int i = 0; i < studentList.size(); i++) {
                    int studentId = studentList.get(i).getId();
                    result
                            .append(studentId)
                            .append(" ")
                            .append(c.getStudentScore(studentId))
                            .append("        ")
                            .append(c.getCourseCompletionByStudent(studentId))
                            .append("%");
                }
            }
            else {
                result.append(Messages.COURSE_NAME_ERROR.getMessage());
            }
        }
        return result.toString();
    }

}
