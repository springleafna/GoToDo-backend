package com.springleaf.gotodo.service.impl;

import com.springleaf.gotodo.common.Result;
import com.springleaf.gotodo.enums.CompletedStatusEnum;
import com.springleaf.gotodo.enums.DeletedStatusEnum;
import com.springleaf.gotodo.enums.PriorityEnum;
import com.springleaf.gotodo.mapper.CategoryMapper;
import com.springleaf.gotodo.mapper.CategoryTaskMapper;
import com.springleaf.gotodo.mapper.TaskMapper;
import com.springleaf.gotodo.model.dto.TaskSaveDTO;
import com.springleaf.gotodo.model.entity.Task;
import com.springleaf.gotodo.model.vo.TaskVO;
import com.springleaf.gotodo.service.TaskService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    
    private final TaskMapper taskMapper;
    private final CategoryMapper categoryMapper;
    private final CategoryTaskMapper categoryTaskMapper;
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Void> saveTask(TaskSaveDTO taskSaveDTO) {
        if (taskSaveDTO.getCategoryId() == null) {
            return Result.error("任务分类ID不能为空");
        }
        if (taskSaveDTO.getTitle() == null) {
            return Result.error("任务标题不能为空");
        }
        if (categoryMapper.getCategoryByCategoryId(taskSaveDTO.getCategoryId()) == null) {
            return Result.error("任务分类不存在");
        }
        // 保存新任务
        Task task = new Task();
        task.setTitle(taskSaveDTO.getTitle());
        task.setPriority(PriorityEnum.LOW.getCode());
        task.setCompleted(CompletedStatusEnum.NOT_COMPLETED.getCode());
        task.setDeleted(DeletedStatusEnum.NORMAL.getCode());
        if (taskMapper.saveTask(task) == 0) {
            return Result.error("保存任务失败");
        };
        // 保存任务-任务分类
        if (categoryTaskMapper.saveCategoryTask(taskSaveDTO.getCategoryId(), task.getTaskId()) == 0) {
            return Result.error("保存任务失败");
        }
        return Result.success();
    }

    @Override
    public Result<List<TaskVO>> listTaskByCategoryId(Long categoryId) {
        log.info("查询分类 {} 下的任务列表", categoryId);
        if (categoryId == null) {
            return Result.error("任务分类Id不能为空");
        }
        if (categoryMapper.getCategoryByCategoryId(categoryId) == null) {
            return Result.error("任务分类不存在");
        }
        List<Task> taskList = taskMapper.getTaskListByCategoryId(categoryId);
        if (taskList == null || taskList.isEmpty()) {
            return Result.success(Collections.emptyList());
        }

        return Result.success(taskList.stream()
                .map(this::convertToTaskVO)
                .toList());
    }

    @Override
    public Result<Void> deleteTask(Long taskId) {
        if (taskId == null) {
            return Result.error("任务ID不能为空");
        }
        if (taskMapper.getTaskByTaskId(taskId) == null) {
            return Result.error("任务不存在");
        }
        if (taskMapper.deleteTaskByTaskId(taskId) == 0) {
            return Result.error("删除任务失败");
        }
        return Result.success();
    }

    /**
     * 单个 Task 转换为 TaskVO
     */
    private TaskVO convertToTaskVO(Task task) {
        TaskVO taskVO = new TaskVO();
        taskVO.setTaskId(task.getTaskId());
        taskVO.setTitle(task.getTitle());
        taskVO.setRemark(task.getRemark());
        taskVO.setEndTime(task.getEndTime());
        taskVO.setReminderTime(task.getReminderTime());
        taskVO.setCompleted(task.getCompleted());
        taskVO.setPriority(task.getPriority());
        taskVO.setCreateTime(task.getCreateTime());
        return taskVO;
    }
}
