package com.example.familygoods.controller;

import com.example.familygoods.dto.ItemDTO;
import com.example.familygoods.dto.ItemQueryDTO;
import com.example.familygoods.entity.GoodsItem;
import com.example.familygoods.service.ItemService;
import com.example.familygoods.util.PageResult;
import com.example.familygoods.util.Result;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * 家庭物品台账接口
 */
@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    /** 新增物品 */
    @PostMapping("/add")
    public Result<GoodsItem> add(@Valid @RequestBody ItemDTO dto) {
        return Result.success("新增成功", itemService.add(dto));
    }

    /** 修改物品 */
    @PostMapping("/update")
    public Result<Void> update(@Valid @RequestBody ItemDTO dto) {
        itemService.update(dto);
        return Result.success();
    }

    /** 删除物品 */
    @PostMapping("/delete")
    public Result<Void> delete(@RequestParam Long id) {
        itemService.delete(id);
        return Result.success();
    }

    /** 批量删除 */
    @PostMapping("/batchDelete")
    public Result<Void> batchDelete(@RequestBody List<Long> ids) {
        itemService.batchDelete(ids);
        return Result.success();
    }

    /** 分页条件查询 */
    @GetMapping("/pageList")
    public Result<PageResult<GoodsItem>> pageList(ItemQueryDTO query) {
        return Result.success(itemService.pageList(query));
    }

    /** 过期预警列表 */
    @GetMapping("/warnList")
    public Result<List<GoodsItem>> warnList() {
        return Result.success(itemService.warnList());
    }

    /** 首页数据统计 */
    @GetMapping("/getStatistics")
    public Result<Map<String, Object>> getStatistics() {
        return Result.success(itemService.getStatistics());
    }

    /** 低值库存列表 */
    @GetMapping("/lowStockList")
    public Result<List<GoodsItem>> lowStockList() {
        return Result.success(itemService.lowStockList());
    }

    /** 采购清单 */
    @GetMapping("/purchaseList")
    public Result<List<GoodsItem>> purchaseList() {
        return Result.success(itemService.purchaseList());
    }

    /** 使用频率排行 */
    @GetMapping("/useFrequencyTop")
    public Result<List<Map<String, Object>>> useFrequencyTop(@RequestParam(defaultValue = "10") int limit) {
        return Result.success(itemService.useFrequencyTop(limit));
    }

    /** 导出CSV */
    @GetMapping("/export")
    public void exportCsv(HttpServletResponse response) throws IOException {
        String csv = itemService.exportCsv();
        String fileName = URLEncoder.encode("家庭物品清单.csv", StandardCharsets.UTF_8);
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + fileName);
        OutputStream out = response.getOutputStream();
        out.write(csv.getBytes(StandardCharsets.UTF_8));
        out.flush();
        out.close();
    }
}
