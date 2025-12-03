package com.tuempresa.FerreControl.actions;

import com.tuempresa.FerreControl.modelo.Producto;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import org.openxava.actions.JasperReportBaseAction;
import org.openxava.jpa.XPersistence;
import org.openxava.model.MapFacade;
import org.openxava.util.Messages;
import org.openxava.validators.ValidationException;

import java.util.HashMap;
import java.util.Map;

public class PrintProductAction extends JasperReportBaseAction {
    private Producto producto;

    @Override
    protected JRDataSource getDataSource() throws Exception {
        return new JREmptyDataSource();
    }

    @Override
    protected String getJRXML() throws Exception {
        return "Productos.jrxml";
    }

    @Override
    protected Map getParameters() throws Exception {
        // Validar datos del producto actual
        Messages errors = MapFacade.validate("Producto", getView().getValues());
        if (errors.contains()) {
            throw new ValidationException(errors.toString());
        }

        Producto p = getProducto();

        Map<String, Object> parameters = new HashMap<>();

        parameters.put("idProducto", p.getIdProducto());
        parameters.put("nombre", p.getNombre());
        parameters.put("categoria", p.getCategoria().getNombre());
        parameters.put("precioVenta", p.getPrecioVenta());
        parameters.put("stock", p.getStock());
        parameters.put("stockMinimo", p.getStockMinimo());
        parameters.put("stockMaximo", p.getStockMaximo());
        parameters.put("unidadMedida", p.getUnidadMedida().toString());
        parameters.put("estadoStock", p.getEstadoStock().toString());

        return parameters;
    }

    private Producto getProducto() {
        if (producto == null) {
            int id = getView().getValueInt("idProducto");
            producto = XPersistence.getManager().find(Producto.class, id);
        }
        return producto;
    }
}
