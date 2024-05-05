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

    private boolean cheat=false;
    private WumpusPlayer player;
    private WumpusMap map;
    private BufferedImage buffer; //idk what to do with this
    private BufferedImage floor, arrow, fog, gold, ladder, pit, breeze, wumpus, deadWumpus, stench, playerUp, playerDown, playerLeft, playerRight;
    static ArrayList<String> list;
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

        reset();
        //add src if no work
    }

    public void reset(){
        list = new ArrayList<>();
        list.add("You bumped into a ladder!");
        status = PLAYING;
        map = new WumpusMap();
        player = new WumpusPlayer();
        player.setColPosition(map.getLadderCol());
        player.setRowPosition(map.getLadderRow());
        WumpusSquare sq = map.getSquare(player.getRowPosition(), player.getColPosition());

        sq.setVisited(true);
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
                    if (player.getDirection() == WumpusPlayer.SOUTH) {
                        g.drawImage(playerDown, r, c, null);
                    }
                    if (player.getDirection() == WumpusPlayer.EAST) {
                        g.drawImage(playerRight, r, c, null);
                    }
                    if (player.getDirection() == WumpusPlayer.WEST) {
                        g.drawImage(playerLeft, r, c, null);
                    }
                }
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        char dir = e.getKeyChar();
        if(dir == 'n') {
            reset();
            repaint();
            return;
        }
        if(status == WON || status == DEAD) {
            return;
        }

        if(dir == 'w' && player.getColPosition() > 0) {

            map.getSquare(player.getRowPosition(), player.getColPosition()).setVisited(true);
            player.setColPosition(player.getColPosition()-1);
            player.setDirection(WumpusPlayer.NORTH);
            WumpusSquare sq = map.getSquare(player.getRowPosition(), player.getColPosition());

            listAdder(sq);
        }

        if(dir == 's'&& player.getColPosition() < 9) {
            map.getSquare(player.getRowPosition(), player.getColPosition()).setVisited(true);
            player.setColPosition(player.getColPosition()+1);
            player.setDirection(WumpusPlayer.SOUTH);
            //change
            WumpusSquare sq = map.getSquare(player.getRowPosition(), player.getColPosition());

            listAdder(sq);

        }
        if(dir == 'a'&& player.getRowPosition() > 0) {
            map.getSquare(player.getRowPosition(), player.getColPosition()).setVisited(true);
            player.setRowPosition(player.getRowPosition()-1);
            player.setDirection(WumpusPlayer.WEST);
            WumpusSquare sq = map.getSquare(player.getRowPosition(), player.getColPosition());

            listAdder(sq);

        }
        if(dir == 'd'&& player.getRowPosition() < 9) {
            map.getSquare(player.getRowPosition(), player.getColPosition()).setVisited(true);
            player.setRowPosition(player.getRowPosition()+1);
            player.setDirection(WumpusPlayer.EAST);
            WumpusSquare sq = map.getSquare(player.getRowPosition(), player.getColPosition());

            listAdder(sq);

        }
        if(dir =='i'&&player.getArrow()) {
            player.setArrow(false);
            for(int i = player.getColPosition(); i >= 0; i--) {
                if(map.getSquare(player.getRowPosition(), i).getWumpus()) {
                    map.getSquare(player.getRowPosition(),i).setWumpus(false);
                    map.getSquare(player.getRowPosition(),i).setDeadWumpus(true);
                    list.add("You hear a scream");
                    break;
                }
            }
        }
        if(dir == 'k'&&player.getArrow()) {
            player.setArrow(false);
            for(int i = player.getColPosition(); i <10; i++) {
                if(map.getSquare(player.getRowPosition(), i).getWumpus()) {
                    map.getSquare(player.getRowPosition(),i).setWumpus(false);
                    map.getSquare(player.getRowPosition(),i).setDeadWumpus(true);
                    System.out.println("scream arrow down");
                    list.add("You hear a scream");
                    break;
                }
            }
        }
        if(dir == 'l' && player.getArrow()) {
            player.setArrow(false);
            for(int i = player.getRowPosition(); i <10; i++) {
                if(map.getSquare(i, player.getColPosition()).getWumpus()) {
                    map.getSquare(i, player.getColPosition()).setWumpus(false);
                    map.getSquare(i, player.getColPosition()).setDeadWumpus(true);
                    System.out.println("scream arrow right");
                    list.add("You hear a scream");
                    break;
                }
            }
        }
        if(dir == 'j' && player.getArrow()) {
            player.setArrow(false);
            for(int i = player.getRowPosition(); i >= 0; i--) {
                if(map.getSquare(i, player.getColPosition()).getWumpus()) {
                    map.getSquare(i, player.getColPosition()).setWumpus(false);
                    map.getSquare(i, player.getColPosition()).setDeadWumpus(true);
                    System.out.println("scream arrow left");
                    list.add("You hear a scream");
                    break;
                }
            }
        }
        if(dir == '*') {
            cheat = !cheat;
        }
        if(dir == 'n') {
            reset();
        }
        if(dir == 'p'&&map.getSquare(player.getRowPosition(),player.getColPosition()).getGold()) {
            player.setGold(true);
            map.getSquare(player.getRowPosition(),player.getColPosition()).setGold(false);
        }
        if(dir == 'c' & player.getGold() && map.getSquare(player.getRowPosition(), player.getColPosition()).getLadder()) {
            status = WON;
            System.out.println("won");
            list.add("You win. (N for new game)");
        }
        System.out.println("KeyPressed: " + dir +" | Row: " + player.getRowPosition() + " | Col: " + player.getColPosition());
        map.getSquare(player.getRowPosition(), player.getColPosition()).setVisited(true);
        repaint();


    }
    public void listAdder(WumpusSquare sq) {
        if(sq.getStench()) {
            list.add("You smell a stench");
        }
        if(sq.getBreeze()) {
            list.add("You feel a breeze");
        }
        if(sq.getGold()) {
            list.add("You see a glimmer");
        }
        if(sq.getPit()) {
            list.add("You fell down a pit to your death");
            list.add("Game Over. (N for new game)");
            status = DEAD;
        }
        if(sq.getLadder()) {
            list.add("You bump into a ladder");
        }
        if(sq.getDeadWumpus()) {
            list.add("You smell a stench");
        }
        if(sq.getWumpus()) {
            list.add("You are eaten by the Wumpus");
            list.add("Game Over. (N for new game)");
            status = DEAD;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
