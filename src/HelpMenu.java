import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class HelpMenu extends JDialog {
    public List<JButton> buttonList = new ArrayList<>();
    JScrollPane scrollPane;
    JTextArea textArea;

    HelpMenu(){
        setSize(300,400);

        scrollPane = new JScrollPane();



    }
}
