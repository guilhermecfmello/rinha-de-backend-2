-- MariaDB Script generated by MySQL Workbench
-- Fri Mar 1 22:51:39 2024
-- Model: New Model Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema rinha
-- -----------------------------------------------------
CREATE DATABASE IF NOT EXISTS `rinha` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `rinha`;

-- -----------------------------------------------------
-- Table `rinha`.`clientes`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `rinha`.`clientes`;

CREATE TABLE IF NOT EXISTS `rinha`.`clientes` (
  `id` TINYINT(1) UNSIGNED NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45),
  `limite` INT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `rinha`.`transacoes`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `rinha`.`transacoes`;

CREATE TABLE IF NOT EXISTS `rinha`.`transacoes` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `valor` INT NULL,
  `tipo` CHAR(1) NULL,
  `descricao` VARCHAR(10) NULL,
  `realizada_em` DATETIME NULL,
  `idCliente` TINYINT(1) UNSIGNED NULL,
  PRIMARY KEY (`id`),
  INDEX `client_fk_idx` (`idCliente` ASC) VISIBLE,
  CONSTRAINT `client_fk`
    FOREIGN KEY (`idCliente`)
    REFERENCES `rinha`.`clientes` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE = InnoDB;

INSERT INTO clientes (nome, limite)
  VALUES
    ('o barato sai caro', 1000 * 100),
    ('zan corp ltda', 800 * 100),
    ('les cruders', 10000 * 100),
    ('padaria joia de cocaia', 100000 * 100),
    ('kid mais', 5000 * 100);

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
