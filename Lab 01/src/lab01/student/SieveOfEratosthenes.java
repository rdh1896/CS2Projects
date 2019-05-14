/**
 * Name: Russell Harvey <rdh1896@rit.edu>
 * Assingment: Lab 01
 * File: SieveOfEratosthenes.java
 * Language: Java
 */

package lab01.student;

import java.util.Scanner;

public class SieveOfEratosthenes {

    /** makeSieve
     * Takes in an upper bound and creates a Sieve from 0 to upper
     * bound (non-inclusive of upper bound) that will proceed to mark
     * any non-prime numbers in the Sieve with a "1". Returns the completed
     * Sieve.
     * @param upperBound: i.e. Python -> range(0, upperBound)
     * @return primes: Completed Sieve
     */
    public static int[] makeSieve(int upperBound) {
        int [] primes = new int [upperBound];
        primes[0] = 1;
        primes[1] = 1;
        for (int count = 2; count < upperBound; count++) {
            for (int i = 2; i < upperBound; i++) {
                int product = count * i;
                if (product >= upperBound) {
                    assert true;
                } else {
                    primes[product] = 1;
                }
            }
        }
        return primes;
    }

    /** Main
     * Asks user to input an upper bound, then creates a Sieve based
     * on that number. Then prompts the user to enter a number and the
     * program will report if the number is prime or not. Quits if number is
     * less than 1 and will display a message telling the user to enter a lower
     * number if their number is greater than the indices in the Sieve.
     * @param args
     */
    public static void main (String [] args){
        System.out.print("Please enter an upper bound: ");
        java.util.Scanner input = new Scanner (System.in);
        int value = Integer.parseInt(input.next());
        int []primeArray = makeSieve(value);
        while (true) {
            System.out.print("Enter a number (0 to quit): ");
            java.util.Scanner primeInput = new Scanner (System.in);
            int primeValue = Integer.parseInt(primeInput.next());
            if (primeValue < 1) {
                System.out.println("Goodbye!");
                System.exit(0);
            } else if (primeValue > value - 1) {
                System.out.println("Please enter a number within range of the Sieve.");
            } else if (primeArray[primeValue] == 1) {
                System.out.println(primeValue + " is not prime!");
            } else {
                System.out.println(primeValue + " is prime!");
            }
        }
    }
}
