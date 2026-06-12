package logic;

import logic.pieces.Piece;
import org.javatuples.Pair;
import utils.Coordinates;
import utils.Directions;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Board {
    public static final int BOARD_SIZE = 8;
    private final Map<Coordinates, Optional<Piece>> squares = new HashMap<>();

    public Board(Collection<Piece> pieces) {
        for (int i = 0; i < Board.BOARD_SIZE; i++) {
            for (int j = 0; j < Board.BOARD_SIZE; j++) {
                squares.put(new Coordinates(i, j), Optional.empty());
            }
        }
        pieces.forEach(piece -> squares.put(piece.getCoordinates().get(), Optional.of(piece)));
    }

    public boolean existsPiece(Piece piece) {
        if (piece == null)
            throw new NullPointerException("piece");
        return squares.get(piece.getCoordinates().get()).isPresent();
    }

    public Piece getPiece(Coordinates coordinates) {
        return this.squares.get(coordinates).get();
    }
    public boolean isOccupied(Coordinates coordinates)
    {
        return squares.get(coordinates).isPresent();
    }

    public void movePiece(Piece piece, Coordinates coordinates) {
        if (piece == null)
            throw new NullPointerException("piece");

        Coordinates oldCoordinates = piece.getCoordinates().orElseThrow(() -> new IllegalArgumentException("piece doesn't exist on board"));
        this.squares.put(oldCoordinates, Optional.empty());
        this.squares.put(coordinates, Optional.of(piece));
    }

    private Coordinates getCurCoordinates(Coordinates center, Coordinates dirV, int range) {
        return Coordinates.add(center, Coordinates.mul(dirV, range));
    }

    public Set<Coordinates> getSquaresInRange(Piece piece, int maxAvailableRange,
                                              EnumSet<Directions> directions, boolean isCapture) {
        if(piece.getCoordinates().isEmpty())
            throw new IllegalArgumentException("piece is not on board");
        Coordinates center = piece.getCoordinates().get();


        Stream<Coordinates> vectors = directions.stream().flatMap(d -> d.getVectors().stream());
        Map<Coordinates, Stream<Coordinates>> coordinatesByDirection = vectors.map(dirV -> Pair.with(dirV, Stream.iterate(
                                1,
                                i -> {
                                    var curCoordinates = getCurCoordinates(center, dirV, i);
                                    return i <= maxAvailableRange
                                            && Coordinates.isValid(curCoordinates)
                                            && squares.get(curCoordinates).isEmpty();
                                }
                                , i -> i + 1)
                        .map(r -> getCurCoordinates(center, dirV, r)))
//                .filter(coord -> !isCapture  )
        ).collect(Collectors.toMap(pair -> pair.getValue0(), pair -> pair.getValue1()));
        if (!isCapture)
            return coordinatesByDirection.values().stream().flatMap(x -> x)
                    .collect(Collectors.toSet());
        Stream<Optional<Coordinates>> optionalStream = coordinatesByDirection.entrySet().stream().map(entry ->
        {
            var direction = entry.getKey();
            var coords = entry.getValue().toList();

            var last = coords.isEmpty() ? center : coords.get(coords.size() - 1);

            var next = Coordinates.add(last, direction);
            var nextDistance =  1  +  coords.size();

            if (nextDistance > maxAvailableRange)
                return Optional.empty();
            if (!Coordinates.isValid(next))
                return Optional.empty();
            if (squares.get(next).get().getOwner() == piece.getOwner())
                return Optional.empty();
            return Optional.of(next);
        });
        Stream<Coordinates> tmp = optionalStream.reduce(Stream.empty(),
                (acc,cur) -> cur.isPresent() ? Stream.concat(acc, Stream.of(cur.get())) : acc,
                // TODO: finish
                (l1,l2) -> Stream.concat(l1,l2));

        return tmp.collect(Collectors.toSet());
    }

    public List<Piece> getAllPieces() {
        return squares.values().stream().filter(piece -> piece.isPresent())
                .map(piece -> piece.get()).toList();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < BOARD_SIZE; i++) {
            sb.append("|");
            for (int j = 0; j < BOARD_SIZE; j++) {
                sb.append(squares.get(new Coordinates(j, i)).map(p -> " " + p.toString() + " ").orElse("   ␣   "));
                sb.append("|");

            }
//            for (int j = 0; j<BOARD_SIZE; j++) {
//                sb.append('_');
//            }

            sb.append("\n");
        }
        return sb.toString();
    }
}
