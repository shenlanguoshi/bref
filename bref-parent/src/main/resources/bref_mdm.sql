SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `mdm_company`
-- ----------------------------
DROP TABLE IF EXISTS `mdm_company`;
CREATE TABLE `mdm_company` (
  `id` varchar(36) NOT NULL COMMENT '主键id',
  `name` varchar(50) NOT NULL COMMENT '公司名称',
  `create_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` boolean NOT NULL DEFAULT 0 COMMENT '是否删除 1：已删除 0：正常',
  `parent_id` varchar(36) NULL DEFAULT NULL COMMENT '母公司名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '公司表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;