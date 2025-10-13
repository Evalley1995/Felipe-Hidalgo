CREATE DATABASE IF NOT EXISTS computec_db
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_unicode_ci;

DROP DATABASE IF EXISTS computec_db;

USE computec_db;

-- ===================================================================
-- LIMPIEZA (DROP de SP y Tablas en orden seguro)
-- ===================================================================
DROP PROCEDURE IF EXISTS sp_cliente_insert;
DROP PROCEDURE IF EXISTS sp_cliente_update;
DROP PROCEDURE IF EXISTS sp_cliente_delete;
DROP PROCEDURE IF EXISTS sp_cliente_list;

DROP PROCEDURE IF EXISTS sp_equipo_insert_desktop;
DROP PROCEDURE IF EXISTS sp_equipo_insert_laptop;
DROP PROCEDURE IF EXISTS sp_equipo_update;
DROP PROCEDURE IF EXISTS sp_equipo_delete;
DROP PROCEDURE IF EXISTS sp_equipo_list;

DROP PROCEDURE IF EXISTS sp_venta_insert;
DROP PROCEDURE IF EXISTS sp_venta_delete;
DROP PROCEDURE IF EXISTS sp_venta_list;

DROP TABLE IF EXISTS ventas;
DROP TABLE IF EXISTS equipos;
DROP TABLE IF EXISTS clientes;

-- ===================================================================
-- TABLAS
-- ===================================================================

-- Clientes
CREATE TABLE clientes (
  rut        VARCHAR(12)  NOT NULL,
  nombre     VARCHAR(100) NOT NULL,
  direccion  VARCHAR(120) NOT NULL,
  comuna     VARCHAR(80)  NOT NULL,
  correo     VARCHAR(100) NOT NULL,
  telefono   VARCHAR(15)  NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT pk_clientes PRIMARY KEY (rut)
);

CREATE INDEX idx_clientes_nombre ON clientes (nombre);

-- Equipos
CREATE TABLE equipos (
  id         INT AUTO_INCREMENT,
  tipo       ENUM('desktop','laptop') NOT NULL,
  modelo     VARCHAR(100) NOT NULL,
  cpu        VARCHAR(80)  NOT NULL,
  ram_gb     INT          NOT NULL,
  gpu        VARCHAR(80)  NULL,
  precio     INT          NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT pk_equipos PRIMARY KEY (id),
  CONSTRAINT ck_equipos_ram CHECK (ram_gb > 0),
  CONSTRAINT ck_equipos_precio CHECK (precio >= 0)
);

CREATE INDEX idx_equipos_modelo ON equipos (modelo);

-- Ventas
CREATE TABLE ventas (
  id           INT AUTO_INCREMENT,
  fecha        TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  cliente_rut  VARCHAR(12) NOT NULL,
  equipo_id    INT         NOT NULL,
  precio_final INT         NOT NULL,
  CONSTRAINT pk_ventas PRIMARY KEY (id),
  CONSTRAINT fk_ventas_cliente FOREIGN KEY (cliente_rut)
    REFERENCES clientes(rut)
    ON UPDATE CASCADE ON DELETE RESTRICT,
  CONSTRAINT fk_ventas_equipo FOREIGN KEY (equipo_id)
    REFERENCES equipos(id)
    ON UPDATE CASCADE ON DELETE RESTRICT,
  CONSTRAINT ck_ventas_precio CHECK (precio_final >= 0)
);

CREATE INDEX idx_ventas_fecha ON ventas (fecha DESC);

-- ===================================================================
-- PROCEDIMIENTOS ALMACENADOS
-- ===================================================================
DELIMITER $$

-- ---------- CLIENTES ----------
CREATE PROCEDURE sp_cliente_insert(
  IN p_rut VARCHAR(12),
  IN p_nombre VARCHAR(100),
  IN p_direccion VARCHAR(120),
  IN p_comuna VARCHAR(80),
  IN p_correo VARCHAR(100),
  IN p_telefono VARCHAR(15)
)
BEGIN
  INSERT INTO clientes(rut,nombre,direccion,comuna,correo,telefono)
  VALUES(p_rut,p_nombre,p_direccion,p_comuna,p_correo,p_telefono);
END$$

CREATE PROCEDURE sp_cliente_update(
  IN p_rut VARCHAR(12),
  IN p_nombre VARCHAR(100),
  IN p_direccion VARCHAR(120),
  IN p_comuna VARCHAR(80),
  IN p_correo VARCHAR(100),
  IN p_telefono VARCHAR(15)
)
BEGIN
  UPDATE clientes
     SET nombre=p_nombre,
         direccion=p_direccion,
         comuna=p_comuna,
         correo=p_correo,
         telefono=p_telefono
   WHERE rut=p_rut;
  SELECT ROW_COUNT() AS rows_affected;
END$$

CREATE PROCEDURE sp_cliente_delete(
  IN p_rut VARCHAR(12)
)
BEGIN
  DELETE FROM clientes WHERE rut=p_rut;
  SELECT ROW_COUNT() AS rows_affected;
END$$

CREATE PROCEDURE sp_cliente_list()
BEGIN
  SELECT rut,nombre,direccion,comuna,correo,telefono,created_at,updated_at
    FROM clientes
   ORDER BY nombre;
END$$

-- ---------- EQUIPOS ----------
-- Inserción de DESKTOP (GPU requerida)
CREATE PROCEDURE sp_equipo_insert_desktop(
  IN p_modelo VARCHAR(100),
  IN p_cpu VARCHAR(80),
  IN p_ram_gb INT,
  IN p_gpu VARCHAR(80),
  IN p_precio INT,
  OUT p_id INT
)
BEGIN
  INSERT INTO equipos(tipo,modelo,cpu,ram_gb,gpu,precio)
  VALUES('desktop',p_modelo,p_cpu,p_ram_gb,p_gpu,p_precio);
  SET p_id = LAST_INSERT_ID();
END$$

-- Inserción de LAPTOP (GPU opcional -> NULL si viene vacía)
CREATE PROCEDURE sp_equipo_insert_laptop(
  IN p_modelo VARCHAR(100),
  IN p_cpu VARCHAR(80),
  IN p_ram_gb INT,
  IN p_gpu VARCHAR(80),
  IN p_precio INT,
  OUT p_id INT
)
BEGIN
  INSERT INTO equipos(tipo,modelo,cpu,ram_gb,gpu,precio)
  VALUES('laptop',p_modelo,p_cpu,p_ram_gb, NULLIF(p_gpu,''), p_precio);
  SET p_id = LAST_INSERT_ID();
END$$

CREATE PROCEDURE sp_equipo_update(
  IN p_id INT,
  IN p_tipo ENUM('desktop','laptop'),
  IN p_modelo VARCHAR(100),
  IN p_cpu VARCHAR(80),
  IN p_ram_gb INT,
  IN p_gpu VARCHAR(80),
  IN p_precio INT
)
BEGIN
  UPDATE equipos
     SET tipo=p_tipo,
         modelo=p_modelo,
         cpu=p_cpu,
         ram_gb=p_ram_gb,
         gpu=NULLIF(p_gpu,''),
         precio=p_precio
   WHERE id=p_id;
  SELECT ROW_COUNT() AS rows_affected;
END$$

CREATE PROCEDURE sp_equipo_delete(
  IN p_id INT
)
BEGIN
  DELETE FROM equipos WHERE id=p_id;
  SELECT ROW_COUNT() AS rows_affected;
END$$

CREATE PROCEDURE sp_equipo_list()
BEGIN
  SELECT id,tipo,modelo,cpu,ram_gb,gpu,precio,created_at,updated_at
    FROM equipos
   ORDER BY id DESC;
END$$

-- ---------- VENTAS ----------
CREATE PROCEDURE sp_venta_insert(
  IN  p_cliente_rut VARCHAR(12),
  IN  p_equipo_id INT,
  IN  p_precio_final INT,
  OUT p_id INT
)
BEGIN
  INSERT INTO ventas(cliente_rut,equipo_id,precio_final)
  VALUES(p_cliente_rut,p_equipo_id,p_precio_final);
  SET p_id = LAST_INSERT_ID();
END$$

CREATE PROCEDURE sp_venta_delete(
  IN p_id INT
)
BEGIN
  DELETE FROM ventas WHERE id=p_id;
  SELECT ROW_COUNT() AS rows_affected;
END$$

CREATE PROCEDURE sp_venta_list()
BEGIN
  SELECT v.id, v.fecha,
         v.cliente_rut, c.nombre   AS cliente,
         v.equipo_id,   e.modelo   AS equipo,
         v.precio_final
    FROM ventas v
    JOIN clientes c ON c.rut = v.cliente_rut
    JOIN equipos  e ON e.id  = v.equipo_id
   ORDER BY v.fecha DESC, v.id DESC;
END$$

DELIMITER ;

-- SELECTs directos
SELECT * FROM clientes;
SELECT * FROM equipos;
SELECT * FROM ventas;

-- Listados vía procedures
CALL sp_cliente_list();
CALL sp_equipo_list();
CALL sp_venta_list();