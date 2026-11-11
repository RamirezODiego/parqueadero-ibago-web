package co.com.ibago.parqueadero.util;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Reglas de validacion del modulo de vehiculos.
 */
public final class ValidadorVehiculo {

    /** Formato colombiano: carro ABC123, moto ABC12D. */
    private static final Pattern PATRON_PLACA = Pattern.compile("^[A-Z]{3}[0-9]{2}[0-9A-Z]$");
    private static final Pattern PATRON_TELEFONO = Pattern.compile("^[0-9]{7,15}$");
    public static final List<String> TIPOS_VALIDOS = List.of("CARRO", "MOTO", "BICICLETA");

    private ValidadorVehiculo() {
    }

    public static String normalizarPlaca(String placa) {
        return placa == null ? "" : placa.trim().toUpperCase();
    }

    public static boolean placaValida(String placa) {
        return PATRON_PLACA.matcher(normalizarPlaca(placa)).matches();
    }

    public static boolean tipoValido(String tipo) {
        return tipo != null && TIPOS_VALIDOS.contains(tipo.trim().toUpperCase());
    }

    /** El telefono es opcional; si se escribe debe tener entre 7 y 15 digitos. */
    public static boolean telefonoValido(String telefono) {
        return telefono == null || telefono.isBlank() || PATRON_TELEFONO.matcher(telefono.trim()).matches();
    }
}
