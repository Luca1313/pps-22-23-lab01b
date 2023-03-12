package e2;

import e2.cell.Cell;
import e2.cell.CellStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {

    private static class CellForTest implements Cell {

        private CellStatus status;
        private boolean isFlagged;
        private boolean isBomb;

        public CellForTest() {
            this.status = CellStatus.UNEXPLORED;
            this.isFlagged = false;
            this.isBomb = false;
        }

        @Override
        public void setBomb() {
            if (this.status == CellStatus.UNEXPLORED) {
                this.isBomb = true;
            }
        }

        @Override
        public void switchFlag() {
            if (this.status != CellStatus.EXPLORED) {
                this.isFlagged = !this.isFlagged;
            }
        }

        @Override
        public boolean isBomb() {
            return this.isBomb;
        }

        @Override
        public void explore() {
            if (this.status.canBeExplored() && !this.isFlagged) {
                this.status = CellStatus.EXPLORED;
            }
        }

        @Override
        public boolean isExplored() {
            return this.status == CellStatus.EXPLORED;
        }

        @Override
        public boolean isFlagged() {
            return this.isFlagged;
        }

        public CellStatus getStatus() {
            return this.status;
        }

    }

    private CellForTest cell;

    @BeforeEach
    void setUp() {
        this.cell = new CellForTest();
    }

    @Test
    void statusCheck() {
        assertEquals(CellStatus.UNEXPLORED, this.cell.getStatus());
        assertNotEquals(CellStatus.EXPLORED, this.cell.getStatus());
        assertFalse(this.cell.isBomb());
    }

    @Test
    void checkExplore() {
        this.cell.explore();
        assertEquals(CellStatus.EXPLORED, this.cell.getStatus());
        assertTrue(this.cell.isExplored());
    }

    @Test
    void checkBomb() {
        this.cell.setBomb();
        assertTrue(this.cell.isBomb());
    }

    @Test
    void checkFlag() {
        assertFalse(this.cell.isFlagged());
        this.cell.switchFlag();
        assertTrue(this.cell.isFlagged());
        this.cell.explore();
        assertEquals(CellStatus.UNEXPLORED, this.cell.getStatus());
        assertNotEquals(CellStatus.EXPLORED, this.cell.getStatus());
        this.cell.switchFlag();
        assertFalse(this.cell.isFlagged());
        this.cell.explore();
        assertEquals(CellStatus.EXPLORED, this.cell.getStatus());
    }

}