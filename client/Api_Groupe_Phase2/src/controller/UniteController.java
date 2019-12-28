package controller;
import Main.MainApp;
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
import model.Unite;
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

public class UniteController implements Initializable {

    @FXML
    private TextField nom;

    @FXML
    private TextField id;
    
    @FXML
    private TextField description;
    
    @FXML
    private TextField code;
    
    @FXML
    private TextField cours;
    
    @FXML
    private TextField td;
    
    @FXML
    private TextField tp;
    
    @FXML
    private TextField valeur;

    @FXML
    private ComboBox<Groupe> groupes_list;
    
    @FXML
    private TableView<Unite> UniteTab;
    
    @FXML
    public TableColumn<Unite, Integer> ids;

    @FXML
    public TableColumn<Unite, String> noms;

    @FXML
    public TableColumn<Unite, String> descriptions;


    @FXML
    private Button save;

    @FXML
    private Button delete;

    @FXML
    private Button eleves;

    @FXML
    private Button unites;

    @FXML
    private Button sujets;

    @FXML
    private Button update;

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
    protected void handleEnregistrerUniteAction(ActionEvent event) throws IOException {
         Window owner = groupes.getScene().getWindow();
        //Verification que tous les champs sont remplis
        if(id.getText().isEmpty() || nom.getText().isEmpty() || description.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Erreur!",
                    "Veuillez entrez tous les champs");
            return ;
        }

        if (getJsonInt(url +"verifIdUE/" + id.getText()) >= 1) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Erreur ! ",
                    "L'id " + id.getText() + " existe deja ! ");
            id.clear();
            return;
        }
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        Unite unite= new Unite(randomUUIDString, nom.getText(), description.getText(), code.getText(), Float.parseFloat(cours.getText()),Float.parseFloat(td.getText()),Float.parseFloat(tp.getText()),Float.parseFloat(valeur.getText()));
        Unirest.setTimeouts(0, 0);
        try {
			HttpResponse<String> response = Unirest.post(url+"UE")
			  .header("Content-Type", "application/json")
			  .body("{\n\t\"id\": \""+randomUUIDString+"\",\n\t\"nom\": \""+nom.getText()+"\",\n\t\"code\": \""+code.getText()+"\",\n\t\"cours\": "+unite.getCours()+",\n\t\"td\": "+unite.getTd()+",\n\t\"tp\": "+unite.getTp()+",\n\t\"valeur\": "+unite.getValeur()+",\n\t\"description\": \""+unite.getDescription().replace(" ", "_")+"\"\n}")
			  .asString();
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        UniteTab.getItems().add(unite) ;
         AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, owner, "Enregister",
                 "unite "+unite.getNom()+" a ete ajoute avec succes" );
        Parent nextSceneParent = FXMLLoader.load(getClass().getResource("/view/unites_enseignement.fxml")) ;
        Scene nextScene = new Scene(nextSceneParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(nextScene);
        window.show();
         return ;
    }
    
    @FXML
    protected void handleMAJUniteAction(ActionEvent event) throws IOException {
         Window owner = groupes.getScene().getWindow();
        //Verification que tous les champs sont remplis
        if(id.getText().isEmpty() || nom.getText().isEmpty() || description.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Erreur!",
                    "Veuillez entrez tous les champs");
            return ;
        }

        if (getJsonInt(url +"verifIdUE/" + id.getText()) == 0) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Erreur ! ",
                    "L'id " + id.getText() + " n'existe pas ! ");
            id.clear();
            return;
        }

        Unite unite= new Unite(id.getText(), nom.getText(), description.getText(), code.getText(), Float.parseFloat(cours.getText()),Float.parseFloat(td.getText()),Float.parseFloat(tp.getText()),Float.parseFloat(valeur.getText()));
        Unirest.setTimeouts(0, 0);
        try {
			HttpResponse<String> response = Unirest.put(url+"UE")
			  .header("Content-Type", "application/json")
			  .body("{\n\t\"id\": \""+unite.getId()+"\",\n\t\"nom\": \""+nom.getText()+"\",\n\t\"code\": \""+code.getText()+"\",\n\t\"cours\": "+unite.getCours()+",\n\t\"td\": "+unite.getTd()+",\n\t\"tp\": "+unite.getTp()+",\n\t\"valeur\": "+unite.getValeur()+",\n\t\"description\": \""+unite.getDescription().replace(" ", "_")+"\"\n}")
			  .asString();
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        UniteTab.getItems().add(unite) ;
         AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, owner, "Enregister",
                 "unite "+unite.getNom()+" a ete modifie avec succes" );
        Parent nextSceneParent = FXMLLoader.load(getClass().getResource("/view/unites_enseignement.fxml")) ;
        Scene nextScene = new Scene(nextSceneParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(nextScene);
        window.show();
         return ;
    }
    
    @FXML
    protected void handleSupprimerUniteAction(ActionEvent event) throws IOException {
       Window owner = groupes.getScene().getWindow();
        if(id.getText().isEmpty() || nom.getText().isEmpty() || description.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Erreur!",
                    "Veuillez entrez tous les champs");
            return ;
        }
        Unirest.setTimeouts(0, 0);
        try {
			HttpResponse<String> response = Unirest.delete(url+"UE/"+id.getText())
			  .header("Content-Type", "application/json")
			  .asString();
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


        AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, owner, "Suppression",
               "unite "+nom.getText()+" a ete supprime avec succes" );
       id.clear();
       nom.clear();
       description.clear();
        Parent nextSceneParent = FXMLLoader.load(getClass().getResource("/view/unites_enseignement.fxml")) ;
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
        List<Unite> list_unite = getJsonUE(url +"UE");
        int i ;
        for( i = 0 ; i < list_unite.size() ; i++)
        {
            UniteTab.getItems().add(list_unite.get(i)) ;
        }
        List<Groupe> list_groupe = getJsonGroupes(url +"groupe/");
        ObservableList<Groupe> g = FXCollections.observableArrayList(list_groupe);
        groupes_list.setItems(g);
        //Double click sur une ligne
        UniteTab.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 1) {
                    Unite e = UniteTab.getSelectionModel().getSelectedItem() ;
                    id.setText(String.valueOf(e.getId()));
                    nom.setText(e.getNom());
                    code.setText(String.valueOf(e.getCode()));
                    cours.setText(String.valueOf(e.getCours()));
                    td.setText(String.valueOf(e.getTd()));
                    tp.setText(String.valueOf(e.getTp()));
                    valeur.setText(String.valueOf(e.getValeur()));
                    description.setText(e.getDescription());
                    id.setDisable(true);
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
        int identifiantUnite = Integer.parseInt(id.getText()) ;
        int identifiantGroupe = groupes_list.getSelectionModel().getSelectedItem().getId();
        insertOrUpdateOrDeleteUE(url +"assignationUEGroupe/"+identifiantUnite+"/"+identifiantGroupe);
        AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, owner, "Ajout à un groupe",
                "L'UE "+nom.getText()+  "a ete assigne au groupe " + groupes_list.getSelectionModel().getSelectedItem().getNom()  );
    }

    public void handleRefresh(MouseEvent mouseEvent) throws IOException {
        Window owner = groupes.getScene().getWindow();
        Parent nextSceneParent = FXMLLoader.load(getClass().getResource("©/view/unites_enseignement.fxml")) ;
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
    public List<Unite> getJsonUE(String url)
    {
		List<Unite> uniteList = new ArrayList<Unite>();
    	try {
			URL newUrl = new URL(url);
			BufferedReader br = new BufferedReader(new InputStreamReader(newUrl.openStream()));
			String strTemp = "";
			while (null != (strTemp = br.readLine())) {
				java.lang.reflect.Type listType = new TypeToken<ArrayList<Unite>>(){}.getType();
				uniteList = new Gson().fromJson(strTemp, listType);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}    	
		return uniteList;
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

    public void insertOrUpdateOrDeleteUE(String url)
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