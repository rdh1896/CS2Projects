package client.gui;
import common.WAMProtocol;
import common.WAMProtocol.*;
import server.Duplexer;
import java.util.List;
import java.io.IOException;
import java.net.*;

/**
 * The Main Network Connection Between Client and Server
 *  The Controller in MVC
 *
 * @author Russell Harvey
 * @author Eric Gao
 */

public class WAMNetworkClient implements WAMProtocol {

    /** Server connection */
    private Socket clientSocket;
    /** Duplexer to talk to server */
    private Duplexer server;
    /** Model for GUI */
    private WAMBoard board;
    /** Is the game running? */
    private boolean running;

    /**
     * Tells if the game is running
     * @return
     */
    public boolean getRunning () {
        return running;
    }

    /**
     * Create a new network client
     * @param host
     * @param port
     */
    public WAMNetworkClient (String host, int port) {
        try {
            this.clientSocket = new Socket(host, port);
            this.server = new Duplexer(clientSocket);
            this.running = true;
        } catch (IOException io) {
            System.out.println(io);
        }
    }

    /**
     * Start the network client's listener
     */
    public void startListener () {
        new Thread(() -> this.run()).start();
    }

    /**
     * Return the model
     * @return
     */
    public WAMBoard getModel () {
        return this.board;
    }

    /**
     * Tells the model to make a mole go up
     * @param mole
     */
    public void moleUp (int mole) {
        board.moleUp(mole);
    }

    /**
     * Tells the model to make a mole go down
     * @param mole
     */
    public void moleDown (int mole) {
        board.moleDown(mole);
    }

    /**
     * Sends a whack message to the server
     * @param mole
     * @param player
     */
    public void whack (int mole, int player) {
        this.server.send("WHACK " + mole + " " + player);
        System.out.println("Whacked Mole " + mole + " @ " + System.currentTimeMillis());
    }

    /**
     * Updates the score of the game
     * @param scores
     */
    public void score (String[] scores) {
        board.updateScore(scores);
    }

    /**
     * Tells the player if they won
     */
    public void won () {
        board.gameWon();
    }

    /**
     * Tells the player if they lost
     */
    public void lost () {
        board.gameLost();
    }

    /** Tells if the player has tied */
    public void tied () {
        board.gameTied();
    }

    /**
     * Used to stop client functionality
     */
    public void close() {
        try {
            this.clientSocket.close();
        }
        catch( IOException ioe ) {
            // squash
        }
        this.board.close();
    }

    /**
     * Main loop of WAMNetworkClient
     */
    private void run () {
        while (this.getRunning()) {
            String request = this.server.read(1);
            String arguments = this.server.read(2);
            String[] args = arguments.split(" ");
            if (!request.equals("")) {
                System.out.println(request + " @ " + System.currentTimeMillis());
            }
            switch (request) {
                case WELCOME:
                    this.board = new WAMBoard(Integer.parseInt(args[0]), Integer.parseInt(args[1]),
                            Integer.parseInt(args[2]), Integer.parseInt(args[3]), this);
                    break;
                case MOLE_UP:
                    moleUp(Integer.parseInt(args[0]));
                    break;
                case MOLE_DOWN:
                    moleDown(Integer.parseInt(args[0]));
                    break;
                case SCORE:
                    score(args);
                    break;
                case GAME_WON:
                    won();
                    break;
                case GAME_LOST:
                    lost();
                    break;
                case GAME_TIED:
                    tied();
                    break;
                case ERROR:
                    System.out.println("Error.");
                    break;
            }
        }
        try {
            this.server.close();
        } catch (IOException ie) {
            System.out.println("Error, could not close.");
        }
    }
}
