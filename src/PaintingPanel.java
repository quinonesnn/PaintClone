import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Stack;

public class PaintingPanel extends JComponent {

    public Stack<BufferedImage> drawnItems = new Stack<>();
    public Stack<BufferedImage> undoItems = new Stack<>();

    private Image image;

    private Graphics2D g2D;

    private int x = 0;
    private int y = 0;
    private int lastx = -10;
    private int lasty = -10;

    public boolean addingShape = false;

    public int typeOfShape = 0;

    private int numberOfSaves = 1;


    public PaintingPanel() {
        setDoubleBuffered(false);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                lastx = e.getX();
                lasty = e.getY();
                saveState();
            }
        });
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                x = e.getX();
                y = e.getY();

                if(!addingShape){
                    if (g2D != null) {
                        g2D.drawLine(lastx, lasty, x, y);
                        repaint();
                        lastx = x;
                        lasty = y;
                    }
                }

            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                x = e.getX();
                y = e.getY();

                int resultX = Math.abs(lastx - x);
                int resultY = Math.abs(lasty - y);

                if(addingShape){
                    addShape(typeOfShape, lastx, lasty, x,y,resultX, resultY);
                }
            }
        });
    }

    protected void paintComponent(Graphics graphics){
        if (image == null) {
            image = createImage(getSize().width, getSize().height);
            g2D = (Graphics2D) image.getGraphics();
            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            clearDrawing();
        }

        graphics.drawImage(image, 0, 0, null);
    }

    public void clearDrawing(){
        g2D.setPaint(Color.white);
        g2D.fillRect(0,0, getSize().width, getSize().height);
        g2D.setPaint(Color.black);
        repaint();
    }

    public void setWhite(){
        g2D.setPaint(Color.white);
    }

    public void setChosenColor(Color color){
        g2D.setPaint(color);
    }

    public void changeStroke(int num){
        g2D.setStroke(new BasicStroke(num));
    }

    public void saveImg(){
        //https://stackoverflow.com/questions/8202253/saving-a-java-2d-graphics-image-as-png-file
        BufferedImage bImg = new BufferedImage(getSize().width, getSize().height, BufferedImage.TYPE_INT_RGB);
        Graphics2D cg = bImg.createGraphics();
        paintAll(cg);
        try {
            ImageIO.write(bImg, "png", new File("./output_image" + numberOfSaves + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        numberOfSaves++;
    }
    public void saveState(){
        BufferedImage bImg = new BufferedImage(getSize().width, getSize().height, BufferedImage.TYPE_INT_RGB);
        Graphics2D cg = bImg.createGraphics();
        paintAll(cg);
        drawnItems.push(bImg);
    }

    public void loadState(BufferedImage image){
        BufferedImage bImg = image;
        clearDrawing();
        g2D.drawImage(bImg, null,0,0);
        repaint();
    }

    public void addShape(int typeOfShape, int x, int y, int x2, int y2, int width, int height){
        switch (typeOfShape) {
            case 1: Polygon squarePoly = new Polygon();
                    squarePoly.addPoint(x,y);
                    squarePoly.addPoint(x2,y);
                    squarePoly.addPoint(x2,y2);
                    squarePoly.addPoint(x,y2);
                    g2D.drawPolygon(squarePoly);
                    repaint();
                    addingShape = false;
                    break;
            case 2: g2D.drawOval(x, y, width, height);
                    repaint();
                    addingShape = false;
                    break;
            case 3: Polygon trianglePoly = new Polygon();
                    trianglePoly.addPoint(x,y2);
                    trianglePoly.addPoint(x2,y2);
                    trianglePoly.addPoint((int) Math.ceil((x + x2)/2), y);
                    g2D.drawPolygon(trianglePoly);
                    repaint();
                    addingShape = false;
                    break;
            case 4: Polygon filledSq = new Polygon();
                    filledSq.addPoint(x,y);
                    filledSq.addPoint(x2,y);
                    filledSq.addPoint(x2,y2);
                    filledSq.addPoint(x,y2);
                    g2D.fillPolygon(filledSq);
                    repaint();
                    addingShape = false;
                    break;
            case 5: g2D.fillOval(x, y, width, height);
                    repaint();
                    addingShape = false;
                    break;
            case 6: Polygon filledTri = new Polygon();
                    filledTri.addPoint(x,y2);
                    filledTri.addPoint(x2,y2);
                    filledTri.addPoint((int) Math.ceil((x + x2)/2), y);
                    g2D.fillPolygon(filledTri);
                    repaint();
                    addingShape = false;
                    break;
        }

    }
}
