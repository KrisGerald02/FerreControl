package com.tuempresa.FerreControl.modelo;

import javax.persistence.*;
import org.openxava.annotations.*;
import lombok.*;

@Entity @Getter @Setter
@View(
        members =
            "nombre;" +
            "descripcion;"
)
public class CategoriaProducto {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCategoria;

    @Column(length=50) @Required
    private String nombre;

    @Column(length=100)
    private String descripcion;
}