package csc22100.assignment4.gameoflife.life;

import csc22100.assignment4.gameoflife.Position;
import org.apache.commons.lang3.NotImplementedException;

import java.util.*;

public class Life  {
    private static final Random RANDOM = new Random();

    private final Map<Position, Boolean> cells;
    private final int maxX;
    private final int maxY;

    public Life(int maxX, int maxY) {
        this(maxX, maxY, 0.5);
    }

    public Life(int maxX, int maxY, double probAlive) {
        this.maxX = maxX;
        this.maxY = maxY;
        cells = new TreeMap<>(Comparator.comparing(Position::getX).thenComparing(Position::getY));
        for (int i = 0; i < maxX; i++) {
            for (int j = 0; j < maxY; j++) {
                Position position = new Position(i, j);
                boolean isAlive = RANDOM.nextDouble() < probAlive;
                cells.put(position, isAlive);
            }
        }

    }

    public Life(int maxX, int maxY, Map<Position, Boolean> state) {
        this.maxX = maxX;
        this.maxY = maxY;
        this.cells = state;
    }

    public Life evolve() {
        throw new NotImplementedException();
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    public Map<Position, Boolean> getCellStates() {
        return cells;
    }
}
