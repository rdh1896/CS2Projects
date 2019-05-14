import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Scanner;

/**
 * The main program and "front end" for the Student Information System.  It
 * is run on the command line in two ways:<br>
 * <br>
 * $ java SIS course-file professor-file student-file<br>
 * $ java SIS course-file professor-file student-file input-file<br>
 * <br>
 * Its job is to create the backend by passing it the course, professor and
 * student input files (see Backend class documentation), and then run
 * an loop reading input (either from standard input (first way of running),
 * or from a file of input commands (second way of running)).<br>
 * <br>
 * The commands the system accepts can be found by running the 'help' command:<br>
 * <br>
 * course {id}: list a course<br>
 * courses: list all courses (by course id)<br>
 * enroll {username} {id}: enroll a student in a course<br>
 * help: this message<br>
 * professor {username}: list courses taught by professor (by course level then by course name)<br>
 * student {username}: list courses taken by student (by course name)<br>
 * unenroll {username} {id}: unenroll a student from a course<br>
 * users: list all users (alphabetically by username) <br>
 * quit: quit SIS<br>
 * <br>
 *
 * @author Sean Strout @ RITCS
 * @author Russell Harvey
 */
public class SIS {
    /** the course command */
    public final static String COURSE = "course";
    /** the courses command */
    public final static String COURSES = "courses";
    /** the enroll command */
    public final static String ENROLL = "enroll";
    /** the help command */
    public final static String HELP = "help";
    /** the professor command */
    public final static String PROFESSOR = "professor";
    /** the student command */
    public final static String STUDENT = "student";
    /** the unenroll command */
    public final static String UNENROLL = "unenroll";
    /** the users command */
    public final static String USERS = "users";
    /** the quit command */
    public final static String QUIT = "quit";
    /** The Backend */
    private Backend backend;

    /**
     * Create the backend.
     *
     * @param courseFile the course file
     * @param professorFile the professor file
     * @param studentFile the student file
     * @throws FileNotFoundException if any of the files cannot be found
     */
    public SIS(String courseFile, String professorFile, String studentFile) throws FileNotFoundException {
        this.backend = new Backend(courseFile, professorFile, studentFile);
    }

    /**
     * A helper method for displaying the help message.
     */
    private void helpMessage() {
        System.out.println("course {id}: list a course");
        System.out.println("courses: list all courses (by course id)");
        System.out.println("enroll {username} {id}: enroll a student in a course");
        System.out.println("help: this message");
        System.out.println("professor {username}: list courses taught by professor (by course level then by course name)");
        System.out.println("student {username}: list courses taken by student (by course name)");
        System.out.println("unenroll {username} {id}: unenroll a student from a course");
        System.out.println("users: list all users (alphabetically by username) ");
        System.out.println("quit: quit SIS");
    }

    /**
     * The main loop runs through the input commands that 'in' is attached
     * to via the Scanner.  It prompts with '&gt;'.  If the command is valid it calls
     * the appropriate private helper method.  Otherwise it should display the message:<br>
     * <br>
     * Unrecognized command {command}<br>
     * <br>
     * And reprompts the user.
     *
     * @param in a Scanner attached to the input (either stdin or the input file)
     * @param stdin tells whether the scanner is attached to stdin or not.  If
     * not, the input command should be displayed to standard output so it is
     * easier to follow the output.
     */
    public void mainLoop(Scanner in, boolean stdin) {
        if (stdin) {
            System.out.println("Type 'help' for the list of commands.");
        }
        System.out.print("> ");
        // continue looping until the user enters "quit" or there is no more input
        boolean done = false;
        while (!done && in.hasNext()) {
            // read the next command and then call the appropriate method to process it
            String line = in.nextLine();
            if (!stdin) {
                System.out.println(line);
            }
            String fields[] = line.split("\\s+");

            // in after each case or else one case will "fall into" another!
            switch (fields[0]) {
                case USERS:
                    Iterator<User> iter4 = backend.getAllUsers().iterator();
                    while (iter4.hasNext()) {
                        System.out.println(iter4.next());
                    }
                    break;
                case UNENROLL:
                    if ((backend.courseExists(Integer.parseInt(fields[2])) && (backend.userExists(fields[1])))) {
                        backend.unenrollStudent(fields[1], Integer.parseInt(fields[2]));
                    } else if (backend.userExists(fields[1])) {
                        System.out.println("Course " + fields[2] + " does not exist.");
                    } else {
                        System.out.println("User " + fields[1] + " does not exist.");
                    }
                    break;
                case STUDENT:
                    Collection<Course> data = backend.getCoursesUser(fields[1]);
                    if (data != null) {
                        Iterator<Course> iter2 = data.iterator();
                        while (iter2.hasNext()) {
                            System.out.println(iter2.next());
                        }
                    } else {
                        System.out.println("Student " + fields[1] + " does not exist.");
                    }
                    break;
                case PROFESSOR:
                    Collection<Course> temp = backend.getCoursesUser(fields[1]);
                    if (temp != null) {
                        Iterator<Course> iter3 = temp.iterator();
                        while (iter3.hasNext()) {
                            System.out.println(iter3.next());
                        }
                    } else {
                        System.out.println("Professor " + fields[1] + " does not exist.");
                    }
                    break;
                case ENROLL:
                    if ((backend.courseExists(Integer.parseInt(fields[2])) && (backend.userExists(fields[1])))) {
                        backend.enrollStudent(fields[1], Integer.parseInt(fields[2]));
                    } else if (backend.userExists(fields[1])) {
                        System.out.println("Course " + fields[2] + " does not exist.");
                    } else {
                        System.out.println("User " + fields[1] + " does not exist.");
                    }
                    break;
                case COURSES:
                    Iterator<Course> iter = backend.getAllCourses().iterator();
                    while (iter.hasNext()) {
                        System.out.println(iter.next());
                    }
                    break;
                case COURSE:
                    if (backend.courseExists(Integer.parseInt(fields[1]))) {
                        System.out.println(backend.getCourse(Integer.parseInt(fields[1])));
                    } else {
                        System.out.println("Course " + fields[1] + " does not exist.");
                    }
                    break;
                case HELP:
                    helpMessage();
                    break;
                case QUIT:
                    done = true;
                    break;
                default:
                    System.out.println("Unrecognized command " + fields[0]);
            }

            // reprompt
            System.out.print("> ");
        }
    }

    /**
     * The main method.
     *
     * @param args command line arguments (used - see class description)
     * @throws FileNotFoundException if a file is not found
     */
    public static void main(String[] args) throws FileNotFoundException {
        // display a usage message if the number of command line arguments
        // is not correct
        if (args.length < 3 || args.length > 4) {
            System.out.println("Usage: java SIS course-file professor-file student-file [input]");
            return;
        }

        // create the frontend and tie it to the backend
        SIS sis = new SIS(args[0], args[1], args[2]);

        // create a scanner that is tied to either standard input or an input file
        Scanner in;
        boolean stdin = true;
        // if no arguments, tie the scanner to standard input
        if (args.length == 3) {
            in = new Scanner(System.in);
        } else {
            // otherwise tie scanner to a file using the last command
            // line argument as the filename
            in = new Scanner(new File(args[3]));
            stdin = false;
        }

        // enter the main loop
        sis.mainLoop(in, stdin);

        // close the scanner
        in.close();
    }
}
