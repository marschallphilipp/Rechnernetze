package de.uulm.in.vs.grn.sockagram;

import java.io.*;
import java.net.*;

import com.sun.java.accessibility.util.GUIInitializedListener;

public class SockagramClient implements Runnable{

	String serverName;
	int portnr;
	int code;
	String pictureAddress;
	byte[] request;
	OutputStream os = null;
	ObjectOutputStream oos;

	InputStream is = null;
	ObjectInputStream ois;
	int responseStatus; // = 0 wenn ok, ungleich 0 bei Fehler
	int responseSize;
	byte[] response;
	String failure;
	
	Socket s = null;
	
	public SockagramClient(String serverName, int portnr, int code, String pictureAddress) {
		this.serverName = serverName;
		this.portnr = portnr;
		this.code = code;
		this.pictureAddress = pictureAddress;
	}
	
		
	@SuppressWarnings("deprecation")
	@Override
	public void run() {
	
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

		try {
			request = this.convertToByte(pictureAddress);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		try {
			this.send(request);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			this.receive();
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			this.convertToFile(response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Thread.currentThread().stop();
	}
	
	//Convert the content of image into byte array	
	public byte[] convertToByte(String pictureAddress) throws IOException {	
		File file = new File(pictureAddress);
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(file);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        byte[] data = new byte[(int) file.length()];
        
        try {
			fileInputStream.read(data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			fileInputStream.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;
	}
	
	//Store the resulting byte array into a file
	public void convertToFile (byte[] response) throws IOException {
        FileOutputStream fos = null;
		try {
			fos = new FileOutputStream("test.png");
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
    
	//sendet Bild als Byte Array zum server
	public void send(byte[] request) throws IOException {
		oos = new ObjectOutputStream(os);
		oos.writeByte(code);

		oos.writeInt(request.length);
		oos.write(request);
		oos.flush();
		//oos.close();
	}
	
	//empfaengt Antwort Bild / String vom server
	public void receive() throws IOException, ClassNotFoundException {
		ois = new ObjectInputStream(is);
		responseStatus = ois.read();
		responseSize = ois.readInt();
		if(responseStatus == 0) {
			response = new byte[responseSize];
			ois.readFully(response);
		}
		else {
			try {
				failure = (String) ois.readObject();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

