-- MySQL Script generated by MySQL Workbench
-- Thu Apr  6 12:58:02 2017
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema SBB
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema SBB
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `SBB` DEFAULT CHARACTER SET utf8 ;
USE `SBB` ;

-- -----------------------------------------------------
-- Table `SBB`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SBB`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) CHARACTER SET 'big5' COLLATE 'big5_chinese_ci' NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `SBB`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SBB`.`role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `SBB`.`usersRole`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SBB`.`usersRole` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `usersId` INT NOT NULL,
  `rolesId` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `usersId_idx` (`usersId` ASC),
  INDEX `rolesId_idx` (`rolesId` ASC),
  CONSTRAINT `usersId`
    FOREIGN KEY (`usersId`)
    REFERENCES `SBB`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `rolesId`
    FOREIGN KEY (`rolesId`)
    REFERENCES `SBB`.`role` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `SBB`.`passenger`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SBB`.`passenger` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `surname` VARCHAR(45) NOT NULL,
  `birthDate` DATE NOT NULL,
  `idUser` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `idUser_idx` (`idUser` ASC),
  CONSTRAINT `idUser`
    FOREIGN KEY (`idUser`)
    REFERENCES `SBB`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `SBB`.`station`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SBB`.`station` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `SBB`.`train`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SBB`.`train` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `number` VARCHAR(45) NOT NULL,
  `capacity` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `SBB`.``
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SBB`.`` (
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `SBB`.`route`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SBB`.`route` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `departureStationId` INT NOT NULL,
  `destinationStationId` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `departureStationId_idx` (`departureStationId` ASC),
  INDEX `destinationStationId_idx` (`destinationStationId` ASC),
  CONSTRAINT `departureStationId`
    FOREIGN KEY (`departureStationId`)
    REFERENCES `SBB`.`station` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `destinationStationId`
    FOREIGN KEY (`destinationStationId`)
    REFERENCES `SBB`.`station` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `SBB`.`ticket`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SBB`.`ticket` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `idPassenger` INT NOT NULL,
  `routeId` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `idPassenger_idx` (`idPassenger` ASC),
  INDEX `trip_idx` (`routeId` ASC),
  CONSTRAINT `idPassenger`
    FOREIGN KEY (`idPassenger`)
    REFERENCES `SBB`.`passenger` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `routeId`
    FOREIGN KEY (`routeId`)
    REFERENCES `SBB`.`route` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `SBB`.`schedule`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SBB`.`schedule` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `trainId` INT NOT NULL,
  `departureTime` DATETIME NOT NULL,
  `destinationTime` DATETIME NOT NULL,
  `routeId` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `trainId_idx` (`trainId` ASC),
  INDEX `routeId_idx` (`routeId` ASC),
  CONSTRAINT `trainId`
    FOREIGN KEY (`trainId`)
    REFERENCES `SBB`.`train` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `srouteId`
    FOREIGN KEY (`routeId`)
    REFERENCES `SBB`.`route` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
