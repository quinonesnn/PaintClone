import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.EmptyStackException;

public class Main extends JFrame implements ActionListener
{
    Icon clearIcon = new ImageIcon(new ImageIcon("res/clearIcon.png").getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
    Icon eraserIcon = new ImageIcon(new ImageIcon("res/eraserIcon.png").getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
    public Icon saveIcon = new ImageIcon(new ImageIcon("res/floppy-disk.png").getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));

    private PaintingPanel paintingPanel;
    private HelpMenu helpMenu;

    JButton selectColorBtn;
    JButton clearBtn;
    JButton eraserBtn;
    JButton saveBtn;
    JButton undoBtn;
    JButton redoBtn;
    JButton databtn;

    JPanel topPanel;

    JTextField strokeTxt;
    JButton setStroke;
    JComboBox strokeSize;

    public static void main(String[] args)
    {
        EventQueue.invokeLater(
                new Thread(() -> {
                    Main app = new Main();
                    app.setLocationRelativeTo(null);
                    app.setVisible(true);
                })
        );
    }

    public Main()
    {
        initGUI();
    }

    private void initGUI()
    {
        setTitle("JavaPainter");
        setSize(new Dimension(1000,600));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container container = this.getContentPane();

        container.setLayout(new BorderLayout());

        paintingPanel = new PaintingPanel();

        container.add(paintingPanel, BorderLayout.CENTER);

        ColorButtons colorButtons = new ColorButtons(paintingPanel, container);

        container.add(colorButtons, BorderLayout.EAST);

        clearBtn = new JButton(clearIcon);
        clearBtn.addActionListener(this);
        selectColorBtn = new JButton("Select Color");
        selectColorBtn.addActionListener(this);

        strokeTxt = new JTextField("Size :");
        strokeTxt.setForeground(Color.white);
        strokeTxt.setEditable(false);
        strokeTxt.setBackground(Color.DARK_GRAY);
        strokeTxt.setPreferredSize(new Dimension(50,30));
        setStroke = new JButton("Change");
        setStroke.addActionListener(this);
        String[] size = new String[]{"1","5","10","15"};
        strokeSize = new JComboBox<>(size);
        strokeSize.setEditable(true);

        eraserBtn = new JButton(eraserIcon);
        eraserBtn.addActionListener(this);

        saveBtn = new JButton(saveIcon);
        saveBtn.addActionListener(this);

        undoBtn = new JButton("Undo");
        undoBtn.addActionListener(this);
        redoBtn = new JButton("Redo");
        redoBtn.addActionListener(this);

        ShapeButtons shapeButtons = new ShapeButtons(paintingPanel);

        databtn = new JButton("Data");
        databtn.addActionListener(this);

        topPanel = new JPanel();
        topPanel.setBackground(Color.darkGray);
        topPanel.setLayout(new FlowLayout());
        topPanel.add(saveBtn);
        topPanel.add(clearBtn);
        topPanel.add(eraserBtn);
        topPanel.add(strokeTxt);
        topPanel.add(strokeSize);
        topPanel.add(setStroke);
        topPanel.add(undoBtn);
        topPanel.add(databtn);
        topPanel.add(shapeButtons);

        container.add(topPanel, BorderLayout.NORTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == clearBtn) {
            paintingPanel.clearDrawing();
        } else if (e.getSource() == selectColorBtn) {
            Color color = JColorChooser.showDialog(this, "Pick a Color", Color.black);
            paintingPanel.setChosenColor(color);
        } else if (e.getSource() == setStroke){
            String value = (String) strokeSize.getSelectedItem();
            assert value != null;
            try {
                paintingPanel.changeStroke(Integer.parseInt(value));
            } catch(NumberFormatException notANum){
                JOptionPane.showMessageDialog(this, "Error: Not a valid input, enter an integer");
            }
        } else if (e.getSource() == eraserBtn) {
            paintingPanel.setWhite();
        } else if (e.getSource() == saveBtn){
            paintingPanel.saveImg();
            JOptionPane.showMessageDialog(this, "Image has been saved successfully", null, JOptionPane.INFORMATION_MESSAGE, saveIcon);
        } else if (e.getSource() == undoBtn){
            try {
                BufferedImage bImg = paintingPanel.drawnItems.pop();
                paintingPanel.undoItems.push(bImg);
                paintingPanel.loadState(bImg);
            } catch(IndexOutOfBoundsException | EmptyStackException stackErr){
                JOptionPane.showMessageDialog(this, "Error: Nothing left to undo");
            }
        } else if (e.getSource() == redoBtn){
            try {
                BufferedImage bImg = paintingPanel.undoItems.pop();
                paintingPanel.loadState(bImg);
            } catch(IndexOutOfBoundsException | EmptyStackException stackErr) {
                JOptionPane.showMessageDialog(this, "Error: You have to undo something first inorder to redo it");
            }
        } else if (e.getSource() == databtn){
            BufferedImage bImg = paintingPanel.drawnItems.pop();
            paintingPanel.drawnItems.push(bImg);
            ColorData colorData = new ColorData(bImg);
        }
    }
}