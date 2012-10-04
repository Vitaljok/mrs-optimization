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

/*Table structure for table `agent` */

DROP TABLE IF EXISTS `agent`;

CREATE TABLE `agent` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` longtext,
  `project_id` smallint(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_AGENT_project_id` (`project_id`),
  CONSTRAINT `FK_AGENT_project_id` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=423 DEFAULT CHARSET=utf8;

/*Table structure for table `agent_component` */

DROP TABLE IF EXISTS `agent_component`;

CREATE TABLE `agent_component` (
  `agent_id` int(11) NOT NULL,
  `component_id` int(11) NOT NULL,
  PRIMARY KEY (`agent_id`,`component_id`),
  KEY `FK_agent_component_component_id` (`component_id`),
  CONSTRAINT `FK_agent_component_agent_id` FOREIGN KEY (`agent_id`) REFERENCES `agent` (`id`),
  CONSTRAINT `FK_agent_component_component_id` FOREIGN KEY (`component_id`) REFERENCES `component` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `component` */

DROP TABLE IF EXISTS `component`;

CREATE TABLE `component` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `complexity` double DEFAULT NULL,
  `family` varchar(255) DEFAULT NULL,
  `investment_costs` double DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `operating_power` double DEFAULT NULL,
  `project_id` smallint(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNQ_COMPONENT_0` (`project_id`,`code`),
  CONSTRAINT `FK_COMPONENT_project_id` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

/*Table structure for table `evolution` */

DROP TABLE IF EXISTS `evolution`;

CREATE TABLE `evolution` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fitness_value` double DEFAULT NULL,
  `generation` int(11) DEFAULT NULL,
  `project_id` smallint(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNQ_EVOLUTION_0` (`project_id`,`generation`),
  CONSTRAINT `FK_EVOLUTION_project_id` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=118 DEFAULT CHARSET=utf8;

/*Table structure for table `evolution_agent` */

DROP TABLE IF EXISTS `evolution_agent`;

CREATE TABLE `evolution_agent` (
  `evolution_id` int(11) DEFAULT NULL,
  `inst_num` int(11) DEFAULT NULL,
  `agent_id` int(11) DEFAULT NULL,
  KEY `FK_evolution_agent_evolution_id` (`evolution_id`),
  KEY `FK_evolution_agent_agent_id` (`agent_id`),
  CONSTRAINT `FK_evolution_agent_agent_id` FOREIGN KEY (`agent_id`) REFERENCES `agent` (`id`),
  CONSTRAINT `FK_evolution_agent_evolution_id` FOREIGN KEY (`evolution_id`) REFERENCES `evolution` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `mission` */

DROP TABLE IF EXISTS `mission`;

CREATE TABLE `mission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mission_type` varchar(31) DEFAULT NULL,
  `area_size_x` double DEFAULT NULL,
  `area_size_y` double DEFAULT NULL,
  `mobile_base_speed` double DEFAULT NULL,
  `work_density` double DEFAULT NULL,
  `project_id` smallint(6) DEFAULT NULL,
  `mobile_base_id` int(11) DEFAULT NULL,
  `work_device_width` double DEFAULT NULL,
  `work_device_id` int(11) DEFAULT NULL,
  `target_offset_x` double DEFAULT NULL,
  `target_offset_y` double DEFAULT NULL,
  `loader_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNQ_MISSION_0` (`project_id`,`mission_type`),
  KEY `FK_MISSION_mobile_base_id` (`mobile_base_id`),
  KEY `FK_MISSION_work_device_id` (`work_device_id`),
  KEY `FK_MISSION_loader_id` (`loader_id`),
  CONSTRAINT `FK_MISSION_project_id` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`),
  CONSTRAINT `FK_MISSION_loader_id` FOREIGN KEY (`loader_id`) REFERENCES `component` (`id`),
  CONSTRAINT `FK_MISSION_mobile_base_id` FOREIGN KEY (`mobile_base_id`) REFERENCES `component` (`id`),
  CONSTRAINT `FK_MISSION_work_device_id` FOREIGN KEY (`work_device_id`) REFERENCES `component` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Table structure for table `project` */

DROP TABLE IF EXISTS `project`;

CREATE TABLE `project` (
  `id` smallint(6) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `generations_limit` int(11) DEFAULT NULL,
  `crossover_rate` double DEFAULT NULL,
  `doublette_chromosomes_allowed` tinyint(1) DEFAULT '0',
  `keep_population_size_constant` tinyint(1) DEFAULT '0',
  `generations_step` smallint(6) DEFAULT NULL,
  `population_size` smallint(6) DEFAULT NULL,
  `agent_instance_limit` smallint(6) DEFAULT NULL,
  `minimum_pop_size_percent` smallint(6) DEFAULT NULL,
  `mutation_rate` smallint(6) DEFAULT NULL,
  `near_nfinity` double DEFAULT NULL,
  `near_zero` double DEFAULT NULL,
  `select_from_prev_gen` double DEFAULT NULL,
  `selector_original_rate` double DEFAULT NULL,
  `system_repl_rate` double DEFAULT NULL,
  `assembly_b0` double DEFAULT NULL,
  `assembly_b1` double DEFAULT NULL,
  `assembly_b2` double DEFAULT NULL,
  `assembly_k` double DEFAULT NULL,
  `design_b0` double DEFAULT NULL,
  `design_b1` double DEFAULT NULL,
  `design_b2` double DEFAULT NULL,
  `design_k` double DEFAULT NULL,
  `energy_loss_b0` double DEFAULT NULL,
  `energy_loss_b1` double DEFAULT NULL,
  `energy_loss_b2` double DEFAULT NULL,
  `energy_loss_k` double DEFAULT NULL,
  `sys_design_b0` double DEFAULT NULL,
  `sys_design_b1` double DEFAULT NULL,
  `sys_design_b2` double DEFAULT NULL,
  `sys_design_k` double DEFAULT NULL,
  `sys_maint_b0` double DEFAULT NULL,
  `sys_maint_b1` double DEFAULT NULL,
  `sys_maint_b2` double DEFAULT NULL,
  `sys_maint_k` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Table structure for table `requirement` */

DROP TABLE IF EXISTS `requirement`;

CREATE TABLE `requirement` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `comment` varchar(255) DEFAULT NULL,
  `component_id` int(11) DEFAULT NULL,
  `ref_component_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNQ_REQUIREMENT_0` (`component_id`,`ref_component_id`),
  KEY `FK_REQUIREMENT_ref_component_id` (`ref_component_id`),
  CONSTRAINT `FK_REQUIREMENT_component_id` FOREIGN KEY (`component_id`) REFERENCES `component` (`id`),
  CONSTRAINT `FK_REQUIREMENT_ref_component_id` FOREIGN KEY (`ref_component_id`) REFERENCES `component` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
