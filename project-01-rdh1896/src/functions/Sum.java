package functions;
import java.util.ArrayList;

/**
 * Sum implementation for Functions
 *
 * @author Russell Harvey (rdh1896@rit.edu)
 */

public class Sum extends Function{

    /** A string representation of Sum. */
    private String concatenation;

    /** Terms being added together in Sum. */
    private ArrayList<Function> terms;

    /**
     * Create a new Sum.
     * @param args
     */
    public Sum (Function... args) {
        super(FunctionType.COMPOSITE);
        this.terms = new ArrayList<>();
        double constants = 0;
        String temp = "";
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof Zero) {
                assert true;
            } else if (args[i].isConstant()) {
                constants += args[i].evaluate(0);
            } else {
                terms.add(args[i]);
            }
        }
        if (constants != 0) {
            terms.add(new Constant(constants));
        }
        if (terms.size() == 1 && terms.get(0).isConstant()) {
            temp = terms.get(0).toString();
        } else {
            temp = "(";
            int counter = terms.size() - 1;
            for (Function fnc : terms) {
                if (counter == 0) {
                    temp += fnc;
                } else {
                    temp += fnc + " + ";
                    counter--;
                }
            }
            temp += ")";
        }
        this.concatenation = temp;
    }

    /**
     * Given a double floating point value of x, compute the value of the function.
     * @param x
     * @return double
     */
    @Override
    public double evaluate(double x) {
        double sum = 0;
        for (Function term : terms) {
            sum += term.evaluate(x);
        }
        return sum;
    }

    /**
     * Returns a human-readable representation of the function.
     * @return String
     */
    @Override
    public String toString() {
        return concatenation;
    }

    /**
     * Computes the first derivative of the function and returns it.
     * @return Function
     */
    @Override
    public Function derivative() {
        Function[] fPrime = new Function[terms.size()];
        int i = 0;
        for (Function f : terms) {
            Function deriv = f.derivative();
            fPrime[i] = deriv;
            i++;
        }

        Sum newFunc = new Sum(fPrime);

        return newFunc;
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
        for (Function f : terms) {
            if (f.isConstant()) {
                assert true;
            } else {
                return false;
            }
        }
        return true;
    }
}
