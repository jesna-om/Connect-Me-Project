/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 5.7.9 : Database - connect_me
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`connect_me` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `connect_me`;

/*Table structure for table `complaints` */

DROP TABLE IF EXISTS `complaints`;

CREATE TABLE `complaints` (
  `complaint_id` int(100) NOT NULL AUTO_INCREMENT,
  `sender_id` int(100) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  `reply` varchar(200) DEFAULT NULL,
  `complaint` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`complaint_id`)
) ENGINE=MyISAM AUTO_INCREMENT=23 DEFAULT CHARSET=latin1;

/*Data for the table `complaints` */

insert  into `complaints`(`complaint_id`,`sender_id`,`date`,`reply`,`complaint`) values 
(1,1,'1/12/2023','okk','poor service'),
(2,2,'15/2/2024','sorry!!','late deliveries'),
(3,1,'2024-11-16','sorry','dfghk'),
(4,1,'2024-11-16','okk','hjasjk'),
(5,1,'2024-12-14','pending','bad'),
(6,2,'2024-12-14','pending','ewetserery'),
(7,2,'2024-12-14','pending','ehjkrwejrhi'),
(8,2,'2024-12-14','sorry for the inconvenience','app not working'),
(9,2,'2024-12-14','pending','app not working'),
(10,2,'2024-12-14','pending','app not working'),
(11,2,'2024-12-14','pending','sjdjg'),
(12,1,'2025-01-19','pending','y dont u reply??'),
(13,22,'2025-01-27','sorry','   so bad'),
(14,22,'2025-01-27','pending',''),
(15,22,'2025-01-27','pending','hogp'),
(16,22,'2025-01-27','pending','hhg'),
(17,1,'2025-02-02','okk','complaint'),
(18,1,'2025-02-02','pending','complaint'),
(19,1,'2025-02-02','pending','complaint'),
(20,1,'2025-02-02','pending','haii'),
(21,1,'2025-02-02','pending','haii'),
(22,1,'2025-02-02','pending','sadsa');

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `login_id` int(100) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `user_type` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`login_id`)
) ENGINE=MyISAM AUTO_INCREMENT=29 DEFAULT CHARSET=latin1;

/*Data for the table `login` */

insert  into `login`(`login_id`,`user_name`,`password`,`user_type`) values 
(1,'admin','admin','admin'),
(2,'user','user','user'),
(3,'shop','shop','shop'),
(6,'shop1','jasna','shop'),
(7,'shop2','ria','shop'),
(11,'workk12','1234','shop'),
(8,'worker1','worker1','reject'),
(9,'worker2','worker2','worker'),
(10,'worker3','worker3','reject'),
(12,'workk12','1234','shop'),
(13,'workk12','1234','shop'),
(14,'workk12','1234','worker'),
(15,'workk12','1234','worker'),
(16,'workk12','1234','worker'),
(17,'workk12','1234','worker'),
(18,'ria','ameer','user'),
(19,'ria','ameer','user'),
(20,'nnnn','123','user'),
(21,'nnnn','123','user'),
(22,'rono','jesu','user'),
(23,'riyeah','amamaaa','user'),
(24,'worker2','worker2','shop'),
(25,'worker2','worker2','shop'),
(26,'arsha','arsha','user'),
(27,'arshuarsh','bromista123','user'),
(28,'achuarsh','achuarsh','user');

/*Table structure for table `order_details` */

DROP TABLE IF EXISTS `order_details`;

CREATE TABLE `order_details` (
  `od_id` int(100) NOT NULL AUTO_INCREMENT,
  `om_id` int(100) DEFAULT NULL,
  `product_id` int(100) DEFAULT NULL,
  `quantity` varchar(100) DEFAULT NULL,
  `amount` varchar(100) DEFAULT NULL,
  `date_time` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`od_id`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

/*Data for the table `order_details` */

insert  into `order_details`(`od_id`,`om_id`,`product_id`,`quantity`,`amount`,`date_time`) values 
(1,1,7,'3','345','2024-12-28 10:34:42'),
(2,2,12,'2','599','2025-01-08 10:48:47'),
(3,3,10,'2','30','2025-01-14 10:12:54'),
(4,4,10,'2','30','2025-01-14 11:04:37'),
(5,5,10,'2','30','2025-01-14 11:35:20'),
(6,6,10,'2','30','2025-01-14 11:49:42'),
(7,7,10,'2','30','2025-01-14 14:14:28'),
(8,8,10,'2','30','2025-01-14 16:03:24');

/*Table structure for table `order_master` */

DROP TABLE IF EXISTS `order_master`;

CREATE TABLE `order_master` (
  `om_id` int(100) NOT NULL AUTO_INCREMENT,
  `user_id` int(100) DEFAULT NULL,
  `shop_id` int(100) DEFAULT NULL,
  `total` varchar(100) DEFAULT NULL,
  `date_time` varchar(100) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`om_id`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

/*Data for the table `order_master` */

insert  into `order_master`(`om_id`,`user_id`,`shop_id`,`total`,`date_time`,`status`) values 
(1,6,4,'1035','2024-12-28 10:34:42','paid'),
(2,6,1,'1198','2025-01-08 10:48:47','paid'),
(3,6,1,'60','2025-01-14 10:12:54','paid'),
(4,6,1,'60','2025-01-14 11:04:37','paid'),
(5,6,1,'60','2025-01-14 11:35:20','paid'),
(6,6,1,'60','2025-01-14 11:49:42','paid'),
(7,6,1,'60','2025-01-14 14:14:28','paid'),
(8,6,1,'60','2025-01-14 16:03:24','paid');

/*Table structure for table `order_payment` */

DROP TABLE IF EXISTS `order_payment`;

CREATE TABLE `order_payment` (
  `payment_id` int(100) NOT NULL AUTO_INCREMENT,
  `om_id` int(80) DEFAULT NULL,
  `amount` varchar(100) DEFAULT NULL,
  `date_time` varchar(150) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`payment_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `order_payment` */

insert  into `order_payment`(`payment_id`,`om_id`,`amount`,`date_time`,`status`) values 
(1,8,'60','2025-01-14','paid'),
(2,1,'1035','2025-01-12','paid');

/*Table structure for table `product` */

DROP TABLE IF EXISTS `product`;

CREATE TABLE `product` (
  `product_id` int(100) NOT NULL AUTO_INCREMENT,
  `shop_id` int(100) DEFAULT NULL,
  `product_name` varchar(200) DEFAULT NULL,
  `price` varchar(100) DEFAULT NULL,
  `quantity` varchar(100) DEFAULT NULL,
  `image` varchar(100) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`product_id`)
) ENGINE=MyISAM AUTO_INCREMENT=32 DEFAULT CHARSET=latin1;

/*Data for the table `product` */

insert  into `product`(`product_id`,`shop_id`,`product_name`,`price`,`quantity`,`image`,`description`) values 
(13,1,'achar podikal','200','2kg','jhgr','tastemaker'),
(10,1,'pears','30','10','png','soap'),
(14,1,'aloevera gel','22','ef','efdsf','efwfs'),
(7,4,'rtfgy','345','12','rtgy','tgyhu'),
(31,1,'rtfgy','400','3','C:UsersjesnaPicturesScreenshotsScreenshot (1).png','er'),
(29,1,'hp laptop','50000','2','ijohuiy','laptop');

/*Table structure for table `request` */

DROP TABLE IF EXISTS `request`;

CREATE TABLE `request` (
  `request_id` int(100) NOT NULL AUTO_INCREMENT,
  `service_id` int(100) DEFAULT NULL,
  `user_id` int(100) DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL,
  `amount` varchar(100) DEFAULT NULL,
  `status` varchar(150) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`request_id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `request` */

insert  into `request`(`request_id`,`service_id`,`user_id`,`title`,`amount`,`status`,`date`) values 
(2,2,6,'vhk','3333','paid','10/1/2025'),
(3,1,6,' house maid ','123','accepted',' 12/3/2025'),
(4,1,6,'c bkk','123','pending','12/5/25'),
(5,1,6,'  house hold','123','paid',' 12/5/25');

/*Table structure for table `request_payment` */

DROP TABLE IF EXISTS `request_payment`;

CREATE TABLE `request_payment` (
  `request_payment_id` int(11) NOT NULL AUTO_INCREMENT,
  `request_id` int(11) DEFAULT NULL,
  `amount` varchar(20) DEFAULT NULL,
  `date_time` varchar(20) DEFAULT NULL,
  `status` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`request_payment_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `request_payment` */

insert  into `request_payment`(`request_payment_id`,`request_id`,`amount`,`date_time`,`status`) values 
(1,2,'3333','2025-01-14 14:13:11','paid'),
(2,5,'123','2025-01-27 13:39:31','paid');

/*Table structure for table `review_product` */

DROP TABLE IF EXISTS `review_product`;

CREATE TABLE `review_product` (
  `review_id` int(100) NOT NULL AUTO_INCREMENT,
  `product_id` int(100) DEFAULT NULL,
  `review` varchar(5000) DEFAULT NULL,
  `rating` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`review_id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `review_product` */

insert  into `review_product`(`review_id`,`product_id`,`review`,`rating`) values 
(1,1,'not bad','5'),
(2,12,'jvkg','1.0'),
(3,10,'good  ','2.5'),
(4,10,'jesna achaar products are very bad am so disappointed ','0.5');

/*Table structure for table `review_worker` */

DROP TABLE IF EXISTS `review_worker`;

CREATE TABLE `review_worker` (
  `review_id` int(11) NOT NULL AUTO_INCREMENT,
  `worker_id` int(11) DEFAULT NULL,
  `review` varchar(100) DEFAULT NULL,
  `rating` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`review_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `review_worker` */

insert  into `review_worker`(`review_id`,`worker_id`,`review`,`rating`) values 
(1,1,'good service','5');

/*Table structure for table `service` */

DROP TABLE IF EXISTS `service`;

CREATE TABLE `service` (
  `service_id` int(11) NOT NULL AUTO_INCREMENT,
  `worker_id` int(11) DEFAULT NULL,
  `service_name` varchar(20) DEFAULT NULL,
  `description` varchar(50) DEFAULT NULL,
  `amount` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`service_id`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

/*Data for the table `service` */

insert  into `service`(`service_id`,`worker_id`,`service_name`,`description`,`amount`) values 
(1,2,'hjn1','huhuhjn11','123'),
(2,2,'rihu','naju','1000'),
(7,2,'jez','dfsr','500'),
(4,4,'naj','dfb','3333');

/*Table structure for table `shop` */

DROP TABLE IF EXISTS `shop`;

CREATE TABLE `shop` (
  `shop_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `shop_name` varchar(30) DEFAULT NULL,
  `place` varchar(30) DEFAULT NULL,
  `pin` varchar(10) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `phone` varchar(10) DEFAULT NULL,
  `latitude` varchar(20) DEFAULT NULL,
  `longitude` varchar(20) DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`shop_id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `shop` */

insert  into `shop`(`shop_id`,`login_id`,`shop_name`,`place`,`pin`,`email`,`phone`,`latitude`,`longitude`,`type`) values 
(1,3,'jesnas achaar','kochannur','1234','jes@123@gmail.com','1234567890','654.654','5432.543','shop'),
(2,7,'ria cycles','porkulam','123','ria@gmail.com','1342534655','23456','2134567','shop'),
(3,13,'wewas','perumbilavu','12134','dsdaas','2134567','213456','234567','shop'),
(4,6,'zudio','ckm','2346','123@234.com','9876543','65432','87654','shop'),
(5,24,'','','','','','','','shop'),
(6,25,'','','','','','','','shop');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `first_name` varchar(100) DEFAULT NULL,
  `last_name` varchar(100) DEFAULT NULL,
  `place` varchar(300) DEFAULT NULL,
  `email` varchar(500) DEFAULT NULL,
  `phone` varchar(100) DEFAULT NULL,
  `latitude` varchar(2000) DEFAULT NULL,
  `longitude` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

/*Data for the table `user` */

insert  into `user`(`user_id`,`login_id`,`first_name`,`last_name`,`place`,`email`,`phone`,`latitude`,`longitude`) values 
(1,2,'ghnd','ewrew','erwwqer','ewtqert','eretrew','tter','rtqet'),
(2,18,'Rihana','Usman','kuhhv','rihanariya4@gmail.com','644523','ria','ameer'),
(3,19,'Rihana','Usman','kuhhv','rihanariya4@gmail.com','644523','ria','ameer'),
(4,20,'Rihana','Usman','hsnsn','jjsjs','nnsnjs','nnnn','123'),
(5,21,'Rihana','Usman','hsnsn','jjsjs','nnsnjs','nnnn','123'),
(6,22,'najj','man','ghjgs','nahariya4@gmail.com','483839658','liyu','harismyheart'),
(7,23,'Rihana','Usman','hy','rihanariya84ew@gmail.com','5434738382','riyeah','amamaaa'),
(8,3,'najma','manaf','dshhd','ewrr@gmail.com','2435t4y5','324576','432546');

/*Table structure for table `worker` */

DROP TABLE IF EXISTS `worker`;

CREATE TABLE `worker` (
  `worker_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(100) DEFAULT NULL,
  `place` varchar(100) DEFAULT NULL,
  `phone` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `work` varchar(100) DEFAULT NULL,
  `latitude` varchar(100) DEFAULT NULL,
  `longitude` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`worker_id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `worker` */

insert  into `worker`(`worker_id`,`login_id`,`first_name`,`last_name`,`place`,`phone`,`email`,`work`,`latitude`,`longitude`) values 
(1,8,'najma','manaf','pavaratty','1234567890','najii@gmail.com','event management','12345678','123456'),
(2,9,'shahma','naru','ladakh mukk','2345678976','naruu@gmail.com','beautician','2134567','8765434'),
(3,10,'riya','mehrin','ckm','2134567777','mehrin@gmail.com','dance tutor','7675453','7895454'),
(4,16,'wrker ','rihu','perumbilavu','09947521233','sdasd@gmail.com','home nurse','3456789','3435678'),
(5,17,'wrker ','rihu','perumbilavu','09947521233','sdasd@gmail.com','home nurse','3456789','3435678');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
