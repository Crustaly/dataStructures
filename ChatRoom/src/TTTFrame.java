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
    private DefaultListModel<String> mess;
    private ArrayList<String> names = new ArrayList<>();
    private DefaultListModel<String> people;
    private JButton send;
    private JButton exit;
    private JList<String> namesList;
    private JList<String> msgsList;
    private JScrollPane namesPane;
    private JTextArea sendArea;
    private JScrollPane msgsPane; //for messages to show up, lines separated by names
    private JLabel chatLabel;
    public TTTFrame(Data data, ObjectOutputStream os, String name) {
        super("Chat Room");
        this.os = os;
        this.name = name;
        this.data = data;
        System.out.println(names);
        people = new DefaultListModel<>();
        for(String s: names){
            people.addElement(s);
        }

        mess = new DefaultListModel<>();
        for(String s: data.getMsgs()){
            mess.addElement(s);
        }

        namesList = new JList<String>(people);
        msgsList = new JList<String>(mess);

        chatLabel = new JLabel("Chat                                                                                                                  Users");
        chatLabel.setBounds(50,25,500,35);
        add(chatLabel);
        msgsPane = new JScrollPane(msgsList);
        msgsPane.setViewportView(msgsList);

        msgsPane.setBounds(50, 50, 7 * 50, 7 * 50);
        msgsPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // namesArea.setBounds(50 * 9, 50, 50 * 2, 50 * 7);
        namesPane = new JScrollPane(namesList);
        namesPane.setViewportView(namesList);
        namesPane.setBounds(50 * 9, 50, 50 * 2, 50 * 7);
        sendArea = new JTextArea();
        sendArea.setBounds(50, 9 * 50, 7 * 50, 2 * 50);

        send = new JButton("Send");
        send.setBounds(9 * 50, 9 * 50, 100, 25);
        send.addActionListener(e -> {
            try {
                os.writeObject(new CommandFromClient(CommandFromClient.SEND, sendArea.getText()));
                sendArea.setText("");
            } catch (Exception o) {
                o.printStackTrace();
            }
            repaint();
        });

        exit = new JButton("Exit"); // on clicked closes window add action listener
        exit.setBounds(9 * 50, 10 * 50, 100, 25);
        exit.addActionListener(e -> this.dispose());
        exit.setVisible(true);
        add(msgsPane);
        add(namesPane);
        add(sendArea);
        add(send);
        add(exit);

        setLayout(null);
        addWindowListener(this); // Add this as a window listener

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(12 * 50, 13 * 50);
        setResizable(false);
        setAlwaysOnTop(true);
        setVisible(true);


        // Adding components to the frame

    }
    public void exitButton(){

    }


    public void addMsg(String msg)
    {
        if(msg!= "") {
            String s = name + ": " + msg;
            data.sendMsg(s);
            System.out.println(data.getMsgs());
        }


    }//command to client

    public void setNames(String names){
        String[] ar = names.substring(1, names.length() - 1).split(",");
        this.names.clear();
        for(String s: ar){
            this.names.add(s);
        }
        Collections.sort(this.names);
        people.clear();

        for(String s: this.names){
            people.addElement(s);
        }
    }
    @Override
    public void windowOpened(WindowEvent e) {

    }


    @Override
    public void windowClosing(WindowEvent e) {
        try {
            os.writeObject(new CommandFromClient(CommandFromClient.EXIT, name));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void closing() throws InterruptedException {
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