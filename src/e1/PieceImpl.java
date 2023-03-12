package e1;

public class PieceImpl implements Piece{

    private Pair<Integer, Integer> position;
    private final PieceType type;

    public PieceImpl(PieceType type, Pair<Integer, Integer> position) {
        this.type = type;
        this.position = position;
    }

    @Override
    public final boolean update(Pair<Integer, Integer> newPosition) {
        if (this.type.getStrategy().canMove(this.position, newPosition)) {
            this.position = newPosition;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Pair<Integer, Integer> getPosition() {
        return this.position;
    }

    @Override
    public PieceType getType() {
        return this.type;
    }
}
