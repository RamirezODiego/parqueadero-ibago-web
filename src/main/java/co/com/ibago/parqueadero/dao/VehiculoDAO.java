package co.com.ibago.parqueadero.dao;

import co.com.ibago.parqueadero.config.ConexionBD;
import co.com.ibago.parqueadero.modelo.Vehiculo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Objeto de acceso a datos (DAO) de Vehiculo.
 * Implementa el CRUD con PreparedStatement (previene inyeccion SQL).
 */
public class VehiculoDAO {

    private static final String SQL_INSERTAR =
            "INSERT INTO vehiculo (placa, tipo, propietario, telefono) VALUES (?, ?, ?, ?)";
    private static final String SQL_LISTAR =
            "SELECT id_vehiculo, placa, tipo, propietario, telefono FROM vehiculo ORDER BY id_vehiculo";
    private static final String SQL_BUSCAR_ID =
            "SELECT id_vehiculo, placa, tipo, propietario, telefono FROM vehiculo WHERE id_vehiculo = ?";
    private static final String SQL_BUSCAR_PLACA =
            "SELECT id_vehiculo, placa, tipo, propietario, telefono FROM vehiculo WHERE placa LIKE ? ORDER BY placa";
    private static final String SQL_ACTUALIZAR =
            "UPDATE vehiculo SET placa = ?, tipo = ?, propietario = ?, telefono = ? WHERE id_vehiculo = ?";
    private static final String SQL_ELIMINAR =
            "DELETE FROM vehiculo WHERE id_vehiculo = ?";

    /** INSERCION: guarda un vehiculo y devuelve el id generado (o -1). */
    public int insertar(Vehiculo v) throws SQLException {
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(SQL_INSERTAR, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, v.getPlaca());
            ps.setString(2, v.getTipo());
            ps.setString(3, v.getPropietario());
            ps.setString(4, v.getTelefono());
            ps.executeUpdate();
            try (ResultSet claves = ps.getGeneratedKeys()) {
                return claves.next() ? claves.getInt(1) : -1;
            }
        }
    }

    /** CONSULTA: lista todos los vehiculos. */
    public List<Vehiculo> listar() throws SQLException {
        List<Vehiculo> vehiculos = new ArrayList<>();
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(SQL_LISTAR);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                vehiculos.add(mapear(rs));
            }
        }
        return vehiculos;
    }

    /** CONSULTA: busca por id (null si no existe). */
    public Vehiculo buscarPorId(int id) throws SQLException {
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(SQL_BUSCAR_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    /** CONSULTA: busca por coincidencia parcial de placa. */
    public List<Vehiculo> buscarPorPlaca(String placa) throws SQLException {
        List<Vehiculo> vehiculos = new ArrayList<>();
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(SQL_BUSCAR_PLACA)) {
            ps.setString(1, "%" + placa + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    vehiculos.add(mapear(rs));
                }
            }
        }
        return vehiculos;
    }

    /** ACTUALIZACION: modifica un vehiculo existente. */
    public boolean actualizar(Vehiculo v) throws SQLException {
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(SQL_ACTUALIZAR)) {
            ps.setString(1, v.getPlaca());
            ps.setString(2, v.getTipo());
            ps.setString(3, v.getPropietario());
            ps.setString(4, v.getTelefono());
            ps.setInt(5, v.getIdVehiculo());
            return ps.executeUpdate() > 0;
        }
    }

    /** ELIMINACION: borra un vehiculo por id. */
    public boolean eliminar(int id) throws SQLException {
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(SQL_ELIMINAR)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Vehiculo mapear(ResultSet rs) throws SQLException {
        return new Vehiculo(
                rs.getInt("id_vehiculo"),
                rs.getString("placa"),
                rs.getString("tipo"),
                rs.getString("propietario"),
                rs.getString("telefono"));
    }
}
