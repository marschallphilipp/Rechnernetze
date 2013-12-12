package de.uulm.in.vs.grn.ex2.echo;

import java.io.*;
import java.net.*;

public class AdvancedEchoServer implements Runnable {
	ServerSocket ss = null;
	
	public AdvancedEchoServer(int portnr) throws IOException {
		ss = new ServerSocket(portnr);
	}

	@Override
	public void run() {
		
		while(true) {
			Socket s = null;
			try {
				s = ss.accept();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				Thread handler = new Thread(new RequestHandler(s));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Thread handler = null;
			try {
				handler = new Thread(new RequestHandler(s));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			handler.start();
		}
	}
}