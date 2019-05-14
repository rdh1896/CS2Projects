package client.gui;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import java.util.List;

/**
 * The GUI for Whack-a-Mole
 *  Handles the View of MVC
 *
 * @author Eric Gao
 * @author Russell Harvey
 */

public class WAMUI extends Application implements Observer<WAMBoard> {

    /** A reference to the current scene being displayed */
    private Scene scene;
    /** A reference to the model being used */
    private WAMBoard model;
    /** A reference to the NetworkClient */
    private WAMNetworkClient server;
    /** BorderPane used in the GUI */
    public BorderPane access;
    /** Gridpane used to store moles */
    public GridPane getGrid;

    /**
     * Initializes the model and the client-side connection, and adds itself as an
     * observer to the model.
     */
    @Override
    public void init () {
        try {
            // get the command line args
            List<String> args = getParameters().getRaw();

            // get host info and port from command line
            String host = args.get(0);
            int port = Integer.parseInt(args.get(1));

            // create the server connection
            this.server = new WAMNetworkClient(host, port);
            this.server.startListener();

            System.out.println("Before Assertion");

            while (this.server.getModel() == null) {
                System.out.println("Waiting for model.");
                assert true;
            }

            System.out.println("After Assertion/Init");

            // get board format from server
            this.model = server.getModel();
            this.model.addObserver(this);

        } catch(NumberFormatException e) {
            System.err.println(e);
            throw new RuntimeException(e);
        }

    }

    /**
     * Creates the interface for the game.
     *
     * The mole holes are represented by a GridPane. The main game board is a BorderPane:
     * the gridpane goes in the center, a label representing game status goes on the
     * the top, and the scores for each of the players goes on the right.
     *
     * @param stage stage
     */
    public void start (Stage stage) {

        int columns = model.getCols();
        int rows = model.getRows();
        int numPlayers = model.getNumOfPlayers();

        BorderPane mainInterface = new BorderPane();
        GridPane moleGrid = new GridPane();
        HBox scoreBoard = new HBox(5);

        String tempScore = "Score: ";

        for (int i = 0; i < numPlayers; i++){
            if (i == numPlayers - 1) {
                tempScore += "0";
            } else {
                tempScore += "0 ";
            }
        }

        Label newLabel = new Label(tempScore);
        scoreBoard.getChildren().add(newLabel);

        //Creates the initial grid of moles and sets them all to "down".
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {

                int currentR = r;
                int currentC = c;
                int finC = columns;

                Button button = new Button();
                button.setMaxSize(150, 150);
                button.setMinSize(150, 150);


                button.setOnAction(ActionEvent -> {
                    model.moleWhacked((currentR * finC) + currentC, model.getThisPlayer());
                });


                Image img = new Image("client/gui/moledown.png");
                button.setGraphic(new ImageView(img));

                moleGrid.add(button, c, r);

            }
        }

            Label gameStatus = new Label("Waiting for connection ...");

            //Score, main board, and game status message has been set. Probably should
            //add a countdown timer for the length of the game as well.
            mainInterface.setCenter(moleGrid);
            mainInterface.setTop(gameStatus);
            mainInterface.setBottom(scoreBoard);

            this.scene = new Scene(mainInterface);

            this.access = mainInterface;
            this.getGrid = moleGrid;

            stage.setTitle("Whack-A-Mole!");
            stage.setScene(scene);
            stage.show();

    }

    /**
     * Ends the network connection when the GUI is closed.
     */
    @Override
    public void stop () {
        server.close();
    }

    /**
     * Changes the GUI view depending on the changes in the model. If the game
     * has been ended in any way (WIN, LOSE, TIE, ERROR), it notifies the player.
     * Otherwise if the game continues to run, it iterates through each mole hole
     * to check if any moles are popping up or going down, and updates accordingly.
     */
    public void refresh () {
        switch (this.model.getStatus()) {
            case RUNNING:
                // Update scores.
                // Redraw board, check each mole's boolean value.
                // If true, set to UP. If false, set to down.
                // Use counter to keep track of moles.

                int numPlayers = model.getNumOfPlayers();

                HBox scoreBoard = new HBox(5);
                String tempScore = "Score: ";

                int[] newScores = model.getUpdatedScores();

                for (int i = 0; i < numPlayers; i++){
                        tempScore += newScores[i] + " ";
                }

                Label newLabel = new Label(tempScore);
                scoreBoard.getChildren().add(newLabel);
                access.setBottom(scoreBoard);


                access.setTop(new Label("Whack those moles!!!"));
                GridPane grid = new GridPane();

                int moleIndex;

                int rows = model.getRows();
                int cols = model.getCols();

                for (int r = 0; r < rows; r++){
                    for(int c = 0; c < cols; c++){
                        moleIndex = (r*cols) + c;
                        if(model.getMole(moleIndex)){

                            Button b = new Button();

                            int finR = r;
                            int finC = cols;
                            int currentC = c;

                            b.setOnAction(ActionEvent -> {
                                model.moleWhacked((finR * finC) + currentC, model.getThisPlayer());
                            });

                            b.setMaxSize(150, 150);
                            b.setMinSize(150, 150);

                            Image img = new Image("client/gui/moleup.png");
                            b.setGraphic(new ImageView(img));
                            grid.add(b, c, r);
                        }
                        else {
                            Button b = new Button();

                            int finR = r;
                            int finC = cols;
                            int currentC = c;

                            b.setOnAction(ActionEvent -> {
                                model.moleWhacked((finR * finC) + currentC, model.getThisPlayer());
                            });

                            b.setMaxSize(150, 150);
                            b.setMinSize(150, 150);
                            Image img = new Image("client/gui/moledown.png");
                            b.setGraphic(new ImageView(img));
                            grid.add(b, c, r);
                        }
                    }
                }
                this.getGrid = grid;
                this.access.setCenter(getGrid);
                this.scene = new Scene(access);
                break;
            case WON:
                access.setTop(new Label("You won! Awesome!"));
                break;
            case LOST:
                access.setTop(new Label("You lost! Aww!"));
                break;
            case TIE:
                access.setTop(new Label("The game has been tied!"));
                break;
            case ERROR:
                access.setTop(new Label("Uh oh, we encountered an error."));
                break;
        }
    }

    /**
     * Called by the model when the view needs to be updated.
     * @param wamBoard The board calling the update method.
     */
    @Override
    public void update (WAMBoard wamBoard) {
        if ( Platform.isFxApplicationThread() ) {
            this.refresh();
        }
        else {
            Platform.runLater( () -> this.refresh() );
        }
    }

    /**
     * The main expects the host and port as command line args.
     * @param args Host and port.
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java WAMUI host port");
            System.exit(-1);
        } else {
            Application.launch(args);
        }
    }
}
