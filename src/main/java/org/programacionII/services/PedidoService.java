package org.programacionII.services;

import org.programacionII.entities.DetallePedido;
import org.programacionII.entities.Pedido;
import org.programacionII.entities.Producto;
import org.programacionII.entities.Usuario;
import org.programacionII.entities.enums.Estado;
import org.programacionII.entities.enums.FormaPago;
import org.programacionII.exception.DatoInvalidoException;
import org.programacionII.exception.EntidadNoEncontradaException;
import org.programacionII.exception.StockInvalidoException;

public class PedidoService {

    private java.util.List<Pedido> pedidos;
    private Long proximoId;
    private Long proximoDetalleId;
    private UsuarioService usuarioService;
    private ProductoService productoService;

    public PedidoService(UsuarioService usuarioService, ProductoService productoService) {
        this.pedidos = new java.util.ArrayList<>();
        this.proximoId = 1L;
        this.proximoDetalleId = 1L;
        this.usuarioService = usuarioService;
        this.productoService = productoService;
    }

    public java.util.List<Pedido> listar() {
        java.util.List<Pedido> pedidosActivos = new java.util.ArrayList<>();

        for (Pedido pedido : pedidos) {
            if (!pedido.isEliminado()) {
                pedidosActivos.add(pedido);
            }
        }

        return pedidosActivos;
    }

    public java.util.List<Pedido> listarPorUsuario(Long usuarioId) {
        java.util.List<Pedido> pedidosDelUsuario = new java.util.ArrayList<>();

        for (Pedido pedido : pedidos) {
            if (!pedido.isEliminado()
                    && pedido.getUsuario() != null
                    && pedido.getUsuario().getId().equals(usuarioId)) {
                pedidosDelUsuario.add(pedido);
            }
        }

        return pedidosDelUsuario;
    }

    public Pedido crear(Long usuarioId, FormaPago formaPago) {
        if (formaPago == null) {
            throw new DatoInvalidoException("La forma de pago no puede estar vacia");
        }

        Usuario usuario = usuarioService.buscarPorId(usuarioId);

        Pedido pedido = new Pedido(proximoId, usuario, formaPago);
        proximoId++;

        return pedido;
    }

    public DetallePedido crearDetalle(Long productoId, Integer cantidad) {
        if (cantidad == null || cantidad <= 0) {
            throw new DatoInvalidoException("La cantidad debe ser mayor a 0");
        }

        Producto producto = productoService.buscarPorId(productoId);

        if (producto.getStock() < cantidad) {
            throw new StockInvalidoException("No hay stock suficiente para el producto " + producto.getNombre());
        }

        DetallePedido detallePedido = new DetallePedido(proximoDetalleId, cantidad, producto);
        proximoDetalleId++;

        return detallePedido;
    }

    public void agregarDetalle(Pedido pedido, Long productoId, Integer cantidad) {
        if (pedido == null) {
            throw new DatoInvalidoException("El pedido no puede estar vacio");
        }

        DetallePedido detallePedido = crearDetalle(productoId, cantidad);

        pedido.addDetallePedido(detallePedido);
        productoService.descontarStock(productoId, cantidad);
    }

    public Pedido guardarPedido(Pedido pedido) {
        if (pedido == null) {
            throw new DatoInvalidoException("El pedido no puede estar vacio");
        }

        if (pedido.getDetallesPedido().isEmpty()) {
            throw new DatoInvalidoException("El pedido debe tener al menos un detalle");
        }

        pedido.calcularTotal();
        pedidos.add(pedido);

        return pedido;
    }

    public Pedido buscarPorId(Long id) {
        for (Pedido pedido : pedidos) {
            if (!pedido.isEliminado() && pedido.getId().equals(id)) {
                return pedido;
            }
        }

        throw new EntidadNoEncontradaException("No se encontro el pedido con id " + id);
    }

    public Pedido actualizarEstadoYFormaPago(Long id, Estado estado, FormaPago formaPago) {
        if (estado == null) {
            throw new DatoInvalidoException("El estado no puede estar vacio");
        }

        if (formaPago == null) {
            throw new DatoInvalidoException("La forma de pago no puede estar vacia");
        }

        Pedido pedido = buscarPorId(id);

        pedido.setEstado(estado);
        pedido.setFormaPago(formaPago);

        return pedido;
    }

    public void eliminar(Long id) {
        Pedido pedido = buscarPorId(id);
        pedido.setEliminado(true);

        for (DetallePedido detalle : pedido.getDetallesPedido()) {
            detalle.setEliminado(true);
        }
    }

    public void cancelarCreacionPedido(Pedido pedido) {
        if (pedido == null) {
            return;
        }

        for (DetallePedido detalle : pedido.getDetallesPedido()) {
            if (!detalle.isEliminado() && detalle.getProducto() != null) {
                productoService.devolverStock(detalle.getProducto().getId(), detalle.getCantidad());
            }
        }
    }
}
