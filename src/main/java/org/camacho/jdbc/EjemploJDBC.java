package org.camacho.jdbc;

import java.sql.*;

public class EjemploJDBC {
    public static void main(String[] args) {

        /*Datos de conexión*/
        String url = "jdbc:mysql://localhost:3308/java_curso?serverTimezone=UTC";
        String user = "root";
        String password = "admin";

        Connection con = null; //Se declara fuera para tener una variable global y así poder usarlo luego en el finally
        Statement st = null;
        ResultSet rs = null;
        try {

            con = DriverManager.getConnection(url, user, password);
            st = con.createStatement();
            rs = st.executeQuery("select * from productos2");

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
        }finally {

            try {
                System.out.println("Cerrando conexion...");
                rs.close();
                st.close();
                con.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar el conexion: " + e.getMessage().toUpperCase());
                //throw new RuntimeException(e);
            }


        }


    }

}
