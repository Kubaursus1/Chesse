package logic.pieces;

import logic.Player;
import utils.Coordinates;

import java.util.Collection;
import java.util.List;

public class King extends Piece{
    public King(Player owner, Coordinates coordinates) {
        super(owner, coordinates, '♚', '♔');
    }

    @Override
    public Collection<Coordinates> getAllValidMoves() {
        return List.of();
    }

    @Override
    public Collection<Coordinates> getAllValidCaptures() {
        return List.of();
    }
}
