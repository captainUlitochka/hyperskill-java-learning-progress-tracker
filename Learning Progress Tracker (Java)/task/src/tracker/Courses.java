package tracker;

public enum Courses {
    JAVA("Java"),
    DSA("Data Structures and Algorithms"),
    DB("Databases"),
    Spring("Spring");

    private final String courseName;

    Courses(String name) {
        this.courseName = name;
    }

    public String getCourseName() {
        return courseName;
    }
}
