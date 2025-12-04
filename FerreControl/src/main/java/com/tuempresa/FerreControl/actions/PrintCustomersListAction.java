package com.tuempresa.FerreControl.actions;

import com.tuempresa.FerreControl.modelo.Cliente;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.openxava.actions.JasperReportBaseAction;
import org.openxava.jpa.XPersistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrintCustomersListAction extends JasperReportBaseAction {

    @Override
    protected JRDataSource getDataSource() throws Exception {
        List<Cliente> clientes = XPersistence.getManager()
                .createQuery("from Cliente", Cliente.class)
                .getResultList();
        return new JRBeanCollectionDataSource(clientes);
    }

    @Override
    protected String getJRXML() {
        return "ClientesListado.jrxml";
    }

    @Override
    @SuppressWarnings("rawtypes")
    protected Map getParameters() {
        return new HashMap(); // no necesitamos parámetros
    }
}
