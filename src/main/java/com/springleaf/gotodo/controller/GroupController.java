package com.springleaf.gotodo.controller;

import com.springleaf.gotodo.common.Result;
import com.springleaf.gotodo.model.dto.CategorySortDTO;
import com.springleaf.gotodo.model.vo.CategoryVO;
import com.springleaf.gotodo.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/group")
@RequiredArgsConstructor
public class GroupController {
    
    private final GroupService groupService;

    /**
     * 将一个任务类移动到某个任务组下
     */
    @PutMapping("/move")
    public Result<Void> moveCategoryToGroup
            (@RequestParam("categoryId") Long categoryId, @RequestParam("groupId") Long groupId) {
        return groupService.moveCategoryToGroup(categoryId, groupId);
    }

    /**
     * 创建任务组
     */
    @PostMapping("/save")
    public Result<Void> saveGroup(@RequestParam String groupName) {
        return groupService.saveGroup(groupName);
    }

    /**
     * 任务类排序
     */
    @PutMapping("/sort")
    public Result<Void> sortCategory(@RequestBody CategorySortDTO taskSortDTO) {
        return groupService.sortCategory(taskSortDTO.getGroupId(), taskSortDTO.getCategoryIds());
    }

    /**
     * 获取某个任务组下的所有任务类
     */
    @GetMapping("/list")
    public Result<List<CategoryVO>> listGroupCategory(@RequestParam("groupId") Long groupId) {
        return groupService.listCategoryByGroup(groupId);
    }
}
