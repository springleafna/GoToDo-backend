package com.springleaf.gotodo.mapper;

import com.springleaf.gotodo.model.entity.Task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Mapper
public interface TaskMapper {
    /**
     * 新增任务
     */
    int saveTask(Task task);

    /**
     * 删除任务
     */
    int deleteTaskByTaskId(Long taskId);

    /**
     * 根据任务分类ID获取所有任务
     */
    List<Task> getTaskListByCategoryId(Long categoryId);

    /**
     * 更新任务状态（完成/未完成）
     */
    int updateTaskCompleted(@Param("taskId") Long taskId, @Param("completed") Integer completed);

    /**
     * 更新任务基本信息（标题、备注、时间等）
     */
    int updateTaskInfo(Task task);

    /**
     * 根据任务ID获取任务
     */
    Task getTaskByTaskId(Long taskId);

    /**
     * 根据任务ID集合批量获取任务
     */
    List<Task> getTaskByTaskIds(Set<Long> taskIds);

    /**
     * 获取任务提醒到期的任务
     */
    List<Task> findByReminderTimeBeforeAndReminderSentFalse(LocalDateTime now);
}
