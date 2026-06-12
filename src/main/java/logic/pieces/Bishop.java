package logic.pieces;

import logic.Board;
import logic.Player;
import utils.Coordinates;
import utils.Directions;

import java.util.Collection;
import java.util.EnumSet;
import java.util.List;

public class Bishop extends Piece{
    public Bishop(Player owner, Coordinates coordinates) {
        super(owner, coordinates, '♝', '♗');
    }

    @Override
    public Collection<Coordinates> getAllValidMoves() {
        return getBoard().getSquaresInRange(this, Board.BOARD_SIZE, EnumSet.of(Directions.DIAGONAL),false);
    }

    @Override
    public Collection<Coordinates> getAllValidCaptures() {
        return getBoard().getSquaresInRange(this, Board.BOARD_SIZE, EnumSet.of(Directions.DIAGONAL), true);
    }
}
