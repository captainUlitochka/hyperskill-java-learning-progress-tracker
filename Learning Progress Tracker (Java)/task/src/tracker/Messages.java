package tracker;

public enum Messages {
    TITLE("Learning Progress Tracker\n"),
    EXIT_SUGGESTION("Enter 'exit' to exit the program."),
    EXIT("Bye!\n"),
    NO_INPUT("No input.\n"),
    COMMAND_ERROR("Unknown command!\n"),
    ADD_ACTION("Enter student credentials or 'back' to return:\n"),
    ADD_SUCCESSFUL("The student has been added.\n"),
    ADD_COMPLETE("Total %d students have been added.\n"),
    ADD_ERROR("Incorrect %s.\n");
    private final String message;

    Messages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
