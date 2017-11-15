-- MySQL dump 10.13  Distrib 5.7.20, for Linux (x86_64)
--
-- Host: localhost    Database: OrganizatePAE
-- ------------------------------------------------------
-- Server version	5.7.20-0ubuntu0.16.04.1

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
-- Table structure for table `Semester`
--

DROP TABLE IF EXISTS `Semester`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Semester` (
  `IDSemester` int(11) NOT NULL AUTO_INCREMENT,
  `Start_Date` date DEFAULT NULL,
  `End_Date` date DEFAULT NULL,
  `Description` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`IDSemester`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Semester`
--

LOCK TABLES `Semester` WRITE;
/*!40000 ALTER TABLE `Semester` DISABLE KEYS */;
/*!40000 ALTER TABLE `Semester` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Subject`
--

DROP TABLE IF EXISTS `Subject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Subject` (
  `IDSubject` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(45) DEFAULT NULL,
  `ProfessorName` varchar(45) DEFAULT NULL,
  `IDSemester` int(11) NOT NULL,
  `Color` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`IDSubject`),
  KEY `fk_Subject_Semester1_idx` (`IDSemester`),
  CONSTRAINT `fk_Subject_Semester1` FOREIGN KEY (`IDSemester`) REFERENCES `Semester` (`IDSemester`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Subject`
--

LOCK TABLES `Subject` WRITE;
/*!40000 ALTER TABLE `Subject` DISABLE KEYS */;
/*!40000 ALTER TABLE `Subject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Subject_Time`
--

DROP TABLE IF EXISTS `Subject_Time`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Subject_Time` (
  `IDSubject_Time` int(11) NOT NULL AUTO_INCREMENT,
  `Day` varchar(50) DEFAULT NULL,
  `Time` datetime DEFAULT NULL,
  `IDSubject` int(11) NOT NULL,
  `Duration` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`IDSubject_Time`),
  KEY `fk_Subject_Time_Subject1_idx` (`IDSubject`),
  CONSTRAINT `fk_Subject_Time_Subject1` FOREIGN KEY (`IDSubject`) REFERENCES `Subject` (`IDSubject`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Subject_Time`
--

LOCK TABLES `Subject_Time` WRITE;
/*!40000 ALTER TABLE `Subject_Time` DISABLE KEYS */;
/*!40000 ALTER TABLE `Subject_Time` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Task`
--

DROP TABLE IF EXISTS `Task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Task` (
  `IDTask` int(11) NOT NULL AUTO_INCREMENT,
  `Description` varchar(200) DEFAULT NULL,
  `Title` varchar(60) NOT NULL,
  `Type` varchar(50) DEFAULT NULL,
  `IsDone` tinyint(4) DEFAULT NULL,
  `IDSubject` int(11) NOT NULL,
  `DueDate` datetime DEFAULT NULL,
  PRIMARY KEY (`IDTask`),
  KEY `fk_Task_Subject1_idx` (`IDSubject`),
  CONSTRAINT `fk_Task_Subject1` FOREIGN KEY (`IDSubject`) REFERENCES `Subject` (`IDSubject`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Task`
--

LOCK TABLES `Task` WRITE;
/*!40000 ALTER TABLE `Task` DISABLE KEYS */;
/*!40000 ALTER TABLE `Task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Theme`
--

DROP TABLE IF EXISTS `Theme`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Theme` (
  `IDTheme` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(45) DEFAULT NULL,
  `color-primary` varchar(30) DEFAULT NULL,
  `color-info` varchar(30) DEFAULT NULL,
  `color-default` varchar(30) DEFAULT NULL,
  `color-bg` varchar(30) DEFAULT NULL,
  `gray-base` varchar(30) DEFAULT NULL,
  `gray-dark` varchar(30) DEFAULT NULL,
  `gray-darker` varchar(30) DEFAULT NULL,
  `gray-light` varchar(30) DEFAULT NULL,
  `gray-lighter` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`IDTheme`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Theme`
--

LOCK TABLES `Theme` WRITE;
/*!40000 ALTER TABLE `Theme` DISABLE KEYS */;
INSERT INTO `Theme` VALUES (1,'Tranquility','#A9B7C0','#CCCBC6','#EFD9C1','#C7D8C6','#999','#555','#111','#BBB','#DDD');
/*!40000 ALTER TABLE `Theme` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `User` (
  `IDUser` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(45) DEFAULT NULL,
  `Mail` varchar(45) DEFAULT NULL,
  `SchoolName` varchar(45) DEFAULT NULL,
  `SelectedThemeID` int(11) NOT NULL,
  `Language` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`IDUser`),
  KEY `fk_User_Theme1_idx` (`SelectedThemeID`),
  CONSTRAINT `fk_User_Theme1` FOREIGN KEY (`SelectedThemeID`) REFERENCES `Theme` (`IDTheme`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES (1,'Generic User','generic_mail@fake.com','ITESO',1,'EN');
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-11-15 11:50:12
