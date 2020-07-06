DROP SCHEMA IF EXISTS `carrental`;
CREATE SCHEMA `carrental`;

use `carrental`;

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
	`id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` varchar(50) NOT NULL,
    `surname` varchar(60) NOT NULL,
    `email` varchar(120) NOT NULL UNIQUE,
    `password` varchar(255) NOT NULL,
    `active` boolean NOT NULL DEFAULT TRUE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
	`id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `role` varchar(20) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
	`user_id` int(11) NOT NULL,
    `role_id` int(11) NOT NULL,
    
    PRIMARY KEY (`user_id`,`role_id`),
    
    KEY `FK_USER_user_role_idx` (`user_id`),
    
    CONSTRAINT `FK_ROLE_user_role` FOREIGN KEY (`role_id`)
    REFERENCES `role` (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION,
    
    CONSTRAINT `FK_USER_user_role` FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
    
DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment` (
	`id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `content` text NOT NULL,
    `time` datetime NOT NULL,
    `user_id` int(11) NOT NULL,
    
    KEY `FK_USER_comment_idx` (`user_id`),
    
    CONSTRAINT `FK_USER_comment` FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `country`;

CREATE TABLE `country` (
	`id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` varchar(100) NOT NULL,
    `iso_code` char(6) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `localization`;

CREATE TABLE `localization` (
	`id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `city` varchar(255) NOT NULL,
    `country_id` int(11),
    
    KEY `FK_COUNTRY_localization_idx` (`country_id`),
    
    CONSTRAINT `FK_COUNTRY_localization` FOREIGN KEY (`country_id`)
    REFERENCES `country` (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `make`;

CREATE TABLE `make` (
	`id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` varchar(100) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `car_model`;

CREATE TABLE `car_model` (
	`id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` varchar(255) NOT NULL,
    `make_id` int(11),
    
    KEY `FK_MAKE_car_model_idx` (`make_id`),
    
    CONSTRAINT `FK_MAKE_car_model` FOREIGN KEY (`make_id`)
    REFERENCES `make` (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `car`;

CREATE TABLE `car` (
	`id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `image` varchar(255),
	`car_model_id` int(11) NOT NULL,
    `localization_id` int(11) NOT NULL,
    `registration_number` varchar(11) NOT NULL,
    `fuel_type` varchar(10) NOT NULL,
    `engine_capacity` float NOT NULL,  
    `horse_power` smallint UNSIGNED NOT NULL,
    `milometer` mediumint UNSIGNED NOT NULL,
    `number_of_doors` tinyint UNSIGNED NOT NULL,
    `description` text,
    `comment_id` int(11),
    
    KEY `FK_COMMENT_car_details_idx` (`comment_id`),
    
    CONSTRAINT `FK_COMMENT_car_details` FOREIGN KEY (`comment_id`)
    REFERENCES `comment` (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION,
    
    KEY `FK_CAR_MODEL_car_idx` (`car_model_id`),
    
    CONSTRAINT `FK_CAR_MODEL_car` FOREIGN KEY (`car_model_id`)
    REFERENCES `car_model` (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION,
    
    KEY `FK_LOCALIZATION_car_idx` (`localization_id`),
    
    CONSTRAINT `FK_LOCALIZATION_car` FOREIGN KEY (`localization_id`)
    REFERENCES `localization` (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION
    
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `reservation`;

CREATE TABLE `reservation` (
  `id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `satus` varchar(100) NOT NULL,
  `from` datetime NOT NULL,
  `to` datetime NOT NULL,
  `user_id` int(11) NOT NULL,
  `car_id` int(11) NOT NULL,
  `comment_id` int(11) NOT NULL,
  
  KEY `FK_USER_reservation_idx` (`user_id`),
  
  CONSTRAINT `FK_USER_reservation` FOREIGN KEY (`user_id`)
  REFERENCES `user` (`id`)
  ON DELETE NO ACTION ON UPDATE NO ACTION,
  
  KEY `FK_CAR_reservation_idx` (`car_id`),
  
  CONSTRAINT `FK_CAR_reservation` FOREIGN KEY (`car_id`)
  REFERENCES `car` (`id`)
  ON DELETE NO ACTION ON UPDATE NO ACTION,
  
  KEY `FK_COMMENT_reservation_idx` (`comment_id`),
  
  CONSTRAINT `FK_COMMENT_reservation` FOREIGN KEY (`comment_id`)
  REFERENCES `comment` (`id`)
  ON DELETE NO ACTION ON UPDATE NO ACTION
  
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `archived_reservation`;

-- Reservations are inserted into this table, when user or car is deleted.
-- TODO: store also comments (probably table comment_archive) to create  
CREATE TABLE `archived_reservation` (
	`id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `status` varchar(100) NOT NULL,
    `from` datetime NOT NULL,
    `to` datetime NOT NULL,
    `user_name` varchar(50) NOT NULL,
    `user_surname` varchar(60) NOT NULL,
    `user_email` varchar(120) NOT NULL,
    `car_make` varchar(100) NOT NULL,
    `car_model` varchar(255) NOT NULL,
	`car_registration_number` varchar(11) NOT NULL
    
) ;

SET FOREIGN_KEY_CHECKS = 1;
