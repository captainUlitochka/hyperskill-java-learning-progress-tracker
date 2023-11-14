package tracker;

import java.util.*;

public class Tracker {
    Map<Integer, Student> students = new LinkedHashMap<>();

    List<Courses> coursesList = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);

    public void startTracker() {
        System.out.println(Messages.TITLE.getMessage());
        coursesList.addAll(List.of(Courses.values()));
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
                    case "find" -> findStudentEntry();
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
        //String apointsRegex = "(?:\\d+ ?){5}";
        String pointsRegex = "^[0-9]+ +[0-9]+ +[0-9]+ +[0-9]+ +[0-9]+$";
        if (input.matches(pointsRegex)) {
            String[] submission = input.split(" ");
            String studentId = submission[0];
            int i = 1;
            if (getStudentById(studentId) != null) {
                Student student = getStudentById(studentId);

                for (Courses c : coursesList) {
                    c.setStudentScore(student, Integer.parseInt(submission[i]));
                    i++;
                }
                return true;
            }
        } else System.out.println(Messages.INCORRECT_POINTS_FORMAT.getMessage());
        return false;
    }

    void findStudentEntry() {//TODO:
        System.out.println(Messages.FIND_STUDENTS.getMessage());
        String input = scanner.nextLine();

        while (!input.equals("back")) {
            input = scanner.nextLine();
            if (getStudentById(input) != null) {
                int id = Integer.parseInt(input); //TODO:Пока не входит в эту ветку. Надо понять почему :(
                System.out.println(String.format(Messages.STUDENT_DATA.getMessage(),
                        coursesList.get(0).getStudentScore(id),
                        coursesList.get(1).getStudentScore(id),
                        coursesList.get(2).getStudentScore(id),
                        coursesList.get(3).getStudentScore(id)));
            }
        }
    }

    Student getStudentById(String input) {
        String idRegex = "^[0-9]*$";
        if (input.matches(idRegex)) {
            for (Student s : students.values()) {
                if (s.getId() == Integer.parseInt(input)) {
                    return s;
                }
            }
        }
        System.out.println(String.format(Messages.INCORRECT_STUDENT_ID.getMessage(), input));
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
            System.out.println(Messages.EMPTY_STUDENTS_LIST.getMessage());
        } else {
            System.out.println("Students:\n");
            students.keySet().forEach(System.out::println);
        }
    }
}
