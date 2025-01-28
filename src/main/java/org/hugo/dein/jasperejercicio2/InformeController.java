package org.hugo.dein.jasperejercicio2;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.hugo.dein.jasperejercicio2.BBDD.ConexionBBDD;


import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Controlador para manejar las acciones del informe en la aplicación.
 * Permite mostrar diferentes tipos de informes Jasper según la opción seleccionada.
 */
public class InformeController {


    @FXML
    private ToggleGroup radioInforme;

    /**
     * Botón de opción (RadioButton) para seleccionar el informe de persona.
     */
    @FXML
    private RadioButton rbPersona;

    /**
     * Botón de opción (RadioButton) para seleccionar el informe de persona con cálculo.
     */
    @FXML
    private RadioButton rbPersonaCalculo;

    /**
     * Botón de opción (RadioButton) para seleccionar el informe de persona con subinforme.
     */
    @FXML
    private RadioButton rbPersonaSubinforme;

    /**
     * Acción que se ejecuta cuando el usuario acepta la selección.
     * Abre el informe correspondiente según el RadioButton seleccionado.
     *
     * @param event el evento generado por la acción.
     */
    @FXML
    void accionAceptar(ActionEvent event) {
        // Obtiene el RadioButton seleccionado
        RadioButton selectedRadio = (RadioButton) radioInforme.getSelectedToggle();

        if (selectedRadio == rbPersona) {
            abrirInformePersona();
        } else if (selectedRadio == rbPersonaCalculo) {
            abrirInformePersonaCalculo();
        } else {
            abrirInformePersonaSubinforme();
        }
    }

    /**
     * Acción que se ejecuta cuando el usuario cancela la selección.
     * Cierra la ventana del informe.
     *
     * @param event el evento generado por la acción.
     */
    @FXML
    void accionCancelar(ActionEvent event) {
        Stage stage = (Stage) rbPersona.getScene().getWindow();
        stage.close();
    }

    /**
     * Metodo que abre el informe de persona.
     * Carga el archivo Jasper correspondiente y lo visualiza.
     */
    public void abrirInformePersona(){
        ConexionBBDD db;
        try {
            // Crear una nueva conexión a la base de datos
            db = new ConexionBBDD();

            // Cargar el archivo Jasper del informe
            InputStream reportStream = db.getClass().getResourceAsStream("/Jasper/Personas.jasper");

            // Verificar si el archivo fue encontrado
            if (reportStream == null) {
                System.out.println("Archivo NO encontrado");
                return;
            }

            // Cargar el informe Jasper
            JasperReport report = (JasperReport) JRLoader.loadObject(reportStream);

            // Parámetros del informe (vacío si no necesitas pasar parámetros)
            Map<String, Object> parameters = new HashMap<>();

            // Llenar el informe con datos
            JasperPrint jprint = JasperFillManager.fillReport(report, parameters, db.getConnection());

            // Mostrar el informe
            JasperViewer viewer = new JasperViewer(jprint, false);
            viewer.setVisible(true);

        } catch (SQLException e) {
            System.err.println("Error de conexión a la base de datos:");
            e.printStackTrace();
        } catch (JRException e) {
            System.err.println("Error al procesar el informe Jasper:");
            e.printStackTrace();
        }
    }

    /**
     * Metodo que abre el informe de persona con cálculos.
     * Carga el archivo Jasper correspondiente y lo visualiza.
     */
    public void abrirInformePersonaCalculo(){
        ConexionBBDD db;
        try {
            // Crear una nueva conexión a la base de datos
            db = new ConexionBBDD();

            // Cargar el archivo Jasper del informe
            InputStream reportStream = db.getClass().getResourceAsStream("/Jasper/PersonaCalculo.jasper");

            // Verificar si el archivo fue encontrado
            if (reportStream == null) {
                System.out.println("Archivo NO encontrado");
                return;
            }

            // Cargar el informe Jasper
            JasperReport report = (JasperReport) JRLoader.loadObject(reportStream);

            // Parámetros del informe
            Map<String, Object> parameters = new HashMap<>();

            // Añadir la ruta de las imágenes
            String imagePath = db.getClass().getResource("/imagenes/").toString(); // Ruta de la carpeta de imágenes
            parameters.put("IMAGE_PATH", imagePath);

            // Llenar el informe con datos
            JasperPrint jprint = JasperFillManager.fillReport(report, parameters, db.getConnection());

            // Mostrar el informe
            JasperViewer viewer = new JasperViewer(jprint, false);
            viewer.setVisible(true);

        } catch (SQLException e) {
            System.err.println("Error de conexión a la base de datos:");
            e.printStackTrace();
        } catch (JRException e) {
            System.err.println("Error al procesar el informe Jasper:");
            e.printStackTrace();
        }
    }

    /**
     * Metodo que abre el informe de persona con subinformes.
     * Carga el archivo Jasper principal y los subinformes, y luego los visualiza.
     */
    public void abrirInformePersonaSubinforme() {
        ConexionBBDD db;
        try {
            // Crear una nueva conexión a la base de datos
            db = new ConexionBBDD();

            // Cargar el archivo Jasper del informe principal
            InputStream reportStream = getClass().getResourceAsStream("/Jasper/PersonaSubinforme.jasper");

            // Verificar si el archivo fue encontrado
            if (reportStream == null) {
                System.out.println("Archivo principal NO encontrado");
                return;
            }

            // Cargar el informe Jasper principal
            JasperReport report = (JasperReport) JRLoader.loadObject(reportStream);

            // Parámetros del informe
            Map<String, Object> parameters = new HashMap<>();

            // Configurar el parámetro Resource_PATH para subinformes
            String resourcePath = getClass().getResource("/Jasper/").toString();
            parameters.put("Resource_PATH", resourcePath);

            // Verificar y cargar el subinforme de email
            InputStream emailSubreportStream = getClass().getResourceAsStream("/Jasper/Email.jasper");
            if (emailSubreportStream == null) {
                System.out.println("Subinforme de Email NO encontrado");
                return;
            }
            parameters.put("EmailSubreport", resourcePath + "Email.jasper");

            // Verificar y cargar el subinforme de teléfono
            InputStream telefonoSubreportStream = getClass().getResourceAsStream("/Jasper/Telefono.jasper");
            if (telefonoSubreportStream == null) {
                System.out.println("Subinforme de Teléfono NO encontrado");
                return;
            }
            parameters.put("TelefonoSubreport", resourcePath + "Telefono.jasper");

            // Llenar el informe con datos
            JasperPrint jprint = JasperFillManager.fillReport(report, parameters, db.getConnection());

            // Mostrar el informe
            JasperViewer viewer = new JasperViewer(jprint, false);
            viewer.setVisible(true);

        } catch (SQLException e) {
            mostrarError("No se ha podido establecer conexion con la Base de Datos");
            e.printStackTrace();
        } catch (JRException e) {
            mostrarError("Error al procesar el informe Jasper");
            e.printStackTrace();
        }
    }

    /**
     * Metodo que muestra un mensaje de error en una ventana emergente.
     *
     * @param error El mensaje de error que se mostrará en la ventana emergente.
     */
    void mostrarError(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(error);
        alert.showAndWait();
    }

    /**
     * Inicializa el controlador, realizando la conexión a la base de datos.
     * Si la conexión a la base de datos falla, muestra un mensaje de error
     * y cierra la aplicación.
     */
    public void initialize() {
        // Controlar acceso a la base de datos
        try {
            new ConexionBBDD();
        } catch (SQLException e) {
            mostrarError("No se ha podido establecer conexion con la Base de Datos");
            Platform.exit(); // Cierra la aplicación
        }
    }

}
