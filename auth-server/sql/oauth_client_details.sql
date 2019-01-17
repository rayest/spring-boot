/*
 Navicat Premium Data Transfer

 Source Server         : 开发环境
 Source Server Type    : MySQL
 Source Server Version : 50720
 Source Host           : 39.106.46.175:3307
 Source Schema         : liangla

 Target Server Type    : MySQL
 Target Server Version : 50720
 File Encoding         : 65001

 Date: 10/07/2018 10:21:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details` (
  `client_id`               varchar(255) NOT NULL,
  `resource_ids`            varchar(255)  DEFAULT NULL,
  `client_secret`           varchar(255)  DEFAULT NULL,
  `scope`                   varchar(255)  DEFAULT NULL,
  `authorized_grant_types`  varchar(255)  DEFAULT NULL,
  `web_server_redirect_uri` varchar(255)  DEFAULT NULL,
  `authorities`             varchar(255)  DEFAULT NULL,
  `access_token_validity`   int(11)       DEFAULT NULL,
  `refresh_token_validity`  int(11)       DEFAULT NULL,
  `additional_information`  varchar(4096) DEFAULT NULL,
  `autoapprove`             varchar(255)  DEFAULT NULL,
  PRIMARY KEY (`client_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

SET FOREIGN_KEY_CHECKS = 1;
