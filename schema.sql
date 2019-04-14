-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema WishlistService
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema WishlistService
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `WishlistService` DEFAULT CHARACTER SET utf8 ;
USE `WishlistService` ;

-- -----------------------------------------------------
-- Table `WishlistService`.`Login`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `WishlistService`.`Login` (
  `login_id` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`login_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `WishlistService`.`Customer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `WishlistService`.`Customer` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `login_id` VARCHAR(45) NOT NULL,
  `email_id` VARCHAR(80) NOT NULL,
  `phone_no` VARCHAR(10) NOT NULL,
  `dob` DATE NOT NULL,
  `gender` ENUM('F', 'M', 'others') NOT NULL,
  `name` VARCHAR(90) NULL,
  UNIQUE INDEX `email_id_UNIQUE` (`email_id` ASC),
  UNIQUE INDEX `login_id_UNIQUE` (`login_id` ASC),
  UNIQUE INDEX `phone_no_UNIQUE` (`phone_no` ASC),
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_login_1`
    FOREIGN KEY (`login_id`)
    REFERENCES `WishlistService`.`Login` (`login_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `WishlistService`.`Catalog`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `WishlistService`.`Catalog` (
  `product_id` INT NOT NULL AUTO_INCREMENT,
  `product_name` VARCHAR(50) NOT NULL,
  `brand` VARCHAR(50) NOT NULL,
  `description` VARCHAR(100) NULL,
  `price` FLOAT NOT NULL DEFAULT 0,
  `quantity` INT NOT NULL DEFAULT -1,
  `pic_location` VARCHAR(75) NOT NULL,
  PRIMARY KEY (`product_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `WishlistService`.`Wishlist`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `WishlistService`.`Wishlist` (
  `wishlist_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(70) NOT NULL,
  `creator_id` VARCHAR(45) NOT NULL,
  `status` ENUM('FULLFILLED', 'ONGOING', 'INACTIVE') NOT NULL DEFAULT 'ONGOING',
  PRIMARY KEY (`wishlist_id`),
  INDEX `fk_creator_id_idx` (`creator_id` ASC),
  CONSTRAINT `fk_creator_id`
    FOREIGN KEY (`creator_id`)
    REFERENCES `WishlistService`.`Login` (`login_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `WishlistService`.`WishlistProduct`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `WishlistService`.`WishlistProduct` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `product_id` INT NOT NULL,
  `wishlist_id` INT NOT NULL,
  `quantity` INT NOT NULL,
  `remaining_qty` INT NOT NULL DEFAULT 0,
  `address` VARCHAR(100) NOT NULL,
  `reason` VARCHAR(100) NULL,
  INDEX `fk_prod_id_1_idx` (`product_id` ASC),
  INDEX `fk_Wishlistid_idx` (`wishlist_id` ASC),
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_prod_id_1`
    FOREIGN KEY (`product_id`)
    REFERENCES `WishlistService`.`Catalog` (`product_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Wishlistid`
    FOREIGN KEY (`wishlist_id`)
    REFERENCES `WishlistService`.`Wishlist` (`wishlist_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `WishlistService`.`WishlistFullfillers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `WishlistService`.`WishlistFullfillers` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `fullfiller_id` VARCHAR(45) NOT NULL,
  `wishlist_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Fullfillers_fullfiller_idx` (`fullfiller_id` ASC),
  INDEX `fk_Wishlist_Fullfillers_1_idx` (`wishlist_id` ASC),
  CONSTRAINT `fk_Fullfillers_fullfiller`
    FOREIGN KEY (`fullfiller_id`)
    REFERENCES `WishlistService`.`Login` (`login_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Wishlist_Fullfillers_1`
    FOREIGN KEY (`wishlist_id`)
    REFERENCES `WishlistService`.`Wishlist` (`wishlist_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `WishlistService`.`Orders`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `WishlistService`.`Orders` (
  `order_id` INT NOT NULL AUTO_INCREMENT,
  `wishlistfullfiller_id` INT NOT NULL,
  PRIMARY KEY (`order_id`),
  INDEX `fk_wishlist_fullfiller_id_idx` (`wishlistfullfiller_id` ASC),
  CONSTRAINT `fk_wishlist_fullfiller_id`
    FOREIGN KEY (`wishlistfullfiller_id`)
    REFERENCES `WishlistService`.`WishlistFullfillers` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `WishlistService`.`OrderProduct`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `WishlistService`.`OrderProduct` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `order_id` INT NOT NULL,
  `product_id` INT NOT NULL,
  `quantity` INT NOT NULL,
  `price` FLOAT NOT NULL DEFAULT 0,
  INDEX `fk_Order_with_productid_idx` (`product_id` ASC),
  PRIMARY KEY (`id`),
  INDEX `fk_OrderProduct_1_idx` (`order_id` ASC),
  CONSTRAINT `fk_Order_with_productid`
    FOREIGN KEY (`product_id`)
    REFERENCES `WishlistService`.`Catalog` (`product_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_OrderProduct_1`
    FOREIGN KEY (`order_id`)
    REFERENCES `WishlistService`.`Orders` (`order_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `WishlistService`.`Login`
-- -----------------------------------------------------
START TRANSACTION;
USE `WishlistService`;
INSERT INTO `WishlistService`.`Login` (`login_id`, `password`) VALUES ('deepika', '12345');
INSERT INTO `WishlistService`.`Login` (`login_id`, `password`) VALUES ('manisha', '12345');
INSERT INTO `WishlistService`.`Login` (`login_id`, `password`) VALUES ('vaishali', '12345');

COMMIT;


-- -----------------------------------------------------
-- Data for table `WishlistService`.`Customer`
-- -----------------------------------------------------
START TRANSACTION;
USE `WishlistService`;
INSERT INTO `WishlistService`.`Customer` (`id`, `login_id`, `email_id`, `phone_no`, `dob`, `gender`, `name`) VALUES (1, 'deepika', 'deepika@gmail.com', '9611529722', '1997-10-25', 'F', 'Deepika Alavala');
INSERT INTO `WishlistService`.`Customer` (`id`, `login_id`, `email_id`, `phone_no`, `dob`, `gender`, `name`) VALUES (2, 'manisha', 'manisha@gmail.com', '7730069061', '1996-06-11', 'F', 'Manisha Sinha');
INSERT INTO `WishlistService`.`Customer` (`id`, `login_id`, `email_id`, `phone_no`, `dob`, `gender`, `name`) VALUES (3, 'vaishali', 'vaishali@gmail.com', '6301319864', '1996-03-09', 'F', 'Vaishali Walia');

COMMIT;


-- -----------------------------------------------------
-- Data for table `WishlistService`.`Catalog`
-- -----------------------------------------------------
START TRANSACTION;
USE `WishlistService`;
INSERT INTO `WishlistService`.`Catalog` (`product_id`, `product_name`, `brand`, `description`, `price`, `quantity`, `pic_location`) VALUES (1, 'Book', 'Oxford', 'English Dictionary', 200, 12, 'images/catalog/1.jpeg');
INSERT INTO `WishlistService`.`Catalog` (`product_id`, `product_name`, `brand`, `description`, `price`, `quantity`, `pic_location`) VALUES (2, 'Pen', 'Classmate', 'Blue pen', 40, 20, 'images/catalog/2.jpeg');
INSERT INTO `WishlistService`.`Catalog` (`product_id`, `product_name`, `brand`, `description`, `price`, `quantity`, `pic_location`) VALUES (3, 'Book', 'Oxford', 'English Dictionary', 200, 12, 'images/catalog/1.jpeg');
INSERT INTO `WishlistService`.`Catalog` (`product_id`, `product_name`, `brand`, `description`, `price`, `quantity`, `pic_location`) VALUES (4, 'Pen', 'Classmate', 'Blue pen', 40, 20, 'images/catalog/2.jpeg');
INSERT INTO `WishlistService`.`Catalog` (`product_id`, `product_name`, `brand`, `description`, `price`, `quantity`, `pic_location`) VALUES (5, 'Book', 'Oxford', 'English Dictionary', 200, 12, 'images/catalog/1.jpeg');
INSERT INTO `WishlistService`.`Catalog` (`product_id`, `product_name`, `brand`, `description`, `price`, `quantity`, `pic_location`) VALUES (6, 'Pen', 'Classmate', 'Blue pen', 40, 20, 'images/catalog/2.jpeg');
INSERT INTO `WishlistService`.`Catalog` (`product_id`, `product_name`, `brand`, `description`, `price`, `quantity`, `pic_location`) VALUES (7, 'Book', 'Oxford', 'English Dictionary', 200, 12, 'images/catalog/1.jpeg');
INSERT INTO `WishlistService`.`Catalog` (`product_id`, `product_name`, `brand`, `description`, `price`, `quantity`, `pic_location`) VALUES (8, 'Pen', 'Classmate', 'Blue pen', 40, 20, 'images/catalog/2.jpeg');

COMMIT;


-- -----------------------------------------------------
-- Data for table `WishlistService`.`Wishlist`
-- -----------------------------------------------------
START TRANSACTION;
USE `WishlistService`;
INSERT INTO `WishlistService`.`Wishlist` (`wishlist_id`, `name`, `creator_id`, `status`) VALUES (1, 'Birthday', 'deepika', 'ongoing');
INSERT INTO `WishlistService`.`Wishlist` (`wishlist_id`, `name`, `creator_id`, `status`) VALUES (2, 'Anniversary', 'vaishali', 'ongoing');

COMMIT;


-- -----------------------------------------------------
-- Data for table `WishlistService`.`WishlistProduct`
-- -----------------------------------------------------
START TRANSACTION;
USE `WishlistService`;
INSERT INTO `WishlistService`.`WishlistProduct` (`id`, `product_id`, `wishlist_id`, `quantity`, `remaining_qty`, `address`, `reason`) VALUES (1, 1, 1, 1, DEFAULT, 'IIIT Bangalore', 'Birthday');
INSERT INTO `WishlistService`.`WishlistProduct` (`id`, `product_id`, `wishlist_id`, `quantity`, `remaining_qty`, `address`, `reason`) VALUES (2, 2, 1, 2, DEFAULT, 'IIT Hyderabad', 'Birthday');

COMMIT;


-- -----------------------------------------------------
-- Data for table `WishlistService`.`WishlistFullfillers`
-- -----------------------------------------------------
START TRANSACTION;
USE `WishlistService`;
INSERT INTO `WishlistService`.`WishlistFullfillers` (`id`, `fullfiller_id`, `wishlist_id`) VALUES (1, 'deepika', 2);
INSERT INTO `WishlistService`.`WishlistFullfillers` (`id`, `fullfiller_id`, `wishlist_id`) VALUES (2, 'vaishali', 1);

COMMIT;
