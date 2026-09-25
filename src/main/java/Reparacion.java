public final class Reparacion {
    private String descripcion;
    private String estado;
    private double costo;

    public Reparacion(String descripcion, String estado, double costo) {
        this.descripcion = descripcion;
        this.estado = estado;
        this.costo = costo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    @Override
    public String toString() {
        return "Reparacion{" + "descripcion=" + descripcion + ", estado=" + estado + ", costo=" + costo + '}';
    }
}
