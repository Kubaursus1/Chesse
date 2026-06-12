package utils;

import logic.Board;

import java.util.Optional;

public record Coordinates(int column, int row) {

    public static Coordinates add(Coordinates c1, Coordinates c2) {
        return new Coordinates(c1.column + c2.column, c1.row + c2.row);
    }
    public static Optional<Coordinates> tryAdd(Coordinates c1, Coordinates c2) {
        return tryCreate(c1.column + c2.column, c1.row + c2.row);
    }

    public static Coordinates mul(Coordinates coordinates, int v) {
        return new Coordinates(coordinates.column * v, coordinates.row * v);
    }
    public static  Optional<Coordinates>  tryMul(Coordinates coordinates, int v) {
        return tryCreate(coordinates.column * v, coordinates.row * v);
    }

    public static boolean isValid(int column, int row) {
        if (column < 0 || column > Board.BOARD_SIZE - 1) {
            return false;
        }
        if (row < 0 || row > Board.BOARD_SIZE - 1) {
            return false;
        }
        return true;
    }
    public static boolean isValid(Coordinates coordinates) {
        return isValid(coordinates.column, coordinates.row);
    }
    public static Optional<Coordinates> tryCreate(int column, int row)
    {
        if (!isValid(column, row))
            return Optional.empty();
        return Optional.of(new Coordinates(column, row));
    }
}
