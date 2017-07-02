/*
SQLyog Ultimate v11.11 (32 bit)
MySQL - 5.5.5-10.1.16-MariaDB : Database - ebdesk_intern
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`ebdesk_intern` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `ebdesk_intern`;

/*Table structure for table `division` */

DROP TABLE IF EXISTS `division`;

CREATE TABLE `division` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `division` */

insert  into `division`(`id`,`name`) values (1,'Android'),(2,'Website'),(3,'Testing'),(4,'Riset');

/*Table structure for table `project` */

DROP TABLE IF EXISTS `project`;

CREATE TABLE `project` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `status` int(10) NOT NULL,
  `price` int(10) NOT NULL,
  `size` int(10) NOT NULL,
  `time` varchar(255) NOT NULL,
  `start_time` date NOT NULL,
  `current` int(10) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=latin1;

/*Data for the table `project` */

insert  into `project`(`id`,`name`,`status`,`price`,`size`,`time`,`start_time`,`current`) values (29,'Project Beneran',1,5000000,10,'1 bulan','2006-06-17',1),(32,'Project Ketiga',1,5000000,3,'2 bulan','2007-06-17',3);

/*Table structure for table `project_skill` */

DROP TABLE IF EXISTS `project_skill`;

CREATE TABLE `project_skill` (
  `id_project` int(10) NOT NULL,
  `id_skill` int(10) NOT NULL,
  PRIMARY KEY (`id_project`,`id_skill`),
  KEY `FK1g3pvuqghog45pm37tie3ancn` (`id_skill`),
  CONSTRAINT `FK1g3pvuqghog45pm37tie3ancn` FOREIGN KEY (`id_skill`) REFERENCES `skill` (`id`),
  CONSTRAINT `FKct6d7pe6vwo8u7w2gynknbkwv` FOREIGN KEY (`id_project`) REFERENCES `project` (`id`),
  CONSTRAINT `project_skill_ibfk_1` FOREIGN KEY (`id_project`) REFERENCES `project` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `project_skill_ibfk_2` FOREIGN KEY (`id_skill`) REFERENCES `skill` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `project_skill` */

insert  into `project_skill`(`id_project`,`id_skill`) values (29,3),(29,4);

/*Table structure for table `skill` */

DROP TABLE IF EXISTS `skill`;

CREATE TABLE `skill` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `skill` */

insert  into `skill`(`id`,`name`) values (1,'Laravel'),(2,'Spring'),(3,'Java'),(4,'PHP'),(5,'Android');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `role` int(10) NOT NULL,
  `totalproject` int(10) NOT NULL DEFAULT '0',
  `totalstar` int(10) NOT NULL DEFAULT '0',
  `ongoingproject` int(10) NOT NULL DEFAULT '0',
  `status` int(10) DEFAULT NULL,
  `division_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhtbcjved07of07x7s5nrjxgje` (`division_id`),
  CONSTRAINT `FKhtbcjved07of07x7s5nrjxgje` FOREIGN KEY (`division_id`) REFERENCES `division` (`id`),
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`division_id`) REFERENCES `division` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

/*Data for the table `user` */

insert  into `user`(`id`,`email`,`password`,`first_name`,`last_name`,`phone`,`role`,`totalproject`,`totalstar`,`ongoingproject`,`status`,`division_id`) values (7,'adhikagunadarma@gmail.com','$2a$10$6QHOzLsOhp6wGnwNUJz6oO/gzv16jEEfjGfc7vA5jn3coCC7lJ5pe','Adhika','Gunadarma','089690907891',1,0,0,0,0,1),(8,'mariochrist@gmail.com','$2a$10$v2/POWc814C.t7QhTt.FEOPYZwKq8nWihvaUNa4zY2nmORc58nHpG','Mario','Christ','012312312',3,0,0,0,0,1),(9,'levina022@gmail.com','$2a$10$uL7hq95gFZMfb20D3HB9qe2CntaZzFAP7ZSXqEUPOSz9i5HTDfL1G','Levina M','Mulia','0912768123751',3,0,0,0,0,2),(10,'skolastikasyenna@gmail.com','$2a$10$VS4j1/Kwq5amtrYuPyrbbeNwBg68kYjo8syQ/EsVzFH38VGY4kyN6','Skolastika ','Syenna','012313914',3,0,0,0,0,NULL),(11,'basukiahok@gmail.com','$2a$10$Tjf0FMNgNJn2FDeCKG65s.5fm8xvE7K2XQiY.w3iBtMXgZVYG2Vb2','Basuki','Ahok','131212312312312',3,0,0,0,0,NULL);

/*Table structure for table `user_project` */

DROP TABLE IF EXISTS `user_project`;

CREATE TABLE `user_project` (
  `id_user` int(10) NOT NULL,
  `id_project` int(10) NOT NULL,
  `rate` int(10) NOT NULL DEFAULT '0',
  `roles` int(10) DEFAULT NULL,
  PRIMARY KEY (`id_user`,`id_project`),
  KEY `FKocfkr6u2yh3w1qmybs8vxuv1c` (`id_project`),
  CONSTRAINT `FK5aqmr4dy6a5xnnsm4gdryxpqj` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`),
  CONSTRAINT `FKocfkr6u2yh3w1qmybs8vxuv1c` FOREIGN KEY (`id_project`) REFERENCES `project` (`id`),
  CONSTRAINT `FKp3iw9p7ar8e6ckuvvj8pj4757` FOREIGN KEY (`id_project`) REFERENCES `project` (`id`),
  CONSTRAINT `FKpw81exe7fsdl7mddqujvu91kx` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`),
  CONSTRAINT `user_project_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_project_ibfk_2` FOREIGN KEY (`id_project`) REFERENCES `project` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `user_project` */

insert  into `user_project`(`id_user`,`id_project`,`rate`,`roles`) values (8,29,0,1),(8,32,0,2),(10,32,0,1),(11,32,0,2);

/*Table structure for table `user_skill` */

DROP TABLE IF EXISTS `user_skill`;

CREATE TABLE `user_skill` (
  `id_user` int(10) NOT NULL,
  `id_skill` int(10) NOT NULL,
  PRIMARY KEY (`id_user`,`id_skill`),
  KEY `FKrsvxrshr30q4756yoxncoaeyl` (`id_skill`),
  CONSTRAINT `FKkwwol8ie05lcjsko1qewnm6r7` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`),
  CONSTRAINT `FKrsvxrshr30q4756yoxncoaeyl` FOREIGN KEY (`id_skill`) REFERENCES `skill` (`id`),
  CONSTRAINT `user_skill_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_skill_ibfk_2` FOREIGN KEY (`id_skill`) REFERENCES `skill` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `user_skill` */

insert  into `user_skill`(`id_user`,`id_skill`) values (7,1),(7,2),(7,3),(8,1),(8,3),(9,2),(9,3);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
