package com.tuempresa.FerreControl.modelo; // PAQUETE AJUSTADO

import java.math.*;
import javax.persistence.*;
import javax.validation.constraints.Min;

import org.openxava.annotations.*;

@Embeddable
public class DetalleFactura {

    @ManyToOne(fetch=FetchType.LAZY, optional=true)
    @DescriptionsList(descriptionProperties="descripcion") // Asumimos que Producto tiene 'descripcion'
    private Producto producto;

    @Min(1)
    private int cantidad;

    @Money
    private BigDecimal precio;

    @Money
    @Column(length=15)
    @Depends("cantidad, precio")
    public BigDecimal getImporte() {
        if (precio == null || cantidad == 0) {
            return BigDecimal.ZERO;
        }
        return precio.multiply(new BigDecimal(cantidad));
    }

    // Getters y Setters

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

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }
}