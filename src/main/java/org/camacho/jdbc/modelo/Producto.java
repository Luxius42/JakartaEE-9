package org.camacho.jdbc.modelo;

import java.util.Date;

public class Producto {

    private Long id;
    private String nombre;
    private Integer precio;
    private Date fechaRegistro; //IMPORTANTE, cuando trabajemos con JAVA, traer siempre .util.
    //Si trabajamos ya directamente con BBDD, traer .sql
    private Categoria categoria;

    @Override
    public String toString() {
        return "Id: " + id +
                "| Descrip: " + nombre +
                " | Precio: " + precio + "€" +
                " | Fecha de registro: " + fechaRegistro +
                " | Categoria: " + categoria.getDescripcion();
    }

    public Producto() {
    }

    public Producto(Long id, String nombre, Integer precio, Date fechaRegistro) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.fechaRegistro = fechaRegistro;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
