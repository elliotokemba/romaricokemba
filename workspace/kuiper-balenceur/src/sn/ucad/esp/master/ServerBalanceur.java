package sn.ucad.esp.master;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerBalanceur implements Runnable{
	
	private ServerSocket ss;
	
	private Socket socket;
	
	Socket s,s1;
	
	private static List<Socket> lstServeurDistant=new ArrayList<Socket>(); 
	
	private static List<Socket> lstServeurClient=new ArrayList<Socket>(); 
	
	public ServerBalanceur(){
		 try {
			ss=new ServerSocket(1086);
			System.out.println("Serveur Balanceur en écoute dans le port .. 1086");
			
			/*********************************  Serveur distant  1   **************************************************************/
			 s = null;
			  //Connexion au Serveur de messgerie
			  try {
				 s=new Socket("192.168.1.18", 86);
				 
				 
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			  
			  lstServeurDistant.add(s);
			  Scanneur scanneur=new ScanneurServeur(s);
			  Thread thread=new Thread(scanneur);
			  thread.start();
			  
			  

				/*********************************  Serveur distant 2    **************************************************************/
				 s1 = null;
				  //Connexion au Serveur de messgerie
				  try {
					 s1=new Socket("192.168.1.21", 86);
					 
					 
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				  
				  lstServeurDistant.add(s1);
				  Scanneur scanneur1=new ScanneurServeur(s1);
				  Thread thread1=new Thread(scanneur1);
				  thread1.start();
				  
			  
			  
			  
			  
			  
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
	   new Thread(new ServerBalanceur()).start();
	}

	@Override
	public void run() {
	
			
	   while(true){
		   
		   
		   try {
			socket=ss.accept();
	        System.out.println("Un Client Connecté depuis load balanceur");
	        lstServeurClient.add(socket);
	        //On demarre le serveur des Clients
	        new Thread(new Route(socket,s)).start();
	      
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		   
	   }
	}
	
	
	private  class Route implements Runnable{
		
		Socket socketCli;
		Socket socketServeur;
		
		public Route(Socket socketCli,Socket socketServeur){
			this.socketCli=socketCli;
			this.socketServeur=socketServeur;
//			lstServeurDistant.add(socketServeur);
			
		}

		@Override
		public void run() {
			
			Scanneur scanneur=new ScanneurClient(socketCli);
			Thread thread=new Thread(scanneur);
			thread.start();
		}
		
		
		
	}
	
	public class Scanneur implements Runnable{
		ObjectInputStream iis;
		Socket socket;
		public Scanneur(Socket socket){
			this.socket=socket;
		}

		@Override
		public void run() {
		
			while (true){
				try {
					iis=new ObjectInputStream(socket.getInputStream());
					try {
						Enveloppe enveloppe=(Enveloppe) iis.readObject();
					
                      envoyer(enveloppe, this);
						
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
			
		}
		
	}
	
	public void envoyer(Enveloppe enveloppe,Scanneur scanneur){
		if (scanneur instanceof ScanneurClient){
			int i=0;
			for (Socket socket:lstServeurDistant){
				
				try {
					//if (socket !=null && i==0){
					
				
					ObjectOutputStream oss=new ObjectOutputStream(socket.getOutputStream());
					oss.writeObject(enveloppe);
					oss.flush();
					i=i+1;
				//	}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		}else{
			
			
			for (Socket socket:lstServeurClient){
				try {
					ObjectOutputStream oss=new ObjectOutputStream(socket.getOutputStream());
					oss.writeObject(enveloppe);
					oss.flush();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				
			}
		}
	}
	
	public class ScanneurClient extends Scanneur{

		public ScanneurClient(Socket socket) {
			super(socket);
			
		}
		
	}
	
	public class ScanneurServeur extends Scanneur{

		public ScanneurServeur(Socket socket) {
			super(socket);
			
		}
		
	}

}
