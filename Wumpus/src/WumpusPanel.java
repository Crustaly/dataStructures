import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

public class WumpusPanel extends JPanel implements KeyListener {

    public static final int PLAYING = 0;
    public static final int DEAD = 1;
    public static final int WON = 2;
    private int status;
    private WumpusPlayer player;
    private WumpusMap map;
    private BufferedImage floor, arrow, fog, gold, ladder, pit, breeze, wumpus, deadWumpus, stench, playerUp, playerDown, playerLeft, playerRight;

    public WumpusPanel() throws IOException {
        addKeyListener(this);
        setSize(500,500);
        floor  = ImageIO.read(new File("Floor.gif"));
        arrow = ImageIO.read(new File("arrow.gif"));
        fog = ImageIO.read(new File("black.GIF"));
        gold = ImageIO.read(new File("gold.gif"));
        ladder = ImageIO.read(new File("ladder.gif"));
        pit = ImageIO.read(new File("pit.gif"));
        breeze = ImageIO.read(new File("breeze.gif"));
        wumpus = ImageIO.read(new File("wumpus.gif"));
        deadWumpus = ImageIO.read(new File("deadwumpus.GIF"));
        stench = ImageIO.read(new File("stench.gif"));
        playerUp = ImageIO.read(new File("playerUp.png"));
        playerDown = ImageIO.read(new File("playerDown.png"));
        playerLeft = ImageIO.read(new File("playerLeft.png"));
        playerRight = ImageIO.read(new File("playerRight.png"));
    }

    public void reset(){

    }

    public void paint(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(0,500,500,200);

        g.setColor(Color.RED);

        g.drawString("Inventory", 10,550);
        if(player.getArrow()){
            g.drawImage(arrow, 0,560, null);
        }
        if(player.getGold()){
            if(player.getArrow())  {
                g.drawImage(gold, 60, 560, null);
            } else {
                g.drawImage(gold, 0, 560, null);
            }
        }
        
        g.drawString("Messages", 150,550);

        g.setColor(Color.GRAY);
        g.fillRect(120,500,20,200);


    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
