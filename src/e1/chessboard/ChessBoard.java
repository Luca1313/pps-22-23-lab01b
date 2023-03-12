package e1.chessboard;

import e1.utils.Pair;
import e1.piece.Piece;

public interface ChessBoard {

    boolean addPiece(Piece piece);

    Pair<Integer, Integer> getFreePosition();

    boolean hasPawn(Pair<Integer, Integer> position);

    boolean hasKnight(Pair<Integer, Integer> position);

    boolean validPosition(Pair<Integer, Integer> position);

    boolean updatePiece(Piece piece, Pair<Integer, Integer> newPosition);

    boolean allPawnsEaten();
}
