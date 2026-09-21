package com.example.familygoods.dto;

import lombok.Data;

/**
 * 物品分页查询参数
 */
@Data
public class ItemQueryDTO {
    private Integer pageNum = 1;
    private Integer pageSize = 10;
    private String name;
    private Long categoryId;
    private String location;
}
