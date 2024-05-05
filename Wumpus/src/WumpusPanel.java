import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

public class WumpusPanel extends JPanel implements KeyListener {

    private WumpusPlayer player;
    private BufferedImage  pit, breeze, stench,wumpus, deadWumpus, gold, fog, ladder, floor, arrow, playerUp, playerDown, playerLeft, playerRight;
    private WumpusMap map;
    private BufferedImage buffer;
    public static final int PLAYING = 0;
    public static final int DEAD = 1;
    public static final int WON = 2;
    private int status;

    private ArrayList<String> msgs;
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

        resetGame();
       // reset();
        //add src if no work
    }


    public void paint(Graphics g) {

        g.setColor(Color.red);
        //g.setFont();
        g.drawString("Inventory", 5, 550);
        if (player.getArrow()) {
            g.drawImage(arrow, 0, 560, null);
            //starts out with an arrow
        }
        if (player.getGold()) {
            g.drawImage(gold, 60, 560, null);
        }

        g.drawString("Messages", 150, 550);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                WumpusSquare sq = map.getSquare(i, j);
                int r = i * 50;
                int c = j * 50;
                if (!cheat && !sq.getVisited()) {
                    g.drawImage(fog, r, c, null);
                    continue;
                }
                g.drawImage(floor, r, c, null);
                if (sq.getWumpus()) {
                    g.drawImage(wumpus, r, c, null);
                }
                if (sq.getDeadWumpus()) {
                    g.drawImage(deadWumpus, r, c, null);
                }
                if (sq.getLadder()) {
                    g.drawImage(ladder, r, c, null);
                }
                if (sq.getPit()) {
                    g.drawImage(pit, r, c, null);
                }
                if (sq.getGold()) {
                    g.drawImage(gold, r, c, null);
                }
                if (sq.getBreeze()) {
                    //g.drawImage()
                    g.drawImage(breeze, r, c, null);
                }
                if (sq.getStench()) {
                    //g.drawImage()
                    g.drawImage(stench, r, c, null);
                }


                if (player.getRowPosition() == i && player.getColPosition() == j) {
                    if (player.getDirection() == WumpusPlayer.NORTH) {
                        g.drawImage(playerUp, r, c, null);
                    }
                    if (player.getDirection() == WumpusPlayer.EAST) {
                        g.drawImage(playerRight, r, c, null);
                    }
                    if (player.getDirection() == WumpusPlayer.WEST) {
                        g.drawImage(playerLeft, r, c, null);
                    }
                    if (player.getDirection() == WumpusPlayer.SOUTH) {
                        g.drawImage(playerDown, r, c, null);
                    }

                }


            }
        }
    }

    public void addToMsgs(WumpusSquare square){
        if(square.getBreeze())
            msgs.add("You feel a breeze");
        else if(square.getStench()) msgs.add("You smell a stench");
        else if(square.getGold()) msgs.add("You see a glimmer");
        else if(square.getLadder()) msgs.add("You bumped into a ladder");
        else if(square.getPit()) {
            msgs.add("You fell down a pit to your death. Press N to restart");
            status = DEAD;
        }
        else if(square.getWumpus()) {
            msgs.add("You are eaten by the Wumpus. Press N to restart.");
            status = DEAD;
        }
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
    public void resetGame() {
        player = new WumpusPlayer();
        status = PLAYING;
        map = new WumpusMap();
        WumpusSquare sq = map.getSquare(player.getRowPosition(), player.getColPosition());
        sq.setVisited(true);
        player.setColPosition(map.getLadderCol());
        player.setRowPosition(map.getLadderCol());
        msgs = new ArrayList<>();
    }
}
