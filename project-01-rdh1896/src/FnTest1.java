import functions.*;

/**
 *
 * @author jeh
 */
public class FnTest1 {

    /**
     * Create a sum of variables, and then apply
     * all the functions.Function operations on them.
     * The integral will go from 0 to 10.
     *
     * @param args an array of name value pairs
     */
    public static void main( String[] args ) {
        int argLen = args.length;
        functions.Variable var = functions.Variable.X;
        functions.Function f = new
            functions.Product( var, new functions.Constant( 9 ), var, new functions.Constant( 11 ) );
        System.out.println( "functions.Function " + f );
        System.out.println( "Value at 0: " + f.evaluate( 0.0 ) );
        for ( int i = 0; i < argLen; ++i ) {
            double value = Double.parseDouble( args[ i ] );
            System.out.println( "Value at " + value + ": " +
                                         f.evaluate( value ) );
        }
        System.out.println( "Derivative: " + f.derivative() );
        System.out.println( "Integral from 0 to 10: " +
                                    f.integral( 0.0, 10.0, 1000000 ) );
    }

}

//OUTPUT
/*
functions.Function ( x * x * 99.0 )
Value at 0: 0.0
Derivative: ( ( x * 99.0 ) + ( x * 99.0 ) )
Integral from 0 to 10: 33000.09899919773
*/
