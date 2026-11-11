<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="co.com.ibago.parqueadero.modelo.Vehiculo" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/WEB-INF/views/header.jspf" %>

<%!
    /* Declaracion JSP: metodo auxiliar disponible en toda la pagina. */
    private String etiquetaTipo(String tipo) {
        if (tipo == null || tipo.isEmpty()) {
            return "";
        }
        return tipo.substring(0, 1).toUpperCase() + tipo.substring(1).toLowerCase();
    }
%>
<%
    /* Scriptlet JSP: codigo Java dentro de la pagina. */
    java.util.List<?> lista = (java.util.List<?>) request.getAttribute("vehiculos");
    int total = (lista == null) ? 0 : lista.size();
%>

<h2>Vehículos registrados</h2>

<c:if test="${not empty mensaje}">
    <div class="alerta exito"><c:out value="${mensaje}"/></div>
</c:if>
<c:if test="${not empty error}">
    <div class="alerta error"><c:out value="${error}"/></div>
</c:if>

<%-- Formulario HTML que envia por GET --%>
<form action="${pageContext.request.contextPath}/vehiculos" method="get" class="formulario en-linea">
    <input type="hidden" name="accion" value="buscar">
    <label for="q">Buscar placa</label>
    <input type="text" id="q" name="q" maxlength="6" value="<c:out value='${consulta}'/>">
    <button type="submit">Buscar</button>
    <a class="boton secundario" href="${pageContext.request.contextPath}/vehiculos">Ver todos</a>
    <a class="boton" href="${pageContext.request.contextPath}/vehiculos?accion=nuevo">+ Nuevo</a>
</form>

<p>Total mostrado: <strong><%= total %></strong></p>

<c:choose>
    <c:when test="${empty vehiculos}">
        <p class="vacio">No hay vehículos para mostrar.</p>
    </c:when>
    <c:otherwise>
        <div class="tabla-scroll">
        <table>
            <thead>
            <tr><th>#</th><th>Placa</th><th>Tipo</th><th>Propietario</th><th>Teléfono</th><th>Acciones</th></tr>
            </thead>
            <tbody>
            <c:forEach var="v" items="${vehiculos}">
                <% Vehiculo fila = (Vehiculo) pageContext.getAttribute("v"); %>
                <tr>
                    <td><c:out value="${v.idVehiculo}"/></td>
                    <td><c:out value="${v.placa}"/></td>
                    <td><%= etiquetaTipo(fila.getTipo()) %></td>
                    <td><c:out value="${v.propietario}"/></td>
                    <td><c:out value="${v.telefono}"/></td>
                    <td class="acciones">
                        <a class="boton secundario"
                           href="${pageContext.request.contextPath}/vehiculos?accion=editar&id=${v.idVehiculo}">Editar</a>
                        <%-- Eliminar usa POST porque modifica datos --%>
                        <form action="${pageContext.request.contextPath}/vehiculos" method="post"
                              onsubmit="return confirm('¿Eliminar el vehículo ${v.placa}?');">
                            <input type="hidden" name="accion" value="eliminar">
                            <input type="hidden" name="id" value="${v.idVehiculo}">
                            <button type="submit" class="peligro">Eliminar</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        </div>
    </c:otherwise>
</c:choose>

<%-- Inclusion DINAMICA: se ejecuta otra pagina JSP en tiempo de peticion --%>
<jsp:include page="/WEB-INF/views/footer.jsp"/>
