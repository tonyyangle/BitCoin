package com.agoraio.usermanualgui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class ComponentTest extends JFrame {
	JRadioButton[] button = new JRadioButton[2];
	ButtonGroup bg = new ButtonGroup();

	public ComponentTest() {
		init();
	}

	public void init() {
		setVisible(true);
		setSize(1024, 768);
		setLayout(new FlowLayout());

		button[0] = new JRadioButton("Market");
		button[1] = new JRadioButton("Assigned");

		bg.add(button[0]);
		bg.add(button[1]);
		add(button[0]);
		add(button[1]);
		button[0].addActionListener(new ButtonListenHandler());
		button[1].addActionListener(new ButtonListenHandler());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ComponentTest test = new ComponentTest();
		
	}

	class ButtonListenHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			for(int i = 0; i < button.length; i ++){
				if(button[i].isSelected()){
					if(i == 0)
						System.out.println("Market");
					else
						System.out.println("Assigned");
				}
			}
		}
	}

}
