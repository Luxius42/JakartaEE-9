package org.camacho.jdbc.util;

import org.apache.commons.dbcp2.BasicDataSource;

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
    //private static Connection connection;
    private static BasicDataSource pool;

    public static BasicDataSource getInstance() throws SQLException {
        if (pool == null) {
            pool = new BasicDataSource();
            pool.setUrl(url);
            pool.setUsername(user);
            pool.setPassword(password);

            pool.setInitialSize(3);
            pool.setMinIdle(5);
            pool.setMaxIdle(8);
            pool.setMaxTotal(10); //Esto indica el máximo de conexiones tanto activas como inactivas que puede existir
        }
        return pool;
    }

    public static Connection getConnection() throws SQLException {
        return getInstance().getConnection();
    }
}
