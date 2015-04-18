/**
 * 
 */
package com.agoraio.btcapp;

import java.awt.Color;
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

public class DepthPanel extends JPanel {

	int capacity;

	JTextPane textPane;
	StyledDocument doc;
	Style style;
	DepthDataArray dda = null;

	public DepthPanel(int capacity) {
		super();
		this.capacity = capacity;
		try {
			init();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public DepthPanel() {
		super();
		this.capacity = 10;
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
		style = textPane.addStyle("DepthStyle", null);
		textPane.setAlignmentX(RIGHT_ALIGNMENT);
		this.setAlignmentX(RIGHT_ALIGNMENT);
		// this.setPreferredSize(this.getMinimumSize());
		this.setBackground(Color.BLACK);
		add(textPane);

		textPane.setBackground(Color.black);

		setVisible(true);
		dda = new DepthDataArray(capacity, "https://www.okcoin.cn/api/depth.do");
		Timer timer = new Timer(2000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateContent();
			}

		});
		timer.start();
	}

	public void updateContent() {
		dda.getData();
		String a = dda.getAsksData();
		String b = dda.getBidsData();
		String[] aa = a.split("\n");
		String[] bb = b.split("\n");
		try {
			String temp = "Depth\n";
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

	}

	public static void main(String args[]) {

		JFrame frame = new JFrame("Application");
		frame.addWindowListener(new MyWindowAdapter());
		DepthPanel depth = new DepthPanel();

		frame.add(depth);
		frame.setSize(1024, 768);
		frame.setVisible(true);

	}
}