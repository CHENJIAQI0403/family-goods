package com.example.familygoods.mapper;

import com.example.familygoods.entity.GoodsCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    List<GoodsCategory> selectAll();

    GoodsCategory selectById(Long id);

    GoodsCategory selectByName(String name);

    int insert(GoodsCategory category);

    int update(GoodsCategory category);

    int deleteById(Long id);
}
