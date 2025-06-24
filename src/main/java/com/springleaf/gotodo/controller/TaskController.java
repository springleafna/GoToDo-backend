package com.springleaf.gotodo.controller;

import com.springleaf.gotodo.common.Result;
import com.springleaf.gotodo.model.dto.TaskSaveDTO;
import com.springleaf.gotodo.model.dto.TaskUpdateDTO;
import com.springleaf.gotodo.model.vo.TaskVO;
import com.springleaf.gotodo.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    /**
     * 创建任务
     */
    @PostMapping("/save")
    public Result<Void> saveTask(@RequestBody TaskSaveDTO taskSaveDTO) {
        return taskService.saveTask(taskSaveDTO);
    }

    /**
     * 获取某个任务分类下的所有任务
     */
    @GetMapping("/list")
    public Result<List<TaskVO>> listTaskByCategoryId(@RequestParam Long categoryId) {
        return taskService.listTaskByCategoryId(categoryId);
    }

    /**
     * 删除任务
     */
    @PutMapping("/delete")
    public Result<Void> deleteTask(@RequestParam Long taskId) {
        return taskService.deleteTask(taskId);
    }

    /**
     * 完成/未完成 任务
     */
    @PutMapping("/completed")
    public Result<Void> completedTask(@RequestParam Long taskId, @RequestParam Boolean status) {
        return taskService.completedTask(taskId, status);
    }

    /**
     * 更新任务基本信息（标题、备注、时间等）
     */
    @PutMapping("/update")
    public Result<TaskVO> updateTaskInfo(@RequestBody TaskUpdateDTO taskUpdateDTO) {
        return taskService.updateTaskInfo(taskUpdateDTO);
    }
}
