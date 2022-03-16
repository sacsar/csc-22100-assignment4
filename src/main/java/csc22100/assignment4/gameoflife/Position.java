package csc22100.assignment4.gameoflife;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * Clas representing a cell position
 */
public class Position {
    private final int r;
    private final int c;

    public Position(int row, int column) {
        this.r = row;
        this.c = column;
    }

    public int getRow() {
        return r;
    }

    public int getColumn() {
        return c;
    }

    public Position getNeighbor(Direction direction) {
        switch (direction) {
            case EAST:
                return new Position(r, c + 1);
            case SOUTHEAST:
                return new Position(r + 1, c + 1);
            case SOUTH:
                return new Position(r + 1, c);
            case SOUTHWEST:
                return new Position(r + 1, c - 1);
            case WEST:
                return new Position(r,  c - 1);
            case NORTHWEST:
                return new Position(r - 1, c - 1);
            case NORTH:
                return new Position(r - 1, c);
            case NORTHEAST:
                return new Position(r - 1, c + 1);
        }
        throw new IllegalStateException(String.format("Unknown direction %s", direction));
    }

    public Set<Position> getAllNeigbors() {
        return Arrays.stream(Direction.values()).map(this::getNeighbor).collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Position.class.getSimpleName() + "[", "]")
                .add("x=" + r)
                .add("y=" + c)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return r == position.r && c == position.c;
    }

    @Override
    public int hashCode() {
        return Objects.hash(r, c);
    }
}
