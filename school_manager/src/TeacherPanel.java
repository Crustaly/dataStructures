import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.awt.event.*;
public class TeacherPanel extends JPanel{
    /*
    wait its like super broken sometimes teacher just takes over everything and then none of the panels work...
    other times its fine i have no clue
    the data doesnt appear uness you click view again after you go on a tab
     */
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
    JLabel sectionsLabel = new JLabel("Sections");
    ArrayList<Data> data = new ArrayList<>();
    JList<Data> dataList = new JList<>();
    JScrollPane scroll1 = new JScrollPane();
    JScrollPane scrollSections;
    ArrayList<sectionData> sections = new ArrayList<>();
    JTable sectionsTable;

    Statement sn2;

    public TeacherPanel(int width, int height, Statement sn, Statement sn2, Frame f) throws SQLException{
        repaint();
        setSize(width, height);
        setLayout(null);
        System.out.println("HEREEEE");


        //sections updates based on teacher clicked
        String[] colNames = {"Section ID","Course"};
        String ss[][] = new String[sections.size()][2];

        sectionsTable = new JTable(ss, colNames);
        sectionsTable.setDefaultEditor(Object.class, null);

        sectionsLabel.setBounds(50,420,100,10);
        add(sectionsLabel);

        saveChanges.setVisible(false);
        deleteContact.setVisible(false);

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

        scroll1 = new JScrollPane(dataList);
        scroll1.setBounds(50, 50, 180, 350);
        add(scroll1);

        try{
            ResultSet rs = sn.executeQuery("SELECT * FROM teacher;");
            while(rs!=null&&rs.next())
            {
                Data temp = new Data(rs.getString("first_name"), rs.getString("last_name"),  rs.getInt("teacher_id") + "");
                if(rs.getInt("teacher_id") != -1)
                    data.add(temp);
            }
        }
        catch(Exception ee){
            ee.printStackTrace();
        }

        Collections.sort(data);
        dataList.setListData(data.toArray(new Data[0]));
        dataList.repaint();

        System.out.println(dataList);
        repaint();
        //ask tully why it isnt showing up when we first make the panel


        saveChanges.setBounds(280, 310, 100, 20);
        saveChanges.setText("Save Changes");
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
            repaint();
            try
            {
                String firstName = temp.getFirst();
                String lastName = temp.getLast();
                int ID = Integer.parseInt(temp.getID());
                sn.executeUpdate("UPDATE teacher SET first_name='" + firstName +"' WHERE id=" + ID + ";");
                sn.executeUpdate("UPDATE teacher SET last_name='" + lastName   +"' WHERE id=" +  ID + ";");
                repaint();
            }
            catch(Exception a)
            {
                System.out.println("Exception in Save changes");
            }
        });

        deleteContact.setBounds(390, 310, 100, 20);
        add(deleteContact);
        deleteContact.addActionListener(e -> {
            System.out.println("delete clicked");
            Data s = dataList.getSelectedValue();
            System.out.println(s.getLast());
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
            repaint();
            try {

                ResultSet rs = sn.executeQuery("SELECT * FROM section where teacher_id="+Integer.parseInt(IDs.getText())+";"); //removes teacher from sections
                while(rs!=null&&rs.next()) {
                    int sectionId = rs.getInt("section_id");
                    sectionIDList.add(sectionId);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            for(int sectionID : sectionIDList) {
                try {
                    sn.executeUpdate("UPDATE section set teacher_id=-1 where teacher_id="+sectionID+";");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

            IDs.setText("");

            try {
                sn.executeUpdate("DELETE FROM teacher WHERE teacher_id =" + ids + ";");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }   repaint();

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
                    ResultSet update = sn.executeQuery("SELECT teacher_id FROM teacher WHERE first_name = '" + temp.getFirst() + "' AND last_name = '" + temp.getLast() + "';");
                    int maxID = -1;
                    while(update!=null&&update.next()) {
                        maxID = Math.max(maxID, update.getInt("teacher_id"));
                    }
                    temp.setID(maxID + "");
                    dataList.setListData(data.toArray(new Data[0]));
                    repaint();
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
                ArrayList<String[]> mod = new ArrayList<>();
                if(dataList.getSelectedValue()==null) {
                    return;
                }
                int tempID = dataList.getSelectedValue()==null? 0:
                        Integer.parseInt(dataList.getSelectedValue().getID());
                System.out.println(tempID);
                try {
                    ResultSet rs = sn.executeQuery("SELECT * FROM section WHERE teacher_id = " + tempID + ";");
                    while(rs!=null&&rs.next()) {

                        sectionData section = new sectionData(rs.getInt("section_id"), rs.getInt("course_id"), rs.getInt("teacher_id"), sn);
                        //System.out.println(section.getTeacherId() + " is the teacher id");
                        sections.add(section);

                        ResultSet rrs = sn2.executeQuery("SELECT * FROM course WHERE course_id=" + section.getCourseId() + ";");
                        while(rrs != null && rrs.next()) {
                            System.out.println(rrs.getString("title"));
                            String[] sss = new String[]{section.getId()+"", rrs.getString("title")};
                            mod.add(sss);
                        }
                        rrs.close();
                    }
                    rs.close();
                    remove(scrollSections);

                    if(mod.size() > 0 ){
                        Collections.sort(sections);
                        sectionsTable = new JTable(alToAr(mod), colNames);
                        scrollSections = new JScrollPane(sectionsTable);
                        scrollSections.setBounds(50, 450, 400, 350);
                        sectionsTable.setDefaultEditor(Object.class, null);
                        add(scrollSections);
                    }
                    else {
                        sectionsTable = new JTable(ss,colNames);
                        scrollSections = new JScrollPane(sectionsTable);
                        scrollSections.setBounds(50, 450, 400, 350);
                        sectionsTable.setDefaultEditor(Object.class, null);
                        add(scrollSections);
                    }


                    repaint();
                    //why do the column labels disappear after setting model?

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
        });

        scrollSections = new JScrollPane(sectionsTable);
        scrollSections.setBounds(50, 450, 400, 350);
        add(scrollSections);

        repaint();
        setVisible(true);
        repaint();
    }

    public String[][] alToAr(ArrayList<String[]> al){
        if(al.size() > 0) {
            String[][] ar = new String[al.size()][al.get(0).length];
            for(int i = 0; i<al.size(); i++){
                ar[i] = al.get(i);
            }
            return ar;
        }
        return null;
    }

}
