import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/***
 * @author goatdidi
 **/
public class Picture {
    public  JFreeChart createChart(CategoryDataset categoryDataset) {
        // 创建JFreeChart对象：ChartFactory.createLineChart
        JFreeChart jfreechart = ChartFactory.createLineChart("随机二进制树仿真", // 标题
                "识别次数", // categoryAxisLabel （category轴，横轴，X轴标签）
                "识别标签数量", // valueAxisLabel（value轴，纵轴，Y轴的标签）
                categoryDataset, // dataset
                PlotOrientation.VERTICAL, true, // legend
                false, // tooltips
                false); // URLs
        // 使用CategoryPlot设置各种参数。以下设置可以省略。
        CategoryPlot plot = (CategoryPlot)jfreechart.getPlot();
        // 背景色 透明度
        plot.setBackgroundAlpha(0.5f);
        // 前景色 透明度
        plot.setForegroundAlpha(0.5f);
        // 其他设置 参考 CategoryPlot类
        LineAndShapeRenderer renderer = (LineAndShapeRenderer)plot.getRenderer();
        renderer.setBaseShapesVisible(true); // series 点（即数据点）可见
        renderer.setBaseLinesVisible(true); // series 点（即数据点）间有连线可见
        renderer.setUseSeriesOffset(true); // 设置偏移量
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setBaseItemLabelsVisible(true);
        return jfreechart;
    }

    public CategoryDataset createDataset(ArrayList<Integer> loads, ArrayList<Integer> trough_put){
        DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
        for (int i = 0;i<loads.size();i++){
            defaultcategorydataset.addValue(trough_put.get(i),"experiment",loads.get(i));
        }
        return defaultcategorydataset;


    }
    public  void saveAsFile(JFreeChart chart, String outputPath, int weight, int height){
        FileOutputStream out = null;
        try {
            File outFile = new File(outputPath);
            if (!outFile.getParentFile().exists()) {
                outFile.getParentFile().mkdirs();
            }
            out = new FileOutputStream(outputPath);

            // 保存为JPEG
            ChartUtilities.writeChartAsJPEG(out, chart, 600, 400);
            out.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }{
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    // do nothing
                }
            }
        }



    }
}
