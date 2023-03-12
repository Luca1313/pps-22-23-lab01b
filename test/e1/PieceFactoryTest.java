package e1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PieceFactoryTest {

    private static final int SIZE = 5;
    private PieceFactory factory;
    private ChessBoard board;

    @BeforeEach
    void setUp() {
        this.board = new ChessBoardImpl(SIZE);
        this.factory = new PieceFactoryImpl();
    }

    @Test
    void checkPawnBuilt() {
        Pair<Integer, Integer> position = this.board.getFreePosition();
        Piece pawn = this.factory.buildPawn(position);
        assertEquals(pawn.getType(), PieceType.PAWN);
        assertNotEquals(pawn.getType(), PieceType.KNIGHT);
        assertEquals(pawn.getPosition(), position);
    }

    @Test
    void checkKnightBuilt() {
        Pair<Integer, Integer> position = this.board.getFreePosition();
        Piece knight = this.factory.buildKnight(position);
        assertEquals(knight.getType(), PieceType.KNIGHT);
        assertNotEquals(knight.getType(), PieceType.PAWN);
        assertEquals(knight.getPosition(), position);
    }
}
