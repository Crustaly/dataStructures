import java.util.*;
import java.awt.*;
import javax.swing.*;
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
    ArrayList<Data> teachers = new ArrayList<>();
    JList<Data> dataList = new JList<>();
    JScrollPane scroll1 = new JScrollPane();
    ArrayList<sectionData> sections = new ArrayList<>();
    JList<sectionData> secList = new JList<>();
    JScrollPane scroll2 = new JScrollPane();

    public TeacherPanel(int width, int height, Statement sn) throws SQLException{
        setSize(width, height);
        setLayout(null);

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
            //implement later
        });

        deleteContact.setBounds(390, 310, 100, 20);
        add(deleteContact);
        deleteContact.addActionListener(e -> {
            //implement later
        });

        save.setBounds(280, 280, 100, 20);
        add(save);
        save.addActionListener(e ->{

        });

        clear.setBounds(390, 280, 100, 20);
        add(clear);
        clear.addActionListener(e ->{
            first.setText("");
            last.setText("");
            IDs.setText("");
        });

        Collections.sort(teachers);
        dataList.setListData(teachers.toArray(new Data[0]));
        dataList.addListSelectionListener(e ->{

        });

        scroll1 = new JScrollPane(dataList);
        scroll1.setBounds(50, 50, 180, 350);
        add(scroll1);

        scroll2 = new JScrollPane(secList);
        scroll2.setBounds(50,420,180,200);
        add(scroll2);

        setVisible(true);
    }

}
