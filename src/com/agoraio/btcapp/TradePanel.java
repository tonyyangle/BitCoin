/**
 * 
 */
package com.agoraio.btcapp;

import java.awt.Color;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.Timer;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class TradePanel extends JPanel {
	int capacity;
	JTextPane textPane;
	StyledDocument doc;
	Style style;
	private java.text.DecimalFormat df = new java.text.DecimalFormat("#.###");
	TextArea askArea;
	TextArea bidArea;
	ArrayList<TradeEntry> tradeEntry = new ArrayList<TradeEntry>();
	DepthDataArray dda = null;

	public TradePanel() {
		super();
		capacity = 10;
		try {
			init();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public TradePanel(int capacity) {
		super();
		this.capacity = capacity;

		try {
			init();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void init() throws InterruptedException {
		textPane = new JTextPane();
		doc = textPane.getStyledDocument();
		style = textPane.addStyle("TradePanel", null);
		textPane.setAlignmentX(RIGHT_ALIGNMENT);
		this.setAlignmentX(RIGHT_ALIGNMENT);
		add(textPane);
		for (int i = 0; i < capacity; i++) {
			tradeEntry.add(new TradeEntry());
		}
		textPane.setAlignmentX(LEFT_ALIGNMENT);
		textPane.setBackground(Color.BLACK);
		this.setBackground(Color.BLACK);
		setSize(1024, 768);
		setVisible(true);

		Timer timer = new Timer(1000, new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				updateContent();
			}

		});
		timer.start();
	}

	int count = 0;

	public void updateContent() {

		DataConnector.getTradeData("https://www.okcoin.cn/api/trades.do",
				tradeEntry, capacity);
		try {
			doc.remove(0, doc.getLength());
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			StyleConstants.setForeground(style, Color.white);
			doc.insertString(doc.getLength(), "Trade\n", style);

		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		for (int i = 0; i < capacity; i++) {
			String temp = "";
			temp = tradeEntry.get(i).price + "\t"
					+ df.format(tradeEntry.get(i).amount) + "\n";

			if (tradeEntry.get(i).isBuy) {
				try {// ask red
					StyleConstants.setForeground(style, Color.green);
					doc.insertString(doc.getLength(), temp, style);
				} catch (BadLocationException e) {
				}
			} else {
				try {
					StyleConstants.setForeground(style, Color.red);
					doc.insertString(doc.getLength(), temp, style);

				} catch (BadLocationException e) {
				}
			}

		}

	}

	public static void main(String args[]) {
		JFrame frame = new JFrame("Application");
		frame.addWindowListener(new MyWindowAdapter());
		TradePanel trade = new TradePanel(10);

		frame.add(trade);
		frame.setSize(1024, 768);
		frame.setVisible(true);

	}
}