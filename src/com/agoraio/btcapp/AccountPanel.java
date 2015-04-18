/**
 * 
 */
package com.agoraio.btcapp;

import java.awt.Color;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.Timer;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class AccountPanel extends JPanel {
	int capacity;
	JTextPane textPane;
	StyledDocument doc;
	Style style;

	TextArea accountArea;
	UserInfo userInfo = null;

	public AccountPanel() {
		super();
		capacity = 10;
		try {
			init();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public AccountPanel(int capacity) {
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
		style = textPane.addStyle("AccountPanel", null);
		add(textPane);
		this.setBackground(Color.black);
		this.setPreferredSize(getMinimumSize());
		userInfo = new UserInfo();
		textPane.setAlignmentX(LEFT_ALIGNMENT);
		textPane.setBackground(Color.black);

		// setSize(1024, 768);
		setVisible(true);

		Timer timer = new Timer(10000, new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				updateContent();
			}

		});
		timer.start();
	}

	int count = 0;

	public void updateContent() {

		userInfo.getData();
		userInfo.getData1();
		userInfo.getData2();

		// UserInfo.getData(convertData(OKCoinApiClient.testApi()));
		try {
			doc.remove(0, doc.getLength());
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			java.text.DecimalFormat df = new java.text.DecimalFormat("#.##");
			java.text.DecimalFormat df1 = new java.text.DecimalFormat("#.####");

			StyleConstants.setForeground(style, Color.white);
			doc.insertString(doc.getLength(), "Type\tValue\n", style);

			doc.insertString(doc.getLength(),
					"BTC:\t" + df1.format(userInfo.btcAmount) + "\n", style);
			doc.insertString(doc.getLength(),
					"LTC:\t" + df1.format(userInfo.ltcAmount) + "\n", style);
			doc.insertString(doc.getLength(),
					"CNY:\t" + df.format(userInfo.cnyAmount) + "\n", style);
			double total = (userInfo.btcAmount * userInfo.btcSellValue)
					+ (userInfo.ltcAmount * userInfo.ltcSellValue)
					+ userInfo.cnyAmount;

			doc.insertString(doc.getLength(), "TOTAL:\t" + df.format(total)
					+ "\n", style);

		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public static void main(String args[]) {
		JFrame frame = new JFrame("UserInfo");
		frame.addWindowListener(new MyWindowAdapter());
		AccountPanel account = new AccountPanel(10);
		System.out.println();
		frame.add(account);
		frame.setSize(1024, 768);
		frame.setVisible(true);

	}
}