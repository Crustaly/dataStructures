import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.sql.SQLException;


public class Frame extends JFrame implements WindowListener{
    static JMenuBar bar;
    static JMenu file;
    static JMenuItem exportData;//
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

    static Panel teacherPanel;
    static Panel studentPanel;
    static Panel sectionPanel;
    static Panel coursePanel;

    public Frame() throws SQLException, ClassNotFoundException{
        super("School Manager");
        Class.forName("com.mysql.jbdc.Driver");
        Connection con = DriverManager.getConnection("jbdc:mysql://localhost:3306/school_manager", "root", "password");
        sn = con.createStatement();
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

        view.add(teacher);
        view.add(student);
        view.add(course);
        view.add(section);


        help = new JMenu("Help");
        about = new JMenuItem("About");
        about.addActionListener (e -> {
            JOptionPane.showMessageDialog(null, "Crystal & Kailin r the best!", "About", JOptionPane.INFORMATION_MESSAGE);
        });

        help.add(about);


        bar.add(file);
        bar.add(view);
        bar.add(help);

        setJMenuBar(bar);
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
