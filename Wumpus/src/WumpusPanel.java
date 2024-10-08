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
    private boolean cheatMode;
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
        g.drawString("Inventory: ", 5, 550);
        if (player.getArrow()) {
            g.drawImage(arrow, 0, 560, null);
            //starts out with an arrow
        }
        if (player.getGold()) {
            g.drawImage(gold, 60, 560, null);
        }

        g.drawString("Game Messages: ", 155, 550);
        if(msgs.size() > 0) {
            g.setColor(Color.black);
            g.drawString(msgs.get(msgs.size() -1), 155, 600);
        }

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                WumpusSquare loc = map.getSquare(i, j);
                int r = i * 50;
                int c = j * 50;
                if (!cheatMode && !loc.getVisited()) {
                    g.drawImage(fog, r, c, null);
                    continue;
                }
                g.drawImage(floor, r, c, null);
                if (loc.getWumpus()) {
                    g.drawImage(wumpus, r, c, null);
                }
                if (loc.getDeadWumpus()) {
                    g.drawImage(deadWumpus, r, c, null);
                }
                if (loc.getLadder()) {
                    g.drawImage(ladder, r, c, null);
                    System.out.println("ladder" + r + " " + c);
                }
                if (loc.getPit()) {
                    g.drawImage(pit, r, c, null);
                }
                if (loc.getGold()) {
                    g.drawImage(gold, r, c, null);
                }
                if (loc.getBreeze()) {
                    //g.drawImage()
                    g.drawImage(breeze, r, c, null);
                }
                if (loc.getStench()) {
                    //g.drawImage()
                    g.drawImage(stench, r, c, null);
                }


                if (player.getRowPosition() == i && player.getColPosition() == j) {
                    System.out.println("player" + r + " " + c);
                    if (player.getDirection() == WumpusPlayer.EAST) {
                        g.drawImage(playerRight, r, c, null);
                    }
                    if (player.getDirection() == WumpusPlayer.WEST) {
                        g.drawImage(playerLeft, r, c, null);
                    }
                    if (player.getDirection() == WumpusPlayer.NORTH) {
                        g.drawImage(playerUp, r, c, null);
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
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        System.out.println("Pressed: ");
        if(keyCode == 78) { //n pressed
            //the game gets reset
            System.out.println("GAME SHOULD RESET NOW");
            resetGame();
            repaint();
            return;
        }
        if(status != PLAYING) return;
        if(keyCode == 78) { //n pressed
            //the game gets reset
            System.out.println("GAME SHOULD RESET NOW");
            resetGame();
            repaint();
            return;
        }
        if(player.getColPosition() > 0 && keyCode == 87) { //w pressed
            //mark as vis!
            map.getSquare(player.getRowPosition(), player.getColPosition()).setVisited(true);
            //go up by 1, col-1
            player.setColPosition(player.getColPosition()-1);
            player.setDirection(WumpusPlayer.NORTH);
            addToMsgs(map.getSquare(player.getRowPosition(), player.getColPosition()));
        }

        if(player.getColPosition() < 9 && keyCode == 83) {//s pressed
            //mark as vis!
            map.getSquare(player.getRowPosition(), player.getColPosition()).setVisited(true);
            //go down by 1, col+1
            player.setColPosition(player.getColPosition()+1);
            player.setDirection(WumpusPlayer.SOUTH);
            addToMsgs(map.getSquare(player.getRowPosition(), player.getColPosition()));

        }
        if(keyCode == 65 && player.getRowPosition() > 0) {//a pressed
            //going left
            map.getSquare(player.getRowPosition(), player.getColPosition()).setVisited(true);
            //left is col-1
            player.setRowPosition(player.getRowPosition()-1);
            player.setDirection(WumpusPlayer.WEST);

            addToMsgs(map.getSquare(player.getRowPosition(), player.getColPosition()));
        }
        if(keyCode == 68 && player.getRowPosition() < 9) {//d pressed
            //going right

            map.getSquare(player.getRowPosition(), player.getColPosition()).setVisited(true);
            //left is col+1
            player.setRowPosition(player.getRowPosition()+1);
            player.setDirection(WumpusPlayer.EAST);

            addToMsgs(map.getSquare(player.getRowPosition(), player.getColPosition()));

        }
        if(keyCode == 73 && player.getArrow() && status != DEAD) {//i pressed
            // Shoots upward
            if(player.getArrow()){
                for(int j = 0; j<player.getColPosition(); j++){
                    WumpusSquare square = map.getSquare(player.getRowPosition(), j);
                    if(square.getWumpus()){
                        square.setWumpus(false);
                        square.setDeadWumpus(true);
                        msgs.add("You hear a scream");
                    }
                }
                player.setArrow(false);
                msgs.add("Wumpus is still alive!");
            }

            // (only works if you have an arrow)
        }
        if(keyCode == 75 && player.getArrow() && status != DEAD) {//k pressed
            // Shoots downward
            if(player.getArrow()){
                for(int i = player.getRowPosition(); i<10; i++){
                    WumpusSquare square = map.getSquare(i, player.getColPosition());
                    if(square.getWumpus()){
                        square.setWumpus(false);
                        square.setDeadWumpus(true);
                        msgs.add("You hear a scream");
                    }
                }
                player.setArrow(false);
                msgs.add("Wumpus is still alive!");
            }
            // (only works if you have an arrow)
        }
        if(keyCode == 74 && player.getArrow() && status != DEAD) {//j pressed
            //Shoots left
            if(player.getArrow()){
                for(int i = 0; i<player.getRowPosition(); i++){
                    WumpusSquare square = map.getSquare(i, player.getColPosition());
                    if(square.getWumpus()){
                        square.setWumpus(false);
                        square.setDeadWumpus(true);
                        msgs.add("You hear a scream");
                    }
                }
                player.setArrow(false);
                msgs.add("Wumpus is still alive!");
            }
            //      (only works if you have an arrow)

        }
        if(keyCode == 76 && player.getArrow() && status != DEAD) {//L pressed
            // Shoots right
            if(player.getArrow()){
                for(int j = player.getColPosition(); j<10; j++){
                    WumpusSquare square = map.getSquare(player.getRowPosition(), j);
                    if(square.getWumpus()){
                        square.setWumpus(false);
                        square.setDeadWumpus(true);
                        msgs.add("You hear a scream");
                    }
                }
                player.setArrow(false);
                msgs.add("Wumpus is still alive!");
            }
            //(only works if you have an arrow)
        }

        if(keyCode == 80 && map.getSquare(player.getRowPosition(),player.getColPosition()).getGold()) {//P pressed
            //
            //Picks up the gold
            //
            //(only when on the square with the gold)
            boolean pickUp = true;
            player.setGold(pickUp);

            //set map gold as not true because you picked it up
            boolean gold = false;
            map.getSquare(player.getRowPosition(),player.getColPosition()).setGold(gold);
        }
        if(player.getGold() && map.getSquare(player.getRowPosition(), player.getColPosition()).getLadder() && keyCode == 67) { //c pressed

            //    Climbs the ladder
            //         (only works if you have the gold)
            status = WON;

            msgs.add("YOU WON! PRESS N TO RESTART THE GAME");
        }
        if(keyCode == 56) {
            if(cheatMode){
                cheatMode = false;
            }
            else{
                cheatMode=  true;
            }
        }


        map.getSquare(player.getRowPosition(), player.getColPosition()).setVisited(true);
        repaint();
    }
    public void resetGame() {
        player = new WumpusPlayer();
        status = PLAYING;
        map = new WumpusMap();
        player.setColPosition(map.getLadderCol());
        player.setRowPosition(map.getLadderRow());
      //  System.out.println(player.getColPosition() + " " + map.getLadderCol());
      //  System.out.println(player.getRowPosition() + " " + map.getLadderRow());
        WumpusSquare sq = map.getSquare(player.getRowPosition(), player.getColPosition());
        sq.setVisited(true);
        msgs = new ArrayList<>();
        msgs.add("You bumped into a ladder");
        cheatMode = false;
    }
    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public void addNotify(){
        super.addNotify();
        requestFocus();
    }
}
