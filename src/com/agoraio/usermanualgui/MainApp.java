package com.agoraio.usermanualgui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.agoraio.btcapp.OKCoinApiClient;
import com.agoraio.btcapp.OkCoinGetOrder;
import com.agoraio.btcapp.OkCoinGetPending;
import com.agoraio.btcapp.UserInfo;

public class MainApp extends JFrame {
	JButton jButton_buy = null;
	JButton jButton_sell = null;
	JButton jButton_order = null;
	JButton jButton_pending = null;
	JPanel panel_center = null;
	JPanel panel_south = null;
	JPanel panel_east = null;
	JTextArea textArea = null;
	UserInfo userInfo = null;
	JTextArea centerArea = null;

	JLabel priceLabel = new JLabel("price");
	JLabel amountLabel = new JLabel("amount");
	JTextField priceField = new JTextField();
	JTextField amountField = new JTextField();
	JRadioButton[] button = new JRadioButton[2];
	ButtonGroup bg = new ButtonGroup();

	JButton jButton_submit = null;
	boolean isMarket = true;

	public MainApp() {
		init();
		this.setSize(600, 600);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void init() {
		userInfo = new UserInfo();
		setTitle("Application");
		this.setLayout(new BorderLayout(10, 10));
		userInfo.getData();
		panel_center = new JPanel();
		panel_south = new JPanel();
		panel_east = new JPanel();
		panel_east.setVisible(true);
		panel_center.setBackground(Color.white);
		jButton_buy = new JButton("Buy");
		jButton_buy.addActionListener(new BuyHandler());
		jButton_sell = new JButton("Sell");
		jButton_sell.addActionListener(new SellHandler());
		jButton_order = new JButton("Order");
		jButton_order.addActionListener(new GetOrderHandler());
		jButton_pending = new JButton("Pending");
		jButton_pending.addActionListener(new GetPendingHandler());
		panel_south.add(jButton_buy);
		panel_south.add(jButton_sell);
		panel_south.add(jButton_order);
		panel_south.add(jButton_pending);
		textArea = new JTextArea();
		textArea.setEditable(false);
		panel_east.add(textArea);
		centerArea = new JTextArea();
		centerArea.setEditable(false);
		jButton_submit = new JButton("Submit");
		jButton_submit.addActionListener(new SubmitListenHandler());
		// jButton_submit.setPreferredSize(new Dimension(50,30));
		button[0] = new JRadioButton("Market");
		button[1] = new JRadioButton("Assigned");
		priceField.setPreferredSize(new Dimension(50, 30));
		amountField.setPreferredSize(new Dimension(50, 30));

		bg.add(button[0]);
		bg.add(button[1]);

		add("East", panel_east);
		add("Center", panel_center);
		add("South", panel_south);
		updateUserInfo();
		// Timer timer = new Timer(1000, new ActionListener() {
		//
		// public void actionPerformed(ActionEvent e) {
		// updateUserInfo();
		// }
		//
		// });
		// timer.start();
	}

	public void updateUserInfo() {
		userInfo.getData();
		java.text.DecimalFormat df = new java.text.DecimalFormat("#.##");
		java.text.DecimalFormat df1 = new java.text.DecimalFormat("#.####");

		String display = "Account Info:\n";
		display += "BTC:\t" + df1.format(userInfo.btcAmount) + "\n";
		display += "LTC:\t" + df1.format(userInfo.ltcAmount) + "\n";
		double total = (userInfo.btcAmount * userInfo.btcSellValue)
				+ (userInfo.ltcAmount * userInfo.ltcSellValue)
				+ userInfo.cnyAmount;
		display += "CNY:\t" + df.format(total) + "\n";
		textArea.setText(display);
	}

	class UserInfoHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			updateUserInfo();
		}
	}

	class GetOrderHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			panel_center.removeAll();
			panel_center.add(centerArea);
			repaint();
			String result = OkCoinGetOrder.testApi();

			String content = "";
			try {
				Timestamp timeStamp = null;
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm");
				Date date = null;

				JSONObject json = new JSONObject(result);
				JSONArray array = json.getJSONArray("orders");
				for (int i = 0; i < array.length(); i++) {
					json = array.getJSONObject(i);
					long time = json.getLong("createDate");
					timeStamp = new Timestamp(time);
					date = new Date(timeStamp.getTime());
					String temp = "Date: " + simpleDateFormat.format(date)
							+ " ";
					temp += "amount " + json.getDouble("amount") + " ";
					temp += "avg_rage " + json.getDouble("avg_rate") + " ";
					temp += "type " + json.getString("type") + "\n";
					content += temp;
				}
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			centerArea.setText(content);
		}
	}

	class GetPendingHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			panel_center.removeAll();
			panel_center.add(centerArea);
			repaint();

			String result = OkCoinGetPending.testApi();
			if (result.isEmpty()) {
				centerArea.setText("No pending orders.");
				return;
			}
			String content = "";
			try {
				Timestamp timeStamp = null;
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm");
				Date date = null;

				JSONObject json = new JSONObject(result);
				JSONArray array = json.getJSONArray("orders");
				for (int i = 0; i < array.length(); i++) {
					json = array.getJSONObject(i);
					long time = json.getLong("createDate");
					timeStamp = new Timestamp(time);
					date = new Date(timeStamp.getTime());
					String temp = "Date: " + simpleDateFormat.format(date)
							+ " ";
					temp += "amountField " + json.getDouble("amountField")
							+ " ";
					temp += "avg_rage " + json.getDouble("avg_rate") + " ";
					temp += "type " + json.getString("type") + "\n";
					content += temp;
				}

			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (content.equals("")) {
				centerArea.setText("No Pending orders\n");
				return;
			}
			centerArea.setText(content);
		}
	}

	class BuyHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			panel_center.removeAll();
			panel_center.revalidate();
			repaint();

			panel_center.add(button[0]);
			panel_center.add(button[1]);
			panel_center.add(priceLabel);
			panel_center.add(priceField);
			panel_center.add(amountLabel);
			panel_center.add(amountField);
			panel_center.add(jButton_submit);
			button[0].addActionListener(new ButtonListenHandler());
			button[1].addActionListener(new ButtonListenHandler());
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		}
	}

	class SellHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			panel_center.removeAll();
			panel_center.revalidate();
			repaint();

			panel_center.add(button[0]);
			panel_center.add(button[1]);
			panel_center.add(priceLabel);
			panel_center.add(priceField);
			panel_center.add(amountLabel);
			panel_center.add(amountField);
			panel_center.add(jButton_submit);
			button[0].addActionListener(new ButtonListenHandler());
			button[1].addActionListener(new ButtonListenHandler());
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		}
	}

	class ButtonListenHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < button.length; i++) {
				if (button[i].isSelected()) {
					if (i == 0)
						isMarket = true;
					else
						isMarket = false;
				}
			}
		}
	}

	class SubmitListenHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (isMarket)
				System.out.print("Market ");
			else
				System.out.print("Assigned ");
			System.out.println("amount = " + amountField.getText() + " price = "
					+ priceField.getText());
			OKCoinApiClient.testApi(amountField.getText(), priceField.getText());
			updateUserInfo();
		}
	}

	public static void main(String[] args) {
		MainApp mainApp = new MainApp();
	}
}
