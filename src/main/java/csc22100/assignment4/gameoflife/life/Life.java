package csc22100.assignment4.gameoflife.life;

import csc22100.assignment4.gameoflife.Position;
import org.apache.commons.lang3.NotImplementedException;

import java.util.*;

public class Life  {
    private static final Random RANDOM = new Random();

    private final Map<Position, Boolean> cells;
    private final int numRows;
    private final int numCols;

    public Life(int numRows, int numCols) {
        this(numRows, numCols, 0.5);
    }

    public Life(int numRows, int numCols, double probAlive) {
        this.numRows = numRows;
        this.numCols = numCols;
        cells = new TreeMap<>(Comparator.comparing(Position::getX).thenComparing(Position::getY));
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                Position position = new Position(i, j);
                boolean isAlive = RANDOM.nextDouble() < probAlive;
                cells.put(position, isAlive);
            }
        }

    }

    public Life(int numRows, int numCols, Map<Position, Boolean> state) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.cells = state;
    }

    public Life evolve() {
        throw new NotImplementedException();
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    public Map<Position, Boolean> getCellStates() {
        return cells;
    }
}
