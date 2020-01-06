package DataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.example.springbootswagger2.model.*;

public class DataBaseManager implements DatabaseInterface {

	public static Connection connexion;
	public static Statement statement;
	public static String urlFileDb = "jdbc:sqlite::resource:DataBase/groupe.db";

	public DataBaseManager() {}

	public static void initialisationBD(){
		// TODO Auto-generated method stub

		try {

			// Initialisation du JDBC
			Class.forName("org.sqlite.JDBC");

			// Connexion � la BD, cr�ation si elle n'existe pas
			connexion = DriverManager.getConnection(urlFileDb);

			// Cr�ation des diff�rentes tables si elles n'existent pas
			statement = connexion.createStatement();
			
			String tableUE = "CREATE TABLE IF NOT EXISTS UniteEnseignement"
					+ "(Identifiant TEXT PRIMARY KEY     NOT NULL,"
					+ " Nom           TEXT    NOT NULL, "
					+ " Code           TEXT    NOT NULL, "
					+ " Cours           REAL    NOT NULL, "
					+ " TD           REAL    NOT NULL, "
					+ " TP           REAL    NOT NULL, "					
					+ " Valeur           REAL    NOT NULL, "
					+ " Description            TEXT     NOT NULL) ";
			
			String tableSujet = "CREATE TABLE IF NOT EXISTS Sujet"
					+ "(Identifiant TEXT PRIMARY KEY     NOT NULL,"
					+ " Nom           TEXT    NOT NULL, "
					+ " Description            TEXT     NOT NULL) ";
			
			String tableGroupe = "CREATE TABLE IF NOT EXISTS Groupe"
					+ "(Identifiant TEXT PRIMARY KEY     NOT NULL,"
					+ " Nom            TEXT     NOT NULL,"
					+ " IdentifiantUE 	INT,"
					+ " IdentifiantSujet 	INT,"
					+ " FOREIGN KEY(IdentifiantUE) REFERENCES UniteEnseignement(Identifiant),"
					+ " FOREIGN KEY(IdentifiantSujet) REFERENCES Sujet(Identifiant))";
			
			String tableEleve = "CREATE TABLE IF NOT EXISTS Eleve"
					+ "(Identifiant TEXT PRIMARY KEY     NOT NULL,"
					+ " Prenom           TEXT    NOT NULL, "
					+ " Nom            TEXT     NOT NULL,"
					+ " IdentifiantGroupe INT,"
					+ " Email            TEXT     NOT NULL,"
					+ " FOREIGN KEY(IdentifiantGroupe) REFERENCES Groupe(Identifiant)) ";
			

			String drp = "DROP TABLE ";






			statement.executeUpdate(tableUE);
			statement.executeUpdate(tableSujet);
			statement.executeUpdate(tableGroupe);
			statement.executeUpdate(tableEleve);

			
			
			statement.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
	//Fonctions insert : C
	public static void insertEleve(String id , String prenom, String nom, String Email)
	{
		try {
	         Class.forName("org.sqlite.JDBC");

	         connexion.setAutoCommit(false);

	         //On recupere le dernier Id de la base puis on l'incr�mente pour la nouvelle valeur
	         //int id = DataBaseManager.lastId("Eleve");
	         //id++;
	         
	         statement = connexion.createStatement();
	         String sql = "INSERT INTO Eleve (Identifiant, Prenom, Nom, Email) " +
	                        "VALUES ('" + id + "', '" + prenom + "', '" + nom + "', '"+Email+"' );"; 
	         statement.executeUpdate(sql);
	         	         
	         statement.close();
	         connexion.commit();
	         ////connexion.close();
	         
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void insertSujet(String id ,String nom, String description)
	{
		try {
	         Class.forName("org.sqlite.JDBC");

	         connexion.setAutoCommit(false);

	         //On recupere le dernier Id de la base puis on l'incr�mente pour la nouvelle valeur
	         //int id = DataBaseManager.lastId("Sujet");
	         //id++;
	         
	         statement = connexion.createStatement();
	         String sql = "INSERT INTO Sujet (Identifiant, Nom, Description) " +
	                        "VALUES ('" + id + "', '" + nom + "', '" + description + "' );"; 
	         statement.executeUpdate(sql);
	         	         
	         statement.close();
	         connexion.commit();
	         
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void insertUE(String id ,String nom, String description, String code ,float cours, float td, float tp, float valeur)
	{
		try {
	         Class.forName("org.sqlite.JDBC");

	         connexion.setAutoCommit(false);

	         //On recupere le dernier Id de la base puis on l'incr�mente pour la nouvelle valeur
	         //int id = DataBaseManager.lastId("UniteEnseignement");
	         //id++;
	         
	         statement = connexion.createStatement();
	         String sql = "INSERT INTO UniteEnseignement (Identifiant, Nom, Code, Cours, TD, TP, Valeur, Description) " +
	                        "VALUES ('" + id + "', '" + nom + "', '"+code+"' , "+cours+", "+td+", "+tp+", "+valeur+", '" + description + "' );"; 
	         statement.executeUpdate(sql);
	         	         
	         statement.close();
	         connexion.commit();
	         
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void insertGroupe(String id , String nom )
	{
		try {
	         Class.forName("org.sqlite.JDBC");

	         connexion.setAutoCommit(false);

	         //On recupere le dernier Id de la base puis on l'incr�mente pour la nouvelle valeur
	         //int id = DataBaseManager.lastId("Groupe");
	         //id++;
	         
	         statement = connexion.createStatement();
	         String sql = "INSERT INTO Groupe (Identifiant, Nom) " +
	                        "VALUES ('" + id + "', '" + nom +"');";
	         statement.executeUpdate(sql);
	         	    	         
	         statement.close();
	         connexion.commit();
	         
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//Fonctions select : R
	
	//Fonction r�cup�rant le dernier identifiant d'une table pass�e en parametre
	public static int lastId(String table) {
		try {
			Class.forName("org.sqlite.JDBC");
			connexion.setAutoCommit(false);

			statement = connexion.createStatement();
			ResultSet res = statement.executeQuery("SELECT MAX(Identifiant) FROM " + table + ";");

			int returnId =  res.getInt(1);
			
			res.close();

			return returnId;

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return -1;
	}
	
	public static List<Eleve> selectListEleve()
	{
		List<Eleve> eleves = new ArrayList<>();
		
		try {
			Class.forName("org.sqlite.JDBC");

			statement = connexion.createStatement();
			ResultSet res = statement.executeQuery("SELECT Identifiant, Prenom, Nom, IdentifiantGroupe, Email FROM Eleve;");


			while ( res.next() ) {
		         
		         eleves.add(new Eleve(
		        		 res.getString(1),
		        		 res.getString(2),
		        		 res.getString(3),
		        		 res.getString(4),
		        		 res.getString(5)));
			}
			
			res.close();
	         statement.close();
	         ////connexion.close();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return eleves;
	}
	
	//Retourne la liste des �leves appartenant � un groupe
	public static List<Eleve> selectListEleveGroupe(String idGroupe)
	{
		List<Eleve> eleves = new ArrayList<>();
		
		try {
			Class.forName("org.sqlite.JDBC");

			statement = connexion.createStatement();
			ResultSet res = statement.executeQuery("SELECT Identifiant, Prenom, Nom, IdentifiantGroupe, Email FROM Eleve WHERE IdentifiantGroupe = '" + idGroupe + "';");


			while ( res.next() ) {
		         
		         eleves.add(new Eleve(
		        		 res.getString(1),
		        		 res.getString(2),
		        		 res.getString(3),
		        		 res.getString(4),
		        		 res.getString(5)));
			}
			
			res.close();
	         statement.close();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return eleves;
	}
	
	public static List<Sujet> selectListSujet()
	{
		List<Sujet> sujets = new ArrayList<>();
		
		try {
			Class.forName("org.sqlite.JDBC");

			statement = connexion.createStatement();
			ResultSet res = statement.executeQuery("SELECT Identifiant, Nom, Description FROM Sujet;");


			while ( res.next() ) {
		         
		         sujets.add(new Sujet(
		        		 res.getString(1),
		        		 res.getString(2),
		        		 res.getString(3)));
			}
			
			res.close();
	         statement.close();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sujets;
	}
	
	
	public static List<Unite> selectListUnite()
	{
		List<Unite> unites = new ArrayList<>();
		
		try {
			Class.forName("org.sqlite.JDBC");

			statement = connexion.createStatement();
			ResultSet res = statement.executeQuery("SELECT Identifiant, Nom, Code, Cours, TD, TP, Valeur, Description FROM UniteEnseignement;");


			while ( res.next() ) {
		         
		         unites.add(new Unite(
		        		 res.getString(1),
		        		 res.getString(2),
		        		 res.getString(8),
		        		 res.getString(3),
		        		 res.getFloat(4),
		        		 res.getFloat(5),
		        		 res.getFloat(6),
		        		 res.getFloat(7)));
			}
			
			res.close();
	         statement.close();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return unites;
	}
	
	public static List<Groupe> selectListGroupe()
	{
		List<Groupe> groupes = new ArrayList<>();
		
		try {
			Class.forName("org.sqlite.JDBC");

			statement = connexion.createStatement();
			ResultSet res = statement.executeQuery("SELECT Identifiant, Nom FROM Groupe;");


			while ( res.next() ) {
		         groupes.add(selectGroupeWithId(res.getString(1)));
			}
			
			res.close();
	         statement.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return groupes;
	}
	
	//Fonction permettant la r�cup�ration d'un objet avec son identifiant
	public static Eleve selectEleveWithId(String id) {
		try {
			Class.forName("org.sqlite.JDBC");

			statement = connexion.createStatement();
			ResultSet res = statement.executeQuery("SELECT Identifiant, Prenom, Nom, IdentifiantGroupe, Email FROM Eleve"
					+ " WHERE Identifiant = '"+id+"' ;");

			Eleve eleve = new Eleve(res.getString(1), res.getString(2), res.getString(3), res.getString(4), res.getString(5));

			res.close();
			statement.close();
			//connexion.close();
			
			return eleve;

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	public static Eleve selectEleve(String id,String nom,String prenom,String email) {
		try {
			Class.forName("org.sqlite.JDBC");
			statement = connexion.createStatement();
			ResultSet res = statement.executeQuery("SELECT Identifiant, Prenom, Nom, IdentifiantGroupe, Email FROM Eleve"
					+ " WHERE Identifiant = '"+id+"' AND Prenom = '"+prenom+"' AND Nom = '"+nom+"' AND Email = '"+email+"' ;");
			Eleve eleve;
			if (!res.next() ) {
			     eleve = null;
			} 
			else
			{
				 eleve = new Eleve(res.getString(1), res.getString(2), res.getString(3), res.getString(4), res.getString(5));
			}
			res.close();
			statement.close();
			//connexion.close();
			
			return eleve;

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	public static Sujet selectSujetWithId(int id) {
		try {
			Class.forName("org.sqlite.JDBC");

			statement = connexion.createStatement();
			ResultSet res = statement.executeQuery("SELECT Identifiant, Nom, Description FROM Sujet"
					 + " WHERE Identifiant = '"+id+"' ;");

			Sujet sujet = new Sujet( res.getString(1),
					res.getString(2),
					res.getString(3));
			
			res.close();
			statement.close();

			return sujet;

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	public static Unite selectUniteWithId(String id) {
		try {
			Class.forName("org.sqlite.JDBC");

			statement = connexion.createStatement();
			ResultSet res = statement.executeQuery("SELECT Identifiant, Nom, Code, Cours, TD, TP, Valeur, Description FROM UniteEnseignement"
					+ " WHERE Identifiant = '"+id+"' ;");

			Unite unite = new Unite(
	        		 res.getString(1),
	        		 res.getString(2),
	        		 res.getString(8),
	        		 res.getString(3),
	        		 res.getFloat(4),
	        		 res.getFloat(5),
	        		 res.getFloat(6),
	        		 res.getFloat(7));
			
			res.close();
			statement.close();

			return unite;

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	public static Unite selectUnite(String id,String code, String intitule, float cour, float td, float tp, float valeur) {
		try {
			Class.forName("org.sqlite.JDBC");

			statement = connexion.createStatement();
			ResultSet res = statement.executeQuery("SELECT Identifiant, Nom, Code, Cours, TD, TP, Valeur, Description FROM UniteEnseignement"
					+ " WHERE Identifiant = '"+id+"' AND Nom ='"+intitule+"' AND Code = '"+code+"' AND Cours = "+cour+" AND TD = "+td+" And TP = "+tp+" AND Valeur = "+valeur+" ;");

			Unite unite;
			if (!res.next() ) {
				unite = null;
			} 
			else
			{
				unite = new Unite(
						res.getString(1),
		        		 res.getString(2),
		        		 res.getString(8),
		        		 res.getString(3),
		        		 res.getFloat(4),
		        		 res.getFloat(5),
		        		 res.getFloat(6),
		        		 res.getFloat(7));
			}

			res.close();
			statement.close();

			return unite;

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	public static Groupe selectGroupeWithId(String id) {
		try {
			Class.forName("org.sqlite.JDBC");

			statement = connexion.createStatement();
			ResultSet res = statement.executeQuery("SELECT Identifiant, Nom, IdentifiantUE, IdentifiantSujet FROM Groupe WHERE Identifiant = '" + id + "';");

			Groupe groupe = new Groupe( res.getString(1),
					res.getString(2),
					selectListEleveGroupe(res.getString(1)),
					(res.getInt(3)),
					(res.getInt(4)));
			
			res.close();
			statement.close();

			return groupe;

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	//Renvoie l'Id des groupes qui contiennent le sujet 
	public static List<String> selectGroupeWithSujet(String idSujet) {
		try {
			Class.forName("org.sqlite.JDBC");
			
			List<String> idGroupe = new ArrayList<>();
			
			statement = connexion.createStatement();
			ResultSet res = statement.executeQuery("SELECT Identifiant FROM Groupe WHERE IdentifiantSujet = '" + idSujet + "';");

			while ( res.next() ) {
		         idGroupe.add(res.getString(1));
			}
			
			res.close();
			statement.close();

			return idGroupe;

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	
	public static List<String> selectGroupeWithUE(String idUE) {
		try {
			Class.forName("org.sqlite.JDBC");
			
			List<String> idGroupe = new ArrayList<>();
			
			statement = connexion.createStatement();
			ResultSet res = statement.executeQuery("SELECT Identifiant FROM Groupe WHERE IdentifiantUE = '" + idUE + "';");

			while ( res.next() ) {
		         idGroupe.add(res.getString(1));
			}
			
			res.close();
			statement.close();

			return idGroupe;

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	//Fonctions update : U
	
	public static void assignationEleveGroupe(String idEleve, String idGroupe) {
		try {
			Class.forName("org.sqlite.JDBC");
			connexion.setAutoCommit(false);

			statement = connexion.createStatement();
			statement.executeUpdate("UPDATE Eleve SET IdentifiantGroupe = '" + idGroupe + "' WHERE Identifiant = '" + idEleve + "';");

			connexion.commit();
			
			statement.close();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void assignationSujetGroupe(String idSujet, String idGroupe) {
		try {
			Class.forName("org.sqlite.JDBC");
			connexion.setAutoCommit(false);

			statement = connexion.createStatement();
			statement.executeUpdate("UPDATE Groupe SET IdentifiantSujet = '" + idSujet + "' WHERE Identifiant = '" + idGroupe + "';");

			connexion.commit();
			
			statement.close();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void assignationUEGroupe(int idUE, int idGroupe) {
		try {
			Class.forName("org.sqlite.JDBC");
			connexion.setAutoCommit(false);

			statement = connexion.createStatement();
			statement.executeUpdate("UPDATE Groupe SET IdentifiantUE = " + idUE + " WHERE Identifiant = " + idGroupe + ";");

			connexion.commit();
			
			statement.close();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//On enleve la r�f�rence d'un groupe � un eleve
	public static void dereferencementEleveGroupe(String idEleve) {
		try {
			Class.forName("org.sqlite.JDBC");
			connexion.setAutoCommit(false);

			statement = connexion.createStatement();
			statement.executeUpdate("UPDATE Eleve SET IdentifiantGroupe = null WHERE Identifiant = '" + idEleve + "';");

			connexion.commit();
			
			statement.close();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//Fonctions delete : D
	public static void delete(String id, String table) {
		try {
			//Si on supprime un groupe alors on passe � null le IdGroupe de tous les eleves associ�s � ce groupe
			if(table.equals("Groupe"))
			{
				//On selectionne tous les eleves du groupe
				List<Eleve> eleves = selectListEleveGroupe(id);
				//Puis on enleve la r�ference
				for(int i = 0 ; i < eleves.size() ; i++)
				{
					dereferencementEleveGroupe(eleves.get(i).getId());
				}
			}
			//Si c'est un sujet on supprime le groupe auquel appartient ce sujet si il est dans un groupe
			else if(table.equals("Sujet"))
			{
				//On r�cupere l'id du groupe
				List<String> idGroupe = selectGroupeWithSujet(id);
				//Puis on le supprime
				for(int i = 0 ; i < idGroupe.size() ; i++)
				{
					delete(idGroupe.get(i), "Groupe");
				}
			}
			//Si c'est un UE on supprime le groupe auquel appartient cet UE si il est dans un groupe
			else if(table.equals("UniteEnseignement"))
			{
				//On r�cupere l'id du groupe
				List<String> idGroupe = selectGroupeWithUE(id);
				//Puis on le supprime
				for(int i = 0 ; i < idGroupe.size() ; i++)
				{
					delete(idGroupe.get(i), "Groupe");
				}
			}
			
			
			Class.forName("org.sqlite.JDBC");
			connexion.setAutoCommit(false);

			statement = connexion.createStatement();
			
			//Si on supprime un UE ou un Sujet alors on supprime le groupe associ�
			//else if(table.equals)
			
			statement.execute("DELETE FROM " + table + " WHERE Identifiant = '" + id + "';");

			connexion.commit();
			
			statement.close();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static int AleaId(String tableName) {
		int id;
		try {
			Class.forName("org.sqlite.JDBC");

			List<Integer> idGroupe = new ArrayList<>();

			statement = connexion.createStatement();
			ResultSet res = statement.executeQuery("SELECT Identifiant FROM " + tableName + " WHERE Identifiant >= (abs(random()) % (SELECT max(Identifiant) FROM "+tableName+")) LIMIT 1 ;");
			id = res.getInt(1);
			res.close();
			statement.close();

			return id;

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	public static Eleve AleaEleveWithoutGroupe() {

		try {
			Class.forName("org.sqlite.JDBC");

			statement = connexion.createStatement();
			ResultSet res = statement.executeQuery("SELECT * FROM Eleve WHERE IdentifiantGroupe IS NULL limit 1 ;");
			Eleve e = null ;
			while ( res.next() ) {
				e = new Eleve(
						res.getString(1),
						res.getString(2),
						res.getString(3),
						res.getString(4), res.getString(5)) ;
			}
			res.close();
			statement.close();
			return e ;

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static int EleveNumberWithoutGroupe () {
		int number;
		try {
			Class.forName("org.sqlite.JDBC");


			statement = connexion.createStatement();
			ResultSet res = statement.executeQuery("SELECT count(*) FROM Eleve WHERE IdentifiantGroupe IS NULL ;");
			number = res.getInt(1);
			res.close();
			statement.close();
			return number;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1 ;
	}

	public static int VerifId (String id , String tableName) {
		int number;
		try {
			Class.forName("org.sqlite.JDBC");
			statement = connexion.createStatement();
			ResultSet res = statement.executeQuery("SELECT count(*) FROM "+tableName+" WHERE Identifiant = '"+id+"' ;");
			number = res.getInt(1);
			res.close();
			statement.close();
			return number;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1 ;
	}
	

	
	
	//Fermer la base
	public static void fermerBD()
	{
		try {
			Class.forName("org.sqlite.JDBC");
			connexion.commit();
			connexion.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
