package com.agoraio.btcapp;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MyWindowAdapter extends WindowAdapter {
	public void windowClosing(WindowEvent we) {
		System.exit(0);
	}
}
