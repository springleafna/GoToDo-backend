package com.springleaf.gotodo.service;

import com.springleaf.gotodo.common.Result;
import com.springleaf.gotodo.model.vo.DisplayItemVO;

import java.util.List;

public interface DisplayItemService {
    
    /**
     * 获取展示项列表
     * @return Result<List<DisplayItemVO>>
     */
    Result<List<DisplayItemVO>> listDisplayItem();
}
