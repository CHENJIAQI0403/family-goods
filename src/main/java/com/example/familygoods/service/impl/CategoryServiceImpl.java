package com.example.familygoods.service.impl;

import com.example.familygoods.dto.CategoryDTO;
import com.example.familygoods.entity.GoodsCategory;
import com.example.familygoods.exception.BusinessException;
import com.example.familygoods.mapper.CategoryMapper;
import com.example.familygoods.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<GoodsCategory> list() {
        return categoryMapper.selectAll();
    }

    @Override
    public GoodsCategory add(CategoryDTO dto) {
        // 分类唯一校验
        GoodsCategory exist = categoryMapper.selectByName(dto.getName());
        if (exist != null) {
            throw new BusinessException("分类名称已存在，不可重复创建");
        }
        GoodsCategory category = new GoodsCategory();
        BeanUtils.copyProperties(dto, category);
        categoryMapper.insert(category);
        return category;
    }

    @Override
    public void update(CategoryDTO dto) {
        GoodsCategory exist = categoryMapper.selectById(dto.getId());
        if (exist == null) {
            throw new BusinessException("分类不存在");
        }
        // 名称重复校验（排除自身）
        GoodsCategory sameName = categoryMapper.selectByName(dto.getName());
        if (sameName != null && !sameName.getId().equals(dto.getId())) {
            throw new BusinessException("分类名称已存在");
        }
        GoodsCategory category = new GoodsCategory();
        BeanUtils.copyProperties(dto, category);
        categoryMapper.update(category);
    }

    @Override
    public void delete(Long id) {
        GoodsCategory exist = categoryMapper.selectById(id);
        if (exist == null) {
            throw new BusinessException("分类不存在");
        }
        categoryMapper.deleteById(id);
    }
}
