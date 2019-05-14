package functions;
import java.lang.Math.*;

/**
 * Cosine class for Functions
 *
 * @author Russell Harvey (rdh1896@rit.edu)
 */

public class Cosine extends Function {

    /** The value located inside of the cosine. */
    private Function value;

    /** Is the cosine negative? */
    private boolean negative;

    /**
     * Create a new cosine.
     * @param value
     */
    public Cosine(Function value) {
        super(Function.FunctionType.COMPOSITE);
        this.value = value;
        this.negative = negative;
    }

    /**
     * Given a double floating point value of x, compute the value of the function.
     * @param x
     * @return double
     */
    @Override
    public double evaluate(double x) {
        if (negative == false) {
            return Math.cos(value.evaluate(x));
        } else {
            return -(Math.cos(value.evaluate(x)));
        }
    }

    /**
     * Returns a human-readable representation of the function.
     * @return String
     */
    @Override
    public String toString() {
        if (negative == false) {
            return "cos(" + value.toString() + ")";
        } else {
            return "-cos(" + value.toString() + ")";
        }
    }

    /**
     * Computes the first derivative of the function and returns it.
     * @return Function
     */
    @Override
    public Function derivative() {
        if (negative == false) {
            if (value instanceof Variable) {
                Sine s = new Sine(value);
                s.setNegative(true);
                return s;
            } else {
                Sine sin = new Sine(value);
                sin.setNegative(true);
                return new Product(sin, value.derivative());
            }
        } else {
            if (value instanceof Variable) {
                return new Sine(value);
            } else {
                return new Product(new Sine(value), value.derivative());
            }
        }
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
            x = lower + (i * base);
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
        return value.isConstant();
    }

    /**
     * Sets the cosine to be negative.
     * @param negative
     */
    public void setNegative(boolean negative) {
        this.negative = negative;
    }
}
