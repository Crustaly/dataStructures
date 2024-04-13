import javax.swing.*;
import javax.xml.transform.Result;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.*;
import java.util.Arrays;
import java.util.Collections;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class sectionsPanel extends JPanel{
    ArrayList<coursesData> allCourses = new ArrayList<>();
    JList<coursesData> coursesList = new JList<>();
    JScrollPane scroll = new JScrollPane();
    JButton save = new JButton("Save");
    JButton clear = new JButton("Clear");
    JButton saveSectionChanges = new JButton("Save Changes");
    JButton deleteSection = new JButton("Delete Section");
    JButton saveStudent = new JButton("Save Student");
    JButton deleteStudent = new JButton("Delete Student");
    JButton saveChanges = new JButton("Save changes");
    JButton deleteContact = new JButton("Delete contact");
    ArrayList<sectionData> allSections = new ArrayList<>();
    JList<sectionData> mySections = new JList<>();
    JScrollPane scroll2 = new JScrollPane();
    ArrayList<Data> allMyStudents = new ArrayList<>();
    JList<Data> myStudents = new JList<>();
    JScrollPane scrollingThree = new JScrollPane();

    JLabel ID = new JLabel("ID");
    JTextField IDText = new JTextField("");

    JLabel course = new JLabel("Course ID:");
    JTextField courseText = new JTextField();

    JLabel teacher = new JLabel("Teacher ID:");
    JTextField teacherText = new JTextField("");

    JLabel fnLabel = new JLabel("First Name");
    JLabel lnLabel = new JLabel("Last Name");
    JLabel studentIDLabel = new JLabel("ID");

    JTextField fn = new JTextField("");
    JTextField ln = new JTextField("");
    JTextField studentID = new JTextField("");

    JLabel sectionLabel = new JLabel("Sections");
    JLabel courseLabel = new JLabel("Courses");
    JLabel studentLabel = new JLabel("Students");
    public sectionsPanel(int w, int h, Statement statementName) throws SQLException {
        setSize(w, h);
        setLayout(null);
        try
        {
            //ResultSet rs = statementName.executeQuery("SELECT id, first_name, last_name FROM student;");
            ResultSet rs = statementName.executeQuery("Select id, title, type FROM course;");
            while(rs!=null&&rs.next())
            {
                coursesData temp = new coursesData(rs.getString("id"), rs.getString("title"),  rs.getInt("type"));
                allCourses.add(temp);
            }

            coursesList.setListData(allCourses.toArray(new coursesData[0]));
        }
        catch(Exception e)
        {
            System.out.println("Exception in loading sections");
        }

        sectionLabel.setBounds(250, 20, 100, 40);
        add(sectionLabel);
        courseLabel.setBounds(50, 20, 100, 40);
        add(courseLabel);
        studentLabel.setBounds(450, 20, 100, 40);
        add(studentLabel);

        scroll= new JScrollPane(coursesList);
        scroll.setBounds(50, 50, 180, 350);
        add(scroll);

        scroll2 = new JScrollPane(sectionsList);
        scroll2.setBounds(250, 50, 180, 350);
        add(scroll2);

        scrollingThree = new JScrollPane(myStudents);
        scrollingThree.setBounds(450, 50, 180, 350);
        add(scrollingThree);

        ID.setBounds(250, 420, 70, 30);
        add(ID);

        IDText.setEditable(false);
        IDText.setBounds(330, 420, 70, 30);
        add(IDText);

        course.setBounds(250, 460, 70, 30);
        add(course);

        courseText.setBounds(330, 460, 70, 30);
        add(courseText);

        teacher.setBounds(250, 500, 70, 30);
        add(teacher);

        teacherText.setBounds(330, 500, 70, 30);
        add(teacherText);

        studentIDLabel.setBounds(450, 420, 70, 30);
        add(studentIDLabel);

        //studentID.setEditable(false);
        studentID.setBounds(530, 420, 70, 30);
        add(studentID);


        saveStudent.setBounds(450, 540, 140, 30);
        add(saveStudent);
        saveStudent.addActionListener(e->{
            //add student into roster just based on id input
            //ResultSet update = null;
            if(mySections.getSelectedValue()!=null) {
                try {
                    ResultSet rs = statementName.executeQuery("SELECT * FROM student WHERE id=" + studentID.getText() + ";");
                    String sectionStr="";
                    boolean alreadyContained=false;
                    while (rs != null && rs.next()) {
                        sectionStr = rs.getString("sections");
                        if(sectionStr.contains(" "+mySections.getSelectedValue().getId())||sectionStr.contains(""+mySections.getSelectedValue().getId()+" ")) {
                            alreadyContained=true;
                            System.out.println("-----ALREADY CONTAINED----");
                            break;
                        }
                        sectionStr+=" " + mySections.getSelectedValue().getId();
                        contactInfos student = new contactInfos(rs.getString("first_name"), rs.getString("last_name"), ""+rs.getInt("id"));
                        allMyStudents.add(student);
                        System.out.println(studentID.getText()+" yes");
                        //statementName.executeUpdate("UPDATE student SET first_name= \'test\' WHERE id=" + studentID.getText() + ";");
                        //statementName.executeUpdate("UPDATE student SET sections=\'" + sectionStr +"\' WHERE id=" + studentID.getText() + ";");

                        //statementName.executeUpdate("UPDATE student SET sections='"+sectionStr+"' WHERE id="+studentID.getText()+";");
                        //adds section into the student's list of sections
                    }
                    if(!alreadyContained) {
                        statementName.executeUpdate("UPDATE student SET sections=\'" + sectionStr +"\' WHERE id=" + studentID.getText() + ";");
                        //allMyStudents.add(student);
                    }
                    //statementName.executeUpdate("UPDATE student SET first_name=\'Matt\' WHERE id=4;");

                } catch (SQLException ex) {
                    System.out.println("Exception in saving Student");
                }
                //put update code here
                Collections.sort(allMyStudents);
                myStudents.setListData(allMyStudents.toArray(new Data[0]));



            }
            repaint();




        });


        setVisible(true);
    }


}
