package com.example.familygoods.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * 家庭物品实体
 */
@Data
public class GoodsItem {
    private Long id;
    private String name;
    private Long categoryId;
    private Integer quantity;
    private Integer lowThreshold;
    private Integer useCount;
    private String location;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate purchaseTime;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expireTime;
    private String remark;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    // 关联字段（查询时填充）
    private String categoryName;
    /** 过期状态：normal-正常, warn-即将过期(7天内), expired-已过期 */
    private String expireStatus;
}
