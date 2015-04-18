package com.agoraio.btcapp;

import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;

import org.jfree.data.xy.DefaultHighLowDataset;

public class DemoDatasetFactory {

	public static double[] high;
	public static double[] low;
	public static double[] open;
	public static double[] close;

	public static DefaultHighLowDataset createHighLowDataset(int limit) {
		// TODO Auto-generated method stub
		Date[] date = new Date[limit];
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.set(2000, 2, 2);

		high = new double[limit];
		low = new double[limit];
		open = new double[limit];
		close = new double[limit];
		double[] volume = new double[limit];

		QuarterDatabase.getData(date, open, close, high, low, limit);
		Arrays.fill(volume, 0);
		DefaultHighLowDataset dataset = new DefaultHighLowDataset("BTC", date,
				high, low, open, close, volume);
		return dataset;

	}

}