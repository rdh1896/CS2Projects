package functions;

/**
 * A class for constant numbers.
 *
 * @author Russell Harvey (rdh1896@rit.edu)
 */

public class Constant extends Function {

    /** Value of the Constant. */
    private double value;

    /**
     * Create a new constant.
     * @param value
     */
    public Constant(double value) {
        super(Function.FunctionType.PRIMITIVE);
        this.value = value;
    }

    /**
     * Gets the value of the constant.
     * @return
     */
    public double getValue() {
        return value;
    }

    /**
     * Returns a human-readable representation of the function.
     * @return String
     */
    @Override
    public String toString() {
        return Double.toString(value);
    }

    /**
     * Computes the first derivative of the function and returns it.
     * @return Function
     */
    @Override
    public Function derivative() {
        return new Zero();
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
        return (upper - lower) * this.value;
    }

    /**
     * Is the function a constant? Or are all "children" constants?
     * @return boolean
     */
    @Override
    public boolean isConstant() {
        return true;
    }

    /**
     * Given a double floating point value of x, compute the value of the function.
     * @param x
     * @return double
     */
    @Override
    public double evaluate(double x) {
        return value;
    }
}
