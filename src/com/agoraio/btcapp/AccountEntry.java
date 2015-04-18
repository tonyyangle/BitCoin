/**
 * 
 */
package com.agoraio.btcapp;

/**
 * @author Tony
 *
 */
public class AccountEntry {
	public String btc;
	public String ltc;
	public String cny;
	public double total;
	
	public AccountEntry(){
	}
	
	public AccountEntry(String btc,String ltc,String cny, double total){
		this.btc = btc;
		this.ltc = ltc;
		this.cny = cny;
		this.total = total;
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
