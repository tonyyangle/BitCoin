
/**
 * 
 */
package com.agoraio.btcapp;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * OKCoin API Example
 * 
 * @author ok 2013/08/16 httpclient 请使用3.1版本
 */
public class OkCoinGetPending {
	// api接口地址
	// private final static String USER_INFO_URI =
	// "https://www.okcoin.cn/api/userinfo.do";
	private final static String TRADE_HISTORY_URI = "https://www.okcoin.cn/api/getOrderHistory.do";
	// private final static String USER_INFO_URI =
	// "https://www.okcoin.cn/api/userinfo.do"
	private final static String SYMBOL = "btc_cny";
	private final static long STATUS = 0;
	private final static long CURRENTPAGE = 1;
	private final static long PAGELENGTH = 10;
	// 密钥
	private final static String SECRET_KEY = "EA4D08FFD43CEB03FD6DF4F451FC376E";
	// 合作者ID

	private final static long PARTNER = 3285631;

	// private final static String RATE = "3624";
	// private final static String TRADE_URI =
	// "https://www.okcoin.cn/api/trade.do";
	// private final static String TYPE = "buy";
	// private final static String AMOUNT = "0.01";

	public static String testApi() {

		// 参数数组
		Map<String, String> sArray = new HashMap<String, String>();
		sArray.put("partner", Long.toString(PARTNER));
		sArray.put("symbol", SYMBOL);

		sArray.put("status", Long.toString(STATUS));
		sArray.put("currentPage", Long.toString(CURRENTPAGE));
		sArray.put("pageLength", Long.toString(PAGELENGTH));

		// 对参数数组签名
		String sign = buildMysign(sArray, SECRET_KEY);

		try {
			HttpClient client = new HttpClient();
			client.getHttpConnectionManager().getParams()
					.setConnectionTimeout(10000);
			client.getHttpConnectionManager().getParams().setSoTimeout(10000);
			// post请求
			PostMethod method = new PostMethod(TRADE_HISTORY_URI);
			NameValuePair[] data = { // new NameValuePair("amount", AMOUNT),
					new NameValuePair("partner", Long.toString(PARTNER)),
					new NameValuePair("symbol", SYMBOL),

					new NameValuePair("status", Long.toString(STATUS)),
					new NameValuePair("currentPage", Long.toString(CURRENTPAGE)),
					new NameValuePair("pageLength", Long.toString(PAGELENGTH)),
					new NameValuePair("sign", sign) };
			method.setRequestBody(data);
			client.executeMethod(method);
			// 返回json结果
			String result = method.getResponseBodyAsString();
			// ////////////////////////////////////////////////////////////////////////////////////////
			// ——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
			System.out.println(result);
			return result;
			// ////////////////////////////////////////////////////////////////////////////////////////
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 生成签名结果
	 * 
	 * @param sArray
	 *            要签名的数组
	 * @return 签名结果字符串
	 */
	public static String buildMysign(Map<String, String> sArray,
			String secretKey) {
		String mysign = "";
		try {

			String prestr = createLinkString(sArray); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
			System.out.println("+++ : " + prestr);
			prestr = prestr + secretKey; // 把拼接后的字符串再与安全校验码直接连接起来
			System.out.println("+++ : " + prestr);

			mysign = getMD5String(prestr);
			System.out.println("+++ : " + mysign);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mysign;
	}

	/**
	 * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	 * 
	 * @param params
	 *            需要排序并参与字符拼接的参数组
	 * @return 拼接后字符串
	 */
	public static String createLinkString(Map<String, String> params) {

		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		String prestr = "";
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key);
			if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
				prestr = prestr + key + "=" + value;
			} else {
				prestr = prestr + key + "=" + value + "&";
			}
		}
		return prestr;
	}

	/**
	 * 生成32位大写MD5值
	 */
	private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	public static String getMD5String(String str) {
		try {
			if (str == null || str.trim().length() == 0) {
				return "";
			}
			byte[] bytes = str.getBytes();
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(bytes);
			bytes = messageDigest.digest();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(HEX_DIGITS[(bytes[i] & 0xf0) >> 4] + ""
						+ HEX_DIGITS[bytes[i] & 0xf]);
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static void main(String[] args) {
		testApi();
	}

}

