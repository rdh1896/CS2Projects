package common;
import java.util.ArrayList;

/**
 * WAMGame class for Whack-a-Mole
 *  Handles the game state and whack messages from clients
 *
 * @author Russell Harvey
 */

public class WAMGame extends Thread{

    /** Amount of rows in the game */
    private int rows;
    /** Amount of columns in the game */
    private int cols;
    /** The board the moles sit on */
    private Mole[][] board;
    /** The amount of players in the game */
    private int numPlayers;
    /** A list of active players */
    private WAMPlayer[] players;
    /** Scores for players */
    private int[] scores;
    /** Is the game running? */
    private boolean running;

    /**
     * Creates a new instance of WAMGame
     * @param rows
     * @param cols
     * @param numPlayers
     */
    public WAMGame (int rows, int cols, int numPlayers) {
        this.running = false;
        this.rows = rows;
        this.cols = cols;
        this.numPlayers = numPlayers;
        this.scores = new int[numPlayers];
        this.board = new Mole[rows][cols];
        int counter = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = new Mole(counter, this);
                counter++;
            }
        }
    }

    /**
     * Run function that starts a thread for each player
     */
    @Override
    public void run() {
        for (WAMPlayer p : players) {
            Thread t = new Thread(() -> playerLoop(p));
            t.start();
        }
    }

    /**
     * The main loop for each player in WAMGame
     * @param p
     */
    public void playerLoop (WAMPlayer p) {
        while (this.running) {
            String request = p.getComms().read(0);
            String[] tokens = request.split(" ");
            if (tokens[0].equals("WHACK")) {
                System.out.println(request);
                int moleNum = Integer.parseInt(tokens[1]);
                int row = moleNum / cols;
                int col = moleNum % cols;
                Mole m = board[row][col];
                synchronized (board) {
                    if (m.getUp()) {
                        this.scores[Integer.parseInt(tokens[2])] += 2;
                        updatePlayers(Integer.parseInt(tokens[1]), true);
                        //m.setUp(false);
                    } else {
                        this.scores[Integer.parseInt(tokens[2])] -= 1;
                        updatePlayers(Integer.parseInt(tokens[1]), false);
                    }
                }
            }
        }
    }

    /**
     * Changes if the game is running or not
     * @param running
     */
    public void setRunning(boolean running) {
        this.running = running;
        if (running) {
            this.start();
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j].setRunning(running);
                if (running) {
                    board[i][j].start();
                }
            }
        }
    }

    /**
     * Returns the amount of rows
     * @return rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * Returns the amount of columns
     * @return cols
     */
    public int getCols() {
        return cols;
    }

    /**
     * Gets the number of players
     * @return numPlayers
     */
    public int getNumPlayers() {
        return numPlayers;
    }

    /**
     * Sets the players
     * @param players
     */
    public void setPlayers(WAMPlayer[] players) {
        this.players = players;
    }

    /**
     * Gets players
     * @return players
     */
    public WAMPlayer[] getPlayers() {
        return players;
    }

    /**
     * Gets scores
     * @return scores
     */
    public int[] getScores() {
        return scores;
    }

    /**
     * Updates the players when a whack has been recieved and is valid
     * @param moleNum
     */
    public synchronized void updatePlayers (int moleNum, boolean decider) {
        if (decider) {
            for (WAMPlayer p : players) {
                p.moleDown(moleNum);
                p.score();
            }
        } else {
            for (WAMPlayer p : players) {
                p.score();
            }
        }

    }

    /**
     * Tells users who has won and lost
     */
    public void declareVictor () {
        int[] finalScores = scores;
        int[] playerNum = new int[players.length];
        ArrayList<Integer> winners = new ArrayList<>();
        for (int i = 0; i < finalScores.length; i++) {
            if (winners.size() == 0) {
                winners.add(i);
            } else {
                if (finalScores[i] > finalScores[winners.get(0)]) {
                    winners.clear();
                    winners.add(i);
                } else if (finalScores[i] == finalScores[winners.get(0)]) {
                    winners.add(i);
                } else {
                    WAMPlayer p = players[i];
                    p.gameLost();
                }
            }
        }
        if (winners.size() > 1) {
            for (Integer num : winners) {
                WAMPlayer tie = players[num];
                tie.gameTied();
            }
        } else {
            WAMPlayer winner = players[winners.get(0)];
            winner.gameWon();
        }
    }
}
