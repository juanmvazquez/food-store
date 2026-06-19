package org.programacionII.services;

import org.programacionII.entities.Categoria;
import org.programacionII.exception.DatoInvalidoException;
import org.programacionII.exception.EntidadDuplicadaException;
import org.programacionII.exception.EntidadNoEncontradaException;

public class CategoriaService {

    private java.util.List<Categoria> categorias;
    private Long proximoId;

    public CategoriaService() {
        this.categorias = new java.util.ArrayList<>();
        this.proximoId = 1L;
    }

    public java.util.List<Categoria> listar() {
        java.util.List<Categoria> categoriasActivas = new java.util.ArrayList<>();

        for (Categoria categoria : categorias) {
            if (!categoria.isEliminado()) {
                categoriasActivas.add(categoria);
            }
        }

        return categoriasActivas;
    }

    public Categoria crear(String nombre, String descripcion) {
        validarTexto(nombre, "El nombre de la categoria no puede estar vacio");
        validarTexto(descripcion, "La descripcion de la categoria no puede estar vacia");

        if (existeNombre(nombre)) {
            throw new EntidadDuplicadaException("Ya existe una categoria con ese nombre");
        }

        Categoria categoria = new Categoria(proximoId, nombre, descripcion);
        categorias.add(categoria);
        proximoId++;

        return categoria;
    }

    public Categoria buscarPorId(Long id) {
        for (Categoria categoria : categorias) {
            if (!categoria.isEliminado() && categoria.getId().equals(id)) {
                return categoria;
            }
        }

        throw new EntidadNoEncontradaException("No se encontro la categoria con id " + id);
    }

    public Categoria editar(Long id, String nombre, String descripcion) {
        validarTexto(nombre, "El nombre de la categoria no puede estar vacio");
        validarTexto(descripcion, "La descripcion de la categoria no puede estar vacia");

        Categoria categoria = buscarPorId(id);

        if (existeNombreEnOtraCategoria(id, nombre)) {
            throw new EntidadDuplicadaException("Ya existe otra categoria con ese nombre");
        }

        categoria.setNombre(nombre);
        categoria.setDescripcion(descripcion);

        return categoria;
    }

    public void eliminar(Long id) {
        Categoria categoria = buscarPorId(id);
        categoria.setEliminado(true);
    }

    public boolean existeCategoria(Long id) {
        for (Categoria categoria : categorias) {
            if (!categoria.isEliminado() && categoria.getId().equals(id)) {
                return true;
            }
        }

        return false;
    }

    private boolean existeNombre(String nombre) {
        for (Categoria categoria : categorias) {
            if (!categoria.isEliminado() && categoria.getNombre().equalsIgnoreCase(nombre)) {
                return true;
            }
        }

        return false;
    }

    private boolean existeNombreEnOtraCategoria(Long id, String nombre) {
        for (Categoria categoria : categorias) {
            if (!categoria.isEliminado()
                    && !categoria.getId().equals(id)
                    && categoria.getNombre().equalsIgnoreCase(nombre)) {
                return true;
            }
        }

        return false;
    }

    private void validarTexto(String texto, String mensaje) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new DatoInvalidoException(mensaje);
        }
    }
}
