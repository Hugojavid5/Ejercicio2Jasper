package org.hugo.dein.jasperejercicio2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LanzadorInforme extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LanzadorInforme.class.getResource("/fxml/ventana.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("INFORMES");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}