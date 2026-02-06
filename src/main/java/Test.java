import logic.Board;
import logic.Game;
import utils.Coordinates;

public class Test {
    public static void main(String[] args) {
//        System.out.println(new Coordinates(0, 5));
        var game = new Game();
        game.start();
        Board board = game.getBoard();

        System.out.println(board);
    }
}


