public class WumpusPlayer {
    public static final int NORTH = 0;
    public static final int EAST = 1;
    public static final int SOUTH = 2;
    public static final int WEST = 3;
    private int direction, colPosition, rowPosition;
    private boolean arrow, gold;

    public WumpusPlayer() {
        direction = NORTH;
        gold = false;
        arrow = true;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getColPosition() {
        return colPosition;
    }

    public void setColPosition(int colPosition) {
        this.colPosition = colPosition;
    }

    public int getRowPosition() {
        return rowPosition;
    }

    public void setRowPosition(int rowPosition) {
        this.rowPosition = rowPosition;
    }

    public boolean getArrow() {
        return arrow;
    }

    public void setArrow(boolean arrow) {
        this.arrow = arrow;
    }

    public boolean getGold() {
        return gold;
    }

    public void setGold(boolean gold) {
        this.gold = gold;
    }
}
