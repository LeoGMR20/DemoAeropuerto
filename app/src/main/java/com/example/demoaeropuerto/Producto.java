package com.example.demoaeropuerto;

public class Producto {

    //Atributos

    private int id;
    private String codigo;
    private String descripcion;
    private int precio;
    private int habilitado;

    public Producto(int id, String codigo, String descripcion, int precio, int habilitado) {
        this.id = id;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.habilitado = habilitado;
    }

    public int getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getPrecio() {
        return precio;
    }

    public int getHabilitado() {
        return habilitado;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                ", habilitado=" + habilitado +
                '}' +"\n";
    }
}
