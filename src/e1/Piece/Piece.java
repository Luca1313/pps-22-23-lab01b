package e1.Piece;

import e1.Utils.Pair;

public interface Piece {

    boolean update(Pair<Integer, Integer> position);

    Pair<Integer, Integer> getPosition();

    PieceType getType();
}
