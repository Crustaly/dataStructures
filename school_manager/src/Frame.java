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
        //done with this part!
        importData.addActionListener(e ->
        {
            System.out.println("export test");
            try {
                sn.execute("DROP TABLE IF EXISTS student");
                sn.execute("DROP TABLE IF EXISTS teacher");
                sn.execute("DROP TABLE IF EXISTS course");
                sn.execute("DROP TABLE IF EXISTS section");
                sn.execute("CREATE TABLE IF NOT EXISTS student(" +
                        "id INTEGER NOT NULL AUTO_INCREMENT,"+
                        "first_name TEXT NOT NULL," +
                        "last_name TEXT NOT NULL," +
                        "sections TEXT NOT NULL," +
                        "PRIMARY KEY(id)"+
                        ");");
                sn.execute("CREATE TABLE IF NOT EXISTS teacher(" +
                        "id INTEGER NOT NULL AUTO_INCREMENT,"+
                        "first_name TEXT NOT NULL," +
                        "last_name TEXT NOT NULL," +
                        "PRIMARY KEY(id)"+
                        ");");
                sn.execute("CREATE TABLE IF NOT EXISTS section(" +
                        "id INTEGER NOT NULL AUTO_INCREMENT,"+
                        "course_id INTEGER NOT NULL," +
                        "teacher_id INTEGER NOT NULL," +
                        "PRIMARY KEY(id)"+
                        ");");
                sn.execute("CREATE TABLE IF NOT EXISTS course(" +
                        "id INTEGER NOT NULL AUTO_INCREMENT,"+
                        "title TEXT NOT NULL," +
                        "type INTEGER NOT NULL," +
                        "PRIMARY KEY(id)"+
                        ");");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            JFileChooser fileChooser = new JFileChooser();
            int response = fileChooser.showOpenDialog(null);
            if(response==JFileChooser.APPROVE_OPTION) {
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());





                try {
                    Scanner sc = new Scanner(file);
                    sc.nextLine();
                    while(sc.hasNextLine()) {
                        String str = sc.nextLine();
                        if(str.contains("Teacher")||str.contains("teacher")) {
                            break;
                        }
                        String[] ar = str.split(", ");
                        int id = Integer.parseInt(ar[0]);
                        String firstName = ar[1];
                        String lastName = ar[2];
                        String sections = ar[3];
                        sn.executeUpdate(" insert into student(id, first_name, last_name, sections) values ("+id+",\'"+firstName+"\', \'"+lastName+"\', \'"+sections+"\');");
                    }



                    while(sc.hasNextLine()) {
                        String str = sc.nextLine();
                        if(str.contains("Courses")||str.contains("courses")) {
                            break;
                        }
                        String[] ar = str.split(", ");
                        int id = Integer.parseInt(ar[0]);
                        String firstName = ar[1];
                        String lastName = ar[2];
                        sn.executeUpdate(" insert into teacher(id, first_name, last_name) values ("+id+",\'"+firstName+"\', \'"+lastName+"\');");

                        }
                    //  } catch (FileNotFoundException | SQLException ex) {
                    //     System.out.println("Couldn't load teacher file");
                    //  }

                    // try {
                    //Scanner sc = new Scanner(file);
                    while(sc.hasNextLine()) {
                        String str = sc.nextLine();
                        if(str.contains("Section")||str.contains("section")) {
                            break;
                        }
                        String[] ar = str.split(", ");
                        int id = Integer.parseInt(ar[0]);
                        sn.executeUpdate(" insert into course(id, title, type) values ("+id+",\'"+ar[1]+"\', \'"+ar[2]+"\');");
                    }

                    while(sc.hasNextLine()) {
                        String str = sc.nextLine();
                        String[] ar = str.split(", ");
                        int id = Integer.parseInt(ar[0]);
                        int course_id = Integer.parseInt(ar[1]);
                        int teacher_id = Integer.parseInt(ar[2]);
                        sn.executeUpdate(" insert into section(id, course_id, teacher_id) values ("+id+", "+course_id+", "+teacher_id+");");
                    }
                } catch (FileNotFoundException | SQLException ex) {
                    System.out.println("Couldn't load sectoin file");
                }




            }



        });

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
