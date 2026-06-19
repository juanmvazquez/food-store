package org.programacionII.services;

import org.programacionII.entities.Categoria;
import org.programacionII.entities.Producto;
import org.programacionII.exception.DatoInvalidoException;
import org.programacionII.exception.EntidadNoEncontradaException;
import org.programacionII.exception.StockInvalidoException;

public class ProductoService {

    private java.util.List<Producto> productos;
    private Long proximoId;
    private CategoriaService categoriaService;

    public ProductoService(CategoriaService categoriaService) {
        this.productos = new java.util.ArrayList<>();
        this.proximoId = 1L;
        this.categoriaService = categoriaService;
    }

    public java.util.List<Producto> listar() {
        java.util.List<Producto> productosActivos = new java.util.ArrayList<>();

        for (Producto producto : productos) {
            if (!producto.isEliminado()) {
                productosActivos.add(producto);
            }
        }

        return productosActivos;
    }

    public Producto crear(String nombre, String descripcion, Double precio, Integer stock, String imagen, Boolean disponible, Long categoriaId) {
        validarTexto(nombre, "El nombre del producto no puede estar vacio");
        validarTexto(descripcion, "La descripcion del producto no puede estar vacia");

        if (precio == null || precio < 0) {
            throw new DatoInvalidoException("El precio no puede ser menor a 0");
        }

        if (stock == null || stock < 0) {
            throw new StockInvalidoException("El stock no puede ser menor a 0");
        }

        Categoria categoria = categoriaService.buscarPorId(categoriaId);

        Producto producto = new Producto(proximoId, nombre, precio, descripcion, stock, imagen, categoria);

        if (disponible != null) {
            producto.setDisponible(disponible);
        }

        productos.add(producto);
        proximoId++;

        return producto;
    }

    public Producto buscarPorId(Long id) {
        for (Producto producto : productos) {
            if (!producto.isEliminado() && producto.getId().equals(id)) {
                return producto;
            }
        }

        throw new EntidadNoEncontradaException("No se encontro el producto con id " + id);
    }

    public Producto editar(Long id, String nombre, String descripcion, Double precio, Integer stock, String imagen, Boolean disponible, Long categoriaId) {
        validarTexto(nombre, "El nombre del producto no puede estar vacio");
        validarTexto(descripcion, "La descripcion del producto no puede estar vacia");

        if (precio == null || precio < 0) {
            throw new DatoInvalidoException("El precio no puede ser menor a 0");
        }

        if (stock == null || stock < 0) {
            throw new StockInvalidoException("El stock no puede ser menor a 0");
        }

        Producto producto = buscarPorId(id);
        Categoria categoria = categoriaService.buscarPorId(categoriaId);

        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setPrecio(precio);
        producto.setStock(stock);
        producto.setImagen(imagen);
        producto.setDisponible(disponible);
        producto.setCategoria(categoria);

        return producto;
    }

    public void eliminar(Long id) {
        Producto producto = buscarPorId(id);
        producto.setEliminado(true);
    }

    public void descontarStock(Long productoId, Integer cantidad) {
        Producto producto = buscarPorId(productoId);

        if (cantidad == null || cantidad <= 0) {
            throw new DatoInvalidoException("La cantidad debe ser mayor a 0");
        }

        if (producto.getStock() < cantidad) {
            throw new StockInvalidoException("No hay stock suficiente para el producto " + producto.getNombre());
        }

        producto.descontarStock(cantidad);
    }

    public void devolverStock(Long productoId, Integer cantidad) {
        Producto producto = buscarPorId(productoId);

        if (cantidad == null || cantidad <= 0) {
            throw new DatoInvalidoException("La cantidad debe ser mayor a 0");
        }

        producto.agregarStock(cantidad);
    }

    private void validarTexto(String texto, String mensaje) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new DatoInvalidoException(mensaje);
        }
    }
}
