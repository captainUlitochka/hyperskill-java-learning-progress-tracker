package tracker;

import java.util.*;
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


    //TODO: перенести заголовки в другое место
    //TODO: для меньших значений проверять вычитание меньшего списка из большего

    boolean checkIfNoScores() {
        int activity = 0;
        for (Courses c:
             coursesList) {
            if (c.getSubmissionsCount() != 0) {
                activity++;
            }
        }
        if (activity > 0) return false;
        else return true;
    }

    String getMostPopularCourse() {
        List<Courses> sortedList = new ArrayList<>(coursesList);
        StringBuilder result = new StringBuilder(Messages.MOST_POPULAR.getMessage());
        if (!checkIfNoScores()) {
            sortedList.sort(Comparator.comparing(Courses::getStudentsCount).reversed());
            result.append(getPopularCoursesString(sortedList));
        } else {
            result.append(Messages.NO_DATA.getMessage());
        }
        return result.toString();
    }


    String getLeastPopularCourse() {
        List<Courses> sortedList = new ArrayList<>(coursesList);
        StringBuilder result = new StringBuilder(Messages.LEAST_POPULAR.getMessage());
        if (!checkIfNoScores()) {
            sortedList.sort(Comparator.comparing(Courses::getStudentsCount));
            result.append(getPopularCoursesString(sortedList));
        } else {
            result.append(Messages.NO_DATA.getMessage());
        }
        return result.toString();
    }

    private StringBuilder getPopularCoursesString(List<Courses> sortedCoursesList) {
        StringBuilder result = new StringBuilder(sortedCoursesList.get(0).getCourseName());
        int i = 1;
        while ( i < (sortedCoursesList.size()) && sortedCoursesList.get(i).getStudentsCount() == sortedCoursesList.get(0).getStudentsCount()) {
            result.append(", ")
                    .append(sortedCoursesList.get(i).getCourseName());
            i++;
        }

        result.append("\n");
        return result;
    }

    String getEasiestCourse() {
        List<Courses> sortedList = new ArrayList<>(coursesList);
        StringBuilder result = new StringBuilder(Messages.EASY_COURSE.getMessage());
        if (!checkIfNoScores()) {
            sortedList.sort(Comparator.comparing(Courses::getAverageGrade).reversed());
            result.append(getCoursesByComplexityString(sortedList));
        } else {
            result.append(Messages.NO_DATA.getMessage());
        }
        return result.toString();
    }

    String getHardestCourse() {
        List<Courses> sortedList = new ArrayList<>(coursesList);
        StringBuilder result = new StringBuilder(Messages.HARD_COURSE.getMessage());
        if (!checkIfNoScores()) {
            sortedList.sort(Comparator.comparing(Courses::getAverageGrade));
            result.append(getCoursesByComplexityString(sortedList));
        } else {
            result.append(Messages.NO_DATA.getMessage());
        }
        return result.toString();
    }

    private StringBuilder getCoursesByComplexityString(List<Courses> sortedCoursesList) {
        StringBuilder result = new StringBuilder(sortedCoursesList.get(0).getCourseName());
        int i = 1;
        while ( i < (sortedCoursesList.size()) && sortedCoursesList.get(i).getAverageGrade() == sortedCoursesList.get(0).getAverageGrade()) {
            result.append(", ")
                    .append(sortedCoursesList.get(i).getCourseName());
            i++;
        }

        result.append("\n");
        return result;
    }

    String getHighestActivityCourse() {
        List<Courses> sortedList = new ArrayList<>(coursesList);
        StringBuilder result = new StringBuilder(Messages.HIGH_ACTIVITY.getMessage());
        if (!checkIfNoScores()) {
            sortedList.sort(Comparator.comparing(Courses::getSubmissionsCount).reversed());
        result.append(getCoursesByActivityString(sortedList));
        } else {
            result.append(Messages.NO_DATA.getMessage());
        }
        return result.toString();
    }

    String getLowestActivityCourse() {
        List<Courses> sortedList = new ArrayList<>(coursesList);
        StringBuilder result = new StringBuilder(Messages.LOW_ACTIVITY.getMessage());
        if (!checkIfNoScores()) {
            sortedList.sort(Comparator.comparing(Courses::getSubmissionsCount));
            result.append(getCoursesByActivityString(sortedList));
        } else {
            result.append(Messages.NO_DATA.getMessage());
        }
        return result.toString();
    }

    private StringBuilder getCoursesByActivityString(List<Courses> sortedCoursesList) {
        StringBuilder result = new StringBuilder(sortedCoursesList.get(0).getCourseName());
        int i = 1;
        while ( i < (sortedCoursesList.size()) && sortedCoursesList.get(i).getSubmissionsCount() == sortedCoursesList.get(0).getSubmissionsCount()) {
            result.append(", ")
                    .append(sortedCoursesList.get(i).getCourseName());
            i++;
        }
        result.append("\n");
        return result;
    }

    String getCompletionState(String input) {
        //TODO: список отсортировать по проценту прохождения
        StringBuilder result = new StringBuilder();
        int indexOfCourse = IntStream
                .range(0, coursesList.size())
                .filter(index -> coursesList.get(index).getCourseName().equalsIgnoreCase(input))
                .findFirst().orElse(-1);

        if (indexOfCourse >= 0) {
            result
                    .append(coursesList.get(indexOfCourse).getCourseName())
                    .append("\nid     points completed\n");
            for (Student student :
                    studentList) {
                int studentId = student.getId();
                result.append(studentId)
                        .append(" ")
                        .append(coursesList.get(indexOfCourse).getStudentScore(studentId))
                        .append("        ")
                        .append(coursesList.get(indexOfCourse).getCourseCompletionByStudent(studentId))
                        .append("%\n");
            }
        } else {
            result.append(Messages.COURSE_NAME_ERROR.getMessage());
        }
        return result.toString();
    }

}
