package org.camacho.jdbc;

import org.camacho.jdbc.modelo.Categoria;
import org.camacho.jdbc.modelo.Producto;
import org.camacho.jdbc.repositorio.ProductoRepositorioIMPL;
import org.camacho.jdbc.repositorio.Repositorio;
import org.camacho.jdbc.util.ConectBBDD;

import java.sql.*;
import java.util.Map;

public class EjemploJDBC {
    public static void main(String[] args) {

            Repositorio <Producto> rep = new ProductoRepositorioIMPL();

            /* Es lo mismo todo
            * List<Producto> productos = rep.listar();
            *   for (Producto p : productos) {
            *       System.out.println(p);
            *   }
            * rep.listar().forEach( p -> System.out.println(p.getNombre()));
            * */
            System.out.println("=========== LISTAR ============");
            System.out.println();

            /* Con esto, se puede debuguear áculmente los valores que estamos utilizando
            * Posición del ResultSet y el valor que estamos mostrando. Usaremos la  clase JDBC ResultSetMetadata para ello*/
            ProductoRepositorioIMPL rep2 = new ProductoRepositorioIMPL();
            try {
                    Map<Integer, String> columnas = rep2.mapaColumnas();
                    columnas.forEach((pos, nombre) ->
                            System.out.println(pos + ": " + nombre)
                    );
            } catch (SQLException e) {
                    throw new RuntimeException(e);
            }
            System.out.println();

            rep.listar().forEach(System.out::println);

            System.out.println();
            System.out.println("=========== VER POR ID ============");
            System.out.println();

            System.out.println(rep.porId(5L));

            System.out.println();
            System.out.println("=========== INSERTAR ============");
            System.out.println();


/*
                Producto p = new Producto();
                p.setNombre("NVIDIA RTX5600 DDR 5 32GB");
                p.setPrecio(650);
                p.setFechaRegistro(new java.util.Date());

                Categoria c = new Categoria();
                c.setIdCategoria(4L);
                p.setCategoria(c);

                rep.guardar(p);


            rep.listar().forEach(System.out::println);
*/
            System.out.println();
            System.out.println("=========== MODIFICAR ============");
            System.out.println();

            Producto m = new Producto();
            m.setNombre("Pantalla LED 16\"");
            m.setPrecio(150);
            m.setId(16L);

            Categoria c = new Categoria();
            c.setIdCategoria(2L);
            m.setCategoria(c);

            rep.guardar(m);

            rep.listar().forEach(System.out::println);

            System.out.println();
            System.out.println("=========== ELIMINAR ============");
            System.out.println();

            rep.eliminar(18L);
            rep.listar().forEach(System.out::println);

    }
}
