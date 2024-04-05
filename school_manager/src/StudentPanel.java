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

        try
        {
            ResultSet rs = statementName.executeQuery("SELECT id, first_name, last_name FROM student;");
            while(rs!=null&&rs.next())
            {
                Data temp = new Data(rs.getString("first_name"), rs.getString("last_name"),  rs.getInt("id") + "");
                storage.add(temp);
            }
            myContacts.setListData(storage.toArray(new Data[0]));
        }
        catch(Exception e) {
            System.out.println("exception in loading student info");
        }



        firstName.setText("first");
        firstName.setBounds(280, 100, 100, 50);
        add(firstName);

        first.setText("first");
        first.setBounds(390,120,100,20);
        add(first);

        setVisible(true);

        lastName.setBounds(280, 150, 100, 20);
        add(lastName);
        last.setText("last");
        last.setBounds(390, 150, 100, 20);
        add(last);

        ID.setBounds(280, 200, 100, 20);
        add(ID);
        IDs.setText("ID");
        IDs.setBounds(390, 200, 100, 20);
        IDs.setEditable(false);
        add(IDs);

        saveChanges.setBounds(280, 310, 100, 20);
        saveChanges.setText("Save Changes");
        saveChanges.setVisible(true);
        add(saveChanges);
        setVisible(true);
        saveChanges.addActionListener(e ->
        {
            Data s = myContacts.getSelectedValue();
            int index = storage.indexOf(s);
            Data temporary = new Data(first.getText(), last.getText(), IDs.getText());
            storage.set(index, temporary);
            first.setText("");
            last.setText("");
            IDs.setText("");

            storage = sort(storage);
            System.out.println(Arrays.toString(storage.toArray(new Data[0])));
            myContacts.setListData(storage.toArray(new Data[0]));

            saveChanges.setVisible(false);
            deleteContact.setVisible(false);

            save.setVisible(true);
            clear.setVisible(true);
            System.out.println("Got here");

            try
            {
                //hello i am crysatl yang please do the sql stuff and set up
                System.out.println(first.getText());
                String firstName = temporary.getFirst();
                String lastName = temporary.getLast();
                int ID = Integer.parseInt(temporary.getID());
                statementName.executeUpdate("UPDATE student SET first_name='" + firstName +"' WHERE id=" + ID + ";");
                statementName.executeUpdate("UPDATE student SET last_name='" + lastName   +"' WHERE id=" +  ID + ";");

//               update student and first nae and id
            }
            catch(Exception a)
            {
                System.out.println("problem within Save changes");
            }

        });


        deleteContact.setBounds(390, 310, 100, 20);
        add(deleteContact);

        deleteContact.addActionListener(e ->
        {
            System.out.println("Tried to delete");
            Data s = myContacts.getSelectedValue();
            int ids = Integer.parseInt(s.getID());
            first.setText("");
            last.setText("");
            IDs.setText("");
            storage.remove(s);
            storage = sort(storage);
            myContacts.setListData(storage.toArray(new Data[0]));
            saveChanges.setVisible(false);
            deleteContact.setVisible(false);
            save.setVisible(true);
            clear.setVisible(true);
            System.out.println("Got here");

            try {
                statementName.executeUpdate("DELETE FROM student WHERE id =" + ids + ";");
            } catch (SQLException ex) {
                ex.printStackTrace();
                System.out.println("Not good in delete contact");
            }
        });

        save.setBounds(280, 280, 100, 20);
        add(save);

        save.addActionListener(e ->
        {
            System.out.println("Tried to save");

            if (first.getText().equals("") || last.getText().equals("")) {
                System.out.println("bad input");
                message("put in input please bruh", "Message");
            } else {
                Data temp = new Data(first.getText(), last.getText(), IDs.getText());
                storage.add(temp);
                storage = sort(storage);
                myContacts.setListData(storage.toArray(new Data[0]));
                first.setText("");
                last.setText("");
                IDs.setText("");

                try {
                    String blank = "";
                    statementName.executeUpdate("INSERT INTO student (first_name, last_name, sections) VALUES ('" + temp.getFirst() + "', '" + temp.getLastName() + "', '" + blank+ "');");
                    //do first name last, and sections
                    ResultSet update = statementName.executeQuery("SELECT id FROM student WHERE first_name = '" + temp.getFirst() + "' AND last_name = '" + temp.getLastName() + "';");
                    int maxID = -1;
                    while(update!=null&&update.next()) {
                        maxID = Math.max(maxID, update.getInt("id"));
                    }
                    temp.setID(maxID + "");
                    myContacts.setListData(storage.toArray(new Data[0]));
                } catch (SQLException ex) {
                    System.out.println("Exception is save method");
                    ex.printStackTrace();
                }
            }
        });

        clear.setBounds(390, 280, 100, 20);
        add(clear);

        scrolling = new JScrollPane(myContacts);
        scrolling.setBounds(50, 50, 180, 350);
        add(scrolling);




    }
    public ArrayList<Data> sort (ArrayList < Data > a)
    {
        ArrayList<String> temp = new ArrayList<>();
        ArrayList<Data> output = new ArrayList<>();
        for (int i = 0; i < a.size(); i++) {
            temp.add(a.get(i).toString());
        }
        Collections.sort(temp, String.CASE_INSENSITIVE_ORDER);
        for (int i = 0; i < temp.size(); i++) {
            // place the data in the now sorted order
            String name = temp.get(i);
            for (int in = 0; in < a.size(); in++) {
                if (a.get(in).toString().compareTo(name) == 0) {
                    output.add(a.get(in));
                }
            }
        }
        return output;
    }

}
