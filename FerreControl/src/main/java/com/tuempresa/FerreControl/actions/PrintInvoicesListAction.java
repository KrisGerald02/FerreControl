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

public class PrintInvoicesListAction extends JasperReportBaseAction {

    @Inject
    private Tab tab;   // tabla de la lista de Factura

    @Override
    protected JRDataSource getDataSource() throws Exception {

        List<Factura> facturaList = new ArrayList<>();

        // Si el usuario seleccionó filas, solo esas
        if (tab.getSelectedKeys().length > 0) {
            for (Map key : tab.getSelectedKeys()) {
                Factura f = (Factura) MapFacade.findEntity("Factura", key);
                facturaList.add(f);
            }
        }
        // Si no seleccionó nada, todas las filas de la lista
        else {
            for (int i = 0; i < tab.getTableModel().getRowCount(); i++) {
                Factura f = (Factura) MapFacade.findEntity(
                        "Factura",
                        (Map) tab.getTableModel().getObjectAt(i)
                );
                facturaList.add(f);
            }
        }

        return new JRBeanCollectionDataSource(facturaList);
    }

    @Override
    protected String getJRXML() throws Exception {
        // Tus JRXML están en src/main/resources/reports
        return "reports/FacturasListado.jrxml";
    }

    @Override
    @SuppressWarnings("rawtypes")
    protected Map getParameters() throws Exception {
        // Sin parámetros por ahora
        return null;
    }
}
