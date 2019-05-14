/**
 * Name: Russell Harvey <rdh1896@rit.edu>
 * Assingment: Lab 01
 * File: GoodHashFunc.java
 * Language: Java
 */

package lab01.student;

import java.util.Scanner;

public class GoodHashFunc {

    /** Main
     * Takes in a user input and computes a hash value from it.
     * @param args
     */
    public static void main (String [] args) {
        System.out.print("Please enter a String: ");
        java.util.Scanner recipient = new Scanner (System.in);
        String input = recipient.next();
        System.out.print("The computed hash is: ");
        computeHash(input);
    }

    /** computeHash
     * Takes in a string and computes the hash value of the string.
     * @param input: Any string to be hashed.
     */
    public static int computeHash (String input) {

        int [] characters = new int [input.length()];
        for (int i = 0; i < input.length(); i++) {

            int factor = (int) Math.pow(31, input.length() - (i + 1));
            int value = input.charAt(i) * factor;
            characters[i] = value;

        }

        int sum = 0;
        int count = 0;
        while (count < input.length()) {

            sum += characters[count];
            count ++;

        }

        return sum;

    }

}
