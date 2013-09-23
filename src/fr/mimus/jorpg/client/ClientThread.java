package fr.mimus.jorpg.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientThread extends Thread {
	public final String SEP = "\u00FE";
	public final String END = "\u00DF";
	Socket socket = null;
	BufferedReader in;
	PrintWriter out;
	public boolean isOnLine = false;
	
	ClientMain cm;
	
	int myid= -1;
	boolean isMoved = false;
	boolean isAttack = false;
	
	public ClientThread(Socket s, ClientMain c) {
		socket = s;
		isOnLine = true;
		cm = c;
	}
	
	public void run() {
		try {
			out = new PrintWriter(socket.getOutputStream());
			in = new BufferedReader (new InputStreamReader (socket.getInputStream()));
	        
			String inputLine;
			String[] t;
			do{
				inputLine = "";
				try {
					inputLine = in.readLine();
				} catch (IOException e) {
					//e.printStackTrace();
					this.isOnLine = false;
				}

				if(inputLine!=null && this.isOnLine) {
					t = inputLine.split(this.SEP);
					ClientReception Recep = new ClientReception(this, t);
					Recep.start();
				}

			} while (isOnLine && inputLine != null);
			
			/*out = new PrintWriter(socket.getOutputStream());
	        out.println("ping"+SEP+END);
	        out.flush();
	        
	        in = new BufferedReader (new InputStreamReader (socket.getInputStream()));
	        String message_distant = in.readLine();
	        System.out.println(message_distant);
	        
	        out.println("say"+SEP+"mdr"+SEP+END);
	        out.flush();*/
	        
	        socket.close();
		       
		}catch (UnknownHostException e) {
			
			e.printStackTrace();
		}catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	public void send(String message){
		out.println(message);
		out.flush();
	}
}
