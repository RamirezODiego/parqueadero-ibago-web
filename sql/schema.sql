-- Parqueadero Ibago SAS - Modulo de vehiculos
CREATE DATABASE IF NOT EXISTS parqueadero_ibago
    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE parqueadero_ibago;

CREATE TABLE IF NOT EXISTS vehiculo (
    id_vehiculo    INT AUTO_INCREMENT PRIMARY KEY,
    placa          VARCHAR(6)   NOT NULL UNIQUE,
    tipo           VARCHAR(10)  NOT NULL,          -- CARRO, MOTO, BICICLETA
    propietario    VARCHAR(100) NOT NULL,
    telefono       VARCHAR(15),
    fecha_registro TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO vehiculo (placa, tipo, propietario, telefono) VALUES
 ('ABC123', 'CARRO', 'Carlos Gomez',  '3001112233'),
 ('XYZ45D', 'MOTO',  'Maria Rodriguez', '3104445566');
