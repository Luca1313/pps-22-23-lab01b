package e1;

public enum PieceType {
    PAWN((startingPosition, newPosition) -> {return false;}),
    KNIGHT((startingPosition, newPosition) -> {
        int row = startingPosition.getX();
        int col = startingPosition.getY();
        int x = row-newPosition.getX();
        int y = col-newPosition.getY();
        return x!=0 && y!=0 && Math.abs(x)+Math.abs(y)==3;
    });

    private final MovementStrategy strategy;

    PieceType(MovementStrategy strategy) {
        this.strategy = strategy;
    }

    public MovementStrategy getStrategy() {
        return this.strategy;
    }
}
