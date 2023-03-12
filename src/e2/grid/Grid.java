package e2.grid;

import e2.utils.Pair;

public interface Grid {

    int getNearBombCount(Pair<Integer, Integer> position);

    void explore(Pair<Integer, Integer> position);

    boolean isBomb(Pair<Integer, Integer> position);

    boolean isExplored(Pair<Integer, Integer> position);

    void switchFlag(Pair<Integer, Integer> position);

    boolean isFlagged(Pair<Integer, Integer> position);

    boolean allDisplayed();
}
