CREATE DATABASE IF NOT EXISTS control_gastos
    CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE control_gastos;

CREATE TABLE IF NOT EXISTS gastos (
    id        INT AUTO_INCREMENT PRIMARY KEY,
    concepto  VARCHAR(100)   NOT NULL,
    monto     DECIMAL(12, 2) NOT NULL CHECK (monto > 0),
    categoria VARCHAR(50)    NOT NULL
);

-- Datos de prueba
INSERT INTO gastos (concepto, monto, categoria) VALUES
    ('Almuerzo universidad', 15000, 'Alimentación'),
    ('Pasaje bus', 3200, 'Transporte'),
    ('Recibo de luz', 85000, 'Servicios'),
    ('Cine con amigos', 22000, 'Entretenimiento'),
    ('Mercado semanal', 120000, 'Alimentación');
