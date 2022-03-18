package csc22100.assignment4.gameoflife.life;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import csc22100.assignment4.gameoflife.Position;

import java.util.*;

/**
 * Simulator for Conway's Game of Life
 */
public class Life  {
    private static final Random RANDOM = new Random();

    private final TreeMap<Position, Boolean> cells;
    private final int numRows;
    private final int numCols;

    /**
     * Create a new simulation with the given number of (visible) rows and columns.
     *
     * The probability that any cell is alive is 0.5.
     *
     * @param numRows the number of *visible* rows
     * @param numCols the number of *visible* columns
     */
    public Life(int numRows, int numCols) {
        this(numRows, numCols, 0.5);
    }

    /**
     * Create a new simulation with the given number of rows and columns,
     * and the specified probability that a cell is alive.
     *
     * @param numRows the number of *visible* rows
     * @param numCols the number of *visible* cols
     * @param probAlive the probability a given cell is initialized to be alive
     */
    public Life(int numRows, int numCols, double probAlive) {
        this.numRows = numRows;
        this.numCols = numCols;
        cells = new TreeMap<>(Comparator.comparing(Position::getRow).thenComparing(Position::getColumn));
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                Position position = new Position(i, j);
                boolean isAlive = RANDOM.nextDouble() < probAlive;
                cells.put(position, isAlive);
            }
        }

    }

    /**
     * Create a simulation with a given state.
     *
     * @param numRows the number of *visible* rows
     * @param numCols the number of *visible* columns
     * @param state the starting state
     */
    public Life(int numRows, int numCols, Map<Position, Boolean> state) {
        this.numCols = numCols;
        this.numRows = numRows;
        this.cells = getEmptyMap();
        this.cells.putAll(state);
    }

    /**
     * Helper method for use in tests for constructing a simulation.
     *
     * *NOTE*: This method is sloppy around the numRows/numCols, but it won't matter.
     */
    @VisibleForTesting
    public static Life fromArray(Boolean[][] arr, int firstRow, int firstCol) {
        Preconditions.checkArgument(firstRow == firstCol,
                "Arguments firstRow and firstCol must be equal.");
        int numRows = arr.length;
        int numCols = arr[0].length;
        Map<Position, Boolean> cellMap = getEmptyMap();
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                cellMap.put(new Position(firstRow + i, firstCol + j), arr[i][j]);
            }
        }

        return new Life(numRows, numCols, cellMap);
    }

    @VisibleForTesting
    public static Life fromArray(Boolean[][] arr) {
        return fromArray(arr, 0,0);
    }

    private static TreeMap<Position, Boolean> getEmptyMap() {
       return new TreeMap<>(Comparator.comparing(Position::getRow).thenComparing(Position::getColumn));
    }

    public Life evolve() {
        Map<Position, Boolean> nextGen = getEmptyMap();
    
        // TODO: Populate the nextGen map. Don't forget to add the "boundary" cells.
        // You can use cellMap.firstKey() and cellMap.lastKey() to get the top left and
        // bottom right cells of the current map.

        return new Life(numRows, numCols, nextGen);
    }

    /**
     * Get the number of *displayed* rows.
     */
    public int getNumRows() {
        return numRows;
    }

    /**
     * Get the number of *displayed* columns.
     */
    public int getNumCols() {
        return numCols;
    }

    /**
     * Get the cell states.
     */
    public Map<Position, Boolean> getCellStates() {
        return cells;
    }
}
