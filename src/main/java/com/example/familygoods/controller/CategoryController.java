package com.example.familygoods.controller;

import com.example.familygoods.dto.CategoryDTO;
import com.example.familygoods.entity.GoodsCategory;
import com.example.familygoods.service.CategoryService;
import com.example.familygoods.util.Result;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 物品分类接口
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /** 查询全部分类 */
    @GetMapping("/list")
    public Result<List<GoodsCategory>> list() {
        return Result.success(categoryService.list());
    }

    /** 新增分类 */
    @PostMapping("/add")
    public Result<GoodsCategory> add(@Valid @RequestBody CategoryDTO dto) {
        return Result.success("新增成功", categoryService.add(dto));
    }

    /** 修改分类 */
    @PostMapping("/update")
    public Result<Void> update(@Valid @RequestBody CategoryDTO dto) {
        categoryService.update(dto);
        return Result.success();
    }

    /** 删除分类 */
    @PostMapping("/delete")
    public Result<Void> delete(@RequestParam Long id) {
        categoryService.delete(id);
        return Result.success();
    }
}
