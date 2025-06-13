CREATE SCHEMA IF NOT EXISTS testdb;

SET SCHEMA testdb;

DROP TABLE IF EXISTS `tutorials`;

CREATE TABLE `tutorials` (
  `id` int NOT NULL AUTO_INCREMENT,
   `title` varchar(100) DEFAULT NULL,
   `description` varchar(100) DEFAULT NULL,
   PRIMARY KEY (`id`)
);

CREATE SEQUENCE IF NOT EXISTS tutorials_seq
    START WITH 100
    INCREMENT BY 1
    MINVALUE 100;
