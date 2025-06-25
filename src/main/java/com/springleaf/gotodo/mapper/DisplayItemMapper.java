package com.springleaf.gotodo.mapper;

import com.springleaf.gotodo.model.entity.DisplayItem;
import com.springleaf.gotodo.model.vo.DisplayItemVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DisplayItemMapper {
    /**
     * 新增展示项
     */
    int saveDisplayItem(DisplayItem displayItem);

    /**
     * 获取所有展示项（排除已有归属的任务类）
     */
    List<DisplayItemVO> listDisplayItem();

    /**
     * 根据类型和ID获取展示项最大排序值
     */
    int getMaxSortOrderByItemTypeAndRefId(@Param("itemType") String itemType, @Param("itemRefId") Long itemRefId);

    /**
     * 获取展示项最大排序值
     */
    int getMaxSortOrder();

    /**
     * 根据分类ID和类型获取展示项
     */
    DisplayItem getDisplayItemByCategoryIdAndType(@Param("itemRefId") Long itemRefId, @Param("itemType") String itemType);

    /**
     * 删除展示项
     */
    int deleteDisplayItemById(Long itemId);
}
