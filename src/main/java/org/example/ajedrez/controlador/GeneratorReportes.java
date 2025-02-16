package org.example.ajedrez.controlador;
import net.sf.jasperreports.engine.*;
import org.example.ajedrez.controlador.DatabaseConnection;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

public class GeneratorReportes {

    public static void main(String[] args) {
        GeneratorReportes g = new GeneratorReportes();
        g.generarReporte(1);
    }

    private static final String JASPER_FILE = "src/main/resources/chess.jasper"; // Cambiar a la ruta real

    public void generarReporte(int partidaId) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try {


            Connection conn = databaseConnection.getConnection();

            // Parámetros para el reporte
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("PARTIDA_ID", partidaId);

            // Llenar el reporte
            JasperPrint print = JasperFillManager.fillReport(JASPER_FILE, parametros, conn);

            // Selección de la ruta de guardado
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar reporte como");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setSelectedFile(new File("reporte_partida_" + partidaId + ".pdf"));

            int userSelection = fileChooser.showSaveDialog(null);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                String outputFilePath = fileToSave.getAbsolutePath();

                // Exportar a PDF
                JasperExportManager.exportReportToPdfFile(print, outputFilePath);
                System.out.println("Reporte generado en: " + outputFilePath);

                // Abrir el PDF automáticamente
                abrirArchivo(outputFilePath);
            }

            // Cerrar conexión
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void abrirArchivo(String rutaArchivo) {
        try {
            File archivo = new File(rutaArchivo);
            if (archivo.exists()) {
                Desktop.getDesktop().open(archivo);
            } else {
                System.out.println("El archivo no se encontró.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}