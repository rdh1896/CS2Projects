package client.gui;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * The Model for the Client Side of Whack-a-Mole
 *  Handles the Model of MVC
 *
 * @author Russell Harvey
 * @author Eric Gao
 */

public class WAMBoard {

    /** Rows on the board */
    private int rows;
    /** Columns on the board */
    private int cols;
    /** The players in the game */
    private List<Integer> players;
    /** Scores for each player */
    private int[] scores;
    /** This player's number */
    private int playerNum;
    /** What is the status of the game? */
    private Status status;
    /** Observers looking at the board */
    private List<Observer<WAMBoard>> observers;
    /** A list for the moles */
    private Boolean[] moles;
    /** A reference to the NetworkClient */
    private WAMNetworkClient server;
    /** Used to update scores */
    private int[] updatedScores;

    /**
     * All possible statuses in the game.
     */
    public enum Status {
        RUNNING, WON, LOST, TIE, ERROR;

        private String message = null;

        public void setMessage( String msg ) {
            this.message = msg;
        }

        @Override
        public String toString() {
            return super.toString() +
                    this.message == null ? "" : ( '(' + this.message + ')' );
        }
    }

    /**
     * Creates a new board.
     *
     * @param rows
     * @param cols
     * @param players
     * @param playerNum
     * @param server
     */
    public WAMBoard (int rows, int cols, int players, int playerNum, WAMNetworkClient server) {
        this.rows = rows;
        this.cols = cols;
        this.players = new ArrayList<>();
        for (int i = 0; i < players; i++) {
            this.players.add(i);
        }

        this.scores = new int[players];

        this.playerNum = playerNum;

        this.observers = new LinkedList<>();

        this.moles = new Boolean[this.rows * this.cols];
        for (int num = 0; num < this.rows * this.cols; num++) {

            this.moles[num] = false;

        }


        updatedScores = new int[this.players.size()];

        this.server = server;
        this.status = Status.RUNNING;
    }

    /**
     * Called by the view to add itself as an observer to this board.
     * @param observer The caller adding itself as an observer.
     */
    public void addObserver (Observer<WAMBoard> observer) {
        this.observers.add(observer);
    }

    /**
     * Alerts all observers to signal a change in the model.
     */
    public void alertObservers() {
        for (Observer<WAMBoard> obs: this.observers ) {
            obs.update(this);
        }
    }

    /**
     * Gets number of rows in mole board.
     * @return Number of rows.
     */
    public int getRows(){
        return rows;
    }

    /**
     * Gets number of columns in mole board.
     * @return Number of columns.
     */
    public int getCols(){
        return cols;
    }

    /**
     * Gets number of players.
     * @return Number of players.
     */
    public int getNumOfPlayers(){
        return players.size();
    }

    /**
     * Gets current game status.
     * @return Game status.
     */
    public Status getStatus() {return this.status;}

    /**
     * Gets player given player number.
     * @param pNum Player's number
     * @return player
     */
    public int getPlayer (int pNum) {
        return this.players.get(pNum);
    }

    public void updateScore(String[] score){
//        int[] updatedScores = new int[players.size()];

        int counter = 0;

        for (String s : score){
            int tempScore = Integer.parseInt(s);
            updatedScores[counter] = tempScore;
            counter++;
        }

        alertObservers();

    }

    public int[] getUpdatedScores(){
        return updatedScores;
    }


    public int getThisPlayer () {
        return playerNum;
    }

    /**
     * Returns boolean value of specific mole given its index (read left-to-right, top-to-bottom
     * in the grid).
     * @param num Mole index
     * @return Boolean value of mole (true means up, false means down).
     */
    public Boolean getMole(int num) {
        return moles[num];
    }

    /**
     * Tells the NetworkClient to send a whack message to the server.
     * @param num Index of mole whacked
     * @param player Player who whacked the mole
     */
    public void moleWhacked (int num, int player) {
        this.server.whack(num, player);
        alertObservers();
    }

    /**
     * Sets the specified mole as being up and notifies the observers.
     * @param mole The mole that is now up.
     */
    public void moleUp (int mole) {
        moles[mole] = true;
        alertObservers();
    }

    /**
     * Sets the specified mole as being down and notifies the observers.
     * @param mole The mole that is now down.
     */
    public void moleDown (int mole) {
        moles[mole] = false;
        alertObservers();
    }

    /**
     * Called when the game has been won by this player.
     */
    public void gameWon() {
        this.status = Status.WON;
        alertObservers();
    }

    /**
     * Called when the game has been won by another other player.
     */
    public void gameLost() {
        this.status = Status.LOST;
        alertObservers();
    }

    /**
     * Called when the game has been tied.
     */
    public void gameTied() {
        this.status = Status.TIE;
        alertObservers();
    }

    /**
     * The user they may close at any time
     */
    public void close() {
        alertObservers();
    }

}
