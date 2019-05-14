import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

/**
 * The 'Backend' that the SIS class interfaces with.
 *
 * @author Russell Harvey
 */

public class Backend {

    /** The database of courses. */
    private CourseDB courseDB;

    /** The database of users. */
    private UserDB userDB;

    /**
     * Creates Backend initializing course and user data.
     * @param courseFile
     * @param professorFile
     * @param studentFile
     */
    public Backend (String courseFile, String professorFile, String studentFile) throws FileNotFoundException {
        this.courseDB = new CourseDB();
        this.userDB = new UserDB();
        initializeCourseDB(courseFile);
        initializeUserDB(professorFile, studentFile);
    }

    /**
     * Adds a user to a collection of courses.
     * @param user
     * @param courseIds
     */
    private void addCourses (User user, String[] courseIds){
        for (int i = 0; i < courseIds.length; i++) {
            int id = Integer.parseInt(courseIds[i]);
            Course course = getCourse(id);
            if (user.getType().equals(User.UserType.STUDENT)) {
                course.addStudent(user.getUsername());
                user.addCourse(course);
            } else {
                course.addProfessor(user.getUsername());
                user.addCourse(course);
            }
        }
    }

    /**
     * Checks if a course exists.
     * @param id
     * @return boolean
     */
    public boolean courseExists (int id) {
        return courseDB.hasKey(id);
    }

    /**
     * Enroll a student in a course.
     * @param username
     * @param courseId
     * @return boolean
     */
    public boolean enrollStudent (String username, int courseId) {
        User usr = userDB.getValue(username);
        Student stu = null;
        if (usr instanceof Student) {
            stu = (Student) usr;
        } else {
            return false;
        }
        Course crse = courseDB.getValue(courseId);
        if (crse.getStudents().contains(username)) {
            return false;
        } else {
            crse.addStudent(username);
            stu.addCourse(crse);
            return true;
        }
    }

    /**
     * Get all courses in the database.
     * @return Collection<Course>
     */
    public Collection<Course> getAllCourses () {
        return courseDB.getAllValues();
    }

    /**
     * Get all users in the database.
     * @return Collection<User>
     */
    public Collection<User> getAllUsers () {
        return userDB.getAllValues();
    }

    /**
     * Get a course by ID.
     * @param id
     * @return Course
     */
    public Course getCourse (int id) {
        return courseDB.getValue(id);
    }

    /**
     * Get the courses for a particular user.
     * @param username
     * @return Collection<User>
     */
    public Collection<Course> getCoursesUser (String username) {
        if (userDB.hasKey(username)) {
            User usr = userDB.getValue(username);
            return usr.getCourses();
        } else {
            return null;
        }
    }

    /**
     * Initializes the course database.
     * @param courseFile
     */
    private void initializeCourseDB (String courseFile) throws FileNotFoundException {
        try (Scanner in = new Scanner(new File(courseFile))) {
            while (in.hasNext()) {
                String[] fields = in.nextLine().split(",");
                int tempId = Integer.parseInt(fields[0]);
                String tempName = fields[1];
                int tempLevel = Integer.parseInt(fields[2]);
                Course temp = new Course(tempId, tempName, tempLevel);
                courseDB.addValue(temp);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the user database.
     * @param professorFile
     * @param studentFile
     */
    private void initializeUserDB (String professorFile, String studentFile) throws FileNotFoundException {
        try (Scanner in = new Scanner(new File(professorFile))) {
            while (in.hasNext()) {
                String[] fields = in.nextLine().split(",");
                String name = fields[0];
                Professor temp = new Professor(name);
                String[] ids = Arrays.copyOfRange(fields, 1, fields.length);
                addCourses(temp, ids);
                userDB.addValue(temp);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try (Scanner in = new Scanner(new File(studentFile))) {
            while (in.hasNext()) {
                String[] fields = in.nextLine().split(",");
                String name = fields[0];
                Student temp = new Student(name);
                String[] ids = Arrays.copyOfRange(fields, 1, fields.length);
                addCourses(temp, ids);
                userDB.addValue(temp);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check whether a username belongs to a student.
     * @param username
     * @return boolean
     */
    public boolean isStudent (String username) {
        User usr = userDB.getValue(username);
        if (usr.getType().equals(User.UserType.STUDENT)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Unenroll a student in a course.
     * @param username
     * @param courseId
     * @return boolean
     */
    public boolean unenrollStudent (String username, int courseId) {
        User usr = userDB.getValue(username);
        Student stu = null;
        if (usr instanceof Student) {
            stu = (Student) usr;
        } else {
            return false;
        }
        Course crse = courseDB.getValue(courseId);
        crse.removeStudent(username);
        stu.removeCourse(crse);
        return true;
    }

    /**
     * Checks if a username exists.
     * @param username
     * @return boolean
     */
    public boolean userExists (String username){
        return userDB.hasKey(username);
    }
}
