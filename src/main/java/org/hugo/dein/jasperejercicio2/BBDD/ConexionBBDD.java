package org.hugo.dein.jasperejercicio2.BBDD;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Clase que gestiona la conexión a la base de datos.
 * Proporciona métodos para establecer y cerrar la conexión,
 * así como cargar configuraciones desde un archivo de propiedades.
 */
public class ConexionBBDD {

    /**
     * Conexión única a la base de datos.
     */
    private static Connection connection;

    /**
     * Constructor de la clase que establece la conexión a la base de datos
     * utilizando las configuraciones cargadas desde el archivo de propiedades.
     *
     * @throws SQLException si ocurre un error al intentar establecer la conexión.
     */
    public ConexionBBDD() throws SQLException {
        Properties connConfig = loadProperties();
        String url = connConfig.getProperty("dburl");
        connection = DriverManager.getConnection(url, connConfig);
        connection.setAutoCommit(true);
    }

    /**
     * Obtiene la conexión actual a la base de datos.
     *
     * @return la conexión actual.
     */
    public static Connection getConnection() {
        return connection;
    }

    /**
     * Cierra la conexión a la base de datos si está abierta.
     *
     * @return null después de cerrar la conexión.
     * @throws SQLException si ocurre un error al cerrar la conexión.
     */
    public Connection closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            connection = null; // Se asigna null después de cerrar
        }
        return connection;
    }

    /**
     * Carga las configuraciones de conexión desde un archivo de propiedades.
     *
     * @return un objeto Properties que contiene las configuraciones de conexión.
     * @throws RuntimeException si ocurre un error al cargar el archivo de propiedades.
     */
    public static Properties loadProperties() {
        try (FileInputStream fs = new FileInputStream("configuration.properties")) {
            Properties props = new Properties();
            props.load(fs);
            return props;
        } catch (IOException e) {
            throw new RuntimeException("Error al cargar el archivo de propiedades", e);
        }
    }
}
