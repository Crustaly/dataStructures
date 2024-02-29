import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;

public class TTTFrame extends JFrame implements WindowListener, MouseListener {
    // Display message
    private String text = "";
    // the letter you are playing as
    private char player;
    // stores all the game data
    private GameData gameData = null;
    // output stream to the server
    ObjectOutputStream os;

    public TTTFrame(GameData gameData, ObjectOutputStream os, char player)
    {
        super("Connect 4 Game");
        // sets the attributes
        this.gameData = gameData;
        this.os = os;
        this.player = player;

        // adds a KeyListener to the Frame
        addMouseListener(this);

        // makes closing the frame close the program
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Set initial frame message
        if(player == 'B')
            text = "Waiting for Red to Connect";
        else text = "Waiting for Black to Connect";

        setSize(760,750);
        setResizable(false);
        setAlwaysOnTop(true);
        setVisible(true);
    }

    public void paint(Graphics g)
    {
        // draws the background
        g.setColor(Color.YELLOW);
        g.fillRect(0,0,getWidth(),getHeight());

        // draws the display text to the screen
        g.setColor(Color.RED);
        g.setFont(new Font("Times New Roman",Font.BOLD,30));
        g.drawString(text,20,55);

        // draws the tic-tac-toe grid lines to the screen

        for(int i =0;i<6;i++){
            for(int j =0;j<7;j++){
                if(gameData.getGrid()[i][j]==' '){
                    g.setColor(Color.WHITE);
                    g.fillOval(100*j+35, 100*i+110, 90 , 90);
                }
                else if(gameData.getGrid()[i][j]=='R'){
                    g.setColor(Color.RED);
                    g.fillOval(100*j+35, 100*i+110, 90 , 90);
                }
                else{
                    g.setColor(Color.BLACK);
                    g.fillOval(100*j+35, 100*i+110, 90 , 90);
                }
            }
        }
        if(checkWin('R')){

        }
        else if(checkWin('B')){

    }
        //change oval

        // draws the player moves to the screen
    /*    g.setFont(new Font("Times New Roman",Font.BOLD,70));
        for(int r=0; r<gameData.getGrid().length; r++)
            for(int c=0; c<gameData.getGrid().length; c++)
                g.drawString(""+gameData.getGrid()[r][c],c*133+42,r*133+150);

     */
    }

    public boolean checkWin(char player) {
        char[][] grid = gameData.getGrid();
        // Check horizontal
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 4; col++) {
                if (grid[row][col] == player &&
                        grid[row][col + 1] == player &&
                        grid[row][col + 2] == player &&
                        grid[row][col + 3] == player) {
                    return true;
                }
            }
        }
        for (int col = 0; col < 7; col++) {
            for (int row = 0; row < 3; row++) {
                if (grid[row][col] == player &&
                        grid[row + 1][col] == player &&
                        grid[row + 2][col] == player &&
                        grid[row + 3][col] == player) {
                    return true;
                }
            }
        }
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 4; col++) {
                if (grid[row][col] == player &&
                        grid[row + 1][col + 1] == player &&
                        grid[row + 2][col + 2] == player &&
                        grid[row + 3][col + 3] == player) {
                    return true;
                }
            }
        }
        for (int row = 3; row < 6; row++) {
            for (int col = 0; col < 4; col++) {
                if (grid[row][col] == player &&
                        grid[row - 1][col + 1] == player &&
                        grid[row - 2][col + 2] == player &&
                        grid[row - 3][col + 3] == player) {
                    return true;
                }
            }
        }
        return false;
    }


    public void setText(String text) {
        this.text = text;
        repaint();
    }


    public void setTurn(char turn) {
        if(turn==player)
            text = "Your turn";
        else
        {
            text = turn+"'s turn.";
        }
        repaint();
    }

    public void makeMove(int c, int r, char letter)
    {
        gameData.getGrid()[r][c] = letter;
        repaint();
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e){
        float pos = e.getX();
        int col = -1;
        //7 columns
        if(pos > 35 && pos < 135) {
            col = 0;
        }
        else if(pos > 135 && pos < 235){
            col = 1;
        }
        else if(pos > 235 && pos < 335){
            col = 2;
        }
        else if(pos > 335 && pos < 435){
            col = 3;
        }
        else if(pos > 435 && pos < 535){
            col = 4;
        }
        else if(pos > 535 && pos < 635){
            col = 5;
        }
        else col = 6;

        int r = -1;
        for(int i = 6; i >= 0; i--){
            if(gameData.getGrid()[i][col] == ' '){
                r = i; break;
            }
        }
    makeMove(r,col, player);

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
