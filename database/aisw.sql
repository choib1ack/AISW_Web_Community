-- MySQL Script generated by MySQL Workbench
-- Mon Jul 12 16:58:47 2021
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema aisw
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema aisw
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `aisw` DEFAULT CHARACTER SET utf8mb4 ;
USE `aisw` ;

-- -----------------------------------------------------
-- Table `aisw`.`admin_user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `aisw`.`admin_user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `password` VARCHAR(1000) NOT NULL,
  `phone_number` VARCHAR(45) NOT NULL,
  `created_at` DATETIME NOT NULL,
  `created_by` VARCHAR(45) NOT NULL,
  `updated_at` DATETIME NULL DEFAULT NULL,
  `updated_by` VARCHAR(45) NULL DEFAULT NULL,
  `role` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  UNIQUE INDEX `phone_number_UNIQUE` (`phone_number` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `aisw`.`account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `aisw`.`account` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `phone_number` VARCHAR(45) NOT NULL,
  `grade` VARCHAR(45) NULL DEFAULT NULL,
  `student_id` VARCHAR(45) NOT NULL,
  `created_at` DATETIME NOT NULL,
  `created_by` VARCHAR(45) NOT NULL,
  `updated_at` DATETIME NULL DEFAULT NULL,
  `updated_by` VARCHAR(45) NULL DEFAULT NULL,
  `gender` VARCHAR(45) NOT NULL,
  `university` VARCHAR(45) NOT NULL,
  `college_name` VARCHAR(45) NOT NULL,
  `department_name` VARCHAR(45) NOT NULL,
  `role` VARCHAR(45) NULL,
  `password` VARCHAR(1000) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `student_id_UNIQUE` (`student_id` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `aisw`.`bulletin`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `aisw`.`bulletin` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` LONGTEXT NOT NULL,
  `writer` VARCHAR(45) NOT NULL,
  `content` LONGTEXT NOT NULL,
  `status` VARCHAR(45) NOT NULL,
  `views` INT NOT NULL,
  `first_category` VARCHAR(45) NOT NULL,
  `second_category` VARCHAR(45) NOT NULL,
  `created_by` VARCHAR(45) NOT NULL,
  `created_at` DATETIME NOT NULL,
  `updated_by` VARCHAR(45) NULL DEFAULT NULL,
  `updated_at` DATETIME NULL DEFAULT NULL,
  `DTYPE` VARCHAR(45) NOT NULL,
  `account_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_bulletin_user1`
    FOREIGN KEY (`account_id`)
    REFERENCES `aisw`.`account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `aisw`.`board`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `aisw`.`board` (
  `category` VARCHAR(45) NOT NULL,
  `likes` INT NOT NULL,
  `is_anonymous` TINYINT NOT NULL,
  `id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_board_bulletin1`
    FOREIGN KEY (`id`)
    REFERENCES `aisw`.`bulletin` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `aisw`.`notice`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `aisw`.`notice` (
  `category` VARCHAR(45) NOT NULL,
  `id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_notice_bulletin1`
    FOREIGN KEY (`id`)
    REFERENCES `aisw`.`bulletin` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `aisw`.`council`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `aisw`.`council` (
  `id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_council_notice1`
    FOREIGN KEY (`id`)
    REFERENCES `aisw`.`notice` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `aisw`.`department`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `aisw`.`department` (
  `id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_department_notice1`
    FOREIGN KEY (`id`)
    REFERENCES `aisw`.`notice` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci
ROW_FORMAT = DEFAULT;


-- -----------------------------------------------------
-- Table `aisw`.`free`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `aisw`.`free` (
  `id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_free_board1`
    FOREIGN KEY (`id`)
    REFERENCES `aisw`.`board` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `aisw`.`qna`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `aisw`.`qna` (
  `subject` VARCHAR(45) NOT NULL,
  `id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_qna_board1`
    FOREIGN KEY (`id`)
    REFERENCES `aisw`.`board` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `aisw`.`university`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `aisw`.`university` (
  `campus` VARCHAR(45) NOT NULL,
  `id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_university_notice1`
    FOREIGN KEY (`id`)
    REFERENCES `aisw`.`notice` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `aisw`.`comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `aisw`.`comment` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `writer` VARCHAR(45) NOT NULL,
  `content` LONGTEXT NOT NULL,
  `is_anonymous` TINYINT NOT NULL,
  `is_deleted` TINYINT NOT NULL,
  `likes` INT NOT NULL,
  `created_at` DATETIME NOT NULL,
  `created_by` VARCHAR(45) NOT NULL,
  `account_id` INT NOT NULL,
  `board_id` INT NOT NULL,
  `super_comment_id` INT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_comment_user1`
    FOREIGN KEY (`account_id`)
    REFERENCES `aisw`.`account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_comment_board1`
    FOREIGN KEY (`board_id`)
    REFERENCES `aisw`.`board` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_comment_comment1`
    FOREIGN KEY (`super_comment_id`)
    REFERENCES `aisw`.`comment` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `aisw`.`content_like`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `aisw`.`content_like` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `account_id` INT NULL,
  `comment_id` INT NULL,
  `board_id` INT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_Like_account1`
    FOREIGN KEY (`account_id`)
    REFERENCES `aisw`.`account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Like_comment1`
    FOREIGN KEY (`comment_id`)
    REFERENCES `aisw`.`comment` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Like_board1`
    FOREIGN KEY (`board_id`)
    REFERENCES `aisw`.`board` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `aisw`.`site_information`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `aisw`.`site_information` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
  `content` VARCHAR(1000) NULL,
  `publish_status` TINYINT NOT NULL,
  `link_url` VARCHAR(1000) NULL,
  `category` VARCHAR(45) NULL,
  `created_at` DATETIME NOT NULL,
  `created_by` VARCHAR(45) NOT NULL,
  `updated_at` DATETIME NULL,
  `updated_by` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `aisw`.`banner`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `aisw`.`banner` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
  `content` VARCHAR(1000) NULL,
  `start_date` DATETIME NOT NULL,
  `end_date` DATETIME NOT NULL,
  `publish_status` TINYINT NOT NULL,
  `link_url` VARCHAR(1000) NULL,
  `created_at` DATETIME NOT NULL,
  `created_by` VARCHAR(45) NOT NULL,
  `updated_at` DATETIME NULL,
  `updated_by` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `aisw`.`file`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `aisw`.`file` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `file_name` LONGTEXT NOT NULL,
  `file_download_uri` LONGTEXT NOT NULL,
  `file_type` VARCHAR(1000) NOT NULL,
  `file_size` INT NOT NULL,
  `created_at` DATETIME NOT NULL,
  `created_by` VARCHAR(45) NOT NULL,
  `updated_at` DATETIME NULL,
  `updated_by` VARCHAR(45) NULL,
  `bulletin_id` INT NULL,
  `site_information_id` INT NULL,
  `banner_id` INT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_attachment_bulletin1`
    FOREIGN KEY (`bulletin_id`)
    REFERENCES `aisw`.`bulletin` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_file_site_information1`
    FOREIGN KEY (`site_information_id`)
    REFERENCES `aisw`.`site_information` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_file_banner1`
    FOREIGN KEY (`banner_id`)
    REFERENCES `aisw`.`banner` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
