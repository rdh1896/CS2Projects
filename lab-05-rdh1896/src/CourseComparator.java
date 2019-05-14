import java.util.Comparator;

/**
 * A class that overrides the natural order comparison of courses to order
 * them alphabetically by course name.
 *
 * @author Russell Harvey (rdh1896@rit.edu)
 */
public class CourseComparator implements Comparator<Course> {

    /**
     * Compares two Courses and returns them in alphabetical order.
     * @param o1
     * @param o2
     * @return int
     */
    @Override
    public int compare(Course o1, Course o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
