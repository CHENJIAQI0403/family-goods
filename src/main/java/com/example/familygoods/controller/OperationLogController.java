package com.example.familygoods.controller;

import com.example.familygoods.entity.OperationLog;
import com.example.familygoods.service.OperationLogService;
import com.example.familygoods.util.PageResult;
import com.example.familygoods.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 操作日志接口
 */
@RestController
@RequestMapping("/log")
public class OperationLogController {

    @Autowired
    private OperationLogService operationLogService;

    /** 分页查询操作日志 */
    @GetMapping("/pageList")
    public Result<PageResult<OperationLog>> pageList(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        return Result.success(operationLogService.pageList(pageNum, pageSize));
    }
}
