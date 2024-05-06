public class WumpusMap {

    public static final int NUM_ROWS = 10;
    public static final int NUM_COLUMNS = 10;
    public static final int NUM_PITS = 10;

    private WumpusSquare[][] grid;

    private int ladderCol, ladderRow;

    public WumpusMap() {
        createMap();
    }

    public void createMap() {
        grid = new WumpusSquare[10][10];

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                grid[i][j] = new WumpusSquare();
            }
        }

        for (int i = 0; i < NUM_PITS; i++) {
            while (true) {
                int r = (int) (Math.random() * 10);
                int c = (int) (Math.random() * 10);
                if (!grid[r][c].getPit() && !grid[r][c].getBreeze()) {
                    grid[r][c].setPit(true);
                    if (c - 1 >= 0 && !grid[r][c-1].getPit())
                        grid[r][c - 1].setBreeze(true);
                    if (c + 1 < 10 && !grid[r][c+1].getPit())
                        grid[r][c + 1].setBreeze(true);
                    if (r - 1 >= 0 && !grid[r-1][c].getPit())
                        grid[r - 1][c].setBreeze(true);
                    if (r + 1 < 10 && !grid[r+1][c].getPit())
                        grid[r + 1][c].setBreeze(true);
                    break;
                }
            }
        }

        while (true) {
            int r = (int) (Math.random() * 10);
            int c = (int) (Math.random() * 10);
            if (!grid[r][c].getPit() && !grid[r][c].getLadder()) {
                grid[r][c].setGold(true);
                break;
            }
        }

        while (true) {
            int r = (int) (Math.random() * 10);
            int c = (int) (Math.random() * 10);
            if (!grid[r][c].getPit() && !grid[r][c].getLadder()) {
                grid[r][c].setWumpus(true);
                if(r-1 >= 0 && !grid[r-1][c].getPit())
                    grid[r - 1][c].setStench(true);
                if(r+1 < 10 && !grid[r+1][c].getPit())
                    grid[r + 1][c].setStench(true);
                if(c-1 >= 0 && !grid[r][c-1].getPit())
                    grid[r][c - 1].setStench(true);
                if(c+1 < 10 && !grid[r][c+1].getPit())
                    grid[r][c + 1].setStench(true);
                break;
            }
        }

        while (true) {
            int r = (int) (Math.random() * 10);
            int c = (int) (Math.random() * 10);
            if (!grid[r][c].getGold() && !grid[r][c].getWumpus() && !grid[r][c].getPit()) {
                grid[r][c].setLadder(true);
                ladderCol = c;
                ladderRow = r;
                break;
            }
        }
    }

    public int getLadderCol(){ return ladderCol;}
    public int getLadderRow(){ return ladderRow;}

    public WumpusSquare getSquare(int row, int col) {
        if(col < 10 && col >= 0 && row < 10 && row >= 0)
            return grid[row][col];
        return null;
    }

    public String toString() {
        String str = "";
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                str+=grid[i][j].toString();
            }
            if(i != 9) {
                str+="\n";
            }
        }
        return str;
    }
}

