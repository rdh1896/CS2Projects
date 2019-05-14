package server;
import common.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server Class for Whack-a-Mole
 *  Handles creating the server-side of the game
 *
 * @author Russell Harvey
 */

public class WAMServer implements WAMProtocol, Runnable {

    /** Socket the server will be running on. */
    private ServerSocket server;
    /** Amount of rows in the game */
    private int rows;
    /** Amount of columns in the game */
    private int cols;
    /** Amount of players in the game */
    private int players;
    /** Amount of game time */
    private int duration;

    /**
     * Creates a new WAMServer
     * @param port
     * @param rows
     * @param cols
     * @param players
     * @param duration
     * @throws WAMException
     */
    public WAMServer(int port, int rows, int cols, int players, int duration) throws WAMException {
        try {
            this.server = new ServerSocket(port);
            this.rows = rows;
            this.cols = cols;
            this.players = players;
            this.duration = duration;

        } catch (IOException e) {
            throw new WAMException(e);
        }
    }

    /**
     * Main function for WAMServer to start a new game
     * @param args
     * @throws WAMException
     */
    public static void main(String[] args) throws WAMException {

        if (args.length != 5) {
            System.out.println("Usage: java WAMServer <port>");
            System.exit(1);
        }

        int port = Integer.parseInt(args[0]);
        int rows = Integer.parseInt(args[1]);
        int cols = Integer.parseInt(args[2]);
        int players = Integer.parseInt(args[3]);
        int duration = Integer.parseInt(args[4]);
        WAMServer server = new WAMServer(port, rows, cols, players, duration);
        server.run();
    }


    /**
     * Creates a new WAM on the server side
     */
    @Override
    public void run() {
        try {
            System.out.println("Initializing game.");
            WAMGame game = new WAMGame(this.rows, this.cols, this.players);
            System.out.println("Connecting players.");
            WAMPlayer[] users = new WAMPlayer[this.players];
            for (int i = 0; i < players; i++) {
                System.out.println("Waiting for Player " + i + "...");
                Socket playerSocket = this.server.accept();
                WAMPlayer player = new WAMPlayer(playerSocket, i, game);
                player.connect();
                System.out.println("Player " + i + " connected!");
                users[i] = player;
            }
            game.setPlayers(users);
            WAMMain main = new WAMMain(users, game, this.duration);
            new Thread(main).run();
        } catch (IOException e) {
            System.err.println("Something has gone horribly wrong!");
            e.printStackTrace();
        }
    }
}
