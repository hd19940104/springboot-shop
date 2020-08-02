/*
Navicat MySQL Data Transfer

Source Server         : hadoop114
Source Server Version : 50721
Source Host           : 192.168.1.114:3306
Source Database       : shop

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2019-05-07 23:26:53
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for mb_user
-- ----------------------------
DROP TABLE IF EXISTS `mb_user`;
CREATE TABLE `mb_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(32) NOT NULL COMMENT '密码，加密存储',
  `phone` varchar(20) DEFAULT NULL COMMENT '注册手机号',
  `email` varchar(50) DEFAULT NULL COMMENT '注册邮箱',
  `created` datetime NOT NULL,
  `updated` datetime NOT NULL,
  `openid` varchar(100) DEFAULT NULL,
  `weixinopenid` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`) USING BTREE,
  UNIQUE KEY `phone` (`phone`) USING BTREE,
  UNIQUE KEY `email` (`email`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of mb_user
-- ----------------------------
INSERT INTO `mb_user` VALUES ('1', '1', '1', '1', '1', '2019-05-06 11:06:55', '2019-05-06 11:06:57', null, null);
INSERT INTO `mb_user` VALUES ('69', 'houdong', '3BDDA55FCF5C832103F15B6B9ACAB0AD', '10086', '1183486985@qq.com', '2019-05-05 14:40:05', '2019-05-05 14:40:05', null, null);
INSERT INTO `mb_user` VALUES ('70', 'admin', 'A33E04C6DD94020C3837E0476753F1EE', '10010', '952542827@qq.com', '2019-05-06 09:51:43', '2019-05-06 09:51:43', null, null);
INSERT INTO `mb_user` VALUES ('71', 'Hello dear ', '028DC7FAB165B317F22D2CF69BDB203C', '17862976975', '1801206221@qq.com', '2019-05-07 17:36:53', '2019-05-07 17:36:53', null, null);
INSERT INTO `mb_user` VALUES ('73', '??', '7224B2D3A05173A11CCAF5B1065E8575', '17724601642', '1655116142@qq.com', '2019-05-07 19:10:23', '2019-05-07 19:10:23', null, null);
