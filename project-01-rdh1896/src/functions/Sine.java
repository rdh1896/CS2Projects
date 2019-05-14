package functions;
import java.lang.Math.*;

/**
 * Sine implementation for Functions
 *
 * @author Russell Harvey (rdh1896@rit.edu)
 */
public class Sine extends Function {

    /** Value inside the sine function. */
    private Function value;

    /** Is the sine negative? */
    private boolean negative;

    /**
     * Create a new sine function.
     * @param value
     */
    public Sine(Function value) {
        super(FunctionType.COMPOSITE);
        this.negative = false;
        this.value = value;
    }

    /**
     * Given a double floating point value of x, compute the value of the function.
     * @param x
     * @return double
     */
    @Override
    public double evaluate(double x) {
        if (negative == false) {
            return Math.sin(value.evaluate(x));
        } else {
            return -(Math.sin(value.evaluate(x)));
        }
    }

    /**
     * Returns a human-readable representation of the function.
     * @return String
     */
    @Override
    public String toString() {
        if (negative == false) {
            return "sin(" + value.toString() + ")";
        } else {
            return "-sin(" + value.toString() + ")";
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
                return new Cosine(value);
            } else {
                return new Product(new Cosine(value), value.derivative());
            }
        } else {
            if (value instanceof Variable) {
                Cosine c = new Cosine(value);
                c.setNegative(true);
                return c;
            } else {
                Cosine cos = new Cosine(value);
                cos.setNegative(true);
                return new Product(cos, value.derivative());
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
     * Sets the sine function to be negative if true.
     * @param negative
     */
    public void setNegative(boolean negative) {
        this.negative = negative;
    }
}
