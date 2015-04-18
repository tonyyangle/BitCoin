package com.agoraio.strategy;

public abstract class Strategy {
	public State state = null;
	public Strategy(){
		state = new State();
	}
	public abstract void decision();
}
