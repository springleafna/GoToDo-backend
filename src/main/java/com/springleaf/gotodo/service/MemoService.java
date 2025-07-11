package com.springleaf.gotodo.service;

import com.springleaf.gotodo.common.Result;
import com.springleaf.gotodo.model.dto.MemoSaveDTO;
import com.springleaf.gotodo.model.dto.MemoUpdateDTO;
import com.springleaf.gotodo.model.dto.MemoUpdatePinnedDTO;
import com.springleaf.gotodo.model.vo.MemoVO;

import java.util.List;

public interface MemoService {
    /**
     * 保存便签
     * @return 新增的便签ID
     */
    Result<Long> saveMemo();

    /**
     * 获取便签列表
     * @return 便签列表
     */
    Result<List<MemoVO>> getMemoList();

    /**
     * 更新便签
     * @param memoUpdateDTO 待更新的便签信息
     * @return Result<Void>
     */
    Result<Void> updateMemo(MemoUpdateDTO memoUpdateDTO);

    /**
     * 删除便签
     * @param memoId 待删除的便签ID
     * @return Result<Void>
     */
    Result<Void> deleteMemo(Long memoId);

    /**
     * 获取便签详情
     * @param memoId 便签ID
     * @return Result<MemoVO>
     */
    Result<MemoVO> getMemoDetail(Long memoId);

    /**
     * 清理空便签定时任务
     */
    void cleanEmptyMemo();

    /**
     * 当用户离开界面且内容不为空时触发便签内容同步到数据库
     * @param memoSaveDTO 待保存的便签信息
     * @return Result<Void>
     */
    Result<Void> addMemo(MemoSaveDTO memoSaveDTO);

    /**
     * 置顶/取消置顶便签
     * @param memoUpdatePinnedDTO 待更新的便签信息
     * @return Result<Void>
     */
    Result<Void> updatePinnedMemo(MemoUpdatePinnedDTO memoUpdatePinnedDTO);
}
