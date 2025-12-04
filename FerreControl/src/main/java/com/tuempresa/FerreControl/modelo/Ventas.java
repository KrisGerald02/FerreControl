package com.tuempresa.FerreControl.modelo;

import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.*;
import org.openxava.annotations.*;

@Entity
@Views({
        @View(members =
                "numero;" +
                        "cliente;" +
                        "detalles { detalles }"
        )
})
public class Ventas {

    @Id
    @Column(length = 6)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int numero;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @DescriptionsList(descriptionProperties = "cedula, nombreCompleto")
    private Cliente cliente;

    // Colección inicializada para evitar null
    @OneToMany(mappedBy = "movimiento", cascade = CascadeType.ALL)
    @ListProperties("numeroLinea, producto.nombre, cantidad")
    @AsCollection
    private Collection<DetalleVentas> detalles = new ArrayList<>();

    // Lógica para asignar el número de línea secuencialmente
    @PrePersist
    @PreUpdate
    public void rellenarNumeroLinea() {

        if (detalles == null || detalles.isEmpty()) return; // prevención de null o vacío

        int contador = 1;

        for (DetalleVentas detalle : detalles) {
            detalle.setNumeroLinea(contador++);
            detalle.setMovimiento(this); // referencia inversa
        }
    }

    // --- Getters y Setters ---
    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Collection<DetalleVentas> getDetalles() { return detalles; }
    public void setDetalles(Collection<DetalleVentas> detalles) { this.detalles = detalles; }

}
