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
Allows you to add / remove courses and edit their data.
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

}
