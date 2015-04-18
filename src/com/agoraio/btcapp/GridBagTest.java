package com.agoraio.btcapp;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GridBagTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame f = new JFrame();

		CandleStickPanel candle = new CandleStickPanel(96, 5, 15);
		f.add(candle.panel);
		// JPanel panel = new JPanel();
		DTPanel dtPanel = new DTPanel();
		f.add(dtPanel);

		dtPanel.setBackground(Color.BLACK);

		JPanel debug = new JPanel();
		debug.setBackground(Color.white);
		f.add(debug);

		AccountPanel account = new AccountPanel();
		f.add(account);

		GridBagLayout layout = new GridBagLayout();
		f.setLayout(layout);

		GridBagConstraints s = new GridBagConstraints();
		s.fill = GridBagConstraints.BOTH;
		s.gridwidth = 10;
		s.weightx = 1;
		s.weighty = 1;
		layout.setConstraints(candle.panel, s);
		s.gridwidth = 0;
		s.weightx = 0.5;
		s.weighty = 1;
		layout.setConstraints(dtPanel, s);
		s.gridwidth = 10;
		s.gridheight = 3;
		s.weightx = 0;
		s.weighty = 0;
		layout.setConstraints(debug, s);
		s.gridwidth = 0;
		s.weightx = 1;
		s.weighty = 1;
		layout.setConstraints(account, s);
		f.setSize(1024, 768);

		f.setVisible(true);
	}

}
