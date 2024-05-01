public class WumpusMap {

    public static final int NUM_ROWS = 10;
    public static final int NUM_COLUMNS = 10;
    public static final int NUM_PITS = 10;

    private WumpusSquare[][] grid;
    public static int ladderC, ladderR;

    public WumpusMap(){ createMap();}

    public void createMap(){
        grid = new WumpusSquare[10][10];

        for(int i = 0; i<grid.length;i++){
            for(int j = 0; j<grid[0].length; j++){
                grid[i][j] = new WumpusSquare();
            }
        }

        for(int i = 0; i<NUM_PITS; i++){
            while(true){
                int r = (int) (Math.random() * 8) + 1;
                int c = (int) (Math.random() * 8) + 1;
                if(!grid[r][c].getPit() && !grid[r][c].getBreeze()){
                    grid[r][c].setPit(true);
                    grid[r-1][c].setBreeze(true);
                    grid[r+1][c].setBreeze(true);
                    grid[r][c-1].setBreeze(true);
                    grid[r][c+1].setBreeze(true);
                    break;
                }
            }

            while(true) {
                int r = (int) (Math.random() * 10);
                int c = (int) (Math.random() * 10);
                if(!grid[r][c].getPit()) {
                    grid[r][c].setGold(true);
                    break;
                }
            }

            while(true) {
                int r = (int) (Math.random() * 8)+1;
                int c = (int) (Math.random() * 8)+1;
                if(!grid[r][c].getPit() && !grid[r][c].getGold()) {
                    grid[r][c].setWumpus(true);
                    grid[r-1][c].setStench(true);
                    grid[r+1][c].setStench(true);
                    grid[r][c-1].setStench(true);
                    grid[r][c+1].setStench(true);
                    break;
                }
            }

            while(true) {
                int r = (int) (Math.random() * 10);
                int c = (int) (Math.random() * 10);
                if(!grid[r][c].getGold()&&!grid[r][c].getWumpus() && !grid[r][c].getPit()) {
                    grid[r][c].setLadder(true);
                    ladderC = c;
                    ladderR = r;
                    break;
                }
            }
        }
    }

    public WumpusSquare getSquare(int row, int col) {
        if(col >= grid[0].length || col < 0 || row < 0 || row >= grid.length) {
            return null;
        }
        return grid[row][col];
        //return getSquare(row, col);
    }

    public String toString() {
        String str = "";
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[0].length; j++) {
                str+=grid[i][j].toString();
            }
            if(i != grid.length-1) {
                str+="\n";
            }
        }
        return str;
    }
}
