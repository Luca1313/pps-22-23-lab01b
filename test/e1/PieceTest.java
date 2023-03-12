package e1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

public class PieceTest {

    private static final int SIZE = 5;
    private PieceFactory factory;
    private Piece pawn;
    private Piece knight;
    private ChessBoard board;
    private Pair<Integer, Integer> startingPawn;
    private Pair<Integer, Integer> startingKnight;

    @BeforeEach
    void setUp() {
        this.board = new ChessBoardImpl(SIZE);
        this.factory = new PieceFactoryImpl();
        this.startingPawn = this.board.getFreePosition();
        this.pawn = this.factory.buildPawn(this.startingPawn);
        this.board.addPiece(this.pawn);
        this.startingKnight = this.board.getFreePosition();
        this.knight = this.factory.buildKnight(this.startingKnight);
        this.board.addPiece(this.knight);
    }

    @Test
    void checkPosition() {
        assertEquals(this.pawn.getPosition(), this.startingPawn);
        assertEquals(this.knight.getPosition(), this.startingKnight);
    }

    @Test
    void checkType() {
        assertEquals(this.pawn.getType(), PieceType.PAWN);
        assertEquals(this.knight.getType(), PieceType.KNIGHT);
    }

    @Test
    void checkPawnUpdates() {
        assertTrue(IntStream.range(0, SIZE)
                .boxed()
                .flatMap(i -> IntStream.range(0, SIZE).mapToObj(j -> new Pair<Integer, Integer>(i, j)))
                .noneMatch(p -> this.board.updatePiece(this.pawn, p)));
        assertTrue(IntStream.range(0, SIZE)
                .boxed()
                .flatMap(i -> IntStream.range(0, SIZE).mapToObj(j -> new Pair<Integer, Integer>(i, j)))
                .filter(pos -> this.knight.getType().getStrategy().canMove(this.knight.getPosition(), pos))
                .allMatch(pos -> {
                    int row = this.knight.getPosition().getX();
                    int col = this.knight.getPosition().getY();
                    int x = row-pos.getX();
                    int y = col-pos.getY();
                    return x!=0 && y!=0 && Math.abs(x)+Math.abs(y)==3;
                }));
    }
}
