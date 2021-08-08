/*
 Navicat Premium Data Transfer

 Source Server         : root
 Source Server Type    : MySQL
 Source Server Version : 80026
 Source Host           : localhost:3306
 Source Schema         : genshin_impact_info

 Target Server Type    : MySQL
 Target Server Version : 80026
 File Encoding         : 65001

 Date: 08/08/2021 11:43:47
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `uid` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `login_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, 'user244693w', '1111', 'nick1', '2021-08-07 21:10:55');
INSERT INTO `users` VALUES (2, 'user24462932w', '1111', 'nick1', '2021-08-07 21:15:15');
INSERT INTO `users` VALUES (3, 'user244622932w', '1111', 'nick1', '2021-08-07 00:00:00');
INSERT INTO `users` VALUES (4, 'user2446222932w', '1111', 'nick1', '2021-08-07 00:00:00');
INSERT INTO `users` VALUES (5, 'user23446222932w', '1111', 'nick1', '2021-08-07 21:29:43');
INSERT INTO `users` VALUES (6, 'user232446222932w', '1111', 'nick1', '2021-08-07 21:31:11');
INSERT INTO `users` VALUES (7, 'user2324462f22932w', '1111', 'nick1', '2021-08-07 21:32:54');
INSERT INTO `users` VALUES (8, 'user2324462f22g932w', '1111', 'nick1', '2021-08-07 21:33:32');
INSERT INTO `users` VALUES (9, 'user2324462f22gg932w', '1111', 'nick1', '2021-08-07 21:33:51');
INSERT INTO `users` VALUES (10, 'user2324462f22ggg932w', '1111', 'nick1', '2021-08-07 21:34:07');
INSERT INTO `users` VALUES (11, 'user2324462f22gggh932w', '1111', 'nick1', '2021-08-07 21:34:47');
INSERT INTO `users` VALUES (12, 'user2324462f232gggh932w', '1111', 'nick1', '2021-08-07 21:43:02');
INSERT INTO `users` VALUES (13, 'user2324462f232gggh932sw', '1111', 'nick1', '2021-08-07 21:43:31');
INSERT INTO `users` VALUES (14, 'user2324462f232gwggh932sw', '1111', 'nick1', '2021-08-07 21:44:02');
INSERT INTO `users` VALUES (15, 'user2324462f232gwsggh932sw', '1111', 'nick1', '2021-08-07 21:44:30');
INSERT INTO `users` VALUES (16, 'user2s324462f232gwsggh932sw', '1111', 'nick1', '2021-08-07 21:45:07');
INSERT INTO `users` VALUES (17, 'user1', '1111', 'nick1', '2021-08-07 21:48:45');

SET FOREIGN_KEY_CHECKS = 1;
