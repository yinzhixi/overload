创建用户权限表
CREATE TABLE `t_user_node` (
  `empId` varchar(100) NOT NULL COMMENT '用户id',
  `nodeCode` varchar(100) NOT NULL COMMENT '设备id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户设备权限表';

创建用户站点关系表
CREATE TABLE `t_user_station` (
  `userId` int(30) NOT NULL COMMENT '用户id',
  `stationId` int(30) NOT NULL COMMENT '站点Id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

设备表
CREATE TABLE `node` (
  `id` int(50) NOT NULL AUTO_INCREMENT COMMENT '设备id',
  `nodeCode` varchar(64) DEFAULT NULL COMMENT '设备编号',
  `nodeName` varchar(128) DEFAULT NULL COMMENT '设备名称',
  `pinyin` varchar(512) DEFAULT NULL COMMENT '拼音名称',
  `online` tinyint(1) DEFAULT NULL,
  `lat` decimal(10,7) DEFAULT NULL,
  `lon` decimal(10,7) DEFAULT NULL,
  `key` varchar(128) DEFAULT NULL,
  `adminUser` varchar(64) DEFAULT NULL,
  `createUser` varchar(64) DEFAULT NULL COMMENT '创建用户',
  `holderUser` varchar(64) DEFAULT NULL,
  `createTime` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `nId` varchar(64) DEFAULT NULL COMMENT '设备类型',
  `ip` varchar(64) DEFAULT NULL,
  `stationId` int(30) DEFAULT NULL COMMENT '关联站点id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;