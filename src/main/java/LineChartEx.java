import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.io.*;

public class LineChartEx extends JFrame {
    public XYDataset dataset;

    public LineChartEx() {

        //initUI();
    }

    public void initUI() {

        //XYDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        add(chartPanel);

        pack();
        setTitle("Line chart");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public XYDataset createDataset(String sFile, String sKey) {
        XYSeries series = new XYSeries(sKey);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(sFile)))) {
            String sB;

            while ((sB = br.readLine()) != null) {
                String [] sP = sB.split(":");
                String sLock = sP[0].substring(0,4);
                if (!sLock.equals(sKey))
                    continue;
                System.out.println(sB);
                System.out.println(sLock);
                series.add(Double.parseDouble(sP[0].substring(4)), Double.parseDouble(sP[1]));

            }
        } catch(IOException ex) {
            System.out.println(ex);
            return null;
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        return dataset;
    }

    public XYDataset createDataset() {
        XYSeries series = new XYSeries("2016");
        series.add(18, 567);
        series.add(20, 612);
        series.add(25, 800);
        series.add(30, 980);
        series.add(40, 1410);
        series.add(50, 2350);

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        return dataset;
    }

    private JFreeChart createChart(XYDataset dataset) {
        JFreeChart chart = ChartFactory.createXYLineChart(
                "EKP personalization",
                "Months",
                "Card Quantity",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);

        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

        chart.getLegend().setFrame(BlockBorder.NONE);

        chart.setTitle(new TextTitle("EKP personalization",
                        new Font("Serif", java.awt.Font.BOLD, 18)
                )
        );

        return chart;
    }

    public static void main(String[] args) {
        String fileName = "E:\\Java\\IdeaProjectsRosan\\TstJFreeChart\\data\\data.txt";

        EventQueue.invokeLater(() -> {

            LineChartEx ex = new LineChartEx();
            if((ex.dataset = ex.createDataset(fileName, "2019")) == null)
                return;
            ex.initUI();
            ex.setVisible(true);
        });
    }
}
