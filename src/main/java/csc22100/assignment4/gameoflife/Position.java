package csc22100.assignment4.gameoflife;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * Clas representing a cell position
 */
public class Position {
    private final int x;
    private final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Position getNeighbor(Direction direction) {
        switch (direction) {
            case EAST:
                return new Position(x + 1, y);
            case SOUTHEAST:
                return new Position(x + 1, y + 1);
            case SOUTH:
                return new Position(x, y + 1);
            case SOUTHWEST:
                return new Position(x - 1, y + 1);
            case WEST:
                return new Position(x - 1, y);
            case NORTHWEST:
                return new Position(x - 1, y - 1);
            case NORTH:
                return new Position(x, y - 1);
            case NORTHEAST:
                return new Position(x + 1, y - 1);
        }
        throw new IllegalStateException(String.format("Unknown direction %s", direction));
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Position.class.getSimpleName() + "[", "]")
                .add("x=" + x)
                .add("y=" + y)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
