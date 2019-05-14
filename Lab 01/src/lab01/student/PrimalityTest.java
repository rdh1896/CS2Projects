/**
 * Name: Russell Harvey <rdh1896@rit.edu>
 * Assingment: Lab 01
 * File: PrimalityTest.java
 * Language: Java
 */

package lab01.student;

import java.util.Scanner;

public class PrimalityTest {

    /** isPrime
     * Takes in a number and determines whether or not the number
     * is prime.
     * @param number: Number being checked for primality
     * @return boolean
     */
    public static boolean isPrime(int number) {
        if (number == 0) {
            return false;
        } else if (number == 1) {
            return false;
        } else {
            for (int i = 2; i < number; i++) {
                if ((number % i) == 0) {
                    return false;
                }
            }
            return true;
        }
    }

    /** Main
     * Asks user for a number to test primality and proceeds to check it
     * using isPrime. If the user enters a number less than 1 the program
     * will terminate.
     * @param args
     */
    public static void main (String [] args) {

        while (true) {
            System.out.print("Enter a number (0 to quit): ");
            java.util.Scanner input = new Scanner (System.in);
            int value = Integer.parseInt(input.next());

            if (value < 1) {

                System.out.println("Goodbye!");
                System.exit(0);

            } else {

                boolean results = isPrime(value);

                if (results == false) {

                    System.out.println(value + " is not prime!");

                } else {

                    System.out.println(value + " is prime!");
                }

            }
        }
    }
}
