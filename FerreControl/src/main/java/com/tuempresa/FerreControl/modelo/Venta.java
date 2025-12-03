package com.tuempresa.FerreControl.modelo;

import java.util.Collection;
import javax.persistence.*;
import org.openxava.annotations.*;

@Entity
@Views({
        @View(members =
                "numero;" + // Solo queda 'numero' como dato de cabecera
                        "cliente;" + // Búsqueda y selección de cliente
                        "detalles { detalles }" + // Lista de productos (Detalles)
                        "observaciones"
        )
})
public class Venta {

    @Id
    @Column(length = 6)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int numero;

    // anyo y fecha han sido eliminados

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @DescriptionsList(descriptionProperties="cedula, nombreCompleto") // Permite buscar y seleccionar un cliente
    private Cliente cliente;

    // 'detalles' es la lista de los productos que se van a cobrar
    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
    @ListProperties("numeroLinea, producto.nombre, cantidad, precio, importe")
    @AsCollection
    private Collection<DetalleVenta> detalles;

    @Stereotype("TEXT_AREA")
    private String observaciones;

    @PrePersist
    @PreUpdate
    public void rellenarNumeroLinea() {
        int contador = 1;
        for (DetalleVenta detalle : detalles) {
            detalle.setNumeroLinea(contador++);
            detalle.setVenta(this); // Asegura la referencia de vuelta
        }
    }

    // --- Getters y Setters ---

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Collection<DetalleVenta> getDetalles() {
        return detalles;
    }

    public void setDetalles(Collection<DetalleVenta> detalles) {
        this.detalles = detalles;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}