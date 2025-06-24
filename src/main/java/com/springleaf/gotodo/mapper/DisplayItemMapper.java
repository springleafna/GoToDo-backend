package com.springleaf.gotodo.mapper;

import com.springleaf.gotodo.model.entity.DisplayItem;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DisplayItemMapper {
    /**
     * 新增展示项
     */
    int saveDisplayItem(DisplayItem displayItem);
}
