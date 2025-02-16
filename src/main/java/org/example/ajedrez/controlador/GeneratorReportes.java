package org.example.ajedrez.controlador;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

public class GeneratorReportes {

    // Ruta al archivo .jasper
    private static final String JASPER_FILE = "/org/example/ajedrez/reports/chess.jasper";
    /**
     * Genera un reporte en formato PDF para una partida específica.
     *
     * @param partidaId El ID de la partida para la cual se generará el reporte.
     */
    public void generarReporte(int partidaId) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection conn = null;

        try {
            // Obtener la conexión a la base de datos
            conn = databaseConnection.getConnection();

            // Cargar el archivo .jasper desde el classpath
            InputStream jasperStream = getClass().getResourceAsStream(JASPER_FILE);
            if (jasperStream == null) {
                throw new JRException("No se pudo encontrar el archivo .jasper: " + JASPER_FILE);
            }
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);

            // Parámetros para el reporte
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("PARTIDA_ID", partidaId);

            // Llenar el reporte con los datos de la base de datos
            JasperPrint print = JasperFillManager.fillReport(jasperReport, parametros, conn);

            // Selección de la ruta de guardado
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar reporte como");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setSelectedFile(new File("reporte_partida_" + partidaId + ".pdf"));

            int userSelection = fileChooser.showSaveDialog(null);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                String outputFilePath = fileToSave.getAbsolutePath();

                // Exportar el reporte a PDF
                JasperExportManager.exportReportToPdfFile(print, outputFilePath);
                System.out.println("Reporte generado en: " + outputFilePath);

                // Abrir el PDF automáticamente
                abrirArchivo(outputFilePath);
            }
        } catch (JRException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al generar el reporte: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error inesperado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            // Cerrar la conexión a la base de datos
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Abre un archivo en el visor predeterminado del sistema.
     *
     * @param rutaArchivo La ruta completa del archivo a abrir.
     */
    private void abrirArchivo(String rutaArchivo) {
        try {
            File archivo = new File(rutaArchivo);
            if (archivo.exists()) {
                Desktop.getDesktop().open(archivo);
            } else {
                JOptionPane.showMessageDialog(null, "El archivo no se encontró: " + rutaArchivo, "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al abrir el archivo: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}