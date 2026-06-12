package logic.pieces;

import logic.Player;
import org.javatuples.Pair;
import utils.Coordinates;
import utils.Directions;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Knight extends Piece{
    public Knight(Player owner, Coordinates coordinates) {
        super(owner, coordinates, '♞', '♘');

        //♕	♖	♗	♘	♙ ♔
        //♚	♛	♜	♝	♞	♟
    }

    @Override
    public Collection<Coordinates> getAllValidMoves() {
        if(getCoordinates().isEmpty())
            throw new IllegalArgumentException("piece is not on board");
        var curCoordinates = getCoordinates().get();

        List<Pair<Directions, Directions>> directions = List.of(Pair.with(Directions.VERTICAL,Directions.HORIZONTAL),
                Pair.with(Directions.HORIZONTAL,Directions.VERTICAL));

        Stream<Coordinates> vectors = directions.stream().flatMap( pair ->
            pair.getValue0().getVectors().stream().flatMap( vLong ->
                    pair.getValue1().getVectors().stream().map(vShort ->
                        Coordinates.add(Coordinates.mul(vLong,2),vShort))));

        Stream<Coordinates> destinations =
                vectors.map(v -> Coordinates.tryAdd(curCoordinates,v))
                        .filter(Optional::isPresent)
                        .map(Optional::get);

        return destinations.filter(destination -> !getBoard().isOccupied(destination))
                .toList();

    }

    @Override
    public Collection<Coordinates> getAllValidCaptures() {
        return List.of();
    }
}
