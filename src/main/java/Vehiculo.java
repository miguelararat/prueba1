import java.util.LinkedList;

public abstract class Vehiculo {
    protected String placa;
    protected int modelo;
    protected Propietario suPropietario;
    protected LinkedList<Reparacion> susReparaciones;

    public Vehiculo(String placa, int modelo, Propietario suPropietario) {
        this.placa = placa;
        this.modelo = modelo;
        this.suPropietario = suPropietario;
        this.susReparaciones = new LinkedList<>();
    }

    public abstract double adicionarReparacion(Reparacion nvaReparacion);

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public int getModelo() {
        return modelo;
    }

    public void setModelo(int modelo) {
        this.modelo = modelo;
    }

    public Propietario getSuPropietario() {
        return suPropietario;
    }

    public void setSuPropietario(Propietario suPropietario) {
        this.suPropietario = suPropietario;
    }

    public LinkedList<Reparacion> getSusReparaciones() {
        return susReparaciones;
    }

    public void setSusReparaciones(LinkedList<Reparacion> susReparaciones) {
        this.susReparaciones = susReparaciones;
    }

    @Override
    public String toString() {
        return "Vehiculo{" + "placa=" + placa + ", modelo=" + modelo + ", suPropietario=" + suPropietario + ", susReparaciones=" + susReparaciones + '}';
    }
}
