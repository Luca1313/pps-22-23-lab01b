package e1.Piece;

import e1.Utils.Pair;

public interface PieceFactory {

    Piece buildPawn(Pair<Integer, Integer> position);

    Piece buildKnight(Pair<Integer, Integer> position);
}
