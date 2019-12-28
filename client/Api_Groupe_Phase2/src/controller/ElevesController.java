package controller;
import Main.MainApp;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Window;
import model.Eleve;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import model.*;

public class ElevesController implements Initializable {

    @FXML
    private TextField nom;

    @FXML
    private TextField prenom;

    @FXML
    private TextField id;
    
    @FXML
    private TextField email;

    @FXML
    private TableView<Eleve> elevesTab;

    @FXML
    private ComboBox<Groupe> groupes_list;


    @FXML
    public TableColumn<Eleve, Integer> ids;

    @FXML
    public TableColumn<Eleve, String> noms;

    @FXML
    public TableColumn<Eleve, String> prenoms;

    @FXML
    public TableColumn<Eleve , Integer> groupess;

    @FXML
    private Button save;

    @FXML
    private Button delete;
    
    @FXML
    private Button update;

    @FXML
    private Button eleves;

    @FXML
    private Button unite;

    @FXML
    private Button sujet;

    @FXML
    private Button groupes;

    private ObservableList<Eleve> eleveModels ;
    
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
    protected void handleEnregistrerEleveAction(ActionEvent event) throws IOException {
        Window owner = groupes.getScene().getWindow();
        if(id.getText().isEmpty() || nom.getText().isEmpty() || prenom.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Erreur!",
                    "Veuillez entrez tous les champs");
            return ;
        }
        if (getJsonInt(url +"verifIdEleve/" + id.getText()) >= 1) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Erreur ! ",
                    "L'id " + id.getText() + " existe deja ! ");
            id.clear();
            return;
        }
        //Verification que tous les champs sont remplis
        if(id.getText().isEmpty() || nom.getText().isEmpty() || prenom.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Erreur!",
                    "Veuillez entrez tous les champs");
            return ;
        }
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        Eleve eleve= new Eleve(randomUUIDString, nom.getText(), prenom.getText(), email.getText());
        Unirest.setTimeouts(0, 0);
        try {
			HttpResponse<String> response = Unirest.post(url+"eleve")
			  .header("Content-Type", "application/json")
			  .body("{\n\t\"id\":\""+randomUUIDString+"\",\n\t\"nom\":\""+nom.getText()+"\",\n\t\"prenom\":\""+prenom.getText()+"\",\n\t\"email\":\""+email.getText()+"\"\n}")
			  .asString();
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (!groupes_list.getSelectionModel().isEmpty())
        {
            eleve.setIdGroupe(groupes_list.getSelectionModel().getSelectedItem().getId());
            insertOrUpdateOrDeleteEleve(url +"assignationEleveGroupe/"+eleve.getId()+"/"+groupes_list.getSelectionModel().getSelectedItem().getId());
            AlertHelper.showAlert(Alert.AlertType.INFORMATION, owner, "Enregister",
                    "L'eleve "+eleve.getNom()+" "+eleve.getPrenom()+" a ete ajoute avec succes et assigne au groupe "+groupes_list.getSelectionModel().getSelectedItem().getId() );

        }
        else
        AlertHelper.showAlert(Alert.AlertType.INFORMATION, owner, "Enregister",
          "eleve "+eleve.getNom()+" "+eleve.getPrenom()+" a ete ajoute avec succes" );

        elevesTab.getItems().add(eleve) ;

        Parent nextSceneParent = FXMLLoader.load(getClass().getResource("/view/eleves.fxml")) ;
        Scene nextScene = new Scene(nextSceneParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(nextScene);
        window.show();
         return ;
        
    }
    
    @FXML
    protected void handleMAJEleveAction(ActionEvent event) throws IOException {
        Window owner = groupes.getScene().getWindow();
        if(id.getText().isEmpty() || nom.getText().isEmpty() || prenom.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Erreur!",
                    "Veuillez entrez tous les champs");
            return ;
        }
        if (getJsonInt(url +"verifIdEleve/" + id.getText()) == 0) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Erreur ! ",
                    "L'id " + id.getText() + " n'existe pas ! ");
            id.clear();
            return;
        }
        
        Eleve eleve= new Eleve(id.getText(), nom.getText(), prenom.getText(), email.getText());
        Unirest.setTimeouts(0, 0);
        try {
			HttpResponse<String> response = Unirest.put(url+"eleve")
			  .header("Content-Type", "application/json")
			  .body("{\n\t\"id\":\""+id.getText()+"\",\n\t\"nom\":\""+nom.getText()+"\",\n\t\"prenom\":\""+prenom.getText()+"\",\n\t\"email\":\""+email.getText()+"\"\n}")
			  .asString();
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (!groupes_list.getSelectionModel().isEmpty())
        {
            eleve.setIdGroupe(groupes_list.getSelectionModel().getSelectedItem().getId());
            insertOrUpdateOrDeleteEleve(url +"assignationEleveGroupe/"+eleve.getId()+"/"+groupes_list.getSelectionModel().getSelectedItem().getId());
            AlertHelper.showAlert(Alert.AlertType.INFORMATION, owner, "Enregister",
                    "L'eleve "+eleve.getNom()+" "+eleve.getPrenom()+" a ete modifie avec succes et assigne au groupe "+groupes_list.getSelectionModel().getSelectedItem().getId() );

        }
        else
        AlertHelper.showAlert(Alert.AlertType.INFORMATION, owner, "Enregister",
          "eleve "+eleve.getNom()+" "+eleve.getPrenom()+" a ete modifie avec succes" );

        elevesTab.getItems().add(eleve) ;

        Parent nextSceneParent = FXMLLoader.load(getClass().getResource("/view/eleves.fxml")) ;
        Scene nextScene = new Scene(nextSceneParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(nextScene);
        window.show();
         return ;
        
    }
    
    @FXML
    protected void handleSupprimerEleveAction(ActionEvent event) throws IOException {
       Window owner = groupes.getScene().getWindow();
        if(id.getText().isEmpty() || nom.getText().isEmpty() || prenom.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Erreur!",
                    "Veuillez entrez tous les champs");
            return ;
        }
        
       Unirest.setTimeouts(0, 0);
       try {
		HttpResponse<String> response = Unirest.delete(url+"eleve/"+id.getText())
		     .header("Content-Type", "application/json")
		     .asString();
	} catch (UnirestException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
       id.clear();
       nom.clear();
       prenom.clear();
       AlertHelper.showAlert(Alert.AlertType.INFORMATION, owner, "Suppression",
               "eleve "+nom.getText()+ " "+prenom.getText()+" a ete supprime avec succes" );
        Parent nextSceneParent = FXMLLoader.load(getClass().getResource("/view/eleves.fxml")) ;
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
        prenoms.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        groupess.setCellValueFactory(new PropertyValueFactory<>("idGroupe"));
        //add your data to the table here.
               
        List<Eleve> list_eleve = getJsonEleve(url +"eleve");
        List<Groupe> list_groupe = getJsonGroupes(url +"groupe/");

        int i ;
        for( i = 0 ; i < list_eleve.size() ; i++)
        {
            elevesTab.getItems().add(list_eleve.get(i)) ;
        }

        ObservableList<Groupe> g = FXCollections.observableArrayList(list_groupe);
        groupes_list.setItems(g);

        //Double click sur une ligne
        elevesTab.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 1) {
                    Eleve e = elevesTab.getSelectionModel().getSelectedItem() ;
                    id.setText(String.valueOf(e.getId()));
                    nom.setText(e.getNom());
                    prenom.setText(e.getPrenom());
                    email.setText(e.getEmail());
                    id.setDisable(true);
                    save.setDisable(true);

                }
            }
        });

    }


    public void handleAddGroupe(ActionEvent actionEvent) {
        Window owner = groupes.getScene().getWindow();
        if(id.getText().isEmpty() || nom.getText().isEmpty() || prenom.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Erreur!",
                    "Veuillez entrez tous les champs");
            return ;
        }
        String identifiantEleve = id.getText() ;
        int identifiantGroupe = groupes_list.getSelectionModel().getSelectedItem().getId();
        insertOrUpdateOrDeleteEleve(url +"assignationEleveGroupe/"+identifiantEleve+"/"+identifiantGroupe);
        AlertHelper.showAlert(Alert.AlertType.INFORMATION, owner, "Ajout a un groupe",
                "eleve "+nom.getText()+ " "+prenom.getText()+" a ete assigne au groupe " + groupes_list.getSelectionModel().getSelectedItem().getNom()  );
    }

    public void handleRefresh(MouseEvent mouseEvent) throws IOException {
        Window owner = groupes.getScene().getWindow();
        Parent nextSceneParent = FXMLLoader.load(getClass().getResource("/view/eleves.fxml")) ;
        Scene nextScene = new Scene(nextSceneParent);
        Stage window = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();

        window.setScene(nextScene);
        window.show();
    }
    
    public List<Eleve> getJsonEleve(String url)
    {
		List<Eleve> eleveList = new ArrayList<Eleve>();
    	try {
			URL newUrl = new URL(url);
			BufferedReader br = new BufferedReader(new InputStreamReader(newUrl.openStream()));
			String strTemp = "";
			while (null != (strTemp = br.readLine())) {
				java.lang.reflect.Type listType = new TypeToken<ArrayList<Eleve>>(){}.getType();
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

    public void insertOrUpdateOrDeleteEleve(String url)
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
