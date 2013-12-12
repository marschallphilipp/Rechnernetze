package de.uulm.in.vs.grn.ex2.echo;

import java.io.IOException;

public class AdvanceEchoServiceMain {
	public static void main(String[] args) throws IOException, InterruptedException {
		Thread server = new Thread(new AdvancedEchoServer(6789));
		server.start();
		server.sleep(500);
		
		while(TCPEchoClient.count>0) {
		Thread client = new Thread(new TCPEchoClient("localhost", 6789, 2, 1, "Dies ist Testnachricht 1..."));
		client.start();
		client.sleep(500);
		Thread client2 = new Thread(new TCPEchoClient("localhost", 6789, 3, 4, "Dies ist Testnachricht 2..."));
		client2.start();
		TCPEchoClient.count--;
		}

	}
}