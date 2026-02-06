package logic;

import logic.moves.Capture;
import logic.moves.Move;

public interface MoveVisitor {
    void visit(Capture capture);
    void visit(Move move);
}
