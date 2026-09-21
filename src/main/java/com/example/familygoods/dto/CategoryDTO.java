package com.example.familygoods.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 分类新增/修改参数
 */
@Data
public class CategoryDTO {
    private Long id;
    @NotBlank(message = "分类名称不能为空")
    private String name;
}
