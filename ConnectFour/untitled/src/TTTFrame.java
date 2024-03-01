import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;

public class TTTFrame extends JFrame implements WindowListener, MouseListener, KeyListener {
    // Display message
    private String text = "";
    // the letter you are playing as
    private char player;
    // stores all the game data
    private GameData gameData = null;
    private char turn;
    // output stream to the server
    ObjectOutputStream os;
    private long start = -1;
    private boolean resetRequest = false;
    private boolean confirmReset = false;

    public TTTFrame(GameData gameData, ObjectOutputStream os, char player)
    {
        super("Connect 4 Game");
        // sets the attributes
        this.gameData = gameData;
        this.os = os;
        this.player = player;

        // adds a KeyListener to the Frame
        addMouseListener(this);
        addWindowListener((WindowListener)this);
        addKeyListener(this);
        // makes closing the frame close the program
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Set initial frame message
        if(player == 'B')
            text = "Waiting for Red to Connect";
        else text = "Waiting for Black to Connect";

        setSize(770,750);
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

        for(int i = gameData.getGrid().length-1; i >= 0; i--){
            for(int j = 0; j<gameData.getGrid()[0].length; j++){
                //change oval
                if(gameData.getGrid()[i][j]=='B'){
                    g.setColor(Color.BLACK);
                }
                else if(gameData.getGrid()[i][j]=='R'){
                    g.setColor(Color.RED);
                }
                else{
                    g.setColor(Color.WHITE);
                }
                g.fillOval(100*j+35, 100*i+110, 90 , 90);
            }
        }


    }


    public void setText(String text) {
        this.text = text;
        repaint();
    }


    public void setTurn(char turn) {
        this.turn = turn;
        if(turn==player)
            text = "Your turn";
        else
        {
            if(turn == 'B')
                text = "Black's turn.";
            else if(turn == 'R'){
                text = "Red's turn.";
            }
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
        if (e.getModifiers() == MouseEvent.BUTTON3_MASK && e.getClickCount() == 1) {
            if (gameData.isWinner('B') || gameData.isWinner('R') || gameData.isCat()) {

                if (confirmReset) {
                    try {
                        os.writeObject(new CommandFromClient(CommandFromClient.CONFIRM, ""));
                    } catch (IOException o) {
                        o.printStackTrace();
                    }
                    return;
                }
                resetRequest = true;
                try {
                    os.writeObject(new CommandFromClient(CommandFromClient.RESTART, ""));
                } catch (Exception o) {
                    System.out.println("Exception reset");
                    o.printStackTrace();
                }
            }
        }
    }
    @Override
    public void mousePressed(MouseEvent e){
        if(player != turn) return;
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
        else if(pos > 635 && pos < 735)
            col = 6;

        int r = -1;
        for(int i = 5; i >= 0; i--){
            if(gameData.getGrid()[i][col] == ' '){
                r = i; break;
            }
        }

        if(col!=-1 && r!= -1) {
            try {
                os.writeObject(new CommandFromClient(CommandFromClient.MOVE, "" + col + r + player));
            } catch (Exception o) {
                o.printStackTrace();
            }
        }
        repaint();
    }

    public void resetGrid(){
        gameData.reset();
        start = -1;
        resetRequest = false;
        confirmReset = false;
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
        try {
            os.writeObject(new CommandFromClient(CommandFromClient.CLOSING, ""+player));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void closing() throws InterruptedException {
        start = System.currentTimeMillis();
        long prev = -1;
        while(true){
            long secondsPassed = (System.currentTimeMillis() - start)/1000;
            if(secondsPassed > prev){
                text = "Other client disconnected. Closing in: " + (5-secondsPassed);
                prev = secondsPassed;
                repaint();
            }
            if(secondsPassed >= 6) break;
        }

        System.exit(0);
    }

    public void confirm(){
        confirmReset = true;
        text = "Other client wants to reset. Right click to reset.";
        repaint();
    }

    public boolean getResetRequest(){
        return resetRequest;
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

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
