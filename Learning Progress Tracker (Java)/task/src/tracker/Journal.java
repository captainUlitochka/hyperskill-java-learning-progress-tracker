package tracker;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        String pointsRegex = "^\\d+ +\\d+ +\\d+ +\\d+ +\\d+$";
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
            return String.format((Messages.STUDENT_DATA.getMessage()),
                    input,
                    coursesList.get(0).getStudentScore(id),
                    coursesList.get(1).getStudentScore(id),
                    coursesList.get(2).getStudentScore(id),
                    coursesList.get(3).getStudentScore(id));
        }
        return null;
    }

    private Student getStudentById(String input) {
        String idRegex = "^\\d*$";
        if (input.matches(idRegex)) {
            for (Student s : studentList) {
                if (s.getId() == Integer.parseInt(input)) {
                    return s;
                }
            }
        }
        System.out.printf((Messages.INCORRECT_STUDENT_ID.getMessage()), input);
        return null;
    }

    private boolean isEmailUnique(String email) {
        if (!studentList.isEmpty()) {
            return studentList.stream().noneMatch(s -> s.getEmail().equals(email));
        }
        return true;
    }

    boolean anyScoreExists() {
        return coursesList.stream().anyMatch(c -> c.getSubmissionsCount() != 0);
    }

    List<Courses> getMostPopularCourse() {
        List<Courses> sortedCoursesList = new ArrayList<>(coursesList);
        sortedCoursesList.sort(Comparator.comparing(Courses::getStudentsCount).reversed());
        return sortedCoursesList
                .stream()
                .filter(course -> course.getStudentsCount() == sortedCoursesList.get(0).getStudentsCount())
                .toList();
    }


    List<Courses> getLeastPopularCourse() {
        List<Courses> mostPopular = getMostPopularCourse();
        List<Courses> sortedCoursesList = new ArrayList<>(coursesList);
        sortedCoursesList.removeAll(mostPopular);

        sortedCoursesList.sort(Comparator.comparing(Courses::getStudentsCount));
        if (sortedCoursesList.isEmpty()) {
            return new ArrayList<>();
        } else return sortedCoursesList
                .stream()
                .filter(course -> course.getStudentsCount() == sortedCoursesList.get(0).getStudentsCount())
                .toList();
    }

    List<Courses> getEasiestCourse() {
        List<Courses> sortedCoursesList = new ArrayList<>(coursesList);
        sortedCoursesList.sort(Comparator.comparing(Courses::getAverageGrade).reversed());
        return sortedCoursesList
                .stream()
                .filter(course -> course.getAverageGrade() == sortedCoursesList.get(0).getAverageGrade())
                .toList();
    }

    List<Courses> getHardestCourse() {
        List<Courses> easyCoursesList = getEasiestCourse();
        List<Courses> sortedCoursesList = new ArrayList<>(coursesList);
        sortedCoursesList.removeAll(easyCoursesList);

        sortedCoursesList.sort(Comparator.comparing(Courses::getAverageGrade));
        if (sortedCoursesList.isEmpty()) {
            return new ArrayList<>();
        } else return sortedCoursesList
                .stream()
                .filter(course -> course.getAverageGrade() == sortedCoursesList.get(0).getAverageGrade())
                .toList();
    }

    List<Courses> getHighestActivityCourse() {
        List<Courses> sortedCoursesList = new ArrayList<>(coursesList);
        sortedCoursesList.sort(Comparator.comparing(Courses::getSubmissionsCount).reversed());
        return sortedCoursesList
                .stream()
                .filter(course -> course.getSubmissionsCount() == sortedCoursesList.get(0).getSubmissionsCount())
                .toList();
    }

    List<Courses> getLowestActivityCourse() {
        List<Courses> highPopularList = getHighestActivityCourse();
        List<Courses> sortedCoursesList = new ArrayList<>(coursesList);
        sortedCoursesList.removeAll(highPopularList);

        sortedCoursesList.sort(Comparator.comparing(Courses::getSubmissionsCount));
        if (sortedCoursesList.isEmpty()) {
            return new ArrayList<>();
        } else return sortedCoursesList
                .stream()
                .filter(course -> course.getSubmissionsCount() == sortedCoursesList.get(0).getSubmissionsCount())
                .toList();
    }

    private String getCoursesNames(List<Courses> courses) {
        if (courses.isEmpty())
            return Messages.NO_DATA.getMessage();
        return courses.stream().map(Courses::getCourseName).collect(Collectors.joining(", ")).concat("\n");
    }

    public String getCourseStatistics() {
        StringBuilder result = new StringBuilder(Messages.MOST_POPULAR.getMessage());
        if (anyScoreExists()) {
            result
                    .append(getCoursesNames(getMostPopularCourse()))
                    .append(Messages.LEAST_POPULAR.getMessage())
                    .append(getCoursesNames(getLeastPopularCourse()))
                    .append(Messages.HIGH_ACTIVITY.getMessage())
                    .append(getCoursesNames(getHighestActivityCourse()))
                    .append(Messages.LOW_ACTIVITY.getMessage())
                    .append(getCoursesNames(getLowestActivityCourse()))
                    .append(Messages.EASY_COURSE.getMessage())
                    .append(getCoursesNames(getEasiestCourse()))
                    .append(Messages.HARD_COURSE.getMessage())
                    .append(getCoursesNames(getHardestCourse()));
        } else {
            result
                    .append(Messages.NO_DATA.getMessage())
                    .append(Messages.LEAST_POPULAR.getMessage())
                    .append(Messages.NO_DATA.getMessage())
                    .append(Messages.HIGH_ACTIVITY.getMessage())
                    .append(Messages.NO_DATA.getMessage())
                    .append(Messages.LOW_ACTIVITY.getMessage())
                    .append(Messages.NO_DATA.getMessage())
                    .append(Messages.EASY_COURSE.getMessage())
                    .append(Messages.NO_DATA.getMessage())
                    .append(Messages.HARD_COURSE.getMessage())
                    .append(Messages.NO_DATA.getMessage());
        }
        return result.toString();
    }

    String getCompletionState(String input) {
        StringBuilder result = new StringBuilder();
        int indexOfCourse = IntStream
                .range(0, coursesList.size())
                .filter(index -> coursesList.get(index).getCourseName().equalsIgnoreCase(input))
                .findFirst().orElse(-1);

        if (indexOfCourse >= 0) {
            Courses foundCourse = coursesList.get(indexOfCourse);
            result
                    .append(foundCourse.getCourseName())
                    .append("\nid     points completed\n");

            List<StudentScoreByCourse> studentScore = new ArrayList<>();
            for (Student student :
                    studentList) {
                int studentId = student.getId();
                if (foundCourse.getStudentScore(studentId) > 0) {
                    studentScore.add(new
                            StudentScoreByCourse(studentId,
                            foundCourse.getStudentScore(studentId),
                            foundCourse.getCourseCompletionByStudent(studentId)));
                }
            }
            studentScore
                    .sort(Comparator.comparing(StudentScoreByCourse::getScore)
                    .reversed());
            studentScore.forEach(s -> result
                    .append(s.getStudentId())
                    .append(" ")
                    .append(s.getScore())
                    .append("        ")
                    .append(s.getPercentOfCompletion())
                    .append("%\n"));
        } else {
            result.append(Messages.COURSE_NAME_ERROR.getMessage());
        }
        return result.toString();
    }

    public String getCompletionists() {
        int completionistCount = 0;
        StringBuilder result = new StringBuilder();
        for (Student student:
             studentList) {
            int coursesFinished = 0;
            for (Courses course :
                    coursesList) {
                if (course.getStudentScore(student.getId()) == course.getCourseMaxPoints() && !course.isCompletedByStudent()) {
                    result.append(String.format(
                            Messages.COMPLETION_NOTIFICATION.getMessage(),
                            student.getEmail(),
                            student.getFirstName(),
                            student.getLastName(),
                            course.getCourseName()));
                    coursesFinished++;
                    course.setCompletedByStudent();
                }
            }
            if (coursesFinished > 0) completionistCount++;
        }
        result.append(String.format(Messages.TOTAL_NOTIFIED.getMessage(), completionistCount));
        return result.toString();
    }

}
