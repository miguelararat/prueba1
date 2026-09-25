import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PropietarioDAO {

    public static boolean insertarPropietario(Propietario p) {
        Connection con = ConexionBD.getConnection();
        if (con == null) return false;

        String sql = "INSERT INTO propietario (cedula, nombre, celular) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, p.getCedula());
            stmt.setString(2, p.getNombre());
            stmt.setString(7, p.getCelular());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("❌ Error al insertar propietario: " + e.getMessage());
            return false;
        }
    }
}
