package org.camacho.jdbc.repositorio;

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
        try(
            Statement st = getConnection().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM PRODUCTOS");
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
        try (
            PreparedStatement ps = getConnection().prepareStatement("SELECT * FROM PRODUCTOS WHERE id = ?")
        ){
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                p = buscaProducto(rs);
            }
            /* En este caso tenemos que hacer un cierre manual porque no podemos
            * meterlo en el cierre automático. En el cierre automático sólo debe
            * estar las declaraciones de RECURSOS, nunca instrucciones
            */
            rs.close();
        } catch (SQLException e) {
            System.out.println("Error al conectar al buscar el producto: "  + e.getMessage().toUpperCase());
        }
        return p;
    }

    @Override
    public void guardar(Producto producto) {

    }

    @Override
    public void eliminar(Long id) {

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

        return p;
    }
}
