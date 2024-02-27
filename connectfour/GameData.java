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
        if(grid[0][0] !=' ' && grid[0][1] !=' ' && grid[0][2] !=' '
                && grid[1][0] !=' ' && grid[1][1] !=' ' && grid[1][2] !=' '
                && grid[2][0] !=' ' && grid[2][1] !=' ' && grid[2][2] !=' '
                && !isWinner('X') && !isWinner('O'))
            return true;
        else
            return false;
    }

    public boolean isWinner(char letter)
    {
        for(int i = 0; i<6; i++){
            if(checkRows(letter, i)) return true;
        }
        for(int i = 0; i<7; i++){
            if (checkCol(letter, i)) return true;
        }
        for(int i = 0; i<6; i++){
            for(int j = 0; j< 7; j++){
                if(diag(letter, i, j)) return true;
            }
        }

        return false;
    }

    public boolean checkTie()
    {
        int cnt = 0;
        for(int i = 0; i<6; i++)
        {
            for(int j = 0; j<7; j++)
            {
                if (grid[i][j] != ' ') cnt++;
            }
        }
        return cnt == 42 && !isWinner('B') && !isWinner('R');
    }
    public boolean checkRows(char player, int r)
    {
        int temp = 0;
        for(int j = 0; j<7; j++)
        {
            if (grid[r][j] == player) temp++;
            else temp = 0;
            if (temp >= 4) return true;
        }

        return false;
    }

    public boolean checkCol(char player, int c)
    {
        int temp = 0;
        for(int i = 0; i<6; i++)
        {
            if (grid[i][c] == player) temp++;
            else temp = 0;
            if (temp >= 4) return true;
        }
        return false;
    }

    public boolean diag(char player, int r, int c)
    {
        int row = r;
        int col = c;
        int cnt = 0;
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
