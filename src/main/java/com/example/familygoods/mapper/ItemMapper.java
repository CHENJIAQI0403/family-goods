package com.example.familygoods.mapper;

import com.example.familygoods.dto.ItemQueryDTO;
import com.example.familygoods.entity.GoodsItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Mapper
public interface ItemMapper {
    int insert(GoodsItem item);

    int update(GoodsItem item);

    int deleteById(Long id);

    int batchDelete(@Param("ids") List<Long> ids);

    GoodsItem selectById(Long id);

    /** 分页条件查询 */
    List<GoodsItem> selectPageList(ItemQueryDTO query);

    long selectPageCount(ItemQueryDTO query);

    /** 过期预警列表（即将过期 + 已过期） */
    List<GoodsItem> selectWarnList(@Param("today") LocalDate today,
                                   @Param("warnDate") LocalDate warnDate);

    /** 统计总数 */
    long countTotal();

    /** 统计已过期数量 */
    long countExpired(@Param("today") LocalDate today);

    /** 统计即将过期数量（7天内） */
    long countWarn(@Param("today") LocalDate today,
                   @Param("warnDate") LocalDate warnDate);

    /** 按分类统计物品分布 */
    List<Map<String, Object>> countByCategory();

    /** 低值库存列表（数量 <= 低值阈值） */
    List<GoodsItem> selectLowStockList();

    /** 采购清单（已过期 + 低值库存） */
    List<GoodsItem> selectPurchaseList(@Param("today") LocalDate today);

    /** 统计低值库存数量 */
    long countLowStock();

    /** 使用频率排行（按 use_count 倒序） */
    List<Map<String, Object>> selectUseFrequencyTop(@Param("limit") int limit);

    /** 增加使用次数 */
    int incrementUseCount(@Param("id") Long id);

    /** 查询所有物品（用于导出） */
    List<GoodsItem> selectAllForExport();
}
