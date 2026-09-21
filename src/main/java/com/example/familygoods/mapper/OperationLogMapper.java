package com.example.familygoods.mapper;

import com.example.familygoods.entity.OperationLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OperationLogMapper {
    int insert(OperationLog log);

    List<OperationLog> selectPage(@Param("offset") int offset, @Param("size") int size);

    long count();
}
