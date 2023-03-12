package e1;

public class LogicsImpl implements Logics {

	private final Piece pawn;
	private final Piece knight;
	private final ChessBoard board;
	private final PieceFactory factory;

    public LogicsImpl(int size){
		this.board = new ChessBoardImpl(size);
		this.factory  = new PieceFactoryImpl();
        this.pawn = this.factory.buildPawn(this.board.getFreePosition());
        this.board.addPiece(this.pawn);
        this.knight = this.factory.buildKnight(this.board.getFreePosition());
        this.board.addPiece(this.knight);
    }

	public LogicsImpl(int size, Pair<Integer, Integer> pawnPosition, Pair<Integer, Integer> knightPosition){
		this.board = new ChessBoardImpl(size);
		this.factory  = new PieceFactoryImpl();
		this.pawn = this.factory.buildPawn(pawnPosition);
		this.knight = this.factory.buildKnight(knightPosition);
		if (!this.board.addPiece(this.pawn) || !this.board.addPiece(this.knight)) {
			throw new IllegalArgumentException("Invalid position");
		}
	}
    
	@Override
	public boolean hit(int row, int col) {
    	Pair<Integer, Integer> newPosition = new Pair<>(row, col);
    	this.board.updatePiece(this.knight, newPosition);
    	return this.board.allPawnsEaten();
	}

	@Override
	public boolean hasKnight(int row, int col) {
		return this.board.hasKnight(new Pair<>(row,col));
	}

	@Override
	public boolean hasPawn(int row, int col) {
		return this.board.hasPawn(new Pair<>(row,col));
	}
}
