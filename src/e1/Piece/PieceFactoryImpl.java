package e1.Piece;

import e1.Utils.Pair;

public class PieceFactoryImpl implements PieceFactory {

    @Override
    public Piece buildPawn(Pair<Integer, Integer> position) {
        return new PieceImpl(PieceType.PAWN, position);
    }

    @Override
    public Piece buildKnight(Pair<Integer, Integer> position) {
        return new PieceImpl(PieceType.KNIGHT, position);
    }
}
