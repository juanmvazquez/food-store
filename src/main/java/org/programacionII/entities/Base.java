package org.programacionII.entities;

public abstract class Base {

    private Long id;
    private boolean eliminado;
    private java.time.LocalDateTime createdAt;

    public Base() {
        this.eliminado = false;
        this.createdAt = java.time.LocalDateTime.now();
    }

    public Base(Long id) {
        this.id = id;
        this.eliminado = false;
        this.createdAt = java.time.LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public java.time.LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(java.time.LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Base base = (Base) obj;

        if (id == null || base.id == null) {
            return false;
        }

        return id.equals(base.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", eliminado=" + eliminado +
                ", createdAt=" + createdAt;
    }
}