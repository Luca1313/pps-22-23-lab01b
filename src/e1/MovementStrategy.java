package e1;

import e1.utils.Pair;

@FunctionalInterface
public interface MovementStrategy {

    boolean canMove(Pair<Integer, Integer> startingPosition, Pair<Integer, Integer> newPosition);
}
