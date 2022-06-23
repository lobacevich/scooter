DROP DATABASE IF EXISTS scooter_rent;
CREATE DATABASE scooter_rent;
USE scooter_rent;

CREATE TABLE `discount_card` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `discount_percent` int NOT NULL,
  `total_sum` int,
  PRIMARY KEY (`id`)
);

CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `firstname` varchar(255) NOT NULL,
  `lastname` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(255) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  `discount_card_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`),
  UNIQUE KEY `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username`),
  KEY `FKnwmjar7xu4985yqvljggcqtya` (`discount_card_id`),
  CONSTRAINT `FKnwmjar7xu4985yqvljggcqtya` FOREIGN KEY (`discount_card_id`) REFERENCES `discount_card` (`id`)
);

CREATE TABLE `city` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_qsstlki7ni5ovaariyy9u8y79` (`name`)
);

CREATE TABLE `point` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) NOT NULL,
  `latitude` int NOT NULL,
  `longitude` int NOT NULL,
  `phone_number` varchar(255) NOT NULL,
  `city_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKt7minb5j2gr37dd16pmuuq7px` (`city_id`),
  CONSTRAINT `FKt7minb5j2gr37dd16pmuuq7px` FOREIGN KEY (`city_id`) REFERENCES `city` (`id`)
);

CREATE TABLE `scooter_model` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `max_speed` int NOT NULL,
  `name` varchar(255) NOT NULL,
  `power_reserve_km` int NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `tariff` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `price_per_first_day` decimal(19,2) NOT NULL,
  `price_per_first_hour` decimal(19,2) NOT NULL,
  `price_per_next_day` decimal(19,2) NOT NULL,
  `price_per_next_hour` decimal(19,2) NOT NULL,
  `model_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKb92mqmhkk46puliixhwpi4emn` (`model_id`),
  CONSTRAINT `FKb92mqmhkk46puliixhwpi4emn` FOREIGN KEY (`model_id`) REFERENCES `scooter_model` (`id`)
);

CREATE TABLE `scooter` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `mileage` double DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `model_id` bigint DEFAULT NULL,
  `point_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKiqeiuhay6je8ggiya6e6u29fx` (`model_id`),
  KEY `FKjnxeg7te62oe8e1obn7t7fp0p` (`point_id`),
  CONSTRAINT `FKiqeiuhay6je8ggiya6e6u29fx` FOREIGN KEY (`model_id`) REFERENCES `scooter_model` (`id`),
  CONSTRAINT `FKjnxeg7te62oe8e1obn7t7fp0p` FOREIGN KEY (`point_id`) REFERENCES `point` (`id`)
);

CREATE TABLE `order_rent` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `closed_date` datetime(6) DEFAULT NULL,
  `created_date` datetime(6) NOT NULL,
  `mileage` double DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `total_cost` decimal(19,2) DEFAULT NULL,
  `end_point_id` bigint DEFAULT NULL,
  `scooter_id` bigint DEFAULT NULL,
  `start_point_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKku64n3y91j9t7qgkua2wmx77m` (`end_point_id`),
  KEY `FKn1i2dafcg0wk7mj991gb520wl` (`scooter_id`),
  KEY `FKmjp8014s9r1iugchifja2vc7d` (`start_point_id`),
  KEY `FK546y12dm0gpauijf94nyk8w3o` (`user_id`),
  CONSTRAINT `FK546y12dm0gpauijf94nyk8w3o` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKku64n3y91j9t7qgkua2wmx77m` FOREIGN KEY (`end_point_id`) REFERENCES `point` (`id`),
  CONSTRAINT `FKmjp8014s9r1iugchifja2vc7d` FOREIGN KEY (`start_point_id`) REFERENCES `point` (`id`),
  CONSTRAINT `FKn1i2dafcg0wk7mj991gb520wl` FOREIGN KEY (`scooter_id`) REFERENCES `scooter` (`id`)
);

INSERT INTO scooter_rent.user (`id`,`email`,`firstname`,`lastname`,`password`,`role`,`username`,`discount_card_id`)
VALUES (1,'admin@mail.ru','admin','admin','$2a$10$r2lUz7IrFdOXbuPjQohVN.d8A.1id9am2KJr9a..2BzGglO7Ic/uq','ROLE_ADMIN','admin',NULL);

INSERT INTO city (`id`,`name`) VALUES (1,'Minsk');
INSERT INTO city (`id`,`name`) VALUES (2,'Molodechno');

INSERT INTO scooter_model (`id`,`max_speed`,`name`,`power_reserve_km`) VALUES (1,35,'КUGOO S3',30);
INSERT INTO scooter_model (`id`,`max_speed`,`name`,`power_reserve_km`) VALUES (2,45,'КUGOO M4 PRO',40);
INSERT INTO scooter_model (`id`,`max_speed`,`name`,`power_reserve_km`) VALUES (3,50,'КUGOO C2',50);

INSERT INTO point (`id`,`phone_number`,`address`,`latitude`,`longitude`,`city_id`) VALUES (1,'+375 29 607-71-47','3B, Beruta str., off. 405',83,36,1);
INSERT INTO point (`id`,`phone_number`,`address`,`latitude`,`longitude`,`city_id`) VALUES (2,'+375 33 363-56-35','16, Logindkaya str.',92,28,1);
INSERT INTO point (`id`,`phone_number`,`address`,`latitude`,`longitude`,`city_id`) VALUES (3,'+375 29 668-48-50','11, Dzerzhinsky avenue, off. 93',70,16,1);
INSERT INTO point (`id`,`phone_number`,`address`,`latitude`,`longitude`,`city_id`) VALUES (4,'+375 29 559-52-05','3, Ilyanskaya str.',10,115,2);

INSERT INTO scooter (`id`,`mileage`,`status`,`model_id`,`point_id`) VALUES (1,0,'VACANT',1,1);
INSERT INTO scooter (`id`,`mileage`,`status`,`model_id`,`point_id`) VALUES (2,0,'VACANT',1,1);
INSERT INTO scooter (`id`,`mileage`,`status`,`model_id`,`point_id`) VALUES (3,0,'VACANT',2,1);
INSERT INTO scooter (`id`,`mileage`,`status`,`model_id`,`point_id`) VALUES (4,0,'VACANT',2,1);
INSERT INTO scooter (`id`,`mileage`,`status`,`model_id`,`point_id`) VALUES (5,0,'VACANT',3,1);
INSERT INTO scooter (`id`,`mileage`,`status`,`model_id`,`point_id`) VALUES (6,0,'VACANT',3,1);
INSERT INTO scooter (`id`,`mileage`,`status`,`model_id`,`point_id`) VALUES (7,0,'VACANT',1,2);
INSERT INTO scooter (`id`,`mileage`,`status`,`model_id`,`point_id`) VALUES (8,0,'VACANT',1,2);
INSERT INTO scooter (`id`,`mileage`,`status`,`model_id`,`point_id`) VALUES (9,0,'VACANT',2,2);
INSERT INTO scooter (`id`,`mileage`,`status`,`model_id`,`point_id`) VALUES (10,0,'VACANT',2,2);
INSERT INTO scooter (`id`,`mileage`,`status`,`model_id`,`point_id`) VALUES (11,0,'VACANT',3,2);
INSERT INTO scooter (`id`,`mileage`,`status`,`model_id`,`point_id`) VALUES (12,0,'VACANT',3,2);
INSERT INTO scooter (`id`,`mileage`,`status`,`model_id`,`point_id`) VALUES (13,0,'VACANT',1,3);
INSERT INTO scooter (`id`,`mileage`,`status`,`model_id`,`point_id`) VALUES (14,0,'VACANT',1,3);
INSERT INTO scooter (`id`,`mileage`,`status`,`model_id`,`point_id`) VALUES (15,0,'VACANT',2,3);
INSERT INTO scooter (`id`,`mileage`,`status`,`model_id`,`point_id`) VALUES (16,0,'VACANT',2,3);
INSERT INTO scooter (`id`,`mileage`,`status`,`model_id`,`point_id`) VALUES (17,0,'VACANT',3,3);
INSERT INTO scooter (`id`,`mileage`,`status`,`model_id`,`point_id`) VALUES (18,0,'VACANT',3,3);
INSERT INTO scooter (`id`,`mileage`,`status`,`model_id`,`point_id`) VALUES (19,0,'VACANT',1,4);
INSERT INTO scooter (`id`,`mileage`,`status`,`model_id`,`point_id`) VALUES (20,0,'VACANT',1,4);
INSERT INTO scooter (`id`,`mileage`,`status`,`model_id`,`point_id`) VALUES (21,0,'VACANT',2,4);
INSERT INTO scooter (`id`,`mileage`,`status`,`model_id`,`point_id`) VALUES (22,0,'VACANT',2,4);
INSERT INTO scooter (`id`,`mileage`,`status`,`model_id`,`point_id`) VALUES (23,0,'VACANT',3,4);
INSERT INTO scooter (`id`,`mileage`,`status`,`model_id`,`point_id`) VALUES (24,0,'VACANT',3,4);

INSERT INTO tariff (`id`,`price_per_first_day`,`price_per_first_hour`,`price_per_next_day`,`price_per_next_hour`,`model_id`) VALUES (1,30.00,10.00,20.00,7.00,1);
INSERT INTO tariff (`id`,`price_per_first_day`,`price_per_first_hour`,`price_per_next_day`,`price_per_next_hour`,`model_id`) VALUES (2,40.00,15.00,27.00,10.00,2);
INSERT INTO tariff (`id`,`price_per_first_day`,`price_per_first_hour`,`price_per_next_day`,`price_per_next_hour`,`model_id`) VALUES (3,50.00,20.00,34.00,13.00,3);
