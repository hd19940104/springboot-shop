/*
Navicat MySQL Data Transfer

Source Server         : hadoop114
Source Server Version : 50721
Source Host           : 192.168.1.114:3306
Source Database       : shop

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2019-05-07 22:00:14
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for payment_info
-- ----------------------------
DROP TABLE IF EXISTS `payment_info`;
CREATE TABLE `payment_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `typeid` bigint(20) NOT NULL COMMENT '支付类',
  `orderid` bigint(20) NOT NULL COMMENT '订单编号',
  `platformorderid` varchar(500) DEFAULT NULL COMMENT '第三方支付平台订单id',
  `price` bigint(20) NOT NULL COMMENT '商品价格 单位：分',
  `source` varchar(10) NOT NULL COMMENT '渠道来源',
  `state` varchar(2) NOT NULL COMMENT '状态 1待支付 1支付成功 2支付失败',
  `paymessage` varchar(500) DEFAULT NULL COMMENT '支付报文',
  `txnTime` datetime DEFAULT NULL COMMENT '订单发起时间',
  `created` datetime NOT NULL,
  `updated` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8 COMMENT='支付信息表';

-- ----------------------------
-- Records of payment_info
-- ----------------------------
INSERT INTO `payment_info` VALUES ('38', '37', '2019050712231', null, '12', '1', '0', null, null, '2019-05-07 16:50:47', '2019-05-07 16:50:47');
