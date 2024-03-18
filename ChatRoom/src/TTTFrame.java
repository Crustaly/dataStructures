import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.*;

public class TTTFrame extends JFrame implements WindowListener, ActionListener {
    // the letter you are playing as
    private String name;
    // output stream to the server
    ObjectOutputStream os;

    private Data data;
    private JButton send;
    private JButton exit;
    private JTextArea namesArea;

    private JScrollPane namesPane;
    private JTextArea sendArea;
    private JTextArea msgsArea;

    private JScrollPane msgsPane; //for messages to show up, lines separated by names

    public TTTFrame(Data data, ObjectOutputStream os, String name) {
        super("Chat Room");
        this.os = os;
        this.name = name;
        this.data = data;
        msgsArea = new JTextArea(name + " has connected.\n");
        msgsPane = new JScrollPane(msgsArea);
        msgsArea.setBounds(50, 50, 7 * 50, 7 * 50);

        namesArea = new JTextArea(); // add names from data.getNames(), new line after every name
       // namesArea.setBounds(50 * 9, 50, 50 * 2, 50 * 7);
        namesPane = new JScrollPane(namesArea);
        namesPane.setBounds(50 * 9, 50, 50 * 2, 50 * 7);
        sendArea = new JTextArea();
        sendArea.setBounds(50, 9 * 50, 7 * 50, 2 * 50);

        send = new JButton("Send");
        send.setBounds(9 * 50, 9 * 50, 100, 25);
        send.addActionListener(e -> {sendMessage();});
        exit = new JButton("Exit"); // on clicked closes window add action listener
        exit.setBounds(9 * 50, 10 * 50, 100, 25);
        exit.addActionListener(e -> this.dispose());
        setLayout(null);
        addWindowListener(this); // Add this as a window listener

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(12 * 50, 13 * 50);
        setResizable(false);
        setAlwaysOnTop(true);
        setVisible(true);

        // Adding components to the frame
        add(msgsArea);
        add(namesPane);
        add(sendArea);
        add(send);
        add(exit);
    }

    public void exitButton(){
        
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
        data.sendMsg(name + ": " + msg);
    }//command to client

    @Override
    public void windowOpened(WindowEvent e) {

    }


    @Override
    public void windowClosing(WindowEvent e) {
        try {
            os.writeObject(new CommandFromClient(CommandFromClient.EXIT, ""));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void closing() throws InterruptedException {


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
