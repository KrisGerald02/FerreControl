package com.tuempresa.FerreControl.actions;

import com.tuempresa.FerreControl.modelo.Factura;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.openxava.actions.JasperReportBaseAction;
import org.openxava.model.MapFacade;
import org.openxava.tab.Tab;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PrintInvoicesByCustomerAction extends JasperReportBaseAction {

    @Inject
    private Tab tab;   // tabla de la lista de Factura (Invoices)

    @Override
    protected JRDataSource getDataSource() throws Exception {

        List<Factura> facturas = new ArrayList<>();

        // Si el usuario seleccionó filas, solo esas
        if (tab.getSelectedKeys().length > 0) {
            for (Map key : tab.getSelectedKeys()) {
                Factura f = (Factura) MapFacade.findEntity("Factura", key);
                if (f != null) facturas.add(f);
            }
        }
        // Si no seleccionó nada, todas las que están en la lista
        else {
            for (int i = 0; i < tab.getTableModel().getRowCount(); i++) {
                Factura f = (Factura) MapFacade.findEntity(
                        "Factura",
                        (Map) tab.getTableModel().getObjectAt(i)
                );
                if (f != null) facturas.add(f);
            }
        }

        //ORDENAR POR NOMBRE DE CLIENTE
        facturas.sort(
                (a, b) -> a.getClienteNombre().compareToIgnoreCase(b.getClienteNombre())
        );

        return new JRBeanCollectionDataSource(facturas);
    }

    @Override
    protected String getJRXML() throws Exception {
        // El archivo estará en src/main/resources/reports
        return "reports/FacturasPorCliente.jrxml";
    }

    @Override
    @SuppressWarnings("rawtypes")
    protected Map getParameters() throws Exception {
        // No necesitamos parámetros adicionales
        return null;
    }
}
