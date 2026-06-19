package org.programacionII.entities;

import org.programacionII.entities.enums.Estado;
import org.programacionII.entities.enums.FormaPago;
import org.programacionII.interfaces.Calculable;

public class Pedido extends Base implements Calculable {

    private java.time.LocalDateTime fecha;
    private Estado estado;
    private Double total;
    private FormaPago formaPago;
    private Usuario usuario;
    private java.util.List<DetallePedido> detallesPedido;

    public Pedido() {
        super();
        this.fecha = java.time.LocalDateTime.now();
        this.estado = Estado.PENDIENTE;
        this.total = 0.0;
        this.detallesPedido = new java.util.ArrayList<>();
    }

    public Pedido(Long id, Usuario usuario, FormaPago formaPago) {
        super(id);
        this.fecha = java.time.LocalDateTime.now();
        this.estado = Estado.PENDIENTE;
        this.total = 0.0;
        this.formaPago = formaPago;
        this.usuario = usuario;
        this.detallesPedido = new java.util.ArrayList<>();
    }

    public java.time.LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(java.time.LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public java.util.List<DetallePedido> getDetallesPedido() {
        return detallesPedido;
    }

    public void setDetallesPedido(java.util.List<DetallePedido> detallesPedido) {
        this.detallesPedido = detallesPedido;
        calcularTotal();
    }

    public void addDetallePedido(DetallePedido detallePedido) {
        this.detallesPedido.add(detallePedido);
        calcularTotal();
    }

    public DetallePedido findDetallePedidoByProducto(Producto producto) {
        for (DetallePedido detalle : detallesPedido) {
            if (!detalle.isEliminado() && detalle.getProducto().equals(producto)) {
                return detalle;
            }
        }

        return null;
    }

    public void deleteDetallePedidoByProducto(Producto producto) {
        DetallePedido detalle = findDetallePedidoByProducto(producto);

        if (detalle != null) {
            detalle.setEliminado(true);
            calcularTotal();
        }
    }

    @Override
    public Double calcularTotal() {
        double suma = 0.0;

        for (DetallePedido detalle : detallesPedido) {
            if (!detalle.isEliminado()) {
                suma += detalle.getSubtotal();
            }
        }

        this.total = suma;
        return this.total;
    }

    @Override
    public String toString() {
        String nombreUsuario = usuario != null ? usuario.getNombre() + " " + usuario.getApellido() : "Sin usuario";

        return "Pedido{" +
                "id=" + getId() +
                ", fecha=" + fecha +
                ", estado=" + estado +
                ", total=" + total +
                ", formaPago=" + formaPago +
                ", usuario=" + nombreUsuario +
                ", detalles=" + detallesPedido.size() +
                ", eliminado=" + isEliminado() +
                '}';
    }
}
