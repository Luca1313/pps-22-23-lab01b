package e2;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class LogicsTest {

    private static final int SIZE = 10;
    private static final int BOMB = 10;
    private Logics logics;

    @BeforeEach
    void setUp() {
        this.logics = new LogicsImpl(SIZE, BOMB);
    }
}