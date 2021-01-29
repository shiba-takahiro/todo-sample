SET NAMES utf8mb4 ;

SET character_set_client = utf8mb4 ;
DROP TABLE IF EXISTS `todo_items`;
CREATE TABLE `todo_items` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(11) NOT NULL COMMENT 'ユーザーID',
  `item_name` varchar(100) DEFAULT NULL COMMENT '項目名',
  `registration_date` date DEFAULT NULL COMMENT '登録日',
  `expire_date` date DEFAULT NULL COMMENT '期限日',
  `finished_date` date DEFAULT NULL COMMENT '完了日',
  PRIMARY KEY (`id`),
  KEY `IX_todo_items_user_id` (`user_id`)
) DEFAULT CHARSET=utf8mb4 COMMENT='作業項目';

SET character_set_client = utf8mb4 ;
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user` varchar(50) NOT NULL COMMENT 'ログインユーザー名',
  `pass` varchar(255) NOT NULL COMMENT 'ログインパスワード',
  `family_name` varchar(50) NOT NULL COMMENT 'ユーザー姓',
  `first_name` varchar(50) NOT NULL COMMENT 'ユーザー名',
  PRIMARY KEY (`id`)
) DEFAULT CHARSET=utf8mb4 COMMENT='ユーザー';


LOCK TABLES `users` WRITE;
INSERT INTO `users` VALUES 
(1,'test1','370cb1b6247689dee17882c1c3ce893481e57d56544f3d45d25cfee5376acb4e','山田','太郎');
UNLOCK TABLES;
