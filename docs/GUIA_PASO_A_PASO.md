# Guía paso a paso – EV02 Módulos codificados y probados (Servlets + JSP)

**Proyecto:** Parqueadero Ibagó SAS · **Aprendiz:** Diego Fernando Ramírez Ortiz

## Paso 1. Partir de los artefactos y de la EV01
Se reutilizan la entidad `Vehiculo`, el DAO y la conexión JDBC de la EV01. Los casos de uso
(registrar, consultar, editar, eliminar) y los prototipos definen las pantallas.

## Paso 2. Proyecto web con control de versiones
Proyecto Maven con `packaging` **war**. `git init` y commits por etapa.

## Paso 3. Dependencias (`pom.xml`)
- `jakarta.servlet-api` (provided: la trae Tomcat)
- JSTL (`c:forEach`, `c:if`, `c:out`)
- `mysql-connector-j`
- `junit-jupiter` (pruebas)

## Paso 4. Capa de datos
Se copian `Vehiculo`, `ConexionBD` y `VehiculoDAO`, y se amplía `ValidadorVehiculo`.

## Paso 5. El Servlet (`VehiculoServlet`)
Anotado con `@WebServlet("/vehiculos")`. Actúa como intermediario entre el navegador y la BD.
- **doGet** (no modifica datos): listar, buscar por placa, abrir formulario de nuevo/edición.
- **doPost** (modifica datos): guardar (inserta si `idVehiculo = 0`, si no actualiza) y eliminar.
- Valida los datos; si hay error, vuelve al formulario con el mensaje y los datos escritos.
- Tras un POST exitoso redirige (`sendRedirect`) para evitar reenvíos duplicados al recargar
  (patrón *Post/Redirect/Get*).

## Paso 6. Formularios HTML
`index.html` contiene un formulario **GET** (búsqueda) y uno **POST** (registro rápido).
Usan `action="vehiculos"`, `method`, campos con `name` y validaciones HTML5 (`required`, `pattern`).

## Paso 7. Vistas JSP (en `WEB-INF/views`, no accesibles directamente por URL)
- `header.jspf`: `<%@ include %>` (inclusión estática).
- `lista.jsp`: declaración `<%! %>`, scriptlet `<% %>`, expresión `<%= %>`, JSTL, EL y `jsp:include`.
- `formulario.jsp`: formulario POST, lista de tipos con `c:forEach`.
- `footer.jsp`: expresión JSP con el año actual.
Se usa `<c:out>` para escapar texto y evitar XSS.

## Paso 8. Pruebas
- Unitarias (JUnit 5): `ValidadorVehiculoTest` (`mvn test`).
- Funcionales: casos de `docs/PLAN_DE_PRUEBAS.md` sobre Tomcat.

## Paso 9. Empaquetado y despliegue
`mvn clean package` → `target/parqueadero.war` → Tomcat 10.1 → `http://localhost:8080/parqueadero/`.

## Paso 10. Entrega
Comprimir como `DIEGORAMIREZ_AA2_EV02.zip` con el proyecto y `REPOSITORIO.txt` con el enlace real.
