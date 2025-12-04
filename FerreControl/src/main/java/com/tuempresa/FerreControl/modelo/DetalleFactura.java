package com.tuempresa.FerreControl.modelo;

import java.math.*;
import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.openxava.annotations.*;

@Embeddable
@Getter @Setter
public class DetalleFactura {

    @ManyToOne(fetch = FetchType.LAZY)
    @DescriptionsList(descriptionProperties="nombre") // o "descripcion"
    private Producto producto;

    private BigDecimal cantidad;

    @Money
    private BigDecimal precio;

    @Money
    public BigDecimal getImporte() {
        if (cantidad == null || precio == null) return BigDecimal.ZERO;
        return precio.multiply(cantidad);
    }

    public String getProductoNombre() {
        return producto != null ? producto.getNombre() : "";
    }

}
