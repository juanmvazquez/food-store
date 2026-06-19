package org.programacionII.entities;

public class Producto extends Base {

    private String nombre;
    private Double precio;
    private String descripcion;
    private Integer stock;
    private String imagen;
    private Boolean disponible;
    private Categoria categoria;

    public Producto() {
        super();
        this.disponible = true;
    }

    public Producto(Long id, String nombre, Double precio, String descripcion, Integer stock, String imagen, Categoria categoria) {
        super(id);
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.stock = stock;
        this.imagen = imagen;
        this.disponible = true;
        this.categoria = categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public void descontarStock(Integer cantidad) {
        this.stock = this.stock - cantidad;

        if (this.stock <= 0) {
            this.disponible = false;
        }
    }

    public void agregarStock(Integer cantidad) {
        this.stock = this.stock + cantidad;

        if (this.stock > 0) {
            this.disponible = true;
        }
    }

    @Override
    public String toString() {
        String nombreCategoria = categoria != null ? categoria.getNombre() : "Sin categoria";

        return "Producto{" +
                "id=" + getId() +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", descripcion='" + descripcion + '\'' +
                ", stock=" + stock +
                ", disponible=" + disponible +
                ", categoria=" + nombreCategoria +
                ", eliminado=" + isEliminado() +
                '}';
    }
}
