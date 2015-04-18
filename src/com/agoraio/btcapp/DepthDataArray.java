/**
 * 
 */
package com.agoraio.btcapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author yangkklt
 * 
 */
public class DepthDataArray {
	private ArrayList<DepthEntry> askData = null;
	private ArrayList<DepthEntry> bidData = null;
	private java.text.DecimalFormat df = new java.text.DecimalFormat("#.###");
	private int capacity;
	private int size;
	private String url = null;

	public DepthDataArray() {

	}

	public DepthDataArray(int capacity, String url) {
		this.capacity = capacity;
		this.size = 0;
		this.askData = new ArrayList<DepthEntry>(capacity);
		this.bidData = new ArrayList<DepthEntry>(capacity);
		this.url = url;
		for (int i = 0; i < capacity; i++) {
			askData.add(i, new DepthEntry());
			bidData.add(i, new DepthEntry());
		}
	}

	public void getData() {
		String result = httpGet(url);
		convertData(result);
	}

	public String getAsksData() {
		StringBuffer ask = new StringBuffer();
		for (int i = 0; i < capacity; i++) {
			ask.append(askData.get(i).price);
			ask.append("\t");
			ask.append(df.format(askData.get(i).amount));
			ask.append("\n");
		}

		return ask.toString();
	}

	public String getBidsData() {
		StringBuffer bid = new StringBuffer();
		for (int i = 0; i < capacity; i++) {
			bid.append(bidData.get(i).price);
			bid.append("\t");
			bid.append(df.format(bidData.get(i).amount));
			bid.append("\n");
		}
		return bid.toString();
	}

	public void convertData(String result) {
		JSONObject jObject;
		try {

			jObject = new JSONObject(result);

			JSONArray arrayAsks = jObject.getJSONArray("asks");
			JSONArray arrayBids = jObject.getJSONArray("bids");
			for (int i = 0; i < capacity; i++) {
				JSONArray small = arrayAsks.getJSONArray(190 + i);
				askData.get(i).price = small.getDouble(0);
				askData.get(i).amount = small.getDouble(1);
				small = arrayBids.getJSONArray(i);
				bidData.get(i).price = small.getDouble(0);
				bidData.get(i).amount = small.getDouble(1);

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String httpGet(String urlStr) {
		URL url = null;
		try {
			url = new URL(urlStr);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) url.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			if (conn.getResponseCode() != 200) {
				try {
					throw new IOException(conn.getResponseMessage());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Buffer the result into a string
		BufferedReader rd = null;
		try {
			rd = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StringBuilder sb = new StringBuilder();
		String line;
		try {
			while ((line = rd.readLine()) != null) {
				sb.append(line);

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			rd.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		conn.disconnect();
		return sb.toString();
	}

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		String url = "https://www.okcoin.cn/api/depth.do";
		DepthDataArray dataArray = new DepthDataArray(10, url);
		while (true) {
			dataArray.getData();
			dataArray.getAsksData();
			Thread.sleep(1000);
		}

	}

}
