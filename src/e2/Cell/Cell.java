package e2.Cell;

public interface Cell {

    void setBomb();

    void switchFlag();

    boolean isBomb();

    void explore();

    boolean isExplored();

    boolean isFlagged();
}
