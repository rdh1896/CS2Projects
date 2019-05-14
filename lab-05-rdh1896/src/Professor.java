import java.util.Comparator;

/**
 * Professor class for SIS, child of User.
 *
 * @author Russell Harvey (rdh1896@rit.edu)
 */

public class Professor extends User {

    /**
     * Creates a new professor.
     * @param username
     */
    public Professor(String username) {
        super(username, UserType.PROFESSOR, new ProfCourseComparator());
    }

}
