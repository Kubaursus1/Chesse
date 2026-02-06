package logic.pieces;

import logic.Player;
import logic.moves.BaseMove;
import logic.moves.Move;
import utils.Coordinates;

import javax.swing.text.html.Option;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public abstract class Piece {
    private Optional<Coordinates> coordinates;
    private final Player owner;
    private final Map<Player.COLOR, Character> symbols;

    protected Piece(Player owner, Coordinates coordinates, char symbolBlack, char symbolWhite) {
        this.owner = owner;
        this.coordinates = Optional.of(coordinates);
        symbols = Map.of(Player.COLOR.BLACK, symbolBlack, Player.COLOR.WHITE, symbolWhite);
    }

    // returns all possible landing points for this piece
    public abstract Collection<Coordinates> getAllValidMoves();


    public abstract Collection<Coordinates> getAllValidCaptures();

    public  Optional<Coordinates>  getCoordinates() {
        return coordinates;
    }

    public void makeMove(BaseMove move) {
        if (move == null)
            throw new NullPointerException("move");
        if (move.getPieceOwner() != owner)
            throw new IllegalArgumentException("invalid owner");

        setCoordinates( Optional.of(move.getDestination()));
    }
    public void removeFromBoard()
    {
        this.coordinates = Optional.empty();
    }
    private void setCoordinates( Optional<Coordinates>  coordinates) {
        this.coordinates = coordinates;
    }

    public Player getOwner() {
        return owner;
    }

    @Override
    public String toString() {
        return String.valueOf(symbols.get(owner.getColor()));
    }
}
