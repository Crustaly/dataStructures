import javax.swing.*;
import javax.xml.transform.Result;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.*;
import java.util.Arrays;
import java.util.Collections;

public class sectionsPanel extends JPanel {


    ArrayList<coursesData> allMyCourses = new ArrayList<>();
    JList<coursesData> myCourses = new JList<>();
    JScrollPane scrolling = new JScrollPane();

    JButton save = new JButton("Save");
    JButton clear = new JButton("Clear");
    JButton saveSectionChanges = new JButton("Save Changes");
    JButton deleteSection = new JButton("Delete Section");


    JButton saveStudent = new JButton("Save Student");
    JButton deleteStudent = new JButton("Delete Student");

    JButton saveChanges = new JButton("Save changes");
    JButton deleteContact = new JButton("Delete contact");

    ArrayList<sectionData> allMySections = new ArrayList<>();
    JList<sectionData> mySections = new JList<>();
    JScrollPane scrollingTwo = new JScrollPane();

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

            ResultSet rs = statementName.executeQuery("Select id, title, type FROM course;");
            while(rs!=null&&rs.next())
            {
                coursesData temp = new coursesData(rs.getString("id"), rs.getString("title"),  rs.getInt("type"));
                allMyCourses.add(temp);
            }

            myCourses.setListData(allMyCourses.toArray(new coursesData[0]));
        }
        catch(Exception e)
        {
            System.out.println("Exception in loading sections");
        }

        sectionLabel.setBounds(250,20,100,40);
        add(sectionLabel);
        courseLabel.setBounds(50,20,100,40);
        add(courseLabel);
        studentLabel.setBounds(450,20,100,40);
        add(studentLabel);

        scrolling = new JScrollPane(myCourses);
        scrolling.setBounds(50, 50, 180, 350);
        add(scrolling);

        scrollingTwo = new JScrollPane(mySections);
        scrollingTwo.setBounds(250,50,180,350);
        add(scrollingTwo);

        scrollingThree = new JScrollPane(myStudents);
        scrollingThree.setBounds(450,50,180,350);
        add(scrollingThree);

        ID.setBounds(250,420,70,30);
        add(ID);

        IDText.setEditable(false);
        IDText.setBounds(330,420,70,30);
        add(IDText);

        course.setBounds(250,460,70,30);
        add(course);

        courseText.setBounds(330,460,70,30);
        add(courseText);

        teacher.setBounds(250,500,70,30);
        add(teacher);

        teacherText.setBounds(330, 500, 70, 30);
        add(teacherText);

        studentIDLabel.setBounds(450,420,70,30);
        add(studentIDLabel);

        studentID.setBounds(530,420,70,30);
        add(studentID);


        saveStudent.setBounds(450,540,140,30);
        add(saveStudent);
        saveStudent.addActionListener(e->{
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
                        Data student = new Data(rs.getString("first_name"), rs.getString("last_name"), ""+rs.getInt("id"));
                        allMyStudents.add(student);
                        System.out.println(studentID.getText()+" yes");

                    }
                    if(!alreadyContained) {
                        statementName.executeUpdate("UPDATE student SET sections=\'" + sectionStr +"\' WHERE id=" + studentID.getText() + ";");

                    }

                } catch (SQLException ex) {
                    System.out.println("Exception in saving Student");
                }

                Collections.sort(allMyStudents);
                myStudents.setListData(allMyStudents.toArray(new Data[0]));



            }
            repaint();




        });

        deleteStudent.setBounds(450, 580, 140,30);
        add(deleteStudent);
        deleteStudent.addActionListener(e-> {
            if(mySections.getSelectedValue()!=null) {
                try {
                    //ResultSet rs = statementName.executeQuery("SELECT * FROM student WHERE id=4;");
                    ResultSet rs = statementName.executeQuery("SELECT * FROM student WHERE id=" + Integer.parseInt(studentID.getText()) + ";");
                    String sectionStr="";
                    String newSectionStr = "";
                    while (rs != null && rs.next()) {
                        System.out.println("student: " + rs.getInt("id"));
                        sectionStr = rs.getString("sections");
                        String[] tempAr = sectionStr.split(" ");

                        for(String str : tempAr) {
                            if(str.length()==0) {
                                continue;
                            }
                            if(str.equals(""+mySections.getSelectedValue().getId())) {
                                continue;
                            }
                            newSectionStr+=" " + str;
                        }


                    }
                    statementName.executeUpdate("UPDATE student SET sections=\'"+newSectionStr+"\' WHERE id="+studentID.getText()+";");

                } catch (SQLException ex) {
                    System.out.println("Exception delete Student");
                }
            }

            if(!allMySections.isEmpty()) {
                sectionData temp = mySections.getSelectedValue();
                if(temp!=null) {

                    allMyStudents.clear();
                    try {
                        ResultSet rs = statementName.executeQuery("SELECT * FROM student");
                        while(rs!=null&&rs.next()) {
                            System.out.println("Student: " + rs.getString("id") + " sections: " + rs.getString("sections"));
                            // if(rs.getString("sections").contains(""+mySections.getSelectedValue().id)) {
                            if(rs.getString("sections").contains(""+mySections.getSelectedValue().id+" ")||rs.getString("sections").contains(" "+mySections.getSelectedValue().id)) {

                                System.out.println("inside");
                                Data student = new Data(rs.getString("first_name"), rs.getString("last_name"), ""+rs.getInt("id"));
                                allMyStudents.add(student);
                            }

                        }
                    } catch (SQLException ex) {
                        System.out.println("Exception in my sections");
                    }


                    courseText.setText("" + temp.getCourseId());
                    IDText.setText("" + temp.getId());
                    teacherText.setText("" + temp.getTeacherId());
                }

                Collections.sort(allMyStudents);

                myStudents.setListData(allMyStudents.toArray(new Data[0]));


            }
            repaint();
        });





        clear.setBounds(250, 550, 150, 20);
        add(clear);
        clear.addActionListener(e ->
        {
            IDText.setText("");
            courseText.setText("");
            teacherText.setText("");
        });

        saveSectionChanges.setBounds(250, 580, 150, 20);
        add(saveSectionChanges);
        saveSectionChanges.addActionListener(e-> {
            if(IDText.getText().length()>0) {
                sectionData s = mySections.getSelectedValue();
                int index = allMySections.indexOf(s);
                sectionData temporary = null;
                try {
                    temporary = new sectionData(Integer.parseInt(IDText.getText()), Integer.parseInt(courseText.getText()), Integer.parseInt(teacherText.getText()), statementName);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                allMySections.set(index, temporary);
                IDText.setText("");
                courseText.setText("");
                teacherText.setText("");

                 mySections.setListData(allMySections.toArray(new sectionData[0]));

                System.out.println("Got here");

                try
                {

                    int courseID = temporary.getCourseId();
                    int teacherID = temporary.getTeacherId();
                    int ID = temporary.getId();
                    statementName.executeUpdate("UPDATE section SET course_id='" + courseID +"' WHERE id=" + ID + ";");
                    statementName.executeUpdate("UPDATE section SET teacher_id='" + teacherID   +"' WHERE id=" +  ID + ";");
                    System.out.println("At least I got here");

                }
                catch(Exception a)
                {
                    System.out.println("Exception in Save changes");
                }

            } else {
                System.out.print("empty id");
            }


            if(!allMyCourses.isEmpty())
            {
                allMyStudents.clear();
                coursesData temp = myCourses.getSelectedValue();
                int tempId = Integer.parseInt(temp.getID());
                allMySections.clear();
                ResultSet ab = null;
                try {
                    ab = statementName.executeQuery("SELECT id, teacher_id, course_id FROM section;");

                    while(ab!=null&&ab.next()) {
                        sectionData temp2 = null;
                        temp2 = new sectionData(ab.getInt("id"), ab.getInt("course_id"), ab.getInt("teacher_id"), statementName);


                        if(tempId==temp2.getCourseId()) {
                            allMySections.add(temp2);
                        }

                    }
                }
                catch(Exception r)
                {
                    System.out.println("savesectionchanges error");
                }
                myStudents.setListData(allMyStudents.toArray(new Data[0]));
                mySections.setListData(allMySections.toArray(new sectionData[0]));
            }

            repaint();


        });


        save.setBounds(250,610,150,20);
        add(save);
        save.addActionListener(e-> {

            try {
                statementName.executeUpdate("INSERT INTO section (course_id, teacher_id) VALUES ('" + courseText.getText()+"', '" + teacherText.getText()+"');");
                ResultSet ab = statementName.executeQuery("SELECT id FROM section WHERE course_id = '" + courseText.getText() + "' AND teacher_id = '" + teacherText.getText()+"';");
                int tempID = -1;
                while(ab!=null&&ab.next())
                {
                    tempID = ab.getInt("id");

                }
                mySections.setListData(allMySections.toArray(new sectionData[0]));
                System.out.print(allMySections);


            } catch (SQLException ex) {
                System.out.print("exception inserting into section");
            }



            allMyStudents.clear();
            coursesData temp = myCourses.getSelectedValue();

            int tempId = Integer.parseInt(temp.getID());
            allMySections.clear();
            try {
                ResultSet ab = statementName.executeQuery("SELECT id, teacher_id, course_id FROM section;");
                while(ab!=null&&ab.next()) {
                    sectionData temp2 = new sectionData(ab.getInt("id"), ab.getInt("course_id"), ab.getInt("teacher_id"), statementName);
                    if(tempId==temp2.getCourseId()) {
                        allMySections.add(temp2);
                    }

                }
            }
            catch(Exception r)
            {
                System.out.println("mycourses exception");
            }
            mySections.setListData(allMySections.toArray(new sectionData[0]));

            repaint();


        });


        deleteSection.setBounds(250,640,150,20);
        add(deleteSection);
        deleteSection.addActionListener(e-> {
            if(!allMySections.isEmpty()) {
                sectionData temp = mySections.getSelectedValue();


                ArrayList<Integer> studentIDs=new ArrayList<>();
                ArrayList<String> studentSections = new ArrayList<>();
                try {
                    ResultSet rs = statementName.executeQuery("SELECT * from student");

                    while(rs!=null&&rs.next()) {
                        String[] ar = rs.getString("sections").split(" ");
                        String newSections = "";
                        for(String str : ar) {
                            if(!str.equals(IDText.getText())) {
                                newSections+=str+" ";
                            }
                        }
                        studentIDs.add(rs.getInt("id"));
                        studentSections.add(newSections);
                        }
                } catch (SQLException ex) {
                    System.out.println("Exception retrieving student sections");
                }

                for(int i = 0; i < studentIDs.size(); i++) {
                    String newSections = studentSections.get(i);
                    int curId = studentIDs.get(i);
                    try {
                        statementName.executeUpdate("UPDATE student set sections=\'" + newSections+"\' where id="+curId+";");
                    } catch (SQLException ex) {
                        System.out.println("Exception updating student section");
                    }

                }


                if(temp!=null) {

                    try {
                        statementName.executeUpdate("DELETE FROM section WHERE id =" + temp.getId() + ";");
                    } catch (SQLException ex) {
                        System.out.println("Exception deleting section");
                    }

                }
            }
            IDText.setText("");
            courseText.setText("");
            teacherText.setText("");


            allMyStudents.clear();
            myStudents.setListData(allMyStudents.toArray(new Data[0]));
            repaint();



            if(!allMyCourses.isEmpty())
            {
                coursesData temp = myCourses.getSelectedValue();

                int tempId = Integer.parseInt(temp.getID());
                allMySections.clear();
                try {
                    ResultSet ab = statementName.executeQuery("SELECT id, teacher_id, course_id FROM section;");
                    while(ab!=null&&ab.next()) {
                        sectionData temp2 = new sectionData(ab.getInt("id"), ab.getInt("course_id"), ab.getInt("teacher_id"), statementName);
                        if(tempId==temp2.getCourseId()) {
                            allMySections.add(temp2);
                        }
                        //allMySections.add(temp2);
                    }

                }
                catch(Exception r)
                {
                    System.out.println("delete section error");
                }
                mySections.setListData(allMySections.toArray(new sectionData[0]));
            }
        });

        mySections.addListSelectionListener(e-> {

            if(!allMySections.isEmpty()) {
                sectionData temp = mySections.getSelectedValue();

                repaint();
                if(temp!=null) {

                    allMyStudents.clear();
                    try {
                        ResultSet rs = statementName.executeQuery("SELECT * FROM student");
                        while(rs!=null&&rs.next()) {
                            System.out.println("Student: " + rs.getString("id") + " sections: " + rs.getString("sections"));
                            if(rs.getString("sections").contains(""+mySections.getSelectedValue().id+" ")||rs.getString("sections").contains(" "+mySections.getSelectedValue().id)) {
                                System.out.println("inside");
                                Data student = new Data(rs.getString("first_name"), rs.getString("last_name"), ""+rs.getInt("id"));
                                allMyStudents.add(student);
                            }

                        }
                    } catch (SQLException ex) {
                        System.out.println("Exception in my sections");
                    }


                    courseText.setText("" + temp.getCourseId());
                    IDText.setText("" + temp.getId());
                    teacherText.setText("" + temp.getTeacherId());
                }

                Collections.sort(allMyStudents);

                myStudents.setListData(allMyStudents.toArray(new Data[0]));

                repaint();

            }


        });



        myCourses.addListSelectionListener(e ->
        {
            if(!allMyCourses.isEmpty())
            {
                allMyStudents.clear();
                coursesData temp = myCourses.getSelectedValue();
                //courseText.setText(temp.getID());
                int tempId = Integer.parseInt(temp.getID());
                allMySections.clear();
                try {
                    ResultSet ab = statementName.executeQuery("SELECT id, teacher_id, course_id FROM section;");
                    while(ab!=null&&ab.next()) {
                        sectionData temp2 = new sectionData(ab.getInt("id"), ab.getInt("course_id"), ab.getInt("teacher_id"), statementName);
                        if(tempId==temp2.getCourseId()) {
                            allMySections.add(temp2);
                        }
                       
                    }
                }
                catch(Exception r)
                {
                    System.out.println("mycourses exception");
                }
                myStudents.setListData(allMyStudents.toArray(new Data[0]));
                mySections.setListData(allMySections.toArray(new sectionData[0]));
            }

            repaint();
        });

        myStudents.addListSelectionListener(e-> {
            if(myStudents.getSelectedValue()!=null) {
                studentID.setText(myStudents.getSelectedValue().getID());
            }
        });
        setVisible(true);
    }
}