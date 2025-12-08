package com.tuempresa.FerreControl.modelo;

import java.math.*;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.Getter;
import lombok.Setter;
import org.openxava.annotations.*;

@Embeddable
@Getter @Setter
public class DetalleFactura {

    @ManyToOne(fetch = FetchType.LAZY)
    @DescriptionsList(descriptionProperties="nombre")
    private Producto producto;

    private BigDecimal cantidad;

    @Money
    private BigDecimal precio;

    @Min(0) @Max(100)
    private BigDecimal descuentoPorcentaje = BigDecimal.ZERO;

    @OnChange(CategoriaProducto.class)
    public void cargarPrecioProducto() {
        if (producto != null && producto.getPrecioVenta() > 0) {
            this.precio = new BigDecimal(String.valueOf(producto.getPrecioVenta())).setScale(2, RoundingMode.HALF_UP);
        }
    }

    @Money
    public BigDecimal getImporte() {
        if (cantidad == null || precio == null) return BigDecimal.ZERO;

        BigDecimal precioConDescuento = precio;
        if (descuentoPorcentaje != null && descuentoPorcentaje.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal porcentajeDescuento = descuentoPorcentaje.divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP);
            BigDecimal montoDescuento = precio.multiply(porcentajeDescuento);
            precioConDescuento = precio.subtract(montoDescuento);
        }

        return precioConDescuento.multiply(cantidad).setScale(2, RoundingMode.HALF_UP);
    }

    public String getProductoNombre() {
        return producto != null ? producto.getNombre() : "";
    }

}
