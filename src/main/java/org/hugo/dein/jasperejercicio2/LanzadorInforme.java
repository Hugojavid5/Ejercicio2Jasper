package org.hugo.dein.jasperejercicio2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Clase principal de la aplicación para lanzar la ventana de informes.
 * <p>
 * Esta clase extiende {@link javafx.application.Application} y se encarga de cargar la interfaz gráfica
 * de la aplicación utilizando FXML.
 * </p>
 */
public class LanzadorInforme extends Application {

    /**
     * Metodo que inicializa la aplicación y carga la interfaz gráfica de usuario.
     *
     * @param stage El escenario principal donde se mostrará la aplicación.
     * @throws IOException Si ocurre un error al cargar el archivo FXML.
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Cargar el archivo FXML que contiene la interfaz de usuario
        FXMLLoader fxmlLoader = new FXMLLoader(LanzadorInforme.class.getResource("/fxml/ventana.fxml"));

        // Crear la escena con el archivo FXML cargado
        Scene scene = new Scene(fxmlLoader.load());

        // Configurar el título de la ventana
        stage.setTitle("INFORMES");

        // Establecer la escena en el escenario
        stage.setScene(scene);

        // Mostrar la ventana
        stage.show();
    }

    /**
     * Metodo principal que lanza la aplicación.
     * <p>
     * Este metodo invoca {@link javafx.application.Application#launch(String...)} para iniciar la aplicación.
     * </p>
     *
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        // Llamar al metodo launch() para iniciar la aplicación
        launch();
    }
}
