# Plan de pruebas – Módulo web de vehículos

Complete la columna **Resultado obtenido** al ejecutar cada prueba en su entorno
(Tomcat 10.1 + MySQL) y adjunte capturas si el instructor las solicita.

## Pruebas unitarias (`mvn test`)
| Clase | Qué valida |
|---|---|
| `ValidadorVehiculoTest` | Formato de placa, normalización, tipo y teléfono |

## Pruebas funcionales
| # | Caso | Método | Resultado esperado | Resultado obtenido |
|---|---|---|---|---|
| 1 | Abrir `/vehiculos` | GET | Lista de vehículos en tabla | |
| 2 | Buscar placa `ABC` | GET | Solo vehículos cuya placa contiene `ABC` | |
| 3 | Registrar placa `DEF456`, tipo CARRO | POST | Redirige a la lista con mensaje de éxito | |
| 4 | Registrar placa `12ABCD` | POST | Mensaje de placa inválida y datos conservados | |
| 5 | Registrar placa ya existente | POST | Mensaje de placa duplicada | |
| 6 | Editar un vehículo | GET + POST | Formulario cargado; cambios guardados | |
| 7 | Eliminar un vehículo | POST | Se pide confirmación; desaparece de la lista | |
| 8 | Abrir `/vehiculos?accion=editar&id=9999` | GET | Mensaje "El vehículo solicitado no existe" | |
| 9 | Detener MySQL y abrir `/vehiculos` | GET | Mensaje de error de BD sin mostrar detalles técnicos | |
