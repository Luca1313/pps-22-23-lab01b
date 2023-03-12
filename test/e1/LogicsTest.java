package e1;

import e1.Utils.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class LogicsTest {

    public static final int TEST_TIMES = 100;
    private static final int SIZE = 5;
    private Logics logics;

    static class noSetUpNeeded {

        @Test
        void knightAndPawnInSameStartingPositionThrowsException() {
            assertThrows(IllegalArgumentException.class, () -> new LogicsImpl(SIZE, new Pair<>(0, 0), new Pair<>(0, 0)));
        }

        @Test
        void knightAndPawnInDifferentStartingPositionThrowsException() {
            assertDoesNotThrow(() -> new LogicsImpl(SIZE, new Pair<>(0, 0), new Pair<>(1, 1)));
        }
    }

    @BeforeEach
    void setUp() {
        this.logics = new LogicsImpl(SIZE);
    }

    @Test
    void knightAndPawnNotInSameStartingPosition() {
        assertNotEquals(this.getKnightPosition(this.logics), (this.getPawnPosition(this.logics)));
    }

    @Test
    void knightCanMoveInRightPositions() {
        Pair<Integer, Integer> knightPosition = this.getKnightPosition(this.logics);
        Pair<Integer, Integer> newPosition = this.getNewKnightValidPosition(knightPosition);
        this.logics.hit(newPosition.getX(), newPosition.getY());
        Pair<Integer, Integer> knightPositionAfterMovement = this.getKnightPosition(this.logics);
        assertNotEquals(knightPosition, knightPositionAfterMovement);
    }

    @Test
    void knightCannotMoveInWrongPositions() {
        Pair<Integer, Integer> knightPosition = this.getKnightPosition(this.logics);
        List<Pair<Integer, Integer>> validPositions = this.getKnightValidPositions(knightPosition);
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                Pair<Integer, Integer> newPosition = new Pair<>(x, y);
                if (!validPositions.contains(newPosition)) {
                    this.logics.hit(newPosition.getX(), newPosition.getY());
                    Pair<Integer, Integer> knightPositionAfterMovement = this.getKnightPosition(this.logics);
                    assertEquals(knightPosition, knightPositionAfterMovement);
                }
            }
        }
    }

    @Test
    void knightCannotMoveInPositionsOutOfBoard() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            this.logics.hit(-1, -1);
        });
    }

    @RepeatedTest(TEST_TIMES)
    void testCorrectWin() {
        Pair<Integer, Integer> knightPosition = this.getKnightPosition(this.logics);
        Pair<Integer, Integer> newKnightPosition = this.getNewKnightValidPosition(knightPosition);
        if (newKnightPosition.equals(this.getPawnPosition(this.logics))) {
            assertTrue(this.logics.hit(newKnightPosition.getX(), newKnightPosition.getY()));
        } else {
            assertFalse(this.logics.hit(newKnightPosition.getX(), newKnightPosition.getY()));
        }
    }

    static class onlyOnePiecePerGame {

        private Logics logics;

        @BeforeEach
        void setUp() {
            this.logics = new LogicsImpl(SIZE);
        }

        @Test
        void hasOnlyOneKnight() {
            assertTrue(this.oneKnightFound(this.logics));
        }

        @Test
        void hasOnlyOnePawn() {
            assertTrue(this.onePawnFound(this.logics));
        }

        @Test
        void hasOnlyOneKnightAfterMovement() {
            Pair<Integer, Integer> newKnightPosition = this.getNewKnightValidPosition(this.getKnightPosition(this.logics));
            this.logics.hit(newKnightPosition.getX(), newKnightPosition.getY());
            this.oneKnightFound(this.logics);
        }

        private Pair<Integer, Integer> getKnightPosition(Logics logics) {
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (logics.hasKnight(i, j)) {
                        return new Pair<>(i, j);
                    }
                }
            }
            return null;
        }

        private boolean oneKnightFound(Logics logics) {
            boolean alreadyHasKnight = false;
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (logics.hasKnight(i, j)) {
                        if (!alreadyHasKnight) {
                            alreadyHasKnight = true;
                        } else {
                            return false;
                        }
                    }
                }
            }
            return true;
        }

        private boolean onePawnFound(Logics logics) {
            boolean alreadyHasPawn = false;
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (logics.hasPawn(i, j)) {
                        if (!alreadyHasPawn) {
                            alreadyHasPawn = true;
                        } else {
                            return false;
                        }
                    }
                }
            }
            return true;
        }

        private boolean isValidPosition(Pair<Integer, Integer> position) {
            return position.getX() >= 0 && position.getX() < SIZE && position.getY() >= 0 && position.getY() < SIZE;
        }

        private List<Pair<Integer, Integer>> getKnightValidPositions(Pair<Integer, Integer> startingPosition) {
            List<Pair<Integer, Integer>> validPositions = new ArrayList<>();
            for (int x = -2; x <= 2; x++) {
                for (int y = -2; y <= 2; y++) {
                    if (x!=0 && y!=0 && Math.abs(x)+Math.abs(y)==3) {
                        Pair<Integer, Integer> newPosition = new Pair<>(startingPosition.getX()+x, startingPosition.getY()+y);
                        if (isValidPosition(newPosition)) {
                            validPositions.add(newPosition);
                        }
                    }
                }
            }
            return validPositions;
        }

        private Pair<Integer, Integer> getNewKnightValidPosition(Pair<Integer, Integer> startingPosition) {
            List<Pair<Integer, Integer>> validPositions = this.getKnightValidPositions(startingPosition);
            return validPositions.get(0);
        }
    }

    private Pair<Integer, Integer> getKnightPosition(Logics logics) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (logics.hasKnight(i, j)) {
                    return new Pair<>(i, j);
                }
            }
        }
        return null;
    }

    private Pair<Integer, Integer> getPawnPosition(Logics logics) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (logics.hasPawn(i, j)) {
                    return new Pair<>(i, j);
                }
            }
        }
        return null;
    }

    private boolean isValidPosition(Pair<Integer, Integer> position) {
        return position.getX() >= 0 && position.getX() < SIZE && position.getY() >= 0 && position.getY() < SIZE;
    }

    private List<Pair<Integer, Integer>> getKnightValidPositions(Pair<Integer, Integer> startingPosition) {
        List<Pair<Integer, Integer>> validPositions = new ArrayList<>();
        for (int x = -2; x <= 2; x++) {
            for (int y = -2; y <= 2; y++) {
                if (x!=0 && y!=0 && Math.abs(x)+Math.abs(y)==3) {
                    Pair<Integer, Integer> newPosition = new Pair<>(startingPosition.getX()+x, startingPosition.getY()+y);
                    if (isValidPosition(newPosition)) {
                        validPositions.add(newPosition);
                    }
                }
            }
        }
        return validPositions;
    }

    private Pair<Integer, Integer> getNewKnightValidPosition(Pair<Integer, Integer> startingPosition) {
        List<Pair<Integer, Integer>> validPositions = this.getKnightValidPositions(startingPosition);
        return validPositions.get(0);
    }
}