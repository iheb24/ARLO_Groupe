package DataBase;

import java.util.List;
import com.example.springbootswagger2.model.*;

public interface DatabaseInterface {
    //Initialisation
    public static void initialisationBD(){};

    //Create
    public static  void insertEleve(int id , String prenom, String nom){};
    public static  void insertSujet(int id ,String nom, String description){};
    public static  void insertUE(int id ,String nom, String description){};
    public static  void insertGroupe(int id , String nom )	{};

    public static  int lastId(String table) {return -1 ;};

    //Read
    public static List<Eleve> selectListEleve(){return null ;};
    public static  List<Eleve> selectListEleveGroupe(int idGroupe){return null;};
    public static  List<Sujet> selectListSujet(){return null;};
    public static  List<Unite> selectListUnite(){return null;};
    public static  List<Groupe> selectListGroupe(){return null;};
    public static  Eleve selectEleveWithId(int id) {return null;};
    public static  Sujet selectSujetWithId(int id) {return null;};
    public static  Unite selectUniteWithId(int id) {return null;};
    public static  Groupe selectGroupeWithId(int id) {return null;};
    public static  List<Integer> selectGroupeWithSujet(int idSujet) {return null;};
    public static  List<Integer> selectGroupeWithUE(int idUE) {return null ;};

    public static  void assignationEleveGroupe(int idEleve, int idGroupe){};
    public static  void assignationSujetGroupe(int idSujet, int idGroupe){};
    public static Eleve AleaEleveWithoutGroupe() {return null;};
}
