package com.example.familygoods.service.impl;

import com.example.familygoods.entity.OperationLog;
import com.example.familygoods.mapper.OperationLogMapper;
import com.example.familygoods.service.OperationLogService;
import com.example.familygoods.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperationLogServiceImpl implements OperationLogService {

    @Autowired
    private OperationLogMapper operationLogMapper;

    @Override
    public void record(Long itemId, String itemName, String operationType, String detail) {
        OperationLog log = new OperationLog();
        log.setItemId(itemId);
        log.setItemName(itemName);
        log.setOperationType(operationType);
        log.setDetail(detail);
        log.setOperator("系统");
        operationLogMapper.insert(log);
    }

    @Override
    public PageResult<OperationLog> pageList(int pageNum, int pageSize) {
        int offset = (pageNum - 1) * pageSize;
        long total = operationLogMapper.count();
        return new PageResult<>(total, operationLogMapper.selectPage(offset, pageSize));
    }
}
