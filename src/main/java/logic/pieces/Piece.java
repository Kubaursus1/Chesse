package logic.pieces;

import logic.Player;
import utils.Coordinates;

import java.util.Collection;
import java.util.Map;

public abstract class Piece {
    private Coordinates coordinates;
    private final Player owner;
//    private final char symbol;
    private final Map<Player.COLOR, Character> symbols;

    protected Piece(Player owner, Coordinates coordinates, char symbolBlack,char symbolWhite) {
        this.owner = owner;
        this.coordinates = coordinates;
//        this.symbol = symbol;
        symbols = Map.of(Player.COLOR.BLACK, symbolBlack, Player.COLOR.WHITE, symbolWhite);
    }

    // returns all possible landing points for this piece
    public abstract Collection<Coordinates> getAllValidMoves();


    public abstract Collection<Coordinates> getAllValidCaptures();

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Player getOwner() {
        return owner;
    }

    @Override
    public String toString() {
        return String.valueOf( symbols.get(owner.getColor()));
    }
}
