package e2;

public interface Cell {

    void setBomb();

    void switchFlag();

    boolean isBomb();

    void explore();

    boolean isExplored();

    boolean isFlagged();
}
