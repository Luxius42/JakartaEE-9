package org.camacho.jdbc.repositorio;

import org.camacho.jdbc.modelo.Categoria;
import org.camacho.jdbc.modelo.Producto;
import org.camacho.jdbc.util.ConectBBDD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta es una clase tipo DAO. A través de una interfaz, recoge los métodos que va
 * a utilizar. Importante recordar que, en todas las clases DAO, se puede implementar
 * métodos extra, y las interfaces son como unas reglas de métodos que sí o sí debería
 * tener por estructura.
 */
public class ProductoRepositorioIMPL implements Repositorio<Producto> {
    /*Recoge la conexión*/
    private Connection getConnection() throws SQLException {
        return ConectBBDD.getInstance();
    }

    @Override //--> Gracias a esto, se puede entender que viene de una interfaz o clase padre
    // Esta anotación de JAVA, permite una protección con el compilador y te avisa si has escrito mal un método
    // Lo que se hace es sobreescribir ese método heredado de la interfaz, le estás metiendo código al esqueleto de la interfaz
    public List<Producto> listar() {
        List <Producto> productos = new ArrayList<>();
        /*
        * Estos dos objetos, se cierran de forma automática con el auto close.
        * Un "AutoCLOSE" es cuando en la instrucción try..catch ya estamos insertando
        * los results set y/o statements, gracias a esto se cierra automáticamente la conexión
        * una vez finalice la ejecución. Estas instrucciones permiten un código seguro que evita
        * fugas de recursos. FUNCIONA A PARTIR DE JAVA 7, EN VERSIONES ANTERIORES HAY QUE REALIZAR FINALLY      *
        */
        String sql =
                "SELECT P.*, C.Descrip as Tipo " +
                "FROM PRODUCTOS P " +
                "INNER JOIN CATEGORIAS C ON P.idCategoria = C.idCategorias " +
                "ORDER BY P.id";

        //De esta forma, se abre y se cierra la conexión en tiempo de ejecución
        try(
            Connection conn = getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
        ) {
            while (rs.next()) {
                getProductos(rs, productos);
            }
        } catch (SQLException e) {
            System.out.println("Error al conectar al listar productos: "  + e.getMessage().toUpperCase());
        }
        return productos;
    }

    @Override
    public Producto porId(Long id) {
        Producto p = null;
        String sql =
                "SELECT P.*, C.Descrip as Tipo " +
                "FROM PRODUCTOS P " +
                "INNER JOIN CATEGORIAS C ON P.idCategoria = C.idCategorias " +
                "WHERE P.id = ? ";
        try (
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ){
            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    p = buscaProducto(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar el producto: "  + e.getMessage().toUpperCase());
        }
        return p;
    }

    @Override
    public void guardar(Producto producto) {
        String sql;
        if (producto.getId() != null && producto.getId() > 0) {
            sql = "UPDATE PRODUCTOS SET nombre = ?, precio = ?, idCategoria = ? WHERE ID = ?";
        } else {
            sql = "INSERT INTO PRODUCTOS(nombre, precio, idCategoria, fecha_registro) VALUES (?, ?, ?, ?)";
        }
        try (
                Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
            )
        {
            ps.setString(1, producto.getNombre());
            ps.setLong(2, producto.getPrecio());
            ps.setLong(3, producto.getCategoria().getIdCategoria());

            if (producto.getId() != null && producto.getId() > 0) {
                ps.setLong(4, producto.getId());
            } else {
                ps.setDate(4, new Date(producto.getFechaRegistro().getTime()));
            }


            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al conectar al insertar productos: "  + e.getMessage().toUpperCase());
        }
    }

    @Override
    public void eliminar(Long id) {
        String sql;
        if (id != null) {
            sql = "DELETE FROM PRODUCTOS WHERE ID = ?";
        }  else {
            throw new RuntimeException("No se puede eliminar el producto ya que no has seleccionado correctamente el Id o no existe");
        }

        try (
                Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
            ) {
            ps.setLong(1, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar producto: "  + e.getMessage().toUpperCase());
        }
    }
    /* MÉTODOS PRIVADOS */

    /**
     * Esta clase privada te devuelve los productos al array
     * @param rs Es la query que ha ejecutado
     * @param productos El array de los productos
     * @throws SQLException Manejo de error
     */
    private static void getProductos(ResultSet rs, List<Producto> productos) throws SQLException {
        /*
         * Hay que recordar que, además del nombre de la columna, se puede indicar
         * el número de la columna para traer el valor
         */
        Producto p = new Producto();
        p.setId(rs.getLong(1));
        p.setNombre(rs.getString(2));
        p.setPrecio(rs.getInt("precio"));
        p.setFechaRegistro(rs.getDate("fecha_registro")); //Esto permite pasar a .util el DATE, pero nunca de .sql a .util
        Categoria c = new Categoria();

        c.setIdCategoria(rs.getLong(5));
        c.setDescripcion(rs.getString(6));

        p.setCategoria(c);

        productos.add(p);
    }

    /**
     * Método que se utiliza para buscar el producto seleccionado
     * @param rs Valor de la consulta que se está realizando
     * @return Devuelve los valores que pedimos
     * @throws SQLException Manejo del error
     */
    private Producto buscaProducto(ResultSet rs) throws SQLException {
        Producto p  = new Producto();
        p.setId(rs.getLong(1));
        p.setNombre(rs.getString(2));
        p.setPrecio(rs.getInt(3));
        p.setFechaRegistro(rs.getDate(4));

        Categoria c = new Categoria();

        c.setIdCategoria(rs.getLong(5));
        c.setDescripcion(rs.getString(6));

        p.setCategoria(c);


        return p;
    }
}
