package ec.com.levelap.gameclub.module.jasper;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

@Service
public class JasperService {
	
	@Autowired
	private DataSource dataBaseDataSource;
	
	public File createExcelReport(String template, Map<String, Object> params) throws JRException, SQLException, IOException {
		JasperReport report = JasperCompileManager.compileReport(getClass().getResourceAsStream(template));
		JasperPrint jasperPrint = JasperFillManager.fillReport(report, params, dataBaseDataSource.getConnection());
		File file = File.createTempFile("report-", ".xlsx");
		
		JRXlsxExporter exporter = new JRXlsxExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(file));
		SimpleXlsxReportConfiguration reportConfig = new SimpleXlsxReportConfiguration();
		reportConfig.setSheetNames(new String[] {"Reporte"});
		
		exporter.setConfiguration(reportConfig);
		exporter.exportReport();
		
		return file;
	}
	
	public File createPdfReport(String template, Map<String, Object> params) throws JRException, SQLException, IOException {
		JasperReport report = JasperCompileManager.compileReport(getClass().getResourceAsStream(template));
		JasperPrint jasperPrint = JasperFillManager.fillReport(report, params, dataBaseDataSource.getConnection());
		File file = File.createTempFile("report-", ".pdf");
		
		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(file));
		
		SimplePdfReportConfiguration reportConfig = new SimplePdfReportConfiguration();
		reportConfig.setSizePageToContent(true);
		reportConfig.setForceLineBreakPolicy(false);
		
		SimplePdfExporterConfiguration exportConfig = new SimplePdfExporterConfiguration();
		exportConfig.setMetadataAuthor("Levelap Software");
		exportConfig.setEncrypted(true);
		exportConfig.setAllowedPermissionsHint("PRINTING");
		
		exporter.setConfiguration(reportConfig);
		exporter.setConfiguration(exportConfig);
		exporter.exportReport();
		
		return file;
	}
}