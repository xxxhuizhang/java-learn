/*
 Navicat Premium Data Transfer

 Source Server         : docker-mysql3306
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : localhost:3306
 Source Schema         : mybatis

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 13/10/2020 21:43:38
*/

DROP database IF EXISTS `mybatis`;
create database mybatis;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tbl_dept
-- ----------------------------
DROP TABLE IF EXISTS `tbl_dept`;
CREATE TABLE `tbl_dept` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dept_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tbl_dept
-- ----------------------------
BEGIN;
INSERT INTO `tbl_dept` VALUES (1, '开发部');
INSERT INTO `tbl_dept` VALUES (2, '测试部');
COMMIT;

-- ----------------------------
-- Table structure for tbl_employee
-- ----------------------------
DROP TABLE IF EXISTS `tbl_employee`;
CREATE TABLE `tbl_employee` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `last_name` varchar(255) DEFAULT NULL,
  `gender` char(1) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `d_id` int(11) DEFAULT NULL,
  `emp_status` varchar(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_emp_dept` (`d_id`),
  CONSTRAINT `fk_emp_dept` FOREIGN KEY (`d_id`) REFERENCES `tbl_dept` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tbl_employee
-- ----------------------------
BEGIN;
INSERT INTO `tbl_employee` VALUES (1, 'Tom1', '0', 'jerry@atguigu.com', 1, NULL);
INSERT INTO `tbl_employee` VALUES (2, 'Tom', '1', 'tom@sina.com', 1, NULL);
INSERT INTO `tbl_employee` VALUES (3, 'jerry4', '1', 'jerry@qq.com', 2, NULL);
INSERT INTO `tbl_employee` VALUES (5, 'test_enum', '1', 'enum@atguigu.com', 2, NULL);
INSERT INTO `tbl_employee` VALUES (10, 'smith0x1', '1', 'smith0x1@atguigu.com', 1, NULL);
INSERT INTO `tbl_employee` VALUES (11, 'allen0x1', '0', 'allen0x1@atguigu.com', 1, NULL);
INSERT INTO `tbl_employee` VALUES (14, 'jerry4', '1', NULL, NULL, NULL);
INSERT INTO `tbl_employee` VALUES (17, 'jerry4', '1', NULL, NULL, NULL);
INSERT INTO `tbl_employee` VALUES (19, 'test_enum', '1', 'enum@atguigu.com', NULL, 'LOGOUT');
INSERT INTO `tbl_employee` VALUES (20, 'test_enum', '1', 'enum@atguigu.com', NULL, '200');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
