/**
 * 
 */
package com.agoraio.btcapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DataConnector {

	// Database credentials

	public static void getTradeData(String url,
			ArrayList<TradeEntry> tradeEntry, int length) {
		String curr = "";
		curr = httpGet(url);
		try {
			parseRowData(curr, tradeEntry, length);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void parseRowData(String input,
			ArrayList<TradeEntry> tradeEntry, int length) throws JSONException {
		JSONArray array;

		for (int i = 59; i >= 59 - length + 1; i--) {
			JSONObject jObject;
			try {
				array = new JSONArray(input);
				jObject = array.getJSONObject(i);
				double amount = jObject.getDouble("amount");
				long currTime = jObject.getLong("date");
				String time = parseDate(currTime);
				double price = jObject.getDouble("price");
				String type = jObject.getString("type");

				if (type.equals("buy")) {
					tradeEntry.get(59 - i).isBuy = true;
				} else
					tradeEntry.get(59 - i).isBuy = false;
				tradeEntry.get(59 - i).price = price;
				tradeEntry.get(59 - i).amount = amount;
				tradeEntry.get(59 - i).date = time;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public static String httpGet(String urlStr) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		
		URL url = null;
		StringBuilder sb = new StringBuilder();
		try {
			url = new URL(urlStr);
			HttpURLConnection conn = null;
			conn = (HttpURLConnection) url.openConnection();
			if (conn.getResponseCode() != 200) {
				try {
					throw new IOException(conn.getResponseMessage());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			BufferedReader rd = null;
			rd = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));

			String line;
			
			while ((line = rd.readLine()) != null) {
				sb.append(line);

			}
			rd.close();
			conn.disconnect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Buffer the result into a string

		date = new Date();
		return sb.toString();
	}

	public static String parseDate(long input) {
		long currTime = input * 1000L;
		Timestamp timeStamp = new Timestamp(currTime);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyyMMddHHmmss");
		Date date = new Date(timeStamp.getTime());
		return simpleDateFormat.format(date);
	}

	/**
	 * @param args
	 * @throws IOException
	 * @throws JSONException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws IOException, JSONException,
			InterruptedException {
		// TODO Auto-generated method stub
		ArrayList<TradeEntry> tradeEntry = new ArrayList<TradeEntry>();
		int size = 20;
		for (int i = 0; i < size; i++) {
			tradeEntry.add(new TradeEntry());
		}
		while (true) {
			Thread.sleep(1000);

			DataConnector.getTradeData("https://www.okcoin.cn/api/trades.do?",
					tradeEntry, size);
		}
	}

}