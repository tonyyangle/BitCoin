/**
 * 
 */
package com.agoraio.btcapp;
/**
 * @author yangkklt
 * 
 */
public class TradeEntry {
	public boolean isBuy;
	public double price;
	public double amount;
	public String date;

	public TradeEntry() {
	}

	public TradeEntry(boolean isBuy, double price, double amount) {
		this.isBuy = isBuy;
		this.price = price;
		this.amount = amount;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
