import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Iterator;

public class ImageIterator implements Iterable<Color> {
    private BufferedImage bImg;

    public ImageIterator(BufferedImage bImg){
        this.bImg = bImg;
    }

    @Override
    public Iterator<Color> iterator() {
        return new colorIterator();
    }

    private final class colorIterator implements Iterator<Color> {
        private int x = 0;
        private int y = 0;

        @Override
        public boolean hasNext() {
            return  y < bImg.getHeight() - 1;
        }

        @Override
        public Color next() {
            x += 1;
            if ( x >= bImg.getWidth()) {
                x = 1;
                y ++;
            }
            final Color color = new Color(bImg.getRGB(x, y));
            return color;
        }
    }
}
