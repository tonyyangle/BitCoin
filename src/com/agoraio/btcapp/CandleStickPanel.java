package com.agoraio.btcapp;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.DefaultHighLowDataset;
import org.jfree.data.xy.XYDataset;

/**
 * @author yangkklt
 * 
 */

public class CandleStickPanel {

	DefaultHighLowDataset dataset = null;
	TimeSeries closePriceDataset = new TimeSeries("");

	JFreeChart candleChart = null;
	JFreeChart closePriceChart = null;
	TimeSeries series = new TimeSeries("XYGraph");
	public ChartPanel panel = null;
	XYPlot plot = null;
	int size = 0;
	int period1 = 0;
	int period2 = 0;
	XYDataset dataset2 = null;

	public CandleStickPanel(int limit, int period1, int period2) {
		size = limit;
		this.period1 = period1;
		this.period2 = period2;
		dataset = DemoDatasetFactory.createHighLowDataset(limit);
		candleChart = createChart(dataset);
		candleChart.setBackgroundPaint(Color.black);
		TextTitle textTitle = new TextTitle("CandleStick", new Font("System",
				Font.BOLD, 20));
		textTitle.setPaint(Color.white);
		candleChart.setTitle(textTitle);
		panel = new ChartPanel(candleChart);
		candleChart.getXYPlot().setOrientation(PlotOrientation.VERTICAL);

		panel.setMouseWheelEnabled(true);
		panel.setHorizontalAxisTrace(true);
		panel.setVerticalAxisTrace(true);
		panel.setAutoscrolls(true);
		panel.setFocusable(true);
		panel.setZoomAroundAnchor(true);
		panel.setBackground(Color.BLACK);
	}

	public JFreeChart createChart(DefaultHighLowDataset dataset) {
		JFreeChart jfc = ChartFactory.createCandlestickChart("Candlestick",
				"Date", "Price", dataset, true);

		// candleChart.setBackgroundPaint(Color.BLACK);
		TimeSeries s1 = new TimeSeries("MAVG-CLOSE: " + period1 + " MITUTES");
		TimeSeries s2 = new TimeSeries("MAVG-CLOSE: " + period2 + " MITUTES");

		for (int i = period1 - 1; i < size; i++) {
			double value1 = 0;
			for (int j = i - period1 + 1; j <= i; j++) {
				value1 += dataset.getClose(0, j).doubleValue();
			}

			value1 /= period1;
			s1.add(new Minute(dataset.getXDate(0, i)), value1);

		}

		for (int i = period2 - 1; i < size; i++) {
			double value1 = 0;
			for (int j = i - period2 + 1; j <= i; j++) {
				value1 += dataset.getClose(0, j).doubleValue();
			}
			value1 /= period2;
			s2.add(new Minute(dataset.getXDate(0, i)), value1);

		}

		TimeSeriesCollection dataset3 = new TimeSeriesCollection();
		dataset3.addSeries(s1);
		dataset3.addSeries(s2);
		jfc.getXYPlot().getRangeAxis().setRange(3500, 4000);
		jfc.setBorderVisible(true);
		jfc.setTitle("CandleStick123");
		plot = (XYPlot) jfc.getPlot();
		plot.getDomainAxis(0).setAxisLinePaint(Color.white);
		plot.setDomainGridlinePaint(Color.white);
		plot.setDomainCrosshairPaint(Color.white);
		plot.setDomainMinorGridlinePaint(Color.white);
		plot.setBackgroundPaint(Color.BLACK);
		plot.setDomainGridlinePaint(Color.white);
		plot.setDataset(1, dataset3);
		plot.setRenderer(1, new StandardXYItemRenderer());
		plot.setOutlinePaint(Color.white);
		plot.setDomainCrosshairPaint(Color.white);
		XYItemRenderer renderer1 = plot.getRenderer(1);
		// renderer1.setSeriesItemLabelFont(0, new Font());
		renderer1.setSeriesPaint(0, Color.white);
		renderer1.setSeriesPaint(1, Color.cyan);

		plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);

		return jfc;
	}

	/**
	 * @param args
	 * @throws InterruptedException
	 */

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("Application");

		CandleStickPanel candle = new CandleStickPanel(96 * 3, 5, 15);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {

				System.exit(0);
			}
		});
		frame.setBackground(Color.black);
		frame.add(candle.panel);
		frame.setVisible(true);
		frame.setSize(1024, 768);

	}

}
