-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: localhost    Database: loanoffersystem
-- ------------------------------------------------------
-- Server version	8.0.29

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
-- Table structure for table `brand`
--

DROP TABLE IF EXISTS `brand`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `brand` (
  `BrandId` int NOT NULL AUTO_INCREMENT,
  `BrandName` varchar(45) NOT NULL,
  PRIMARY KEY (`BrandId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brand`
--

LOCK TABLES `brand` WRITE;
/*!40000 ALTER TABLE `brand` DISABLE KEYS */;
/*!40000 ALTER TABLE `brand` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `CategoryId` int NOT NULL AUTO_INCREMENT,
  `CategoryName` varchar(45) NOT NULL,
  PRIMARY KEY (`CategoryId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `CustomerId` int NOT NULL AUTO_INCREMENT,
  `LoanBalance` double DEFAULT NULL,
  `UsedAmount` double DEFAULT NULL,
  `InstallmentPlan` int DEFAULT NULL,
  `UserId` int NOT NULL,
  PRIMARY KEY (`CustomerId`),
  KEY `userfk_idx` (`UserId`),
  CONSTRAINT `userfk` FOREIGN KEY (`UserId`) REFERENCES `user` (`UserId`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,15000,0,1,2),(2,10000,5000,2,3),(3,5000,10000,1,4),(4,2000,13000,2,5),(5,15000,0,1,7),(6,15000,0,1,8);
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `installmenthistory`
--

DROP TABLE IF EXISTS `installmenthistory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `installmenthistory` (
  `HistoryId` int NOT NULL AUTO_INCREMENT,
  `CustomerId` int NOT NULL,
  `InstallmentPlanId` int NOT NULL,
  `AmountPaid` double NOT NULL,
  PRIMARY KEY (`HistoryId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `installmenthistory`
--

LOCK TABLES `installmenthistory` WRITE;
/*!40000 ALTER TABLE `installmenthistory` DISABLE KEYS */;
/*!40000 ALTER TABLE `installmenthistory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `installmentplan`
--

DROP TABLE IF EXISTS `installmentplan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `installmentplan` (
  `InstallmentPlanId` int NOT NULL AUTO_INCREMENT,
  `PlanName` varchar(100) NOT NULL,
  PRIMARY KEY (`InstallmentPlanId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `installmentplan`
--

LOCK TABLES `installmentplan` WRITE;
/*!40000 ALTER TABLE `installmentplan` DISABLE KEYS */;
INSERT INTO `installmentplan` VALUES (1,'1000-5000-5000'),(2,'10000-5000-5000');
/*!40000 ALTER TABLE `installmentplan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order` (
  `OrderId` int NOT NULL AUTO_INCREMENT,
  `GrandTotal` double NOT NULL,
  PRIMARY KEY (`OrderId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orderdetail`
--

DROP TABLE IF EXISTS `orderdetail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orderdetail` (
  `OrderDetailId` int NOT NULL AUTO_INCREMENT,
  `OrderId` int NOT NULL,
  `ProductId` int NOT NULL,
  `Qty` double NOT NULL,
  `SubTotal` double NOT NULL,
  PRIMARY KEY (`OrderDetailId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orderdetail`
--

LOCK TABLES `orderdetail` WRITE;
/*!40000 ALTER TABLE `orderdetail` DISABLE KEYS */;
/*!40000 ALTER TABLE `orderdetail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `ProductId` int NOT NULL AUTO_INCREMENT,
  `ProductName` varchar(45) NOT NULL,
  `CategoryId` int NOT NULL,
  `BrandId` int NOT NULL,
  `SalePrice` double NOT NULL,
  PRIMARY KEY (`ProductId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stock`
--

DROP TABLE IF EXISTS `stock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stock` (
  `StockId` int NOT NULL AUTO_INCREMENT,
  `ProductId` int NOT NULL,
  `Qty` double NOT NULL,
  PRIMARY KEY (`StockId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stock`
--

LOCK TABLES `stock` WRITE;
/*!40000 ALTER TABLE `stock` DISABLE KEYS */;
/*!40000 ALTER TABLE `stock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `UserId` int NOT NULL AUTO_INCREMENT,
  `FirstName` varchar(50) NOT NULL,
  `LastName` varchar(50) NOT NULL,
  `DateOfBirth` varchar(20) NOT NULL,
  `UserType` int NOT NULL,
  `Username` varchar(30) NOT NULL,
  `Secretkey` varchar(100) NOT NULL,
  `CreatedOn` timestamp NULL DEFAULT NULL,
  `UpdatedOn` timestamp NULL DEFAULT NULL,
  `UserEmail` varchar(45) NOT NULL,
  `UserMobileNumber` varchar(10) NOT NULL,
  `NIC` varchar(20) NOT NULL,
  PRIMARY KEY (`UserId`),
  UNIQUE KEY `Username_UNIQUE` (`Username`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Kathirkamanathan','Premnath','05-03-1991',1,'kpremnath','12345','2023-02-11 11:15:38',NULL,'rexprem1991@gmail.com','0772863745','90167883V'),(2,'Sivanath','Kumara','05-03-1991',2,'p1991','12345','2023-02-11 11:16:39',NULL,'kumara@kum.com','0772863745','90167883V'),(3,'Kamal','Darazx','05-03-1992',2,'srtwe','dfsdfs',NULL,NULL,'abc@pk.com','0772863745','90167883V'),(4,'Samal','Andros','05-03-1993',2,'dsssd','dfdsfsdfsd',NULL,NULL,'abcd@pk.com','0772863745','90167883V'),(5,'Dameel','Muru','05-03-1994',2,'dsfsd','fdsfsdfsdsd',NULL,NULL,'abcde@pk.com','0772863745','90167883V'),(6,'TestFirst','TestLast','01-01-2010',1,'useradmin1','37427346','2023-02-12 05:27:30',NULL,'useradmin1@gmail.com','3423423','354334'),(7,'TestFirst','TestLast','01-01-2010',2,'useradmin2','37427346','2023-02-12 05:32:54',NULL,'useradmin2@gmail.com','3423423','354334'),(8,'TestFirst','TestLast','01-01-2010',2,'useradmin3','37427346','2023-02-12 06:08:36',NULL,'useradmin3@gmail.com','3423423','354334'),(9,'TestFirst','TestLast','01-01-2010',1,'useradmin4','5994471abb1112afcc18159f6cc74b4f511b9986da59b3caf5a9c173cacfc5','2023-02-12 06:45:24',NULL,'useradmin4@gmail.com','3423423','354334');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'loanoffersystem'
--
/*!50003 DROP PROCEDURE IF EXISTS `get_customer_detail` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_customer_detail`(IN pCustomerId INT)
BEGIN
	select
    concat(u.FirstName,' ',u.LastName) FullName,
    u.DateOfBirth,
    (CASE
        WHEN u.UserType = 1 then 'AdminUser'
        WHEN u.UserType = 2 then 'Customer'
        END) UserTypeStr,
    u.UserEmail,
    u.UserMobileNumber,
    cus.LoanBalance,
    cus.UsedAmount,
    plan.PlanName
    from user u
    inner join customer cus on u.UserId = cus.UserId
    inner join installmentplan plan on cus.InstallmentPlan = plan.InstallmentPlanId
    where cus.CustomerId = pCustomerId
;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `get_customer_detail_proc` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_customer_detail_proc`(IN pCustomerId INT)
BEGIN
	select
    concat(u.FirstName,' ',u.LastName) FullName,
    u.DateOfBirth,
    (CASE
        WHEN u.UserType = 1 then 'AdminUser'
        WHEN u.UserType = 2 then 'Customer'
        END) UserTypeStr,
    u.UserEmail,
    u.UserMobileNumber,
    cus.LoanBalance,
    cus.UsedAmount,
    plan.PlanName
    from user u
    inner join customer cus on u.UserId = cus.UserId
    inner join installmentplan plan on cus.InstallmentPlan = plan.InstallmentPlanId
    where cus.CustomerId = pCustomerId
;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `get_customer_list` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_customer_list`()
BEGIN
	select
    concat(u.FirstName,' ',u.LastName) FullName,
    u.DateOfBirth,
    (CASE
        WHEN u.UserType = 1 then 'AdminUser'
        WHEN u.UserType = 2 then 'Customer'
        END) UserTypeStr,
    u.UserEmail,
    u.UserMobileNumber,
    cus.LoanBalance,
    cus.UsedAmount,
    plan.PlanName
    from user u
    inner join customer cus on u.UserId = cus.UserId
    inner join installmentplan plan on cus.InstallmentPlan = plan.InstallmentPlanId
;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `get_customer_list_proc` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_customer_list_proc`()
BEGIN
	select
    concat(u.FirstName,' ',u.LastName) FullName,
    u.DateOfBirth,
    (CASE
        WHEN u.UserType = 1 then 'AdminUser'
        WHEN u.UserType = 2 then 'Customer'
        END) UserTypeStr,
    u.UserEmail,
    u.UserMobileNumber,
    cus.LoanBalance,
    cus.UsedAmount,
    plan.PlanName
    from user u
    inner join customer cus on u.UserId = cus.UserId
    inner join installmentplan plan on cus.InstallmentPlan = plan.InstallmentPlanId
;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insert_update_admin_customer` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_update_admin_customer`(IN puserid int, IN pfirstname varchar(50), IN plastname varchar(50),
                                                IN pdateofbirth varchar(20), IN pusertype int, IN pusername varchar(30),
                                                IN psecretkey varchar(100), IN puseremail varchar(45),
                                                IN pusermobilenumber varchar(45), IN pnic varchar(45),
                                                IN pcustomerid int, IN ploanbalance DOUBLE, IN pusedamount DOUBLE,
                                                IN pinstallmentplan int, out rres tinyint(1), out rstatuscode int,
                                                out rmsg varchar(50))
BEGIN
    DECLARE luserid INTEGER default 0;
    DECLARE lcount INTEGER default 0;
    SET rres := true;
    SET rstatuscode := 3000;#success status code
    SET rmsg := 'Success';
    IF puseremail IS NULL
        OR
       puseremail = '' then
        SET rres := false;SET rstatuscode := 3001;#invalid information
        SET rmsg := 'Incorrect Email Address';
    ELSE
        SELECT Count(*)
        INTO lcount
        FROM USER
        WHERE useremail = puseremail;
        IF lcount > 0 then
            SET rres := false;SET rstatuscode := 3002;#already registered
            SET rmsg := 'User Already Registered...!';
        ELSE
            IF puserid = 0 then
                INSERT INTO USER
                (firstname,
                 lastname,
                 dateofbirth,
                 usertype,
                 username,
                 secretkey,
                 useremail,
                 usermobilenumber,
                 nic,
                 createdon)
                VALUES (pfirstname,
                        plastname,
                        pdateofbirth,
                        pusertype,
                        pusername,
                        psecretkey,
                        puseremail,
                        pusermobilenumber,
                        pnic,
                        now());
                SELECT Last_insert_id()
                INTO luserid;
                IF pusertype = 2 then
                    INSERT INTO customer
                    (loanbalance,
                     usedamount,
                     installmentplan,
                     userid)
                    VALUES (ploanbalance,
                            pusedamount,
                            pinstallmentplan,
                            luserid);
                END IF;
            ELSE
                UPDATE USER
                SET firstname   = pfirstname,
                    lastname    = plastname,
                    dateofbirth = pdateofbirth,
                    updatedon   = now()
                WHERE userid = puserid;
            END IF;
        END IF;
    END IF;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `user_login` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `user_login`(IN pusername varchar(50),IN ppasscode varchar(100),
OUT rfirstname varchar(50), OUT rlastname varchar(50), OUT rusertype int,  OUT ruserid int,out rres tinyint(1), out rstatuscode int,
                                                out rmsg varchar(50))
BEGIN
	declare lCount integer default 0;
	SET rres := true;
    SET rstatuscode := 3000;#success status code
    SET rmsg := 'Success';
    
    select count(*)  into lCount from user where username = pusername and secretkey = ppasscode;
    if lCount > 0 then
		select firstname,lastname,usertype,userid into
        rfirstname,rlastname,rusertype,ruserid
        from user where username = pusername and secretkey = ppasscode;
        else
        SET rres := false;
    SET rstatuscode := 3003;
    SET rmsg := 'Invalid Username or Password...!';
    end if;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-02-12 12:19:51
