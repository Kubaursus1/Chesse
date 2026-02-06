package logic;

import logic.moves.BaseMove;
import logic.pieces.Piece;

import java.util.Collection;

public interface InputOutput {
    void printBoard(Board board);

    Piece choosePiece(Player player);

    BaseMove chooseMove(Collection<BaseMove> moves);
}
