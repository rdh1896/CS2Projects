package connectfour.gui;

import connectfour.ConnectFourException;
import connectfour.client.ConnectFourBoard;
import connectfour.client.ConnectFourNetworkClient;
import connectfour.client.Observer;
import connectfour.server.ConnectFour;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import connectfour.client.ConnectFourBoard.Move;
import connectfour.client.ConnectFourBoard.Status;
import javafx.scene.control.Label;

import java.util.List;

/**
 * A JavaFX GUI for the networked Connect Four game.
 *
 * @author James Heloitis @ RIT CS
 * @author Sean Strout @ RIT CS
 * @author Russell Harvey (rdh1896@rit.edu)
 */
public class ConnectFourGUI extends Application implements Observer<ConnectFourBoard> {

    /** The main stage for the UI to display the game. */
    private Stage stage;

    /** The Connect Four game the server talks to. */
    private ConnectFourBoard model;

    /** Connection to the server. */
    private ConnectFourNetworkClient server;

    /** Main GridPane for the Connect Four game. */
    private GridPane board;

    /** Main scene of the game. */
    private Scene scene;

    /** Image for an empty space. */
    public final Image EMPTY = new Image(getClass().getResourceAsStream("empty.png"));

    /** Image for a red space. */
    public final Image RED = new Image(getClass().getResourceAsStream("p2red.png"));

    /** Image for a black space. */
    public final Image BLACK = new Image(getClass().getResourceAsStream("p1black.png"));


    /**
     * Initializes the ConnectFourGUI by creating a model and a connection to the server.
     */
    @Override
    public void init() {
        try {
            // get the command line args
            List<String> args = getParameters().getRaw();

            // get host info and port from command line
            String host = args.get(0);
            int port = Integer.parseInt(args.get(1));

            // create the model
            this.model = new ConnectFourBoard();
            this.model.addObserver(this);

            // create the server connection
            try {
                this.server = new ConnectFourNetworkClient(host, port, this.model);
            } catch (ConnectFourException ce) {
                System.out.println(ce);
            }
        } catch(NumberFormatException e) {
            System.err.println(e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates the board from a GridPane.
     * @return The fresh Connect Four board.
     */
    public GridPane makeConnectFourBoard(){
        GridPane board = new GridPane();
        for (int row = 0; row < 6; row++) {
            for (int col=0; col < 7; col++) {
                Button button = new Button();
                button.setGraphic(new ImageView(EMPTY));
                int temp = col;
                button.setOnAction((event) -> {
                    if (model.isMyTurn() && model.isValidMove(temp)) {
                        server.sendMove(temp);
                    } else {
                        if (!model.isMyTurn()) {
                            System.out.println("Wait your turn!");
                        } else {
                            System.out.println("Column full.");
                        }
                    }
                });
                board.add(button, col, row);
            }
        }

        return board;
    }

    /**
     * Construct the layout for the game.
     *
     * @param stage container (window) in which to render the GUI
     * @throws Exception if there is a problem
     */
    public void start( Stage stage ) throws Exception {
        // TODO
        this.stage = stage;
        this.board = makeConnectFourBoard();
        this.scene = new Scene(board);
        stage.setTitle("Connect Four");
        stage.setScene(scene);
        stage.show();

        // TODO: call startListener() in ConnectFourNetworkClient here
        server.startListener();
    }

    /**
     * GUI is closing, so close the network connection. Server will get the message.
     */
    @Override
    public void stop() {
        // TODO
        server.close();
    }

    /**
     * Do your GUI updates here.
     */
    private void refresh() {
        // TODO
        switch (model.getStatus()) {
            case NOT_OVER:
                GridPane board = new GridPane();
                for (int row = 0; row < 6; row++) {
                    for (int col=0; col < 7; col++) {
                        Button button = new Button();
                        Move player = model.getContents(col, row);
                        if (player.equals(Move.NONE)) {
                            button.setGraphic(new ImageView(EMPTY));
                        } else if (player.equals(Move.PLAYER_ONE)) {
                            button.setGraphic(new ImageView(BLACK));
                        } else {
                            button.setGraphic(new ImageView(RED));
                        }
                        int temp = col;
                        button.setOnAction((event) -> {
                            if (model.isMyTurn() && model.isValidMove(temp)) {
                                server.sendMove(temp);
                            } else {
                                System.out.println("Wait your turn!");
                            }
                        });
                        board.add(button, col, row);
                    }
                }
                this.board = board;
                this.scene = new Scene(board);
                this.stage.setScene(scene);
                break;
            case I_WON:
                BorderPane bp = new BorderPane();
                Label l = new Label("CONGRATS, YOU WIN!!!");
                bp.setCenter(l);
                this.scene = new Scene(bp);
                this.stage.setScene(scene);
                break;
            case I_LOST:
                BorderPane bp2 = new BorderPane();
                Label l2 = new Label("You lose...");
                bp2.setCenter(l2);
                this.scene = new Scene(bp2);
                this.stage.setScene(scene);
                break;
            case TIE:
                BorderPane bp3 = new BorderPane();
                Label l3 = new Label("Tie.");
                bp3.setCenter(l3);
                this.scene = new Scene(bp3);
                this.stage.setScene(scene);
                break;
            case ERROR:
                BorderPane bp4 = new BorderPane();
                Label l4 = new Label("The game has encountered a fatal error. Please re-run ConnectFourGUI.");
                bp4.setCenter(l4);
                this.scene = new Scene(bp4);
                this.stage.setScene(scene);
                break;
        }
    }

    /**
     * Called by the model, client.ConnectFourBoard, whenever there is a state change
     * that needs to be updated by the GUI.
     *
     * @param connectFourBoard
     */
    @Override
    public void update(ConnectFourBoard connectFourBoard) {
        if ( Platform.isFxApplicationThread() ) {
            this.refresh();
        }
        else {
            Platform.runLater( () -> this.refresh() );
        }
    }

    /**
     * The main method expects the host and port.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java ConnectFourGUI host port");
            System.exit(-1);
        } else {
            Application.launch(args);
        }
    }
}
