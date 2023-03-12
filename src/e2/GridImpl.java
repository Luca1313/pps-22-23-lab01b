package e2;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

public class GridImpl implements Grid{

    private final Map<Pair<Integer,Integer>, Cell> grid;
    private final int grid_size;
    private final int bombs;

    public GridImpl(int size, int bombs) {
        this.grid_size = size;
        this.grid = new HashMap<>();
        IntStream.range(0, size)
                .boxed()
                .flatMap(i -> IntStream.range(0, size)
                        .mapToObj(j -> new Pair<>(i, j)))
                .forEach(p -> this.grid.put(p, new CellImpl()));
        this.bombs = bombs;
    }

    private void setBombs(Pair<Integer, Integer> position) {
        Random random = new Random();
        random.ints(0, this.grid_size*this.grid_size)
                .filter(i -> {
                    int x = i / this.grid_size;
                    int y = i % this.grid_size;
                    return !new Pair<>(x, y).equals(position);
                })
                .distinct()
                .limit(this.bombs)
                .forEach(i -> {
                    int x = i / this.grid_size;
                    int y = i % this.grid_size;
                    this.grid.get(new Pair<>(x, y)).setBomb();
                });
    }

    @Override
    public int getNearBombCount(Pair<Integer, Integer> position) {
        return (int) IntStream.range(position.getX()-1, position.getX()+2)
                .boxed()
                .flatMap(i -> IntStream.range(position.getY()-1, position.getY()+2)
                        .mapToObj(j -> new Pair<>(i, j)))
                .filter(p -> p.getX() >= 0 && p.getX() < this.grid_size && p.getY() >= 0 && p.getY() < this.grid_size)
                .filter(p -> !p.equals(position))
                .filter(this::isBomb)
                .count();
    }

    @Override
    public void explore(Pair<Integer, Integer> position) {
        if (this.grid.values().stream().noneMatch(Cell::isExplored)) {
            this.setBombs(position);
        }
        this.grid.get(position).explore();
        if (!this.isBomb(position) && !this.isFlagged(position) &&  this.getNearBombCount(position) == 0) {
            IntStream.range(position.getX() - 1, position.getX() + 2)
                    .boxed()
                    .flatMap(i -> IntStream.range(position.getY() - 1, position.getY() + 2)
                            .mapToObj(j -> new Pair<>(i, j)))
                    .filter(p -> p.getX() >= 0 && p.getX() < this.grid_size && p.getY() >= 0 && p.getY() < this.grid_size)
                    .filter(p -> !p.equals(position))
                    .filter(p -> !this.isExplored(p))
                    .forEach(this::explore);
        }
    }

    @Override
    public boolean isBomb(Pair<Integer, Integer> position) {
        return this.grid.get(position).isBomb();
    }

    @Override
    public boolean isExplored(Pair<Integer, Integer> position) {
        return this.grid.get(position).isExplored();
    }

    @Override
    public void switchFlag(Pair<Integer, Integer> position) {
        this.grid.get(position).switchFlag();
    }

    @Override
    public boolean isFlagged(Pair<Integer, Integer> position) {
        return this.grid.get(position).isFlagged();
    }

    @Override
    public boolean allDisplayed() {
        return this.grid.values().stream()
            .filter(p -> !p.isBomb()).allMatch(Cell::isExplored);
    }
}
