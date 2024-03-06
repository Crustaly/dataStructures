import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;

public class TTTFrame extends JFrame implements WindowListener, ActionListener {
    // the letter you are playing as
    private String name;
    // stores all the game data
    private Data data = null;
    // output stream to the server
    ObjectOutputStream os;

    private JButton send;
    private JButton exit;
    private JTextArea namesArea;

    private JScrollPane namesPane;
    private JTextArea msgsArea;
    private JScrollPane msgsPane; //for messages to show up, lines separated by names

    public TTTFrame(Data data, ObjectOutputStream os, String name)
    {
        super("Chat Room");
        // sets the attributes
        this.data = data;
        this.os = os;
        this.name = name;

        msgsArea = new JTextArea(name + " has connected.\n");
        msgsPane = new JScrollPane(msgsArea);

        msgsPane.setBounds(10,10,500,650); //crystal pls change the size idk

        namesArea = new JTextArea(); // add names from data.getNames(), new line after every name
        namesPane = new JScrollPane(namesArea);

        //crystal help set bounds plsss

        send = new JButton("Send");
        exit = new JButton("Exit"); //on clicked closes window add action listener

        setLayout(null);
        addWindowListener((WindowListener)this);
        // makes closing the frame close the program
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setSize(770,750);
        setResizable(false);
        setAlwaysOnTop(true);
        setVisible(true);
    }

    public void paint(Graphics g)
    {
        // draws the background
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0,0,getWidth(),getHeight());

        // draws the display text to the screen
        g.setColor(Color.WHITE);
        g.setFont(new Font("Times New Roman",Font.BOLD,11));
    }

    public void addMsg(String msg)
    {
        data.getMsgs().add(name + ": " + msg);
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

    @Override
    public void actionPerformed(ActionEvent e) {

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
