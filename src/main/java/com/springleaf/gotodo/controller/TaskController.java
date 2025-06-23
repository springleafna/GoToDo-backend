package com.springleaf.gotodo.controller;

import com.springleaf.gotodo.common.Result;
import com.springleaf.gotodo.model.dto.TaskSaveDTO;
import com.springleaf.gotodo.model.vo.TaskVO;
import com.springleaf.gotodo.service.TaskService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    
    @PostMapping("/save")
    public Result<Void> saveTask(@RequestBody TaskSaveDTO taskSaveDTO) {
        return taskService.saveTask(taskSaveDTO);
    }
    
    @GetMapping("/list")
    public Result<List<TaskVO>> listTaskByCategoryId(@RequestParam Long categoryId) {
        return taskService.listTaskByCategoryId(categoryId);
    }
    
    @PutMapping("/delete")
    public Result<Void> deleteTask(@RequestParam Long taskId) {
        return taskService.deleteTask(taskId);
    }
}
