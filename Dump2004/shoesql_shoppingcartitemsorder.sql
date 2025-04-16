-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: shoesql
-- ------------------------------------------------------
-- Server version	8.0.40

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `shoppingcartitemsorder`
--

DROP TABLE IF EXISTS `shoppingcartitemsorder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shoppingcartitemsorder` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `quantity` int DEFAULT NULL,
  `orderId` int DEFAULT NULL,
  `sizeId` int DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `orderId` (`orderId`),
  KEY `sizeId` (`sizeId`),
  CONSTRAINT `shoppingcartitemsorder_ibfk_2` FOREIGN KEY (`orderId`) REFERENCES `orders` (`orderId`),
  CONSTRAINT `shoppingcartitemsorder_ibfk_3` FOREIGN KEY (`sizeId`) REFERENCES `sizes` (`sizeId`)
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shoppingcartitemsorder`
--

LOCK TABLES `shoppingcartitemsorder` WRITE;
/*!40000 ALTER TABLE `shoppingcartitemsorder` DISABLE KEYS */;
INSERT INTO `shoppingcartitemsorder` VALUES (74,1,121,116),(75,1,121,115),(76,1,122,114),(77,1,123,114),(82,1,124,118),(83,1,126,114),(84,1,127,114),(85,1,128,114),(86,1,129,114),(87,1,130,114),(88,1,132,123),(89,1,134,114),(90,1,135,115),(91,1,136,123),(92,1,137,118),(93,1,138,114),(94,1,139,114),(95,1,140,126),(96,1,141,130),(97,1,142,119),(98,1,143,119),(99,1,144,119),(100,1,145,118),(101,1,146,118),(102,1,147,126);
/*!40000 ALTER TABLE `shoppingcartitemsorder` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-14  8:21:04
