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

/**
 * @author yangkklt
 * 
 */
public class DTPanel extends JPanel {
	private java.text.DecimalFormat df = new java.text.DecimalFormat("#.###");
	private int capacity;
	JTextPane textPane;
	StyledDocument doc;
	Style style;
	TextArea askArea;
	TextArea bidArea;
	ArrayList<TradeEntry> tradeEntry = new ArrayList<TradeEntry>();
	DepthDataArray dda = null;

	public DTPanel() {
		super();
		this.capacity = 10;
		try {
			init();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public DTPanel(int capacity) {
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
		dda = new DepthDataArray(capacity, "https://www.okcoin.cn/api/depth.do");
		textPane = new JTextPane();
		doc = textPane.getStyledDocument();
		style = textPane.addStyle("TradePanel", null);
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

	public void updateContent() {
		DataConnector.getTradeData("https://www.okcoin.cn/api/trades.do",
				tradeEntry, capacity);
		dda.getData();
		String a = dda.getAsksData();
		String b = dda.getBidsData();
		String[] aa = a.split("\n");
		String[] bb = b.split("\n");

		try {
			doc.remove(0, doc.getLength());
		} catch (BadLocationException e) {
			e.printStackTrace();
		}

		StyleConstants.setForeground(style, Color.white);

		try {
			doc.insertString(doc.getLength(), "Depth" + "\n", style);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		StyleConstants.setForeground(style, Color.red);
		for (int i = 0; i < capacity; i++) {

			try {
				doc.insertString(doc.getLength(), aa[i] + "\n", style);
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		StyleConstants.setForeground(style, Color.green);
		for (int i = 0; i < capacity; i++) {

			try {
				doc.insertString(doc.getLength(), bb[i] + "\n", style);

			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			StyleConstants.setForeground(style, Color.white);
			doc.insertString(doc.getLength(), "\n", style);
			
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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame();
		
		DTPanel panel = new DTPanel();
		frame.add(panel);
		frame.setVisible(true);
		frame.setSize(1024, 768);
	}

}
