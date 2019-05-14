package server;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Simple Duplexer for Connection to the Server and Client
 *
 * @author Russell Harvey (rdh1896@rit.edu)
 * Based on lecture code given by Professor Herring
 */

public class Duplexer implements AutoCloseable {

    /** Socket being used for communication */
    private Socket socket;

    /** Reads incoming messages */
    private Scanner in;

    /** Sends outgoing messages */
    private PrintWriter out;

    /**
     * Creates a new Duplexer
     * @param socket
     * @throws IOException
     */
    public Duplexer(Socket socket) throws IOException {

        this.socket = socket;
        this.in = new Scanner (socket.getInputStream());
        this.out = new PrintWriter(socket.getOutputStream());

    }

    /**
     * Sends a message to a target
     * @param message
     * */
    public void send (String message) {
        out.println(message);
        out.flush();
    }

    /**
     * Reads a message from a server/client
     * @return String
     */
    public String read (int setting) {
        if (setting == 0) {
            try {
                return in.nextLine();
            } catch (NoSuchElementException ne) {
                return "";
            }
        } else if (setting == 1) {
            try {
                return in.next();
            } catch (NoSuchElementException ne) {
                return "";
            }
        } else if (setting == 2) {
            try {
                return in.nextLine().trim();
            } catch (NoSuchElementException ne) {
                return "";
            }
        }

        return in.nextLine();
    }

    /**
     * Closes the ongoing connection.
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        socket.close();
    }

    /**
     * Checks if the socket is open.
     * @return
     */
    public boolean isOpen () {
        return !socket.isClosed();
    }
}
