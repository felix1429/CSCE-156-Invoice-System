use thennig;
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
-- Table structure for table `Persons`
--

DROP TABLE IF EXISTS `Persons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;

CREATE TABLE `Persons` (
  `PersonID` int(11) NOT NULL AUTO_INCREMENT,
  `PersonCode` varchar(20) NOT NULL,
  `PersAddressID` int(11) NOT NULL,
  `PersonLastName` varchar(30) NOT NULL,
  `PersonFirstName` varchar(30) NOT NULL,
  PRIMARY KEY (`PersonID`),
  FOREIGN KEY (`PersAddressID`) REFERENCES PersonAddress(`PersAddressID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Persons`
--

/*--LOCK TABLES `Persons` WRITE;*/;
/*!40000 ALTER TABLE `Persons` DISABLE KEYS */;
INSERT INTO `Persons` VALUES (1,'944c',1,'Castro','Starlin'),(2,'306a',2,'Sampson','Brock'),(3,'55bb',3,"O'Brien",'Miles'),(4,'2342',4,"O'Brien",'Miles'),(5,'aef1',5,'Gekko','Gordon'),(6,'321f',6,'Fox','Bud'),(7,'ma12',7,'Sveum','Dale'),(8,'321nd',8,'Hartnell','William'),(9,'nf32a',9,'Troughton','Patrick'),(10,'321na',10,'Pertwee','Jon'),(11,'231',11,'Baker','Tom'),(12,'6doc',12,'Hurndall','Richard'),(13,'321dr',13,'Baker','C.'),(14,'1svndr',14,'McCoy','Sylvester'),(15,'123lst',15,'McGann','Paul'),(16,'nwdoc1',16,'Eccleston','Chris'),(17,'2ndbestd',17,'Tennant','David'),(18,'wrddoc',18,'Smith','Matt'),(19,'bbchar',19,'Ehrmantraut','Kaylee'),(20,'doc05',20,'Davison','Peter');
/*!40000 ALTER TABLE `Persons` ENABLE KEYS */;
/*--UNLOCK TABLES;*/;

--
-- Table structure for table `Customers`
--

DROP TABLE IF EXISTS `Customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;

CREATE TABLE `Customers` (
  `CustomerID` int(11) NOT NULL AUTO_INCREMENT,
  `CustAddressID` int(11) NOT NULL,
  `CustomerCode` varchar(20) NOT NULL,
  `CustomerType` varchar(2) NOT NULL,
  `PersonCode` varchar(20) NOT NULL,
  `PersonID` int(11) NOT NULL,
  `CustomerName` varchar(40) NOT NULL,
  PRIMARY KEY (`CustomerID`),
  FOREIGN KEY (`PersonID`) REFERENCES Persons(`PersonID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  FOREIGN KEY (`CustAddressID`) REFERENCES CustomerAddress(`CustAddressID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Customers`
--

/*--LOCK TABLES `Customers` WRITE;*/;
/*!40000 ALTER TABLE `Customers` DISABLE KEYS */;
INSERT INTO `Customers` VALUES (1,1,'C001','G','231',11,'University of Nebraska-Lincoln'),(2,2,'C002','C','944c',1,'Stark Industries'),(3,3,'C003','C','306a',2,'Venture Industries'),(4,4,'C004','G','321f',6,'National Security Administration'),(5,5,'C005','C','bbchar',19,'Vandelay Industries'),(6,6,'C006','C','321dr',13,"Mr. Smith's #1 Supply Co. Inc.");
/*!40000 ALTER TABLE `Customers` ENABLE KEYS */;
/*--UNLOCK TABLES;*/;

--
-- Table structure for table `Email`
--

DROP TABLE IF EXISTS `Email`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;

CREATE TABLE `Email` (
  `EmailID` int(11) NOT NULL AUTO_INCREMENT,
  `PersonID` int(11) NOT NULL,
  `EmailAddress` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`EmailID`),
  FOREIGN KEY (`PersonID`) REFERENCES Persons(`PersonID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Email`
--

/*--LOCK TABLES `Email` WRITE;*/;
/*!40000 ALTER TABLE `Email` DISABLE KEYS */;
INSERT INTO `Email` VALUES (1,1,'scastro@cubs.com'),(2,1,'starlin_castro13@gmail.com'),(3,2,'brock_f_sampson@gmail.com'),(4,2,'bsampson@venture.com'),(5,3,'obrien@ds9.com'),(6,3,'obrien@enterprise.gov'),(7,4,NULL),(8,5,NULL),(9,6,'bfox@gmail.com'),(10,6,'csheen@crazy.net'),(11,7,'sveum@cubs.com'),(12,8,'whartnell@doctors.com'),(13,8,'dr@who.com'),(14,9,'ptroug@cse.unl.edu'),(15,9,'ptrou32@unl.edu'),(16,10,'jpet@whofan.com'),(17,11,'famousdoc@who.com'),(18,11,'tbaker@cse.unl.edu'),(19,11,'mostfamous@whovian.com'),(20,11,'thedoctor@bbc.com'),(21,12,'rhurndall@cse.unl.edu'),(22,12,'richard@unl.edu'),(23,13,'dr@baker.com'),(24,14,'slyguy@hotmail.com'),(25,14,'mccoy@whofan.com'),(26,15,'pmcgann@mlb.com'),(27,15,'foo@bar.com'),(28,15,'pmc@unl.edu'),(29,16,'newguy@whovian.com'),(30,17,'actor@shakespeare.com'),(31,17,'tdavid@unl.edu'),(32,18,'msmith@who.com'),(33,18,'thedoc@cse.unl.edu'),(34,19,NULL),(35,20,NULL);
/*!40000 ALTER TABLE `Email` ENABLE KEYS */;
/*--UNLOCK TABLES;*/;

--
-- Table structure for table `CustomerAddress`
--

DROP TABLE IF EXISTS `CustomerAddress`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;

CREATE TABLE `CustomerAddress` (
  `CustAddressID` int(11) NOT NULL AUTO_INCREMENT,
  `Street` varchar(30) NOT NULL,
  `City` varchar(30) NOT NULL,
  `CityState` varchar(30) NOT NULL,
  `ZipCode` varchar(20) NOT NULL,
  `Country` varchar(20) NOT NULL,
  PRIMARY KEY (`CustAddressID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CustomerAddress`
--

/*--LOCK TABLES `CustomerAddress` WRITE;*/;
/*!40000 ALTER TABLE `CustomerAddress` DISABLE KEYS */;
INSERT INTO `CustomerAddress` VALUES (1,'259 Avery Hall','Lincoln','NE','68588-0115','USA'),(2,'184 Marvel Way','New York','NY','10453','USA'),(3,'123 Venture Way','Culver City','CA','90230','USA'),(4,'9800 Savage Rd','Fort Meade','MD','20755','USA'),(5,'1060 West Addison','Chicago','IL','60601','USA'),(6,'456 West 7th St.','Omaha','NE','68500','USA');
/*!40000 ALTER TABLE `CustomerAddress` ENABLE KEYS */;
/*--UNLOCK TABLES;*/;

--
-- Table structure for table `PersonAddress`
--

DROP TABLE IF EXISTS `PersonAddress`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;

CREATE TABLE `PersonAddress` (
  `PersAddressID` int(11) NOT NULL AUTO_INCREMENT,
  `Street` varchar(30) NOT NULL,
  `City` varchar(30) NOT NULL,
  `CityState` varchar(30) NOT NULL,
  `ZipCode` varchar(20) NOT NULL,
  `Country` varchar(20) NOT NULL,
  PRIMARY KEY (`PersAddressID`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PersonAddress`
--

/*--LOCK TABLES `PersonAddress` WRITE;*/;
/*!40000 ALTER TABLE `PersonAddress` DISABLE KEYS */;
INSERT INTO `PersonAddress` VALUES (1,'1060 West Addison Street','Chicago','IL','60613','USA'),(2,'123 N 1st Street','Omaha','NE','68116','USA'),(3,'8753 West 3rd Ave.','Dallas','TX','75001','USA'),(4,'123 Friendly Street','Ottawa','ON','K1A 0G9','Canada'),(5,'1 Wall Street','New York','NY','10005-0012','USA'),(6,'321 Bronx Street','New York','NY','10004','USA'),(7,'1060 West Addison Street','Chicago','IL','60613','USA'),(8,'1060 West Addison Street','Chicago','IL','60613','USA'),(9,'1060 West Addison Street','Chicago','IL','60613','USA'),(10,'301 Front St W', 'Toronto','ON','M5V 2T6','Canada'),(11,'1 Blue Jays Way','Toronto','ON','M5V 1J1','Canada'),(12,'Campos El290','Mexico City','FD','','Mexico'),(13,'Avery Hall','Lincoln','NE','68503','USA'),(14,'126-01 Roosevelt Ave','Flushing','NY','11368','USA'),(15,'1 MetLife Stadium Dr','East Rutherford','NJ','07073','USA'),(16,'1 E 161st St','Bronx','NY','10451','USA'),(17,'700 E Grand Ave','Chicago','IL','60611','USA'),(18,'333 W 35th St', 'Chicago','IL','60616','USA'),(19,'800 West 7th Street','Albuquerque','NM','87105','USA'),(20,'123 Cabo San Lucas','Los Cabos','BCS','','Mexico');
/*!40000 ALTER TABLE `PersonAddress` ENABLE KEYS */;
/*--UNLOCK TABLES;*/;

--
-- Table structure for table `Products`
--

DROP TABLE IF EXISTS `Products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;

CREATE TABLE `Products` (
  `ProductID` int(11) NOT NULL AUTO_INCREMENT,
  `ProductCode` varchar(30) NOT NULL,
  `ProductType` varchar(5) NOT NULL,
  `ProductName` varchar(100) NOT NULL,
  `PersonID` int(30) DEFAULT NULL,
  `PricePerUnit` float(15) NOT NULL DEFAULT '0',
  `ProductFee` float(15) NOT NULL DEFAULT '0',
  `PricePerHour` float(15) NOT NULL DEFAULT '0',
  `PricePerYear` float(15) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ProductID`),
  FOREIGN KEY (`PersonID`)
  REFERENCES Persons (`PersonID`)
    ON DELETE CASCADE ON UPDATE CASCADE
)  ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE = utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Products`
--

/*--LOCK TABLES `Products` WRITE;*/;
/*!40000 ALTER TABLE `Products` DISABLE KEYS */;
INSERT INTO `Products` VALUES (1,'b29e','E','Cinco MIDI Organizer',NULL,2500.0,0.00,0.00,0.00),(2,'ff23','E','Cinco-Fone',NULL,124.99,0.00,0.00,0.00),(3,'fp12','E','Internette Discs',NULL,14.99,0.00,0.00,0.00),(4,'1239','E','Cinco Video Cube Playback System',NULL,5000.00,0.00,0.00,0.00),(5,'90fa','L','Cinco Long Distance Service',NULL,0.00,2000.00,0.00,12000.00),(6,'3289','C','Cinco-Fone Training',5,0.00,150.00,25.00,0.00),(7,'782g','C','Server System Setup',8,0.00,150.00,150.00,0.00),(8,'3294','L','Cloud SQL Hosting',NULL,0.00,0.00,0.00,35000.00),(9,'3295','L','Domain registration',NULL,0.00,350.00,0.00,1200.00);
/*!40000 ALTER TABLE `Products` ENABLE KEYS */;
/*--UNLOCK TABLES;*/;

--
-- Table structure for table `Invoices`
--

DROP TABLE IF EXISTS `Invoices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;

CREATE TABLE `Invoices` (
  `InvoiceID` int(11) NOT NULL AUTO_INCREMENT,
  `InvoiceCode` varchar(30) NOT NULL,
  `CustomerID` int(20) DEFAULT NULL,
  `PersonID` int(30) DEFAULT NULL,
  `ProductID` int(30) DEFAULT NULL,
  `NumberOfUnits` int(11) NOT NULL DEFAULT '0',
  `NumberOfHours` float(15) NOT NULL DEFAULT '0',
  `BeginDate` date DEFAULT NULL,
  `EndDate` date DEFAULT NULL,
  PRIMARY KEY (`InvoiceID`),
  FOREIGN KEY (`ProductID`)
  REFERENCES Products (`ProductID`)
    ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (`CustomerID`)
  REFERENCES Customers (`CustomerID`)
    ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (`PersonID`)
  REFERENCES Persons (`PersonID`)
    ON DELETE CASCADE ON UPDATE CASCADE
)  ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE = utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Invoices`
--

/*--LOCK TABLES `Invoices` WRITE;*/;
/*!40000 ALTER TABLE `Invoices` DISABLE KEYS */;
INSERT INTO `Invoices` VALUES (1,'INV001',2,16,1,2,0.00,NULL,NULL),(2,'INV001',2,16,5,0,0.00,'2013-01-01','2013-06-30'),(3,'INV002',1,18,2,25,0.00,NULL,NULL),(4,'INV002',1,18,6,0,10.00,NULL,NULL),(5,'INV002',1,18,4,1,0.00,NULL,NULL),(6,'INV003',5,17,2,10,0.00,NULL,NULL),(7,'INV003',5,17,5,0,0.00,'2013-01-15','2014-12-31'),(8,'INV004',2,17,7,0,10.00,NULL,NULL),(9,'INV004',2,17,8,0,0.00,'2014-01-01','2018-12-31'),(10,'INV004',2,17,9,0,0.00,'2014-01-01','2018-12-31');
/*!40000 ALTER TABLE `Invoices` ENABLE KEYS */;
/*--UNLOCK TABLES;*/;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
