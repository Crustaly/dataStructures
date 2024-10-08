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

/*
to-do:
- also display type on MyContacts list
 */

public class coursePanel extends JPanel{
    JLabel ID = new JLabel("ID:");
    JLabel courseName = new JLabel("Course Name:");
    JRadioButton academic = new JRadioButton("Academic");
    JRadioButton kap = new JRadioButton("KAP");
    JRadioButton ap = new JRadioButton("AP");
    ButtonGroup bg = new ButtonGroup();

/*
Allows you to add / remove coursesData and edit their data.
Display
ID (Generated by SQL, not Editable)
Course Name (Editable)
Type (Radio Buttons Academic / AP / KAP), (Editable)
 */

    JTextField ids = new JTextField();
    JTextField course = new JTextField();

    JButton save = new JButton("Save");
    JButton clear = new JButton("Clear");
    JButton saveChanges = new JButton("Save changes");
    JButton deleteContact = new JButton("Delete contact");

    ArrayList<coursesData> storage = new ArrayList<>();
    JList<coursesData> myContacts = new JList<>();

    JScrollPane scrolling = new JScrollPane();
    public coursePanel(int width, int height, Statement sn) throws SQLException{
        setSize(width, height);
        setLayout(null);
        ID.setBounds(280,100,100,50);
        add(ID);
        ids.setText("");
        ids.setBounds(390,120,100,20);
        ids.setEditable(false);
        add(ids);

        setVisible(true);

        courseName.setBounds(280, 150, 100, 20);
        add(courseName);
        course.setText("");
        course.setBounds(390, 150, 300, 20);
        add(course);

        bg.add(academic);
        bg.add(kap);
        bg.add(ap);
        academic.setBounds(280,250,100,20);
        kap.setBounds(280,290,100,20);
        ap.setBounds(280,330,100,20);
        academic.setSelected(false);
        kap.setSelected(false);
        ap.setSelected(false);
        add(academic);
        add(kap);
        add(ap);
        repaint();

        try{
            ResultSet rs = sn.executeQuery("SELECT * FROM course;");
            while(rs!=null&&rs.next())
            {
                coursesData temp = new coursesData(rs.getInt("course_id") +"",rs.getString("title"), rs.getInt("type"));
                if(rs.getInt("course_id") != -1)
                    storage.add(temp);
            }
        }
        catch(Exception ee){
            ee.printStackTrace();
        }

        Collections.sort(storage);
        myContacts.setListData(storage.toArray(new coursesData[0]));
        myContacts.repaint();

        save.setBounds(280,200,100,20);
        add(save);
        save.addActionListener(e ->
        {
            System.out.println("not full "+courseName.getText());
            if(courseName.getText().equals("")||(academic.isSelected()==false&&kap.isSelected()==false&&ap.isSelected()==false))
            {
                System.out.println("not full "+courseName.getText());
                JOptionPane.showMessageDialog(null, "Not valid", "Message", JOptionPane.INFORMATION_MESSAGE);

            }
            else
            {
                int type = -1;//0 aca, 1 kap, 2 ap
                if(academic.isSelected())
                {
                    type = 0;
                }
                if(kap.isSelected())
                {
                    type = 1;
                }
                if(ap.isSelected())
                {
                    type = 2;
                }
                academic.setSelected(false);
                kap.setSelected(false);
                ap.setSelected(false);
                coursesData temp = new coursesData(ids.getText(), course.getText(), type);
                storage.add(temp);
                Collections.sort(storage);
                myContacts.setListData(storage.toArray(new coursesData[0]));
                course.setText("");

                try
                {
                    sn.executeUpdate("INSERT INTO course (title, type) VALUES ('" + temp.getCourseName() + "', " + temp.getType() + ");");
                    ResultSet results = sn.executeQuery("SELECT course_id FROM course");
                    int maxID = -1;
                    while(results!=null&&results.next()) {
                        maxID = Math.max(maxID, results.getInt("course_id"));
                    }
                    temp.setID(maxID + "");
                    //myContacts.setListData(storage.toArray(new coursesData[0]));
                    System.out.println("maxID: " + maxID);
                }
                catch(Exception b)
                {
                    b.printStackTrace();
                }
            }
            academic.setSelected(false);
            kap.setSelected(false);
            ap.setSelected(false);
        });


        save.setVisible(true);
        clear.setVisible(true);

        saveChanges.setBounds(280, 200, 100, 20);
        saveChanges.setText("Save Changes");
        add(saveChanges);
        setVisible(true);

        deleteContact.setBounds(390, 200, 100, 20);
        add(deleteContact);

        saveChanges.addActionListener(e ->
        {

            coursesData s = myContacts.getSelectedValue();
            int index = storage.indexOf(s);
            int type = -1;
            if(academic.isSelected())
            {
                type = 0;
                academic.setSelected(false);
            }
            if(kap.isSelected())
            {
                type = 1;
                kap.setSelected(false);
            }
            if(ap.isSelected())
            {
                type = 2;
                ap.setSelected(false);
            }
            coursesData temporary = new coursesData(ids.getText(), course.getText(), type);
            storage.set(index, temporary);
            course.setText("");
            Collections.sort(storage);
            myContacts.setListData(storage.toArray(new coursesData[0]));


            try
            {
                sn.executeUpdate("UPDATE course SET title='" + temporary.getCourseName() + "' WHERE course_id = " + temporary.getID() + ";");
                sn.executeUpdate("UPDATE course SET type = " + temporary.getType() + " WHERE course_id = " + temporary.getID() + ";");
                //save course
            }
            catch(Exception a)
            {
                a.printStackTrace();
            }
            saveChanges.setVisible(false);
            deleteContact.setVisible(false);

            save.setVisible(true);
            clear.setVisible(true);
            ids.setText(" ");
            ap.setSelected(false);
            System.out.print(ap.isSelected());
            kap.setSelected(false);
            System.out.print(kap.isSelected());
            academic.setSelected(false);
            System.out.print(academic.isSelected());
            repaint();
            academic.setSelected(false);
            kap.setSelected(false);
            ap.setSelected(false);
        });

        clear.setBounds(390,200,100,20);
        add(clear);
        clear.addActionListener(e ->
        {
            academic.setSelected(false);
            kap.setSelected(false);
            ap.setSelected(false);
            course.setText("");
        });
        Collections.sort(storage);
        myContacts.setListData(storage.toArray(new coursesData[0]));

        myContacts.addListSelectionListener(e ->
        {
            Collections.sort(storage);
            academic.setSelected(false);
            kap.setSelected(false);
            ap.setSelected(false);

            if (storage.isEmpty() == false) {
                save.setVisible(false);
                clear.setVisible(false);

                saveChanges.setVisible(true);
                deleteContact.setVisible(true);
                if (myContacts.getSelectedValue() != null) {
                    ids.setText(myContacts.getSelectedValue().getID());
                    course.setText(myContacts.getSelectedValue().getCourseName());
                    if(myContacts.getSelectedValue().getType()==0)
                    {

                        academic.setSelected(true);
                    }
                    if(myContacts.getSelectedValue().getType()==1)
                    {

                        kap.setSelected(true);
                    }
                    if(myContacts.getSelectedValue().getType()==2)
                    {

                        ap.setSelected(true);
                    }

                }
            }
        });


        deleteContact.addActionListener(e ->
        {
            //for each section that has the course
            //remove all students
            //remove section
            ArrayList<Integer> sectionIDs = new ArrayList<>();
            try {
                ResultSet rs = sn.executeQuery("SELECT * from section where course_id="+ids.getText()+";");
                while(rs!=null&&rs.next()) {
                    sectionIDs.add(rs.getInt("section_id"));
                }
            } catch (SQLException ex) {
                System.out.println("Exception creating section_id list");
            }
            //remove sections from students thru enrollment
            for(int id : sectionIDs) {
                try{
                    sn.executeUpdate("DELETE FROM enrollment where section_id=" +id+";");
                }
                catch(Exception ee){
                    ee.printStackTrace();
                }
            }

            try {
                sn.executeUpdate("DELETE from section where course_id="+ids.getText()+";");
            } catch (SQLException ex) {
                System.out.println("Exception deleting section");
            }

            //removes course
            ids.setText("");
            coursesData s = myContacts.getSelectedValue();
            academic.setSelected(false);
            kap.setSelected(false);
            ap.setSelected(false);
            storage.remove(s);
            course.setText("");
            Collections.sort(storage);
            myContacts.setListData(storage.toArray(new coursesData[0]));
            saveChanges.setVisible(false);
            deleteContact.setVisible(false);
            save.setVisible(true);
            clear.setVisible(true);
            ap.setSelected(false);
            kap.setSelected(false);
            academic.setSelected(false);

            try {
                sn.executeUpdate("DELETE FROM course WHERE course_id =" + s.getID() + ";");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            repaint();
        });

        scrolling = new JScrollPane(myContacts);
        scrolling.setBounds(3, 3, 250, 350);
        add(scrolling);
        repaint();
    }
}
