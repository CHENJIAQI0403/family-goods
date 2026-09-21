package com.example.familygoods.service;

import com.example.familygoods.dto.CategoryDTO;
import com.example.familygoods.entity.GoodsCategory;

import java.util.List;

public interface CategoryService {
    List<GoodsCategory> list();

    GoodsCategory add(CategoryDTO dto);

    void update(CategoryDTO dto);

    void delete(Long id);
}
