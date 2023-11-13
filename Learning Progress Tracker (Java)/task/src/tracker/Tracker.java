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
                    case "add students" -> processInput();
                    case "list" -> printStudents();
                    case "back" -> System.out.println(Messages.EXIT_SUGGESTION.getMessage());
                    case "null" -> System.out.println(Messages.NO_INPUT.getMessage());
                    default -> System.out.println(Messages.COMMAND_ERROR.getMessage());
                }
            }
        }
    }

    void processInput() {
        System.out.printf(Messages.ADD_ACTION.getMessage());
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
