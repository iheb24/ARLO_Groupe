package com.example.springbootswagger2.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ConnexionController {

    public boolean connexion_value ;

    @FXML
    private TextField identifiant;

    @FXML
    private PasswordField mdp;

    @FXML
    private Button connexion;

    @FXML
    protected void handleSubmitButtonAction(ActionEvent event) throws IOException {
        Window owner = identifiant.getScene().getWindow();
        if(identifiant.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Erreur!",
                    "Entrez votre login");
            return ;
        }

        if(mdp.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Erreur!",
                    "Entrez votre mot de passe");
            return ;
        }

        if(!(mdp.getText().equals("admin") && identifiant.getText().equals("admin"))) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Erreur!",
                    "Probleme login ou mot de passe");
            mdp.clear();
            identifiant.clear();
            return ;
        }

        AlertHelper.showAlert(Alert.AlertType.INFORMATION, owner, "Connexion etablie!",
                "Welcome " + identifiant.getText());
        this.connexion_value = true ;

        Parent nextSceneParent = FXMLLoader.load(getClass().getResource("/view/eleves.fxml")) ;
        Scene nextScene = new Scene(nextSceneParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(nextScene);
        window.show();


    }

}