package functions;

/**
 * Represents a function and the many actions that can be done with one.
 *
 * @author Russell Harvey (rdh1896@rit.edu)
 */

public abstract class Function {

    /** Is the function composite or primitive? */
    public FunctionType type;

    /**
     * Denotes if the function is a composite or primitive.
     */
    public enum FunctionType {
        COMPOSITE,
        PRIMITIVE;
    }

    /**
     * Creates a new function, but cannot be used due to function being
     * an abstract class.
     * @param type
     */
    public Function (FunctionType type) {
        this.type = type;
    }

    /**
     * Given a double floating point value of x, compute the value of the function.
     * @param x
     * @return double
     */
    public abstract double evaluate (double x);

    /**
     * Returns a human-readable representation of the function.
     * @return String
     */
    @Override
    public abstract String toString ();

    /**
     * Computes the first derivative of the function and returns it.
     * @return Function
     */
    public abstract Function derivative();

    /**
     * Computes the definite integral of the function from the upper
     * bound to the lower bound with *traps* amount of trapezoids.
     * @param lower
     * @param upper
     * @param traps
     * @return double
     */
    public abstract double integral (double lower, double upper, int traps);

    /**
     * Is the function a constant? Or are all "children" constants?
     * @return boolean
     */
    public abstract boolean isConstant();

}
