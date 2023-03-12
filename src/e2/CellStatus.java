package e2;

public enum CellStatus {
    EXPLORED(false),
    UNEXPLORED(true);

    private final boolean canBeExplored;

    CellStatus(boolean canBeExplored) {
        this.canBeExplored = canBeExplored;
    }

    public boolean canBeExplored() {
        return this.canBeExplored;
    }
}
