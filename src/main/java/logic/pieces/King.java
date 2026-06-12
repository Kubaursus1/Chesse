package logic.pieces;

import logic.Player;
import utils.Coordinates;
import utils.Directions;

import java.util.Collection;
import java.util.EnumSet;
import java.util.List;

public class King extends Piece{
    public King(Player owner, Coordinates coordinates) {
        super(owner, coordinates, '♚', '♔');
    }

    @Override
    public Collection<Coordinates> getAllValidMoves() {
        return getBoard().getSquaresInRange(this, 1,
                EnumSet.of(Directions.DIAGONAL,Directions.HORIZONTAL, Directions.VERTICAL), false);
    }

    @Override
    public Collection<Coordinates> getAllValidCaptures() {
        return getBoard().getSquaresInRange(this, 1,
                EnumSet.of(Directions.DIAGONAL,Directions.HORIZONTAL, Directions.VERTICAL), true);
    }
}
