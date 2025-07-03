package com.springleaf.gotodo.controller;

import com.springleaf.gotodo.common.Result;
import com.springleaf.gotodo.model.dto.MemoSaveDTO;
import com.springleaf.gotodo.model.dto.MemoUpdateDTO;
import com.springleaf.gotodo.model.vo.MemoVO;
import com.springleaf.gotodo.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/memo")
public class MemoController {
    
    private final MemoService memoService;

    /**
     * 创建便签
     */
    @PostMapping("/save")
    public Result<Long> saveMemo() {
        return memoService.saveMemo();
    }

    /**
     * 当用户离开界面且内容不为空时触发便签内容同步到数据库
     */
    @PostMapping("add")
    public Result<Void> addMemo(@RequestBody MemoSaveDTO memoSaveDTO) {
        return memoService.addMemo(memoSaveDTO);
    }

    /**
     * 获取不为空的便签列表
     */
    @GetMapping("/list")
    public Result<List<MemoVO>> getMemoList() {
        return memoService.getMemoList();
    }

    /**
     * 更新便签
     */
    @PutMapping("/update")
    public Result<Void> updateMemo(@RequestBody MemoUpdateDTO memoUpdateDTO) {
        return memoService.updateMemo(memoUpdateDTO);
    }

    /**
     * 删除便签
     */
    @DeleteMapping("/delete/{memoId}")
    public Result<Void> deleteMemo(@PathVariable("memoId") Long memoId) {
        return memoService.deleteMemo(memoId);
    }

    /**
     * 获取便签详情
     */
    @GetMapping("/detail")
    public Result<MemoVO> getMemoDetail(@RequestParam Long memoId) {
        return memoService.getMemoDetail(memoId);
    }
}
