package co.com.ibago.parqueadero.servlet;

import co.com.ibago.parqueadero.dao.VehiculoDAO;
import co.com.ibago.parqueadero.modelo.Vehiculo;
import co.com.ibago.parqueadero.util.ValidadorVehiculo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Controlador del modulo de vehiculos del Parqueadero Ibago SAS.
 *
 * GET  -> consultas que no modifican datos: listar, buscar, mostrar formulario.
 * POST -> operaciones que modifican datos: guardar (insertar/actualizar) y eliminar.
 */
@WebServlet(name = "VehiculoServlet", urlPatterns = {"/vehiculos"})
public class VehiculoServlet extends HttpServlet {

    private static final String VISTA_LISTA = "/WEB-INF/views/lista.jsp";
    private static final String VISTA_FORMULARIO = "/WEB-INF/views/formulario.jsp";

    private final VehiculoDAO dao = new VehiculoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if (accion == null) {
            accion = "listar";
        }
        try {
            switch (accion) {
                case "nuevo" -> mostrarFormulario(request, response, new Vehiculo(), "Registrar vehículo");
                case "editar" -> editar(request, response);
                case "buscar" -> buscar(request, response);
                default -> listar(request, response);
            }
        } catch (SQLException e) {
            errorDeBaseDeDatos(request, response, e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");
        try {
            if ("eliminar".equals(accion)) {
                eliminar(request, response);
            } else {
                guardar(request, response);
            }
        } catch (SQLException e) {
            errorDeBaseDeDatos(request, response, e);
        }
    }

    // ---------------------- Acciones GET ----------------------

    private void listar(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        request.setAttribute("vehiculos", dao.listar());
        request.setAttribute("mensaje", traducirMensaje(request.getParameter("mensaje")));
        despachar(request, response, VISTA_LISTA);
    }

    private void buscar(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        String consulta = ValidadorVehiculo.normalizarPlaca(request.getParameter("q"));
        request.setAttribute("vehiculos", dao.buscarPorPlaca(consulta));
        request.setAttribute("consulta", consulta);
        despachar(request, response, VISTA_LISTA);
    }

    private void editar(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        Vehiculo vehiculo = dao.buscarPorId(leerEntero(request.getParameter("id")));
        if (vehiculo == null) {
            response.sendRedirect(request.getContextPath() + "/vehiculos?mensaje=noexiste");
            return;
        }
        mostrarFormulario(request, response, vehiculo, "Editar vehículo");
    }

    // ---------------------- Acciones POST ----------------------

    private void guardar(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        Vehiculo vehiculo = new Vehiculo(
                leerEntero(request.getParameter("idVehiculo")),
                ValidadorVehiculo.normalizarPlaca(request.getParameter("placa")),
                texto(request.getParameter("tipo")).toUpperCase(),
                texto(request.getParameter("propietario")),
                texto(request.getParameter("telefono")));

        String error = validar(vehiculo);
        if (error != null) {
            request.setAttribute("error", error);
            mostrarFormulario(request, response, vehiculo,
                    vehiculo.getIdVehiculo() > 0 ? "Editar vehículo" : "Registrar vehículo");
            return;
        }

        try {
            boolean esNuevo = vehiculo.getIdVehiculo() == 0;
            if (esNuevo) {
                dao.insertar(vehiculo);
            } else {
                dao.actualizar(vehiculo);
            }
            response.sendRedirect(request.getContextPath()
                    + "/vehiculos?mensaje=" + (esNuevo ? "registrado" : "actualizado"));
        } catch (SQLException e) {
            if ("23000".equals(e.getSQLState())) { // violacion de restriccion UNIQUE
                request.setAttribute("error", "Ya existe un vehículo registrado con esa placa.");
                mostrarFormulario(request, response, vehiculo,
                        vehiculo.getIdVehiculo() > 0 ? "Editar vehículo" : "Registrar vehículo");
            } else {
                throw e;
            }
        }
    }

    private void eliminar(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        boolean eliminado = dao.eliminar(leerEntero(request.getParameter("id")));
        response.sendRedirect(request.getContextPath()
                + "/vehiculos?mensaje=" + (eliminado ? "eliminado" : "noexiste"));
    }

    // ---------------------- Utilidades ----------------------

    private String validar(Vehiculo v) {
        if (!ValidadorVehiculo.placaValida(v.getPlaca())) {
            return "La placa no es válida. Use el formato ABC123 (carro) o ABC12D (moto).";
        }
        if (!ValidadorVehiculo.tipoValido(v.getTipo())) {
            return "Seleccione un tipo de vehículo válido.";
        }
        if (v.getPropietario().isEmpty()) {
            return "El nombre del propietario es obligatorio.";
        }
        if (!ValidadorVehiculo.telefonoValido(v.getTelefono())) {
            return "El teléfono debe tener entre 7 y 15 dígitos.";
        }
        return null;
    }

    private void mostrarFormulario(HttpServletRequest request, HttpServletResponse response,
                                   Vehiculo vehiculo, String titulo) throws ServletException, IOException {
        request.setAttribute("vehiculo", vehiculo);
        request.setAttribute("titulo", titulo);
        request.setAttribute("tipos", ValidadorVehiculo.TIPOS_VALIDOS);
        despachar(request, response, VISTA_FORMULARIO);
    }

    private void despachar(HttpServletRequest request, HttpServletResponse response, String vista)
            throws ServletException, IOException {
        request.getRequestDispatcher(vista).forward(request, response);
    }

    private void errorDeBaseDeDatos(HttpServletRequest request, HttpServletResponse response, SQLException e)
            throws ServletException, IOException {
        getServletContext().log("Error de base de datos en VehiculoServlet", e);
        request.setAttribute("vehiculos", List.of());
        request.setAttribute("error", "No fue posible completar la operación con la base de datos.");
        despachar(request, response, VISTA_LISTA);
    }

    /** Solo se aceptan codigos conocidos: evita mostrar texto arbitrario de la URL. */
    private String traducirMensaje(String codigo) {
        if (codigo == null) {
            return null;
        }
        return switch (codigo) {
            case "registrado" -> "Vehículo registrado correctamente.";
            case "actualizado" -> "Vehículo actualizado correctamente.";
            case "eliminado" -> "Vehículo eliminado correctamente.";
            case "noexiste" -> "El vehículo solicitado no existe.";
            default -> null;
        };
    }

    private int leerEntero(String valor) {
        try {
            return Integer.parseInt(valor.trim());
        } catch (NumberFormatException | NullPointerException e) {
            return 0;
        }
    }

    private String texto(String valor) {
        return valor == null ? "" : valor.trim();
    }
}
