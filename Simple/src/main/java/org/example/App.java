package org.example;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class App extends Application {
    private static Stage primaryStage;
    private static Scene mainScene;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;

        // Login ablak
        FXMLLoader loader = new FXMLLoader(App.class.getResource("fxml/login.fxml"));
        Stage loginStage = new Stage();
        loginStage.initModality(Modality.APPLICATION_MODAL);
        loginStage.setScene(new Scene(loader.load()));
        loginStage.setTitle("Lentory Login");
        loginStage.setResizable(false);
        loginStage.showAndWait();

        // Login eredmény
        LoginController loginController = loader.getController();
        if (loginController.isLoginSuccessful()) {
            // Main ablak
            FXMLLoader mainLoader = new FXMLLoader(App.class.getResource("fxml/main.fxml"));
            mainScene = new Scene(mainLoader.load());
            primaryStage.setScene(mainScene);
            primaryStage.setTitle("Lentory 0.0.2");
            primaryStage.show();
        } else {
            System.exit(0);
        }
    }

    // A későbbiekben ahelyett, hogy egy új ablakot nyitna meg lecseréli a jelenlegit. - Hunor
    public static void setRoot(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("fxml/" + fxml + ".fxml"));
        mainScene.setRoot(loader.load());
    }

    public static void main(String[] args) {
        launch();
    }
}
