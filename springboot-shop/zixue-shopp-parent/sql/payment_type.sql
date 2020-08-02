/*
Navicat MySQL Data Transfer

Source Server         : hadoop114
Source Server Version : 50721
Source Host           : 192.168.1.114:3306
Source Database       : shop

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2019-05-07 22:00:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for payment_type
-- ----------------------------
DROP TABLE IF EXISTS `payment_type`;
CREATE TABLE `payment_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `typename` varchar(100) NOT NULL COMMENT '支付类型名称',
  `fronturl` varchar(500) NOT NULL COMMENT '同步回调url',
  `backurl` varchar(500) NOT NULL COMMENT '异步回调url',
  `merchantid` varchar(150) NOT NULL COMMENT '商户id',
  `created` datetime NOT NULL,
  `updated` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8 COMMENT='支付类型表';

-- ----------------------------
-- Records of payment_type
-- ----------------------------
INSERT INTO `payment_type` VALUES ('37', 'yinlianPay', ' http://lovedongdong.natapp1.cc/pay-web/pay/callback/syn', ' http://lovedongdong.natapp1.cc/pay-web/pay/callback/asyn', ' 777290058110048', '2019-05-06 21:20:42', '2019-05-06 21:20:45');
