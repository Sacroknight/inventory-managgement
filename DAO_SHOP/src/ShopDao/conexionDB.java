/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ShopDao;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
/**
 *
 * @author ajota
 */
public class conexionDB {
    private String url;
    private String usuario;
    private String contrasena;
    private Connection conn;

    /**
     * Constructor que toma los parámetros de conexión.
     * @param url       La URL de la base de datos.
     * @param usuario   El nombre de usuario para la conexión.
     * @param contrasena La contraseña para la conexión.
     */
    public conexionDB(String url, String usuario, String contrasena) {
        this.url = url;
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    /**
     * Constructor que lee la configuración desde un archivo de texto.
     * El archivo de texto debe tener las propiedades "jdbc.url", "db.user" y "db.password".
     */
    public conexionDB() {
        Properties properties = new Properties();
        try (BufferedReader br = new BufferedReader(new FileReader("src/dbconfig.txt"))) {
            properties.load(br);
            this.url = properties.getProperty("jdbc.url");
            this.usuario = properties.getProperty("db.user");
            this.contrasena = properties.getProperty("db.password");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Establece la conexión con la base de datos.
     * @return La conexión establecida.
     */
    public Connection establecerConexion() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            conn = DriverManager.getConnection(url, usuario, contrasena);
        } catch (Exception e) {
            System.out.println("No se puede cargar el Driver de MariaDB");
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * Cierra la conexión con la base de datos.
     */
    public void cerrarConexion() {
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
