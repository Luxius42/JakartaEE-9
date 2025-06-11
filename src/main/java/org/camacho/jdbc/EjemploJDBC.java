package org.camacho.jdbc;

import org.camacho.jdbc.util.ConectBBDD;

import java.sql.*;

public class EjemploJDBC {
    public static void main(String[] args) {

        try (
                Connection con = ConectBBDD.getInstance(); //Se ha creado una clase única que contiene la conexión a la BBDD
                Statement  st = con.createStatement();
                ResultSet rs = st.executeQuery("select * from productos");
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
