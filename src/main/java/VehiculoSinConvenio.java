public final class VehiculoSinConvenio extends Vehiculo {
    private String Aseguradora;

    public VehiculoSinConvenio(String Aseguradora, String placa, int modelo, Propietario suPropietario) {
        super(placa, modelo, suPropietario);
        this.Aseguradora = Aseguradora;
    }

    @Override
    public double adicionarReparacion(Reparacion nvaReparacion) {
        susReparaciones.add(nvaReparacion);
        return nvaReparacion.getCosto();
    }

    public String getAseguradora() {
        return Aseguradora;
    }

    public void setAseguradora(String Aseguradora) {
        this.Aseguradora = Aseguradora;
    }

    @Override
    public String toString() {
        return super.toString() + " VehiculoSinConvenio{" + "Aseguradora=" + Aseguradora + '}';
    }
}
