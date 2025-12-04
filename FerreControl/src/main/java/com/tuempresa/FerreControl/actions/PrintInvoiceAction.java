package com.tuempresa.FerreControl.actions;

import com.tuempresa.FerreControl.modelo.Cliente;
import com.tuempresa.FerreControl.modelo.Factura;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.openxava.actions.JasperReportBaseAction;
import org.openxava.model.MapFacade;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PrintInvoiceAction extends JasperReportBaseAction {

    private Factura factura;

    @Override
    protected JRDataSource getDataSource() throws Exception {
        Factura f = getFactura();
        // Los detalles (DetalleFactura) son el datasource
        return new JRBeanCollectionDataSource(f.getDetalles());
    }

    @Override
    protected String getJRXML() {
        return "FacturaDetalle.jrxml";
    }

    @Override
    @SuppressWarnings("rawtypes")
    protected Map getParameters() throws Exception {

        Factura f = getFactura();
        Cliente c = f.getCliente();

        Map<String, Object> params = new HashMap<>();

        // Número de factura tipo 2025-000001
        String numeroFactura = f.getAnyo() + "-" + String.format("%06d", f.getNumero());
        params.put("numeroFactura", numeroFactura);

        // Fecha (java.util.Date)
        Date fecha = Date.from(
                f.getFecha().atStartOfDay(ZoneId.systemDefault()).toInstant()
        );
        params.put("fecha", fecha);

        // Datos de cliente
        String nombreCompleto = c != null ? c.getNombreCompleto() : "";
        String cedula = c != null ? c.getCedula() : "";
        String direccion = c != null && c.getDireccion() != null ? c.getDireccion() : "N/D";
        String telefono = c != null && c.getTelefono() != null ? c.getTelefono() : "N/D";

        params.put("clienteNombre", nombreCompleto);
        params.put("clienteCedula", cedula);
        params.put("clienteDireccion", direccion);
        params.put("clienteTelefono", telefono);

        // Subtotal, IVA 15% y total
        BigDecimal subtotal = f.getImporteTotal() != null ? f.getImporteTotal() : BigDecimal.ZERO;
        BigDecimal iva = subtotal
                .multiply(new BigDecimal("0.15"))
                .setScale(2, RoundingMode.HALF_UP);
        BigDecimal total = subtotal.add(iva);

        params.put("subtotal", subtotal);
        params.put("iva", iva);
        params.put("total", total);

        // Forma de pago (por ahora fija)
        params.put("metodoPago", "Efectivo");

        return params;
    }

    private Factura getFactura() throws Exception {
        if (factura == null) {
            factura = (Factura) MapFacade.findEntity("Factura", getView().getKeyValues());
        }
        return factura;
    }
}

