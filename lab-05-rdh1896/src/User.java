import java.util.Collections;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.Collection;

/**
 * User implementation for the SIS.
 *
 * @author Russell Harvey (rdh1896@rit.edu)
 */

public class User implements Comparable<User> {

    /** Username for the user. */
    private String username;

    /** Type of user. */
    private UserType type;

    /** Courses attached to user. */
    private TreeSet<Course> courses;

    /** The types for user. */
    public enum UserType {
        PROFESSOR,
        STUDENT
    }

    /**
     * Constructor for the User class.
     * @param username
     * @param type
     * @param comp
     */
    public User (String username, UserType type, Comparator<Course> comp) {
        this.username = username;
        this.type = type;
        this.courses = new TreeSet<>(comp);
    }

    /**
     * Checks if two users are equal to each other.
     * @param o
     * @return boolean
     */
    @Override
    public boolean equals (Object o){
        if (o instanceof User) {
            User other = (User) o;
            return this.getUsername().equals(other.getUsername());
        } else {
            return false;
        }
    }

    /**
     * Gets the hash code for the user.
     * @return int
     */
    @Override
    public int hashCode () {
        return getUsername().hashCode();
    }

    /**
     * User's toString() implementation.
     *
     * @return String
     */
    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", type=" + type +
                ", courses=" + courses +
                '}';
    }

    /**
     * Compares two user classes to one another by username.
     * @param o
     * @return int
     */
    @Override
    public int compareTo(User o) {
        return this.getUsername().compareTo(o.username);
    }

    /** Adds a course to the users courses. */
    public boolean addCourse (Course course) {
        if (!(getCourses().contains(course))) {
            courses.add(course);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Removes a course from the users courses.
     * @param course
     * @return boolean
     */
    public boolean removeCourse (Course course) {
        if (!(getCourses().contains(course))) {
            return false;
        } else {
            courses.remove(course);
            return true;
        }
    }

    /**
     * Gets the database of a user's courses.
     * @return Collection<Course>
     */
    public Collection<Course> getCourses () {
        return courses;
    }

    /**
     * Gets the type of the user.
     * @return UserType
     */
    public UserType getType () {
        return type;
    }

    /**
     * Gets the username of a user.
     * @return String.
     */
    public String getUsername () {
        return username;
    }
}
