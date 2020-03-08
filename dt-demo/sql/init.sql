CREATE DATABASE `account` CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
use account;

create table account (
   `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
   `uid` VARCHAR(64) NOT NULL DEFAULT '' COMMENT '用户ID',
   `available_amount` bigint(20) NOT NULL DEFAULT '0' COMMENT '可用余额',
   `freeze_amount` bigint(20) NOT NULL DEFAULT '0' COMMENT '冻结金额',
   PRIMARY KEY (`id`),
   UNIQUE KEY `uk_uid` (`uid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账户表';

create table account_flow (
   `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
   `uid` VARCHAR(64) NOT NULL DEFAULT '' COMMENT '用户ID',
   `order_id` VARCHAR(64) NOT NULL DEFAULT '' COMMENT '交易流水ID',
   `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态',
   `amount` bigint(20) NOT NULL DEFAULT '0' COMMENT '交易金额',
   PRIMARY KEY (`id`),
   UNIQUE KEY `uk_order_id` (`order_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账户流水表';



CREATE DATABASE `coupon` CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
use coupon;

create table coupon (
   `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
   `uid` VARCHAR(64) NOT NULL DEFAULT '' COMMENT '用户ID',
   `coupon_id` VARCHAR(64) NOT NULL DEFAULT '' COMMENT '券ID',
   `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态',
   `orderId` VARCHAR(64) NOT NULL DEFAULT '' COMMENT '交易流水ID',
   PRIMARY KEY (`id`),
   UNIQUE KEY `uk_coupon_id` (`coupon_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='券表';



CREATE DATABASE `pay` CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
use pay;

create table pay_order (
   `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
   `uid` VARCHAR(64) NOT NULL DEFAULT '' COMMENT '用户ID',
   `order_id` VARCHAR(64) NOT NULL DEFAULT '' COMMENT '交易订单ID',
   `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态',
   `amount` bigint(20) NOT NULL DEFAULT '0' COMMENT '交易金额',
   PRIMARY KEY (`id`),
   UNIQUE KEY `uk_order_id` (`order_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付订单表';

create table pay_channel (
   `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
   `uid` VARCHAR(64) NOT NULL DEFAULT '' COMMENT '用户ID',
   `order_id` VARCHAR(64) NOT NULL DEFAULT '' COMMENT '交易订单ID',
   `channel_id` int(11) NOT NULL DEFAULT '0' COMMENT '渠道ID',
   `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态',
   `amount` bigint(20) NOT NULL DEFAULT '0' COMMENT '交易金额',
   `assets_id` VARCHAR(64) NOT NULL DEFAULT '' COMMENT '资产ID',
   PRIMARY KEY (`id`),
   UNIQUE KEY `uk_order_id_channel_id` (`order_id`, `channel_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付订单渠道表';


