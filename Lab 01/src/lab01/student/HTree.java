/**
 * Name: Russell Harvey <rdh1896@rit.edu>
 * Assingment: Lab 01
 * File: HTree.java
 * Language: Java
 */

package lab01.student;

import static turtle.Turtle.*;

public class HTree {

    /**
     * Globals:
     * MAX_SEGMENT_LENGTH: Maximum length of an HTree segment
     */
    public static int MAX_SEGMENT_LENGTH = 200;

    /**
     * Initializes the turtle canvas.
     * @param initLength: MAX_SEGMENT_LENGTH
     * @param initDepth: Depth of recursion.
     */
    public static void init (int initLength, int initDepth) {

        Turtle.setWorldCoordinates(-initLength * 2, -initLength * 2, initLength * 2, -initLength * 2);
        Turtle.title("H-Tree, depth: " + initDepth);

    }

    /**
     * Draws an HTree to a user defined depth.
     * @param length: MAX_SEGMENT_LENGTH
     * @param depth: Depth of recursion.
     */
    public static void drawHTree (int length, int depth) {

        if (depth > 0) {
            Turtle.forward(length / 2);
            Turtle.left(90);
            Turtle.forward(length / 2);
            Turtle.right(90);

            drawHTree(length / 2, depth - 1);

            Turtle.right(90);
            Turtle.forward(length);
            Turtle.left(90);

            drawHTree(length / 2, depth - 1);

            Turtle.left(90);
            Turtle.forward(length / 2);
            Turtle.left(90);
            Turtle.forward(length);
            Turtle.right(90);
            Turtle.forward(length / 2);
            Turtle.right(90);

            drawHTree(length / 2, depth - 1);

            Turtle.right(90);
            Turtle.forward(length);
            Turtle.left(90);

            drawHTree(length / 2, depth - 1);

            Turtle.left(90);
            Turtle.forward(length / 2);
            Turtle.right(90);
            Turtle.forward(length / 2);
        }
    }

    /**
     * Takes in an argument from the command line draws an
     * HTree to the user specified depth.
     * @param args
     */
    public static void main (String[] args){

        if (args.length < 1) {
            System.out.println("Usage: java HTree #");
            return;
        }

        int conversion = Integer.parseInt(args[0]);
        int depth = conversion;
        if (depth < 0) {
            System.out.println("The depth must be greater than or equal to 0");
            return;
        } else {

            init(MAX_SEGMENT_LENGTH, depth);

            drawHTree(MAX_SEGMENT_LENGTH, depth);

        }
    }
}
