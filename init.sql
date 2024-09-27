-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema film_affinity_fruna
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema film_affinity_fruna
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `film_affinity_fruna` DEFAULT CHARACTER SET utf8 ;
USE `film_affinity_fruna` ;

-- -----------------------------------------------------
-- Table `film_affinity_fruna`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `film_affinity_fruna`.`usuario` (
                                                               `id_usuario` INT NOT NULL,
                                                               `nombre` VARCHAR(45) NOT NULL,
    `correo` VARCHAR(45) NOT NULL,
    `contrasennia` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id_usuario`))
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `film_affinity_fruna`.`genero`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `film_affinity_fruna`.`genero` (
                                                              `nombre` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`nombre`))
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `film_affinity_fruna`.`pelicula`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `film_affinity_fruna`.`pelicula` (
                                                                `id_pelicula` INT NOT NULL AUTO_INCREMENT,
                                                                `nombre` VARCHAR(45) NOT NULL,
    `pais` VARCHAR(45) NULL,
    `duracion` TIME NOT NULL,
    `estreno` DATE NOT NULL,
    `sinopsis` TEXT NULL,
    `enlace_wiki` VARCHAR(80) NULL,
    `genero_nombre` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id_pelicula`),
    INDEX `fk_pelicula_genero1_idx` (`genero_nombre` ASC) VISIBLE,
    CONSTRAINT `fk_pelicula_genero1`
    FOREIGN KEY (`genero_nombre`)
    REFERENCES `film_affinity_fruna`.`genero` (`nombre`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `film_affinity_fruna`.`actor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `film_affinity_fruna`.`actor` (
                                                             `id_actor` INT NOT NULL AUTO_INCREMENT,
                                                             `nombre` VARCHAR(45) NULL,
    `pais` VARCHAR(45) NOT NULL,
    `fecha_nacimiento` DATE NOT NULL,
    `fecha_defuncion` DATE NULL,
    `enlace_wiki` VARCHAR(80) NULL,
    PRIMARY KEY (`id_actor`))
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `film_affinity_fruna`.`personaje`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `film_affinity_fruna`.`personaje` (
                                                                 `id_personaje` INT NOT NULL AUTO_INCREMENT,
                                                                 `nombre` VARCHAR(45) NOT NULL,
    `actor_id_actor` INT NOT NULL,
    `pelicula_id_pelicula` INT NOT NULL,
    PRIMARY KEY (`id_personaje`),
    INDEX `fk_personaje_actor_idx` (`actor_id_actor` ASC) VISIBLE,
    INDEX `fk_personaje_pelicula1_idx` (`pelicula_id_pelicula` ASC) VISIBLE,
    CONSTRAINT `fk_personaje_actor`
    FOREIGN KEY (`actor_id_actor`)
    REFERENCES `film_affinity_fruna`.`actor` (`id_actor`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_personaje_pelicula1`
    FOREIGN KEY (`pelicula_id_pelicula`)
    REFERENCES `film_affinity_fruna`.`pelicula` (`id_pelicula`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
