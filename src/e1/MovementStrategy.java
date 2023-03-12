package e1;

@FunctionalInterface
public interface MovementStrategy {

    boolean canMove(Pair<Integer, Integer> startingPosition, Pair<Integer, Integer> newPosition);
}
