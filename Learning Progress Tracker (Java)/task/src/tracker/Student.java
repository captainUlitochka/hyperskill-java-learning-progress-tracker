package tracker;

public class Student {

    public static boolean checkCredentials(String data) {
        if (data.chars().filter(c -> c == (int) ' ').count() > 1) {
            String[] credentials = data.split(" ");
            if (isCorrectName(credentials[0], credentials[1]) && isCorrectEmail(credentials[credentials.length - 1])) {
                System.out.println(Messages.ADD_SUCCESSFUL.getMessage());
                return true;
            }
        } else {
            System.out.printf(Messages.ADD_ERROR.getMessage(), "credentials");
        }
        return false;

    }

    static boolean isCorrectName(String firstName, String lastName) {
        String nameRegex = "^[A-Za-z]+((\\s)?((['\\-.])?([A-Za-z])+))*$";
        if (firstName.length() > 1 && lastName.length() > 1 && firstName.matches(nameRegex) && lastName.matches(nameRegex)) {
            return true;
        }
        if (!lastName.matches(nameRegex) || lastName.length() <= 1) {
            System.out.printf(Messages.ADD_ERROR.getMessage(), "last name");
        }
        if (firstName.length() <= 1 || !firstName.matches(nameRegex)) {
            System.out.printf(Messages.ADD_ERROR.getMessage(), "first name");
        }
        return false;
    }

    static boolean isCorrectEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+\\.+[a-zA-Z0-9.-]+$";
        if (email.matches(emailRegex)) {
            return true;
        }
        System.out.printf(Messages.ADD_ERROR.getMessage(), "email");
        return false;
    }
}
