package logic.moves;

import logic.MoveVisitor;
import logic.pieces.Piece;
import utils.Coordinates;

public class Move extends BaseMove{
    public Move(Coordinates destination, Piece piece) {
        super(destination, piece);
    }

    @Override
    public void accept(MoveVisitor moveVisitor) {
        moveVisitor.visit(this);
    }
}
