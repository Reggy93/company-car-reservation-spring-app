DROP SCHEMA IF EXISTS `carrentaltest`;
CREATE SCHEMA `carrentaltest`;

use `carrentaltest`;

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
	`id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` varchar(50) NOT NULL,
    `surname` varchar(60) NOT NULL,
    `email` varchar(120) NOT NULL UNIQUE,
    `password` varchar(255) NOT NULL,
    `phone_number` varchar(12),
    `active` boolean NOT NULL DEFAULT TRUE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `roles`;

CREATE TABLE `roles` (
	`id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `role` varchar(20) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `users_roles`;

CREATE TABLE `users_roles` (
	`user_id` int(11) NOT NULL,
    `role_id` int(11) NOT NULL,

    PRIMARY KEY (`user_id`,`role_id`),

    KEY `FK_USERS_users_roles_idx` (`user_id`),

    CONSTRAINT `FK_ROLES_users_roles` FOREIGN KEY (`role_id`)
    REFERENCES `roles` (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION,

    CONSTRAINT `FK_USERS_users_roles` FOREIGN KEY (`user_id`)
    REFERENCES `users` (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `comments`;

CREATE TABLE `comments` (
	`id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `content` text NOT NULL,
    `time` datetime NOT NULL,
    `user_id` int(11) NOT NULL,
    `car_id` int(11) DEFAULT NULL,
    `reservation_id` int(11) DEFAULT NULL,

    KEY `FK_USERS_comments_idx` (`user_id`),

    CONSTRAINT `FK_USERS_comments` FOREIGN KEY (`user_id`)
    REFERENCES `users` (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION,

    KEY `FK_CARS_comments_idx` (`car_id`),

    CONSTRAINT `FK_CARS_comments` FOREIGN KEY (`car_id`)
    REFERENCES `cars` (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION,

    KEY `FK_RESERVATIONS_comments_idx` (`reservation_id`),

    CONSTRAINT `FK_RESERVATIONS_comments` FOREIGN KEY (`reservation_id`)
    REFERENCES `reservations` (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION

) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `countries`;

CREATE TABLE `countries` (
	`id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` varchar(100) NOT NULL,
    `iso_code` char(6) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `localizations`;

CREATE TABLE `localizations` (
	`id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `city` varchar(255) NOT NULL,
    `country_id` int(11),

    KEY `FK_COUNTRIES_localizations_idx` (`country_id`),

    CONSTRAINT `FK_COUNTRIES_localizations` FOREIGN KEY (`country_id`)
    REFERENCES `countries` (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `makes`;

CREATE TABLE `makes` (
	`id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` varchar(100) NOT NULL UNIQUE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `cars_models`;

CREATE TABLE `cars_models` (
	`id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` varchar(255) NOT NULL UNIQUE,
    `make_id` int(11),

    KEY `FK_MAKES_cars_models_idx` (`make_id`),

    CONSTRAINT `FK_MAKES_cars_models` FOREIGN KEY (`make_id`)
    REFERENCES `makes` (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `cars`;

CREATE TABLE `cars` (
	`id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `image` varchar(255),
    `currently_available` boolean,
	`car_model_id` int(11) NOT NULL,
    `localization_id` int(11) NOT NULL,
    `registration_number` varchar(11) NOT NULL UNIQUE,
    `fuel_type` varchar(10) NOT NULL,
    `engine_capacity` float NOT NULL,
    `horse_power` smallint UNSIGNED NOT NULL,
    `milometer` mediumint UNSIGNED NOT NULL,
    `number_of_doors` tinyint UNSIGNED NOT NULL,
    `description` text,
    -- `comment_id` int(11),

    -- KEY `FK_COMMENTS_cars_details_idx` (`comment_id`),
--
--     CONSTRAINT `FK_COMMENTS_cars_details` FOREIGN KEY (`comment_id`)
--     REFERENCES `comments` (`id`)
--     ON DELETE NO ACTION ON UPDATE NO ACTION,

    KEY `FK_CARS_MODELS_cars_idx` (`car_model_id`),

    CONSTRAINT `FK_CARS_MODELS_cars` FOREIGN KEY (`car_model_id`)
    REFERENCES `cars_models` (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION,

    KEY `FK_LOCALIZATIONS_cars_idx` (`localization_id`),

    CONSTRAINT `FK_LOCALIZATIONS_cars` FOREIGN KEY (`localization_id`)
    REFERENCES `localizations` (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION

) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `reservations`;

CREATE TABLE `reservations` (
  `id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `status` varchar(100) NOT NULL,
  `from` datetime NOT NULL,
  `to` datetime NOT NULL,
  `type` varchar(10) NOT NULL,
  `user_id` int(11) NOT NULL,
  `car_id` int(11) NOT NULL,
  -- `comment_id` int(11) NOT NULL,

  KEY `FK_USERS_reservations_idx` (`user_id`),

  CONSTRAINT `FK_USERS_reservations` FOREIGN KEY (`user_id`)
  REFERENCES `users` (`id`)
  ON DELETE NO ACTION ON UPDATE NO ACTION,

  KEY `FK_CARS_reservations_idx` (`car_id`),

  CONSTRAINT `FK_CARS_reservations` FOREIGN KEY (`car_id`)
  REFERENCES `cars` (`id`)
  ON DELETE NO ACTION ON UPDATE NO ACTION

  -- KEY `FK_COMMENTS_reservations_idx` (`comment_id`),
--
--   CONSTRAINT `FK_COMMENTS_reservations` FOREIGN KEY (`comment_id`)
--   REFERENCES `comments` (`id`)
--   ON DELETE NO ACTION ON UPDATE NO ACTION

) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `archived_reservations`;

-- Reservations are inserted into this table, when user or car is deleted.
-- TODO: store also comments (probably table comment_archive) to create
CREATE TABLE `archived_reservations` (
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
