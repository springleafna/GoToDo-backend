package com.springleaf.gotodo.service;

import com.springleaf.gotodo.common.Result;
import com.springleaf.gotodo.model.dto.TaskSaveDTO;
import com.springleaf.gotodo.model.vo.TaskVO;

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
}
