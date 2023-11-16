package tracker;

import java.util.ArrayList;
import java.util.List;

public class Journal {
    int nextStudentId;
    List<Student> studentList;
    List<Courses> coursesList;

    public Journal() {
        studentList = new ArrayList<>();
        coursesList = new ArrayList<>();
        coursesList.addAll(List.of(Courses.values()));
        nextStudentId = 10000;
    }

    public boolean addStudent(String input) {
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
        if (getStudentById(submission[0]) != null) {
            if (input.matches(pointsRegex)) {
                for (Courses c : coursesList) {
                    c.setStudentScore(
                            Integer.parseInt(submission[0]),
                            Integer.parseInt(submission[pointCounter]));
                    pointCounter++;
                }
                return true;
            } else System.out.println(Messages.INCORRECT_POINTS_FORMAT.getMessage());
        }
        return false;
    }

    Student getStudentById(String input) {
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

    String getListOfStudents() {
        StringBuilder output = new StringBuilder();
        if (!studentList.isEmpty()) {
            output.append(Messages.LIST_OF_STUDENTS.getMessage());
            for (Student s :
                    studentList) {
                output.append(s.getId()).append("\n");
            }
            return output.toString();
        }
        return output.append(Messages.EMPTY_STUDENTS_LIST.getMessage()).toString();
    }

    boolean isEmailUnique(String email) {
        if (!studentList.isEmpty()) {
            for (Student studentModel :
                    studentList) {
                if (studentModel.getEmail().equals(email)) {
                    return false;
                }
            }
        }
        return true;
    }

}
