package de.uulm.in.vs.grn.sockagram;

import java.io.IOException;

public class SockagramMain {
	static int code;
	static int port;
	static String image;
	static String host;
	static Thread client;
			
	public static void main(String[] args) throws IOException, InterruptedException {
		Thread server = new Thread(new SockagramServer(6789));
		server.start();
		
		Thread gui = new Thread(new SockagramGui());
		gui.start();
			
	}
	
	public static void start() {
		Thread client = new Thread(new SockagramClient(host, port, code, image));
		client.start();
	}
	
}