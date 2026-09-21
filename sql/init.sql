-- =============================================
-- 家庭物品管理系统 数据库初始化脚本
-- 数据库：family_goods
-- =============================================

CREATE DATABASE IF NOT EXISTS `family_goods` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE `family_goods`;

-- 物品分类表
DROP TABLE IF EXISTS `goods_category`;
CREATE TABLE `goods_category` (
    `id`         BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `name`       VARCHAR(50)  NOT NULL COMMENT '分类名称',
    `create_time` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_category_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物品分类表';

-- 家庭物品表
DROP TABLE IF EXISTS `goods_item`;
CREATE TABLE `goods_item` (
    `id`            BIGINT        NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `name`          VARCHAR(100)  NOT NULL COMMENT '物品名称',
    `category_id`   BIGINT        NOT NULL COMMENT '分类ID',
    `quantity`      INT           NOT NULL DEFAULT 1 COMMENT '数量',
    `location`      VARCHAR(100)  DEFAULT NULL COMMENT '存放位置',
    `purchase_time` DATE          DEFAULT NULL COMMENT '购入时间',
    `expire_time`   DATE          DEFAULT NULL COMMENT '保质期截止时间',
    `remark`        VARCHAR(255)  DEFAULT NULL COMMENT '备注',
    `create_time`   DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_category_id` (`category_id`),
    KEY `idx_expire_time` (`expire_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='家庭物品台账表';

-- 初始化分类数据
INSERT INTO `goods_category` (`name`) VALUES
('药品'),
('零食'),
('日用品'),
('清洁用品'),
('数码配件'),
('衣物耗材');

-- 初始化物品数据（部分设置为即将过期和已过期，方便测试预警功能）
INSERT INTO `goods_item` (`name`, `category_id`, `quantity`, `location`, `purchase_time`, `expire_time`, `remark`) VALUES
('感冒灵颗粒', 1, 2, '药箱', '2025-01-10', '2026-03-01', '已过期示例'),
('布洛芬', 1, 1, '药箱', '2025-06-15', DATE_ADD(CURDATE(), INTERVAL 3 DAY), '即将过期示例'),
('维生素C片', 1, 3, '药箱', '2025-09-01', '2027-09-01', NULL),
('薯片', 2, 5, '零食柜', '2026-08-01', DATE_ADD(CURDATE(), INTERVAL 5 DAY), '即将过期'),
('巧克力', 2, 2, '零食柜', '2026-07-15', '2027-07-15', NULL),
('洗衣液', 4, 2, '阳台', '2026-05-01', '2028-05-01', NULL),
('数据线', 5, 3, '抽屉', '2026-01-01', NULL, '无保质期'),
('牙膏', 3, 4, '洗漱台', '2026-06-01', '2029-06-01', NULL);
