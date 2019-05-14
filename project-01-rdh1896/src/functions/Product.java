package functions;
import java.util.ArrayList;

/**
 * Product class for Functions
 *
 * @author Russell Harvey (rdh1896@rit.edu)
 */

public class Product extends Function {

    /** Terms being multiplied together in product. */
    private ArrayList<Function> terms;

    /** A string representation of the Product class. */
    private String concatenation;

    /**
     * Create a new Product.
     * @param args
     */
    public Product(Function... args) {
        super(FunctionType.COMPOSITE);
        this.terms = new ArrayList<>();
        double product = 1;
        int count = 0;
        String temp = "";
        for (Function f : args) {
            if (f instanceof Variable) {
                count += 1;
                terms.add(f);
            } else if (f instanceof Constant) {
                Constant c = (Constant) f;
                product = product * c.getValue();
            } else if (f instanceof Zero) {
                product = 0;
            } else {
                if (f.isConstant()) {
                    product = product * f.evaluate(0);
                } else {
                     terms.add(f);
                }
            }
        }
        if (product > 1) {
            terms.add(new Constant(product));
        }
        if (terms.size() == 1 && terms.get(0).isConstant()) {
            temp = terms.get(0).toString();
        } else {
            temp = "(";
            int termLen = terms.size();
            for (Function term : terms) {
                if (termLen == 1) {
                    temp += term.toString() + ")";
                } else {
                    temp += term.toString() + " * ";
                    termLen -= 1;
                }
            }
        }
        if (product == 0) {
            temp = "";
            this.terms.clear();
        }
        this.concatenation = temp;
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
        if (terms.size() == 1) {
            return terms.get(0).derivative();
        }
        Function f = terms.get(0);
        Function[] funcs = new Function[terms.size() - 1];
        for (int i = 1; i < terms.size(); i++) {
            funcs[i - 1] = terms.get(i);
        }
        Function fnc = new Product(funcs);
        Function result = new Sum(new Product(f, fnc.derivative()), new Product(fnc, f.derivative()));
        return result;
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
        for (Function f : terms) {
            if (f.isConstant()) {
                assert true;
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * Given a double floating point value of x, compute the value of the function.
     * @param x
     * @return double
     */
    @Override
    public double evaluate(double x) {
        double product;
        if (this.terms.size() == 0) {
            product = 0;
        } else {
            product = 1;
        }
        for (Function func : terms) {
            if (func instanceof Constant) {
                product = product * ((Constant) func).getValue();
            } else if (func instanceof Variable) {
                product = product * x;
            } else if (func instanceof Product) {
                product = product * func.evaluate(x);
            } else {
                product = product * func.evaluate(x);
            }
        }
        return product;
    }
}

