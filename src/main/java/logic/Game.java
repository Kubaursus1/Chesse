package logic;

import logic.moves.BaseMove;
import logic.moves.Capture;
import logic.moves.Move;
import logic.pieces.Piece;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class Game {
    private final Player[] players = {new Player(Player.COLOR.WHITE), new Player(Player.COLOR.BLACK)};
    private Player activePlayer = players[0];
    private Board board;
    private STATE state = STATE.NOT_STARTED;
    private final MoveVisitor moveVisitor;
    public enum STATE {
        NOT_STARTED,
        STARTED,
    }

    private boolean shouldGameFinish()
    {
        return false;
    }

    public Game() {
        moveVisitor = new MoveHandler();
    }

    public void start() {
        board = new Board(Arrays.stream(players).flatMap(p -> p.getPieces().stream()).toList());
        List<Piece> pieces = board.getAllPieces();
        for ( var piece : pieces) {
            piece.setBoard(board);
        }
        state = STATE.STARTED;
    }

    public Player getNonActivePlayer() {
        return activePlayer == players[0] ? players[1] : players[0];
    }

    public Collection<BaseMove> getPossibleMoves(Piece piece) {
        if (piece == null) {
            throw new NullPointerException("piece");
        }
        if (state == STATE.NOT_STARTED) {
            throw new IllegalStateException("Game must be started!");
        }
        if (!board.existsPiece(piece)) {
            throw new IllegalArgumentException("Piece doesn't exist on board");
        }

        return List.of(
                        piece.getAllValidMoves().stream().map(c -> (BaseMove) (new Move(c, piece))).toList(),
                        piece.getAllValidCaptures().stream().map(c -> (BaseMove) (new Capture(c, piece))).toList())
                .stream().flatMap(x -> x.stream()).collect(Collectors.toList());
    }

    public void makeMove(BaseMove move) {
        if (move == null) {
            throw new NullPointerException("move");
        }
        if (state == STATE.NOT_STARTED) {
            throw new IllegalStateException("Game must be started!");
        }
        if(activePlayer != move.getPieceOwner()) {
            throw new IllegalArgumentException("It is not active player's piece");
        }
        if (!board.existsPiece(move.getPiece())) {
            throw new IllegalArgumentException("Piece doesn't exist on board");
        }

        if (shouldGameFinish())
        {
            state = STATE.NOT_STARTED;
            //TODO: rest of logic
        }

        move.accept(moveVisitor);
    }

    public Player[] getPlayers() {
        return players;
    }

    public STATE getState() {
        return state;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public Board getBoard() {
        return board;
    }

    private class MoveHandler implements MoveVisitor {
        private void movePiece(BaseMove baseMove) {
            board.movePiece(baseMove.getPiece(), baseMove.getDestination());
            baseMove.getPiece().makeMove(baseMove);
        }
        @Override
        public void visit(Capture capture) {
            Player nonActivePlayer = getNonActivePlayer();

            Piece pieceToBeRemoved = board.getPiece(capture.getDestination());
            nonActivePlayer.remove(pieceToBeRemoved);
            pieceToBeRemoved.removeFromBoard();

            movePiece(capture);
        }

        @Override
        public void visit(Move move) {
            movePiece(move);
        }
    }
}
