import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ReporteDAO {
    
    public static String listarReparacionesDesdeBD() {
    StringBuilder sb = new StringBuilder();
    String sql = "SELECT * FROM reparaciones_detalladas ORDER BY placa";

    try (Connection con = ConexionBD.getConnection();
         Statement stmt = con.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

        while (rs.next()) {
            sb.append("Placa: ").append(rs.getString("placa")).append("\n");
            sb.append("Modelo: ").append(rs.getInt("modelo")).append("\n");
            sb.append("Propietario: Propietario{cedula=")
                    .append(rs.getInt("cedula")).append(", nombre=")
                    .append(rs.getString("nombre")).append(", celular=")
                    .append(rs.getString("celular")).append("}\n");

            if (rs.getString("aseguradora") != null) {
                sb.append("Tipo: SIN CONVENIO\n");
                sb.append("Aseguradora: ").append(rs.getString("aseguradora")).append("\n");
            } else {
                sb.append("Tipo: CON CONVENIO\n");
                sb.append("Fecha Afiliación: ").append(rs.getDate("fecha_afiliacion")).append("\n");
            }

            sb.append("Reparaciones:\n");
            sb.append(" - Reparacion{descripcion=")
                    .append(rs.getString("descripcion")).append(", estado=")
                    .append(rs.getString("estado")).append(", costo=")
                    .append(rs.getDouble("costo")).append("}\n");

            sb.append("\n");
        }

    } catch (Exception e) {
        sb.append("❌ Error al leer datos: ").append(e.getMessage()).append("\n");
    }

    return sb.toString();
}

public static String buscarVehiculoPorPlaca(String placaBuscada) {
    StringBuilder sb = new StringBuilder();
    String sql = "SELECT * FROM reparaciones_detalladas WHERE placa = '" + placaBuscada + "'";

    try (Connection con = ConexionBD.getConnection();
         Statement stmt = con.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

        boolean encontrado = false;

        while (rs.next()) {
            if (!encontrado) {
                sb.append("Placa: ").append(rs.getString("placa")).append("\n");
                sb.append("Modelo: ").append(rs.getInt("modelo")).append("\n");
                sb.append("Propietario: Propietario{cedula=")
                        .append(rs.getInt("cedula")).append(", nombre=")
                        .append(rs.getString("nombre")).append(", celular=")
                        .append(rs.getString("celular")).append("}\n");

                if (rs.getString("aseguradora") != null) {
                    sb.append("Tipo: SIN CONVENIO\n");
                    sb.append("Aseguradora: ").append(rs.getString("aseguradora")).append("\n");
                } else {
                    sb.append("Tipo: CON CONVENIO\n");
                    sb.append("Fecha Afiliación: ").append(rs.getDate("fecha_afiliacion")).append("\n");
                }

                sb.append("Reparaciones:\n");
                encontrado = true;
            }

            sb.append(" - Reparacion{descripcion=")
                    .append(rs.getString("descripcion")).append(", estado=")
                    .append(rs.getString("estado")).append(", costo=")
                    .append(rs.getDouble("costo")).append("}\n");
        }

        if (!encontrado) {
            return "Vehículo con placa " + placaBuscada + " no encontrado.";
        }

    } catch (Exception e) {
        sb.append("❌ Error al buscar: ").append(e.getMessage()).append("\n");
    }

    return sb.toString();
}

public static boolean existePlacaEnBD(String placa) {
    String sql = "SELECT 1 FROM reparaciones_detalladas WHERE placa = ? LIMIT 1";

    try (Connection con = ConexionBD.getConnection();
         PreparedStatement stmt = con.prepareStatement(sql)) {

        stmt.setString(1, placa);
        try (ResultSet rs = stmt.executeQuery()) {
            return rs.next();
        }

    } catch (Exception e) {
        System.out.println("❌ Error al verificar existencia de placa: " + e.getMessage());
        return false;
    }
}

}