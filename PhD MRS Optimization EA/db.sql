/*
SQLyog Community v10.2 
MySQL - 5.5.27 : Database - mrs
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
USE `mrs`;

/*Table structure for table `evolution` */

DROP TABLE IF EXISTS `evolution`;

CREATE TABLE `evolution` (
  `process_id` int(10) unsigned NOT NULL,
  `generation` int(10) unsigned NOT NULL,
  `chromosome` smallint(5) unsigned NOT NULL,
  `age` int(10) unsigned DEFAULT NULL,
  `fitness_value` double NOT NULL,
  `best_ind` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`process_id`,`generation`,`chromosome`),
  CONSTRAINT `evolution_ibfk_1` FOREIGN KEY (`process_id`) REFERENCES `process` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `process` */

DROP TABLE IF EXISTS `process`;

CREATE TABLE `process` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `start_time` datetime NOT NULL,
  `end_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
