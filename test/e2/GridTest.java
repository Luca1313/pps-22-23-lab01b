package e2;

import e2.Grid.Grid;
import e2.Grid.GridImpl;
import e2.Utils.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class GridTest {

    private static final int SIZE = 10;
    private static final int BOMB = 10;
    private Grid grid;

    @BeforeEach
    void setUp() {
        this.grid = new GridImpl(SIZE, BOMB);
    }

    @Test
    void checkBombs() {
        assertEquals(IntStream.range(0, SIZE)
                .boxed()
                .flatMap(i -> IntStream.range(0, SIZE)
                        .mapToObj(j -> new Pair<>(i, j)))
                .filter(p -> this.grid.isBomb(p))
                .count(),
                BOMB);
    }

    private int countNearBomb(Pair<Integer, Integer> position) {
        return (int) IntStream.range(position.getX()-1, position.getX()+2)
                .boxed()
                .flatMap(i -> IntStream.range(position.getY()-1, position.getY()+2)
                        .mapToObj(j -> new Pair<>(i, j)))
                .filter(p -> p.getX() >= 0 && p.getX() < SIZE && p.getY() >= 0 && p.getY() < SIZE)
                .filter(p -> !p.equals(position))
                .filter(p -> this.grid.isBomb(p))
                .count();
    }

    @Test
    void checkNearBombCount() {
        IntStream.range(0, SIZE)
                .boxed()
                .flatMap(i -> IntStream.range(0, SIZE)
                        .mapToObj(j -> new Pair<>(i, j)))
                .forEach(p -> assertEquals(this.grid.getNearBombCount(p), this.countNearBomb(p)));
    }

    @Test
    void checkExplored() {
        IntStream.range(0, SIZE)
                .boxed()
                .flatMap(i -> IntStream.range(0, SIZE)
                        .mapToObj(j -> new Pair<>(i, j)))
                .filter(p -> !this.grid.isBomb(p) && !this.grid.isFlagged(p))
                .forEach(p -> {
                    this.grid.explore(p);
                    assertTrue(this.grid.isExplored(p));
                });
    }

    @Test
    void checkWin() {
        IntStream.range(0, SIZE)
                .boxed()
                .flatMap(i -> IntStream.range(0, SIZE)
                        .mapToObj(j -> new Pair<>(i, j)))
                .filter(p -> !this.grid.isBomb(p))
                .forEach(p -> {
                    if (this.grid.isFlagged(p)) {
                        this.grid.switchFlag(p);
                    }
                    this.grid.explore(p);
                });
        assertTrue(this.grid.allDisplayed());
    }

    static class noSetUp {

        @Test
        void neverGetABombOnFirstClick() {
            int size = 100;
            int bomb = size*size -1;
            Grid grid = new GridImpl(size, bomb);
            IntStream.range(0, SIZE)
                    .boxed()
                    .flatMap(i -> IntStream.range(0, SIZE)
                            .mapToObj(j -> new Pair<>(i, j)))
                    .findAny()
                    .ifPresent(p -> {
                        grid.explore(p);
                        assertFalse(grid.isBomb(p));
                    });
        }
    }

}