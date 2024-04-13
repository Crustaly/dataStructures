import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.awt.event.*;
public class TeacherPanel extends JPanel{
    JLabel firstName = new JLabel("First Name: ");
    JLabel lastName = new JLabel("Last Name: ");
    JLabel ID = new JLabel("ID: ");
    JTextField first = new JTextField();
    JTextField last = new JTextField();
    JTextField IDs = new JTextField();
    JButton save = new JButton("Save");
    JButton clear = new JButton("Clear");
    JButton saveChanges = new JButton("Save changes");
    JButton deleteContact = new JButton("Delete contact");
    ArrayList<Data> data = new ArrayList<>();
    JList<Data> dataList = new JList<>();
    JScrollPane scroll1 = new JScrollPane();
    JScrollPane scrollSections;
    ArrayList<sectionData> sections = new ArrayList<>();
    JTable sectionsTable;

    public TeacherPanel(int width, int height, Statement sn) throws SQLException{
        setSize(width, height);
        setLayout(null);

        //sections updates based on teacher clicked
        String[] colNames = {"Section ID","Course"};
        String ss[][] = new String[sections.size()][2];

        sectionsTable = new JTable(ss, colNames);
        sectionsTable.setDefaultEditor(Object.class, null);

        firstName.setBounds(280, 100, 100, 50);
        add(firstName);
        first.setText(""); //sql
        first.setBounds(390,120,100,20);
        add(first);

        setVisible(true);

        lastName.setBounds(280, 150, 100, 20);
        add(lastName);
        last.setText(""); //sql
        last.setBounds(390, 150, 100, 20);
        add(last);


        ID.setBounds(280, 200, 100, 20);
        add(ID);
        IDs.setText(""); //sql
        IDs.setBounds(390, 200, 100, 20);
        IDs.setEditable(false);
        add(IDs);

        saveChanges.setBounds(280, 310, 100, 20);
        saveChanges.setText("Save Changes");
        saveChanges.setVisible(true);
        add(saveChanges);
        saveChanges.addActionListener(e -> {
            //edit teachers
            Data s = dataList.getSelectedValue();
            int index = data.indexOf(s);
            Data temp = new Data(first.getText(), last.getText(), IDs.getText());
            data.set(index, temp);

            first.setText("");
            last.setText("");
            IDs.setText("");

            Collections.sort(data);
            dataList.setListData(data.toArray(new Data[0]));

            saveChanges.setVisible(false);
            deleteContact.setVisible(false);

            save.setVisible(true);
            clear.setVisible(true);

            try
            {
                String firstName = temp.getFirst();
                String lastName = temp.getLast();
                int ID = Integer.parseInt(temp.getID());
                sn.executeUpdate("UPDATE teacher SET first_name='" + firstName +"' WHERE id=" + ID + ";");
                sn.executeUpdate("UPDATE teacher SET last_name='" + lastName   +"' WHERE id=" +  ID + ";");
            }
            catch(Exception a)
            {
                System.out.println("Exception in Save changes");
            }
        });

        deleteContact.setBounds(390, 310, 100, 20);
        add(deleteContact);
        deleteContact.addActionListener(e -> {
            Data s = dataList.getSelectedValue();
            int ids = Integer.parseInt(s.getID());
            first.setText("");
            last.setText("");
            data.remove(s);
            Collections.sort(data);
            dataList.setListData(data.toArray(new Data[0]));
            saveChanges.setVisible(false);
            deleteContact.setVisible(false);
            save.setVisible(true);
            clear.setVisible(true);
            ArrayList<Integer> sectionIDList = new ArrayList<>();
            try {
                ResultSet rs = sn.executeQuery("SELECT * FROM section where teacher_id="+Integer.parseInt(IDs.getText())+";"); //removes teacher from sections
                while(rs!=null&&rs.next()) {
                    int sectionId = rs.getInt("id");
                    sectionIDList.add(sectionId);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            for(int sectionID : sectionIDList) {
                try {
                    sn.executeUpdate("UPDATE section set teacher_id=-1 where id="+sectionID+";");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

            IDs.setText("");

            try {
                sn.executeUpdate("DELETE FROM teacher WHERE id =" + ids + ";");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        save.setBounds(280, 280, 100, 20);
        add(save);
        save.addActionListener(e ->{
            if (first.getText().equals("") || last.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Not valid", "Message", JOptionPane.INFORMATION_MESSAGE);
            } else {
                Data temp = new Data(first.getText(), last.getText(), IDs.getText());
                data.add(temp);
                Collections.sort(data);
                dataList.setListData(data.toArray(new Data[0]));
                first.setText("");
                last.setText("");
                IDs.setText("");
                try {
                    sn.executeUpdate("INSERT INTO teacher (first_name, last_name) VALUES ('" + temp.getFirst() + "', '" + temp.getLast() + "');");
                    ResultSet update = sn.executeQuery("SELECT id FROM teacher WHERE first_name = '" + temp.getFirst() + "' AND last_name = '" + temp.getLast() + "';");
                    int maxID = -1;
                    while(update!=null&&update.next()) {
                        maxID = Math.max(maxID, update.getInt("id"));
                    }
                    temp.setID(maxID + "");
                    dataList.setListData(data.toArray(new Data[0]));
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        clear.setBounds(390, 280, 100, 20);
        add(clear);
        clear.addActionListener(e ->{
            first.setText("");
            last.setText("");
            IDs.setText("");
        });

        Collections.sort(data);
        dataList.setListData(data.toArray(new Data[0]));
        dataList.addListSelectionListener(e ->{
            //click on teacher to show info

            if (!data.isEmpty()) {
                save.setVisible(false);
                clear.setVisible(false);

                saveChanges.setVisible(true);
                deleteContact.setVisible(true);

                if (dataList.getSelectedValue() != null) {
                    if (dataList.getSelectedValue().getFirst() != "")
                        first.setText(dataList.getSelectedValue().getFirst());
                    if (dataList.getSelectedValue().getFirst() != "")
                        last.setText(dataList.getSelectedValue().getLast());
                    if (dataList.getSelectedValue().getFirst() != "")
                        IDs.setText(dataList.getSelectedValue().getID());

                }

                //show teacher sections
                sections.clear();
                DefaultTableModel mod = new DefaultTableModel();

                if(dataList.getSelectedValue()==null) {
                    return;
                }
                int tempID = dataList.getSelectedValue()==null? 0:
                        Integer.parseInt(dataList.getSelectedValue().getID());

                try {

                    ResultSet rs = sn.executeQuery("SELECT id, course_id, teacher_id FROM section");
                    while(rs!=null&&rs.next()) {
                        if(rs.getInt("teacher_id")==tempID) {
                            sectionData section = new sectionData(rs.getInt("id"), rs.getInt("course_id"), rs.getInt("teacher_id"), sn);
                            //System.out.println(section.getTeacherId() + " is the teacher id");
                            sections.add(section);
                        }
                    }

                    Collections.sort(sections);
                    //add sections to tableModel
                    for(sectionData sd: sections) {
                        int tempCourseID = sd.getCourseId();
                        String tempSectionID = sd.getId() + "";
                        rs = sn.executeQuery("SELECT * FROM course where id=" + tempCourseID + ";");
                        String[] sss = new String[]{tempSectionID, rs.getString("title")};
                        mod.addRow(sss);
                    }
                    sectionsTable.setModel(mod);

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }


            }
        });

        scroll1 = new JScrollPane(dataList);
        scroll1.setBounds(50, 50, 180, 350);
        add(scroll1);

        scrollSections = new JScrollPane(sectionsTable);
        scrollSections.setBounds(50, 450, 400, 350);
        add(scrollSections);

        setVisible(true);
    }

}
