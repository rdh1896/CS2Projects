import java.util.Comparator;

/**
 * Comparator for the Professor Class.
 *
 * @author Russell Harvey (rdh1896@rit.edu)
 */

public class ProfCourseComparator implements Comparator<Course> {

    /**
     * Takes two courses and compares them first by level then
     * by name.
     * @param o1
     * @param o2
     * @return int
     */
    @Override
    public int compare(Course o1, Course o2) {
        if (o1.getLevel() == o2.getLevel()) {
            return o1.getName().compareTo(o2.getName());
        } else {
            return o1.getLevel() - o2.getLevel();
        }
    }
}
