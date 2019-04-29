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
INSERT INTO `WishlistService`.`Login` (`login_id`, `password`) VALUES ('suraj', '12345');

COMMIT;


-- -----------------------------------------------------
-- Data for table `WishlistService`.`Customer`
-- -----------------------------------------------------
START TRANSACTION;
USE `WishlistService`;
INSERT INTO `WishlistService`.`Customer` (`id`, `login_id`, `email_id`, `phone_no`, `dob`, `gender`, `name`) VALUES (1, 'deepika', '99alavaladeepika@gmail.com', '9611529722', '1997-10-25', 'F', 'Deepika Alavala');
INSERT INTO `WishlistService`.`Customer` (`id`, `login_id`, `email_id`, `phone_no`, `dob`, `gender`, `name`) VALUES (2, 'manisha', 'manisha.anu95@gmail.com', '7730069061', '1996-06-11', 'F', 'Manisha Sinha');
INSERT INTO `WishlistService`.`Customer` (`id`, `login_id`, `email_id`, `phone_no`, `dob`, `gender`, `name`) VALUES (3, 'vaishali', 'vaishali.walia99@gmail.com', '6301319864', '1996-03-09', 'F', 'Vaishali Walia');
INSERT INTO `WishlistService`.`Customer` (`id`, `login_id`, `email_id`, `phone_no`, `dob`, `gender`, `name`) VALUES (4, 'suraj', 'surajjumpy@gmail.com', '9849813768', '1992-03-18', 'M', 'Suraj Singh');

COMMIT;


-- -----------------------------------------------------
-- Data for table `WishlistService`.`Catalog`
-- -----------------------------------------------------
START TRANSACTION;
USE `WishlistService`;
INSERT INTO `WishlistService`.`Catalog` (`product_id`, `product_name`, `brand`, `description`, `price`, `quantity`, `pic_location`) VALUES (1, 'Women Party Wear', 'Biba', 'Nice dress for parties', 2000, 12, 'images/catalog/1.jpeg');
INSERT INTO `WishlistService`.`Catalog` (`product_id`, `product_name`, `brand`, `description`, `price`, `quantity`, `pic_location`) VALUES (2, 'Women Casual Wear', 'W', 'Cotton cloth', 800, 12, 'images/catalog/2.jpeg');
INSERT INTO `WishlistService`.`Catalog` (`product_id`, `product_name`, `brand`, `description`, `price`, `quantity`, `pic_location`) VALUES (3, 'Samsung UHD TV', 'Samsung', '42-inch TV', 20000, 12, 'images/catalog/3.jpeg');
INSERT INTO `WishlistService`.`Catalog` (`product_id`, `product_name`, `brand`, `description`, `price`, `quantity`, `pic_location`) VALUES (4, 'Men Jeans', 'Denim', 'Blue jeans', 1200, 12, 'images/catalog/4.jpeg');
INSERT INTO `WishlistService`.`Catalog` (`product_id`, `product_name`, `brand`, `description`, `price`, `quantity`, `pic_location`) VALUES (5, 'Women Jeans', 'Lee', 'Black Jeans', 1300, 12, 'images/catalog/5.jpeg');
INSERT INTO `WishlistService`.`Catalog` (`product_id`, `product_name`, `brand`, `description`, `price`, `quantity`, `pic_location`) VALUES (6, 'Men Formal Wear', 'Arrow', 'White formal shirt', 1400, 12, 'images/catalog/6.jpeg');
INSERT INTO `WishlistService`.`Catalog` (`product_id`, `product_name`, `brand`, `description`, `price`, `quantity`, `pic_location`) VALUES (7, 'Men Casual Wear', 'Indian Terrain', 'Red and Black Stripes', 1500, 12, 'images/catalog/7.jpeg');
INSERT INTO `WishlistService`.`Catalog` (`product_id`, `product_name`, `brand`, `description`, `price`, `quantity`, `pic_location`) VALUES (8, 'Sony LED TV', 'Sony', '36-inch Tv', 12000, 12, 'images/catalog/8.jpeg');
INSERT INTO `WishlistService`.`Catalog` (`product_id`, `product_name`, `brand`, `description`, `price`, `quantity`, `pic_location`) VALUES (9, 'Women Party Watch', 'Titan Raga', 'Gold finishing', 5000, 12, 'images/catalog/9.jpeg');
INSERT INTO `WishlistService`.`Catalog` (`product_id`, `product_name`, `brand`, `description`, `price`, `quantity`, `pic_location`) VALUES (10, 'Women Band Watch', 'Fastrack', 'Red and Black band', 3000, 12, 'images/catalog/10.jpeg');
INSERT INTO `WishlistService`.`Catalog` (`product_id`, `product_name`, `brand`, `description`, `price`, `quantity`, `pic_location`) VALUES (11, 'Men Band Watch', 'Fastrack', 'Black Band', 3500, 12, 'images/catalog/11.jpeg');
INSERT INTO `WishlistService`.`Catalog` (`product_id`, `product_name`, `brand`, `description`, `price`, `quantity`, `pic_location`) VALUES (12, 'Men Formal Watch', 'Skagen', 'Grey Strap', 8000, 12, 'images/catalog/12.jpeg');
INSERT INTO `WishlistService`.`Catalog` (`product_id`, `product_name`, `brand`, `description`, `price`, `quantity`, `pic_location`) VALUES (13, 'Women Walking Shoes', 'Adidas', 'Size 40 available', 5999, 12, 'images/catalog/13.jpeg');
INSERT INTO `WishlistService`.`Catalog` (`product_id`, `product_name`, `brand`, `description`, `price`, `quantity`, `pic_location`) VALUES (14, 'Women Casual Footwear', 'Catwalk', 'Gold color', 1200, 12, 'images/catalog/14.jpeg');
INSERT INTO `WishlistService`.`Catalog` (`product_id`, `product_name`, `brand`, `description`, `price`, `quantity`, `pic_location`) VALUES (15, 'Women Flipflops', 'Flipside', 'Yellow color', 300, 12, 'images/catalog/15.jpeg');
INSERT INTO `WishlistService`.`Catalog` (`product_id`, `product_name`, `brand`, `description`, `price`, `quantity`, `pic_location`) VALUES (16, 'Men flipflops', 'Puma', 'Black color', 700, 12, 'images/catalog/16.jpeg');
INSERT INTO `WishlistService`.`Catalog` (`product_id`, `product_name`, `brand`, `description`, `price`, `quantity`, `pic_location`) VALUES (17, 'Men Formal Shoes', 'Nike', 'Brown shoes', 3000, 12, 'images/catalog/17.jpeg');
INSERT INTO `WishlistService`.`Catalog` (`product_id`, `product_name`, `brand`, `description`, `price`, `quantity`, `pic_location`) VALUES (18, 'Men Casual Footwear', 'Clarks', 'Tan color available', 1200, 12, 'images/catalog/18.jpeg');
INSERT INTO `WishlistService`.`Catalog` (`product_id`, `product_name`, `brand`, `description`, `price`, `quantity`, `pic_location`) VALUES (19, 'Lakme Face cream', 'Lakme', 'For fairness', 300, 12, 'images/catalog/19.jpeg');
INSERT INTO `WishlistService`.`Catalog` (`product_id`, `product_name`, `brand`, `description`, `price`, `quantity`, `pic_location`) VALUES (20, 'Fair and Handsome', 'Emami', 'For men fairness', 120, 12, 'images/catalog/20.jpeg');
INSERT INTO `WishlistService`.`Catalog` (`product_id`, `product_name`, `brand`, `description`, `price`, `quantity`, `pic_location`) VALUES (21, 'Dinner set', 'Laopala', '12 pieces in box', 3000, 12, 'images/catalog/21.jpeg');
INSERT INTO `WishlistService`.`Catalog` (`product_id`, `product_name`, `brand`, `description`, `price`, `quantity`, `pic_location`) VALUES (22, 'Induction stove', 'Panasonic', 'Warranty for 2 years', 3000, 12, 'images/catalog/22.jpeg');
INSERT INTO `WishlistService`.`Catalog` (`product_id`, `product_name`, `brand`, `description`, `price`, `quantity`, `pic_location`) VALUES (23, 'Stove', 'Haffle', '5 slots available', 20000, 12, 'images/catalog/23.jpeg');
INSERT INTO `WishlistService`.`Catalog` (`product_id`, `product_name`, `brand`, `description`, `price`, `quantity`, `pic_location`) VALUES (24, 'Toaster', 'Prestige', 'Bread toaster', 4000, 12, 'images/catalog/24.jpeg');
INSERT INTO `WishlistService`.`Catalog` (`product_id`, `product_name`, `brand`, `description`, `price`, `quantity`, `pic_location`) VALUES (25, 'Stationary Kit', 'Apsara', 'All stationary available', 400, 12, 'images/catalog/25.jpeg');
INSERT INTO `WishlistService`.`Catalog` (`product_id`, `product_name`, `brand`, `description`, `price`, `quantity`, `pic_location`) VALUES (26, 'Alarm Clock', 'Casio', 'Snooze option available', 200, 12, 'images/catalog/26.jpeg');
INSERT INTO `WishlistService`.`Catalog` (`product_id`, `product_name`, `brand`, `description`, `price`, `quantity`, `pic_location`) VALUES (27, '4 set books', 'Classmate', 'Ruled books', 280, 12, 'images/catalog/27.jpeg');
INSERT INTO `WishlistService`.`Catalog` (`product_id`, `product_name`, `brand`, `description`, `price`, `quantity`, `pic_location`) VALUES (28, 'Pen stand', 'Chumbak', 'Fancy pen stand', 400, 12, 'images/catalog/28.jpeg');
INSERT INTO `WishlistService`.`Catalog` (`product_id`, `product_name`, `brand`, `description`, `price`, `quantity`, `pic_location`) VALUES (29, 'Cello Pens', 'Cello', '12 pens in the box', 120, 12, 'images/catalog/29.jpeg');
INSERT INTO `WishlistService`.`Catalog` (`product_id`, `product_name`, `brand`, `description`, `price`, `quantity`, `pic_location`) VALUES (30, '12 step ladder', 'Bathla', '12 feet height', 4500, 12, 'images/catalog/30.jpeg');
INSERT INTO `WishlistService`.`Catalog` (`product_id`, `product_name`, `brand`, `description`, `price`, `quantity`, `pic_location`) VALUES (31, 'Mini size cupboard', 'Godrej', 'Brown polishing', 1200, 12, 'images/catalog/31.jpeg');
INSERT INTO `WishlistService`.`Catalog` (`product_id`, `product_name`, `brand`, `description`, `price`, `quantity`, `pic_location`) VALUES (32, 'Laptop table', 'Godrej', 'Wood table', 800, 12, 'images/catalog/32.jpeg');
INSERT INTO `WishlistService`.`Catalog` (`product_id`, `product_name`, `brand`, `description`, `price`, `quantity`, `pic_location`) VALUES (33, 'Samsung A100', 'Samsung', '4GB RAM', 12000, 12, 'images/catalog/33.jpeg');
INSERT INTO `WishlistService`.`Catalog` (`product_id`, `product_name`, `brand`, `description`, `price`, `quantity`, `pic_location`) VALUES (34, 'Oneplus 6t', 'Oneplus', 'Notch available', 39999, 12, 'images/catalog/34.jpeg');
INSERT INTO `WishlistService`.`Catalog` (`product_id`, `product_name`, `brand`, `description`, `price`, `quantity`, `pic_location`) VALUES (35, 'iPhone XS', 'Apple', 'Long battery', 90000, 12, 'images/catalog/35.jpeg');
INSERT INTO `WishlistService`.`Catalog` (`product_id`, `product_name`, `brand`, `description`, `price`, `quantity`, `pic_location`) VALUES (36, 'Ideapad 510', 'Lenovo', '8GB RAM ', 50000, 12, 'images/catalog/36.jpeg');
INSERT INTO `WishlistService`.`Catalog` (`product_id`, `product_name`, `brand`, `description`, `price`, `quantity`, `pic_location`) VALUES (37, 'Inspiron 560', 'Dell', '1 TB Memory', 56000, 12, 'images/catalog/37.jpeg');
INSERT INTO `WishlistService`.`Catalog` (`product_id`, `product_name`, `brand`, `description`, `price`, `quantity`, `pic_location`) VALUES (38, 'Mac Book Air', 'Apple', '256GB RAM', 99999, 12, 'images/catalog/38.jpeg');
INSERT INTO `WishlistService`.`Catalog` (`product_id`, `product_name`, `brand`, `description`, `price`, `quantity`, `pic_location`) VALUES (39, 'Notebook 360', 'HP', 'Touch screen laptop', 23000, 12, 'images/catalog/39.jpeg');
INSERT INTO `WishlistService`.`Catalog` (`product_id`, `product_name`, `brand`, `description`, `price`, `quantity`, `pic_location`) VALUES (40, 'Dobby wallpaper', 'Abstract', '36 cm long', 300, 12, 'images/catalog/40.jpeg');
INSERT INTO `WishlistService`.`Catalog` (`product_id`, `product_name`, `brand`, `description`, `price`, `quantity`, `pic_location`) VALUES (41, 'Eifel tower wallpaper', 'Sathrangi', '46 inch height', 420, 12, 'images/catalog/41.jpeg');
INSERT INTO `WishlistService`.`Catalog` (`product_id`, `product_name`, `brand`, `description`, `price`, `quantity`, `pic_location`) VALUES (42, 'Paper Clips', 'Apex', '20 clips in the box', 130, 12, 'images/catalog/42.jpeg');

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
INSERT INTO `WishlistService`.`WishlistProduct` (`id`, `product_id`, `wishlist_id`, `quantity`, `remaining_qty`, `address`, `reason`) VALUES (1, 1, 1, 1, 1, 'IIIT Bangalore', 'Birthday');
INSERT INTO `WishlistService`.`WishlistProduct` (`id`, `product_id`, `wishlist_id`, `quantity`, `remaining_qty`, `address`, `reason`) VALUES (2, 2, 1, 2, 2, 'IIT Hyderabad', 'Birthday');

COMMIT;


-- -----------------------------------------------------
-- Data for table `WishlistService`.`WishlistFullfillers`
-- -----------------------------------------------------
START TRANSACTION;
USE `WishlistService`;
INSERT INTO `WishlistService`.`WishlistFullfillers` (`id`, `fullfiller_id`, `wishlist_id`) VALUES (1, 'deepika', 2);
INSERT INTO `WishlistService`.`WishlistFullfillers` (`id`, `fullfiller_id`, `wishlist_id`) VALUES (2, 'vaishali', 1);

COMMIT;

