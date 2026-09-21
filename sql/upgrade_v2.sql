-- =============================================
-- 家庭物品管理系统 数据库升级脚本
-- 新增：低值库存预警、操作日志、使用频率统计
-- =============================================

USE `family_goods`;

-- 1. 物品表增加低值阈值和使用次数字段
ALTER TABLE `goods_item` ADD COLUMN `low_threshold` INT NOT NULL DEFAULT 1 COMMENT '低值库存阈值' AFTER `quantity`;
ALTER TABLE `goods_item` ADD COLUMN `use_count` INT NOT NULL DEFAULT 0 COMMENT '使用/操作次数' AFTER `low_threshold`;

-- 2. 操作日志表
DROP TABLE IF EXISTS `operation_log`;
CREATE TABLE `operation_log` (
    `id`             BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `item_id`        BIGINT       DEFAULT NULL COMMENT '物品ID',
    `item_name`      VARCHAR(100) DEFAULT NULL COMMENT '物品名称',
    `operation_type` VARCHAR(20)  NOT NULL COMMENT '操作类型: add/update/delete',
    `detail`         VARCHAR(500) DEFAULT NULL COMMENT '操作详情',
    `operator`       VARCHAR(50)  DEFAULT '系统' COMMENT '操作人',
    `create_time`    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
    PRIMARY KEY (`id`),
    KEY `idx_item_id` (`item_id`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作日志表';

-- 3. 初始化部分物品的低值阈值，方便测试
UPDATE `goods_item` SET `low_threshold` = 3 WHERE id IN (1, 2, 4);
