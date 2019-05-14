package common;
import server.Duplexer;
import java.io.Closeable;
import java.io.IOException;
import java.net.*;

/**
 * WAMPlayer implementation for Whack-a-Mole
 *  Handles sending messages to the client
 *
 * @author Russell Harvey
 */

public class WAMPlayer implements WAMProtocol, Closeable  {

    /** The socket communication is on */
    private Socket sock;
    /** Duplexer used for communication */
    private Duplexer comms;
    /** The player number */
    private int number;
    /** The player's name */
    private String name;
    /** The active game state */
    private WAMGame game;
    /** Player's score */
    private int score;

    /**
     * Creates a new WAMPlayer
     * @param sock
     * @param number
     * @param game
     */
    public WAMPlayer (Socket sock, int number, WAMGame game) {
        this.sock = sock;
        this.number = number;
        this.name = "Player " + number;
        this.game = game;
        try {
            this.comms = new Duplexer(sock);
        } catch (IOException ie) {
            System.out.println(ie);
        }
    }

    /**
     * Gets the duplexer the WAMPlayer is using
     * @return
     */
    public Duplexer getComms() {
        return comms;
    }

    /**
     * Sends the client a WELCOME message
     */
    public void connect () {
        comms.send("WELCOME " + game.getRows() + " " + game.getCols() + " " + game.getNumPlayers() + " " + number);
    }

    /**
     * Tells the client if a mole is up
     * @param num
     */
    public void moleUp (int num) {
        comms.send("MOLE_UP " + num);
    }

    /**
     * Tells the client if a mole is down
     * @param num
     */
    public void moleDown (int num) {
        comms.send("MOLE_DOWN " + num);
    }

    /**
     * Tells the client the score
     */
    public void score () {
        int[] scores = game.getScores();
        String temp = "";
        for (int s : scores) {
            temp += s + " ";
        }
        temp.strip();
        comms.send("SCORE " + temp);
    }

    /**
     * Sends if game is won by player
     */
    public void gameWon () {
        comms.send("GAME_WON");
    }

    /**
     * Sends if game is tied by player
     */
    public void gameTied () {
        comms.send("GAME_TIED");
    }

    /**
     * Sends if game is lost by player
     */
    public void gameLost () {
        comms.send("GAME_LOST");
    }

    public void error () {
        comms.send("ERROR");
    }

    /**
     * Closes the connection to the player
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        try {
            sock.close();
        } catch (IOException ie) {
            System.out.println(ie);
        }
    }
}
