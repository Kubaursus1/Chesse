package utils;

import logic.Board;

public record Coordinates(int column, int row) {

    public Coordinates {
        if( column < 0 || column > Board.BOARD_SIZE - 1) {
            throw new IllegalArgumentException("column");
        }
        if( row < 0 || row > Board.BOARD_SIZE - 1) {
            throw new IllegalArgumentException("row");
        }
    }
}
