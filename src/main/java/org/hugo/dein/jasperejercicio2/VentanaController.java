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

    private void generarReporte(String reportePath, Map<String, Object> parameters) {
        try {
            ConexionBBDD db = new ConexionBBDD();
            InputStream reportStream = getClass().getResourceAsStream(reportePath);

            if (reportStream == null) {
                System.out.println("El archivo no está ahí: " + reportePath);
                return;
            }

            JasperReport report = (JasperReport) JRLoader.loadObject(reportStream);
            JasperPrint jprint = JasperFillManager.fillReport(report, parameters, db.getConnection());
            JasperViewer viewer = new JasperViewer(jprint, false);
            viewer.setVisible(true);

        } catch (SQLException | JRException e) {
            e.printStackTrace();
            mostrarError("Error en la base de datos", "No se pudo conectar a la base de datos o generar el informe.");
        }
    }


    private void mostrarError(String titulo, String mensaje) {
        // Crear una ventana emergente de tipo "error"
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    void Aceptar(ActionEvent event) {
        if (rbPersonas.isSelected()) {
            generarReporte("/Jasper/InformePersonas.jasper", null);
        } else if (rbPCalculos.isSelected()) {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("IMAGE_PATH", getClass().getResource("/imagenes/").toString());
            generarReporte("/Jasper/InformePersonasConCalculos.jasper", parameters);
        } else if (rbPSubinformes.isSelected()) {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("Resource_PATH", getClass().getResource("/jrxml/").toString());
            generarReporte("/Jasper/Subinformes.jasper", parameters);
        }
    }

    @FXML
    void Cancelar(ActionEvent event) {
        System.exit(0);
    }
}