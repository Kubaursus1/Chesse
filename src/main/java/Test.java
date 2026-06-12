import logic.*;
import logic.moves.BaseMove;
import logic.pieces.Piece;

import java.util.Collection;

public class Test {

    static class TestInputOutput implements InputOutput{

        @Override
        public void printBoard(Board board) {
            System.out.println(board);
        }

        @Override
        public Piece choosePiece(Player player) {
           return player.getPieces().stream().findFirst().get();
        }

        @Override
        public BaseMove chooseMove(Collection<BaseMove> moves) {
            return moves.stream().findFirst().get();
        }
    }


    public static void main(String[] args) {
//        System.out.println(new Coordinates(0, 5));
        var game = new Game();
        game.start();
        var gameLoop = new GameLoop(new TestInputOutput());

        gameLoop.execute(game);

    }
}


