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
        course.setBounds(390, 150, 100, 20);
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

        save.setBounds(280,350,100,20);
        add(save);
        save.addActionListener(e ->
        {
            if(courseName.getText()==""||(academic.isSelected()==false&&kap.isSelected()==false&&ap.isSelected()==false))
            {
                System.out.println("not full");
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
                myContacts.setListData(storage.toArray(new coursesData[0]));
                course.setText("");

                try
                {
                    sn.executeUpdate("INSERT INTO course (title, type) VALUES ('" + temp.getCourseName() + "', " + temp.getType() + ");");
                    ResultSet results = sn.executeQuery("SELECT id FROM course");
                    int maxID = -1;
                    while(results!=null&&results.next()) {
                        maxID = Math.max(maxID, results.getInt("id"));
                    }
                    temp.setID(maxID + "");
                    //myContacts.setListData(storage.toArray(new courses[0]));
                    System.out.println("maxID: " + maxID);
                }
                catch(Exception b)
                {
                    b.printStackTrace();
                }
            }
        });

        save.setVisible(true);
        clear.setVisible(true);

        saveChanges.setBounds(280, 380, 100, 20);
        saveChanges.setText("Save Changes");
        saveChanges.setVisible(true);
        add(saveChanges);
        setVisible(true);

        deleteContact.setBounds(390, 380, 100, 20);
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

            myContacts.setListData(storage.toArray(new coursesData[0]));

            try
            {
                sn.executeUpdate("UPDATE course SET title='" + temporary.getCourseName() + "' WHERE id = " + temporary.getID() + ";");
                sn.executeUpdate("UPDATE course SET type = " + temporary.getType() + " WHERE id = " + temporary.getID() + ";");
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
        });

        deleteContact.addActionListener(e ->
        {
            //for each section that has the course
            //remove all students
            //remove section
            ArrayList<Integer> sectionIDs = new ArrayList<>();
            try {
                ResultSet rs = sn.executeQuery("SELECT * from section where course_id="+Integer.parseInt(ids.getText())+";");
                while(rs!=null&&rs.next()) {
                    sectionIDs.add(rs.getInt("id"));
                }
            } catch (SQLException ex) {
                System.out.println("Exception creating sectionid list");
            }
            for(int id : sectionIDs) {
                ArrayList<Integer> studentIDS = new ArrayList<>();
                ArrayList<String> studentSections = new ArrayList<>();

                try {
                    ResultSet rs = sn.executeQuery("SELECT * from student");

                    while (rs != null && rs.next()) {
                        studentIDS.add(rs.getInt("id"));
                        String[] ar = rs.getString("sections").split(" ");
                        String newStr = "";
                        for (String str : ar) {
                            if (!str.equals("" + id)) {
                                newStr += str + " ";
                            }
                        }
                        studentSections.add(newStr);
                    }
                } catch (SQLException ex) {
                    System.out.println("Exception creating new schedule");
                }

                try {
                    for (int i = 0; i < studentIDS.size(); i++) {
                        sn.executeUpdate("UPDATE student set sections=\'" + studentSections.get(i) + "\' where ID=" + studentIDS.get(i) + ";");
                    }
                } catch (SQLException ex) {
                    System.out.println("Exception changing student sections");
                }


            }

            try {
                sn.executeUpdate("DELETE from section where course_id="+Integer.parseInt(ids.getText())+";");
            } catch (SQLException ex) {
                System.out.println("Exception deleting section");
            }

            //removes course
            ids.setText("");
            System.out.println("Tried to delete");
            coursesData s = myContacts.getSelectedValue();
            academic.setSelected(false);
            kap.setSelected(false);
            ap.setSelected(false);
            storage.remove(s);
            course.setText("");
            myContacts.setListData(storage.toArray(new coursesData[0]));
            saveChanges.setVisible(false);
            deleteContact.setVisible(false);
            save.setVisible(true);
            clear.setVisible(true);
            System.out.println("Got here");
            ap.setSelected(false);
            kap.setSelected(false);
            academic.setSelected(false);

            try {
                sn.executeUpdate("DELETE FROM course WHERE id =" + s.getID() + ";");
            } catch (SQLException ex) {
                ex.printStackTrace();
                System.out.println("Not good in delete course");
            }
            repaint();
        });

        scrolling = new JScrollPane(myContacts);
        scrolling.setBounds(50, 50, 180, 350);
        add(scrolling);
        repaint();
    }
}
