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
                                                              `descripcion` VARCHAR(45) NULL,
                                                              PRIMARY KEY (`nombre`))
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `film_affinity_fruna`.`director`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `film_affinity_fruna`.`director` (
                                                                `id_director` INT NOT NULL,
                                                                `nombre` VARCHAR(45) NOT NULL,
                                                                `pais` VARCHAR(45) NOT NULL,
                                                                `fecha_nacimiento` DATE NOT NULL,
                                                                `fecha_defuncion` DATE NULL,
                                                                `enlace_wiki` VARCHAR(80) NULL,
                                                                PRIMARY KEY (`id_director`))
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
                                                                `director_id_director` INT NOT NULL,
                                                                PRIMARY KEY (`id_pelicula`),
                                                                INDEX `fk_pelicula_genero1_idx` (`genero_nombre` ASC) VISIBLE,
                                                                INDEX `fk_pelicula_director1_idx` (`director_id_director` ASC) VISIBLE,
                                                                CONSTRAINT `fk_pelicula_genero1`
                                                                    FOREIGN KEY (`genero_nombre`)
                                                                        REFERENCES `film_affinity_fruna`.`genero` (`nombre`)
                                                                        ON DELETE NO ACTION
                                                                        ON UPDATE NO ACTION,
                                                                CONSTRAINT `fk_pelicula_director1`
                                                                    FOREIGN KEY (`director_id_director`)
                                                                        REFERENCES `film_affinity_fruna`.`director` (`id_director`)
                                                                        ON DELETE NO ACTION
                                                                        ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `film_affinity_fruna`.`actor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `film_affinity_fruna`.`actor` (
                                                             `id_actor` INT NOT NULL AUTO_INCREMENT,
                                                             `nombre` VARCHAR(45) NOT NULL,
                                                             `pais` VARCHAR(45) NOT NULL,
                                                             `fecha_nacimiento` DATE NOT NULL,
                                                             `fecha_defuncion` DATE NULL,
                                                             `enlace_wiki` VARCHAR(80) NULL,
                                                             PRIMARY KEY (`id_actor`))
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `film_affinity_fruna`.`reparto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `film_affinity_fruna`.`reparto` (
                                                               `id_reparto` INT NOT NULL AUTO_INCREMENT,
                                                               `nombre_personaje` VARCHAR(45) NOT NULL,
                                                               `actor_id_actor` INT NOT NULL,
                                                               `pelicula_id_pelicula` INT NOT NULL,
                                                               PRIMARY KEY (`id_reparto`),
                                                               INDEX `fk_reparto_actor_idx` (`actor_id_actor` ASC) VISIBLE,
                                                               INDEX `fk_reparto_pelicula1_idx` (`pelicula_id_pelicula` ASC) VISIBLE,
                                                               CONSTRAINT `fk_reparto_actor`
                                                                   FOREIGN KEY (`actor_id_actor`)
                                                                       REFERENCES `film_affinity_fruna`.`actor` (`id_actor`)
                                                                       ON DELETE NO ACTION
                                                                       ON UPDATE NO ACTION,
                                                               CONSTRAINT `fk_reparto_pelicula1`
                                                                   FOREIGN KEY (`pelicula_id_pelicula`)
                                                                       REFERENCES `film_affinity_fruna`.`pelicula` (`id_pelicula`)
                                                                       ON DELETE NO ACTION
                                                                       ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `film_affinity_fruna`.`usuario_has_pelicula`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `film_affinity_fruna`.`usuario_has_pelicula` (
                                                                            `usuario_id_usuario` INT NOT NULL,
                                                                            `pelicula_id_pelicula` INT NOT NULL,
                                                                            PRIMARY KEY (`usuario_id_usuario`, `pelicula_id_pelicula`),
                                                                            INDEX `fk_usuario_has_pelicula_pelicula1_idx` (`pelicula_id_pelicula` ASC) VISIBLE,
                                                                            INDEX `fk_usuario_has_pelicula_usuario1_idx` (`usuario_id_usuario` ASC) VISIBLE,
                                                                            CONSTRAINT `fk_usuario_has_pelicula_usuario1`
                                                                                FOREIGN KEY (`usuario_id_usuario`)
                                                                                    REFERENCES `film_affinity_fruna`.`usuario` (`id_usuario`)
                                                                                    ON DELETE NO ACTION
                                                                                    ON UPDATE NO ACTION,
                                                                            CONSTRAINT `fk_usuario_has_pelicula_pelicula1`
                                                                                FOREIGN KEY (`pelicula_id_pelicula`)
                                                                                    REFERENCES `film_affinity_fruna`.`pelicula` (`id_pelicula`)
                                                                                    ON DELETE NO ACTION
                                                                                    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
