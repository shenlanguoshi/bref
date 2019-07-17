-- ----------------------------
-- Records of sys_corporation
-- ----------------------------
INSERT INTO `sys_corporation` VALUES ('20190104185140053c4931524898e4124808', '/20190104185140053c4931524898e4124808', '201901041851125540e61bf209e174987a22', 'bref', '', '2019-01-04 18:51:40', NULL, 0);

-- ----------------------------
-- Records of sys_corporation_permission
-- ----------------------------
INSERT INTO `sys_corporation_permission` VALUES ('20190104185140053c4931524898e4124808', 'bref:admin');

-- ----------------------------
-- Records of sys_corporation_work
-- ----------------------------
INSERT INTO `sys_corporation_work` VALUES ('201901041852557223749dd8f8f784361b87', '20190104185140053c4931524898e4124808', 'CEO', '2019-01-04 18:52:55', NULL, 0);

-- ----------------------------
-- Records of sys_corporation_work_permission
-- ----------------------------
INSERT INTO `sys_corporation_work_permission` VALUES ('201901041852557223749dd8f8f784361b87', 'bref:admin');

-- ----------------------------
-- Records of sys_job_position
-- ----------------------------
INSERT INTO `sys_job_position` VALUES ('201901041855027160e4a050f11564210bc2', '董事长', '2019-01-04 18:55:02', NULL, 0);

-- ----------------------------
-- Records of sys_job_position_permission
-- ----------------------------
INSERT INTO `sys_job_position_permission` VALUES ('201901041855027160e4a050f11564210bc2', 'bref:admin');

-- ----------------------------
-- Records of sys_module
-- ----------------------------
INSERT INTO `sys_module` VALUES ('201811301437346880aa713d544314c2082a', '个人资料', NULL, '/info', 'icon-iframe', 1, 1, 'views/admin/user/infoOld', '2018-11-30 14:37:34', '2018-12-22 15:30:15', 0);
INSERT INTO `sys_module` VALUES ('20181130143833494f0a4bddb5cae4bdb97b', '用户管理', NULL, '/user', 'icon-quanxianguanli', 2, 1, 'views/admin/user/index', '2018-11-30 14:38:33', '2018-12-22 15:30:09', 0);
INSERT INTO `sys_module` VALUES ('20181130143914515642d29b24f624989bd2', '组织机构管理', NULL, '/corporation', 'icon-caidanguanli', 3, 1, 'views/admin/corporation/index', '2018-11-30 14:39:14', '2018-12-22 15:31:40', 0);
INSERT INTO `sys_module` VALUES ('20181130143932417b05fb6d5961240188f0', '岗位管理', NULL, '/corporationWork', 'icon-qingchuhuancun', 4, 1, 'views/admin/corporationWork/index', '2018-11-30 14:39:32', '2018-12-22 15:32:41', 0);
INSERT INTO `sys_module` VALUES ('20181130143948813f3ec2564f91b4e708eb', '职务管理', NULL, '/jobPosition', 'icon-gtsquanjushiwufuwuGTS', 5, 1, 'views/admin/jobPosition/index', '2018-11-30 14:39:48', '2018-12-22 15:33:13', 0);
INSERT INTO `sys_module` VALUES ('201811301440304631badc9bf63494f5bbfd', '权限管理', NULL, '/permission', 'icon-denglvlingpai', 6, 1, 'Layout', '2018-11-30 14:40:30', '2018-12-24 17:24:07', 0);
INSERT INTO `sys_module` VALUES ('20181130144319174a19f72479a2847aa88f', '模块管理', '201811301440304631badc9bf63494f5bbfd', 'moduleTree/index', 'icon-web-icon-', 101, 1, 'views/admin/permission/module/treeIndex', '2018-11-30 14:43:19', '2018-12-24 18:17:27', 0);
INSERT INTO `sys_module` VALUES ('2018113014440502380eb92b8d0544a9bbe7', '数据权限-行', '201811301440304631badc9bf63494f5bbfd', 'dataScope', 'icon-yanzhengma', 102, 1, 'views/admin/permission/dataScope/index', '2018-11-30 14:44:05', '2018-12-25 09:35:52', 0);
INSERT INTO `sys_module` VALUES ('20181130144430908b1beec8772c949fa8fe', '数据权限-列', '201811301440304631badc9bf63494f5bbfd', 'dataSchema', 'icon-tubiao', 103, 1, 'views/admin/permission/dataSchema/index', '2018-11-30 14:44:30', '2018-12-25 09:36:00', 0);
INSERT INTO `sys_module` VALUES ('20181130144835257cc172c42404b4c69a9c', '新增', '20181130143833494f0a4bddb5cae4bdb97b', 'module:user:insert', NULL, 1, 0, NULL, '2018-11-30 14:48:35', '2018-11-30 14:49:18', 0);
INSERT INTO `sys_module` VALUES ('20181130144957151d62051985aaa4acbb10', '删除', '20181130143833494f0a4bddb5cae4bdb97b', 'module:user:delete', NULL, 2, 0, NULL, '2018-11-30 14:49:57', NULL, 0);
INSERT INTO `sys_module` VALUES ('20181130145046663d2d99ac9be294d3e8d3', '修改', '20181130143833494f0a4bddb5cae4bdb97b', 'module:user:update', NULL, 3, 0, NULL, '2018-11-30 14:50:46', NULL, 0);
INSERT INTO `sys_module` VALUES ('20181130145105226a58e80ec2ac640698aa', '查询', '20181130143833494f0a4bddb5cae4bdb97b', 'module:user:select', NULL, 4, 0, NULL, '2018-11-30 14:51:05', NULL, 0);
INSERT INTO `sys_module` VALUES ('201811301458347376e03e1b847074f04bb0', '新增', '20181130143914515642d29b24f624989bd2', 'module:corporation:insert', NULL, 1, 0, NULL, '2018-11-30 14:58:34', NULL, 0);
INSERT INTO `sys_module` VALUES ('20181130145918494cac15cdb581243119af', '删除', '20181130143914515642d29b24f624989bd2', 'module:corporation:delete', NULL, 2, 0, NULL, '2018-11-30 14:59:18', NULL, 0);
INSERT INTO `sys_module` VALUES ('20181130145940689cdee95431a2e4ed4bfc', '修改', '20181130143914515642d29b24f624989bd2', 'module:corporation:update', NULL, 3, 0, NULL, '2018-11-30 14:59:40', NULL, 0);
INSERT INTO `sys_module` VALUES ('20181130150000594cd3e676f8b4b4f46ac6', '查询', '20181130143914515642d29b24f624989bd2', 'module:corporation:select', NULL, 4, 0, NULL, '2018-11-30 15:00:00', NULL, 0);
INSERT INTO `sys_module` VALUES ('2018113015014044936dd9865aac14b1595e', '新增', '20181130143932417b05fb6d5961240188f0', 'module:corporationWork:insert', NULL, 1, 0, NULL, '2018-11-30 15:01:40', NULL, 0);
INSERT INTO `sys_module` VALUES ('2018113015015891349f5eec6421c48e19cb', '删除', '20181130143932417b05fb6d5961240188f0', 'module:corporationWork:delete', NULL, 2, 0, NULL, '2018-11-30 15:01:58', NULL, 0);
INSERT INTO `sys_module` VALUES ('201811301502163343a5c70a40ac5473b851', '修改', '20181130143932417b05fb6d5961240188f0', 'module:corporationWork:update', NULL, 3, 0, NULL, '2018-11-30 15:02:16', NULL, 0);
INSERT INTO `sys_module` VALUES ('20181130150255281ea06a7770cda4f389da', '查询', '20181130143932417b05fb6d5961240188f0', 'module:corporationWork:select', NULL, 4, 0, NULL, '2018-11-30 15:02:55', NULL, 0);
INSERT INTO `sys_module` VALUES ('20181130150514501803e4d6c26744afd973', '新增', '20181130143948813f3ec2564f91b4e708eb', 'module:jobPosition:insert', NULL, 1, 0, NULL, '2018-11-30 15:05:14', NULL, 0);
INSERT INTO `sys_module` VALUES ('20181130150533399df526c11bd4e45d9947', '删除', '20181130143948813f3ec2564f91b4e708eb', 'module:jobPosition:delete', NULL, 2, 0, NULL, '2018-11-30 15:05:33', NULL, 0);
INSERT INTO `sys_module` VALUES ('2018113015054725117decf31176c4160926', '修改', '20181130143948813f3ec2564f91b4e708eb', 'module:jobPosition:update', NULL, 3, 0, NULL, '2018-11-30 15:05:47', NULL, 0);
INSERT INTO `sys_module` VALUES ('201811301506063443933dbefbf8f4c819af', '查询', '20181130143948813f3ec2564f91b4e708eb', 'module:jobPosition:select', NULL, 4, 0, NULL, '2018-11-30 15:06:06', NULL, 0);
INSERT INTO `sys_module` VALUES ('2018113015085072747287b3a83bb42e4a3a', '新增', '201811301440304631badc9bf63494f5bbfd', 'module:permission:insert', NULL, 1, 0, NULL, '2018-11-30 15:08:50', NULL, 0);
INSERT INTO `sys_module` VALUES ('20181130150905377d72b4e2125bd4696830', '删除', '201811301440304631badc9bf63494f5bbfd', 'module:permission:delete', NULL, 2, 0, NULL, '2018-11-30 15:09:05', NULL, 0);
INSERT INTO `sys_module` VALUES ('20181130150929035aab66b79e5bb4cd3b8a', '修改', '201811301440304631badc9bf63494f5bbfd', 'module:permission:update', NULL, 3, 0, NULL, '2018-11-30 15:09:29', NULL, 0);
INSERT INTO `sys_module` VALUES ('20181130150942883a085b7abbebb42889ea', '查询', '201811301440304631badc9bf63494f5bbfd', 'module:permission:select', NULL, 4, 0, NULL, '2018-11-30 15:09:42', NULL, 0);
INSERT INTO `sys_module` VALUES ('20181130151719332462996790c6f45739ee', '新增', '20181130144319174a19f72479a2847aa88f', 'module:module:insert', NULL, 1, 0, NULL, '2018-11-30 15:17:19', NULL, 0);
INSERT INTO `sys_module` VALUES ('20181130151733331c67b64f9e3ff43b5a25', '删除', '20181130144319174a19f72479a2847aa88f', 'module:module:delete', NULL, 2, 0, NULL, '2018-11-30 15:17:33', NULL, 0);
INSERT INTO `sys_module` VALUES ('2018113015175300984093518d0274304bdf', '修改', '20181130144319174a19f72479a2847aa88f', 'module:module:update', NULL, 3, 0, NULL, '2018-11-30 15:17:53', NULL, 0);
INSERT INTO `sys_module` VALUES ('201811301518125191e1bdfe5c79a4845980', '查询', '20181130144319174a19f72479a2847aa88f', 'module:module:select', NULL, 4, 0, NULL, '2018-11-30 15:18:12', NULL, 0);
INSERT INTO `sys_module` VALUES ('201811301523116717be709e883bd4edfb49', '新增', '2018113014440502380eb92b8d0544a9bbe7', 'module:dataScope:insert', NULL, 1, 0, NULL, '2018-11-30 15:23:11', NULL, 0);
INSERT INTO `sys_module` VALUES ('2018113015233067399e394f9d138435982f', '删除', '2018113014440502380eb92b8d0544a9bbe7', 'module:dataScope:delete', NULL, 2, 0, NULL, '2018-11-30 15:23:30', NULL, 0);
INSERT INTO `sys_module` VALUES ('20181130152344556ad6237ce77e34aa8b0b', '修改', '2018113014440502380eb92b8d0544a9bbe7', 'module:dataScope:update', NULL, 3, 0, NULL, '2018-11-30 15:23:44', NULL, 0);
INSERT INTO `sys_module` VALUES ('20181130152402067f7eb092932b74a9ab94', '查询', '2018113014440502380eb92b8d0544a9bbe7', 'module:dataScope:select', NULL, 4, 0, NULL, '2018-11-30 15:24:02', NULL, 0);
INSERT INTO `sys_module` VALUES ('20181130155051638b7aa5fc703cf46e69cc', '新增', '20181130144430908b1beec8772c949fa8fe', 'module:dataSchema:insert', NULL, 1, 0, NULL, '2018-11-30 15:50:51', NULL, 0);
INSERT INTO `sys_module` VALUES ('2018113015513771341a1faa8bb9d42a6bd5', '删除', '20181130144430908b1beec8772c949fa8fe', 'module:dataSchema:delete', NULL, 2, 0, NULL, '2018-11-30 15:51:37', NULL, 0);
INSERT INTO `sys_module` VALUES ('201811301551525861bf763f95ca2429a83b', '修改', '20181130144430908b1beec8772c949fa8fe', 'module:dataSchema:update', NULL, 3, 0, NULL, '2018-11-30 15:51:52', NULL, 0);
INSERT INTO `sys_module` VALUES ('20181130155212844165671f8f6204f15aa2', '查询', '20181130144430908b1beec8772c949fa8fe', 'module:dataSchema:select', NULL, 4, 0, NULL, '2018-11-30 15:52:12', NULL, 0);

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('bref:admin', 'AUTH_ADMIN', '2018-11-28 09:47:27', '2018-11-28 10:01:18', 0);
INSERT INTO `sys_permission` VALUES ('bref:basic', 'AUTH_BASIC', '2018-11-28 09:47:17', '2018-11-28 09:50:05', 0);

-- ----------------------------
-- Records of sys_permission_module
-- ----------------------------
INSERT INTO `sys_permission_module` VALUES ('bref:admin', '20181130143833494f0a4bddb5cae4bdb97b');
INSERT INTO `sys_permission_module` VALUES ('bref:admin', '20181130143914515642d29b24f624989bd2');
INSERT INTO `sys_permission_module` VALUES ('bref:admin', '20181130143932417b05fb6d5961240188f0');
INSERT INTO `sys_permission_module` VALUES ('bref:admin', '20181130143948813f3ec2564f91b4e708eb');
INSERT INTO `sys_permission_module` VALUES ('bref:admin', '201811301440304631badc9bf63494f5bbfd');
INSERT INTO `sys_permission_module` VALUES ('bref:admin', '20181130144319174a19f72479a2847aa88f');
INSERT INTO `sys_permission_module` VALUES ('bref:admin', '2018113014440502380eb92b8d0544a9bbe7');
INSERT INTO `sys_permission_module` VALUES ('bref:admin', '20181130144430908b1beec8772c949fa8fe');
INSERT INTO `sys_permission_module` VALUES ('bref:admin', '20181130144835257cc172c42404b4c69a9c');
INSERT INTO `sys_permission_module` VALUES ('bref:admin', '20181130144957151d62051985aaa4acbb10');
INSERT INTO `sys_permission_module` VALUES ('bref:admin', '20181130145046663d2d99ac9be294d3e8d3');
INSERT INTO `sys_permission_module` VALUES ('bref:admin', '20181130145105226a58e80ec2ac640698aa');
INSERT INTO `sys_permission_module` VALUES ('bref:admin', '201811301458347376e03e1b847074f04bb0');
INSERT INTO `sys_permission_module` VALUES ('bref:admin', '20181130145918494cac15cdb581243119af');
INSERT INTO `sys_permission_module` VALUES ('bref:admin', '20181130145940689cdee95431a2e4ed4bfc');
INSERT INTO `sys_permission_module` VALUES ('bref:admin', '20181130150000594cd3e676f8b4b4f46ac6');
INSERT INTO `sys_permission_module` VALUES ('bref:admin', '2018113015014044936dd9865aac14b1595e');
INSERT INTO `sys_permission_module` VALUES ('bref:admin', '2018113015015891349f5eec6421c48e19cb');
INSERT INTO `sys_permission_module` VALUES ('bref:admin', '201811301502163343a5c70a40ac5473b851');
INSERT INTO `sys_permission_module` VALUES ('bref:admin', '20181130150255281ea06a7770cda4f389da');
INSERT INTO `sys_permission_module` VALUES ('bref:admin', '20181130150514501803e4d6c26744afd973');
INSERT INTO `sys_permission_module` VALUES ('bref:admin', '20181130150533399df526c11bd4e45d9947');
INSERT INTO `sys_permission_module` VALUES ('bref:admin', '2018113015054725117decf31176c4160926');
INSERT INTO `sys_permission_module` VALUES ('bref:admin', '201811301506063443933dbefbf8f4c819af');
INSERT INTO `sys_permission_module` VALUES ('bref:admin', '2018113015085072747287b3a83bb42e4a3a');
INSERT INTO `sys_permission_module` VALUES ('bref:admin', '20181130150905377d72b4e2125bd4696830');
INSERT INTO `sys_permission_module` VALUES ('bref:admin', '20181130150929035aab66b79e5bb4cd3b8a');
INSERT INTO `sys_permission_module` VALUES ('bref:admin', '20181130150942883a085b7abbebb42889ea');
INSERT INTO `sys_permission_module` VALUES ('bref:admin', '20181130151719332462996790c6f45739ee');
INSERT INTO `sys_permission_module` VALUES ('bref:admin', '20181130151733331c67b64f9e3ff43b5a25');
INSERT INTO `sys_permission_module` VALUES ('bref:admin', '2018113015175300984093518d0274304bdf');
INSERT INTO `sys_permission_module` VALUES ('bref:admin', '201811301518125191e1bdfe5c79a4845980');
INSERT INTO `sys_permission_module` VALUES ('bref:admin', '201811301523116717be709e883bd4edfb49');
INSERT INTO `sys_permission_module` VALUES ('bref:admin', '2018113015233067399e394f9d138435982f');
INSERT INTO `sys_permission_module` VALUES ('bref:admin', '20181130152344556ad6237ce77e34aa8b0b');
INSERT INTO `sys_permission_module` VALUES ('bref:admin', '20181130152402067f7eb092932b74a9ab94');
INSERT INTO `sys_permission_module` VALUES ('bref:admin', '20181130155051638b7aa5fc703cf46e69cc');
INSERT INTO `sys_permission_module` VALUES ('bref:admin', '2018113015513771341a1faa8bb9d42a6bd5');
INSERT INTO `sys_permission_module` VALUES ('bref:admin', '201811301551525861bf763f95ca2429a83b');
INSERT INTO `sys_permission_module` VALUES ('bref:admin', '20181130155212844165671f8f6204f15aa2');
INSERT INTO `sys_permission_module` VALUES ('bref:admin', '20181220154523193537a585f594b4892ab1');
INSERT INTO `sys_permission_module` VALUES ('bref:admin', '201812211436399205912b9e2c510420bbed');
INSERT INTO `sys_permission_module` VALUES ('bref:admin', '201812250940390354892cb5c9aa04d8fab9');
INSERT INTO `sys_permission_module` VALUES ('bref:basic', '201811301437346880aa713d544314c2082a');

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('201812171919033284c9bd8aa2f1e4f1a89f', 'admin', 'admin', 1, '2019-01-04 16:19:10', NULL, 0);
INSERT INTO `sys_user` VALUES ('201901041851125540e61bf209e174987a22', 'fangjiexi', 'fangjiexi', 1, '2019-01-04 18:51:12', NULL, 0);

-- ----------------------------
-- Records of sys_user_corporation
-- ----------------------------
INSERT INTO `sys_user_corporation` VALUES ('201901041851125540e61bf209e174987a22', '20190104185140053c4931524898e4124808');

-- ----------------------------
-- Records of sys_user_corporation_work
-- ----------------------------
INSERT INTO `sys_user_corporation_work` VALUES ('201901041851125540e61bf209e174987a22', '201901041852557223749dd8f8f784361b87');

-- ----------------------------
-- Records of sys_user_job_position
-- ----------------------------
INSERT INTO `sys_user_job_position` VALUES ('201901041851125540e61bf209e174987a22', '201901041855027160e4a050f11564210bc2');

-- ----------------------------
-- Records of sys_user_permission
-- ----------------------------
INSERT INTO `sys_user_permission` VALUES ('201812171919033284c9bd8aa2f1e4f1a89f', 'bref:admin');

