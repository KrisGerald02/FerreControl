package com.tuempresa.FerreControl.modelo;

import javax.persistence.*;
import javax.validation.constraints.Min;
import org.openxava.annotations.*;

@Entity
@IdClass(DetalleVentasKey.class)
public class DetalleVentas {

    // --- CLAVE COMPUESTA (@Id) ---

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name="movimiento_numero", referencedColumnName="numero")
    private Ventas movimiento;

    @Id
    @Column(length = 3)
    @Hidden
    private int numeroLinea;

    // --- ATRIBUTOS DE LA LÍNEA ---

    @ManyToOne(optional = false)
    @DescriptionsList(descriptionProperties="nombre")
    private Producto producto;

    @Min(1)
    private int cantidad;

    // --- Getters y Setters ---

    public Ventas getMovimiento() { return movimiento; }
    public void setMovimiento(Ventas movimiento) { this.movimiento = movimiento; }

    public int getNumeroLinea() { return numeroLinea; }
    public void setNumeroLinea(int numeroLinea) { this.numeroLinea = numeroLinea; }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
}