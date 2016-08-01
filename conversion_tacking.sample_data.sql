-- MySQL dump 10.13  Distrib 5.7.13, for Win64 (x86_64)
--
-- Host: localhost    Database: conversion_tracking
-- ------------------------------------------------------
-- Server version	5.7.13-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `areas`
--

DROP TABLE IF EXISTS `areas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `areas` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `version` tinyint(4) DEFAULT NULL,
  `anonymous_count` bigint(20) DEFAULT NULL,
  `num` int(11) DEFAULT NULL,
  `handle` varchar(255) DEFAULT NULL,
  `user_login_count` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `areas`
--

LOCK TABLES `areas` WRITE;
/*!40000 ALTER TABLE `areas` DISABLE KEYS */;
INSERT INTO `areas` VALUES (1,'2016-07-15 15:15:29','2016-07-18 20:48:03',0,4,1,'sudamerica',4),(2,'2016-07-15 15:15:45','2016-07-15 17:41:09',0,2,2,'america del norte',1),(3,'2016-07-15 17:23:48','2016-07-15 17:42:47',0,-1,3,'asia',2);
/*!40000 ALTER TABLE `areas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hits`
--

DROP TABLE IF EXISTS `hits`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hits` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `version` tinyint(4) DEFAULT NULL,
  `anonymous_count` bigint(20) DEFAULT NULL,
  `day_of_year` int(11) DEFAULT NULL,
  `year` int(11) DEFAULT NULL,
  `url_id` int(10) unsigned DEFAULT NULL,
  `registered_count` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_s5o233eg5n9e32nqtlp2ukygy` (`url_id`),
  CONSTRAINT `FK_s5o233eg5n9e32nqtlp2ukygy` FOREIGN KEY (`url_id`) REFERENCES `urls` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hits`
--

LOCK TABLES `hits` WRITE;
/*!40000 ALTER TABLE `hits` DISABLE KEYS */;
INSERT INTO `hits` VALUES (1,'2016-07-20 14:04:13','2016-07-21 14:21:07',0,1,190,2016,3,1),(2,'2016-07-20 14:04:15','2016-07-21 14:21:07',0,1,160,2016,3,1),(3,'2016-07-20 14:04:16','2016-07-21 14:22:51',0,1,201,2015,3,0),(5,'2016-07-20 15:46:53','2016-07-21 14:21:07',0,16,202,2016,8,10),(8,'2016-07-20 15:50:08','2016-07-21 14:21:07',0,2,202,2016,10,1),(9,'2016-07-20 15:50:12','2016-07-21 14:22:51',0,1,202,2016,13,0),(32,'2016-07-20 20:13:01','2016-07-21 14:21:07',0,2,202,2016,3,1),(33,'2016-07-21 12:22:33','2016-07-21 17:37:39',0,8,203,2016,3,9),(34,'2016-07-21 13:46:56','2016-07-21 14:22:51',0,2,203,2016,13,0),(35,'2016-07-21 13:52:32','2016-07-21 14:21:07',0,1,203,2016,10,1),(36,'2016-07-22 12:57:40','2016-07-22 12:57:45',1,0,204,2016,10,1),(37,'2016-07-22 16:59:45','2016-07-22 17:02:57',1,5,204,2016,8,4);
/*!40000 ALTER TABLE `hits` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `locations`
--

DROP TABLE IF EXISTS `locations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `locations` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `version` tinyint(4) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `area_id` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_rm8r8o6qchx43v7ycubqonjpk` (`area_id`),
  CONSTRAINT `FK_rm8r8o6qchx43v7ycubqonjpk` FOREIGN KEY (`area_id`) REFERENCES `areas` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `locations`
--

LOCK TABLES `locations` WRITE;
/*!40000 ALTER TABLE `locations` DISABLE KEYS */;
INSERT INTO `locations` VALUES (2,'2016-07-15 15:24:27','2016-07-15 15:24:27',0,'USA',2),(3,'2016-07-15 15:24:54','2016-07-15 15:24:54',0,'México',2),(4,'2016-07-15 15:24:59','2016-07-15 15:24:59',0,'Canada',2),(5,'2016-07-15 15:25:06','2016-07-15 15:25:06',0,'Argentina',1),(6,'2016-07-15 15:25:11','2016-07-15 15:25:11',0,'Brasil',1),(7,'2016-07-15 17:25:22','2016-07-15 17:25:22',0,'澳门',3);
/*!40000 ALTER TABLE `locations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `urls`
--

DROP TABLE IF EXISTS `urls`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `urls` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `version` tinyint(4) DEFAULT NULL,
  `anonymous_count` bigint(20) DEFAULT NULL,
  `base_url` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `label` varchar(255) NOT NULL,
  `extension_url` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_s65xbdsm6149qnaevew7ex1wr` (`label`),
  UNIQUE KEY `UK_d17k7t8abplagawi2lop62wxs` (`base_url`,`extension_url`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `urls`
--

LOCK TABLES `urls` WRITE;
/*!40000 ALTER TABLE `urls` DISABLE KEYS */;
INSERT INTO `urls` VALUES (3,'2016-07-19 18:06:18','2016-07-19 20:23:19',0,1,'/login','third times a charm','third',2),(8,'2016-07-19 18:13:09','2016-07-19 18:13:09',0,0,'/login','what\'s four for?','four',3),(10,'2016-07-19 18:15:23','2016-07-19 18:15:23',0,0,'/login','this should be extension 4!','fiver',4),(13,'2016-07-19 18:36:58','2016-07-19 18:36:58',0,0,'/login','this should b e1','oner',1),(33,'2016-07-21 15:17:36','2016-07-21 15:17:36',0,NULL,'/login','one','another',6),(36,'2016-07-21 18:27:34','2016-07-21 18:27:34',0,NULL,'/login',NULL,'deletable',5);
/*!40000 ALTER TABLE `urls` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `version` tinyint(4) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `area_id` int(10) unsigned DEFAULT NULL,
  `admin_rights` bit(1) DEFAULT NULL,
  `url_id` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_3g1j96g94xpk3lpxl2qbl985x` (`name`),
  KEY `FK_kt5b19y8nw95wrs32mtqo481k` (`area_id`),
  KEY `FK_nwr1r9o4qgq29phcebi6nslwo` (`url_id`),
  CONSTRAINT `FK_kt5b19y8nw95wrs32mtqo481k` FOREIGN KEY (`area_id`) REFERENCES `areas` (`id`),
  CONSTRAINT `FK_nwr1r9o4qgq29phcebi6nslwo` FOREIGN KEY (`url_id`) REFERENCES `urls` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'2016-07-15 15:26:32','2016-06-21 12:35:34',0,'Bimbo Star','bs',1,NULL,3),(2,'2016-07-15 15:36:33','2016-05-21 12:35:34',0,'Bimba La Tigresa','bbs',1,NULL,3),(3,'2016-07-15 17:27:15','2016-07-21 12:35:34',0,'Bruce Lee','hawaaa!!',3,NULL,8),(5,'2016-07-19 12:14:43','2016-07-19 12:14:43',1,'admin','password',1,'',NULL),(7,'2016-07-20 18:48:42','2016-07-21 12:38:29',1,'bob','barker',1,NULL,10),(8,'2016-07-20 18:49:19','2016-07-21 12:38:29',1,'bingo','barker',1,NULL,10),(9,'2016-07-20 18:58:15','2016-07-21 12:38:29',1,'daimen','poopie',1,NULL,13),(10,'2016-07-20 19:34:14','2016-07-21 12:38:30',1,'bbb','ccc',3,NULL,13),(11,'2016-07-20 20:20:10','2016-07-21 12:38:30',1,'ccc','ddd',3,NULL,13),(12,'2016-07-21 12:27:06','2016-04-21 12:38:30',1,'cookie monster','cookies!',1,NULL,3),(13,'2016-07-21 12:37:22','2016-07-21 12:38:30',1,'big bird','preschool',1,NULL,3),(15,'2016-07-21 12:44:50','2016-07-21 12:44:50',1,'the Count','12',1,NULL,3),(16,'2016-07-21 20:36:15','2016-07-21 20:36:15',1,'new user','I dont know my name',1,NULL,NULL),(17,'2016-07-21 20:36:38','2016-07-21 20:36:38',1,'bbbb','cccc',1,NULL,3);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-07-22 14:13:45
