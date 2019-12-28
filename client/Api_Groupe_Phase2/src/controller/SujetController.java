package controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import model.Groupe;
import model.Sujet;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import Main.MainApp;

public class SujetController implements Initializable {

    @FXML
    private TextField nom;

    @FXML
    private TextField id;

    @FXML
    private ComboBox<Groupe> groupes_list ;
    
    @FXML
    private TextField description;

    @FXML
    private TableView<Sujet> SujetTab;
    
    @FXML
    public TableColumn<Sujet, Integer> ids;

    @FXML
    public TableColumn<Sujet, String> noms;

    @FXML
    public TableColumn<Sujet, String> descriptions;

    @FXML
    private Button save;

    @FXML
    private Button delete;

    @FXML
    private Button eleves;

    @FXML
    private Button unite;

    @FXML
    private Button sujet;

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
    protected void handleEnregistrerSujetAction(ActionEvent event) throws IOException {
        Window owner = groupes.getScene().getWindow();
        //Verification que tous les champs sont remplis
        if(id.getText().isEmpty() || nom.getText().isEmpty() || description.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Erreur!",
                    "Veuillez entrez tous les champs");
            return ;
        }
        Sujet sujet=new Sujet(Integer.parseInt(id.getText()), nom.getText(),description.getText());
        if (getJsonInt(url +"verifIdSujet/" + id.getText()) >= 1) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Erreur ! ",
                    "L'id " + id.getText() + " existe deja ! ");
            id.clear();
            return;
        }
        
        Unirest.setTimeouts(0, 0);
        try {
			HttpResponse<String> response = Unirest.post(url+"sujet")
			  .header("Content-Type", "application/json")
			  .body("{\n\t\"id\":\""+sujet.getId()+"\",\n\t\"nom\":\""+sujet.getNom()+"\",\n\t\"description\":\""+sujet.getDescription().replace(" ", "_")+"\"\n}")
			  .asString();
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        SujetTab.getItems().add(sujet) ;

        AlertHelper.showAlert(Alert.AlertType.INFORMATION, owner, "Enregister",
                 "Sujet "+sujet.getNom()+" a ete ajoute avec succes" );
        Parent nextSceneParent = FXMLLoader.load(getClass().getResource("/view/sujets.fxml")) ;
        Scene nextScene = new Scene(nextSceneParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(nextScene);
        window.show();
         return ;
    }
    
    @FXML
    protected void handleSupprimerSujetAction(ActionEvent event) throws IOException {
       Window owner = groupes.getScene().getWindow();
        if(id.getText().isEmpty() || nom.getText().isEmpty() || description.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Erreur!",
                    "Veuillez entrez tous les champs");
            return ;
        }
       Unirest.setTimeouts(0, 0);
       try {
		HttpResponse<String> response = Unirest.delete(url+"sujet/"+id.getText())
		     .header("Content-Type", "application/json")
		     .body("")
		     .asString();
	} catch (UnirestException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
       AlertHelper.showAlert(Alert.AlertType.INFORMATION, owner, "Suppression",
               "Sujet "+nom.getText()+" a ete supprime avec succes" );
       id.clear();
       nom.clear();
       description.clear();
        Parent nextSceneParent = FXMLLoader.load(getClass().getResource("/view/sujets.fxml")) ;
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
        descriptions.setCellValueFactory(new PropertyValueFactory<>("description"));
        //add your data to the table here.
        List<Sujet> list_sujet = getJsonSujet(url +"sujet/");
        int i;
        for (i = 0; i < list_sujet.size(); i++) {
            SujetTab.getItems().add(list_sujet.get(i));
        }
        List<Groupe> list_groupe = getJsonGroupes(url +"groupe/");
        ObservableList<Groupe> g = FXCollections.observableArrayList(list_groupe);
        groupes_list.setItems(g);
        //Double click sur une ligne
        SujetTab.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 1) {
                    Sujet e = SujetTab.getSelectionModel().getSelectedItem() ;
                    id.setText(String.valueOf(e.getId()));
                    nom.setText(e.getNom());
                    description.setText(e.getDescription());
                    id.setDisable(true);
                    nom.setDisable(true);
                    description.setDisable(true);
                    save.setDisable(true);


                }
            }
        });

    }


    public void handleAddGroupe(ActionEvent actionEvent) {
        Window owner = groupes.getScene().getWindow();
        if(id.getText().isEmpty() || nom.getText().isEmpty() || description.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Erreur!",
                    "Veuillez entrez tous les champs");
            return ;
        }
        int identifiantSujet = Integer.parseInt(id.getText()) ;
        int identifiantGroupe = groupes_list.getSelectionModel().getSelectedItem().getId();
        insertOrUpdateOrDeleteSujet(url +"assignationSujetGroupe/"+identifiantSujet+"/"+identifiantGroupe);
        AlertHelper.showAlert(Alert.AlertType.INFORMATION, owner, "Ajout à un groupe",
                "Le sujet "+nom.getText()+  "a ete assigne au groupe " + groupes_list.getSelectionModel().getSelectedItem().getNom()  );
    }

    public void handleRefresh(MouseEvent mouseEvent) throws IOException {
        Window owner = groupes.getScene().getWindow();
        Parent nextSceneParent = FXMLLoader.load(getClass().getResource("/view/sujets.fxml")) ;
        Scene nextScene = new Scene(nextSceneParent);
        Stage window = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();

        window.setScene(nextScene);
        window.show();
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
    public List<Sujet> getJsonSujet(String url)
    {
		List<Sujet> sujetList = new ArrayList<Sujet>();
    	try {
			URL newUrl = new URL(url);
			BufferedReader br = new BufferedReader(new InputStreamReader(newUrl.openStream()));
			String strTemp = "";
			while (null != (strTemp = br.readLine())) {
				java.lang.reflect.Type listType = new TypeToken<ArrayList<Sujet>>(){}.getType();
				sujetList = new Gson().fromJson(strTemp, listType);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}    	
		return sujetList;
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

    public void insertOrUpdateOrDeleteSujet(String url)
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