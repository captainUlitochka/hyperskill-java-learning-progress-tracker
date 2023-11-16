package tracker;

import java.util.*;

public class Tracker {
    Scanner scanner = new Scanner(System.in);
    Journal journal = new Journal();

    private static final String EXIT = "exit";
    private static final String ADD_STUDENTS = "add students";
    private static final String ADD_POINTS = "add points";
    private static final String LIST = "list";
    private static final String FIND = "find";
    private static final String BACK = "back";
    private static final String NULL = "null";


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
                    case EXIT -> {
                        System.out.println(Messages.EXIT.getMessage());
                        return;
                    }
                    case ADD_STUDENTS -> processStudentInput();
                    case ADD_POINTS -> processPointsInput();
                    case LIST -> printStudents();
                    case FIND -> findStudentEntry();
                    case BACK -> System.out.println(Messages.EXIT_SUGGESTION.getMessage());
                    case NULL -> System.out.println(Messages.NO_INPUT.getMessage());
                    default -> System.out.println(Messages.COMMAND_ERROR.getMessage());
                }
            }
        }
    }

    private void processPointsInput() {
        System.out.println(Messages.ADD_POINTS.getMessage());
        String input = scanner.nextLine();
        while (!input.equals("back")) {
            if (journal.addPoints(input)) {
                System.out.println(Messages.POINTS_UPDATED.getMessage());
            }
            input = scanner.nextLine();
        }

    }

    private void findStudentEntry() {
        System.out.println(Messages.FIND_STUDENTS.getMessage());
        String input = scanner.nextLine();
        while (!input.equals(BACK)) {
            System.out.println(journal.getStudentScores(input));
            input = scanner.nextLine();
        }
    }

    private void processStudentInput() {
        System.out.println(Messages.ADD_STUDENT.getMessage());
        String input = scanner.nextLine();
        while (!input.equals(BACK)) {
            journal.addStudent(input);
            input = scanner.nextLine();
        }
        System.out.printf(Messages.ADD_COMPLETE.getMessage(), journal.studentList.size());
    }

    private void printStudents() {
        System.out.println(journal.getListOfStudents());
    }
}
