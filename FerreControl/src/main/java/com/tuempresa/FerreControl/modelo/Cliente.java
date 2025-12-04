package com.tuempresa.FerreControl.modelo;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.ws.rs.DefaultValue;

import org.openxava.annotations.*;
import lombok.*;

@Entity
@Getter @Setter
// Vista de la entidad Cliente: se muestran todos los campos relevantes
@View(members=
        "cedula, estado;" +          // ahora la cédula es clave primaria, se ve en el formulario
                "nombres; apellidos;" +
                "departamento, direccion;"+
                "correo, telefono;"
)
public class Cliente {

    // La máscara sirve para poner los guiones automáticamente cuando el usuario ingrese los valores
    // Regex: regla escrita con símbolos que sirve para verificar si un texto cumple un formato específico
    // Sirve para validar cédulas, contraseñas, etc., con un formato específico muyayos
    // Una regex es un patrón que permite verificar si un texto tiene el formato correcto
    @Id
    @Mask("###-######-####A")
    @Pattern(
            regexp = "^\\d{3}-\\d{6}-\\d{4}[A-Za-z]$",
            message = "{Cliente.cedula.invalid}" // i18n: mensaje internacionalizable
    )
    @Column(length=20)
    @Required
    @Stereotype("ID") // valida unicidad automáticamente en OpenXava
    private String cedula;

    @Mask("####-####")
    @Column(length=9)
    @Required
    private String telefono;

    @Enumerated(EnumType.STRING) // Guarda el nombre (ej: MANAGUA) en la BD en lugar del índice
    @Required
    private DepartamentoNicaragua departamento;

    @Column(length=300)
    private String direccion;

    @Column(length=40, unique = true) @Required
    private String correo;

    // Nombres y apellidos pueden repetirse
    @Column(length=50) @Required
    private String nombres;

    @Column(length=50) @Required
    private String apellidos;

    @Enumerated(EnumType.STRING)
    @Required
    @DefaultValue("Activo")
    private EstadoCliente estado;

    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }

    // Nuevo: usado por el reporte de listado
    public String getDepartamentoTexto() {
        return departamento != null ? departamento.toString() : "";
    }

    // Nuevo: usado por el reporte de listado
    public String getEstadoTexto() {
        return estado != null ? estado.toString() : "";
    }

}
