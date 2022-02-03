import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.stream.Collectors;

public class ColorData extends JFrame {

    HashMap<String, Integer> colorOccurrences;
    int occurrences = 0;

    ColorData(BufferedImage bImg){
        setSize(400,400);
        setLocation(600,300);
        colorOccurrences = new HashMap<>();
        ImageIterator imgItr = new ImageIterator(bImg);
        ColorNames name = new ColorNames();
        for(Color color : imgItr){
            String colorName = name.getColorNameFromColor(color);
            if (!colorName.equals("White") ){
                colorOccurrences.put(colorName, occurrences + 1);
                occurrences = colorOccurrences.get(colorName);
            }
        }

        Map<String, Integer> sortedMap = colorOccurrences.entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        CategoryDataset data = createData(sortedMap);

        ChartPanel chartPanel = new ChartPanel(createChart(data));

        setLayout(new BorderLayout());

        add(chartPanel, BorderLayout.CENTER);

        revalidate();
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private JFreeChart createChart(CategoryDataset dataset){
        return ChartFactory.createBarChart("Most used colors in image","", "Color per pixel", dataset,
                                                            PlotOrientation.HORIZONTAL, false, true, false);
    }

    private CategoryDataset createData(Map<String, Integer> map){
        var dataSet = new DefaultCategoryDataset();
        for(String key : map.keySet()){
            dataSet.setValue(map.get(key), "Color per Pixel", key);
        }
        return dataSet;
    }

}
