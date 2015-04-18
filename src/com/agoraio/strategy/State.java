/**
 * 
 */
package com.agoraio.strategy;

/**
 * @author yangkklt
 * 
 */
public class State {
	public boolean isEmpty;
	public Position position = null;

	/**
	 * @param args
	 */
	public State() {
		isEmpty = true;
		position = new Position();
	}

	public State(boolean e, Position p) {
		isEmpty = e;
		position.amount = p.amount;
		position.price = p.price;
		position.symbol = p.symbol;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
