package com.tuempresa.FerreControl.modelo;

import javax.persistence.*;
import javax.validation.constraints.Min;

import org.openxava.annotations.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@IdClass(DetalleVentaKey.class)
public class DetalleVenta {

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name="venta_numero", referencedColumnName="numero")
    private Venta venta;

    @Id
    @Column(length = 3)
    @Hidden // OCULTA el campo en la interfaz de usuario
    // NOTA: Eliminamos la anotación @DefaultValueCalculator,
    // lo que requiere que el número de línea se asigne programáticamente (ej. en un @PrePersist de Venta).
    private int numeroLinea;

    @ManyToOne(optional = false)
    @DescriptionsList(descriptionProperties="idProducto, nombre")
    private Producto producto;

    @Min(1)
    private int cantidad;

    @Money
    @Column(precision = 10, scale = 2)
    private double precio;

    @Stereotype("MONEY")
    @Depends("cantidad, precio")
    @Transient
    @Column(precision = 10, scale = 2)
    public double getImporte() {
        // Se mantiene el cálculo para que se muestre en la lista de detalles
        BigDecimal c = new BigDecimal(cantidad);
        BigDecimal p = new BigDecimal(precio);
        return c.multiply(p).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    // --- Getters y Setters ---

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public int getNumeroLinea() {
        return numeroLinea;
    }

    public void setNumeroLinea(int numeroLinea) {
        this.numeroLinea = numeroLinea;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}