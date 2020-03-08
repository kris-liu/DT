CREATE DATABASE `account` CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
use account;

create table account (
   `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
   `uid` VARCHAR(64) NOT NULL DEFAULT '' COMMENT '用户ID',
   `availableAmount` bigint(20) NOT NULL DEFAULT '0' COMMENT '可用余额',
   `freezeAmount` bigint(20) NOT NULL DEFAULT '0' COMMENT '冻结金额',
   PRIMARY KEY (`id`),
   UNIQUE KEY `uk_uid` (`uid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账户表';

create table account_flow (
   `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
   `uid` VARCHAR(64) NOT NULL DEFAULT '' COMMENT '用户ID',
   `orderId` VARCHAR(64) NOT NULL DEFAULT '' COMMENT '流水ID',
   `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态',
   `amount` bigint(20) NOT NULL DEFAULT '0' COMMENT '交易金额',
   PRIMARY KEY (`id`),
   UNIQUE KEY `uk_orderId` (`orderId`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账户流水表';




