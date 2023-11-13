package tracker;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Tracker {
    Map<Integer, Student> students = new LinkedHashMap<>();

    Scanner scanner = new Scanner(System.in);

    public void startTracker() {
        System.out.println(Messages.TITLE.getMessage());
        printMenu();
    }

    void printMenu() {

        String command;
        while (scanner.hasNextLine()) {
            command = scanner.nextLine();
            if (command.trim().isEmpty()) {
                System.out.println(Messages.NO_INPUT.getMessage());
            } else {
                switch (command) {
                    case "exit" -> {
                        System.out.println(Messages.EXIT.getMessage());
                        return;
                    }
                    case "add students" -> processStudentInput();
                    case "add points" -> processPointsInput();
                    case "list" -> printStudents();
                    case "back" -> System.out.println(Messages.EXIT_SUGGESTION.getMessage());
                    case "null" -> System.out.println(Messages.NO_INPUT.getMessage());
                    default -> System.out.println(Messages.COMMAND_ERROR.getMessage());
                }
            }
        }
    }

    void processPointsInput() {
        System.out.println(Messages.ADD_PONTS.getMessage());
        String input = scanner.nextLine();
        while (!input.equals("back")) {
            addPoints(input);
            input = scanner.nextLine();
        }
        System.out.println(Messages.POINTS_UPDATED.getMessage());
    }

    boolean addPoints(String input) {
        String[] submission = input.split(" ");
        for (Courses c: Courses.values()) {
            int i = 1;
            Course course = new Course(c.name()); //TODO: Баллы не запоминаются
            course.setStudentScore(getStudentById(Integer.parseInt(submission[0])), Integer.parseInt(submission[i]));
        }
        return false;
    }

    Student getStudentById(int id) {
        for (Student s : students.values()) {
           if (s.getId() == id) {
               return s;
           }
        }
        System.out.println(Messages.NO_STUDENTS);
        return null;
    }

    void processStudentInput() {
        System.out.println(Messages.ADD_STUDENT.getMessage());
        String input = scanner.nextLine();
        while (!input.equals("back")) {
            addStudent(input);
            input = scanner.nextLine();
        }
        System.out.printf(Messages.ADD_COMPLETE.getMessage(), students.size());
    }

    public boolean addStudent(String input) {
        try {
            Student student = new Student(input);
            if (isEmailUnique(student.getEmail())) {
                students.put(student.getId(), student);
                System.out.println(Messages.ADD_SUCCESSFUL.getMessage());
                return true;
            }
           else System.out.println(Messages.EMAIL_EXISTS.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    boolean isEmailUnique(String email) {
        return !students.containsKey(email.hashCode());
    }

    void printStudents() {
        if (students.isEmpty()) {
            System.out.println(Messages.NO_STUDENTS.getMessage());
        } else {
            System.out.println("Students:\n");
            students.keySet().forEach(System.out::println);
        }
    }
}
