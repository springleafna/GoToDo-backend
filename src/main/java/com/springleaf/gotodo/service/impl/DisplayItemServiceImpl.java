package com.springleaf.gotodo.service.impl;

import com.springleaf.gotodo.common.Result;
import com.springleaf.gotodo.mapper.DisplayItemMapper;
import com.springleaf.gotodo.model.vo.DisplayItemVO;
import com.springleaf.gotodo.service.DisplayItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DisplayItemServiceImpl implements DisplayItemService {

    private final DisplayItemMapper displayItemMapper;

    @Override
    public Result<List<DisplayItemVO>> listDisplayItem() {
        List<DisplayItemVO> displayItemVOList = displayItemMapper.listDisplayItem();
        if (displayItemVOList == null || displayItemVOList.isEmpty()) {
            return Result.success(Collections.emptyList());
        }
        return Result.success(displayItemVOList);
    }
}
