package de.uulm.in.vs.grn.sockagram;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class SockagramGui extends JFrame implements Runnable {

	String pictureAddress;
	int code;
	String image;
	String host;
	int port;
	JPanel jp = new JPanel();
	JLabel text = new JLabel();
	JLabel picture = new JLabel();
	static JButton[] buttons = new JButton[12];
	JFrame jf = new JFrame();
	theHandler handler = new theHandler();
	JButton enter = new JButton("Enter");
	JButton standard = new JButton("Standard (localhost, 6789)");
	JTextField tf = new JTextField("Hostname?");
	JTextField tf2 = new JTextField("Port?");
	
	@Override
	public void run() {
		setSize(1024, 768);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Willkommen bei Sockagram!");
		setLayout(null);
		setVisible(true);
		jp.add(text);
		jp.add(picture);
		jp.setVisible(true);
		jf.add(jp);
		text.setBounds(10, 10, 768, 60);
		for(int i=0;i<12;i++) {
			buttons[i] = new JButton();
		}
		
		this.screen("Bitte wählen Sie einen Filter:", "Filter ",0);
			
	}
	
	public void screen(String headline, String buttonText, int page) {
		text.setText(headline);	
		Font schrift = text.getFont().deriveFont(Font.BOLD, 30);
		text.setFont(schrift);
		add(text);
				
		for(int i=0;i<6;i++) {
			buttons[i+(page*6)].setText(buttonText+(i+1));
			buttons[i+(page*6)].setBounds(10, 100+(i*60), 200, 50);
			add(buttons[i+(page*6)]);
			buttons[i+(page*6)].addActionListener(handler);
			buttons[i+(page*6)].setVisible(true);
		}
	}
	
	public void hostname(String headline) {
		text.setText(headline);	
		Font schrift = text.getFont().deriveFont(Font.BOLD, 30);
		text.setFont(schrift);
		add(text);
		tf.setBounds(10, 100, 400, 50);
		add(tf);
		tf.setVisible(true);
		tf2.setBounds(10, 200, 400, 50);
		add(tf2);
		tf2.setVisible(true);
		enter.setBounds(10, 300, 200, 100);
		add(enter);
		enter.setVisible(true);
		enter.addActionListener(handler);
		standard.setBounds(10, 450, 200, 100);
		add(standard);
		standard.setVisible(true);
		standard.addActionListener(handler);
	}
	
	class TimerListener implements ActionListener {
		public TimerListener(){
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			screen("Bitte wählen Sie ein Bild:", "Bild ",1);
			handler.timer.stop();
		}
	}
	
	class TimerListener2 implements ActionListener {
		public TimerListener2(){
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			hostname("Bitte geben Sie einen Hostnamen des Servers ein:");
			handler.timer2.stop();
			
		}
	}
	
	class theHandler implements ActionListener {
		public Timer timer = new Timer(1000, new TimerListener());
		public Timer timer2 = new Timer(1000, new TimerListener2());

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==buttons[0]) {
				code =0;
				text.setText("Sie haben Filter 1 gewählt!");
				for(int i=0;i<6;i++) {
					buttons[i].setVisible(false);
				}
				timer.start();			
			}
			if(e.getSource()==buttons[1]) {
				code =1;
				text.setText("Sie haben Filter 2 gewählt!");
				for(int i=0;i<6;i++) {
					buttons[i].setVisible(false);
				}
				timer.start();			
			}
			if(e.getSource()==buttons[2]) {
				code =2;
				text.setText("Sie haben Filter 3 gewählt!");
				for(int i=0;i<6;i++) {
					buttons[i].setVisible(false);
				}
				timer.start();			
			}
			if(e.getSource()==buttons[3]) {
				code =3;
				text.setText("Sie haben Filter 4 gewählt!");
				for(int i=0;i<6;i++) {
					buttons[i].setVisible(false);
				}
				timer.start();			
			}
			if(e.getSource()==buttons[4]) {
				code =4;
				text.setText("Sie haben Filter 5 gewählt!");
				for(int i=0;i<6;i++) {
					buttons[i].setVisible(false);
				}
				timer.start();			
			}
			if(e.getSource()==buttons[5]) {
				code =5;
				text.setText("Sie haben Filter 6 gewählt!");
				for(int i=0;i<6;i++) {
					buttons[i].setVisible(false);
				}
				timer.start();			
			}
			if(e.getSource()==buttons[6]) {
				image = "image1.jpg";
				text.setText("Sie haben Bild 1 gewählt!");
				for(int i=6;i<12;i++) {
					buttons[i].setVisible(false);
				}
				timer2.start();			
			}
			if(e.getSource()==buttons[7]) {
				image = "image2.jpg";
				text.setText("Sie haben Bild 2 gewählt!");
				for(int i=6;i<12;i++) {
					buttons[i].setVisible(false);
				}
				timer2.start();			
			}
			if(e.getSource()==buttons[8]) {
				image = "image3.jpg";
				text.setText("Sie haben Bild 3 gewählt!");
				for(int i=6;i<12;i++) {
					buttons[i].setVisible(false);
				}
				timer2.start();			
			}
			if(e.getSource()==buttons[9]) {
				image = "image4.jpg";
				text.setText("Sie haben Bild 4 gewählt!");
				for(int i=6;i<12;i++) {
					buttons[i].setVisible(false);
				}
				timer2.start();			
			}
			if(e.getSource()==buttons[10]) {
				image = "image5.jpg";
				text.setText("Sie haben Bild 5 gewählt!");
				for(int i=6;i<12;i++) {
					buttons[i].setVisible(false);
				}
				timer2.start();			
			}
			if(e.getSource()==buttons[11]) {
				image = "image6.jpg";
				text.setText("Sie haben Bild 6 gewählt!");
				for(int i=6;i<12;i++) {
					buttons[i].setVisible(false);
				}
				timer2.start();			
			}
			if(e.getSource()==enter) {
				host = tf.getText();
				String portnumber = tf2.getText();
				port = Integer.parseInt(portnumber);
				for(int i=6;i<12;i++) {
					buttons[i].setVisible(false);
				}
				tf.setVisible(false);
				tf2.setVisible(false);
				enter.setVisible(false);
				text.setText("Ihre Eingaben: " + host + " / " + port);
				SockagramMain.code = code;
				SockagramMain.image = image;
				SockagramMain.host = host;
				SockagramMain.port = port;
				SockagramMain.start();
				picture.setBounds(0, 0, 1024, 768);
				add(picture);
				Icon icon = new ImageIcon("test.png");
				picture.setIcon(icon);
				validate();
								
			}
			if(e.getSource()==standard) {
				host = "localhost";
				String portnumber = "6789";
				port = Integer.parseInt(portnumber);
				for(int i=6;i<12;i++) {
					buttons[i].setVisible(false);
				}
				tf.setVisible(false);
				tf2.setVisible(false);
				enter.setVisible(false);
				standard.setVisible(false);
				text.setText("Ihre Eingaben: " + host + " / " + port);
				SockagramMain.code = code;
				SockagramMain.image = image;
				SockagramMain.host = host;
				SockagramMain.port = port;
				SockagramMain.start();
				picture.setBounds(0, 0, 1024, 768);
				try {
					Thread.currentThread().sleep(4000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				/*
				try {
					SockagramMain.client.join();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}*/
				Icon icon = new ImageIcon("test.png");
				picture.setIcon(icon);
				add(picture);
				validate();
								
			}
		}
		
	}
	
}