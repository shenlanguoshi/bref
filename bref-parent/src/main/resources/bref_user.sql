SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `sys_corporation`
-- ----------------------------
DROP TABLE IF EXISTS `sys_corporation`;
CREATE TABLE `sys_corporation` (
  `id` char(36) NOT NULL COMMENT '主键id',
  `supervisor_id` char(36) NULL DEFAULT NULL COMMENT '主管id',
  `name` varchar(50) NOT NULL COMMENT '组织机构名称',
  `parent_id` char(36) NULL DEFAULT NULL COMMENT '上级组织机构id',
  `create_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除 1：已删除 0：正常',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX (`name`) USING BTREE,
  INDEX (`parent_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '组织机构表' ROW_FORMAT = Dynamic;

-- ----------------------------
--  Table structure for `sys_corporation_permission`
-- ----------------------------
DROP TABLE IF EXISTS `sys_corporation_permission`;
CREATE TABLE `sys_corporation_permission` (
  `corporation_id` char(36) NOT NULL COMMENT '组织机构id',
  `permission_id` char(36) NOT NULL COMMENT '权限id',
  PRIMARY KEY (`corporation_id`, `permission_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '组织机构权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
--  Table structure for `sys_corporation_work`
-- ----------------------------
DROP TABLE IF EXISTS `sys_corporation_work`;
CREATE TABLE `sys_corporation_work` (
  `id` char(36) NOT NULL COMMENT '主键id',
  `corporation_id` char(36) NOT NULL COMMENT '组织机构id',
  `name` varchar(50) NOT NULL COMMENT '岗位名称',
  `create_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除 1：已删除 0：正常',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX (`corporation_id`) USING BTREE,
  INDEX (`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '岗位表' ROW_FORMAT = Dynamic;

-- ----------------------------
--  Table structure for `sys_corporation_work_permission`
-- ----------------------------
DROP TABLE IF EXISTS `sys_corporation_work_permission`;
CREATE TABLE `sys_corporation_work_permission` (
  `corporation_work_id` char(36) NOT NULL COMMENT '岗位id',
  `permission_id` char(36) NOT NULL COMMENT '权限id',
  PRIMARY KEY (`corporation_work_id`, `permission_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '岗位权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_data_schema
-- ----------------------------
DROP TABLE IF EXISTS `sys_data_schema`;
CREATE TABLE `sys_data_schema`  (
  `id` char(36) NOT NULL COMMENT '主键id',
  `corporation_id` char(36) NULL DEFAULT NULL COMMENT '组织机构id',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `entry` varchar(50) NOT NULL COMMENT '标识',
  `priority` int(11) NOT NULL COMMENT '优先级，值越大优先级越高',
  `data_schema` varchar(999) NULL DEFAULT NULL COMMENT '列域',
  `create_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除 1：已删除 0：正常',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX (`entry`) USING BTREE,
  INDEX (`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '列域表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_data_scope
-- ----------------------------
DROP TABLE IF EXISTS `sys_data_scope`;
CREATE TABLE `sys_data_scope`  (
  `id` char(36) NOT NULL COMMENT '主键id',
  `corporation_id` char(36) NULL DEFAULT NULL COMMENT '组织机构id',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `entry` varchar(50) NOT NULL COMMENT '标识',
  `priority` int(11) NOT NULL COMMENT '优先级，值越大优先级越高',
  `create_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除 1：已删除 0：正常',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX (`entry`) USING BTREE,
  INDEX (`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '行域表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_data_scope_item
-- ----------------------------
DROP TABLE IF EXISTS `sys_data_scope_item`;
CREATE TABLE `sys_data_scope_item`  (
  `id` char(36) NOT NULL COMMENT '主键id',
  `data_scope_id` char(36) NOT NULL COMMENT '行域id',
  `column_name` varchar(255) NOT NULL COMMENT '列名',
  `operator` varchar(20) NOT NULL DEFAULT '=' COMMENT '操作符',
  `data_scope` varchar(999) NULL DEFAULT NULL COMMENT '行域',
  `create_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除 1：已删除 0：正常',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX KEY (`data_scope_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '行域项目表' ROW_FORMAT = Dynamic;

-- ----------------------------
--  Table structure for `sys_job_position`
-- ----------------------------
DROP TABLE IF EXISTS `sys_job_position`;
CREATE TABLE `sys_job_position` (
  `id` char(36) NOT NULL COMMENT '主键id',
  `name` varchar(50) NOT NULL COMMENT '职务名称',
  `create_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除 1：已删除 0：正常',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX (`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '职务表' ROW_FORMAT = Dynamic;

-- ----------------------------
--  Table structure for `sys_job_position_permission`
-- ----------------------------
DROP TABLE IF EXISTS `sys_job_position_permission`;
CREATE TABLE `sys_job_position_permission` (
  `job_position_id` char(36) NOT NULL COMMENT '职务id',
  `permission_id` char(36) NOT NULL COMMENT '权限id',
  PRIMARY KEY (`job_position_id`, `permission_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '职务权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_module
-- ----------------------------
DROP TABLE IF EXISTS `sys_module`;
CREATE TABLE `sys_module`  (
  `id` char(36) NOT NULL COMMENT '主键id',
  `title` varchar(50) NULL DEFAULT NULL COMMENT '模块名',
  `parent_id` char(36) NULL DEFAULT NULL COMMENT '父模块id',
  `uri` varchar(255) NULL DEFAULT NULL COMMENT '模块连接地址',
  `icon` varchar(255)  NULL DEFAULT NULL COMMENT '模块图标',
  `sort` int(11) NOT NULL DEFAULT 999 COMMENT '模块的排序',
  `is_menu` tinyint(1) NOT NULL DEFAULT 0 COMMENT '模块是否是菜单',
  `controls` varchar(255) NULL DEFAULT NULL COMMENT '前端控件',
  `create_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除 1：已删除 0：正常',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX (`title`) USING BTREE,
  INDEX (`parent_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '模块表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `id` char(36) NOT NULL COMMENT '主键id',
  `corporation_id` char(36) NULL DEFAULT NULL COMMENT '组织机构id',
  `name` varchar(50) NOT NULL COMMENT '权限名',
  `create_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除 1：已删除 0：正常',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX (`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
--  Table structure for `sys_permission_data_schema`
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission_data_schema`;
CREATE TABLE `sys_permission_data_schema` (
  `permission_id` char(36) NOT NULL COMMENT '权限id',
  `data_schema_id` char(36) NOT NULL COMMENT '列域id',
  PRIMARY KEY (`permission_id`, `data_schema_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '权限列域表（数据权限-列权限）' ROW_FORMAT = Dynamic;

-- ----------------------------
--  Table structure for `sys_permission_data_scope`
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission_data_scope`;
CREATE TABLE `sys_permission_data_scope` (
  `permission_id` char(36) NOT NULL COMMENT '权限id',
  `data_scope_id` char(36) NOT NULL COMMENT '行域id',
  PRIMARY KEY (`permission_id`, `data_scope_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '权限行域表（数据权限-行权限）' ROW_FORMAT = Dynamic;

-- ----------------------------
--  Table structure for `sys_permission_module`
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission_module`;
CREATE TABLE `sys_permission_module` (
  `permission_id` char(36) NOT NULL COMMENT '权限id',
  `module_id` char(36) NOT NULL COMMENT '模块id',
  PRIMARY KEY (`permission_id`, `module_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '权限模块表（功能权限）' ROW_FORMAT = Dynamic;

-- ----------------------------
--  Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` char(36) NOT NULL COMMENT '主键id',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `enabled` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否启用 1：已启用 0：禁止',
  `create_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除 1：已删除 0：正常',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_sys_user_username` (`username`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
--  Table structure for `sys_user_corporation`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_corporation`;
CREATE TABLE `sys_user_corporation` (
  `user_id` char(36) NOT NULL COMMENT '用户id',
  `corporation_id` char(36) NOT NULL COMMENT '组织机构id',
  PRIMARY KEY (`user_id`, `corporation_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户组织机构表' ROW_FORMAT = Dynamic;

-- ----------------------------
--  Table structure for `sys_user_corporation_work`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_corporation_work`;
CREATE TABLE `sys_user_corporation_work` (
  `user_id` char(36) NOT NULL COMMENT '用户id',
  `corporation_work_id` char(36) NOT NULL COMMENT '岗位id',
  PRIMARY KEY (`user_id`, `corporation_work_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户岗位表' ROW_FORMAT = Dynamic;

-- ----------------------------
--  Table structure for `sys_user_job_position`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_job_position`;
CREATE TABLE `sys_user_job_position` (
  `user_id` char(36) NOT NULL COMMENT '用户id',
  `job_position_id` char(36) NOT NULL COMMENT '职务id',
  PRIMARY KEY (`user_id`, `job_position_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户职务表' ROW_FORMAT = Dynamic;

-- ----------------------------
--  Table structure for `sys_user_permission`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_permission`;
CREATE TABLE `sys_user_permission` (
  `user_id` char(36) NOT NULL COMMENT '用户id',
  `permission_id` char(36) NOT NULL COMMENT '权限id',
  PRIMARY KEY (`user_id`, `permission_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户权限表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;