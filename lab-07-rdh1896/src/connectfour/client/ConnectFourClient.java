package connectfour.client;

import connectfour.ConnectFourException;
import connectfour.ConnectFour;
import connectfour.Duplexer;

import java.util.Scanner;
import java.io.IOException;
import java.net.*;

/**
 * Represents the client side of a Connect Four game. Establishes a connection
 * with the server and then responds to requests from the server (often by
 * prompting the real user).
 *
 * @author Russell Harvey (rdh1896@rit.edu)
 */
public class ConnectFourClient {

    /**
     * Starts a new {@link ConnectFourClient}.
     *
     * @param args Used to specify the hostname and port of the Connect Four
     *             server through which the client will play.
     * @throws ConnectFourException If there is a problem creating the client
     *                              or connecting to the server.
     */
    public static void main(String[] args) throws ConnectFourException {
        if (args.length != 2) {
            System.out.println(
                    "Usage: java ConnectFourClient hostname port");
            System.exit(1);
        }

        String hostname = args[0];
        int port = Integer.parseInt(args[1]);

        Socket server = null;

        try {
             server = new Socket (hostname, port);
        } catch (IOException ie) {

        }

        ConnectFour game = new ConnectFour(6,7);

        Duplexer comms = null;

        try {
            comms = new Duplexer(server);
        } catch (IOException ie) {

        }

        boolean stop = false;

        while (stop == false) {
            if (!comms.isOpen()) {
                break;
            }
            String request = comms.read();
            String[] tokens = request.split(" ");
            switch (tokens[0]) {
                case ("CONNECT"):
                    System.out.println("Connected to server.");
                    break;
                case ("MAKE_MOVE"):
                    Scanner in = new Scanner(System.in);
                    System.out.print("Make a move: ");
                    String response = in.nextLine();
                    comms.send("MOVE " + response);
                    break;
                case ("MOVE_MADE"):
                    System.out.println("Move made at column " + tokens[1] + ".");
                    game.makeMove(Integer.parseInt(tokens[1]));
                    System.out.println(game);
                    break;
                case ("GAME_WON"):
                    System.out.println("You win!");
                    try {
                        comms.close();
                        stop = true;
                    } catch (IOException ie) {

                    }
                    break;
                case ("GAME_LOST"):
                    System.out.println("You lose.");
                    try {
                        comms.close();
                        stop = true;
                    } catch (IOException ie) {

                    }
                    break;
                case ("GAME_TIED"):
                    System.out.println("Tied!");
                    try {
                        comms.close();
                        stop = true;
                    } catch (IOException ie) {

                    }
                    break;
                default:
                    System.out.println("ERROR");
                    try {
                        comms.close();
                        stop = true;
                    } catch (IOException ie) {

                    }
                    break;
            }

        }

    }
}
