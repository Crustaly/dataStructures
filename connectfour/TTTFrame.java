import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.awt.*;
import java.util.*;


public class TTTFrame extends JFrame implements  WindowListener, MouseListener {
    // Display message
    private String text = "";
    // the letter you are playing as
    private char player;
    // stores all the game data
    private GameData gameData = null;
    private long startTime = -1;
    private boolean sentResetRequest = false;
    private boolean sendingConfirmReset = false;
    // output stream to the s
    ObjectOutputStream os;

    private char turn;

    public TTTFrame(GameData gameData, ObjectOutputStream os, char player)
    {
        super("TTT Game");
        this.gameData = gameData;
        this.os = os;
        this.player = player;

        addWindowListener((WindowListener) this);
        addMouseListener(this);

        // makes closing the frame close the program
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Set initial frame message
        if(player == 'X')
            text = "Waiting for Black to Connect";

        setSize(700,800);
        setResizable(false);
        setAlwaysOnTop(true);
        setVisible(true);
    }

    public void paint(Graphics g)
    {
        // draws the background
        g.setColor(Color.BLUE);
        g.fillRect(0,0,getWidth(),getHeight());

        // draws the display text to the screen

        // draws the tic-tac-toe grid lines to the screen
        /*g.setColor(Color.RED);
        for(int y =0;y<=1; y++)
            g.drawLine(0,(y+1)*133+60,getWidth(),(y+1)*133+60);
        for(int x =0;x<=1; x++)
            g.drawLine((x+1)*133,60,(x+1)*133,getHeight());*/

        g.setColor(Color.white);

        // draws the player moves to the screen
        int actualR = 0;
        for(int r=gameData.getGrid().length-1; r>=0; r--) {
            for(int c=0; c<gameData.getGrid()[0].length; c++) {
                if(gameData.getGrid()[r][c]=='X') {
                    g.setColor(Color.RED);
                } else if(gameData.getGrid()[r][c]=='O') {
                    g.setColor(Color.BLACK);
                } else {
                    g.setColor(Color.white);
                }
                g.fillOval(c*100+50,actualR*100+50, 50,50);

            }
            actualR++;
        }

        //g.drawString(""+gameData.getGrid()[r][c],c*133+42,r*133+150);
        g.setColor(Color.RED);
        g.setFont(new Font("Times New Roman",Font.BOLD,30));
        g.drawString(text,20,750);

    }

    public void setText(String text) {
        this.text = text;
        repaint();
    }


    public void setTurn(char t) {
        turn = t;

        if(t==player)
            text = "Your turn";
        else
        {
            if(t=='e') {

            } else {
                if(t=='X') {
                    text = "Red's turn";
                } else {
                    text = "Black's turn";
                }
            }
        }
        repaint();
    }

    public void makeMove(int c, int r, char letter)
    {
        gameData.getGrid()[r][c] = letter;
        repaint();
    }





    public void resetGrid() {

        gameData.reset();
        startTime=-1;
        sentResetRequest=false;
        sendingConfirmReset=false;
    }
    public String getGrid() {return Arrays.deepToString(gameData.getGrid());}


    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        try {
            System.out.println("closing window");
            os.writeObject(new CommandFromClient(CommandFromClient.CLOSING, ""+player));
        } catch (IOException ex) {
            System.out.println("Exception with closing window");
            ex.printStackTrace();
        }

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

    public void closing() throws InterruptedException {
        System.out.println("Inside closing method");
        startTime = System.currentTimeMillis();
        long prev = -1;
        while(true) {
            long timepassed=System.currentTimeMillis()-startTime;
            long secondspassed=timepassed/1000;
            if(secondspassed>prev) {
                text = "Other Client Disconnected. Closing in: "+(5-secondspassed);
                System.out.println(text);
                prev=secondspassed;
                repaint();
            }
            if(secondspassed>=6) {
                break;
            }

        }
        System.exit(0);

    }
    public void initializeConfirm (String m, String title)
    {
        sendingConfirmReset=true;
        text = "Other client wants to reset. Press R to reset";






        //JOptionPane.showMessageDialog(null, m, title, JOptionPane.INFORMATION_MESSAGE);
    }
    public boolean getSentResetRequest() {
        return sentResetRequest;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mouseX = e.getX();
        int column = (mouseX - 50) / 100;
        if (column >= 0 && column < 7) {
            if (player == turn) {
                int row = gameData.addToLowest(column + 1, player); // Adjust column to 1-based index
                if (row != -1) {
                    try {
                        os.writeObject(new CommandFromClient(CommandFromClient.MOVE, "" + column + row + player));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
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
}
