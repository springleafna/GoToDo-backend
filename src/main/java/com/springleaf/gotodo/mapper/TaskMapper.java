package com.springleaf.gotodo.mapper;

import com.springleaf.gotodo.model.entity.Task;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TaskMapper {
    /**
     * 新增任务
     */
    int saveTask(Task task);

    /**
     * 删除任务
     */
    int deleteTaskByTaskId(Integer taskId);

    /**
     * 根据任务列表ID获取所有任务
     */
    List<Task> getTaskList(Integer listId);
}
