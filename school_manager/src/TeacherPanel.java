import javax.swing.*;
import java.util.ArrayList;

public class TeacherPanel {
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

    ArrayList<Data> people = new ArrayList<>();
    JList<Data> myContacts = new JList<>();

    JScrollPane pane = new JScrollPane();

    ArrayList<sectionData> sections = new ArrayList<>();
    JList<sectionData> jSections = new JList<>();
    JScrollPane sScrolling = new JScrollPane();


}
