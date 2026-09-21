package com.example.familygoods.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

/**
 * 物品新增/修改参数
 */
@Data
public class ItemDTO {
    private Long id;
    @NotBlank(message = "物品名称不能为空")
    private String name;
    @NotNull(message = "分类ID不能为空")
    private Long categoryId;
    @NotNull(message = "数量不能为空")
    private Integer quantity;
    private Integer lowThreshold;
    private String location;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate purchaseTime;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expireTime;
    private String remark;
}
