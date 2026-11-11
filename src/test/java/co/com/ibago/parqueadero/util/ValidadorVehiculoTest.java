package co.com.ibago.parqueadero.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** Pruebas unitarias de las reglas de validacion. */
class ValidadorVehiculoTest {

    @Test
    void aceptaPlacaDeCarroYMoto() {
        assertTrue(ValidadorVehiculo.placaValida("ABC123"));
        assertTrue(ValidadorVehiculo.placaValida("xyz45d"));
    }

    @Test
    void rechazaPlacasInvalidas() {
        assertFalse(ValidadorVehiculo.placaValida("AB1234"));
        assertFalse(ValidadorVehiculo.placaValida("ABCD12"));
        assertFalse(ValidadorVehiculo.placaValida(""));
        assertFalse(ValidadorVehiculo.placaValida(null));
    }

    @Test
    void normalizaPlaca() {
        assertEquals("ABC123", ValidadorVehiculo.normalizarPlaca("  abc123 "));
    }

    @Test
    void validaTipoDeVehiculo() {
        assertTrue(ValidadorVehiculo.tipoValido("moto"));
        assertFalse(ValidadorVehiculo.tipoValido("CAMION"));
        assertFalse(ValidadorVehiculo.tipoValido(null));
    }

    @Test
    void validaTelefonoOpcional() {
        assertTrue(ValidadorVehiculo.telefonoValido(""));
        assertTrue(ValidadorVehiculo.telefonoValido("3001112233"));
        assertFalse(ValidadorVehiculo.telefonoValido("123"));
        assertFalse(ValidadorVehiculo.telefonoValido("30011a2233"));
    }
}
