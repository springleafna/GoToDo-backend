package com.springleaf.gotodo.service.impl;

import com.springleaf.gotodo.common.Result;
import com.springleaf.gotodo.enums.CompletedStatusEnum;
import com.springleaf.gotodo.enums.DeletedStatusEnum;
import com.springleaf.gotodo.enums.FavoriteStatusEnum;
import com.springleaf.gotodo.enums.PriorityEnum;
import com.springleaf.gotodo.feishu.FeiShu;
import com.springleaf.gotodo.mapper.CategoryMapper;
import com.springleaf.gotodo.mapper.CategoryTaskMapper;
import com.springleaf.gotodo.mapper.FavoriteMapper;
import com.springleaf.gotodo.mapper.TaskMapper;
import com.springleaf.gotodo.model.dto.TaskSaveDTO;
import com.springleaf.gotodo.model.dto.TaskUpdateDTO;
import com.springleaf.gotodo.model.entity.CategoryTask;
import com.springleaf.gotodo.model.entity.Favorite;
import com.springleaf.gotodo.model.entity.Task;
import com.springleaf.gotodo.model.vo.TaskVO;
import com.springleaf.gotodo.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    
    private final TaskMapper taskMapper;
    private final CategoryMapper categoryMapper;
    private final CategoryTaskMapper categoryTaskMapper;
    private final FavoriteMapper favoriteMapper;
    private final FeiShu feiShu;
    
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
        // 需要获取当前最大的sortOrder的值，+1则为新插入的任务分类的排序号
        int sortOrder = categoryTaskMapper.getMaxSortOrderByCategoryId(taskSaveDTO.getCategoryId());
        CategoryTask categoryTask = new CategoryTask();
        categoryTask.setCategoryId(taskSaveDTO.getCategoryId());
        categoryTask.setTaskId(task.getTaskId());
        categoryTask.setSortOrder(sortOrder + 1);
        if (categoryTaskMapper.saveCategoryTask(categoryTask) == 0) {
            return Result.error("建立分类任务关系失败");
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
        
        // 根据分类ID从关联表中获取分类-任务关联列表
        List<CategoryTask> categoryTaskList = categoryTaskMapper.getCategoryTaskListByCategoryId(categoryId);
        if (categoryTaskList == null || categoryTaskList.isEmpty()) {
            return Result.success(Collections.emptyList());
        }
        // 获取任务ID集合
        Set<Long> TaskIds = categoryTaskList.stream()
                .map(CategoryTask::getTaskId)
                .collect(Collectors.toSet());
        List<Task> taskList = taskMapper.getTaskByTaskIds(TaskIds);
        // 将任务信息转换为Map，便于快速查找
        Map<Long, Task> taskMap = taskList.stream()
                .collect(Collectors.toMap(Task::getTaskId, Function.identity(), (existing, replacement) -> existing));
        // 构建返回的TaskVO列表
        List<TaskVO> taskVOList = categoryTaskList.stream()
                .map(categoryTask -> convertToTaskVO(taskMap.get(categoryTask.getTaskId()), categoryTask.getSortOrder()))
                .toList();

        return Result.success(taskVOList);
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
    public Result<Void> updateTaskInfo(TaskUpdateDTO taskUpdateDTO) {
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
        return Result.success();
    }

    @Override
    public void checkAndSendReminders() {
        LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);
        log.info("检查并发送任务提醒，当前时间{}", now);
        List<Task> remindTask = taskMapper.findByReminderTimeBeforeAndReminderSentFalse(now);
        if (remindTask == null || remindTask.isEmpty()) {
            log.info("没有需要发送的任务提醒");
            return;
        }
        for (Task task : remindTask) {
            try {
                String title = task.getTitle();
                String categoryName = categoryMapper.getCategoryNameByTaskId(task.getTaskId());
                String remark = task.getRemark() == null || task.getRemark().isEmpty() ? "无备注" : task.getRemark();
                String reminderTime = task.getReminderTime().toString().replace("T", " ");
                String endTime = task.getEndTime() != null ? task.getEndTime().toString().replace("T", " ") : "无截止时间";

                feiShu.sendTemplateMessage(title, categoryName, remark, reminderTime, endTime);
                // 更新任务提醒发送状态
                taskMapper.updateTaskReminderSent(task.getTaskId());
                log.info("成功发送飞书任务提醒通知：{}", task);
            } catch (Exception e) {
                log.error("发送任务提醒通知失败，任务ID：{}，错误信息：{}", task.getTaskId(), e.getMessage(), e);
            }
        }
    }

    @Override
    public Result<TaskVO> getTaskDetail(Long taskId) {
        if (taskId == null) {
            return Result.error("任务ID不能为空");
        }
        Task task = taskMapper.getTaskByTaskId(taskId);
        if (task == null) {
            return Result.error("该任务不存在");
        }
        TaskVO taskVO = convertToTaskVO(task, categoryTaskMapper.getSortOrderByTaskId(taskId));
        return Result.success(taskVO);
    }

    /**
     * 单个 Task 转换为 TaskVO
     */
    private TaskVO convertToTaskVO(Task task, Integer sortOrder) {
        TaskVO taskVO = new TaskVO();
        taskVO.setTaskId(task.getTaskId());
        taskVO.setTitle(task.getTitle());
        taskVO.setRemark(task.getRemark());
        taskVO.setEndTime(task.getEndTime());
        taskVO.setReminderTime(task.getReminderTime());
        taskVO.setCompleted(task.getCompleted());
        taskVO.setPriority(task.getPriority());
        taskVO.setCreateTime(task.getCreateTime());
        taskVO.setSortOrder(sortOrder);
        
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
