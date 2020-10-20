/**
 * 
 */
package com.citi.euces.pronosticos.web.reports;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * 
import java.io.InputStream;
import java.util.HashMap;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
*/

/**
 * 
 * @author lbermejo
 *
 */
public class ReporteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
        response.setContentType("application/pdf");
        
        String nombreReporte = "Testing";
        response.addHeader("Content-disposition", "attachment;filename=" + nombreReporte + ".pdf");
        try {
            final InputStream reportStream = ReporteServlet.class.getResourceAsStream("reporte.jasper");
            /*-final JasperReport jasperReport = JasperCompileManager.compileReport(ReporteServlet.class.getResourceAsStream("reporte.jrxml")); -* /
            final JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, new HashMap<String, Object>(), new JREmptyDataSource());
            JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());

            /*-final byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);
            response.setContentLength(bytes.length);
            response.getOutputStream().write(bytes); -* /
        } catch (JRException e) {
            e.printStackTrace();
        }
        */
    }

}
