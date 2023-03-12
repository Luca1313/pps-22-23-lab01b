package e1;

public interface Piece {

    boolean update(Pair<Integer, Integer> position);

    Pair<Integer, Integer> getPosition();

    PieceType getType();
}
