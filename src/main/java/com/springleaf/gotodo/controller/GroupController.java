package com.springleaf.gotodo.controller;

import com.springleaf.gotodo.common.Result;
import com.springleaf.gotodo.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
public class GroupController {
    
    private final GroupService groupService;

    /**
     * 将一个任务类移动到某个任务组下
     */
    @PutMapping("/remove")
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
}
