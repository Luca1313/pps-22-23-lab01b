package e2.cell;

public class CellImpl implements Cell{

    private CellStatus status;
    private boolean isFlagged;
    private boolean isBomb;

    public CellImpl() {
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
}
