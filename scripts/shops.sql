/*
 Navicat Premium Data Transfer

 Source Server         : 本地数据库
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost
 Source Database       : shops

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : utf-8

 Date: 04/13/2021 14:52:23 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `goods`
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) DEFAULT NULL COMMENT '商品名',
  `code` varchar(40) DEFAULT NULL COMMENT '商品code',
  `stock` int(10) DEFAULT NULL COMMENT '商品库存',
  `state` int(2) DEFAULT NULL COMMENT '商品状态',
  `price` float DEFAULT NULL COMMENT '商品价格',
  `goodsid` varchar(20) DEFAULT NULL COMMENT '商品id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `goods`
-- ----------------------------
BEGIN;
INSERT INTO `goods` VALUES ('1', 'iPhone8', 'iPhone8', '10', '0', '3999', '123123'), ('2', 'HuaweiMate40', 'HuaweiMate40', '10', '0', '4999', '321321'), ('3', '秒杀测试商品', 'miaosha', '100', '0', '3999', '123123123');
COMMIT;

-- ----------------------------
--  Table structure for `goodsinfo`
-- ----------------------------
DROP TABLE IF EXISTS `goodsinfo`;
CREATE TABLE `goodsinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `goodsName` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '商品名称',
  `price` float DEFAULT NULL COMMENT '单价',
  `picture` varchar(36) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '图片 路径',
  `des` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '商品描述',
  `producter` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `editDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
--  Table structure for `ordergoods`
-- ----------------------------
DROP TABLE IF EXISTS `ordergoods`;
CREATE TABLE `ordergoods` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `orderId` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '订单号',
  `goodsId` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '商品ID',
  `goodsNumber` int(11) DEFAULT NULL COMMENT '商品数量',
  `goodsName` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `unit` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `price` float DEFAULT NULL,
  `saleCount` int(11) DEFAULT NULL,
  `picture` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `orderid_index` (`orderId`,`goodsId`,`goodsNumber`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11018 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
--  Records of `ordergoods`
-- ----------------------------
BEGIN;
INSERT INTO `ordergoods` VALUES ('50', '00001', '123123', '2', 'iPhone8', null, '3999', '2', null), ('51', '00001', '321321', '1', 'HuaweiMate40', null, '4999', '1', null), ('52', '00002', '123123', '1', 'iPhone8', null, '3999', '1', null), ('57', '00003', '123123', '2', 'iPhone8', null, '3999', '2', null), ('58', '00003', '321321', '1', 'HuaweiMate40', null, '4999', '1', null), ('159', '00004', '123123', '2', 'iPhone8', null, '3999', '2', null), ('160', '00004', '321321', '1', 'HuaweiMate40', null, '4999', '1', null);
COMMIT;

-- ----------------------------
--  Table structure for `orderinfo`
-- ----------------------------
DROP TABLE IF EXISTS `orderinfo`;
CREATE TABLE `orderinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `orderNo` varchar(40) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '订单号',
  `userId` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '该表单对应的用户的id，若是游客购买则没有用户id',
  `postage` float DEFAULT NULL COMMENT '邮费',
  `postMethod` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '邮递方式',
  `payMethod` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '支持方式',
  `orderDate` datetime DEFAULT NULL COMMENT '订单生成日期',
  `address` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '邮寄地址',
  `orderState` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '订单状态 -1新秒杀订单（需还原库存），0:新订单. 1:已支付订单. 2:被取消订单. 3:已发货订单',
  `amount` float DEFAULT NULL COMMENT '金额',
  `sendDate` datetime DEFAULT NULL,
  `editDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `orderNo_index` (`orderNo`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11596 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
--  Records of `orderinfo`
-- ----------------------------
BEGIN;
INSERT INTO `orderinfo` VALUES ('33', '00001', '20166007', '10', null, null, null, null, '0', '12997', null, '2021-04-12 14:25:20'), ('34', '00002', '20166006', '10', null, null, null, null, '0', '3999', null, '2021-04-12 15:37:27'), ('37', '00003', '20166007', '10', null, null, null, null, '0', '12997', null, '2021-04-12 14:14:17'), ('738', '00004', '20166007', '10', null, null, null, null, '0', '12997', null, '2021-04-12 14:14:01');
COMMIT;

-- ----------------------------
--  Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(10) CHARACTER SET utf8 DEFAULT NULL,
  `username` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `password` varchar(20) CHARACTER SET utf8 DEFAULT '0',
  `description` varchar(40) CHARACTER SET utf8 DEFAULT NULL,
  `telephone` varchar(11) COLLATE utf8_bin DEFAULT NULL,
  `sex` varchar(4) COLLATE utf8_bin DEFAULT NULL COMMENT '性别',
  `userId` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
