import java.util.Date;

public final class VehiculoConConvenio extends Vehiculo {
    private Date fechaAfiliacion;

    public VehiculoConConvenio(Date fechaAfiliacion, String placa, int modelo, Propietario suPropietario) {
        super(placa, modelo, suPropietario);
        this.fechaAfiliacion = fechaAfiliacion;
    }

    @Override
    public double adicionarReparacion(Reparacion nvaReparacion) {
        susReparaciones.add(nvaReparacion);
        return nvaReparacion.getCosto() * 0.8;
    }

    public Date getFechaAfiliacion() {
        return fechaAfiliacion;
    }

    public void setFechaAfiliacion(Date fechaAfiliacion) {
        this.fechaAfiliacion = fechaAfiliacion;
    }

    @Override
    public String toString() {
        return super.toString() + " VehiculoConConvenio{" + "fechaAfiliacion=" + fechaAfiliacion + '}';
    }
}
