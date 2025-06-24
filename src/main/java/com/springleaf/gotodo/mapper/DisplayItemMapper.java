package com.springleaf.gotodo.mapper;

import com.springleaf.gotodo.model.entity.DisplayItem;
import com.springleaf.gotodo.model.vo.DisplayItemVO;
import org.apache.ibatis.annotations.Mapper;

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
}
