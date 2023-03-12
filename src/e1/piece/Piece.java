package e1.piece;

import e1.utils.Pair;

public interface Piece {

    boolean update(Pair<Integer, Integer> position);

    Pair<Integer, Integer> getPosition();

    PieceType getType();
}
