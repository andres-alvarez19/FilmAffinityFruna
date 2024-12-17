-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema film_affinity_fruna
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `film_affinity_fruna` DEFAULT CHARACTER SET utf8mb3 ;
USE `film_affinity_fruna` ;

-- -----------------------------------------------------
-- Table `film_affinity_fruna`.`actor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `film_affinity_fruna`.`actor` (
                                                             `id_actor` BIGINT NOT NULL AUTO_INCREMENT,
                                                             `nombre` VARCHAR(45) NOT NULL,
                                                             `pais` VARCHAR(45) NOT NULL,
                                                             `fecha_nacimiento` DATE NOT NULL,
                                                             `fecha_defuncion` DATE NULL DEFAULT NULL,
                                                             `enlace_wiki` VARCHAR(80) NULL DEFAULT NULL,
                                                             `imagen` VARCHAR(45) NULL DEFAULT NULL,
                                                             PRIMARY KEY (`id_actor`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `film_affinity_fruna`.`director`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `film_affinity_fruna`.`director` (
                                                                `id_director` BIGINT NOT NULL AUTO_INCREMENT,
                                                                `nombre` VARCHAR(45) NOT NULL,
                                                                `pais` VARCHAR(45) NOT NULL,
                                                                `fecha_nacimiento` DATE NOT NULL,
                                                                `fecha_defuncion` DATE NULL DEFAULT NULL,
                                                                `enlace_wiki` VARCHAR(80) NULL DEFAULT NULL,
                                                                `imagen` VARCHAR(45) NULL DEFAULT NULL,
                                                                PRIMARY KEY (`id_director`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `film_affinity_fruna`.`genero`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `film_affinity_fruna`.`genero` (
                                                              `nombre` VARCHAR(45) NOT NULL,
                                                              `descripcion` VARCHAR(45) NULL DEFAULT NULL,
                                                              PRIMARY KEY (`nombre`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `film_affinity_fruna`.`pelicula`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `film_affinity_fruna`.`pelicula` (
                                                                `id_pelicula` BIGINT NOT NULL AUTO_INCREMENT,
                                                                `nombre` VARCHAR(45) NOT NULL,
                                                                `duracion` TIME NOT NULL,
                                                                `estreno` DATE NOT NULL,
                                                                `sinopsis` TEXT NULL DEFAULT NULL,
                                                                `pais` VARCHAR(45) NULL DEFAULT NULL,
                                                                `enlace_wiki` VARCHAR(80) NULL DEFAULT NULL,
                                                                `imagen` VARCHAR(45) NULL DEFAULT NULL,
                                                                `rating` FLOAT NULL DEFAULT NULL,
                                                                `genero_nombre` VARCHAR(45) NOT NULL,
                                                                `url_trailer` VARCHAR(80) NULL DEFAULT NULL,
                                                                `url_overview` VARCHAR(80) NULL DEFAULT NULL,
                                                                `director_id_director` BIGINT NOT NULL,
                                                                PRIMARY KEY (`id_pelicula`),
                                                                INDEX `fk_pelicula_genero1_idx` (`genero_nombre` ASC) VISIBLE,
                                                                INDEX `fk_pelicula_director1_idx` (`director_id_director` ASC) VISIBLE,
                                                                CONSTRAINT `fk_pelicula_director1`
                                                                    FOREIGN KEY (`director_id_director`)
                                                                        REFERENCES `film_affinity_fruna`.`director` (`id_director`),
                                                                CONSTRAINT `fk_pelicula_genero1`
                                                                    FOREIGN KEY (`genero_nombre`)
                                                                        REFERENCES `film_affinity_fruna`.`genero` (`nombre`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `film_affinity_fruna`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `film_affinity_fruna`.`usuario` (
                                                               `id_usuario` BIGINT NOT NULL AUTO_INCREMENT,
                                                               `nombre` VARCHAR(45) NOT NULL,
                                                               `correo` VARCHAR(45) NOT NULL,
                                                               `contrasennia` VARCHAR(255) NOT NULL,
                                                               PRIMARY KEY (`id_usuario`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `film_affinity_fruna`.`peliculas_vistas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `film_affinity_fruna`.`peliculas_vistas` (
                                                                        `usuario_id_usuario` BIGINT NOT NULL,
                                                                        `pelicula_id_pelicula` BIGINT NOT NULL,
                                                                        PRIMARY KEY (`usuario_id_usuario`, `pelicula_id_pelicula`),
                                                                        INDEX `fk_peliculas_vistas_pelicula1_idx` (`pelicula_id_pelicula` ASC) VISIBLE,
                                                                        INDEX `fk_peliculas_vistas_usuario1_idx` (`usuario_id_usuario` ASC) VISIBLE,
                                                                        CONSTRAINT `fk_peliculas_vistas_pelicula1`
                                                                            FOREIGN KEY (`pelicula_id_pelicula`)
                                                                                REFERENCES `film_affinity_fruna`.`pelicula` (`id_pelicula`),
                                                                        CONSTRAINT `fk_peliculas_vistas_usuario1`
                                                                            FOREIGN KEY (`usuario_id_usuario`)
                                                                                REFERENCES `film_affinity_fruna`.`usuario` (`id_usuario`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `film_affinity_fruna`.`reparto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `film_affinity_fruna`.`reparto` (
                                                               `id_reparto` BIGINT NOT NULL AUTO_INCREMENT,
                                                               `nombre_personaje` VARCHAR(45) NOT NULL,
                                                               `actor_id_actor` BIGINT NOT NULL,
                                                               `pelicula_id_pelicula` BIGINT NOT NULL,
                                                               PRIMARY KEY (`id_reparto`),
                                                               INDEX `fk_reparto_actor_idx` (`actor_id_actor` ASC) VISIBLE,
                                                               INDEX `fk_reparto_pelicula1_idx` (`pelicula_id_pelicula` ASC) VISIBLE,
                                                               CONSTRAINT `fk_reparto_actor`
                                                                   FOREIGN KEY (`actor_id_actor`)
                                                                       REFERENCES `film_affinity_fruna`.`actor` (`id_actor`),
                                                               CONSTRAINT `fk_reparto_pelicula1`
                                                                   FOREIGN KEY (`pelicula_id_pelicula`)
                                                                       REFERENCES `film_affinity_fruna`.`pelicula` (`id_pelicula`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `film_affinity_fruna`.`review`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `film_affinity_fruna`.`review` (
                                                              `id_review` BIGINT NOT NULL AUTO_INCREMENT,
                                                              `description` VARCHAR(45) NOT NULL,
                                                              `rating` FLOAT NOT NULL,
                                                              `review_date` DATE NOT NULL,
                                                              `pelicula_id_pelicula` BIGINT NOT NULL,
                                                              `usuario_id_usuario` BIGINT NOT NULL,
                                                              PRIMARY KEY (`id_review`),
                                                              INDEX `fk_review_pelicula1_idx` (`pelicula_id_pelicula` ASC) VISIBLE,
                                                              INDEX `fk_review_usuario1_idx` (`usuario_id_usuario` ASC) VISIBLE,
                                                              CONSTRAINT `fk_review_pelicula1`
                                                                  FOREIGN KEY (`pelicula_id_pelicula`)
                                                                      REFERENCES `film_affinity_fruna`.`pelicula` (`id_pelicula`),
                                                              CONSTRAINT `fk_review_usuario1`
                                                                  FOREIGN KEY (`usuario_id_usuario`)
                                                                      REFERENCES `film_affinity_fruna`.`usuario` (`id_usuario`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb3;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
