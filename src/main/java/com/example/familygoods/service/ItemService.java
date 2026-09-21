package com.example.familygoods.service;

import com.example.familygoods.dto.ItemDTO;
import com.example.familygoods.dto.ItemQueryDTO;
import com.example.familygoods.entity.GoodsItem;
import com.example.familygoods.util.PageResult;

import java.util.List;
import java.util.Map;

public interface ItemService {
    GoodsItem add(ItemDTO dto);

    void update(ItemDTO dto);

    void delete(Long id);

    void batchDelete(List<Long> ids);

    PageResult<GoodsItem> pageList(ItemQueryDTO query);

    /** 过期预警列表 */
    List<GoodsItem> warnList();

    /** 首页统计 */
    Map<String, Object> getStatistics();

    /** 低值库存列表 */
    List<GoodsItem> lowStockList();

    /** 采购清单（已过期 + 低值库存） */
    List<GoodsItem> purchaseList();

    /** 使用频率排行 */
    List<Map<String, Object>> useFrequencyTop(int limit);

    /** 导出CSV */
    String exportCsv();
}
