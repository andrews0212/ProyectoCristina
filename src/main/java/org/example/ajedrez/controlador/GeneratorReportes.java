package org.example.ajedrez.controlador;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class GeneratorReportes{
    public static void main(String args[]) {
    }
    public static void generarReporte(int partidaId) {
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) {
            System.out.println("Error de conexión a la base de datos.");
            return;
        }

        try {
            String sql = """
                SELECT p.id AS partida_id, u1.usuario AS blancas, u2.usuario AS negras, m.movimiento
                FROM partidas p
                JOIN usuarios u1 ON p.jugador_blancas_id = u1.id
                JOIN usuarios u2 ON p.jugador_negras_id = u2.id
                JOIN movimientos m ON p.id = m.partida_id
                WHERE p.id = ?
                ORDER BY m.timestamp;
            """;

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, partidaId);
            ResultSet rs = stmt.executeQuery();

            List<Map<String, Object>> datos = new ArrayList<>();
            String blancas = "";
            String negras = "";
            while (rs.next()) {
                if (blancas.isEmpty()) blancas = rs.getString("blancas");
                if (negras.isEmpty()) negras = rs.getString("negras");

                Map<String, Object> fila = new HashMap<>();
                fila.put("movimiento", rs.getString("movimiento"));
                datos.add(fila);
            }

            rs.close();
            stmt.close();
            conn.close();

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("partida_id", partidaId);
            parametros.put("blancas", blancas);
            parametros.put("negras", negras);

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(datos);
            JasperReport reporte = JasperCompileManager.compileReport("/src/main/resources/chess.jrxml");
            JasperPrint print = JasperFillManager.fillReport(reporte, parametros, dataSource);

            JasperExportManager.exportReportToPdfFile(print, "reporte_partida_" + partidaId + ".pdf");
            System.out.println("Informe generado: chess_report_" + partidaId + ".pdf");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
