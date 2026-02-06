package logic;

import logic.moves.BaseMove;
import logic.pieces.Piece;

public class GameLoop {
    private final InputOutput inputOutput;

    public GameLoop(InputOutput inputOutput) {
        this.inputOutput = inputOutput;
    }

    public void execute(Game game) {
        while (game.getState() == Game.STATE.STARTED) {
            inputOutput.printBoard(game.getBoard());
            Piece piece = inputOutput.choosePiece(game.getActivePlayer());
            var moves = game.getPossibleMoves(piece);
            BaseMove move = inputOutput.chooseMove(moves);
            game.makeMove(move);
        }
    }
}
