package de.uulm.in.vs.grn.ex2.echo;

import java.io.*;
import java.net.*;

public class RequestHandler implements Runnable {
	Socket s = null;
	InputStream is = null;
	OutputStream os = null;
	int count; //Anzahl an gleichzeitigen Requests
	int command;
	
	public RequestHandler(Socket s) throws IOException {
		this.s = s;
	}

	@Override
	public void run() {
		try {
			is = s.getInputStream();
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
		
//		System.out.println("jetzt sollte die Nachricht beim Handler ankommen....");
		try {
			count = this.requestCount(); //erstes Byte, naemlich requestCount lesen
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//		System.out.println("RequestCount: " + count);
		//RequestCount mal wird empfangen
		while(count > 0) {
			try {
//				System.out.println("jetzt startet ein durchlauf....");
				this.receive();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			count--;
			if(count == 0) { //thread stoppen wenn alles abgearbeitet
					Thread.currentThread().interrupt();
			}
		}
	}
	
	public int requestCount() throws IOException {
		int requestCount;
		requestCount = is.read();
		return requestCount;
	}
	//command und String empfangen, String manipulieren und send aufrufen
	public void receive() throws IOException {
		int command;
		String string ="";
		int ml; //message length
		
		command = is.read();
//		System.out.println("command:" + command);
		ml = is.read();
//		System.out.println("ml:" + ml);
		InputStreamReader isr = new InputStreamReader(is);
		
		for (int i = 0; i<ml; i++) {
			int data = isr.read();
			char oneChar = (char) data;
		    string += oneChar;
		}
		
//		System.out.println("angekommen beim Handler: " + string);
		string = doCommand(command, string);
		this.send(string);
	}
	// String zurueck schicken
	public void send(String message) throws IOException {
		byte[] b;
				
		int ml = message.length();
		os.write(ml);
		b = new byte[ml];
		b = message.getBytes();
		os.write(b);
		os.flush();
	}
	//String methoden aufrufen
	public String doCommand (int command, String string) {
		if((command&1) == 1) {
			string = reverse(string);
		}
		if((command&2) == 2 && (command&4) != 4) {
			string = lowerCase(string);
		}
		if((command&4) == 4 && (command&2) != 2) {
			string = UpperCase(string);
		}
		if((command&8) == 8) {
			string = Whitespaces(string);
		}
		if((command&2) == 2 && (command&4) == 4) {
		}
		return string;
	}
	
	public String reverse(String string) {
		String reversed = new StringBuffer(string).reverse().toString();
		return reversed;
	}
	
	public String lowerCase(String string) {
		string = string.toLowerCase();
		return string;
	}
	
	public String UpperCase(String string) {
		string = string.toUpperCase();
		return string;
	}
	
	public String Whitespaces(String string) {
		int ml = string.length();
		string = "";
		for(int i=ml; i>0; i--) {
			string += " ";
		}
		return string;
	}

}