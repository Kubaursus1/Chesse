package logic;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Game {
    private final Player[] players = {new Player(Player.COLOR.WHITE), new Player(Player.COLOR.BLACK)};
    private Player currentPlayer;
    private Board board;

    public Game() {
        board = new Board(Arrays.stream(players).flatMap(p -> p.getPieces().stream()).toList());
        // stworz graczy
            // gracze tworza bierki
        // stworz planszed na podstawie bierek graczy
    }

//    public Collection<Move> getPosssibleMoves(Piece piece);
//    {
//
//    }
//    public void makeMove(Move move)
//    {
//
//    }


    public Player[] getPlayers() {
        return players;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Board getBoard() {
        return board;
    }
}
