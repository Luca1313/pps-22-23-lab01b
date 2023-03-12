package e1.piece;

import e1.utils.Pair;

public interface PieceFactory {

    Piece buildPawn(Pair<Integer, Integer> position);

    Piece buildKnight(Pair<Integer, Integer> position);
}
