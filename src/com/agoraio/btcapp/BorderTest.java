package com.agoraio.btcapp;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class BorderTest {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Application");
		frame.setLayout(new BorderLayout());

		CandleStickPanel candle = new CandleStickPanel(96, 5, 5);
		frame.add("Center", candle.panel);
		JPanel east = new JPanel();
		east.setLayout(new BorderLayout());
		east.add("North", new TradePanel());
		east.add("Center", new DepthPanel());

		frame.add("East", east);

		JPanel south = new JPanel();

		south.setLayout(new BorderLayout());
		south.add("East", new AccountPanel());
		south.add("Center", new JPanel());
		frame.add("South", south);

		frame.setSize(1024, 768);
		frame.setVisible(true);
	}
}
