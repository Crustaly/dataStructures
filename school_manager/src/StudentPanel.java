import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
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

    JTable scheduleTable;

    public StudentPanel(int width, int height, Statement sn) throws SQLException {
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
            ResultSet rs = sn.executeQuery("SELECT id, first_name, last_name FROM student;");
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

        firstName.setBounds(280, 100, 100, 50);
        add(firstName);

        first.setText("");
        first.setBounds(390,120,100,20);
        add(first);

        setVisible(true);

        lastName.setBounds(280, 150, 100, 20);
        add(lastName);
        last.setText("");
        last.setBounds(390, 150, 100, 20);
        add(last);

        ID.setBounds(280, 200, 100, 20);
        add(ID);
        IDs.setText("");
        IDs.setBounds(390, 200, 100, 20);
        IDs.setEditable(false);
        add(IDs);

        saveChanges.setBounds(280, 310, 100, 20);
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

            Collections.sort(storage);
            System.out.println(Arrays.toString(storage.toArray(new Data[0])));
            myContacts.setListData(storage.toArray(new Data[0]));

            saveChanges.setVisible(false);
            deleteContact.setVisible(false);

            save.setVisible(true);
            clear.setVisible(true);

            try
            {
                //hello i am crysatl yang please do the sql stuff and set up
                System.out.println(first.getText());
                String firstName = temporary.getFirst();
                String lastName = temporary.getLast();
                int ID = Integer.parseInt(temporary.getID());
                sn.executeUpdate("UPDATE student SET first_name='" + firstName +"' WHERE id=" + ID + ";");
                sn.executeUpdate("UPDATE student SET last_name='" + lastName   +"' WHERE id=" +  ID + ";");

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
            Collections.sort(storage);
            myContacts.setListData(storage.toArray(new Data[0]));
            saveChanges.setVisible(false);
            deleteContact.setVisible(false);
            save.setVisible(true);
            clear.setVisible(true);
            System.out.println("Got here");

            try {
                sn.executeUpdate("DELETE FROM student WHERE id =" + ids + ";");
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
                JOptionPane.showMessageDialog(null, "Not valid", "Message", JOptionPane.INFORMATION_MESSAGE);
            } else {
                Data temp = new Data(first.getText(), last.getText(), IDs.getText());
                storage.add(temp);
                Collections.sort(storage);
                myContacts.setListData(storage.toArray(new Data[0]));
                first.setText("");
                last.setText("");
                IDs.setText("");

                try {
                    String blank = "";
                    sn.executeUpdate("INSERT INTO student (first_name, last_name, sections) VALUES ('" + temp.getFirst() + "', '" + temp.getLast() + "', '" + blank+ "');");
                    //do first name last, and sections
                    ResultSet update = sn.executeQuery("SELECT id FROM student WHERE first_name = '" + temp.getFirst() + "' AND last_name = '" + temp.getLast() + "';");
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

        Collections.sort(storage);
        myContacts.setListData(storage.toArray(new Data[0]));
        myContacts.addListSelectionListener(e ->{
            //click on student to show info

            if (!storage.isEmpty()) {
                save.setVisible(false);
                clear.setVisible(false);

                saveChanges.setVisible(true);
                deleteContact.setVisible(true);

                if (myContacts.getSelectedValue() != null) {
                    if (myContacts.getSelectedValue().getFirst() != "")
                        first.setText(myContacts.getSelectedValue().getFirst());
                    if (myContacts.getSelectedValue().getFirst() != "")
                        last.setText(myContacts.getSelectedValue().getLast());
                    if (myContacts.getSelectedValue().getFirst() != "")
                        IDs.setText(myContacts.getSelectedValue().getID());

                    sections.clear();
                    DefaultTableModel mod = new DefaultTableModel();

                    int tempID = myContacts.getSelectedValue()==null? 0:
                            Integer.parseInt(myContacts.getSelectedValue().getID());

                    try {
                        ResultSet rs = sn.executeQuery("SELECT sections FROM student WHERE id=" + myContacts.getSelectedValue().getID());
                        ArrayList<String> splitSections = new ArrayList<>();
                        while (rs != null && rs.next()) {
                            splitSections.add(rs.getString("sections"));
                        }

                        for (String str : splitSections) {
                            if (str.length() < 1) {
                                continue;
                            }
                            if (str.equals("test")) {
                                continue;
                            }
                            System.out.println("|" + str + "|");
                            ResultSet rs2 = sn.executeQuery("SELECT * FROM section WHERE id=" + Integer.parseInt(str));
                            while (rs2 != null && rs2.next()) {
                                System.out.println("inside loop");
                                sectionData curSection = new sectionData(rs2.getInt("id"), rs2.getInt("course_id"), rs2.getInt("teacher_id"), statementName);
                                sections.add(curSection);
                            }
                        }


                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                }




            }
        });

        clear.setBounds(390, 280, 100, 20);
        add(clear);

        scrolling = new JScrollPane(myContacts);
        scrolling.setBounds(50, 50, 180, 350);
        add(scrolling);

    }


}
