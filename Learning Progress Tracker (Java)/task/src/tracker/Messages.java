package tracker;

public enum Messages {
    TITLE("Learning Progress Tracker\n"),
    EXIT_SUGGESTION("Enter 'exit' to exit the program."),
    EXIT("Bye!\n"),
    NO_INPUT("No input.\n"),
    NO_STUDENTS("No students found\n"),
    COMMAND_ERROR("Unknown command!\n"),
    ADD_STUDENT("Enter student credentials or 'back' to return:\n"),
    ADD_SUCCESSFUL("The student has been added.\n"),
    ADD_COMPLETE("Total %d students have been added.\n"),
    ADD_PONTS("Enter an id and points or 'back' to return\n"),
    ADD_ERROR("Incorrect %s.\n"),
    EMAIL_EXISTS("This email is already taken.\n"),
    INCORRECT_STUDENT_ID("No student is found for id=%s\n"),
    INCORRECT_POINTS_FORMAT("Incorrect points format\n"),
    POINTS_UPDATED("Points updated\n"),
    FIND_STUDENTS("Enter an id or 'back' to return"),
    STUDENT_DATA("id points: Java=%d; DSA=%d; Databases=%d; Spring=%d\n");


    private final String message;

    Messages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
