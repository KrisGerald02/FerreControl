package com.tuempresa.FerreControl.modelo;

import java.util.Collection;
import javax.persistence.*;
import org.openxava.annotations.*;

@Entity
@Views({
        @View(members =
                "numero;" +
                        "cliente;" + // O puedes cambiar esto a 'ubicacion' o 'destino'
                        "detalles { detalles }"
        )
})
public class Ventas {

    @Id
    @Column(length = 6)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int numero;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @DescriptionsList(descriptionProperties="cedula, nombreCompleto")
    private Cliente cliente; // Usamos Cliente, pero podría ser una Ubicación

    // Colección de los productos movidos (Detalles)
    @OneToMany(mappedBy = "movimiento", cascade = CascadeType.ALL)
    @ListProperties("numeroLinea, producto.nombre, cantidad") // ¡Propiedades ajustadas!
    @AsCollection
    private Collection<DetalleVentas> detalles;

    // Lógica para asignar el número de línea secuencialmente
    @PrePersist
    @PreUpdate
    public void rellenarNumeroLinea() {
        int contador = 1;
        for (DetalleVentas detalle : detalles) {
            detalle.setNumeroLinea(contador++);
            // Asegura la referencia de vuelta al movimiento padre
            detalle.setMovimiento(this);
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