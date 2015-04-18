package com.agoraio.strategy;

public class Position {
	public Symbol symbol;
	public double amount;
	public double price;

	public Position(Symbol s, double a, double p) {
		symbol = s;
		amount = a;
		price = p;
	}

	public Position() {

	}
}
