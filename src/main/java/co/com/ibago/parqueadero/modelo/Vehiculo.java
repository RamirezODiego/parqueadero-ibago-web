package co.com.ibago.parqueadero.modelo;

/**
 * Entidad que representa un vehiculo registrado en el Parqueadero Ibago SAS.
 * Corresponde a la clase "Vehiculo" del diagrama de clases.
 */
public class Vehiculo {

    private int idVehiculo;
    private String placa;
    private String tipo;
    private String propietario;
    private String telefono;

    public Vehiculo() {
    }

    public Vehiculo(String placa, String tipo, String propietario, String telefono) {
        this.placa = placa;
        this.tipo = tipo;
        this.propietario = propietario;
        this.telefono = telefono;
    }

    public Vehiculo(int idVehiculo, String placa, String tipo, String propietario, String telefono) {
        this(placa, tipo, propietario, telefono);
        this.idVehiculo = idVehiculo;
    }

    public int getIdVehiculo() { return idVehiculo; }
    public void setIdVehiculo(int idVehiculo) { this.idVehiculo = idVehiculo; }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getPropietario() { return propietario; }
    public void setPropietario(String propietario) { this.propietario = propietario; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    @Override
    public String toString() {
        return String.format("[%d] %s | %s | %s | tel: %s",
                idVehiculo, placa, tipo, propietario, telefono);
    }
}
