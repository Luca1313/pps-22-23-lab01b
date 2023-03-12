package e1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChessBoardTest {

    private ChessBoard chessBoard;
    private static final int SIZE = 5;
    private PieceFactory factory;
    private Piece pawn;
    private Piece knight;
    private Pair<Integer, Integer> startingPawnPosition;
    private Pair<Integer, Integer> startingKnightPosition;

    @BeforeEach
    void setUp() {
        this.chessBoard = new ChessBoardImpl(SIZE);
        this.factory = new PieceFactoryImpl();
        this.startingPawnPosition = new Pair<>(0, 0);
        this.startingKnightPosition = new Pair<>(1, 0);
        this.pawn = this.factory.buildPawn(this.startingPawnPosition);
        this.knight = this.factory.buildKnight(this.startingKnightPosition);
    }

    @Test
    void addPieces() {
        assertTrue(this.chessBoard.addPiece(this.pawn));
        assertTrue(this.chessBoard.addPiece(this.knight));
        assertFalse(this.chessBoard.addPiece(this.factory.buildPawn(this.startingPawnPosition)));
        assertFalse(this.chessBoard.addPiece(this.factory.buildKnight(this.startingKnightPosition)));
        assertFalse(this.chessBoard.addPiece(this.factory.buildPawn(new Pair<>(SIZE, SIZE))));
    }

    @Test
    void arePiecesAdded() {
        this.chessBoard.addPiece(this.pawn);
        this.chessBoard.addPiece(this.knight);
        assertTrue(this.chessBoard.hasPawn(this.startingPawnPosition));
        assertTrue(this.chessBoard.hasKnight(this.startingKnightPosition));
    }

    @Test
    void isFreePosition() {
        this.chessBoard.addPiece(this.pawn);
        this.chessBoard.addPiece(this.knight);
        Pair<Integer, Integer> freePosition = this.chessBoard.getFreePosition();
        assertNotNull(freePosition);
        assertFalse(this.chessBoard.hasPawn(freePosition));
        assertFalse(this.chessBoard.hasKnight(freePosition));
    }

    @Test
    void isPositionValid() {
        assertTrue(this.chessBoard.validPosition(new Pair<>(0, 0)));
        assertTrue(this.chessBoard.validPosition(new Pair<>(SIZE - 1, SIZE - 1)));
        assertThrows(IndexOutOfBoundsException.class, () -> this.chessBoard.validPosition(new Pair<>(SIZE, SIZE)));
        assertThrows(IndexOutOfBoundsException.class, () -> this.chessBoard.validPosition(new Pair<>(-1, -1)));
    }

    @Test
    void updatePiece() {
        this.chessBoard.addPiece(this.pawn);
        this.chessBoard.addPiece(this.knight);
        Pair<Integer, Integer> newPosition = new Pair<>(this.startingKnightPosition.getX()+1, this.startingKnightPosition.getY()+2);
        assertFalse(this.chessBoard.updatePiece(this.pawn, newPosition));
        assertTrue(this.chessBoard.updatePiece(this.knight, newPosition));
        assertFalse(this.chessBoard.hasPawn(newPosition));
        assertTrue(this.chessBoard.hasKnight(newPosition));
        assertTrue(this.chessBoard.hasPawn(this.startingPawnPosition));
        assertFalse(this.chessBoard.hasKnight(this.startingKnightPosition));
    }

    @Test
    void allPawnsEaten() {
        this.chessBoard.addPiece(this.pawn);
        Piece knightEater = this.factory.buildKnight(new Pair<>(this.startingPawnPosition.getX()+1, this.startingPawnPosition.getY()+2));
        this.chessBoard.addPiece(knightEater);
        assertFalse(this.chessBoard.allPawnsEaten());
        this.chessBoard.updatePiece(knightEater, this.startingPawnPosition);
        assertTrue(this.chessBoard.allPawnsEaten());
        this.chessBoard.addPiece(this.factory.buildPawn(this.chessBoard.getFreePosition()));
        assertFalse(this.chessBoard.allPawnsEaten());
    }
}