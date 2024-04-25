import javax.swing.*;
import javax.xml.transform.Result;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.*;
import java.util.Arrays;
import java.util.Collections;

public class sectionsPanel extends JPanel {
    //we have a scrolling jlist with all the possible coursesData(filter through the coursesDatalist)
    //from this, we get all the sections with the course name (filter through the sections list)
    //from this show id, course, teacher, rosters
    /*
    Deleting a teacher will set his/her sections to -1 (No Teacher Assigned)
    Deleting a course will delete all corresponding sections of the course and update the teachers
    / students in those section to no longer have those sections.
    Deleting a section will update the teachers / students in those section to no longer have those sections.
    */
    JComboBox<String> teachersDrop=new JComboBox<>();
    JComboBox<String> courseDrop=new JComboBox<>();

    ArrayList<coursesData> allMyCourses= new ArrayList<>();
    JList<coursesData>  myCourses= new JList<>();
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

    JLabel studentIdAdd = new JLabel("Student ID:");
    JTextField courseText = new JTextField();

    JLabel teacher = new JLabel("Teacher ID:");
    JTextField teacherText = new JTextField("");

    JLabel fnLabel = new JLabel("First Name");
    JLabel lnLabel = new JLabel("Last Name");
    JLabel studentIDLabel = new JLabel("ID");

    JTextField fn = new JTextField("");
    JTextField ln = new JTextField("");
    JTextField studentID = new JTextField("");

    //JLabel sectionLabel, courseLabel, studentLabel;
    JLabel sectionLabel = new JLabel("Sections");
    JLabel courseLabel = new JLabel("Courses");
    JLabel studentLabel = new JLabel("Students");
    JLabel teacherSelect = new JLabel("Select a teacher: ");
    JLabel courseSelect = new JLabel("Select a course: ");

    int selectedTeacher = -1;
    int selectedCourse = -1;


    //deleting teacher

    public sectionsPanel(int w, int h, Statement statementName, Statement sn2) throws SQLException {
        System.out.println("IN HERE");
        setSize(w, h);
        setLayout(null);
        try
        {
            //ResultSet rs = statementName.executeQuery("SELECT id, first_name, last_name FROM student;");
            ResultSet rs = statementName.executeQuery("Select course_id, title, type FROM course;");
            while(rs!=null&&rs.next())
            {
                coursesData temp = new coursesData(rs.getString("course_id"), rs.getString("title"),  rs.getInt("type"));
                allMyCourses.add(temp);
            }

            myCourses.setListData(allMyCourses.toArray(new coursesData[0]));
            System.out.println("HEREaaa");



        for(coursesData c: allMyCourses){
            courseDrop.addItem(c.getCourseName() + "," + c.getID());
           // courseMap.put(c.getCourseName(), Integer.parseInt(c.getID()));
        }

         rs=statementName.executeQuery("SELECT * FROM teacher");
        while(rs!=null&&rs.next()){
            teachersDrop.addItem(rs.getString("last_name")+","+rs.getString("first_name") +","+rs.getInt("teacher_id"));
        }
    }
        catch(Exception e)
    {
        System.out.println("Exception in loading sections");
    }

        sectionLabel.setBounds(250,20,100,40);
    add(sectionLabel);

        courseDrop.setBounds(50,460,180,40);
    add(courseDrop);
        courseSelect.setBounds(50,440,180,20);
        add(courseSelect);

        teachersDrop.setBounds(50,540,180,40);
        add(teachersDrop);
        teacherSelect.setBounds(50,520,180,20);
    add(teacherSelect);

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

        studentIdAdd.setBounds(450,420,70,30);
        add(studentIdAdd);

        studentID.setBounds(530,420,70,30);
        add(studentID);

        saveStudent.setBounds(450,540,140,30);
        add(saveStudent);
        saveStudent.addActionListener(e->{
            //add student into roster just based on id input
            //ResultSet update = null;
            if(mySections.getSelectedValue()!=null) {
                try {
                    //im not sure if its student id
                    ResultSet rs = statementName.executeQuery("SELECT * FROM enrollment WHERE student_id=" + studentID.getText() + ";");
                    String sectionStr="";
                    boolean alreadyContained=false;

                    while (rs != null && rs.next()) {
                        if(rs.getInt("section_id") == mySections.getSelectedValue().getId()) {
                            alreadyContained=true;
                            break;
                        }
                    }

                    if(!alreadyContained) {
                        statementName.executeUpdate("INSERT INTO enrollment (section_id, student_id) VALUES (" + mySections.getSelectedValue().getId() + ", " + studentID.getText() + ");");
                        Data student = new Data(rs.getString("first_name"), rs.getString("last_name"), ""+rs.getInt("student_id"));
                        allMyStudents.add(student);
                    }
                    repaint();

                } catch (SQLException ex) {
                    System.out.println("Exception in saving Student");
                }
                //put update code here
                Collections.sort(allMyStudents);
                myStudents.setListData(allMyStudents.toArray(new Data[0]));

                repaint();
            }

        });

        deleteStudent.setBounds(450, 580, 140,30);
        add(deleteStudent);
        deleteStudent.addActionListener(e-> {
            if(mySections.getSelectedValue()!=null) {
                try {
                    statementName.executeUpdate("DELETE FROM enrollment WHERE student_id=" + studentID.getText() + "AND section_id=" + mySections.getSelectedValue().getId()+ ";");
                } catch (SQLException ex) {
                    System.out.println("Exception delete Student");
                }
            }

            if(!allMySections.isEmpty()) {
                sectionData temp = mySections.getSelectedValue();
                if(temp!=null) {
                    for(int i = 0; i<allMyStudents.size(); i++){
                        if(allMyStudents.get(i).getID().equals(studentID.getText())) {
                            allMyStudents.remove(i);
                            break;
                        }
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
            courseDrop.setSelectedIndex(-1);
            teachersDrop.setSelectedIndex(-1);
            selectedCourse = -1;
            selectedTeacher = -1;
        });
        teachersDrop.addActionListener(e ->{
            String s[] = teachersDrop.getSelectedItem().toString().split(",");
            selectedTeacher = Integer.parseInt(s[2]);

        });

        courseDrop.addActionListener(e ->{
            String s[] = courseDrop.getSelectedItem().toString().split(",");
            selectedCourse = Integer.parseInt(s[1]);
        });

        saveSectionChanges.setBounds(250, 580, 150, 20);
        add(saveSectionChanges);
        saveSectionChanges.addActionListener(e-> {
            if(IDText.getText().length()>0) {
                sectionData s = mySections.getSelectedValue();
                int index = allMySections.indexOf(s);
                sectionData temporary = null;
                try {
                    String x = courseDrop.getSelectedItem().toString();
                    String []aa = x.split(",");
                    int cur = Integer.parseInt(aa[1]);
                    String y = teachersDrop.getSelectedItem().toString();
                    String []bb = y.split(",");
                    int teach = Integer.parseInt(bb[2]);
                    temporary = new sectionData(Integer.parseInt(IDText.getText()), cur, teach, statementName);
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
                    statementName.executeUpdate("UPDATE section SET course_id='" + courseID +"' WHERE section_id=" + ID + ";");
                    statementName.executeUpdate("UPDATE section SET teacher_id='" + teacherID   +"' WHERE section_id=" +  ID + ";");
                    System.out.println("At least I got here");

                }
                catch(Exception a)
                {
                    System.out.println("Exception in Save changes");
                }

            } else {
                System.out.print("empty id");
            }

            //add repaint code here
            if(!allMyCourses.isEmpty())
            {
                allMyStudents.clear();
                coursesData temp = myCourses.getSelectedValue();
                //courseText.setText(temp.getID());
                int tempId = Integer.parseInt(temp.getID());
                allMySections.clear();

                try {
                    ResultSet ab = statementName.executeQuery("SELECT section_id, teacher_id, course_id FROM section;");

                    while(ab!=null&&ab.next()) {
                        sectionData temp2 = new sectionData(ab.getInt("section_id"), ab.getInt("course_id"), ab.getInt("teacher_id"), statementName);
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
            //INSERT INTO student (first_name, last_name) VALUES (‘Jim’, ‘Smith’);
            try {
                String x = courseDrop.getSelectedItem().toString();
                String []aa = x.split(",");
                int cur = Integer.parseInt(aa[1]);
                String y = teachersDrop.getSelectedItem().toString();
                String []bb = y.split(",");
                int teach = Integer.parseInt(bb[2]);
                statementName.executeUpdate("INSERT INTO section (course_id, teacher_id) VALUES ('" + cur+"', '" + teach+"');");
                ResultSet ab = statementName.executeQuery("SELECT section_id FROM section WHERE course_id = '" + cur + "' AND teacher_id = '" + teach+"';");

                Collections.sort(allMySections);
                mySections.setListData(allMySections.toArray(new sectionData[0]));

            } catch (SQLException ex) {
                System.out.print("exception inserting into section");
            }


            //add section repainted code here
            allMyStudents.clear();
            String x = courseDrop.getSelectedItem().toString();
            String []aa = x.split(",");
            int tempId = Integer.parseInt(aa[1]);

            allMySections.clear();
            System.out.println("SDBIHSBDIHFS");
            try {
                ResultSet ab = statementName.executeQuery("SELECT section_id, teacher_id, course_id FROM section;");
                while(ab!=null&&ab.next()) {
                    sectionData temp2 = new sectionData(ab.getInt("section_id"), ab.getInt("course_id"), ab.getInt("teacher_id"), statementName);
                    if(tempId==temp2.getCourseId()) {
                        allMySections.add(temp2);
                    }
                    //allMySections.add(temp2);
                }
            }
            catch(Exception r)
            {
                System.out.println("mycoursesDataexception");
            }
            mySections.setListData(allMySections.toArray(new sectionData[0]));

            repaint();


        });


        deleteSection.setBounds(250,640,150,20);
        add(deleteSection);
        deleteSection.addActionListener(e-> {
            if(!allMySections.isEmpty()) {
                sectionData temp = mySections.getSelectedValue();
                if(temp!=null) {
                    if(mySections.getSelectedValue() != null)
                    {
                        try{
                            statementName.executeUpdate("DELETE FROM enrollment WHERE section_id = " + temp.getId() +";");
                        }
                        catch(Exception ex){
                            ex.printStackTrace();
                        }

                        try {
                            statementName.executeUpdate("DELETE FROM section WHERE section_id =" + temp.getId() + ";");
                        } catch (SQLException ex) {
                            System.out.println("Exception deleting section");
                        }

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
                    ResultSet ab = statementName.executeQuery("SELECT section_id, teacher_id, course_id FROM section;");
                    while(ab!=null&&ab.next()) {
                        sectionData temp2 = new sectionData(ab.getInt("section_id"), ab.getInt("course_id"), ab.getInt("teacher_id"), statementName);
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
                if (temp != null) {
                    allMyStudents.clear();
                    try {
                        ResultSet rs = statementName.executeQuery("SELECT student_id FROM enrollment WHERE section_id = " + temp.getId() + ";");
                        while (rs != null && rs.next()) {
                            ResultSet rs2 = sn2.executeQuery("SELECT * FROM student WHERE student_id = " + rs.getInt("student_id") + ";");
                            while (rs2 != null & rs2.next()) {
                                Data student = new Data(rs2.getString("last_name"), rs2.getString("first_name"), "" + rs2.getInt("student_id"));

                                boolean containsStudent = false;
                                for(Data ss: allMyStudents){
                                    if(student.getID() == ss.getID()){
                                        containsStudent = true;
                                    }
                                }

                                if(!containsStudent) allMyStudents.add(student);
                                System.out.println(student);
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

//meow!
        myCourses.addListSelectionListener(e ->
        {
            if(!allMyCourses.isEmpty())
            {
                allMyStudents.clear();
                coursesData temp = myCourses.getSelectedValue();
                //courseText.setText(temp.getID());
                int tempId = Integer.parseInt(temp.getID());
                allMySections.clear();
                ID.setText("");
                System.out.println("pooop]");
                try {
                    ResultSet ab = statementName.executeQuery("SELECT section_id, teacher_id, course_id FROM section;");
                    while(ab!=null&&ab.next()) {
                        sectionData temp2 = new sectionData(ab.getInt("section_id"), ab.getInt("course_id"), ab.getInt("teacher_id"), statementName);
                        if(tempId==temp2.getCourseId()) {
                            allMySections.add(temp2);
                        }
                        //allMySections.add(temp2);
                    }
                }
                catch(Exception r)
                {
                    System.out.println("mycoursesDataexception");
                }
                myStudents.setListData(allMyStudents.toArray(new Data[0]));
                mySections.setListData(allMySections.toArray(new sectionData[0]));
            }

            repaint();
            System.out.println("HERaE");
        });

        myStudents.addListSelectionListener(e-> {
            if(myStudents.getSelectedValue()!=null) {
                studentID.setText(myStudents.getSelectedValue().getID());
            }
        });
        setVisible(true);
    }
}