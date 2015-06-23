package sn.ucad.esp.master;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DaoServer {

	/**
	 * méthode qui permet de créer un Objet en Base de données
	 * @param query
	 * @return
	 */
	public static boolean creerCompte(String compte,String mdp){
		//Création de Compte 
		String url="jdbc:mysql://localhost/jerry";
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			String c="com.mysql.jdbc.Driver";
			
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		String user="root";
		
		String pass="passer";
		
	    try {
	    	
			Connection conn=DriverManager.getConnection(url, user, pass);
			
			Statement  st=conn.createStatement();
			
			String query="INSERT INTO compte values('"+ compte.trim().replace("'", "''")+"','"+ mdp.trim().replace("'", "''")+"')";
			
			
			System.out.println(query);
			//On enregistre le Compte
			st.executeUpdate(query);
			
			 return true;
			
		} catch (SQLException e) {
			 e.printStackTrace();
			 return false;
			
		}
	    
	}
	
	
	/**
	 * Méthode qui recherche un compte dans le système
	 * @param compte
	 * @param mdp
	 * @return
	 */
	public static boolean rechercherCompte(String compte,String mdp){
		//Création de Compte 
		String url="jdbc:mysql://localhost/jerry";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			
		}
		
		String user="root";
		
		String pass="passer";
		
	    try {
	    	
			Connection conn=DriverManager.getConnection(url, user, pass);
			
			Statement  st=conn.createStatement();
			
			String query="SELECT * FROM compte  WHERE  compte='"+ compte.trim().replace("'", "''")+"' and mdp='"+ mdp.trim().replace("'", "''")+"'";
			
		    System.out.println(query);
			//On enregistre le Compte
			ResultSet rs=st.executeQuery(query);
			
			while (rs.next()){
				
				System.out.println("Server: Utilisateur trouvé");
				
				return true;
			}
			
			 return false;
			
		} catch (SQLException e) {
			
			System.out.println("Server Error: "+e.getMessage());
			
			 return false;
			
		}
	    
	}
	
	
	
}
