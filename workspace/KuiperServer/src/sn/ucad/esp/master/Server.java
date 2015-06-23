package sn.ucad.esp.master;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable{
	
	private ServerSocket ss;
	
	private Socket socket;
	
	public Server(){
		 try {
			ss=new ServerSocket(86);
			System.out.println("Serveur en écoute dans le port .. 86");
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
	   new Thread(new Server()).start();
	}

	@Override
	public void run() {
		
	   while(true){
		   
		   try {
			socket=ss.accept();
			System.out.println("Un client vient de se connecter");
			new Thread(new ProcessusServeur(socket)).start();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		   
	   }
	}

}
