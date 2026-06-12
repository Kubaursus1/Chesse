package logic.pieces;

import logic.Board;
import logic.Player;
import utils.Coordinates;
import utils.Directions;

import java.util.Collection;
import java.util.EnumSet;
import java.util.List;

public class Queen extends Piece{
    public Queen(Player owner, Coordinates coordinates) {
        super(owner, coordinates, '♛', '♕');
    }

    @Override
    public Collection<Coordinates> getAllValidMoves() {
        return getBoard().getSquaresInRange(this, Board.BOARD_SIZE,
                EnumSet.of(Directions.DIAGONAL, Directions.HORIZONTAL, Directions.VERTICAL), false);
    }

    @Override
    public Collection<Coordinates> getAllValidCaptures() {
        return getBoard().getSquaresInRange(this, Board.BOARD_SIZE,
                EnumSet.of(Directions.DIAGONAL, Directions.HORIZONTAL, Directions.VERTICAL), true);
    }
}
