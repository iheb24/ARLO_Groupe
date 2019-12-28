package controller;
import Main.MainApp;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Window;
import model.Eleve;
import model.Groupe;
import javafx.scene.Scene;
import javafx.stage.Stage;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static java.lang.Thread.sleep;

public class GroupeController implements Initializable {

    @FXML
    private TextField nom;

    @FXML
    private TextField id;
    
    @FXML
    private TableView<Groupe> GroupeTab;
    
    @FXML
    public TableColumn<Groupe, Integer> ids;

    @FXML
    public TableColumn<Groupe, String> noms;

    @FXML
    public TableColumn<Groupe, Integer> unitess;

    @FXML
    public TableColumn<Groupe, Integer> sujetss;

    @FXML
    private Button enregistrer;

    @FXML
    private Button delete;

    @FXML
    private Button eleves;

    @FXML
    private Button unite;

    @FXML
    private Button save;

    @FXML
    private Button groupes;    

    private String url=MainApp.url;

    @FXML
    protected void handleGroupeAction(ActionEvent event) throws IOException {
        Window owner = groupes.getScene().getWindow();
        Parent nextSceneParent = FXMLLoader.load(getClass().getResource("/view/groupes.fxml")) ;
        Scene nextScene = new Scene(nextSceneParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(nextScene);
        window.show();
    }
    @FXML
    protected void handleEleveAction(ActionEvent event) throws IOException {
        Window owner = groupes.getScene().getWindow();
        Parent nextSceneParent = FXMLLoader.load(getClass().getResource("/view/eleves.fxml")) ;
        Scene nextScene = new Scene(nextSceneParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(nextScene);
        window.show();
    }
    @FXML
    protected void handleSujetAction(ActionEvent event) throws IOException {
        Window owner = groupes.getScene().getWindow();
        Parent nextSceneParent = FXMLLoader.load(getClass().getResource("/view/sujets.fxml")) ;
        Scene nextScene = new Scene(nextSceneParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(nextScene);
        window.show();
    }
    @FXML
    protected void handleUniteAction(ActionEvent event) throws IOException {
        Window owner = groupes.getScene().getWindow();
        Parent nextSceneParent = FXMLLoader.load(getClass().getResource("/view/unites_enseignement.fxml")) ;
        Scene nextScene = new Scene(nextSceneParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(nextScene);
        window.show();
    }
    
    @FXML
    protected void handleEnregistrerGroupeAction(ActionEvent event) throws IOException {
        Window owner = groupes.getScene().getWindow();
        if(id.getText().isEmpty() || nom.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Erreur!",
                    "Veuillez entrez tous les champs");
            return ;
        }
        if (getJsonInt(url +"verifIdGroupe/" + id.getText()) >= 1) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Erreur ! ",
                    "L'id " + id.getText() + " existe deja ! ");
            id.clear();
            return;
        }
        //Verification que tous les champs sont remplis
        if(id.getText().isEmpty() || nom.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Erreur!",
                    "Veuillez entrez tous les champs");
            return ;
        }
        Groupe groupe = new Groupe(Integer.parseInt(id.getText()), nom.getText());
        Unirest.setTimeouts(0, 0);
        try {
			HttpResponse<String> response = Unirest.post(url+"groupe")
			  .header("Content-Type", "application/json")
			  .body("{\n\t\"id\":\""+groupe.getId()+"\",\n\t\"nom\":\""+groupe.getNom()+"\"\n}")
			  .asString();
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        GroupeTab.getItems().add(groupe) ;

        AlertHelper.showAlert(Alert.AlertType.INFORMATION, owner, "Enregister",
                "le groupe "+groupe.getNom()+" a ete ajoute avec succes" );
        Parent nextSceneParent = FXMLLoader.load(getClass().getResource("/view/groupes.fxml")) ;
        Scene nextScene = new Scene(nextSceneParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(nextScene);
        window.show();
        return ;
    }
    
    @FXML
    protected void handleSupprimerGroupeAction(ActionEvent event) throws IOException {
        Window owner = groupes.getScene().getWindow();
        if(id.getText().isEmpty() || nom.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Erreur!",
                    "Veuillez entrez tous les champs");
            return ;
        }
        Unirest.setTimeouts(0, 0);
        try {
			HttpResponse<String> response = Unirest.delete(url+"groupe/"+id.getText())
			  .header("Content-Type", "application/json")
			  .body("")
			  .asString();
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        id.clear();
        nom.clear();
        AlertHelper.showAlert(Alert.AlertType.INFORMATION, owner, "Suppression",
                "le groupe "+nom.getText()+" a ete supprime avec succes" );
        id.clear();
        nom.clear();
        Parent nextSceneParent = FXMLLoader.load(getClass().getResource("/view/groupes.fxml")) ;
        Scene nextScene = new Scene(nextSceneParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(nextScene);
        window.show();
        return ;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ids.setCellValueFactory(new PropertyValueFactory<>("id"));
        noms.setCellValueFactory(new PropertyValueFactory<>("nom"));
        sujetss.setCellValueFactory(new PropertyValueFactory<>("sujet"));
        unitess.setCellValueFactory(new PropertyValueFactory<>("unite"));


        //add your data to the table here.
        List<Groupe> list_groupe = list_groupe = getJsonGroupes(url +"groupe/");
        int i ;
        for( i = 0 ; i < list_groupe.size() ; i++)
        {
            GroupeTab.getItems().add(list_groupe.get(i)) ;
        }
        GroupeTab.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 1) {
                    Groupe e = GroupeTab.getSelectionModel().getSelectedItem() ;
                    id.setText(String.valueOf(e.getId()));
                    nom.setText(e.getNom());
                    id.setDisable(true);
                    nom.setDisable(true);
                    save.setDisable(true);

                }
            }
        });

    }

    public void handleRefresh(MouseEvent mouseEvent) throws IOException {
        Window owner = groupes.getScene().getWindow();
        Parent nextSceneParent = FXMLLoader.load(getClass().getResource("/view/groupes.fxml")) ;
        Scene nextScene = new Scene(nextSceneParent);
        Stage window = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();

        window.setScene(nextScene);
        window.show();
    }

    public void handlegenerate(ActionEvent actionEvent) throws InterruptedException {
        //Génération de groupe de façon alèatoire
        //La génération inclut l'affectation d'élèves à ces groupes
        //Et une affectation de sujet et unite
        Window owner = groupes.getScene().getWindow();
        List<Integer> idsEleve = null;
        int eleveWithoutGroupe = getJsonInt(url + "eleveNumberWithoutGroupe");
        if (eleveWithoutGroupe == 0 ) {
            int max_id = getJsonInt(url+"lastId");
            max_id ++;
            String nom = "G"+String.valueOf(max_id);
            //On crée le groupe
            Groupe g = new Groupe(max_id,nom);

            Unirest.setTimeouts(0, 0);
            try {
    			HttpResponse<String> response = Unirest.post(url+"groupe")
    			  .header("Content-Type", "application/json")
    			  .body("{\n\t\"id\":\""+g.getId()+"\",\n\t\"nom\":\""+g.getNom()+"\"\n}")
    			  .asString();
    		} catch (UnirestException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
            int idSujet =getJsonInt(url+"aleaId/"+"Sujet");
            int idUE = getJsonInt(url+"aleaId/"+"UniteEnseignement");
            insertOrUpdateOrDeleteGroupe(url +"assignationSujetGroupe/"+idSujet+"/"+max_id);
            insertOrUpdateOrDeleteGroupe(url +"assignationUEGroupe/"+idUE+"/"+max_id);

            AlertHelper.showAlert(Alert.AlertType.INFORMATION, owner, "Important !",
                    "Tous les élèves possédent des groupes. Le groupe qui a ete cree n'est pas affecte a des eleves");
            AlertHelper.showAlert(Alert.AlertType.INFORMATION, owner, "Creation de groupe",
                    "Le groupe "+nom+" dont l'id est "+max_id+" a ete cree a ete assigne a l'UE "+idUE+" et au sujet "+idSujet);
        }
        else {
            int index = 0 ;
            int tmp = eleveWithoutGroupe ;
            while (index <= eleveWithoutGroupe)
            {
                int boucle = 3 ;
                if (tmp/3 == 0) {tmp = tmp % 3 ; boucle = tmp ;}
                else {tmp = tmp - 3 ; boucle = 3 ;}


                index += 3 ;
                int idUE = getJsonInt(url+"aleaId/"+"UniteEnseignement");
                int idSujet = getJsonInt(url+"aleaId/"+"Sujet") ;
                int max_id = getJsonInt(url+"lastId");
                max_id ++ ;
                String nom = "G" + String.valueOf(max_id);
                //On crée le groupe
                Groupe g = new Groupe(max_id, nom);
                Unirest.setTimeouts(0, 0);
                try {
        			HttpResponse<String> response = Unirest.post(url+"groupe")
        			  .header("Content-Type", "application/json")
        			  .body("{\n\t\"id\":\""+g.getId()+"\",\n\t\"nom\":\""+g.getNom()+"\"\n}")
        			  .asString();
        		} catch (UnirestException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
                //On affecte l'UE et le Sujet Aléa
                insertOrUpdateOrDeleteGroupe(url +"assignationSujetGroupe/"+idSujet+"/"+max_id);
                insertOrUpdateOrDeleteGroupe(url +"assignationUEGroupe/"+idUE+"/"+max_id);
                //On affecte pour chaque 3 élèves un groupe
                for (int i = 0; i< boucle ; i ++ ) {
                    Eleve e = null;

                    e = getJsonEleve(url+"/AleaEleveWithoutGroupe");

                    insertOrUpdateOrDeleteGroupe(url +"assignationEleveGroupe/"+e.getId()+"/"+max_id);
                   
                }

                AlertHelper.showAlert(Alert.AlertType.INFORMATION, owner, "Creation de groupe",
                        "Le groupe " + nom + " " + max_id + " a ete cree. L'UE " + idUE + " et le sujet " + idSujet+" lui ont ete attribues");
                 AlertHelper.showAlert(Alert.AlertType.INFORMATION, owner, "Creation de groupe",
                    "Des eleves ont ete assigne à ce groupe" ) ;
            }
        }


    }
    
    
    
    public Eleve getJsonEleve(String url)
    {
		Eleve eleveList=null;
    	try {
			URL newUrl = new URL(url);
			BufferedReader br = new BufferedReader(new InputStreamReader(newUrl.openStream()));
			String strTemp = "";
			while (null != (strTemp = br.readLine())) {
				java.lang.reflect.Type listType = new TypeToken<Eleve>(){}.getType();
				eleveList = new Gson().fromJson(strTemp, listType);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}    	
		return eleveList;
    }
    public List<Groupe> getJsonGroupes(String url)
    {
		List<Groupe> groupeList = new ArrayList<Groupe>();
    	try {
			URL newUrl = new URL(url);
			BufferedReader br = new BufferedReader(new InputStreamReader(newUrl.openStream()));
			String strTemp = "";
			while (null != (strTemp = br.readLine())) {
				java.lang.reflect.Type listType = new TypeToken<ArrayList<Groupe>>(){}.getType();
				groupeList = new Gson().fromJson(strTemp, listType);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}    	
		return groupeList;
    }
    public int getJsonInt(String url)
    {
		int id=-1;
    	try {
			URL newUrl = new URL(url);
			BufferedReader br = new BufferedReader(new InputStreamReader(newUrl.openStream()));
			String strTemp = "";
			while (null != (strTemp = br.readLine())) {
				id= Integer.parseInt(strTemp);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}    	
		return id;
    }

    public void insertOrUpdateOrDeleteGroupe(String url)
    {
    	try {
			URL newUrl = new URL(url);
			BufferedReader br = new BufferedReader(new InputStreamReader(newUrl.openStream()));
			String strTemp = "";
			while (null != (strTemp = br.readLine())) {
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}    	
    }
    
}