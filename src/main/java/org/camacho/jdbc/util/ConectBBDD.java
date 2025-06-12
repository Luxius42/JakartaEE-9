package org.camacho.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase única para gestionar la conexión a la base de datos. Nos permitirá reutilizar
 * esta conexión en otras clases distintas
 */
public class ConectBBDD {

    private static String url = "jdbc:mysql://localhost:3308/java_curso?serverTimezone=UTC";
    private static String password = "admin";
    private static String user = "root";
    private static Connection connection;

    public static Connection getInstance() {

        if (connection == null) {
            try {
                connection = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                System.out.println("Error al conectar a BBDD: "  + e.getMessage().toUpperCase());
            }
        }
        return connection;
    }
}
