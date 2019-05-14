import functions.*;

/**
 * Test for Part One of Functions.
 *
 * @author Russell Harvey (rdh1896@rit.edu)
 */

public class FunctionTest1 {
    /**
     * A test module for Functions. Only tests part one classes.
     * Checks to ensure constant and variable work correctly.
     * Runs three sum tests to ensure sum works correctly.
     * @param args
     */
    public static void main (String[] args) {
        // Testing Constant
        System.out.println("Testing Constant:");
        Constant c = new Constant(5);
        System.out.println("Our Constant: " + c);
        System.out.println("Evaluate = " + c.evaluate(0));
        System.out.println("Derivative = " + c.derivative());
        System.out.println("Integral from 0 to 10 = " + c.integral(0, 10, 100000));
        System.out.println("Is constant? = " + c.isConstant());
        // Test Completed
        // Output should be:
        // 5.0, 5.0, 0, 50.0, true
        System.out.println("--------------------------------------------------------");
        // Testing Variable
        System.out.println("Testing Variable:");
        System.out.println("Our Variable: " + Variable.X);
        System.out.println("Evaluate = " + Variable.X.evaluate(5));
        System.out.println("Derivative = " + Variable.X.derivative());
        System.out.println("Integral from 0 to 10 = " + Variable.X.integral(0, 10, 100000));
        System.out.println("Is constant? = " + Variable.X.isConstant());
        // Test Completed
        // Output should be:
        //  x, 5.0, 1.0, 50.0, false
        System.out.println("--------------------------------------------------------");
        // Testing Sum
        System.out.println("Testing Sum:");
        Sum s = new Sum(Variable.X, new Constant(5), Variable.X, new Constant(4));
        System.out.println("Our Sum: " + s);
        System.out.println("Evaluate = " + s.evaluate(5));
        System.out.println("Derivative = " + s.derivative());
        System.out.println("Integral from 0 to 10 = " + s.integral(0, 10, 100000));
        System.out.println("Is constant? = " + s.isConstant());
        // Test Completed
        // Output should be:
        // (x + x + 9.0), 19.0, 2.0, 190.0, false
        System.out.println("--------------------------------------------------------");
        // Testing Sum Again
        System.out.println("Testing Sum:");
        Sum sum = new Sum(new Constant(12), new Constant(9), Variable.X, Variable.X, Variable.X);
        System.out.println("Our Sum: " + sum);
        System.out.println("Evaluate = " + sum.evaluate(5.3));
        System.out.println("Derivative = " + sum.derivative());
        System.out.println("Integral from 0 to 10 = " + sum.integral(0, 10, 100000));
        System.out.println("Is constant? = " + sum.isConstant());
        // Test Completed
        // Output should be:
        // (x + x + x + 21), 36.9, 3.0, 360.0, false
        System.out.println("--------------------------------------------------------");
        // Testing Sum Again
        System.out.println("Testing Sum:");
        Sum sumC = new Sum(new Constant(12), new Constant(9));
        System.out.println("Our Sum: " + sumC);
        System.out.println("Evaluate = " + sumC.evaluate(5.3));
        System.out.println("Derivative = " + sumC.derivative());
        System.out.println("Integral from 0 to 10 = " + sumC.integral(0, 10, 100000));
        System.out.println("Is constant? = " + sumC.isConstant());
        // Test Completed
        // Output should be:
        // 21.0, 21.0, 0, 210.0, true
        System.out.println("--------------------------------------------------------");

    }
}
