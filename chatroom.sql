/*
 Navicat Premium Data Transfer

 Source Server         : server_diary
 Source Server Type    : MySQL
 Source Server Version : 50738
 Source Host           : 43.138.56.232:3306
 Source Schema         : chatroom

 Target Server Type    : MySQL
 Target Server Version : 50738
 File Encoding         : 65001

 Date: 30/06/2022 16:11:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `name` char(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户名 主键',
  `password` char(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '密码',
  PRIMARY KEY (`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '1');
INSERT INTO `user` VALUES ('cjh', '1');

SET FOREIGN_KEY_CHECKS = 1;
