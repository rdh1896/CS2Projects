package common;
import java.io.IOException;

/**
 * Main for Whack-a-Mole server side
 *  Handles starting and stopping the game
 *
 * @author Russell Harvey
 */

public class WAMMain extends Thread {

    /** Players in the game */
    private WAMPlayer[] players;
    /** The active game state */
    private WAMGame board;
    /** Amount of time the game will last */
    private int duration;

    /**
     * Creates a new WAMMain
     * @param players
     * @param board
     * @param duration
     */
    public WAMMain(WAMPlayer[] players, WAMGame board, int duration) {
        this.players = players;
        this.board = board;
        this.duration = duration;
    }

    /**
     * Primary run method for WAMMain
     */
    @Override
    public void run() {
        try {
            System.out.println("Sleeping to allow for Client GUIs to be made.");
            sleep(2000);
        } catch (InterruptedException ie) {
            System.out.println(ie);
        }

        double dur = this.duration * 1000;
        board.setRunning(true);
        System.out.println("Starting game!");
        double start = System.currentTimeMillis();
        while ((start + dur) > System.currentTimeMillis()) {
            assert true;
        }
        System.out.println("Game Over!");
        board.declareVictor();
        for (WAMPlayer p : players) {
            try {
                p.close();
            } catch (IOException ie) {
                System.out.println(ie);
            }
        }
        board.setRunning(false);
    }
}
