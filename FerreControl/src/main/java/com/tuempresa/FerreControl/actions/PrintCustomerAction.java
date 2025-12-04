package com.tuempresa.FerreControl.actions;

import com.tuempresa.FerreControl.modelo.Cliente;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import org.openxava.actions.JasperReportBaseAction;
import org.openxava.jpa.XPersistence;
import org.openxava.model.MapFacade;
import org.openxava.util.Messages;
import org.openxava.validators.ValidationException;

import java.util.HashMap;
import java.util.Map;

public class PrintCustomerAction extends JasperReportBaseAction {

    private Cliente cliente;

    @Override
    protected JRDataSource getDataSource() {
        // Es una ficha de un solo cliente
        return new JREmptyDataSource();
    }

    @Override
    protected String getJRXML() {
        // Nombre del archivo jrxml
        return "ClienteDetalle.jrxml";
    }

    @Override
    @SuppressWarnings("rawtypes")
    protected Map getParameters() throws Exception {

        // Validar datos del cliente mostrado en la vista
        Messages errors = MapFacade.validate("Cliente", getView().getValues());
        if (errors.contains()) {
            throw new ValidationException(errors.toString());
        }

        Cliente c = getCliente();
        Map<String, Object> params = new HashMap<>();

        params.put("cedula", c.getCedula());
        params.put("nombres", c.getNombres());
        params.put("apellidos", c.getApellidos());
        params.put("nombreCompleto", c.getNombreCompleto());
        params.put("departamento", c.getDepartamento() != null ? c.getDepartamento().toString() : "");
        params.put("direccion", c.getDireccion());
        params.put("correo", c.getCorreo());
        params.put("telefono", c.getTelefono());
        params.put("estado", c.getEstado() != null ? c.getEstado().toString() : "");

        return params;
    }

    private Cliente getCliente() {
        if (cliente == null) {
            // En Cliente la PK es la cédula (String)
            String cedula = (String) getView().getValue("cedula");
            cliente = XPersistence.getManager().find(Cliente.class, cedula);
        }
        return cliente;
    }
}
