package com.springleaf.gotodo.service.impl;

import com.springleaf.gotodo.common.Result;
import com.springleaf.gotodo.enums.DeletedStatusEnum;
import com.springleaf.gotodo.enums.PinnedStatusEnum;
import com.springleaf.gotodo.mapper.MemoMapper;
import com.springleaf.gotodo.model.dto.MemoSaveDTO;
import com.springleaf.gotodo.model.dto.MemoUpdateDTO;
import com.springleaf.gotodo.model.dto.MemoUpdatePinnedDTO;
import com.springleaf.gotodo.model.entity.Memo;
import com.springleaf.gotodo.model.vo.MemoVO;
import com.springleaf.gotodo.service.MemoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemoServiceImpl implements MemoService {

    private final MemoMapper memoMapper;

    @Override
    public Result<Long> saveMemo() {
        // 用户点击创建即创建一个空白便签，后续输入内容则更新
        Memo memo = new Memo();
        memo.setTitle(null);
        memo.setContent(null);
        memo.setPinned(PinnedStatusEnum.UN_PINNED.getCode());
        memo.setDeleted(DeletedStatusEnum.NORMAL.getCode());
        if (memoMapper.saveMemo(memo) == 0) {
            return Result.error("创建便签失败");
        }
        return Result.success(memo.getMemoId());
    }

    @Override
    public Result<List<MemoVO>> getMemoList() {
        List<Memo> memoList = memoMapper.getMemoList();
        if (memoList == null || memoList.isEmpty()) {
            return Result.success(Collections.emptyList());
        }
        List<MemoVO> memoVOList = new ArrayList<>();
        for (Memo memo : memoList) {
            MemoVO memoVO = new MemoVO();
            memoVO.setMemoId(memo.getMemoId());
            memoVO.setTitle(memo.getTitle());
            memoVO.setContent(memo.getContent());
            memoVO.setPinned(memo.getPinned());
            memoVO.setCreateTime(memo.getCreateTime());
            memoVO.setUpdateTime(memo.getUpdateTime());
            memoVOList.add(memoVO);
        }
        return Result.success(memoVOList);
    }

    @Override
    public Result<Void> updateMemo(MemoUpdateDTO memoUpdateDTO) {
        if (memoUpdateDTO == null || memoUpdateDTO.getMemoId() == null) {
            return Result.error("便签ID不能为空");
        }
        Memo updateMemo = new Memo();
        updateMemo.setMemoId(memoUpdateDTO.getMemoId());
        updateMemo.setTitle(memoUpdateDTO.getTitle());
        updateMemo.setContent(memoUpdateDTO.getContent());
        if (memoUpdateDTO.getPinned() == null) {
            updateMemo.setPinned(PinnedStatusEnum.UN_PINNED.getCode());
        } else {
            updateMemo.setPinned(memoUpdateDTO.getPinned());
        }
        if (memoMapper.updateMemo(updateMemo) == 0) {
            return Result.error("更新便签失败");
        }
        return Result.success();
    }

    @Override
    public Result<Void> deleteMemo(Long memoId) {
        if (memoId == null) {
            return Result.error("便签ID不能为空");
        }
        if (memoMapper.getMemoByMemoId(memoId) == null) {
            return Result.error("便签不存在");
        }
        if (memoMapper.deleteMemoById(memoId) == 0) {
            return Result.error("删除便签失败");
        }
        return Result.success();
    }

    @Override
    public Result<MemoVO> getMemoDetail(Long memoId) {
        if (memoId == null) {
            return Result.error("便签ID不能为空");
        }
        Memo memo = memoMapper.getMemoByMemoId(memoId);
        if (memo == null ) {
            return Result.error("该便签不存在");
        }
        MemoVO memoVO = new MemoVO();
        memoVO.setMemoId(memo.getMemoId());
        memoVO.setTitle(memo.getTitle());
        memoVO.setContent(memo.getContent());
        memoVO.setPinned(memo.getPinned());
        memoVO.setCreateTime(memo.getCreateTime());
        memoVO.setUpdateTime(memo.getUpdateTime());
        return Result.success(memoVO);
    }

    @Override
    public void cleanEmptyMemo() {
        int sum =  memoMapper.cleanEmptyMemo();
        log.info("定时任务-删除的空便签数量：{}", sum);
    }

    @Override
    public Result<Void> addMemo(MemoSaveDTO memoSaveDTO) {
        if (memoSaveDTO.getContent().isEmpty() && memoSaveDTO.getTitle().isEmpty()) {
            return Result.success();
        }
        Memo memo = new Memo();
        memo.setContent(memoSaveDTO.getContent().isEmpty() ? null : memoSaveDTO.getContent());
        memo.setTitle(memoSaveDTO.getTitle().isEmpty() ? null : memoSaveDTO.getTitle());
        memo.setPinned(PinnedStatusEnum.UN_PINNED.getCode());
        memo.setDeleted(DeletedStatusEnum.NORMAL.getCode());
        memoMapper.saveMemo(memo);
        return Result.success();
    }

    @Override
    public Result<Void> updatePinnedMemo(MemoUpdatePinnedDTO memoUpdatePinnedDTO) {
        if (memoUpdatePinnedDTO == null) {
            return Result.error("置顶/取消置顶 参数不能为空");
        }
        if (memoUpdatePinnedDTO.getMemoId() == null) {
            return Result.error("便签ID不能为空");
        }
        if (memoUpdatePinnedDTO.getPinned() == null) {
            return Result.error("是否置顶不能为空");
        }
        Integer pinned = memoUpdatePinnedDTO.getPinned() ? 1 : 0;
        if (memoMapper.updatePinnedMemo(memoUpdatePinnedDTO.getMemoId(), pinned) == 0) {
            return Result.error("更新置顶状态失败");
        }
        return Result.success();
    }
}
