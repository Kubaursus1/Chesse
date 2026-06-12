package logic.pieces;

import logic.Player;
import utils.Coordinates;
import utils.Directions;

import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;

public class Pawn extends Piece{
   public Pawn(Player owner, Coordinates coordinates) {
        super(owner, coordinates, '♟', '♙');
    }

    @Override
    public Collection<Coordinates> getAllValidMoves() {

        if (this.getOwner().getColor() == Player.COLOR.WHITE) {
            if (!hasMoved()) {
                return getMoves(1, (a, b) -> a > b, Directions.VERTICAL, false);
            } else {
                return getMoves(2, (a, b) -> a > b, Directions.VERTICAL, false);
            }
        } else {
            if (!hasMoved()) {
                return getMoves(1, (a, b) -> a < b, Directions.VERTICAL, false);
            } else {
                return getMoves(2, (a, b) -> a < b, Directions.VERTICAL, false);
            }
        }
    }

    /**
     * @param maxAvailableRange
     * @param operator first parameter is candidate position's row index, second parameter is pawn row index
     *                 should return true if candidate position is to be included in result coordinates
     * @return
     */
    private List<Coordinates> getMoves(int maxAvailableRange, BiPredicate<Integer,Integer> operator, Directions directions, boolean isCapture) {
        return getBoard().getSquaresInRange(this, maxAvailableRange, EnumSet.of(directions), isCapture)
                .stream().filter(c ->  operator.test(c.row() , getCoordinates().get().row())
                ).toList();
    }

    @Override
    public Collection<Coordinates> getAllValidCaptures() {
        if (this.getOwner().getColor() == Player.COLOR.WHITE) {
            return getMoves(1, (a,b) -> a > b, Directions.DIAGONAL, true);
        } else {
            return getMoves(1, (a,b) -> a < b, Directions.DIAGONAL, true);
        }
    }
}
