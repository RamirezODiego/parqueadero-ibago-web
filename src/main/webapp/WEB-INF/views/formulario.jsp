<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/WEB-INF/views/header.jspf" %>

<h2><c:out value="${titulo}"/></h2>

<c:if test="${not empty error}">
    <div class="alerta error"><c:out value="${error}"/></div>
</c:if>

<%-- Formulario HTML que envia por POST al servlet --%>
<form action="${pageContext.request.contextPath}/vehiculos" method="post" class="formulario">
    <input type="hidden" name="accion" value="guardar">
    <input type="hidden" name="idVehiculo" value="${vehiculo.idVehiculo}">

    <label for="placa">Placa</label>
    <input type="text" id="placa" name="placa" maxlength="6"
           pattern="[A-Za-z]{3}[0-9]{2}[0-9A-Za-z]" required
           value="<c:out value='${vehiculo.placa}'/>">

    <label for="tipo">Tipo de vehículo</label>
    <select id="tipo" name="tipo" required>
        <option value="">Seleccione...</option>
        <c:forEach var="t" items="${tipos}">
            <option value="${t}" ${t == vehiculo.tipo ? 'selected' : ''}><c:out value="${t}"/></option>
        </c:forEach>
    </select>

    <label for="propietario">Propietario</label>
    <input type="text" id="propietario" name="propietario" maxlength="100" required
           value="<c:out value='${vehiculo.propietario}'/>">

    <label for="telefono">Teléfono (opcional)</label>
    <input type="tel" id="telefono" name="telefono" maxlength="15" pattern="[0-9]{7,15}"
           value="<c:out value='${vehiculo.telefono}'/>">

    <div class="botones">
        <button type="submit">Guardar</button>
        <a class="boton secundario" href="${pageContext.request.contextPath}/vehiculos">Cancelar</a>
    </div>
</form>

<jsp:include page="/WEB-INF/views/footer.jsp"/>
