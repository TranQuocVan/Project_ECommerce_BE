-- Drop and create the database
DROP DATABASE IF EXISTS `shoeSql`;
CREATE DATABASE IF NOT EXISTS `shoeSql` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;
USE `shoeSql`;

-- Membership Table
CREATE TABLE IF NOT EXISTS `Membership` (
    `membershipId` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `pointsRequired` INT NOT NULL,
    `endow` VARCHAR(255) NOT NULL COLLATE utf8mb4_unicode_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Users Table
CREATE TABLE IF NOT EXISTS `Users` (
    `userId` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `gmail` VARCHAR(255) UNIQUE COLLATE utf8mb4_unicode_ci,
    `point` FLOAT NOT NULL DEFAULT 0,
    `role` VARCHAR(50) NOT NULL COLLATE utf8mb4_unicode_ci DEFAULT 'user',
    `password` VARCHAR(255) COLLATE utf8mb4_unicode_ci,
    `created_at` DATE NOT NULL,
    `membershipId` INT,
    `remember_me_token` VARCHAR(255),
    `facebook_Id` LONG,
    FOREIGN KEY (`membershipId`) REFERENCES `Membership`(`membershipId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;



CREATE TABLE IF NOT EXISTS `PasswordResetTokens` (
    `userId` INT NOT NULL,
    `token` VARCHAR(255) NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`userId`),
    FOREIGN KEY (`userId`) REFERENCES `Users`(`userId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Product Categories Table
CREATE TABLE IF NOT EXISTS `ProductCategory` (
    `productCategoryId` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(255) NOT NULL COLLATE utf8mb4_unicode_ci,
    `description` VARCHAR(255) COLLATE utf8mb4_unicode_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Group Products Table
CREATE TABLE IF NOT EXISTS `GroupProducts` (
    `groupProductId` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(255) NOT NULL COLLATE utf8mb4_unicode_ci,
    `image` LONGBLOB,
    `description` VARCHAR(255)  COLLATE utf8mb4_unicode_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Products Table
CREATE TABLE IF NOT EXISTS `Products` (
    `productId` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(255) NOT NULL COLLATE utf8mb4_unicode_ci,
    `price` FLOAT NOT NULL,
    `discount` FLOAT DEFAULT 0,
    `productCategoryId` INT NOT NULL,
    `groupProductId` INT NOT NULL,
    FOREIGN KEY (`productCategoryId`) REFERENCES `ProductCategory`(`productCategoryId`),
    FOREIGN KEY (`groupProductId`) REFERENCES `GroupProducts`(`groupProductId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Colors Table
CREATE TABLE IF NOT EXISTS `Colors` (
    `colorId` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `hexCode` VARCHAR(7) NOT NULL COLLATE utf8mb4_unicode_ci,
    `name` VARCHAR(255) NOT NULL COLLATE utf8mb4_unicode_ci,
    `productId` INT NOT NULL,
    FOREIGN KEY (`productId`) REFERENCES `Products`(`productId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Sizes Table
CREATE TABLE IF NOT EXISTS `Sizes` (
    `sizeId` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `stock` INT NOT NULL,
    `size` VARCHAR(50) NOT NULL COLLATE utf8mb4_unicode_ci,
    `colorId` INT NOT NULL,
    FOREIGN KEY (`colorId`) REFERENCES `Colors`(`colorId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Images Table
CREATE TABLE IF NOT EXISTS `Images` (
    `imageId` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `colorId` INT NOT NULL,
    `image` LONGBLOB NOT NULL,
    FOREIGN KEY (`colorId`) REFERENCES `Colors`(`colorId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Reviews Table
CREATE TABLE IF NOT EXISTS `Reviews` (
    `reviewId` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `userId` INT NOT NULL,
    `description` VARCHAR(255) NOT NULL COLLATE utf8mb4_unicode_ci,
    `productId` INT NOT NULL,
    FOREIGN KEY (`userId`) REFERENCES `Users`(`userId`),
    FOREIGN KEY (`productId`) REFERENCES `Products`(`productId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Payments Table
CREATE TABLE IF NOT EXISTS `Payments` (
    `paymentId` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `methodPayment` VARCHAR(255) NOT NULL COLLATE utf8mb4_unicode_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Deliveries Table
CREATE TABLE IF NOT EXISTS `Deliveries` (
    `deliveryId` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `fee` FLOAT NOT NULL,
    `name` VARCHAR(255) NOT NULL COLLATE utf8mb4_unicode_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Orders Table
CREATE TABLE IF NOT EXISTS `Orders` (
    `orderId` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `orderDate` DATE NOT NULL,
    `deliveryAddress` VARCHAR(255) NOT NULL COLLATE utf8mb4_unicode_ci,
    `totalPrice` FLOAT NOT NULL,
    `userId` INT NOT NULL,
    `paymentId` INT NOT NULL,
    `deliveryId` INT NOT NULL,
    `statusPayment` INT NOT NULL DEFAULT 0,
    FOREIGN KEY (`userId`) REFERENCES `Users`(`userId`),
    FOREIGN KEY (`paymentId`) REFERENCES `Payments`(`paymentId`),
    FOREIGN KEY (`deliveryId`) REFERENCES `Deliveries`(`deliveryId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Shopping Cart Items Table
CREATE TABLE IF NOT EXISTS `ShoppingCartItems` (
    `shoppingCartItemId` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `quantity` INT NOT NULL,
    `sizeId` INT NOT NULL,
    `userId` INT NOT NULL,
    FOREIGN KEY (`sizeId`) REFERENCES `Sizes`(`sizeId`),
    FOREIGN KEY (`userId`) REFERENCES `Users`(`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Statuses Table
CREATE TABLE IF NOT EXISTS `Statuses` (
    `statusId` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(255) NOT NULL COLLATE utf8mb4_unicode_ci,
    `orderId` INT NOT NULL,
    `description` VARCHAR(255) NOT NULL COLLATE utf8mb4_unicode_ci,
    `timeline` DATE NOT NULL,
    FOREIGN KEY (`orderId`) REFERENCES `Orders`(`orderId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Shopping Cart Items Order Table
CREATE TABLE IF NOT EXISTS `password_reset_tokensShoppingCartItemsOrder` (
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `paymentId` INT,
    `quantity` INT,
    `orderId` INT,
    `sizeId` INT,
    FOREIGN KEY (`paymentId`) REFERENCES `Payments`(`paymentId`),
    FOREIGN KEY (`orderId`) REFERENCES `Orders`(`orderId`),
    FOREIGN KEY (`sizeId`) REFERENCES `Sizes`(`sizeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE logs (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT, -- ID của user thực hiện
    action VARCHAR(255), -- Hành động: 'UPDATE', 'DELETE', 'INSERT'
    table_name VARCHAR(255), -- Bảng bị tác động
    data_before TEXT, -- Dữ liệu trước khi thay đổi
    data_after TEXT, -- Dữ liệu sau khi thay đổi
    ip_address VARCHAR(50), -- Địa chỉ IP của user
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Sample Data for Product Categories
INSERT INTO `ProductCategory` (`name`, `description`) 
VALUES 
    ('Unclassified', 'unclassified'),
    ('Clothing', 'Apparel and accessories');

INSERT INTO `payments` (`methodPayment`) 
VALUES 
    ("VNPAY");
INSERT INTO `deliveries` (`fee`,`name`) 
VALUES 
    (10,"Hoả Tốc" );


-- Thêm vô table statuses nha ae
ALTER TABLE statuses
    ADD COLUMN timeline TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
