import java.util.*;

/**
 * Course database for the SIS.
 *
 * @author Russell Harvey (rdh1896@rit.edu)
 */

public class CourseDB implements DB<Integer, Course> {

    /** Course storage. */
    private HashMap<Integer,Course> courses;

    /** Constructor for CourseDB */
    public CourseDB() {
        this.courses = new HashMap<>();
    }

    /**
     * Add a value to the database.
     * @param value
     * @return Course
     */
    @Override
    public Course addValue(Course value) {
        if (courses.containsKey(value.getId())) {
            Course temp = courses.get(value.getId());
            courses.replace(value.getId(), value);
            return temp;
        } else {
            courses.put(value.getId(), value);
            return null;
        }
    }

    /**
     * Gets all values in the database.
     * @return Collection<Course>
     */
    @Override
    public Collection<Course> getAllValues() {
        ArrayList<Course> crs = new ArrayList<>();
        Iterator<Course> iter = courses.values().iterator();
        while (iter.hasNext()) {
            crs.add(iter.next());
        }
        Collections.sort(crs);
        return crs;
    }

    /**
     * Get the value for a corresponding key.
     * @param key
     * @return Course
     */
    @Override
    public Course getValue(Integer key) {
        return courses.get(key);
    }

    /**
     * Indicates if the key is in the database.
     * @param key
     * @return boolean
     */
    @Override
    public boolean hasKey(Integer key) {
        return courses.containsKey(key);
    }
}
