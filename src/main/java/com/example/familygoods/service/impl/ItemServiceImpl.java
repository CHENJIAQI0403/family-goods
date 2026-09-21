package com.example.familygoods.service.impl;

import com.example.familygoods.dto.ItemDTO;
import com.example.familygoods.dto.ItemQueryDTO;
import com.example.familygoods.entity.GoodsItem;
import com.example.familygoods.exception.BusinessException;
import com.example.familygoods.mapper.ItemMapper;
import com.example.familygoods.service.ItemService;
import com.example.familygoods.service.OperationLogService;
import com.example.familygoods.util.PageResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private OperationLogService operationLogService;

    /** 预警提前天数 */
    private static final int WARN_DAYS = 7;

    @Override
    public GoodsItem add(ItemDTO dto) {
        GoodsItem item = new GoodsItem();
        BeanUtils.copyProperties(dto, item);
        if (item.getLowThreshold() == null) {
            item.setLowThreshold(1);
        }
        itemMapper.insert(item);
        operationLogService.record(item.getId(), item.getName(), "add",
                "新增物品：" + item.getName() + "，数量：" + item.getQuantity());
        return item;
    }

    @Override
    public void update(ItemDTO dto) {
        GoodsItem exist = itemMapper.selectById(dto.getId());
        if (exist == null) {
            throw new BusinessException("物品不存在");
        }
        GoodsItem item = new GoodsItem();
        BeanUtils.copyProperties(dto, item);
        if (item.getLowThreshold() == null) {
            item.setLowThreshold(1);
        }
        itemMapper.update(item);
        itemMapper.incrementUseCount(dto.getId());
        operationLogService.record(item.getId(), item.getName(), "update",
                "修改物品：" + item.getName() + "，数量：" + item.getQuantity());
    }

    @Override
    public void delete(Long id) {
        GoodsItem exist = itemMapper.selectById(id);
        if (exist == null) {
            throw new BusinessException("物品不存在");
        }
        itemMapper.deleteById(id);
        operationLogService.record(id, exist.getName(), "delete",
                "删除物品：" + exist.getName());
    }

    @Override
    public void batchDelete(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            throw new BusinessException("请选择要删除的物品");
        }
        itemMapper.batchDelete(ids);
        operationLogService.record(null, "批量删除", "delete",
                "批量删除物品，数量：" + ids.size());
    }

    @Override
    public PageResult<GoodsItem> pageList(ItemQueryDTO query) {
        long total = itemMapper.selectPageCount(query);
        List<GoodsItem> records = itemMapper.selectPageList(query);
        records.forEach(this::fillExpireStatus);
        return new PageResult<>(total, records);
    }

    @Override
    public List<GoodsItem> warnList() {
        LocalDate today = LocalDate.now();
        LocalDate warnDate = today.plusDays(WARN_DAYS);
        List<GoodsItem> list = itemMapper.selectWarnList(today, warnDate);
        list.forEach(this::fillExpireStatus);
        return list;
    }

    @Override
    public Map<String, Object> getStatistics() {
        LocalDate today = LocalDate.now();
        LocalDate warnDate = today.plusDays(WARN_DAYS);

        Map<String, Object> result = new HashMap<>();
        result.put("total", itemMapper.countTotal());
        result.put("expired", itemMapper.countExpired(today));
        result.put("warn", itemMapper.countWarn(today, warnDate));
        result.put("lowStock", itemMapper.countLowStock());
        result.put("categoryStats", itemMapper.countByCategory());
        result.put("useFrequencyTop", itemMapper.selectUseFrequencyTop(5));
        return result;
    }

    @Override
    public List<GoodsItem> lowStockList() {
        return itemMapper.selectLowStockList();
    }

    @Override
    public List<GoodsItem> purchaseList() {
        LocalDate today = LocalDate.now();
        List<GoodsItem> list = itemMapper.selectPurchaseList(today);
        list.forEach(this::fillExpireStatus);
        return list;
    }

    @Override
    public List<Map<String, Object>> useFrequencyTop(int limit) {
        return itemMapper.selectUseFrequencyTop(limit);
    }

    @Override
    public String exportCsv() {
        List<GoodsItem> list = itemMapper.selectAllForExport();
        StringBuilder sb = new StringBuilder();
        // UTF-8 BOM 让 Excel 正确识别中文
        sb.append('\uFEFF');
        sb.append("ID,物品名称,分类,数量,低值阈值,存放位置,购入时间,保质期截止,备注,状态\n");
        for (GoodsItem item : list) {
            fillExpireStatus(item);
            String status = "normal".equals(item.getExpireStatus()) ? "正常"
                    : "warn".equals(item.getExpireStatus()) ? "即将过期" : "已过期";
            sb.append(item.getId()).append(",")
              .append(csv(item.getName())).append(",")
              .append(csv(item.getCategoryName())).append(",")
              .append(item.getQuantity()).append(",")
              .append(item.getLowThreshold()).append(",")
              .append(csv(item.getLocation())).append(",")
              .append(item.getPurchaseTime() == null ? "" : item.getPurchaseTime()).append(",")
              .append(item.getExpireTime() == null ? "" : item.getExpireTime()).append(",")
              .append(csv(item.getRemark())).append(",")
              .append(status).append("\n");
        }
        return sb.toString();
    }

    private String csv(String v) {
        if (v == null) return "";
        if (v.contains(",") || v.contains("\"") || v.contains("\n")) {
            return "\"" + v.replace("\"", "\"\"") + "\"";
        }
        return v;
    }

    /**
     * 根据保质期计算过期状态
     */
    private void fillExpireStatus(GoodsItem item) {
        if (item.getExpireTime() == null) {
            item.setExpireStatus("normal");
            return;
        }
        LocalDate today = LocalDate.now();
        if (item.getExpireTime().isBefore(today)) {
            item.setExpireStatus("expired");
        } else if (!item.getExpireTime().isAfter(today.plusDays(WARN_DAYS))) {
            item.setExpireStatus("warn");
        } else {
            item.setExpireStatus("normal");
        }
    }
}
