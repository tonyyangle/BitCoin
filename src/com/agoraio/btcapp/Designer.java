package com.agoraio.btcapp;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;

public class Designer {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Designer window = new Designer();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Designer() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		//frame.setBounds(100, 100, 890, 555);
		frame.setSize(1024, 768);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBackground(Color.black);
		JMenuBar MainMenu = new JMenuBar();
		frame.setJMenuBar(MainMenu);

		JMenu mnFile = new JMenu("File");
		MainMenu.add(mnFile);

		JMenu mnEdit = new JMenu("Edit");
		MainMenu.add(mnEdit);

		CandleStickPanel candle = new CandleStickPanel(96 * 3, 5, 15);
		candle.panel.setBackground(Color.WHITE);
		candle.panel.setForeground(Color.BLACK);


		DepthPanel depth = new DepthPanel();
		depth.setBackground(Color.BLACK);
		depth.setForeground(Color.BLACK);		
		depth.setSize(100,100);
		
		TradePanel trade = new TradePanel();
		trade.setBackground(Color.BLACK);
		trade.setForeground(Color.BLACK);

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.GRAY);
		panel_3.setForeground(Color.BLACK);

		JPanel panel_4 = new JPanel();
		
		panel_4.setBackground(Color.BLUE);
		panel_4.setForeground(Color.BLACK);
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout
				.setHorizontalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																candle.panel,
																GroupLayout.DEFAULT_SIZE,
																751,
																Short.MAX_VALUE)
														.addComponent(
																panel_3,
																GroupLayout.DEFAULT_SIZE,
																751,
																Short.MAX_VALUE))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.TRAILING)
														.addComponent(
																panel_4,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(
																trade,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(
																depth,
																GroupLayout.DEFAULT_SIZE,
																97,
																Short.MAX_VALUE))
										.addContainerGap()));
		groupLayout
				.setVerticalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.TRAILING)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addComponent(
																				depth,
																				GroupLayout.DEFAULT_SIZE,
																				213,
																				Short.MAX_VALUE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				trade,
																				GroupLayout.DEFAULT_SIZE,
																				109,
																				Short.MAX_VALUE))
														.addComponent(
																candle.panel,
																GroupLayout.DEFAULT_SIZE,
																328,
																Short.MAX_VALUE))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																panel_3,
																GroupLayout.DEFAULT_SIZE,
																89,
																Short.MAX_VALUE)
														.addComponent(
																panel_4,
																GroupLayout.DEFAULT_SIZE,
																89,
																Short.MAX_VALUE))));
		depth.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblDepth = new JLabel("Depth");
		depth.add(lblDepth);

		JLabel lblAccount = new JLabel("Account");
		panel_4.add(lblAccount);

		JLabel lblDebug = new JLabel("Debug");
		panel_3.add(lblDebug);

		JLabel lblTrade = new JLabel("Trade");
		trade.add(lblTrade);

		JLabel lblGraph = new JLabel("Graph");
		GroupLayout gl_imagePanel = new GroupLayout(candle.panel);
		gl_imagePanel.setHorizontalGroup(gl_imagePanel.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_imagePanel.createSequentialGroup().addGap(186)
						.addComponent(lblGraph)));
		gl_imagePanel.setVerticalGroup(gl_imagePanel.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_imagePanel.createSequentialGroup().addGap(5)
						.addComponent(lblGraph)));
		candle.panel.setLayout(gl_imagePanel);
		frame.getContentPane().setLayout(groupLayout);

	}
}
