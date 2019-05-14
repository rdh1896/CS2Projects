package common;
import java.util.Random;

/**
 * Mole class for Whack-a-Mole
 *  Handles the moles themselves
 *
 * @author Russell Harvey
 */

public class Mole extends Thread {

    /** The mole's number */
    private int number;
    /** The current game being run */
    private WAMGame game;
    /** Is the mole up? */
    private boolean up;
    /** Is the mole running? (No, moles don't run.) */
    private boolean running;
    /** Minimum time a mole can be down */
    private int MIN_DOWN_TIME = 5000;
    /** Maximum time a mole can be down */
    private int MAX_DOWN_TIME = 15000;
    /** Minimum time a mole can be up */
    private int MAX_UP_TIME = 5000;
    /** Maximum time a mole can be up */
    private int MIN_UP_TIME = 8000;
    /** Random used to generate random numbers */
    private Random rand = new Random();

    /**
     * Creates a new Mole
     * @param number
     * @param game
     */
    public Mole (int number, WAMGame game) {
        this.number = number;
        this.game = game;
        this.up = false;
        this.running = false;
    }

    /**
     * Sets if the Mole is running
     * @param b
     */
    public void setRunning (Boolean b) {
        this.running = b;
    }

    /**
     * Gers the number of the Mole
     * @return number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Is the mole up?
     * @return up
     */
    public boolean getUp() {
        return up;
    }

    /**
     * Changes if the mole is up or down
     * @param b
     */
    public synchronized void setUp(boolean b) {
        WAMPlayer[] players = game.getPlayers();
        for (WAMPlayer p : players) {
            if (b) {
                p.moleUp(this.number);
            } else {
                p.moleDown(this.number);
            }
        }
        this.up = b;
    }

    /**
     * Main run loop for Mole
     */
    @Override
    public void run() {
        while (this.running) {
            try {
                sleep(rand.nextInt(((MAX_DOWN_TIME - MIN_DOWN_TIME) + 1) + MIN_DOWN_TIME));
                setUp(true);
                int upTime = rand.nextInt(((MAX_UP_TIME - MIN_UP_TIME) + 1) + MIN_UP_TIME);
                double start = System.currentTimeMillis();
                while (up) {
                    if ((start + upTime) < System.currentTimeMillis()) {
                        setUp(false);
                    }
                }
            } catch (InterruptedException ie) {
                System.out.println(ie);
            }
        }
    }
}
