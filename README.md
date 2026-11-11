# Parqueadero Ibagó SAS – Módulo Web de Vehículos (Servlets + JSP)
**Evidencia:** GA7-220501096-AA2-EV02 – Módulos de software codificados y probados
**Aprendiz:** Diego Fernando Ramírez Ortiz

Aplicación web que expone el módulo de vehículos mediante **formularios HTML**, un **Servlet**
(métodos **GET** y **POST**) y vistas **JSP**, con acceso a MySQL por JDBC.

## Estructura
```
sql/schema.sql                              Base de datos
src/main/java/co/com/ibago/parqueadero/
   modelo/Vehiculo.java                     Entidad
   config/ConexionBD.java                   Conexión JDBC
   dao/VehiculoDAO.java                     CRUD
   util/ValidadorVehiculo.java              Validaciones
   servlet/VehiculoServlet.java             Controlador (doGet / doPost)
src/main/webapp/
   index.html                               Formularios HTML (GET y POST)
   css/estilos.css
   WEB-INF/web.xml
   WEB-INF/views/header.jspf, lista.jsp, formulario.jsp, footer.jsp
src/test/java/.../ValidadorVehiculoTest.java  Pruebas unitarias JUnit 5
docs/GUIA_PASO_A_PASO.md, docs/PLAN_DE_PRUEBAS.md
```

## Requisitos
JDK 17+, Maven 3.9+, MySQL 8 y **Apache Tomcat 10.1** (Jakarta EE 10).

## Ejecución
1. Ejecutar `sql/schema.sql` en MySQL.
2. Ajustar `USUARIO` y `CLAVE` en `config/ConexionBD.java`.
3. `mvn clean package` (ejecuta también las pruebas unitarias) → genera `target/parqueadero.war`.
4. Copiar el WAR a `TOMCAT/webapps/` e iniciar Tomcat (o desplegarlo desde NetBeans/Eclipse/IntelliJ).
5. Abrir `http://localhost:8080/parqueadero/`

## Elementos solicitados
| Requisito | Dónde está |
|---|---|
| Formularios HTML con servlets | `index.html`, `formulario.jsp`, `lista.jsp` → `/vehiculos` |
| Métodos GET y POST | `doGet` (listar, buscar, editar, nuevo) y `doPost` (guardar, eliminar) |
| Elementos JSP | directivas `page`/`include`/`taglib`, declaración `<%! %>`, scriptlet `<% %>`, expresión `<%= %>`, `jsp:include`, EL y JSTL |
# parqueadero-ibago-web
