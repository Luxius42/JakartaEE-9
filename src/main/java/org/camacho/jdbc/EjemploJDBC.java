package org.camacho.jdbc;

import java.sql.*;

public class EjemploJDBC {
    public static void main(String[] args) {

        /*Datos de conexión*/
        String url = "jdbc:mysql://localhost:3308/java_curso?serverTimezone=UTC";
        String user = "root";
        String password = "admin";

        try {

            Connection con = DriverManager.getConnection(url, user, password);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from productos");

            try {
                while (rs.next()) {
                    System.out.println( "Id: " + rs.getInt(1));
                    System.out.println("Producto: " + rs.getString(2));
                    System.out.println("Precio: " + rs.getInt(3) + '€');
                    System.out.println("Fecha de inserción: " + rs.getDate(4));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            rs.close();
            st.close();
            con.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

}
