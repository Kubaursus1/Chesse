package logic;

import logic.pieces.*;
import utils.Coordinates;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Player {
    private Collection<Piece> pieces;
    private COLOR color;

    public Collection<Piece> getPieces() {
        return pieces;
    }

    public COLOR getColor() {
        return color;
    }

    public enum COLOR {
        WHITE,
        BLACK
    }

    public Player(COLOR color) {
        this.color = color;
        pieces = new ArrayList<>();

        int[] rows = color == COLOR.BLACK ? new int[] {6,7} : new int[] {1,0};
        createPawns(rows[0]);
        createOtherPieces(rows[1]);

    }
    public void remove(Piece piece) {
        this.pieces.remove(piece);
    }
    private void createPawns(int row) {
        for (int i = 0; i< Board.BOARD_SIZE; i++) {
            pieces.add(new Pawn(this, new Coordinates(i, row)));
        }
    }
    private void createOtherPieces(int row) {
        pieces.add(new Bishop(this, new Coordinates(2, row)));
        pieces.add(new Bishop(this, new Coordinates(5, row)));
        pieces.add(new Knight(this, new Coordinates(1, row)));
        pieces.add(new Knight(this, new Coordinates(6, row)));
        pieces.add(new Rook(this, new Coordinates(0, row)));
        pieces.add(new Rook(this, new Coordinates(7, row)));
        pieces.add(new Queen(this, new Coordinates(3, row)));
        pieces.add(new King(this, new Coordinates(4, row)));
    }
}
