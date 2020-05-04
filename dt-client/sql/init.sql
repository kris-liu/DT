CREATE DATABASE `dt` CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
use dt;

create table activity (
   `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
   `xid` VARCHAR(128) NOT NULL DEFAULT '' COMMENT '事务ID',
   `name` VARCHAR(64) NOT NULL DEFAULT '' COMMENT '事务发起者名称',
   `status` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '事务状态',
   `start_time` DATETIME NOT NULL DEFAULT '1971-01-01 00:00:00' COMMENT '事务开始时间',
   `timeout_time` DATETIME NOT NULL DEFAULT '1971-01-01 00:00:00' COMMENT '事务超时时间',
   `execution_time` DATETIME NOT NULL DEFAULT '1971-01-01 00:00:00' COMMENT '执行时间，每次重试后将执行时间向后延迟',
   `retry_count` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '二阶段重试次数',
   `gmt_create` DATETIME NOT NULL DEFAULT '1971-01-01 00:00:00' COMMENT '事务创建时间',
   `gmt_modified` DATETIME NOT NULL DEFAULT '1971-01-01 00:00:00' COMMENT '事务更新时间',
   PRIMARY KEY (`id`),
   UNIQUE KEY `uk_xid` (`xid`),
   KEY `idx_execution_time_status` (`execution_time`, `status`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='主事务记录表';

create table action (
   `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
   `xid` VARCHAR(128) NOT NULL DEFAULT '' COMMENT '主事务ID',
   `name` VARCHAR(64) NOT NULL DEFAULT '' COMMENT '分支事务名称',
   `status` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '分支事务状态',
   `arguments` VARCHAR(2000) NOT NULL DEFAULT '' COMMENT '分支事务一阶段参数',
   `gmt_create` DATETIME NOT NULL DEFAULT '1971-01-01 00:00:00' COMMENT '分支事务创建时间',
   `gmt_modified` DATETIME NOT NULL DEFAULT '1971-01-01 00:00:00' COMMENT '分支事务更新时间',
   PRIMARY KEY (`id`),
   UNIQUE KEY `uk_xid_name` (`xid`, `name`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='分支事务记录表';


DELETE FROM `dt`.`activity`;
DELETE FROM `dt`.`action`;

