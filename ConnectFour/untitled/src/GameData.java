public class GameData
{
    private char[][] grid = {{' ',' ', ' ', ' ', ' ', ' ', ' '},{' ',' ', ' ', ' ', ' ', ' ', ' '},{' ',' ', ' ', ' ', ' ', ' ', ' '},{' ',' ', ' ', ' ', ' ', ' ', ' '},{' ',' ', ' ', ' ', ' ', ' ', ' '},{' ',' ', ' ', ' ', ' ', ' ', ' '}};

    public char[][] getGrid()
    {
        return grid;
    }

    public void reset()
    {
        grid = new char[6][7];
        for(int r=0;r<grid.length; r++)
            for(int c=0; c<grid[0].length; c++)
                grid[r][c]=' ';
    }

   
    public boolean isCat()
    {
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[0].length; j++) {
                if(grid[i][j]==' ') {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isWinner(char letter)
    {
        //horiz win
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j+3 < grid[0].length; j++) {
                if(grid[i][j]==letter&&grid[i][j+1]==letter&&grid[i][j+2]==letter&&grid[i][j+3]==letter) {
                    return true;
                }
            }
        }

        //vert
        for(int i = 0; i+3 < grid.length; i++) {
            for(int j = 0; j < grid[0].length; j++) {
                if(grid[i][j]==letter&&grid[i+1][j]==letter&&grid[i+2][j]==letter&&grid[i+3][j]==letter) {
                    return true;
                }
            }
        }

        //diagonal
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[0].length; j++) {
                if(diag('R', i, j) || diag('B', i, j)) return true;
            }
        }
        return false;
    }

    public boolean diag(char player, int r, int c)
    {
        int row = r;
        int col = c;
        int cnt =0;

        while (row >= 0  && col >= 0) {
            if (grid[row][col] == player) cnt++;
            else { break; }
            col--; row--;
        }
        row = r + 1;
        col = c + 1;
        while(row < 6 && col < 7)
        {
            if (grid[row][col] == player) cnt++;
            else { break; }
            col++; row++;
        }
        if (cnt >= 4) return true;
        //2nd diag
        cnt = 0;
        row = r;
        col = c;
        while(row >= 0 && col < 7)
        {
            if (grid[row][col] == player) cnt++;
            else { break; }
            col++; row--;
        }
        row = r + 1;
        col = c - 1;
        while(row < 6 && col >= 0)
        {
            if (grid[row][col] == player) cnt++;
            else { break; }
            col--; row++;
        }
        if (cnt >= 4) return true;
        return false;
    }

}
