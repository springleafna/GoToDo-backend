-- 任务主表
CREATE TABLE tb_task (
  task_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '任务ID',
  title VARCHAR(200) NOT NULL COMMENT '任务标题',
  remark TEXT COMMENT '任务备注',
  end_time DATETIME COMMENT '结束时间',
  reminder_time DATETIME COMMENT '提醒时间',
  completed TINYINT DEFAULT 0 COMMENT '完成状态(0:未完成 1:已完成)',
  priority TINYINT DEFAULT 0 COMMENT '优先级(0:低 1:中 2:高)',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  deleted TINYINT DEFAULT 0 COMMENT '删除标记(0:正常 1:删除)'
) ENGINE=InnoDB CHARSET=utf8mb4 COMMENT='任务主表';

-- 任务组表
CREATE TABLE tb_group (
    group_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '任务组ID',
    group_name VARCHAR(100) NOT NULL COMMENT '组名称',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0:正常 1:删除)'
) ENGINE=InnoDB CHARSET=utf8mb4 COMMENT='任务组表';

-- 任务类（存储任务分类信息）
CREATE TABLE tb_category(
    category_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '任务类ID',
    category_name VARCHAR(100) NOT NULL COMMENT '分类名称',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0:正常 1:删除)'
) ENGINE=InnoDB CHARSET=utf8mb4 COMMENT='任务类表';

-- 任务类-任务-关联表
CREATE TABLE tb_category_task (
  category_task_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '任务类-任务-关联ID',
  task_id BIGINT NOT NULL COMMENT '任务ID',
  category_id BIGINT NOT NULL COMMENT '任务类ID',
  sort_order INT DEFAULT 0 COMMENT '任务在任务类内的排序',
  UNIQUE KEY uk_task_category (task_id, category_id)
) ENGINE=InnoDB CHARSET=utf8mb4 COMMENT='任务类-任务-关联表';

-- 任务组-任务类-关联表
CREATE TABLE tb_group_category (
  group_task_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '任务组-任务类-关联ID',
  category_id BIGINT NOT NULL COMMENT '任务类ID',
  group_id BIGINT NOT NULL COMMENT '任务组ID',
  sort_order INT DEFAULT 0 COMMENT '任务类在组内的排序',
  UNIQUE KEY uk_category_group (category_id, group_id)
) ENGINE=InnoDB CHARSET=utf8mb4 COMMENT='任务组-任务类-关联表';

-- 收藏表
CREATE TABLE tb_favorite (
  favorite_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '收藏ID',
  task_id BIGINT NOT NULL UNIQUE COMMENT '任务ID',
  status TINYINT DEFAULT 1 COMMENT '收藏状态(1:已收藏 0:已取消)',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '收藏时间'
) ENGINE=InnoDB CHARSET=utf8mb4 COMMENT='用户收藏任务表';

-- 展示项排序表
CREATE TABLE tb_display_item (
  item_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '展示项ID',
  item_type ENUM('group', 'category') NOT NULL COMMENT '类型(group:组 category:分类)',
  item_ref_id BIGINT NOT NULL COMMENT '关联的 group_id 或 category_id',
  sort_order INT DEFAULT 0 COMMENT '展示顺序',
  UNIQUE KEY uk_item_ref (item_type, item_ref_id)
) ENGINE=InnoDB CHARSET=utf8mb4 COMMENT='展示项排序表（用于组和平级任务类的统一排序）';

-- 便签表
CREATE TABLE tb_memo (
  memo_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '便签ID',
  title VARCHAR(200) COMMENT '便签标题',
  content TEXT COMMENT '便签内容',
  pinned TINYINT DEFAULT 0 COMMENT '是否置顶(0:否 1:是)',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  deleted TINYINT DEFAULT 0 COMMENT '删除标记(0:正常 1:删除)'
) ENGINE=InnoDB CHARSET=utf8mb4 COMMENT='便签表';