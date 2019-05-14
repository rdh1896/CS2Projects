import functions.*;

/**
 * A very basic test of the functions.Function classes to help the student see
 * if his/her understanding of the interface is correct
 *
 * @author James Heliotis
 */
public class FnTest0 {

    /**
     * Create a sum of variables, and then apply
     * all the functions.Function operations on them.
     * The integral will go from 0 to 10.
     *
     * @param args not used
     */
    public static void main( String[] args ) {
        if ( args.length != 0 ) {
            System.err.println( "FnTest0 takes no command line arguments." );
            System.exit( 1 );
        }
        Variable var = Variable.X;
        Function f = new
            Sum( var, new Constant( 9 ), var, new Constant( 11 ) );
        System.out.println( "functions.Function " + f );
        System.out.println( "Value at 0: " + f.evaluate( 0.0 ) );
        System.out.println( "Value at 10: " + f.evaluate( 10.0 ) );
        System.out.println( "Derivative: " + f.derivative() );
        double iResult = 0.0;
        iResult = f.integral( 0.0, 10.0, 1000000 );
        System.out.println( "Integral from 0 to 10: " + iResult );
    }

}

// OUTPUT
/*
functions.Function ( x + x + 20.0 )
Value at 0: 20.0
Value at 10: 40.0
Derivative: 2.0
Integral from 0 to 10: 300.0
*/
