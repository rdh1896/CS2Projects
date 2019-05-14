package connectfour.server;

import connectfour.ConnectFourException;
import connectfour.Duplexer;
import connectfour.ConnectFour;

import java.io.IOException;
import java.net.*;

/**
 * The server waits for incoming client connections and pairs them off
 * to play the game.
 *
 * @author Russell Harvey (rdh1896@rit.edu)
 */

public class ConnectFourServer {

    /** Player 1's communications to the server */
    private Duplexer player1;

    /** Player 2's communications to the server */
    private Duplexer player2;

    /** The game currently being played */
    private ConnectFour game;

    /**
     * Creates a new ConnectFourServer.
     * @param p1
     * @param p2
     * @throws IOException
     */
    public ConnectFourServer (Socket p1, Socket p2) throws IOException {
        this.player1 = new Duplexer(p1);
        this.player2 = new Duplexer(p2);
        this.game = new ConnectFour(6, 7);
    }

    /**
     * Makes a move in the Connect Four game.
     * @param tokens
     * @param req
     * @return String
     * @throws ConnectFourException
     */
    public String makeMove (String[] tokens, String req) throws ConnectFourException {
        String response = "";
        switch(tokens[0]){
            case "MOVE":
                game.makeMove(Integer.parseInt(tokens[1]));
                response = "MOVE_MADE " + tokens[1];
                break;
            default:
                response = "ERROR";
        }
        return response;
    }

    /**
     * The main loop the server will run.
     * @throws IOException
     */
    public void run () throws IOException {
        String request = "";
        int tracker = 0;
        Duplexer currPlayer = player1;
        Duplexer otherPlayer = player2;
        while (true) {
            if (tracker == 0) {
                currPlayer = player1;
            } else {
                currPlayer = player2;
            }
            currPlayer.send("MAKE_MOVE");
            request = currPlayer.read();
            System.out.println("RECV: " + request);

            String[] tokens = request.split(" ");

            String response = "";
            try {
                response = makeMove(tokens, request);
            } catch (ConnectFourException ce) {

            }
            System.out.println("SEND: " + response);
            player1.send(response);
            player2.send(response);
            if (this.game.hasWonGame()) {
                currPlayer.send("GAME_WON");
                System.out.println("SEND: GAME_WON");
                otherPlayer.send("GAME_LOST");
                System.out.println("SEND: GAME_LOST");
                break;
            } else if (game.hasTiedGame()) {
                currPlayer.send("GAME_TIED");
                System.out.println("SEND: GAME_TIED");
                otherPlayer.send("GAME_TIED");
                System.out.println("SEND: GAME_TIED");
                break;
            } else {
                if (tracker == 0) {
                    tracker = 1;
                } else {
                    tracker = 0;
                }
                if (tracker == 0) {
                    currPlayer = player1;
                    otherPlayer = player2;
                } else {
                    currPlayer = player2;
                    otherPlayer = player1;
                }
            }
        }
        player1.close();
        player2.close();
    }

    /**
     * Starts a new sever.
     *
     * @param args Used to specify the port on which the server should listen
     *             for incoming client connections.
     *
     * @throws ConnectFourException If there is an error starting the server.
     */
    public static void main(String[] args) throws ConnectFourException {

        if (args.length != 1) {
            System.out.println("Usage: java ConnectFourServer port");
            System.exit(1);
        }

        int port = Integer.parseInt(args[0]);

        ServerSocket server = null;

        try {
            server = new ServerSocket(port);
        } catch (IOException ie) {
            System.out.println("ERROR: PORT INVALID.");
        }

        System.out.println("Waiting for clients...");

        Socket player1 = null;
        Socket player2 = null;

        try {
            player1 = server.accept();
            player2 = server.accept();
        } catch (IOException ie) {

        }

        ConnectFourServer gameSever = null;

        try {
            gameSever = new ConnectFourServer(player1, player2);
        } catch (IOException ie) {

        }

        gameSever.player1.send("CONNECT");
        System.out.println("SEND: CONNECT");
        gameSever.player2.send("CONNECT");
        System.out.println("SEND: CONNECT");
        System.out.println("Starting game..." );


        try {
            gameSever.run();
        } catch (IOException ie) {

        }
    }
}