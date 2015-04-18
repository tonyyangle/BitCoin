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
 * @author Tony
 *
 */
public class UserInfo {
//	public ArrayList<Entry> infoData = null;
//	public ArrayList<Entry> resultData = null;

	public int capacity;
	public String url = "https://www.okcoin.cn/api/ticker.do";
	public String url1 = "https://www.okcoin.cn/api/ticker.do?symbol=ltc_cny";
	public String btcInfo = null;
	public String ltcInfo = null;
	public String cnyInfo = null;
	public double btcSellValue = 0;
	public double ltcSellValue = 0;
	public double btcAmount = 0;
	public double ltcAmount = 0;
	public double cnyAmount = 0;
	public double total = 0;
	public String result = "";

	public UserInfo() {

	}

	public UserInfo(int capacity, String url_btc, String url_ltc) {
		this.capacity = capacity;
		url = url_btc;
		url1 = url_ltc;
		result = "";
	}

	public void getData() {
		result = OkCoinApiUser.testApi();
		convertData(result);
	}

	public void getData1() {
		String url_btc = httpGet(url);
		convertData1(url_btc);
	}

	public void getData2() {
		String url_ltc = httpGet(url1);
		convertData2(url_ltc);
	}

	public void total() {

		double total = (this.btcAmount * this.btcSellValue)
				+ (this.ltcAmount * this.ltcSellValue) + this.cnyAmount;
		this.total = total;
		System.out.println("Total Value:" + total);
	}

	// public String getInfoData() {
	// StringBuffer info = new StringBuffer();
	// for (int i = 0; i < 10; i++) {
	// info.append(infoData.get(i).btc);
	// info.append(infoData.get(i).ltc);
	// info.append(infoData.get(i).cny);
	// }
	// System.out.println(info);
	// return info.toString();
	// }

	// public String getResultData() {
	// StringBuffer result = new StringBuffer();
	// for (int i = 0; i < 10; i++)
	// result.append(resultData.get(i).res);
	// System.out.println(result);
	// return result.toString();
	// }

	public void convertData(String result) {
		JSONObject jObject;
		try {

			jObject = new JSONObject(result);

			JSONObject objectInfo = jObject.getJSONObject("info");
			JSONObject objectFunds = objectInfo.getJSONObject("funds");
			JSONObject objectFree = objectFunds.getJSONObject("free");
			objectFree.getString("btc");
			this.btcInfo = objectFree.getString("btc");
			objectFree.getDouble("btc");
			this.btcAmount = objectFree.getDouble("btc");
			objectFree.getString("ltc");
			this.ltcInfo = objectFree.getString("ltc");
			objectFree.getDouble("ltc");
			this.ltcAmount = objectFree.getDouble("ltc");
			objectFree.getString("cny");
			this.cnyInfo = objectFree.getString("cny");
			objectFree.getDouble("cny");
			this.cnyAmount = objectFree.getDouble("cny");
			System.out.println("BTC Position:" + objectFree.getString("btc"));
			System.out.println("LTC Position:" + objectFree.getString("ltc"));
			System.out.println("CNY Position:" + objectFree.getString("cny"));

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void convertData1(String url_btc) {
		JSONObject jObject;
		try {

			jObject = new JSONObject(url_btc);

			JSONObject objectTicker = jObject.getJSONObject("ticker");

			double buy = objectTicker.getDouble("buy");
			double sell = objectTicker.getDouble("sell");
			this.btcSellValue = sell;
			System.out.println("BTC BuyValue:" + buy);
			System.out.println("BTC SellValue:" + sell);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void convertData2(String url_ltc) {
		JSONObject jObject;
		try {

			jObject = new JSONObject(url_ltc);

			JSONObject objectTicker = jObject.getJSONObject("ticker");

			double buy = objectTicker.getDouble("buy");
			double sell = objectTicker.getDouble("sell");
			this.ltcSellValue = sell;

			System.out.println("LTC BuyValue:" + buy);
			System.out.println("LTC SellValue:" + sell);

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
	 */
	public static void main(String[] args) throws InterruptedException,
			JSONException {
		// TODO Auto-generated method stub
		String url_btc = "https://www.okcoin.cn/api/ticker.do";
		String url_ltc = "https://www.okcoin.cn/api/ticker.do?symbol=ltc_cny";
		String result = OkCoinApiUser.testApi();

		UserInfo userInfo = new UserInfo(1, url_btc, url_ltc);

		while (true) {
			userInfo.getData();
			userInfo.getData1();
			userInfo.getData2();
			userInfo.total();
			Thread.sleep(5000);
		}

		// System.out.println( OkCoinApiUser.testApi());

	}

	class Entry {
		public double btc;
		public double ltc;
		public double cny;
		public double total;
		public boolean res;

		public Entry() {
		}

		public Entry(double btc, double ltc, double cny, double total,
				boolean res) {
			this.btc = btc;
			this.ltc = ltc;
			this.cny = cny;
			this.total = total;
			this.res = res;
		}
	}
}
