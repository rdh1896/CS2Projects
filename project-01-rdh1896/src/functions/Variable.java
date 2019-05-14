package functions;

/**
 * Class representing a variable
 *
 * @author Russell Harvey (rdh1896@rit.edu)
 */

public class Variable extends Function {

    /** An instance of the variable class. */
    public static final Variable X = new Variable();

    /**
     * Create a new variable.
     */
    private Variable () {
        super(FunctionType.PRIMITIVE);
    }

    /**
     * Given a double floating point value of x, compute the value of the function.
     * @param x
     * @return double
     */
    @Override
    public double evaluate(double x) {
        return x;
    }

    /**
     * Returns a human-readable representation of the function.
     * @return String
     */
    @Override
    public String toString() {
        return "x";
    }

    /**
     * Computes the first derivative of the function and returns it.
     * @return Function
     */
    @Override
    public Function derivative() {
        return new Constant(1);
    }

    /**
     * Computes the definite integral of the function from the upper
     * bound to the lower bound with *traps* amount of trapezoids.
     * @param lower
     * @param upper
     * @param traps
     * @return double
     */
    @Override
    public double integral(double lower, double upper, int traps) {
        double area = (evaluate(upper) + evaluate(lower)) / 2;
        double x;
        double base = (upper - lower) / traps;

        for (int i = 1; i < traps; i++) {
            x = lower + (i*base);
            area = area + evaluate(x);
        }

        return area * base;
    }

    /**
     * Is the function a constant? Or are all "children" constants?
     * @return boolean
     */
    @Override
    public boolean isConstant() {
        return false;
    }


}
