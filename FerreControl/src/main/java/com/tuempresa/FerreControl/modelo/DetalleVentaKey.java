package com.tuempresa.FerreControl.modelo;

import java.io.Serializable;

public class DetalleVentaKey implements Serializable {

    // Debe coincidir con el tipo del ID de la entidad Venta (que es 'numero': int)
    private int venta;

    // El número secuencial dentro de esa venta
    private int numeroLinea;

    public DetalleVentaKey() {
    }

    // Constructor útil para inicializar la clave
    public DetalleVentaKey(int venta, int numeroLinea) {
        this.venta = venta;
        this.numeroLinea = numeroLinea;
    }

    // --- Getters y Setters ---

    public int getVenta() {
        return venta;
    }

    public void setVenta(int venta) {
        this.venta = venta;
    }

    public int getNumeroLinea() {
        return numeroLinea;
    }

    public void setNumeroLinea(int numeroLinea) {
        this.numeroLinea = numeroLinea;
    }

    // --- Métodos OBLIGATORIOS: equals y hashCode ---
    // Son esenciales para que JPA/Hibernate pueda manejar claves compuestas

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + venta;
        result = prime * result + numeroLinea;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        DetalleVentaKey other = (DetalleVentaKey) obj;
        if (venta != other.venta) return false;
        if (numeroLinea != other.numeroLinea) return false;
        return true;
    }
}