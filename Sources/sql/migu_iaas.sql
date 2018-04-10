/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50505
Source Host           : localhost:3306
Source Database       : migu_iaas

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2018-04-10 11:00:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `cluster`
-- ----------------------------
DROP TABLE IF EXISTS `cluster`;
CREATE TABLE `cluster` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `annotation` varchar(255) DEFAULT NULL COMMENT '备注',
  `creator` varchar(50) NOT NULL COMMENT '创建者',
  `valid` tinyint(1) NOT NULL COMMENT '0：逻辑删除',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of cluster
-- ----------------------------

-- ----------------------------
-- Table structure for `cluster_label`
-- ----------------------------
DROP TABLE IF EXISTS `cluster_label`;
CREATE TABLE `cluster_label` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '标签',
  `key` varchar(128) NOT NULL COMMENT '标签由键值对构建，key要唯一',
  `value` varchar(128) NOT NULL,
  `cluster_id` varchar(50) NOT NULL COMMENT '集群标识，只能在集群下进行标签管理',
  `valid` tinyint(1) NOT NULL COMMENT '0：逻辑删除',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of cluster_label
-- ----------------------------

-- ----------------------------
-- Table structure for `deploy_app`
-- ----------------------------
DROP TABLE IF EXISTS `deploy_app`;
CREATE TABLE `deploy_app` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL COMMENT '应用名称',
  `creator` varchar(50) NOT NULL COMMENT '创建者',
  `annotation` varchar(255) DEFAULT NULL COMMENT '备注',
  `status` tinyint(1) NOT NULL COMMENT '1：上线；0：下线',
  `valid` tinyint(1) NOT NULL COMMENT '0：逻辑删除',
  `namespace` varchar(50) NOT NULL COMMENT '应用命名空间',
  `ip` varchar(50) DEFAULT NULL COMMENT '作为第三方服务对外暴露的ip地址',
  `port` varchar(10) DEFAULT NULL COMMENT '作为第三方服务对外暴露的端口号',
  `hostname` varchar(50) DEFAULT NULL COMMENT '作为第三方服务对外暴露的域名',
  `is_service` tinyint(1) DEFAULT NULL COMMENT '是否为服务应用   1：是；0：否',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of deploy_app
-- ----------------------------

-- ----------------------------
-- Table structure for `deploy_dispatch_config`
-- ----------------------------
DROP TABLE IF EXISTS `deploy_dispatch_config`;
CREATE TABLE `deploy_dispatch_config` (
  `id` int(11) NOT NULL,
  `app_id` int(11) NOT NULL COMMENT '应用id',
  `image_id` int(11) NOT NULL,
  `position_code` varchar(50) NOT NULL COMMENT '机房code',
  `cluster_id` varchar(50) NOT NULL COMMENT '集群code',
  `base_rps` int(11) DEFAULT NULL COMMENT '实例起始基数',
  `custom_rules` text COMMENT '定制周期性调度策略，json数据字符串；"[{"begin_time":"2017-01-01 11:11:00","end_time":"2017-02-01 11:11:00","period":10,"base":-2}]"',
  `creator` varchar(50) DEFAULT NULL COMMENT '创建者',
  `status` tinyint(1) NOT NULL COMMENT '1：启用调度；0：暂不启用',
  `valid` tinyint(1) NOT NULL COMMENT '0：逻辑删除',
  `annotation` varchar(255) DEFAULT NULL COMMENT '备注',
  `begintime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '开始调度时间',
  `endtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '结束调度时间',
  `createtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updatetime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of deploy_dispatch_config
-- ----------------------------

-- ----------------------------
-- Table structure for `deploy_namespace`
-- ----------------------------
DROP TABLE IF EXISTS `deploy_namespace`;
CREATE TABLE `deploy_namespace` (
  `ns` varchar(50) NOT NULL COMMENT '命名空间字符串',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ns`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of deploy_namespace
-- ----------------------------

-- ----------------------------
-- Table structure for `dispatch_log`
-- ----------------------------
DROP TABLE IF EXISTS `dispatch_log`;
CREATE TABLE `dispatch_log` (
  `id` varchar(50) NOT NULL,
  `position_code` varchar(50) NOT NULL COMMENT '机房code',
  `obj` varchar(50) NOT NULL COMMENT '调度对象',
  `type` varchar(50) NOT NULL COMMENT '调度类型',
  `param` text NOT NULL COMMENT '调度参数，json字符串',
  `status` tinyint(1) NOT NULL COMMENT '调度状态  1调度成功；0调度失败',
  `annotation` varchar(255) DEFAULT NULL COMMENT '备注',
  `createtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of dispatch_log
-- ----------------------------

-- ----------------------------
-- Table structure for `image`
-- ----------------------------
DROP TABLE IF EXISTS `image`;
CREATE TABLE `image` (
  `id` int(11) NOT NULL,
  `name` varchar(50) DEFAULT NULL COMMENT '镜像名称',
  `version` varchar(50) DEFAULT NULL COMMENT '镜像版本号',
  `annotation` varchar(255) DEFAULT NULL COMMENT '备注',
  `creator` varchar(50) DEFAULT NULL COMMENT '创建者',
  `valid` tinyint(1) DEFAULT NULL COMMENT '0：逻辑删除',
  `git_path` varchar(255) DEFAULT NULL COMMENT '镜像代码git地址',
  `ftp_path` varchar(50) DEFAULT NULL COMMENT '镜像ftp地址',
  `upload_hub` tinyint(1) DEFAULT NULL COMMENT '上传hub   1:上传成功    0：上传失败',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of image
-- ----------------------------

-- ----------------------------
-- Table structure for `image_deploy`
-- ----------------------------
DROP TABLE IF EXISTS `image_deploy`;
CREATE TABLE `image_deploy` (
  `id` int(11) NOT NULL,
  `name` varchar(50) DEFAULT NULL COMMENT '部署名称，自动生成，格式：镜像名-镜像版本号',
  `image_id` int(11) NOT NULL COMMENT '镜像id',
  `app_id` int(11) NOT NULL COMMENT '应用id',
  `cluster_id` int(11) NOT NULL COMMENT '集群id',
  `deploy_label` varchar(50) DEFAULT NULL COMMENT '部署标签，生成服务使用，自动生成，格式：命名空间:部署名称',
  `min_pods` tinyint(4) DEFAULT NULL COMMENT '最小实例数',
  `max_pods` tinyint(4) DEFAULT NULL COMMENT '最大实例数',
  `position_pods` text COMMENT '按机房配置的实例数，json数组字符串  "[{"address_code":"hf","pods":12}]"',
  `cpu_limits` tinyint(4) DEFAULT NULL COMMENT 'cpu核心限制数',
  `memory_limits` int(11) DEFAULT NULL COMMENT '内存限制，单位MB',
  `simult_updates` tinyint(4) DEFAULT NULL COMMENT '同时更新主机数量',
  `time_out` int(11) DEFAULT NULL COMMENT '设置容器启动的超时时间，单位s',
  `unique_deploy` tinyint(4) DEFAULT NULL COMMENT '1：主机唯一性部署；0：非唯一性部署',
  `health_check` tinyint(4) DEFAULT NULL COMMENT '1：需要轮询检查；0：不需要检查',
  `envs` text COMMENT '环境变量设置，json数据字符串；"[{"key":"config","value":"12"}]"',
  `init_cmd` varchar(255) DEFAULT NULL COMMENT '容器启动命令',
  `mount_dirs` text COMMENT '挂载目录配置，json数组字符串   "[{"server_dir":"","container_dir":""}]"',
  `deploy_type` varchar(50) DEFAULT NULL COMMENT '部署类型：测试（test）、正式部署（deploy）、扩容缩容（scale）,下线或删除（delete）',
  `deploy_status` tinyint(1) DEFAULT NULL COMMENT '部署状态：-2停止部署，-1未开始，0完成，1正在部署，2其他异常',
  `auto_dispatch` tinyint(1) DEFAULT NULL COMMENT '1：自动调度；0非自动调度',
  `valid` tinyint(1) DEFAULT NULL COMMENT '0：逻辑删除',
  `createtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updatetime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of image_deploy
-- ----------------------------

-- ----------------------------
-- Table structure for `operation_log`
-- ----------------------------
DROP TABLE IF EXISTS `operation_log`;
CREATE TABLE `operation_log` (
  `id` varchar(50) NOT NULL,
  `type` varchar(50) DEFAULT NULL COMMENT '操作类型',
  `detail` varchar(255) DEFAULT NULL COMMENT '操作详情',
  `obj` varchar(50) DEFAULT NULL COMMENT '操作对行啊',
  `param` text COMMENT '操作参数',
  `creator` varchar(50) DEFAULT NULL COMMENT '创建者',
  `createtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of operation_log
-- ----------------------------

-- ----------------------------
-- Table structure for `server`
-- ----------------------------
DROP TABLE IF EXISTS `server`;
CREATE TABLE `server` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ipv4` varchar(50) DEFAULT NULL COMMENT '主机ip',
  `ipv6` varchar(128) DEFAULT NULL,
  `hostname` varchar(128) NOT NULL COMMENT '主机名',
  `sn` varchar(50) NOT NULL COMMENT '主机序列号',
  `os` varchar(50) DEFAULT NULL COMMENT '主机操作系统',
  `kernel` varchar(50) DEFAULT NULL COMMENT '主机内核',
  `disk` varchar(50) DEFAULT NULL COMMENT '主机磁盘',
  `memory` varchar(50) DEFAULT NULL COMMENT '主机内存',
  `status` tinyint(1) NOT NULL COMMENT '主机状态，那些状态？',
  `valid` tinyint(1) NOT NULL COMMENT '0：逻辑删除',
  `annotation` varchar(255) DEFAULT NULL COMMENT '备注',
  `position_code` varchar(50) NOT NULL COMMENT '机房code',
  `docker_version` varchar(128) DEFAULT NULL COMMENT 'docker版本',
  `cluster_id` varchar(50) DEFAULT NULL COMMENT '集群code',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of server
-- ----------------------------

-- ----------------------------
-- Table structure for `server_label_relation`
-- ----------------------------
DROP TABLE IF EXISTS `server_label_relation`;
CREATE TABLE `server_label_relation` (
  `server_id` int(11) NOT NULL,
  `label_id` int(11) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`server_id`,`label_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of server_label_relation
-- ----------------------------

-- ----------------------------
-- Table structure for `server_position`
-- ----------------------------
DROP TABLE IF EXISTS `server_position`;
CREATE TABLE `server_position` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `ftp_path` varchar(128) DEFAULT NULL,
  `container_img` varchar(128) DEFAULT NULL COMMENT '资源容器镜像，用于启动初始化容器下载大资源',
  `img_registry` varchar(128) DEFAULT NULL COMMENT '镜像仓库地址',
  `etcd_api` varchar(128) DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of server_position
-- ----------------------------

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(50) NOT NULL,
  `account` varchar(50) NOT NULL,
  `nickname` varchar(50) DEFAULT NULL,
  `salt` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `role_id` varchar(50) DEFAULT NULL,
  `department_code` varchar(50) DEFAULT NULL COMMENT '部门code',
  `valid` tinyint(1) NOT NULL COMMENT '0：逻辑删除',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'test', '测试账号', '', 'e10adc3949ba59abbe56e057f20f883e', null, null, null, null, '0', '2018-04-02 10:35:01', '2018-04-08 18:22:17');
INSERT INTO `user` VALUES ('2', 'ldfjlsf', '??', '', '', null, null, null, null, '0', '2018-04-02 13:44:48', '2018-04-02 13:44:48');

-- ----------------------------
-- Table structure for `user_organization`
-- ----------------------------
DROP TABLE IF EXISTS `user_organization`;
CREATE TABLE `user_organization` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_code` varchar(50) NOT NULL,
  `company_name` varchar(50) NOT NULL,
  `depatment_code` varchar(50) NOT NULL,
  `depatment_name` varchar(50) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user_organization
-- ----------------------------

-- ----------------------------
-- Table structure for `user_permission`
-- ----------------------------
DROP TABLE IF EXISTS `user_permission`;
CREATE TABLE `user_permission` (
  `id` int(11) NOT NULL,
  `pid` int(11) NOT NULL COMMENT '父id',
  `code` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `annotation` varchar(255) DEFAULT NULL COMMENT '备注',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user_permission
-- ----------------------------

-- ----------------------------
-- Table structure for `user_role`
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `int` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `annotation` varchar(255) DEFAULT NULL COMMENT '备注',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`int`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user_role
-- ----------------------------

-- ----------------------------
-- Table structure for `user_role_permission`
-- ----------------------------
DROP TABLE IF EXISTS `user_role_permission`;
CREATE TABLE `user_role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` varchar(50) NOT NULL,
  `permission_code` varchar(50) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user_role_permission
-- ----------------------------
