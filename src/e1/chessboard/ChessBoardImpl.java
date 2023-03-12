package e1.chessboard;

import e1.utils.Pair;
import e1.piece.Piece;
import e1.piece.PieceType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ChessBoardImpl implements ChessBoard {

    private final List<Piece> board;
    private final int size;

    public ChessBoardImpl(int size) {
        this.size = size;
        this.board = new ArrayList<>();
    }

    private boolean isAValidPosition(Pair<Integer, Integer> position) {
        return position.getX() >= 0 && position.getX() < this.size && position.getY() >= 0 && position.getY() < this.size;
    }

    @Override
    public boolean addPiece(Piece piece) {
        if (!this.isAValidPosition(piece.getPosition()) ||
                this.board.stream().anyMatch(p -> p.getPosition().equals(piece.getPosition()))) {
            return false;
        }
        this.board.add(piece);
        return true;
    }

    @Override
    public Pair<Integer, Integer> getFreePosition() {
        return IntStream.range(0, size)
                .boxed()
                .flatMap(i -> IntStream.range(0, size)
                    .mapToObj(j -> new Pair<Integer, Integer>(i, j)))
                .filter(p -> this.board.stream().noneMatch(p2 -> p2.getPosition().equals(p)))
                .findAny()
                .orElseThrow(NullPointerException::new);
    }

    @Override
    public boolean hasPawn(Pair<Integer, Integer> position) {
        return this.board.stream().filter(p -> p.getType() == PieceType.PAWN).anyMatch(p -> p.getPosition().equals(position));
    }

    @Override
    public boolean hasKnight(Pair<Integer, Integer> position) {
        return this.board.stream().filter(p -> p.getType() == PieceType.KNIGHT).anyMatch(p -> p.getPosition().equals(position));
    }

    @Override
    public boolean validPosition(Pair<Integer, Integer> position) {
        if (this.isAValidPosition(position)) {
            return true;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public boolean updatePiece(Piece piece, Pair<Integer, Integer> newPosition) {
    	if (this.validPosition(newPosition)) {
    	    Piece p = this.board.stream().filter(piece1 -> piece1.equals(piece)).findAny().orElseThrow(IllegalArgumentException::new);
    	    if (p.update(newPosition)) {
                this.board.removeIf(p2 -> !p2.equals(p) && p2.getPosition().equals(newPosition));
                return true;
            }
        }
    	return false;
    }

    @Override
    public boolean allPawnsEaten() {
        return this.board.stream().noneMatch(p -> p.getType() == PieceType.PAWN);
    }
}
