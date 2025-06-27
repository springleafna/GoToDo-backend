package com.springleaf.gotodo.service;

import com.springleaf.gotodo.common.Result;
import com.springleaf.gotodo.model.dto.TaskSaveDTO;
import com.springleaf.gotodo.model.dto.TaskUpdateDTO;
import com.springleaf.gotodo.model.vo.TaskVO;

import java.io.IOException;
import java.util.List;

public interface TaskService {
    /**
     * 保存任务
     * @param taskSaveDTO 任务新增DTO
     * @return Result<Void>
     */
    Result<Void> saveTask(TaskSaveDTO taskSaveDTO);

    /**
     * 获取任务列表
     * @param categoryId 任务分类ID
     * @return Result<List<TaskVO>>
     */
    Result<List<TaskVO>> listTaskByCategoryId(Long categoryId);

    /**
     * 删除任务
     * @param taskId 任务ID
     * @return Result<Void>
     */
    Result<Void> deleteTask(Long taskId);

    /**
     * 完成/未完成 任务
     * @param taskId 任务ID
     * @param status 完成状态(false:未完成 true:已完成)
     * @return Result<Void>
     */
    Result<Void> completedTask(Long taskId, Boolean status);

    /**
     * 更新任务基本信息（标题、备注、时间等）
     * @param taskUpdateDTO 任务更新DTO
     * @return Result<TaskVO>
     */
    Result<Void> updateTaskInfo(TaskUpdateDTO taskUpdateDTO);

    /**
     * 执行定时任务（任务提醒）
     */
    void checkAndSendReminders() throws IOException;

    /**
     * 获取任务详细信息
     * @param taskId 任务ID
     * @return Result<TaskVO>
     */
    Result<TaskVO> getTaskDetail(Long taskId);
}
