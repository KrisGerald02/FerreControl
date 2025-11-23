package com.tuempresa.FerreControl.modelo;
import javax.persistence.*;
import org.openxava.annotations.*;
import lombok.*;

@Entity @Getter @Setter
public class Rol {

    @Id @Column(length=20)
    private String idRol;

    @Column(length=50) @Required
    private String nombre;

    @Column(length=255)
    private String descripcion;
}