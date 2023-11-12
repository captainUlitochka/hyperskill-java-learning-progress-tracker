package tracker;


public class Student {

    private String firstName;
    private String lastName;
    private String email;

    public String getId() {
        return String.valueOf(email.hashCode());
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (isCorrectName(firstName)) this.firstName = firstName;
        else throw new IllegalArgumentException(String.format(Messages.ADD_ERROR.getMessage(), "first name"));
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (isCorrectName(lastName)) this.lastName = lastName;
        else throw new IllegalArgumentException(String.format(Messages.ADD_ERROR.getMessage(), "last name"));
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (isCorrectEmail(email)) this.email = email;
        else throw new IllegalArgumentException(String.format(Messages.ADD_ERROR.getMessage(), "email"));
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public Student(String userInput) {
        if (userInput.chars().filter(c -> c == (int) ' ').count() > 1) {
            int firstSpaceIndex = userInput.indexOf(" ");
            int lastSpaceIndex = userInput.lastIndexOf(" ");
            setFirstName(userInput.substring(0, firstSpaceIndex));
            setLastName(userInput.substring(firstSpaceIndex + 1, lastSpaceIndex));
            setEmail(userInput.substring(lastSpaceIndex + 1));

        } else {
            throw new IllegalArgumentException(String.format(Messages.ADD_ERROR.getMessage(), "credentials"));
        }

    }

    static boolean isCorrectName(String name) {
        String nameRegex = "^[A-Za-z]+((\\s)?((['\\-.])?([A-Za-z])+))*$";
        if (name.length() > 1 && name.matches(nameRegex)) return true;
        else return false;
    }

    static boolean isCorrectEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+\\.+[a-zA-Z0-9.-]+$";
        if (email.matches(emailRegex)) return true;
        return false;
    }

}
