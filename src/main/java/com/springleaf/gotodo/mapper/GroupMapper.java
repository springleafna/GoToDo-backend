package com.springleaf.gotodo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GroupMapper {
    /**
     * 新增任务组
     */
    int saveGroup(String groupName);

    /**
     * 删除任务组
     */
    int deleteGroup(Long groupId);

    /**
     * 修改任务组名称
     */
    int updateGroupName(@Param("groupId") Long groupId, @Param("groupName") String groupName);
}
