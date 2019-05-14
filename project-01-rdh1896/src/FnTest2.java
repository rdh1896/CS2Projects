import java.util.ArrayList;
import java.util.List;
import functions.*;

/*
 * $Id: FnTest2.java,v 1.1 2012/09/08 19:32:49 vcss243 Exp $
 */

/**
 *
 * @author James Heliotis
 */
public class FnTest2 {

    /**
     * Create a table of some trigonometric functions plus
     * some more complex functions.
     * @param args not used
     */
    @SuppressWarnings( "serial" )
    public static void main( String[] args ) {
        if ( args.length != 0 ) {
            System.err.println( "FnTest2 takes no command line arguments." );
            System.exit( 1 );
        }

        final functions.Function sin = new functions.Sine( functions.Variable.X );
        final functions.Function cos = new functions.Cosine( functions.Variable.X );
        List< functions.Function > functions = new ArrayList<>() {{
            add( sin );
            add( cos );
            add( cos.derivative() );
            add( new functions.Sum( Variable.X, new functions.Constant( 1.5 ),
                          new functions.Sum( Variable.X, new functions.Constant( 2.0 ) )
            ) );
            add( new functions.Sum( new functions.Product( Variable.X, new functions.Constant( 1.5 ) ),
                          new functions.Product( Variable.X, new functions.Constant( 2.0 ) )
            ) );
        }};

        for ( functions.Function func: functions ) {
            System.out.println( func + " is " +
                            ( func.isConstant() ? "" : "not " ) +
                            "a constant." );
            System.out.println( "The derivative of " + func +
                    " is " + func.derivative() +
                    ", which is " +
                    ( func.derivative().isConstant() ? "" : "not " ) +
                    "a constant." );
        }

        System.out.println();

        System.out.printf( "%4s %8s %8s %8s %8s %8s\n",
                "x", "sin", "cos", "cos'", "fn#4", "fn#5" );
        System.out
                .println( "=================================================" );
        for ( double x = 0; x <= Math.PI; x += 0.1 ) {
            System.out.printf( "%4.2f", x );
            for ( functions.Function f: functions ) {
                System.out.printf( " %8.4f", f.evaluate( x ) );
            }
            System.out.println();
        }
        System.out.println();
    }

}

// OUTPUT
/*
sin( x ) is not a constant.
The derivative of sin( x ) is cos( x ), which is not a constant.
cos( x ) is not a constant.
The derivative of cos( x ) is ( sin( x ) * -1.0 ), which is not a constant.
( sin( x ) * -1.0 ) is not a constant.
The derivative of ( sin( x ) * -1.0 ) is ( cos( x ) * -1.0 ), which is not a constant.
( x + ( x + 2.0 ) + 1.5 ) is not a constant.
The derivative of ( x + ( x + 2.0 ) + 1.5 ) is 2.0, which is a constant.
( ( x * 1.5 ) + ( x * 2.0 ) ) is not a constant.
The derivative of ( ( x * 1.5 ) + ( x * 2.0 ) ) is 3.5, which is a constant.

   x      sin      cos     cos'     fn#4     fn#5
=================================================
0.00   0.0000   1.0000  -0.0000   3.5000   0.0000
0.10   0.0998   0.9950  -0.0998   3.7000   0.3500
0.20   0.1987   0.9801  -0.1987   3.9000   0.7000
0.30   0.2955   0.9553  -0.2955   4.1000   1.0500
0.40   0.3894   0.9211  -0.3894   4.3000   1.4000
0.50   0.4794   0.8776  -0.4794   4.5000   1.7500
0.60   0.5646   0.8253  -0.5646   4.7000   2.1000
0.70   0.6442   0.7648  -0.6442   4.9000   2.4500
0.80   0.7174   0.6967  -0.7174   5.1000   2.8000
0.90   0.7833   0.6216  -0.7833   5.3000   3.1500
1.00   0.8415   0.5403  -0.8415   5.5000   3.5000
1.10   0.8912   0.4536  -0.8912   5.7000   3.8500
1.20   0.9320   0.3624  -0.9320   5.9000   4.2000
1.30   0.9636   0.2675  -0.9636   6.1000   4.5500
1.40   0.9854   0.1700  -0.9854   6.3000   4.9000
1.50   0.9975   0.0707  -0.9975   6.5000   5.2500
1.60   0.9996  -0.0292  -0.9996   6.7000   5.6000
1.70   0.9917  -0.1288  -0.9917   6.9000   5.9500
1.80   0.9738  -0.2272  -0.9738   7.1000   6.3000
1.90   0.9463  -0.3233  -0.9463   7.3000   6.6500
2.00   0.9093  -0.4161  -0.9093   7.5000   7.0000
2.10   0.8632  -0.5048  -0.8632   7.7000   7.3500
2.20   0.8085  -0.5885  -0.8085   7.9000   7.7000
2.30   0.7457  -0.6663  -0.7457   8.1000   8.0500
2.40   0.6755  -0.7374  -0.6755   8.3000   8.4000
2.50   0.5985  -0.8011  -0.5985   8.5000   8.7500
2.60   0.5155  -0.8569  -0.5155   8.7000   9.1000
2.70   0.4274  -0.9041  -0.4274   8.9000   9.4500
2.80   0.3350  -0.9422  -0.3350   9.1000   9.8000
2.90   0.2392  -0.9710  -0.2392   9.3000  10.1500
3.00   0.1411  -0.9900  -0.1411   9.5000  10.5000
3.10   0.0416  -0.9991  -0.0416   9.7000  10.8500

*/
