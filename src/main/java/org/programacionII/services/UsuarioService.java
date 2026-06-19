package org.programacionII.services;

import org.programacionII.entities.Usuario;
import org.programacionII.entities.enums.Rol;
import org.programacionII.exception.DatoInvalidoException;
import org.programacionII.exception.EntidadDuplicadaException;
import org.programacionII.exception.EntidadNoEncontradaException;

public class UsuarioService {

    private java.util.List<Usuario> usuarios;
    private Long proximoId;

    public UsuarioService() {
        this.usuarios = new java.util.ArrayList<>();
        this.proximoId = 1L;
    }

    public java.util.List<Usuario> listar() {
        java.util.List<Usuario> usuariosActivos = new java.util.ArrayList<>();

        for (Usuario usuario : usuarios) {
            if (!usuario.isEliminado()) {
                usuariosActivos.add(usuario);
            }
        }

        return usuariosActivos;
    }

    public Usuario crear(String nombre, String apellido, String mail, String celular, String contrasenia, Rol rol) {
        validarTexto(nombre, "El nombre no puede estar vacio");
        validarTexto(apellido, "El apellido no puede estar vacio");
        validarTexto(mail, "El mail no puede estar vacio");
        validarTexto(celular, "El celular no puede estar vacio");
        validarTexto(contrasenia, "La contrasenia no puede estar vacia");

        if (rol == null) {
            throw new DatoInvalidoException("El rol no puede estar vacio");
        }

        if (existeMail(mail)) {
            throw new EntidadDuplicadaException("Ya existe un usuario con ese mail");
        }

        Usuario usuario = new Usuario(proximoId, nombre, apellido, mail, celular, contrasenia, rol);
        usuarios.add(usuario);
        proximoId++;

        return usuario;
    }

    public Usuario buscarPorId(Long id) {
        for (Usuario usuario : usuarios) {
            if (!usuario.isEliminado() && usuario.getId().equals(id)) {
                return usuario;
            }
        }

        throw new EntidadNoEncontradaException("No se encontro el usuario con id " + id);
    }

    public Usuario editar(Long id, String nombre, String apellido, String mail, String celular, String contrasenia, Rol rol) {
        validarTexto(nombre, "El nombre no puede estar vacio");
        validarTexto(apellido, "El apellido no puede estar vacio");
        validarTexto(mail, "El mail no puede estar vacio");
        validarTexto(celular, "El celular no puede estar vacio");
        validarTexto(contrasenia, "La contrasenia no puede estar vacia");

        if (rol == null) {
            throw new DatoInvalidoException("El rol no puede estar vacio");
        }

        Usuario usuario = buscarPorId(id);

        if (existeMailEnOtroUsuario(id, mail)) {
            throw new EntidadDuplicadaException("Ya existe otro usuario con ese mail");
        }

        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setMail(mail);
        usuario.setCelular(celular);
        usuario.setContrasenia(contrasenia);
        usuario.setRol(rol);

        return usuario;
    }

    public void eliminar(Long id) {
        Usuario usuario = buscarPorId(id);
        usuario.setEliminado(true);
    }

    private boolean existeMail(String mail) {
        for (Usuario usuario : usuarios) {
            if (!usuario.isEliminado() && usuario.getMail().equalsIgnoreCase(mail)) {
                return true;
            }
        }

        return false;
    }

    private boolean existeMailEnOtroUsuario(Long id, String mail) {
        for (Usuario usuario : usuarios) {
            if (!usuario.isEliminado()
                    && !usuario.getId().equals(id)
                    && usuario.getMail().equalsIgnoreCase(mail)) {
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
