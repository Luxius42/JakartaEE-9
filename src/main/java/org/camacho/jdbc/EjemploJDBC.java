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

        /*      GRACIAS A ESTO, SI TENEMOS DUDAS SOBRE CÓMO RESUELVE/DEVUELVE DATOS
                NUESTRO LISTADO, PODEMOS DEBUGUEAR LA CONSULTA

            String sql = """
            SELECT P.*, C.Descrip AS Tipo
            FROM PRODUCTOS P
            INNER JOIN CATEGORIAS C ON P.idCategoria = C.idCategorias
            ORDER BY P.id
        """;

            try (
                    Connection conn = ConectBBDD.getInstance();
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ResultSet rs = ps.executeQuery()
            ) {
                    ResultSetMetaData meta = rs.getMetaData();
                    int columnCount = meta.getColumnCount();

                    System.out.println("Número de columnas: " + columnCount);
                    System.out.println("===================================");

                    for (int i = 1; i <= columnCount; i++) {
                            System.out.println(
                                    i + ": " + meta.getColumnName(i) +
                                            " (" + meta.getColumnTypeName(i) + ")"
                            );
                    }

                    // Si quieres ver también una fila de datos:
                    if (rs.next()) {
                            System.out.println("\n--- Primera fila ---");
                            for (int i = 1; i <= columnCount; i++) {
                                    System.out.println(i + ": " + rs.getObject(i));
                            }
                    }

            } catch (SQLException e) {
                    e.printStackTrace();
            }

         */
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
