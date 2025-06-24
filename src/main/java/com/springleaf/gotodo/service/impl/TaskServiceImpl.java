package com.springleaf.gotodo.service.impl;

import com.springleaf.gotodo.common.Result;
import com.springleaf.gotodo.enums.CompletedStatusEnum;
import com.springleaf.gotodo.enums.DeletedStatusEnum;
import com.springleaf.gotodo.enums.FavoriteStatusEnum;
import com.springleaf.gotodo.enums.PriorityEnum;
import com.springleaf.gotodo.mapper.CategoryMapper;
import com.springleaf.gotodo.mapper.CategoryTaskMapper;
import com.springleaf.gotodo.mapper.FavoriteMapper;
import com.springleaf.gotodo.mapper.TaskMapper;
import com.springleaf.gotodo.model.dto.TaskSaveDTO;
import com.springleaf.gotodo.model.dto.TaskUpdateDTO;
import com.springleaf.gotodo.model.entity.Favorite;
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
    private final FavoriteMapper favoriteMapper;
    
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
        }
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

    @Override
    public Result<Void> completedTask(Long taskId, Boolean status) {
        if (taskId == null) {
            return Result.error("任务ID不能为空");
        }
        if (status == null) {
            return Result.error("完成状态不能为空");
        }
        // 获取任务状态（true->1   false->0）
        Integer completed = status ? CompletedStatusEnum.COMPLETED.getCode() : CompletedStatusEnum.NOT_COMPLETED.getCode();
        Task task = taskMapper.getTaskByTaskId(taskId);
        if (task == null) {
            return Result.error("任务不存在");
        }
        if (task.getCompleted().equals(completed)) {
            return Result.success();
        }
        if (taskMapper.updateTaskCompleted(taskId, completed) == 0) {
            return Result.error("更新任务状态失败");
        }
        return Result.success();
    }

    @Override
    public Result<TaskVO> updateTaskInfo(TaskUpdateDTO taskUpdateDTO) {
        if (taskUpdateDTO == null) {
            return Result.error("任务更新信息不能为空");
        }
        Long taskId = taskUpdateDTO.getTaskId();
        if (taskId == null) {
            return Result.error("任务ID不能为空");
        }
        if (taskMapper.getTaskByTaskId(taskId) == null) {
            return Result.error("任务不存在");
        }
        Task task = new Task();
        task.setTaskId(taskUpdateDTO.getTaskId());
        task.setTitle(taskUpdateDTO.getTitle());
        task.setRemark(taskUpdateDTO.getRemark());
        task.setEndTime(taskUpdateDTO.getEndTime());
        task.setReminderTime(taskUpdateDTO.getReminderTime());
        task.setPriority(taskUpdateDTO.getPriority());
        
        if (taskMapper.updateTaskInfo(task) == 0) {
            return Result.error("更新任务信息失败");
        }
        return Result.success(convertToTaskVO(taskMapper.getTaskByTaskId(taskId)));
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
        
        // 判断该任务是否被收藏
        Favorite favoriteByTaskId = favoriteMapper.getFavoriteByTaskId(task.getTaskId());
        if (favoriteByTaskId == null) {
            taskVO.setFavorite(FavoriteStatusEnum.CANCELLED.getCode());
            return taskVO;
        }
        taskVO.setFavorite(favoriteByTaskId.getStatus());
        return taskVO;
    }
}
