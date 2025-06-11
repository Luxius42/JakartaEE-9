package org.camacho.jdbc.repositorio;

import java.util.List;


/**
 * Todo repositorio debe tener un CRUD para cada tabla de
 * la BBDD. Con esta interfaz lo que conseguiremos será que
 * todas las clases que creemos de la tabla producto, tengan la misma
 * estructura de métodos.
 * @param <T> Dentro de <T> se mete la clase que usará los métodos
 */
public interface Repositorio <T> {

    List<T> listar();

    T porId(Long id);

    void guardar(T t);

    void eliminar(Long id);

}
