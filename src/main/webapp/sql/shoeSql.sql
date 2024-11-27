-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.4.24-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             12.1.0.6537
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for mywebsite

CREATE DATABASE IF NOT EXISTS `shoeSql` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;
USE `shoeSql`;

-- Create the database if it doesn't already exist
CREATE DATABASE IF NOT EXISTS `shoeSql` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;
USE `shoeSql`;


CREATE TABLE IF NOT EXISTS `Users` (
    `gmail` VARCHAR(255) PRIMARY KEY COLLATE utf8mb4_unicode_ci NOT NULL, -- Email là khóa chính
    `role` VARCHAR(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'user', -- Vai trò (mặc định là 'user')
    `password` VARCHAR(255) COLLATE utf8mb4_unicode_ci NOT NULL, -- Mật khẩu đã mã hóa
    `remember_me_token` VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL, -- Token cho tính năng Remember Me
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Ngày giờ tạo tài khoản
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP -- Ngày giờ cập nhật tài khoản
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE IF NOT EXISTS `ProductCategory` (
    idProductCategory INTEGER NOT NULL AUTO_INCREMENT,
    nameCategory VARCHAR(255) NOT NULL,
    PRIMARY KEY (idProductCategory)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `Products` (
    idproduct INTEGER NOT NULL AUTO_INCREMENT,
    quantity INTEGER NOT NULL,
    name VARCHAR(255) NOT NULL,
    image VARCHAR(255) NOT NULL,
    price FLOAT NOT NULL,
    idProductCategory INTEGER NOT NULL,
    PRIMARY KEY (idproduct),
    FOREIGN KEY (idProductCategory) REFERENCES ProductCategory(idProductCategory)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `Product_Stock` (
    stock_id VARCHAR(255) PRIMARY KEY,
    idproduct INTEGER NOT NULL,
    color_id INTEGER NOT NULL,
    stock_quantity INTEGER NOT NULL,
    size_id INTEGER NOT NULL,
    FOREIGN KEY (idproduct) REFERENCES Products(idproduct),
    FOREIGN KEY (color_id) REFERENCES Colors(color_id),
    FOREIGN KEY (size_id) REFERENCES Sizes(size_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `Colors` (
    color_id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    color_name VARCHAR(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `Sizes` (
    size_id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    size_name VARCHAR(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `Color_Images` (
    image_id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    image_data BLOB NOT NULL,
    color_id INTEGER NOT NULL,
    FOREIGN KEY (color_id) REFERENCES Colors(color_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `Orders` (
    idOrder INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    orderDate DATE NOT NULL,
    gmail VARCHAR(255) NOT NULL,
    address_shipping VARCHAR(255) NOT NULL,
    totalPrice FLOAT NOT NULL,
    idShipping FLOAT NOT NULL,
    FOREIGN KEY (gmail) REFERENCES Users(gmail),
    FOREIGN KEY (idShipping) REFERENCES Shipping(idShipping)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `Shipping` (
    idShipping FLOAT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    shippingfee FLOAT NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `ShoppingCartItem` (
    idShoppingcartitem INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    quantity INTEGER NOT NULL,
    idproduct INTEGER NOT NULL,
    idOrder INTEGER NOT NULL,
    FOREIGN KEY (idproduct) REFERENCES Products(idproduct),
    FOREIGN KEY (idOrder) REFERENCES Orders(idOrder)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
