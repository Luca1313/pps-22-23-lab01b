package e2;

import e2.Grid.Grid;
import e2.Grid.GridImpl;
import e2.Utils.Pair;

public class LogicsImpl implements Logics {

    private final Grid grid;

    public LogicsImpl(int size, int bombs) {
        this.grid = new GridImpl(size, bombs);
    }

    @Override
    public void explore(Pair<Integer, Integer> position) {
        this.grid.explore(position);
    }

    @Override
    public void switchFlag(Pair<Integer, Integer> position) {
        this.grid.switchFlag(position);
    }

    @Override
    public boolean isFlagged(Pair<Integer, Integer> position) {
        return this.grid.isFlagged(position);
    }

    @Override
    public boolean isExplored(Pair<Integer, Integer> position) {
        return this.grid.isExplored(position);
    }

    @Override
    public boolean isBomb(Pair<Integer, Integer> position) {
        return this.grid.isBomb(position);
    }

    @Override
    public int getNearBombCount(Pair<Integer, Integer> position) {
        return this.grid.getNearBombCount(position);
    }

    @Override
    public boolean allDisplayed() {
        return this.grid.allDisplayed();
    }
}
