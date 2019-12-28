package Main;

import controller.ConnexionController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;


public class MainApp extends Application {
	
	//private String url="http://localhost:9090/swagger2-demo/";
    public static String url="";
       
    // La plateforme où l'on affiche les scènes
    private Stage primaryStage;
    private BorderPane rootLayout;

    public static void main(String[] args) {
    	
		try {
			Ini config = new Ini(new File("urlConfig.ini"));
			url=config.get("Serveur","URL");
		} catch (InvalidFileFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Application - Partie Groupe");

        initRootLayout();
        playSceneConnexion();
        //playSceneSample();
    }

    public void initRootLayout() {
        try {
            // Récupération du code XML de notre scène principale
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.this.getClass().getResource("/view/RootLayout.fxml"));
            rootLayout = loader.load();

            // Affichage de la scène dans notre "primaryStage"
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public  void playSceneConnexion() {
        try {
            // Récupération du code XML de notre scène sample
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/connexion.fxml"));

            GridPane connexionScene = loader.load();

            rootLayout.setCenter(connexionScene);
            ConnexionController myControllerHandle = (ConnexionController)loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  void playSceneSample() {
        try {
            // Récupération du code XML de notre scène sample
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/eleves.fxml"));

            GridPane sampleScene = loader.load();

            rootLayout.setCenter(sampleScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
	 
}
