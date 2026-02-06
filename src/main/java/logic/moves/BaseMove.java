package logic.moves;

import logic.MoveVisitor;
import logic.Player;
import logic.pieces.Piece;
import utils.Coordinates;

public abstract class BaseMove {
    private final Coordinates destination;
    private final Piece piece;

    public BaseMove(Coordinates destination, Piece piece) {
        if (destination == null)
            throw new NullPointerException("destination");
        if (piece == null)
            throw new NullPointerException("piece");

        this.destination = destination;
        this.piece = piece;
    }

    public Coordinates getDestination() {
        return destination;
    }

    public Piece getPiece() {
        return piece;
    }

    public Player getPieceOwner()
    {
        return piece.getOwner();
    }

    public abstract void accept(MoveVisitor moveVisitor);
}
