import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.util.ArrayList;
import java.util.Objects;

import java.util.*;
import java.io.*;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Random;
import java.sql.*;
public class StudentPanel extends JPanel{
    JLabel firstName = new JLabel("First Name:");
    JLabel lastName = new JLabel("Last Name:");
    JLabel ID = new JLabel("ID:");

    JTextField first = new JTextField();
    JTextField last = new JTextField();
    JTextField IDs = new JTextField();

    JButton save = new JButton("Save");
    JButton clear = new JButton("Clear");
    JButton saveChanges = new JButton("Save changes");
    JButton deleteContact = new JButton("Delete contact");

    ArrayList<Data> storage = new ArrayList<>();
    JList<Data> myContacts = new JList<>();

    JScrollPane scrolling = new JScrollPane();

    ArrayList<sectionData> sections;
    JList<sectionData> jSections;
    JScrollPane sectionScrolling;

    JLabel schedule = new JLabel("schedule");


    ArrayList<Component> allComponents = new ArrayList<>();
    JScrollPane bar;

    public StudentPanel(int width, int height, Statement statementName) throws SQLException {
        setSize(width, height);
        setLayout(null);
        //setBounds(0,0,w,h);
        sections = new ArrayList<>();
        jSections = new JList<>();
        sectionScrolling = new JScrollPane();
        schedule.setBounds(50,420,100,10);
        add(schedule);

        //missing the try part add files in!


        firstName.setText("first");
        firstName.setBounds(280, 100, 100, 50);
        add(firstName);

        first.setText("first");
        first.setBounds(390,120,100,20);
        add(first);

        setVisible(true);

        lastName.setBounds(280, 150, 100, 20);
        add(lastName);
        last.setText("last");//get from sql
        last.setBounds(390, 150, 100, 20);
        add(last);

        ID.setBounds(280, 200, 100, 20);
        add(ID);
        IDs.setText("ID");//get from sql
        IDs.setBounds(390, 200, 100, 20);
        IDs.setEditable(false);
        add(IDs);

        saveChanges.setBounds(280, 310, 100, 20);
        saveChanges.setText("Save Changes");
        saveChanges.setVisible(true);
        add(saveChanges);
        setVisible(true);

    }


}
