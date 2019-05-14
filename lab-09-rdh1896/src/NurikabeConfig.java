import javax.swing.event.MenuKeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOError;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;


/**
 * A class to represent a single configuration in the Nurikabe puzzle.
 *
 * @author Sean Strout @ RITCS
 * @author Russell Harvey
 */
public class NurikabeConfig implements Configuration {


    /**
     * Private class to make holding onto a row/column easier
     *
     * @author Russell Harvey
     */
    private class Location {
        /** Row of the Location */
        int row;
        /** Column of the Location */
        int column;
        /** Value at the Location */
        String tile;

        /**
         * Creates a new Location
         * @param row
         * @param column
         * @param tile
         */
        public Location (int row, int column, String tile) {
            this.row = row;
            this.column = column;
            this.tile = tile;
        }

        /**
         * Equals function for Location
         * @param l
         * @return
         */
        @Override
        public boolean equals (Object l) {
            if (l instanceof Location) {
                Location loc = (Location) l;
                if (loc.tile.equals(this.tile) && loc.row == this.row && loc.column == this.column) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }

        }
    }

    /** Empty location marker */
    private static final String EMPTY = ".";
    /** Island location marker */
    private static final String ISLAND = "#";
    /** Sea location marker */
    private static final String SEA = "@";

    /** Total amount of rows */
    private int rows;
    /** Total amount of columns */
    private int cols;
    /** Total amount of seas */
    private int seas;
    /** Total amount of islands */
    private int islands;

    /** The game board */
    private Location[][] tiles;
    /** Location of number tiles */
    private ArrayList<Location> numbers;
    /** Current row */
    private int row;
    /** Current column */
    private int col;



    /**
     * Construct the initial configuration from an input file whose contents
     * are, for example:<br>
     * <tt><br>
     * 3 3          # rows columns<br>
     * 1 . #        # row 1, .=empty, 1-9=numbered island, #=island, &#64;=sea<br>
     * &#64; . 3    # row 2<br>
     * 1 . .        # row 3<br>
     * </tt><br>
     * @param filename the name of the file to read from
     * @throws FileNotFoundException if the file is not found
     */
    public NurikabeConfig(String filename) throws FileNotFoundException {
        try (Scanner in = new Scanner(new File(filename))) {
            String firstLine = in.nextLine();
            String[] tokens = firstLine.split(" ");
            this.rows = Integer.parseInt(tokens[0]);
            this.cols = Integer.parseInt(tokens[1]);
            this.tiles = new Location[rows][cols];
            this.numbers = new ArrayList<>();
            for (int i = 0; i < rows; i++) {
                String line = in.nextLine();
                String[] tkns = line.split(" ");
                for (int j = 0; j < cols; j++) {
                    Location l = new Location(i, j, tkns[j]);
                    this.tiles[i][j] = l;
                    if (!tkns[j].equals(EMPTY)) {
                        numbers.add(l);
                    }
                }
            }
            this.row = 0;
            this.col = 0;
            this.seas = 0;
            this.islands = 0;
            for (Location loc : this.numbers) {
                int temp = Integer.parseInt(loc.tile);
                this.islands += temp;
            }
        } catch (FileNotFoundException ie) {
            System.out.println("Error: invalid file.");
        }

    }

    /**
     * The copy constructor takes a config, other, and makes a full "deep" copy
     * of its instance data.
     *
     * @param other the config to copy
     */
    protected NurikabeConfig(NurikabeConfig other) {
        this.rows = other.rows;
        this.cols = other.cols;
        this.row = other.row;
        this.col = other.col;
        this.islands = other.islands;
        this.seas = other.seas;

        this.numbers = new ArrayList<>();
        for (Location l : other.numbers) {
            this.numbers.add(l);
        }

        this.tiles = new Location[rows][cols];

        for (int i = 0; i < this.rows; i++){
            for (int j = 0; j < this.cols; j++) {
                this.tiles[i][j] = new Location(i, j, other.tiles[i][j].tile);
            }
        }


    }

    /**
     * Gets the successors of the current configurations
     * @return Collection<Configuration></Configuration>
     */
    @Override
    public Collection<Configuration> getSuccessors() {
        List<Configuration> successors = new LinkedList<>();
        for (int j = col; j < cols; j = col) {
            if (!this.tiles[row][j].tile.equals(EMPTY)) {
                col++;
                if(col == cols) {
                    row++;
                    if (row == rows) {
                        return new LinkedList<>();
                    }
                    col = 0;
                }
            } else {
                NurikabeConfig c1 = new NurikabeConfig(this);
                NurikabeConfig c2 = new NurikabeConfig(this);
                c1.tiles[c1.row][c1.col].tile = ISLAND;
                c2.tiles[c2.row][c2.col].tile = SEA;
                c2.seas++;
                successors.add(c1);
                successors.add(c2);
                return successors;
            }
        }

        return new LinkedList<>();
    }

    /**
     * If a water tile has two or more connected water tiles, check the diagonal
     * tiles for a water tile.
     * @return boolean
     */
    public boolean noPools() {
        if (!this.tiles[this.row][this.col].tile.equals(SEA)) {
            return true;
        } else {
            // Establish Cardinal Directions
            Location north = new Location(0, 0, "TEMP");
            Location south = new Location(0, 0, "TEMP");
            Location east = new Location(0, 0, "TEMP");
            Location west = new Location(0, 0, "TEMP");

            // Get Tiles at Cardinal Directions
            if (this.row != 0) {
                north = this.tiles[this.row - 1][this.col];
            }
            if (this.row + 1 < rows) {
                south = this.tiles[this.row + 1][this.col];
            }
            if (this.col + 1 < cols) {
                east = this.tiles[this.row][this.col + 1];
            }
            if (this.col != 0) {
                west = this.tiles[this.row][this.col - 1];
            }

            // Check Diagonals
            if (north.tile.equals(SEA) && west.tile.equals(SEA)) {
                if (tiles[row - 1][col - 1].tile.equals(SEA)) {
                    return false;
                } else {
                    return true;
                }
            }
            if (north.tile.equals(SEA) && east.tile.equals(SEA)) {
                if (tiles[row - 1][col + 1].tile.equals(SEA)) {
                    return false;
                } else {
                    return true;
                }
            }
            if (south.tile.equals(SEA) && west.tile.equals(SEA)) {
                if (tiles[row + 1][col - 1].tile.equals(SEA)) {
                    return false;
                } else {
                    return true;
                }
            }
            if (south.tile.equals(SEA) && east.tile.equals(SEA)) {
                if (tiles[row + 1][col + 1].tile.equals(SEA)) {
                    return false;
                } else {
                    return true;
                }
            }
        }
        return true;
    }

    /**
     * Based on the water tile count, pick any water tile and check all adjacent
     * water tiles to insure they add up to the count.
     * @return boolean
     */
    public boolean seasConnected() {
        Location startLoc = null;
        int i = 0;
        int j = 0;
        while (true) {
            if (i >= rows || j >= cols) {
                return true;
            }
            Location l = this.tiles[i][j];
            if (l.tile.equals(SEA)) {
                startLoc = l;
                break;
            } else {
                if (j < this.cols - 1) {
                    j++;
                } else {
                    i++;
                    j = 0;
                }
            }
        }

        Queue<Location> queue = new LinkedList<>();
        ArrayList<Location> visited = new ArrayList<>();
        Location currLoc = startLoc;
        int counter = 0;

        while (true) {

            // Establish Cardinal Directions
            Location north = new Location(0, 0, "TEMP");
            Location south = new Location(0, 0, "TEMP");
            Location east = new Location(0, 0, "TEMP");
            Location west = new Location(0, 0, "TEMP");

            // Get Tiles at Cardinal Directions
            if (currLoc.row != 0) {
                north = this.tiles[currLoc.row - 1][currLoc.column];
            }
            if (currLoc.row + 1 < rows) {
                south = this.tiles[currLoc.row + 1][currLoc.column];
            }
            if (currLoc.column + 1 < cols) {
                east = this.tiles[currLoc.row][currLoc.column + 1];
            }
            if (currLoc.column != 0) {
                west = this.tiles[currLoc.row][currLoc.column - 1];
            }

            // Check if visited
            for (Location visit : visited) {
                if (north.equals(visit)) {
                    north = new Location(0, 0, "TEMP");
                }
                if (south.equals(visit)) {
                    south = new Location(0, 0, "TEMP");
                }
                if (east.equals(visit)) {
                    east = new Location(0, 0, "TEMP");
                }
                if (west.equals(visit)) {
                    west = new Location(0, 0, "TEMP");
                }
            }

            // Check and Add Neighbors
            if (north.tile.equals(SEA)) {
                queue.add(north);
            }
            if (south.tile.equals(SEA)) {
                queue.add(south);
            }
            if (east.tile.equals(SEA)) {
                queue.add(east);
            }
            if (west.tile.equals(SEA)) {
                queue.add(west);
            }

            // End Loop
            if (queue.isEmpty()) {
                counter++;
                break;
            } else {
                visited.add(currLoc);
                counter++;
                currLoc = ((LinkedList<Location>) queue).getFirst();
                ((LinkedList<Location>) queue).removeFirst();
            }
        }

        if (counter != seas && seas != 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Uses a counter to check each numbered tile, if the number of connected islands
     * is greater than the required the solution is invalid.
     * @return boolean
     */
    public boolean islandsValid() {
        for (Location l : numbers) {
            int locNum = Integer.parseInt(l.tile);

            Queue<Location> queue = new LinkedList<>();
            ArrayList<Location> visited = new ArrayList<>();
            Location currLoc = l;
            visited.add(currLoc);
            int counter = 0;

            while (true) {

                // Establish Cardinal Directions
                Location north = new Location(0, 0, "TEMP");
                Location south = new Location(0, 0, "TEMP");
                Location east = new Location(0, 0, "TEMP");
                Location west = new Location(0, 0, "TEMP");

                // Get Tiles at Cardinal Directions
                if (currLoc.row != 0) {
                    north = this.tiles[currLoc.row - 1][currLoc.column];
                }
                if (currLoc.row + 1 < rows) {
                    south = this.tiles[currLoc.row + 1][currLoc.column];
                }
                if (currLoc.column + 1 < cols) {
                    east = this.tiles[currLoc.row][currLoc.column + 1];
                }
                if (currLoc.column != 0) {
                    west = this.tiles[currLoc.row][currLoc.column - 1];
                }

                // Check if visited
                for (Location visit : visited) {
                    if (north.equals(visit)) {
                        north = new Location(0, 0, "TEMP");
                    }
                    if (south.equals(visit)) {
                        south = new Location(0, 0, "TEMP");
                    }
                    if (east.equals(visit)) {
                        east = new Location(0, 0, "TEMP");
                    }
                    if (west.equals(visit)) {
                        west = new Location(0, 0, "TEMP");
                    }
                }

                // Check and Add Neighbors
                if (north.tile.equals(ISLAND)) {
                    queue.add(north);
                    visited.add(north);
                }
                if (south.tile.equals(ISLAND)) {
                    queue.add(south);
                    visited.add(south);
                }
                if (east.tile.equals(ISLAND)) {
                    queue.add(east);
                    visited.add(east);
                }
                if (west.tile.equals(ISLAND)) {
                    queue.add(west);
                    visited.add(west);
                }

                // End Loop
                if (queue.isEmpty()) {
                    counter++;
                    break;
                } else {
                    counter++;
                    currLoc = ((LinkedList<Location>) queue).getFirst();
                    ((LinkedList<Location>) queue).removeFirst();
                }
            }
            if (counter > locNum) {
                return false;
            } else {
                assert true;
            }
        }

        int total = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (!this.tiles[i][j].tile.equals(SEA) && !this.tiles[i][j].tile.equals(EMPTY)) {
                    total++;
                } else {
                    assert true;
                }
            }
        }

        return total <= this.islands;

    }

    /**
     * Check all numbered tiles to see if any are connected to each other.
     * @return boolean
     */
    public boolean numbersConnected() {
        for (Location l : numbers) {
            int locNum = Integer.parseInt(l.tile);

            Queue<Location> queue = new LinkedList<>();
            ArrayList<Location> visited = new ArrayList<>();
            Location currLoc = l;

            while (true) {

                // Establish Cardinal Directions
                Location north = new Location(0, 0, "TEMP");
                Location south = new Location(0, 0, "TEMP");
                Location east = new Location(0, 0, "TEMP");
                Location west = new Location(0, 0, "TEMP");

                // Get Tiles at Cardinal Directions
                if (currLoc.row != 0) {
                    north = this.tiles[currLoc.row - 1][currLoc.column];
                }
                if (currLoc.row + 1 < rows) {
                    south = this.tiles[currLoc.row + 1][currLoc.column];
                }
                if (currLoc.column + 1 < cols) {
                    east = this.tiles[currLoc.row][currLoc.column + 1];
                }
                if (currLoc.column != 0) {
                    west = this.tiles[currLoc.row][currLoc.column - 1];
                }

                // Check if visited
                for (Location visit : visited) {
                    if (north.equals(visit)) {
                        north = new Location(0, 0, "TEMP");
                    }
                    if (south.equals(visit)) {
                        south = new Location(0, 0, "TEMP");
                    }
                    if (east.equals(visit)) {
                        east = new Location(0, 0, "TEMP");
                    }
                    if (west.equals(visit)) {
                        west = new Location(0, 0, "TEMP");
                    }
                }

                // Check for Other Numbers
                for (Location n : numbers) {
                    if (north.equals(n) && !north.equals(l)) {
                        return true;
                    }
                    if (south.equals(n) && !south.equals(l)) {
                        return true;
                    }
                    if (east.equals(n) && !east.equals(l)) {
                        return true;
                    }
                    if (west.equals(n) && !west.equals(l)) {
                        return true;
                    }
                }

                // Check and Add Neighbors
                if (north.tile.equals(ISLAND)) {
                    queue.add(north);
                }
                if (south.tile.equals(ISLAND)) {
                    queue.add(south);
                }
                if (east.tile.equals(ISLAND)) {
                    queue.add(east);
                }
                if (west.tile.equals(ISLAND)) {
                    queue.add(west);
                }

                // End Loop
                if (queue.isEmpty()) {
                    break;
                } else {
                    visited.add(currLoc);
                    currLoc = ((LinkedList<Location>) queue).getFirst();
                    ((LinkedList<Location>) queue).removeFirst();
                }
            }
        }
        return false;
    }

    /**
     * Checks if the islands are correct
     * @param l
     * @return boolean
     */
    public boolean islandsCorrect (Location l) {
        int numIslands = Integer.parseInt(l.tile);

        Queue<Location> queue = new LinkedList<>();
        ArrayList<Location> visited = new ArrayList<>();
        Location currLoc = l;
        int counter = 0;

        while (true) {
            // Establish Cardinal Directions
            Location north = new Location(0, 0, "TEMP");
            Location south = new Location(0, 0, "TEMP");
            Location east = new Location(0, 0, "TEMP");
            Location west = new Location(0, 0, "TEMP");

            // Get Tiles at Cardinal Directions
            if (currLoc.row != 0) {
                north = this.tiles[currLoc.row - 1][currLoc.column];
            }
            if (currLoc.row + 1 < rows) {
                south = this.tiles[currLoc.row + 1][currLoc.column];
            }
            if (currLoc.column + 1 < cols) {
                east = this.tiles[currLoc.row][currLoc.column + 1];
            }
            if (currLoc.column != 0) {
                west = this.tiles[currLoc.row][currLoc.column - 1];
            }

            // Check if visited
            for (Location visit : visited) {
                if (north.equals(visit)) {
                    north = new Location(0, 0, "TEMP");
                }
                if (south.equals(visit)) {
                    south = new Location(0, 0, "TEMP");
                }
                if (east.equals(visit)) {
                    east = new Location(0, 0, "TEMP");
                }
                if (west.equals(visit)) {
                    west = new Location(0, 0, "TEMP");
                }
            }

            if (north.tile.equals(ISLAND)) {
                queue.add(north);
                visited.add(north);
            }
            if (south.tile.equals(ISLAND)) {
                queue.add(south);
                visited.add(south);
            }
            if (west.tile.equals(ISLAND)) {
                queue.add(west);
                visited.add(west);
            }
            if (east.tile.equals(ISLAND)) {
                queue.add(east);
                visited.add(east);
            }

            if (queue.isEmpty()) {
                counter++;
                break;
            } else {
                counter++;
                currLoc = ((LinkedList<Location>) queue).getFirst();
                ((LinkedList<Location>) queue).removeFirst();
            }


        }

        return counter == numIslands;
    }

    /**
     * Is the configuration valid
     * @return boolean
     */
    @Override
    public boolean isValid() {
        if (!noPools()) {
            return false;
        }
        if (!islandsValid()) {
            return false;
        }
        if (numbersConnected()) {
            return false;
        }
        if (isFull()) {
            if (seasConnected()) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * Is the board full?
     * @return boolean
     */
    public boolean isFull() {
        if (!this.tiles[rows-1][cols-1].tile.equals(EMPTY)) {
            if (this.tiles[rows-1][cols-2].tile.equals(EMPTY)) {
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * Is the board completed?
     * @return boolean
     */
    @Override
    public boolean isGoal() {
        if (isFull()) {
            for (Location l : numbers) {
                Boolean b = islandsCorrect(l);
                if (b) {
                    assert true;
                } else {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Returns a string representation of the puzzle, e.g.: <br>
     * <tt><br>
     * 1 . #<br>
     * &#64; . 3<br>
     * 1 . .<br>
     * </tt><br>
     */
    @Override
    public String toString() {
        String output = "";
        for (int i = 0; i < rows; i++) {
            String currRow = "";
            for (int j = 0; j < cols; j++) {
                if (j < cols - 1) {
                    currRow += this.tiles[i][j].tile + " ";
                } else {
                    currRow += this.tiles[i][j].tile;
                }
            }
            output += currRow + "\n";

        }
        return output;
    }
}
