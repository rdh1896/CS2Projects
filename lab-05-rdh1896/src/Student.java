import java.util.Comparator;

/**
 * Student class for SIS, child of User.
 *
 * @author Russell Harvey (rdh1896@rit.edu)
 */
public class Student extends User {

    /**
     * Creates a new student.
     * @param username
     */
    public Student(String username) {
        super(username, UserType.STUDENT, new CourseComparator());
    }

}
