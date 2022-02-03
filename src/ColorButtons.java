import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ColorButtons extends JPanel {

    public JButton selectColor;

    ColorButtons(PaintingPanel paintingPanel, Container container){
        setLayout(new GridLayout(10, 2));

        selectColor = new JButton("Select Color");
        selectColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == selectColor){
                    Color color = JColorChooser.showDialog(container, "Pick a Color", Color.black);
                    paintingPanel.setChosenColor(color);
                    JButton newButton = new JButton();
                    newButton.setBackground(color);
                    newButton.setOpaque(true);
                    newButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            paintingPanel.setChosenColor(color);
                        }
                    });
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            add(newButton);
                            revalidate();
                            repaint();
                        }
                    });
                }
            }
        });
        add(selectColor);
    }
}
