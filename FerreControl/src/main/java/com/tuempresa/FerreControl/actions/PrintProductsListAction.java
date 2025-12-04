package com.tuempresa.FerreControl.actions;

import com.tuempresa.FerreControl.modelo.Producto;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.openxava.actions.JasperReportBaseAction;
import org.openxava.jpa.XPersistence;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PrintProductsListAction extends JasperReportBaseAction {

    private Collection<Producto> productos;

    @Override
    protected JRDataSource getDataSource() throws Exception {
        return new JRBeanCollectionDataSource(getProductos());
    }

    @Override
    protected String getJRXML() throws Exception {
        // Nombre del archivo JRXML en /reports
        return "ProductosListado.jrxml";
    }

    @Override
    @SuppressWarnings("rawtypes")
    protected Map getParameters() throws Exception {
        // No necesitamos parámetros por ahora, pero dejamos el Map por si luego
        // quieres agregar filtros, título dinámico, etc.
        return new HashMap<>();
    }

    private Collection<Producto> getProductos() {
        if (productos == null) {
            productos = XPersistence.getManager()
                    .createQuery("from Producto p order by p.nombre", Producto.class)
                    .getResultList();
        }
        return productos;
    }
}
