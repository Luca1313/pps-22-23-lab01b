package e1;

public interface PieceFactory {

    Piece buildPawn(Pair<Integer, Integer> position);

    Piece buildKnight(Pair<Integer, Integer> position);
}
