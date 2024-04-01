import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.*;

public class frame extends JFrame implements WindowListener{
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

    static Panel teacherPanel;
    static Panel studentPanel;
    static Panel sectionPanel;
    static Panel coursePanel;

    public frame(){
        super("School Manager");
        setSize(600,700);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        bar = new JMenuBar();

        file = new JMenu("File");
        importData = new JMenuItem("Import Data");
        exportData = new JMenuItem("Export Data");
        purge = new JMenuItem("Purge");
        exit = new JMenuItem("Exit");

        view = new JMenu("View");
        teacher = new JMenuItem("Teacher");
        student = new JMenuItem("student");
        course = new JMenuItem("Course");
        section = new JMenuItem("Section");

        help = new JMenu("Help");
        about = new JMenuItem("About");

        bar.add(file);
        bar.add(view);
        bar.add(help);

        setLayout(null);
        setJMenuBar(bar);



    }
    public static void main(String[] args) {


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
