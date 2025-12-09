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

    // cantidad que vende
    private BigDecimal cantidad;

    // porcentaje de descuento
    @Min(0) @Max(100)
    private BigDecimal descuentoPorcentaje = BigDecimal.ZERO;

    // ======= PRECIO CALCULADO A PARTIR DEL PRODUCTO =======
    @Transient                 // no se guarda en la tabla embebida
    @ReadOnly                  // la columna en la factura será de solo lectura
    @Money
    public BigDecimal getPrecio() {
        if (producto == null) return null;

        double pv = producto.getPrecioVenta();  // viene como double
        if (pv <= 0) return null;

        return BigDecimal
                .valueOf(pv)
                .setScale(2, RoundingMode.HALF_UP);
    }
    // ======================================================

    @Money
    public BigDecimal getImporte() {
        BigDecimal precio = getPrecio();
        if (cantidad == null || precio == null) return BigDecimal.ZERO;

        BigDecimal precioConDescuento = precio;

        if (descuentoPorcentaje != null &&
                descuentoPorcentaje.compareTo(BigDecimal.ZERO) > 0) {

            BigDecimal porcentajeDescuento = descuentoPorcentaje
                    .divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP);

            BigDecimal montoDescuento = precio.multiply(porcentajeDescuento);
            precioConDescuento = precio.subtract(montoDescuento);
        }

        return precioConDescuento
                .multiply(cantidad)
                .setScale(2, RoundingMode.HALF_UP);
    }

    public String getProductoNombre() {
        return producto != null ? producto.getNombre() : "";
    }
}
