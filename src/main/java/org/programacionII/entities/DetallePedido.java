package org.programacionII.entities;

public class DetallePedido extends Base {

    private Integer cantidad;
    private Double subtotal;
    private Producto producto;

    public DetallePedido() {
        super();
    }

    public DetallePedido(Long id, Integer cantidad, Producto producto) {
        super(id);
        this.cantidad = cantidad;
        this.producto = producto;
        calcularSubtotal();
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
        calcularSubtotal();
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
        calcularSubtotal();
    }

    public void calcularSubtotal() {
        if (producto != null && producto.getPrecio() != null && cantidad != null) {
            this.subtotal = producto.getPrecio() * cantidad;
        } else {
            this.subtotal = 0.0;
        }
    }

    @Override
    public String toString() {
        String nombreProducto = producto != null ? producto.getNombre() : "Sin producto";

        return "DetallePedido{" +
                "id=" + getId() +
                ", producto=" + nombreProducto +
                ", cantidad=" + cantidad +
                ", subtotal=" + subtotal +
                ", eliminado=" + isEliminado() +
                '}';
    }
}
