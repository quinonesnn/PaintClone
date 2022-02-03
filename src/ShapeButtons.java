import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ShapeButtons extends JPanel implements ActionListener {

    Icon filledCircleIcon = new ImageIcon(new ImageIcon("res/filledCircle.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
    Icon filledSquareIcon = new ImageIcon(new ImageIcon("res/filledSquare.png").getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
    Icon filledTriangleIcon = new ImageIcon(new ImageIcon("res/filledTriangle.png").getImage().getScaledInstance(35, 35, Image.SCALE_DEFAULT));
    Icon outlineCircleIcon = new ImageIcon(new ImageIcon("res/outlineCircle.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
    Icon outlineSquareIcon = new ImageIcon(new ImageIcon("res/outlineSquare.png").getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
    Icon outlineTriangleIcon = new ImageIcon(new ImageIcon("res/outlineTriangle.png").getImage().getScaledInstance(35, 35, Image.SCALE_DEFAULT));
    Icon crossArrows = new ImageIcon(new ImageIcon("res/cross-arrows.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
    Icon up = new ImageIcon(new ImageIcon("res/chevron-up.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
    Icon down = new ImageIcon(new ImageIcon("res/chevron-down.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));

    CardLayout card = new CardLayout();

    JPanel outlineShapes;
    JPanel filledShapes;

    JButton switchFilled;
    JButton switchOutline;

    JButton outSquare;
    JButton outCircle;
    JButton outTriangle;

    JButton filledSquare;
    JButton filledCircle;
    JButton filledTriangle;

    boolean alreadyInformed = false;

    ActionListener actionListener;

    ShapeButtons(PaintingPanel paintingPanel) {
        actionListener = e -> {
            if(!alreadyInformed){
                JOptionPane.showMessageDialog(paintingPanel, "Draw shape by clicking and dragging diagonally", null, JOptionPane.INFORMATION_MESSAGE, crossArrows);
                alreadyInformed = true;
            }
            if (e.getSource() == outSquare) {
                paintingPanel.addingShape = true;
                paintingPanel.typeOfShape = 1;
            }
            if (e.getSource() == outCircle) {
                paintingPanel.addingShape = true;
                paintingPanel.typeOfShape = 2;
            }
            if (e.getSource() == outTriangle) {
                paintingPanel.addingShape = true;
                paintingPanel.typeOfShape = 3;
            }
            if (e.getSource() == filledSquare){
                paintingPanel.addingShape = true;
                paintingPanel.typeOfShape = 4;
            }
            if (e.getSource() == filledCircle){
                paintingPanel.addingShape = true;
                paintingPanel.typeOfShape = 5;
            }
            if (e.getSource() == filledTriangle){
                paintingPanel.addingShape = true;
                paintingPanel.typeOfShape = 6;
            }
        };
        setDoubleBuffered(false);
        setPreferredSize(new Dimension(200, 50));
        setLayout(card);

        outlineShapes = new JPanel();
        outlineShapes.setBackground(Color.lightGray);
        outlineShapes.setLayout(new FlowLayout(FlowLayout.CENTER,1,1));
        filledShapes = new JPanel();
        filledShapes.setBackground(Color.lightGray);
        filledShapes.setLayout(new FlowLayout(FlowLayout.CENTER,1,1));

        switchFilled = new JButton(up);
        switchFilled.addActionListener(this);
        switchOutline = new JButton(down);
        switchOutline.addActionListener(this);

        outSquare = new JButton(outlineSquareIcon);
        outSquare.setPreferredSize(new Dimension(50,50));
        outSquare.addActionListener(actionListener);
        outCircle = new JButton(outlineCircleIcon);
        outCircle.setPreferredSize(new Dimension(50,50));
        outCircle.addActionListener(actionListener);
        outTriangle = new JButton(outlineTriangleIcon);
        outTriangle.setPreferredSize(new Dimension(50,50));
        outTriangle.addActionListener(actionListener);

        filledSquare = new JButton(filledSquareIcon);
        filledSquare.setPreferredSize(new Dimension(50,50));
        filledSquare.addActionListener(actionListener);
        filledCircle = new JButton(filledCircleIcon);
        filledCircle.setPreferredSize(new Dimension(50,50));
        filledCircle.addActionListener(actionListener);
        filledTriangle = new JButton(filledTriangleIcon);
        filledTriangle.setPreferredSize(new Dimension(50,50));
        filledTriangle.addActionListener(actionListener);

        outlineShapes.add(outSquare);
        outlineShapes.add(outCircle);
        outlineShapes.add(outTriangle);
        outlineShapes.add(switchFilled);

        filledShapes.add(filledSquare);
        filledShapes.add(filledCircle);
        filledShapes.add(filledTriangle);
        filledShapes.add(switchOutline);

        add(outlineShapes, "1");
        add(filledShapes, "2");

        card.show(this, "1");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == switchFilled) {
            card.show(this, "2");
        } else if (e.getSource() == switchOutline) {
            card.show(this, "1");
        }
    }
}

