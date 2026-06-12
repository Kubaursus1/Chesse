package logic.pieces;

import logic.Board;
import logic.Player;
import logic.moves.BaseMove;
import logic.moves.Move;
import utils.Coordinates;

import javax.swing.text.html.Option;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public abstract class Piece {
    /**
     * empty if was removed from board
     */
    private Optional<Coordinates> coordinates;
    private final Player owner;
    private final Map<Player.COLOR, Character> symbols;
    private Board board;
    private boolean hasMoved = false;

    public Board getBoard() {
        return board;
    }

    protected Piece(Player owner, Coordinates coordinates, char symbolBlack, char symbolWhite) {
        this.owner = owner;
        this.coordinates = Optional.of(coordinates);
        symbols = Map.of(Player.COLOR.BLACK, symbolBlack, Player.COLOR.WHITE, symbolWhite);
    }

    // returns all possible landing points for this piece
    public abstract Collection<Coordinates> getAllValidMoves();

    public boolean hasMoved() {
        return hasMoved;
    }

    public abstract Collection<Coordinates> getAllValidCaptures();

    public  Optional<Coordinates>  getCoordinates() {
        return coordinates;
    }

    public void setBoard(Board board) {
        if (board == null)
            throw new NullPointerException("board");
        this.board = board;
    }

    public void makeMove(BaseMove move) {
        if (move == null)
            throw new NullPointerException("move");
        if (move.getPieceOwner() != owner)
            throw new IllegalArgumentException("invalid owner");

        setCoordinates( Optional.of(move.getDestination()));
        if(!hasMoved)
            hasMoved = true;
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
