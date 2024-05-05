import javax.swing.*;
import java.awt.*;

public class WumpusFrame extends JFrame {

    public WumpusFrame(String frameName) throws Exception{
        super(frameName);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        WumpusPanel p = new WumpusPanel();

        p.setSize(500,700);
        setSize(500,700);
        setLayout(null);

        add(p);
        setVisible(true);
        setResizable(false);
    }
}
