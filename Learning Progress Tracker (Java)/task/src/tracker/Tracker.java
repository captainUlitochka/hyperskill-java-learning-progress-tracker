package tracker;

import java.util.Scanner;

public class Tracker {
    int studentsCounter;

    Scanner scanner = new Scanner(System.in);

    public void startTracker() {
        printMessage(Messages.TITLE.getMessage());
        printMenu();
    }

    void printMessage(String msg) {
        System.out.println(msg);
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
                    case "back" -> System.out.println(Messages.EXIT_SUGGESTION.getMessage());
                    case "null" -> System.out.println(Messages.NO_INPUT.getMessage());
                    default -> System.out.println(Messages.COMMAND_ERROR.getMessage());
                }
            }
        }
    }

    void processInput() {
        System.out.printf(Messages.ADD_ACTION.getMessage(), "email");
        String input = scanner.nextLine();
        while (!input.equals("back")) {
            if (Student.checkCredentials(input)) studentsCounter++;
            input = scanner.nextLine();
        }
        System.out.printf(Messages.ADD_COMPLETE.getMessage(), studentsCounter);
    }
}
