package tracker;

import java.util.LinkedHashMap;
import java.util.Map;

public class Student {

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    static Map<String,String> students = new LinkedHashMap<>();

    public static boolean checkCredentials(String data) {
        if (data.chars().filter(c -> c == (int) ' ').count() > 1) {
            String[] credentials = data.split(" ");
            if (isCorrectName(credentials[0], credentials[1]) && isCorrectEmail(credentials[credentials.length - 1])) {
                addStudent(credentials[credentials.length - 1]);
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
        if (email.matches(emailRegex) && isEmailUnique(email)) {
            return true;
        } else if (email.matches(emailRegex) && !isEmailUnique(email)) {
            System.out.println(Messages.EMAIL_EXISTS.getMessage());
            return false;
        }
        System.out.printf(Messages.ADD_ERROR.getMessage(), "email");
        return false;
    }

    static boolean isEmailUnique(String email) {
        if (!students.isEmpty() && students.containsValue(email)) {
            return false;
        } else if (students.isEmpty()) {
            return true;
        } else {
            return true;
        }
    }

    static void addStudent(String email) {
        students.put(String.valueOf(email.hashCode()), email);
        //students.entrySet().forEach(System.out::println);
    }
}
