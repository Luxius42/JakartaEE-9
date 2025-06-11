package org.camacho.jdbc;

import org.camacho.jdbc.modelo.Producto;
import org.camacho.jdbc.repositorio.ProductoRepositorioIMPL;
import org.camacho.jdbc.repositorio.Repositorio;
import org.camacho.jdbc.util.ConectBBDD;

import java.sql.*;

public class EjemploJDBC {
    public static void main(String[] args) {

        try (
                Connection con = ConectBBDD.getInstance(); //Se ha creado una clase única que contiene la conexión a la BBDD
             )
        {
            Repositorio <Producto> rep = new ProductoRepositorioIMPL();
            /* Es lo mismo todo
            * List<Producto> productos = rep.listar();
            *   for (Producto p : productos) {
            *       System.out.println(p);
            *   }
            * rep.listar().forEach( p -> System.out.println(p.getNombre()));
            * */
            rep.listar().forEach(System.out::println);
            System.out.println(rep.porId(3L));


        } catch (SQLException e) {
            System.out.println("Error a la hora de conectar a la base de datos: " + e.getMessage().toUpperCase());
           //throw new RuntimeException("Error conectando a la base de datos: ", e);
        }
    }
}
