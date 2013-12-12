package de.uulm.in.vs.grn.sockagram;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import de.uulm.in.vs.grn.sockagram.filter.SockagramFilter;

public class RequestHandler implements Runnable {

	private final Socket s;
	InputStream is = null;
	ObjectInputStream ois;
	byte code;
	int requestSize;
	byte[] request;
		
	OutputStream os = null;
	ObjectOutputStream oos;
	int responseStatus = 0; // = 0 wenn ok, ungleich 0 bei Fehler
	int responseSize;
	byte[] response;
	String failure = "alles jut...";

	public RequestHandler(Socket s) {
		this.s = s;
	}

	@Override
	public void run() {
		try {
			os = s.getOutputStream();
			is = s.getInputStream();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		try {
			this.receive();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			this.convertToFile(request);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			this.doFilter();
		} catch (IllegalArgumentException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			this.send();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//Store the resulting byte array into a file
		public void convertToFile(byte[] response) throws IOException {
	        FileOutputStream fos = null;
			try {
				fos = new FileOutputStream("testvonsender.png");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        try {
				fos.write(response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        try {
				fos.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	
	// do the filtering process, changing to existing byte Array
	public void doFilter() throws IllegalArgumentException, IOException {	
		SockagramFilter filter = SockagramFilter.getFilterByCode(code); // gibt Filter zurück
		response = filter.apply(request); // Anwendung des Filters 
	}
		    
	//sendet Bild als Byte Array zum sender
	public void send() throws IOException {
		oos = new ObjectOutputStream(os);
		oos.writeByte(responseStatus);
		
		if(responseStatus == 0) {
			oos.writeInt(response.length);
			oos.write(response);
			oos.flush();
		}
		else {
			oos.writeInt(failure.length());
			oos.writeObject(failure);
			oos.flush();
		}
				
		oos.close();
	}
	
	//empfaengt Bild von Sender
	public void receive() throws IOException {
		ois = new ObjectInputStream(is);
		code = ois.readByte();
		
		//
		System.out.println("empfangener Code: " + (int) code);
		//
		
		if((int) code < 0 || (int) code > 6) {
			failure = "Dies ist kein gueltiger Filter-Code! Code muss zwischen 0 und 6 liegen.";
			responseStatus = 1;
		}
		requestSize = ois.readInt();
		request = new byte[requestSize];

		//
		System.out.println(requestSize);
		//
		
		ois.readFully(request);	
	}
	
}