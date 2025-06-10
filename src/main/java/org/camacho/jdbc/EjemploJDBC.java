package org.camacho.jdbc;

import java.sql.*;

public class EjemploJDBC {
    public static void main(String[] args) {

        /*Datos de conexión*/
        String url = "jdbc:mysql://localhost:3308/java_curso?serverTimezone=UTC";
        String user = "root";
        String password = "admin";

        try ( //De esta forma, el manejo de errores se cerrará conforme falle la conexión y no es necesario hacer un finally
              //Hacerlo de esta manera, permite un código mucho más legible y limpio
                Connection con = DriverManager.getConnection(url, user, password);
                Statement  st = con.createStatement();
                ResultSet rs = st.executeQuery("select * from productos2");
             )
        {
            try {
                while (rs.next()) {
                    System.out.println( "Id: " + rs.getInt(1));
                    System.out.println("Producto: " + rs.getString(2));
                    System.out.println("Precio: " + rs.getInt(3) + '€');
                    System.out.println("Fecha de inserción: " + rs.getDate(4));
                }
            } catch (SQLException e) {
                System.out.println("Error al obtener datos: "  + e.getMessage().toUpperCase());
               // throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            System.out.println("Error a la hora de conectar a la base de datos: " + e.getMessage().toUpperCase());
           //throw new RuntimeException("Error conectando a la base de datos: ", e);
        }
    }
}
