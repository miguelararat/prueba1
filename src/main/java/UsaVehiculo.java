import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class UsaVehiculo {
private static final LinkedList<Vehiculo> losVehiculos = new LinkedList<>();

public static void main(String[] args) {
    usaVehiculo();
}

public static void usaVehiculo() {
    System.out.println("Sistema de Taller Mecánico iniciado correctamente.");
}

public static void insertarVehiculoActionPerformance(Vehiculo v) {
    losVehiculos.add(v);
}

public static void insertarReparacionActionPerformance(String placa, Reparacion r) {
    for (Vehiculo v : losVehiculos) {
        if (v.getPlaca().equalsIgnoreCase(placa)) {
            double valor = v.adicionarReparacion(r);
            System.out.println("Reparación registrada. Valor a pagar: $" + valor);
            return;
        }
    }
    System.out.println("Vehículo con placa " + placa + " no encontrado.");
}

public static String listarVehiculosRegistrados() {
    StringBuilder sb = new StringBuilder();

    Collections.sort(losVehiculos, Comparator.comparing(Vehiculo::getPlaca));

    for (Vehiculo v : losVehiculos) {
        sb.append("Placa: ").append(v.getPlaca()).append("\n");
        sb.append("Modelo: ").append(v.getModelo()).append("\n");
        sb.append("Propietario: ").append(v.getSuPropietario().toString()).append("\n");

        switch (v) {
            case VehiculoConConvenio vc -> {
                sb.append("Tipo: CON CONVENIO\n");
                sb.append("Fecha Afiliación: ").append(vc.getFechaAfiliacion()).append("\n");
            }
            case VehiculoSinConvenio vs -> {
                sb.append("Tipo: SIN CONVENIO\n");
                sb.append("Aseguradora: ").append(vs.getAseguradora()).append("\n");
            }
            default -> {
            }
        }

        sb.append("Reparaciones:\n");
        for (Reparacion r : v.getSusReparaciones()) {
            sb.append(" - ").append(r.toString()).append("\n");
        }

        sb.append("\n");
    }

    return sb.toString();
}

public static String mostrarVehiculoDado(String placaDada) {
    for (Vehiculo v : losVehiculos) {
        if (v.getPlaca().equalsIgnoreCase(placaDada)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Placa: ").append(v.getPlaca()).append("\n")
              .append("Modelo: ").append(v.getModelo()).append("\n")
              .append("Propietario: ").append(v.getSuPropietario().toString()).append("\n");

            switch (v) {
                case VehiculoConConvenio vc -> {
                    sb.append("Tipo: CON CONVENIO\n");
                    sb.append("Fecha Afiliación: ").append(vc.getFechaAfiliacion()).append("\n");
                }
                case VehiculoSinConvenio vs -> {
                    sb.append("Tipo: SIN CONVENIO\n");
                    sb.append("Aseguradora: ").append(vs.getAseguradora()).append("\n");
                }
                default -> {
                }
            }

            sb.append("Reparaciones:\n");
            for (Reparacion r : v.getSusReparaciones()) {
                sb.append(" - ").append(r.toString()).append("\n");
            }

            return sb.toString();
        }
    }
    return "Vehículo con placa " + placaDada + " no encontrado.";
}

public static Vehiculo obtenerVehiculoPorPlaca(String placa) {
    for (Vehiculo v : losVehiculos) {
        if (v.getPlaca().equalsIgnoreCase(placa)) {
            return v;
        }
    }
    return null;
}
}