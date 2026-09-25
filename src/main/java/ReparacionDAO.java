import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class ReparacionDAO {
    
    public static boolean insertarReparacionDetallada(
    Vehiculo v,
    Reparacion r
) {
    Connection con = ConexionBD.getConnection();
    if (con == null) return false;

    String sql = "INSERT INTO reparaciones_detalladas " +
                 "(placa, modelo, fecha_afiliacion, aseguradora, cedula, nombre, celular, descripcion, estado, costo) " +
                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (PreparedStatement stmt = con.prepareStatement(sql)) {

        stmt.setString(1, v.getPlaca());
        stmt.setInt(2, v.getModelo());

        
        switch (v) {
            case VehiculoConConvenio vehiculoConConvenio -> {
                Date fecha = vehiculoConConvenio.getFechaAfiliacion();
                stmt.setDate(3, new java.sql.Date(fecha.getTime()));
                stmt.setString(4, null);
            }
            case VehiculoSinConvenio vehiculoSinConvenio -> {
                stmt.setDate(3, null);
                stmt.setString(4, vehiculoSinConvenio.getAseguradora());
            }
            default -> {
                stmt.setDate(3, null);
                stmt.setString(4, null);
            }
        }

        Propietario p = v.getSuPropietario();
        stmt.setInt(5, p.getCedula());
        stmt.setString(6, p.getNombre());
        stmt.setString(7, p.getCelular());

        stmt.setString(8, r.getDescripcion());
        stmt.setString(9, r.getEstado());
        stmt.setDouble(10, r.getCosto());

        stmt.executeUpdate();
        return true;

    } catch (SQLException e) {
        System.out.println("❌ Error al insertar reparación detallada: " + e.getMessage());
        return false;
    }
}

}