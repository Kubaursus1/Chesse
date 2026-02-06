package logic;

import logic.pieces.Piece;
import utils.Coordinates;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
        return squares.get(piece.getCoordinates()).isPresent();
    }

    public Piece getPiece(Coordinates coordinates) {
        return this.squares.get(coordinates).get();
    }

    public void setPiece(Piece piece, Coordinates coordinates) {
        this.squares.put(coordinates, Optional.of(piece));
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
