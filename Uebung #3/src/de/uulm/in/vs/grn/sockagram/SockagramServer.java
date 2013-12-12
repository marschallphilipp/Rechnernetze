package de.uulm.in.vs.grn.sockagram;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SockagramServer implements Runnable{

	private final ServerSocket serverSocket;

	public SockagramServer(int port) throws IOException {
		serverSocket = new ServerSocket(port);
	}
	
	@Override
	public void run() {
		while(true){
			try {
				Socket connectionSocket = serverSocket.accept();
				//RequestHandler erzeugen und als separaten Thread starten
				RequestHandler requestHandler = new RequestHandler(connectionSocket);
				new Thread(requestHandler).start();
				}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}