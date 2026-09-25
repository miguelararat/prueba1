public final class Propietario {
    private int cedula;
    private String nombre;
    private String celular;

    public Propietario(int cedula, String nombre, String celular) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.celular = celular;
    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    @Override
    public String toString() {
        return "Propietario{" + "cedula=" + cedula + ", nombre=" + nombre + ", celular=" + celular + '}';
    }
}
