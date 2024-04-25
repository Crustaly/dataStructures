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

/*
to-do:

- figure out why all student ids are -1

 */
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
    JScrollPane scheduleScrolling;

    JLabel schedule = new JLabel("Schedule");

    JTable scheduleTable;

    public StudentPanel(int width, int height, Statement sn, Frame f) throws SQLException {
        setSize(width, height);
        setLayout(null);
        //setBounds(0,0,w,h);
        sections = new ArrayList<>();
        jSections = new JList<>();

        schedule.setBounds(50,420,100,10);
        add(schedule);

        String[] tableCols = {"Section ID","Course","Teacher"};
        String ss[][] = new String[sections.size()][3];

        scheduleTable = new JTable(ss, tableCols);
        scheduleTable.setDefaultEditor(Object.class, null);
        scheduleScrolling = new JScrollPane(scheduleTable);
        scheduleScrolling.setBounds(50, 450, 450, 350);
        add(scheduleScrolling);
        System.out.println("POOP");
        try
        {

            ResultSet rs = sn.executeQuery("SELECT student_id, first_name, last_name FROM student;");


            while(rs!=null&&rs.next())
            {

                Data temp = new Data(rs.getString("first_name"), rs.getString("last_name"),  rs.getInt("student_id") + "");
                System.out.println(temp);

                storage.add(temp);
            }
            Collections.sort(storage);
            myContacts.setListData(storage.toArray(new Data[0]));
            System.out.println("REPAINT");

            repaint();
            f.repaint();

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
                sn.executeUpdate("UPDATE student SET first_name='" + firstName +"' WHERE student_id=" + ID + ";");
                sn.executeUpdate("UPDATE student SET last_name='" + lastName   +"' WHERE student_id=" +  ID + ";");

//               update student and first nae and id
            }
            catch(Exception a)
            {
                a.printStackTrace();
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
                sn.executeUpdate("DELETE FROM student WHERE student_id =" + ids + ";");
            } catch (SQLException ex) {
                ex.printStackTrace();
                System.out.println("Not good in delete contact");
            }
        });

        save.setBounds(280, 280, 100, 20);
        add(save);

        save.addActionListener(e ->
        {

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
                    /*
                    ResultSet update = sn.executeQuery("SELECT student_id FROM student WHERE first_name = '" + temp.getFirst() + "' AND last_name = '" + temp.getLast() + "';");
                    int maxID = -1;
                    while(update!=null&&update.next()) {
                        maxID = Math.max(maxID, update.getInt("student_id"));
                    }
                    temp.setID(maxID + "");
                    myContacts.setListData(storage.toArray(new Data[0]));
*/
                    String blank = "";
                    sn.executeUpdate("INSERT INTO student (first_name, last_name) VALUES ('" + temp.getFirst() + "', '" + temp.getLast() +  "');");
                    ResultSet update = sn.executeQuery("SELECT student_id FROM student WHERE first_name = '" + temp.getFirst() + "' AND last_name = '" + temp.getLast() + "';");
                    int maxID = -1;
                    while(update!=null&&update.next()) {
                        maxID = Math.max(maxID, update.getInt("student_id"));
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

                    int tempID = myContacts.getSelectedValue()==null? 0:
                            Integer.parseInt(myContacts.getSelectedValue().getID());

                    sections.clear();
                    ArrayList<String[]> mod = new ArrayList<>();

                    try {
                        ResultSet rs = sn.executeQuery("SELECT section_id FROM enrollment WHERE student_id=" + myContacts.getSelectedValue().getID());
                        ArrayList<String> splitSections = new ArrayList<>();
                        while (rs != null && rs.next()) {
                            splitSections.add(rs.getString("section_id"));
                        }

                        for (String str : splitSections) {
                            if (str.length() < 1) {
                                continue;
                            }
                            if (str.equals("test")) {
                                continue;
                            }
                            ResultSet rs2 = sn.executeQuery("SELECT * FROM section WHERE section_id=" + Integer.parseInt(str));
                            while (rs2 != null && rs2.next()) {
                                sectionData curSection = new sectionData(rs2.getInt("section_id"), rs2.getInt("course_id"), rs2.getInt("teacher_id"), sn);
                                sections.add(curSection);
                            }
                        }

                        //show schedule table
                        Collections.sort(sections);
                        //add sections to tableModel
                        for(sectionData sd: sections) {
                            int tempCourseID = sd.getCourseId();
                            String tempSectionID = sd.getId() + "";
                            int tempTeacherID = sd.getTeacherId();
                            ResultSet rs3 = sn.executeQuery("SELECT course.title, teacher.last_name FROM course, teacher WHERE course.course_id=" + tempCourseID + " AND teacher.teacher_id = " + tempTeacherID+ ";");
                            while(rs3 != null && rs3.next()){

                                String course = rs3.getString("title");
                                String teachLast = rs3.getString("last_name");
                                System.out.println(course + " " + teachLast);
                                String[] sss = new String[]{tempSectionID, course, teachLast};
                                mod.add(sss);
                            }
                        }
                        remove(scheduleScrolling);

                        if(mod.size() > 0 ){
                            Collections.sort(sections);
                            scheduleTable = new JTable(alToAr(mod), tableCols);
                        }
                        else {
                            scheduleTable = new JTable(ss, tableCols);
                        }
                        scheduleScrolling = new JScrollPane(scheduleTable);
                        scheduleScrolling.setBounds(50, 450, 450, 350);
                        scheduleTable.setDefaultEditor(Object.class, null);
                        add(scheduleScrolling);

                    }
                    catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        repaint();
        clear.setBounds(390, 280, 100, 20);
        add(clear);

        scrolling = new JScrollPane(myContacts);
        scrolling.setBounds(50, 50, 180, 350);
        add(scrolling);
        setVisible(true);
        repaint();
    }

    public String[][] alToAr(ArrayList<String[]> al){
        if(al.size() > 0) {
            String[][] ar = new String[al.size()][al.get(0).length];
            for(int i = 0; i<al.size(); i++){
                ar[i] = al.get(i);
            }
            return ar;
        }
        return null;
    }
}
