-- =============================================================================
-- DUMP DE BASE DE DATOS: SISTEMA DE REGISTRO Y FIDELIZACIÓN DE MARCAS
-- Dialecto: MySQL 8.x / MariaDB
-- Codificación: UTF-8 (utf8mb4)
-- Generado el: 2026-09-07
-- =============================================================================

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;

-- -----------------------------------------------------------------------------
-- 0. CREACIÓN Y SELECCIÓN DE BASE DE DATOS
-- -----------------------------------------------------------------------------
CREATE DATABASE IF NOT EXISTS `fidelizaciondb`
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE `fidelizaciondb`;

-- -----------------------------------------------------------------------------
-- 1. ELIMINACIÓN DE TABLAS SI YA EXISTEN
-- -----------------------------------------------------------------------------
DROP TABLE IF EXISTS `usuarios`;
DROP TABLE IF EXISTS `ubicaciones`;
DROP TABLE IF EXISTS `tipos_documento`;
DROP TABLE IF EXISTS `marcas`;

-- -----------------------------------------------------------------------------
-- 2. ESTRUCTURA DE TABLAS (DDL)
-- -----------------------------------------------------------------------------

--
-- Estructura de la tabla `marcas`
--
CREATE TABLE `marcas` (
  `id` VARCHAR(36) NOT NULL,
  `nombre` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_marcas_nombre` (`nombre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Estructura de la tabla `tipos_documento`
--
CREATE TABLE `tipos_documento` (
  `id` VARCHAR(36) NOT NULL,
  `abreviatura` VARCHAR(255) NOT NULL,
  `nombre` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tipos_documento_nombre` (`nombre`),
  UNIQUE KEY `uk_tipos_documento_abreviatura` (`abreviatura`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Estructura de la tabla `ubicaciones`
--
CREATE TABLE `ubicaciones` (
  `id` VARCHAR(36) NOT NULL,
  `direccion` VARCHAR(255) NOT NULL,
  `ciudad` VARCHAR(255) NOT NULL,
  `departamento` VARCHAR(255) NOT NULL,
  `pais` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Estructura de la tabla `usuarios`
--
CREATE TABLE `usuarios` (
  `id` VARCHAR(36) NOT NULL,
  `nombre` VARCHAR(255) DEFAULT NULL,
  `apellido` VARCHAR(255) DEFAULT NULL,
  `email` VARCHAR(255) NOT NULL,
  `contrasena` VARCHAR(255) NOT NULL,
  `rol` ENUM('ADMIN', 'CLIENTE') NOT NULL DEFAULT 'CLIENTE',
  `fecha_nacimiento` DATE DEFAULT NULL,
  `numero_documento` VARCHAR(255) DEFAULT NULL,
  `direccion_id` VARCHAR(36) DEFAULT NULL,
  `marca_id` VARCHAR(36) DEFAULT NULL,
  `tipo_documento_id` VARCHAR(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_usuarios_email` (`email`),
  UNIQUE KEY `uk_usuarios_numero_documento` (`numero_documento`),
  KEY `fk_usuarios_marca` (`marca_id`),
  KEY `fk_usuarios_tipo_documento` (`tipo_documento_id`),
  KEY `fk_usuarios_direccion` (`direccion_id`),
  CONSTRAINT `fk_usuarios_marca` FOREIGN KEY (`marca_id`) REFERENCES `marcas` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `fk_usuarios_tipo_documento` FOREIGN KEY (`tipo_documento_id`) REFERENCES `tipos_documento` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `fk_usuarios_direccion` FOREIGN KEY (`direccion_id`) REFERENCES `ubicaciones` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- 3. VOLCADO DE DATOS (DML)
-- -----------------------------------------------------------------------------

--
-- Datos para la tabla `marcas`
--
INSERT INTO `marcas` (`id`, `nombre`) VALUES
('3d5d9230-69bd-4d1f-aef9-626a31ea1ea2', 'Americanino'),
('ae728815-8e69-4436-80ba-0f88d1f00f60', 'American Eagle'),
('21c97efc-7259-4060-bab5-c49f828ec982', 'Chevignon'),
('db567d5a-8405-4ff0-acd0-8803cce699c9', 'Esprit'),
('0673f713-e73d-43a4-bafc-86bc971db006', 'Naf Naf'),
('1f19eb0a-deb7-4cfd-a076-adfd85fb75ee', 'Rifle');

--
-- Datos para la tabla `tipos_documento`
--
INSERT INTO `tipos_documento` (`id`, `abreviatura`, `nombre`) VALUES
('04500e1b-e69b-4cd5-ac21-a30043ea300a', 'CC', 'Cédula de Ciudadanía'),
('98291fef-8bbf-4023-bc44-a24e6ae7e085', 'TI', 'Tarjeta de Identidad'),
('624b07cf-5a5c-41e6-93dd-437284ba3c78', 'CE', 'Cédula de Extranjería'),
('987b0406-0e02-4c5f-8aae-0014fa8448e0', 'PAS', 'Pasaporte'),
('3352911d-8ec1-4a1e-abb7-a3328625f7c2', 'PEP', 'Permiso Especial de Permanencia');

--
-- Datos para la tabla `ubicaciones`
--
INSERT INTO `ubicaciones` (`id`, `direccion`, `ciudad`, `departamento`, `pais`) VALUES
('79a60ae8-250c-4882-9295-91c4fb08f13f', 'carrera 79b #92-52', 'Apartadó', 'Antioquia Department', 'Colombia'),
('8cb8573b-e1bf-4afc-bdf1-06aaaf8e7f9c', 'carrera 79b #92-52', 'Medellín', 'Antioquia Department', 'Colombia');

--
-- Datos para la tabla `usuarios`
--
INSERT INTO `usuarios` (`id`, `nombre`, `apellido`, `email`, `contrasena`, `rol`, `fecha_nacimiento`, `numero_documento`, `direccion_id`, `marca_id`, `tipo_documento_id`) VALUES
('2bed0185-63ca-4a94-ba85-8adf5cdebaa8', 'juan', 'zapata', '1234@1234.com', '32123asdasdasSADASDA$%%', 'CLIENTE', '1997-12-26', '1028033477', '79a60ae8-250c-4882-9295-91c4fb08f13f', '3d5d9230-69bd-4d1f-aef9-626a31ea1ea2', '04500e1b-e69b-4cd5-ac21-a30043ea300a'),
('ccc489a9-196a-4770-85e9-a9efe487c059', 'fgsdfgsg', 'villa', '1234@123434.com', 'asdasdaSDASDSAD21321#$%&', 'CLIENTE', '1997-12-26', '1028033472', '79a60ae8-250c-4882-9295-91c4fb08f13f', '3d5d9230-69bd-4d1f-aef9-626a31ea1ea2', '04500e1b-e69b-4cd5-ac21-a30043ea300a'),
('9624bc0d-6546-4ee9-936f-6a6c6357107b', 'samuel', 'zapata', 'samuelzaba2@gmai.com', 'Crover4515#', 'CLIENTE', '2001-05-27', '1000902803', '8cb8573b-e1bf-4afc-bdf1-06aaaf8e7f9c', '21c97efc-7259-4060-bab5-c49f828ec982', '04500e1b-e69b-4cd5-ac21-a30043ea300a'),
('8a12c328-eee0-4571-a801-7b294c88dacd', 'paola', 'marin', 'p1558627@gmail.com', 'Crosover4515#', 'CLIENTE', '1999-01-18', '1037370606', '8cb8573b-e1bf-4afc-bdf1-06aaaf8e7f9c', '3d5d9230-69bd-4d1f-aef9-626a31ea1ea2', '04500e1b-e69b-4cd5-ac21-a30043ea300a');

-- -----------------------------------------------------------------------------
-- 4. RESTABLECER CONFIGURACIONES
-- -----------------------------------------------------------------------------
SET FOREIGN_KEY_CHECKS = 1;
COMMIT;

