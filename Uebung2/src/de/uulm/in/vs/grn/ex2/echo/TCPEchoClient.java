package de.uulm.in.vs.grn.ex2.echo;

import java.io.*;
import java.net.*;

public class TCPEchoClient implements Runnable{

	static int count = 4; //Anzahl der Durchlaeufe, wird in main veraendert
	String serverName;
	int portnr;
	int requestCount;
	int command;
	String message;
	byte[] b;
	InputStream is = null;
	OutputStream os = null;
	
	public TCPEchoClient(String serverName, int portnr, int requestCount, int command, String message) {
		this.serverName = serverName;
		this.portnr = portnr;
		this.requestCount = requestCount;
		this.message = message;
		this.command = command;
	}
	
	@Override
	public void run() {
		Socket s = null;
		try {
			s = new Socket (serverName, portnr);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			os = s.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			is = s.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	//	System.out.println("jetzt wird die Nachricht beim sender versendet....");
		
		try {
			os.write(requestCount);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//this.send wird requestCount mal ausgefuehrt
		while(requestCount>0) {
				
			try {
				this.send(s, message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			requestCount--;
		}	
	}
	//nimmt einen String entgegen und schickt ihn ueber Socket, schickt auch command Befehl
	public void send(Socket s,String message) throws IOException {
//		System.out.println("jetzt sollte die Nachricht beim sender zur�ck kommen....");

		os.write(command);
		int ml = message.length();
		os.write(ml);
		b = new byte[ml];
		b = message.getBytes();
		os.write(b);
		os.flush();
		this.receive(s);
	}
	//empfaengt Antwort-String vom server
	public void receive(Socket s) throws IOException {
		String string ="";
		int ml; //message length
		
		
		ml = is.read();
//		System.out.println("ml:" + ml);
		InputStreamReader isr = new InputStreamReader(is);
		
		for (int i = 0; i<ml; i++) {
			int data = isr.read();
			char oneChar = (char) data;
		    string += oneChar;
		}
		
		System.out.println("angekommen beim sender: " + string);
	}
}

