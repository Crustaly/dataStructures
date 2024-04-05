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
    ArrayList<Data> data = new ArrayList<>();
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

        Collections.sort(data);
        dataList.setListData(data.toArray(new Data[0]));
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
