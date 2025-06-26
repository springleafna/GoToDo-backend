package com.springleaf.gotodo.mapper;

import com.springleaf.gotodo.model.entity.Memo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemoMapper {

    /**
     * 创建便签
     */
    int saveMemo(Memo memo);

    /**
     * 获取所有内容不为空且未删除的便签
     */
    List<Memo> getMemoList();

    /**
     * 根据便签id获取便签
     */
    Memo getMemoByMemoId(Long memoId);

    /**
     * 根据便签id删除便签
     */
    int deleteMemoById(Long memoId);

    /**
     * 更新便签
     */
    int updateMemo(Memo updateMemo);

    /**
     * 删除空便签
     * @return 删除的数量
     */
    int cleanEmptyMemo();
}
