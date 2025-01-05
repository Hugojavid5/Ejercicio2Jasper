package org.hugo.dein.jasperejercicio2;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.hugo.dein.jasperejercicio2.BBDD.ConexionBBDD;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 * Controlador principal para la ventana de generación de informes.
 * <p>
 * Esta clase gestiona la interacción con los elementos de la interfaz gráfica
 * de usuario y genera los informes mediante JasperReports.
 * </p>
 */
public class VentanaController {

    @FXML
    private Button btt_Aceptar;

    @FXML
    private Button btt_Cancelar;

    @FXML
    private ToggleGroup grupoInformes;

    @FXML
    private RadioButton rbPersonas;

    @FXML
    private RadioButton rbPCalculos;

    @FXML
    private RadioButton rbPSubinformes;

    /**
     * Metodo para generar un reporte usando JasperReports.
     *
     * @param reportePath Ruta del archivo Jasper (.jasper) que contiene el informe.
     * @param parameters Parámetros que se pasarán al informe, como rutas de imágenes o subinformes.
     */
    private void generarReporte(String reportePath, Map<String, Object> parameters) {
        try {
            // Establecer conexión a la base de datos
            ConexionBBDD db = new ConexionBBDD();

            // Cargar el archivo del reporte
            InputStream reportStream = getClass().getResourceAsStream(reportePath);

            if (reportStream == null) {
                System.out.println("El archivo no está ahí: " + reportePath);
                return;
            }

            // Cargar el reporte y generar el informe
            JasperReport report = (JasperReport) JRLoader.loadObject(reportStream);
            JasperPrint jprint = JasperFillManager.fillReport(report, parameters, db.getConnection());

            // Visualizar el informe generado
            JasperViewer viewer = new JasperViewer(jprint, false);
            viewer.setVisible(true);

        } catch (SQLException | JRException e) {
            e.printStackTrace();
            // Mostrar mensaje de error si no se puede generar el reporte
            mostrarError("Error en la base de datos", "No se pudo conectar a la base de datos o generar el informe.");
        }
    }

    /**
     * Metodo para mostrar un mensaje de error en un cuadro de diálogo.
     *
     * @param titulo El título del cuadro de diálogo.
     * @param mensaje El mensaje de error a mostrar.
     */
    private void mostrarError(String titulo, String mensaje) {
        // Crear una ventana emergente de tipo "error"
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Metodo que maneja la acción de clic en el botón "Aceptar" para generar el informe correspondiente.
     * Dependiendo de la opción seleccionada en los radio buttons, genera un informe diferente.
     *
     * @param event El evento que se dispara al hacer clic en el botón "Aceptar".
     */
    @FXML
    void Aceptar(ActionEvent event) {
        if (rbPersonas.isSelected()) {
            // Generar el informe de personas sin parámetros
            generarReporte("/Jasper/InformePersonas.jasper", null);
        } else if (rbPCalculos.isSelected()) {
            // Generar el informe de personas con cálculos y parámetros adicionales
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("IMAGE_PATH", getClass().getResource("/imagenes/").toString());
            generarReporte("/Jasper/InformePersonasConCalculos.jasper", parameters);
        } else if (rbPSubinformes.isSelected()) {
            // Generar el informe con subinformes y parámetros adicionales
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("Resource_PATH", getClass().getResource("/jrxml/").toString());
            generarReporte("/Jasper/Subinformes.jasper", parameters);
        }
    }

    /**
     * Metodo que maneja la acción de clic en el botón "Cancelar" para cerrar la aplicación.
     *
     * @param event El evento que se dispara al hacer clic en el botón "Cancelar".
     */
    @FXML
    void Cancelar(ActionEvent event) {
        // Cerrar la aplicación
        System.exit(0);
    }
}
