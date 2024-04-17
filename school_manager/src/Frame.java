import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.awt.event.*;
import java.awt.Panel;

/*
to-do:

- when clicking back and forth between views, data does not save from previous inserts somehow
- figure out where to put enrollment table stuff
 */
public class Frame extends JFrame implements WindowListener{
    static JMenuBar bar;
    static JMenu file;
    static JMenuItem exportData;
    static JMenuItem importData;
    static JMenuItem purge;
    static JMenuItem exit;
    static JMenu view;
    static JMenuItem teacher;
    static JMenuItem course;
    static JMenuItem section;
    static JMenuItem student;
    static JMenu help;
    static JMenuItem about;

    Statement sn;

    static JPanel teacherPanel;
    static JPanel studentPanel;
    static JPanel sectionPanel;
    static JPanel coursePanel;

    public Frame() throws SQLException, ClassNotFoundException{
        super("School Manager");
       //    Class.forName("com.mysql.jbdc.Driver");

        Class.forName("com.mysql.cj.jdbc.Driver");

        //might need to create the database
        Connection con =
                DriverManager.getConnection("jdbc:mysql://localhost:3306/school_manager","root","password");
        sn = con.createStatement();
        sn.execute("USE school_manager");

        sn.execute("CREATE TABLE IF NOT EXISTS teacher(" +
                "teacher_id INTEGER NOT NULL AUTO_INCREMENT,"+
                "first_name TEXT NOT NULL," +
                "last_name TEXT NOT NULL," +
                "PRIMARY KEY(teacher_id)"+
                ");");

        sn.execute("CREATE TABLE IF NOT EXISTS student(" +
                "student_id INTEGER NOT NULL AUTO_INCREMENT,"+
                "first_name TEXT NOT NULL," +
                "last_name TEXT NOT NULL," +
                "PRIMARY KEY(student_id)"+
                ");");

        sn.execute("CREATE TABLE IF NOT EXISTS section(" +
                "section_id INTEGER NOT NULL AUTO_INCREMENT,"+
                "course_id INTEGER NOT NULL," +
                "teacher_id INTEGER NOT NULL," +
                "PRIMARY KEY(section_id)"+
                ");");

        sn.execute("CREATE TABLE IF NOT EXISTS course(" +
                "course_id INTEGER NOT NULL AUTO_INCREMENT,"+
                "title TEXT NOT NULL," +
                "type INTEGER NOT NULL," +
                "PRIMARY KEY(course_id)"+
                ");");

        sn.execute("CREATE TABLE IF NOT EXISTS enrollment(" +
               "section_id INTEGER NOT NULL," +
                "student_id INTEGER NOT NULL" +
                ");");

      //  sn.executeUpdate("DELETE FROM teacher WHERE teacher_id =" + "-1" + ";");
        //  sn.execute("INSERT INTO teacher(teacher_id, first_name, last_name) VALUES (-1,\"Teacher\", \"No\");");

        setSize(1000,1000);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);


        bar = new JMenuBar();


        file = new JMenu("File");
        importData = new JMenuItem("Import");//add action listener later
        // 1) get rid of existing tables
        // 2) Create new tables from data (JFileChooser)
        // 2a) data comma separated

        exportData = new JMenuItem("Export");
        // 1) printwriter
        // 2) separate diff view data w/ headers

        purge = new JMenuItem("Purge");
        purge.addActionListener(e -> {
            try {
                sn.execute("DROP TABLE IF EXISTS teacher");
                sn.execute("DROP TABLE IF EXISTS student");
                sn.execute("DROP TABLE IF EXISTS course");
                sn.execute("DROP TABLE IF EXISTS section");
            }
            catch (Exception o){
                o.printStackTrace();
            }
            System.exit(0);
        });
        // delete tables & exit

        exit = new JMenuItem("Exit");
        exit.addActionListener (e-> {
            System.out.println("Bye bye");
            System.exit(0);
        });

        file.add(importData);
        file.add(exportData);
        file.add(purge);
        file.add(exit);


        view = new JMenu("View");
        //remove existing panel before opening/making selected panel

        teacher = new JMenuItem("Teacher");
        student = new JMenuItem("Student");
        course = new JMenuItem("Course");
        section = new JMenuItem("Section");

        teacher.addActionListener(e->
        {
            if(studentPanel!=null) {
                remove(studentPanel);
                studentPanel=null;
                repaint();
            }
            if(sectionPanel!=null) {
                remove(sectionPanel);
                sectionPanel=null;
                repaint();
            }
            if(coursePanel!=null) {
                remove(coursePanel);
                coursePanel=null;
                repaint();
            }
            System.out.println("added teacher");

            try {
                teacherPanel=new TeacherPanel(700,700, sn);
            } catch (SQLException ex) {
                System.out.println("Exception creating teacher");
                ex.printStackTrace();
            }
            teacherPanel.setLocation(50,50);
            add(teacherPanel);
            System.out.println("creating teacher");
            repaint();
        });
        course.addActionListener(e->
        {
            if(studentPanel!=null) {
                remove(studentPanel);
                studentPanel=null;
                repaint();
            }
            if(teacherPanel!=null) {
                remove(teacherPanel);
                teacherPanel=null;
                repaint();
            }
            if(sectionPanel!=null) {
                remove(sectionPanel);
                sectionPanel=null;
                repaint();
            }
            try {
                coursePanel =new coursePanel(700,700, sn);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            coursePanel.setLocation(50,50);
            add(coursePanel);
            repaint();
        });
        section.addActionListener(e->
        {
            if(teacherPanel!=null) {
                remove(teacherPanel);
                teacherPanel=null;
                repaint();
            }
            if(studentPanel!=null) {
                remove(studentPanel);
                studentPanel=null;
                repaint();
            }
            if(coursePanel!=null) {
                remove(coursePanel);
                coursePanel=null;
                repaint();
            }
            System.out.println("student clicked");
            try {
                sectionPanel = new sectionsPanel(700,700,sn);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            sectionPanel.setLocation(50,50);
            add(sectionPanel);
            System.out.println("creating section");
            repaint();
        });

        student.addActionListener(e->
        {
            if(teacherPanel!=null) {
                remove(teacherPanel);
                teacherPanel=null;
                repaint();
            }
            if(sectionPanel!=null) {
                remove(sectionPanel);
                sectionPanel=null;
                repaint();
            }
            if(coursePanel!=null) {
                remove(coursePanel);
                coursePanel=null;
                repaint();
            }
            try {
                studentPanel = new StudentPanel(700,700, sn);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            studentPanel.setLocation(50,50);
            add(studentPanel);

            repaint();
            if(studentPanel!=null) {
                ResultSet rs = null;
                try {
                    rs = sn.executeQuery("SELECT student_id, first_name, last_name FROM student;");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        view.add(teacher);
        view.add(student);
        view.add(course);
        view.add(section);

        help = new JMenu("Help");
        about = new JMenuItem("About");
        about.addActionListener (e -> {
            JOptionPane.showMessageDialog(null, "Crystal & Kailin r the best! \n Version 1", "About", JOptionPane.INFORMATION_MESSAGE);
        });

        help.add(about);

        bar.add(file);
        bar.add(view);
        bar.add(help);

        setJMenuBar(bar);
        setVisible(true);
    }
    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

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
