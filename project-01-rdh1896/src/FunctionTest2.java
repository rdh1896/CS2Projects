import functions.*;

/**
 * Tests all functionality of Functions
 *
 * @author Russell Harvey (rdh1896@rit.edu)
 */

public class FunctionTest2 {

    /**
     * Tests functionality of the entire Functions program.
     * @param args
     */
    public static void main(String[] args) {
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
        // Testing Product
        System.out.println("Testing Product:");
        Product p = new Product(Variable.X, new Constant(6), Variable.X);
        System.out.println("Our Constant: " + p);
        System.out.println("Evaluate = " + p.evaluate(2));
        System.out.println("Derivative = " + p.derivative());
        System.out.println("Integral from 0 to 10 = " + p.integral(0, 10, 100000));
        System.out.println("Is constant? = " + p.isConstant());
        // Test Completed
        // Output should be:
        // (x * x * 6.0), 24.0, ((x * 6.0) + ((x * 6.0))), 2000.0, false
        System.out.println("--------------------------------------------------------");
        // Testing Sine
        System.out.println("Testing Sine:");
        Sine sin = new Sine(new Product(Variable.X, new Constant(6)));
        System.out.println("Our Sine: " + sin);
        System.out.println("Evaluate = " + sin.evaluate(2));
        System.out.println("Derivative = " + sin.derivative());
        System.out.println("Integral from 0 to 10 = " + sin.integral(0, 10, 100000));
        System.out.println("Is constant? = " + sin.isConstant());
        // Test Completed
        // Output should be:
        // (sin((x * 6.0)), -0.5366, (cos((x * 6.0)) * 6.0), 0.3254, false
        System.out.println("--------------------------------------------------------");
        // Testing Cosine
        System.out.println("Testing Cosine:");
        Cosine cos = new Cosine(new Product(Variable.X, new Constant(6)));
        System.out.println("Our Cosine: " + cos);
        System.out.println("Evaluate = " + cos.evaluate(2));
        System.out.println("Derivative = " + cos.derivative());
        System.out.println("Integral from 0 to 10 = " + cos.integral(0, 10, 100000));
        System.out.println("Is constant? = " + cos.isConstant());
        // Test Completed
        // Output should be:
        // (cos((x * 6.0)), 0.8439, (-sin((x * 6.0)) * 6.0), -0.0508, false
        System.out.println("--------------------------------------------------------");
        // Testing Product Rule
        System.out.println("Testing Product Rule:");
        Product prdRule = new Product(sin, p);
        System.out.println("Our Product: " + prdRule);
        System.out.println("Evaluate = " + prdRule.evaluate(2));
        System.out.println("Derivative = " + prdRule.derivative());
        System.out.println("Integral from 0 to 10 = " + prdRule.integral(0, 10, 100000));
        System.out.println("Is constant? = " + prdRule.isConstant());
        // Test Completed!!! (Like, I was scared this was going to break.)
        // Output should be:
        // (sin((x * 6.0)) * (x * x * 6.0)), -12.87775003201044, ((sin((x * 6.0)) * ((x * 6.0) + ((x * 6.0)))) +
        // (((x * x * 6.0)) * (cos((x * 6.0)) * 6.0))), 94.11679236232035, false
        System.out.println("--------------------------------------------------------");
        // Testing a Polynomial
        System.out.println("Testing Polynomial:");
        Sum s2 = new Sum(p, sin, new Constant(4));
        System.out.println("Our Polynomial: " + s2);
        System.out.println("Evaluate = " + s2.evaluate(2));
        System.out.println("Derivative = " + s2.derivative());
        System.out.println("Integral from 0 to 10 = " + s2.integral(0, 10, 100000));
        System.out.println("Is constant? = " + s2.isConstant());
        // Test Completed
        // Output should be:
        // ((x * x * 6.0) + sin((x * 6.0)) + 4.0), 27.463427081999566, (((x * 6.0) + ((x * 6.0))) +
        // (cos((x * 6.0)) * 6.0)), 2040.3254022536487, false
        System.out.println("--------------------------------------------------------");
        // Testing isConstant() for Sum, Product, Sine, and Cosine
        // All should print true.
        Constant c1 = new Constant(4);
        Constant c2 = new Constant(5);
        Sum csum = new Sum(c1, c2);
        Product cprd = new Product(c1, c2);
        Sine csin = new Sine(c1);
        Cosine ccos = new Cosine(c2);
        System.out.println("Is constant? (all should print true): " + csum.isConstant() + " " + cprd.isConstant()
                            + " " + csin.isConstant() + " " + ccos.isConstant());
        // Test Completed
        System.out.println("--------------------------------------------------------");
    }
}
