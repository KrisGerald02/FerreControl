package com.tuempresa.FerreControl.modelo;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.openxava.annotations.*;
import lombok.*;

@Entity @Getter @Setter
@View(
    members =
        "nombre;" +
        "correo,telefono;" +
        "departamento,direccion;"

)
public class Proveedor {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idProveedor;

    @Column(length=20, unique = true) @Required
    private String nombre;

    @Column(length=20, unique = true) @Required
    private String correo;

    @Size(max=8)
    @Pattern(regexp = "\\d{8}", message = "El telefono debe contener exactamente 8 digitos.")
    @Column(length=8)
    private String telefono;

    @Column(length=20)
    private String departamento;

    @Column(length=150)
    private String direccion;


    // 1..* para que OpenXava maneje bien proveedor - compra
    @OneToMany(mappedBy="proveedor")
    private java.util.Collection<Compra> compras;
}
