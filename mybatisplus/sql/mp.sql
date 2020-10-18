/*
 Navicat Premium Data Transfer

 Source Server         : docker-mysql3306
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : localhost:3306
 Source Schema         : mp

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 13/10/2020 21:43:27
*/

DROP database IF EXISTS `mp`;
create database mp;
use mp;


SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tbl_employee
-- ----------------------------
DROP TABLE IF EXISTS `tbl_employee`;
CREATE TABLE `tbl_employee` (
  `id` bigint(20) NOT NULL,
  `last_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `gender` char(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `version` int(255) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `delete_flag` tinyint(2) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of tbl_employee
-- ----------------------------
BEGIN;
INSERT INTO `tbl_employee` VALUES (2, 'Jerry', 'jerry@atguigu.com', '0', 25, 0, NULL, NULL, 0);
INSERT INTO `tbl_employee` VALUES (3, 'Black', 'black@atguigu.com', '1', 30, 0, NULL, NULL, 0);
INSERT INTO `tbl_employee` VALUES (4, 'White', 'white@atguigu.com', '0', 35, 0, NULL, NULL, 0);
INSERT INTO `tbl_employee` VALUES (5, '玛利亚老师', 'mly@sina.com', '0', 22, 0, NULL, NULL, 0);
INSERT INTO `tbl_employee` VALUES (7, '小泽老师', 'xz1@sina.com', '0', 35, 1, NULL, '2020-10-10 00:27:09', 1);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
