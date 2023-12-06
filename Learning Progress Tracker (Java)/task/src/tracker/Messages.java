package tracker;

public enum Messages {
    TITLE("Learning Progress Tracker\n"),
    EXIT_SUGGESTION("Enter 'exit' to exit the program."),
    EXIT("Bye!\n"),
    NO_INPUT("No input.\n"),
    EMPTY_STUDENTS_LIST("No students found\n"),
    COMMAND_ERROR("Unknown command!\n"),
    COURSE_NAME_ERROR("Unknown course!\n"),
    ADD_STUDENT("Enter student credentials or 'back' to return:\n"),
    ADD_SUCCESSFUL("The student has been added.\n"),
    ADD_COMPLETE("Total %d students have been added.\n"),
    ADD_POINTS("Enter an id and points or 'back' to return\n"),
    ADD_ERROR("Incorrect %s.\n"),
    EMAIL_EXISTS("This email is already taken.\n"),
    INCORRECT_STUDENT_ID("No student is found for id=%s\n"),
    INCORRECT_POINTS_FORMAT("Incorrect points format\n"),
    POINTS_UPDATED("Points updated\n"),
    FIND_STUDENTS("Enter an id or 'back' to return"),
    LIST_OF_STUDENTS("Students:\n"),
    STUDENT_DATA("%s points: Java=%d; DSA=%d; Databases=%d; Spring=%d\n"),
    SHOW_STATS("\nType the name of a course to see details or 'back' to quit"),
    MOST_POPULAR("Most popular: "),
    LEAST_POPULAR("Least popular: "),
    HIGH_ACTIVITY("Highest activity: "),
    LOW_ACTIVITY("Lowest activity: "),
    EASY_COURSE("Easiest course: "),
    HARD_COURSE("Hardest course: "),
    NO_DATA("n/a\n"),
    COMPLETION_NOTIFICATION("To: %s%nRe: Your Learning Progress%nHello, %s %s! You have accomplished our %s course!%n"),
    TOTAL_NOTIFIED("Total %d students have been notified.");


    private final String message;

    Messages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
