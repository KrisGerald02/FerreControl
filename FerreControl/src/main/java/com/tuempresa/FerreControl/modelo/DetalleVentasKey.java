package com.tuempresa.FerreControl.modelo;

import java.io.Serializable;
import java.util.Objects;

public class DetalleVentasKey implements Serializable {

    // Los campos deben COINCIDIR con los @Id de DetalleMovimiento.
    private Ventas movimiento;

    private int numeroLinea;

    // Constructor sin argumentos requerido por JPA
    public DetalleVentasKey() {
    }

    // --- Métodos Requeridos por JPA (equals y hashCode) ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DetalleVentasKey that = (DetalleVentasKey) o;
        return numeroLinea == that.numeroLinea &&
                Objects.equals(movimiento, that.movimiento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movimiento, numeroLinea);
    }

    // --- Getters y Setters (Opcional) ---

    public Ventas getMovimiento() { return movimiento; }
    public void setMovimiento(Ventas movimiento) { this.movimiento = movimiento; }

    public int getNumeroLinea() { return numeroLinea; }
    public void setNumeroLinea(int numeroLinea) { this.numeroLinea = numeroLinea; }
}