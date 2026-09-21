package com.example.familygoods.service;

import com.example.familygoods.entity.OperationLog;
import com.example.familygoods.util.PageResult;

public interface OperationLogService {
    void record(Long itemId, String itemName, String operationType, String detail);

    PageResult<OperationLog> pageList(int pageNum, int pageSize);
}
