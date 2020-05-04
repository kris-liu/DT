CREATE DATABASE `account`
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci;
USE account;

CREATE TABLE account (
  `id`               BIGINT(20)  NOT NULL AUTO_INCREMENT
  COMMENT '自增主键',
  `uid`              VARCHAR(64) NOT NULL DEFAULT ''
  COMMENT '用户ID',
  `available_amount` BIGINT(20)  NOT NULL DEFAULT '0'
  COMMENT '可用余额',
  `freeze_amount`    BIGINT(20)  NOT NULL DEFAULT '0'
  COMMENT '冻结金额',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_uid` (`uid`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '账户表';

CREATE TABLE account_flow (
  `id`       BIGINT(20)  NOT NULL AUTO_INCREMENT
  COMMENT '自增主键',
  `uid`      VARCHAR(64) NOT NULL DEFAULT ''
  COMMENT '用户ID',
  `order_id` VARCHAR(64) NOT NULL DEFAULT ''
  COMMENT '交易流水ID',
  `status`   INT(11)     NOT NULL DEFAULT '0'
  COMMENT '状态',
  `amount`   BIGINT(20)  NOT NULL DEFAULT '0'
  COMMENT '交易金额',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_id` (`order_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '账户流水表';


CREATE DATABASE `coupon`
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci;
USE coupon;

CREATE TABLE coupon (
  `id`        BIGINT(20)  NOT NULL AUTO_INCREMENT
  COMMENT '自增主键',
  `uid`       VARCHAR(64) NOT NULL DEFAULT ''
  COMMENT '用户ID',
  `coupon_id` VARCHAR(64) NOT NULL DEFAULT ''
  COMMENT '券ID',
  `status`    INT(11)     NOT NULL DEFAULT '0'
  COMMENT '状态',
  `amount`    BIGINT(20)  NOT NULL DEFAULT '0'
  COMMENT '券面额',
  `order_id`  VARCHAR(64) NOT NULL DEFAULT ''
  COMMENT '交易流水ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_coupon_id` (`coupon_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '券表';


CREATE DATABASE `pay`
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci;
USE pay;

CREATE TABLE pay_order (
  `id`       BIGINT(20)  NOT NULL AUTO_INCREMENT
  COMMENT '自增主键',
  `uid`      VARCHAR(64) NOT NULL DEFAULT ''
  COMMENT '用户ID',
  `order_id` VARCHAR(64) NOT NULL DEFAULT ''
  COMMENT '交易订单ID',
  `status`   INT(11)     NOT NULL DEFAULT '0'
  COMMENT '状态',
  `amount`   BIGINT(20)  NOT NULL DEFAULT '0'
  COMMENT '交易金额',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_id` (`order_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '支付订单表';

CREATE TABLE pay_channel (
  `id`         BIGINT(20)  NOT NULL AUTO_INCREMENT
  COMMENT '自增主键',
  `uid`        VARCHAR(64) NOT NULL DEFAULT ''
  COMMENT '用户ID',
  `order_id`   VARCHAR(64) NOT NULL DEFAULT ''
  COMMENT '交易订单ID',
  `channel_id` INT(11)     NOT NULL DEFAULT '0'
  COMMENT '渠道ID',
  `status`     INT(11)     NOT NULL DEFAULT '0'
  COMMENT '状态',
  `amount`     BIGINT(20)  NOT NULL DEFAULT '0'
  COMMENT '交易金额',
  `asset_id`   VARCHAR(64) NOT NULL DEFAULT ''
  COMMENT '资产ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_id_channel_id` (`order_id`, `channel_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '支付订单渠道表';

## 初始化数据
DELETE FROM `account`.`account`;
DELETE FROM `account`.`account_flow`;
DELETE FROM `coupon`.`coupon`;
DELETE FROM `pay`.`pay_order`;
DELETE FROM `pay`.`pay_channel`;
DELETE FROM `dt`.`activity`;
DELETE FROM `dt`.`action`;

INSERT INTO `account`.`account` (uid, available_amount) VALUE ('000001', 1000000);
INSERT INTO `coupon`.`coupon` (uid, coupon_id, status, amount) VALUE ('000001', 'COUPON000001', 0, 100);


