/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : jm

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2019-04-10 14:36:28
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for node
-- ----------------------------
DROP TABLE IF EXISTS `node`;
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
  `stationCode` varchar(50) DEFAULT NULL COMMENT '所属站点编码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for node_type
-- ----------------------------
DROP TABLE IF EXISTS `node_type`;
CREATE TABLE `node_type` (
  `nId` varchar(64) NOT NULL,
  `name` varchar(128) DEFAULT NULL,
  `pinyin` varchar(512) DEFAULT NULL,
  `desp` varchar(512) DEFAULT NULL,
  `version` varchar(45) DEFAULT NULL,
  `createUser` varchar(64) DEFAULT NULL,
  `updateTime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`nId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for preview_img
-- ----------------------------
DROP TABLE IF EXISTS `preview_img`;
CREATE TABLE `preview_img` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `img` varchar(256) NOT NULL COMMENT '公路抓拍照片',
  `creatTime` varchar(100) DEFAULT NULL COMMENT '添加时间',
  `previewId` int(30) DEFAULT NULL,
  `carNum` varchar(45) DEFAULT NULL,
  `snapTime` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for s_cartype
-- ----------------------------
DROP TABLE IF EXISTS `s_cartype`;
CREATE TABLE `s_cartype` (
  `date` varchar(10) NOT NULL COMMENT '天，例如：2018-04-01',
  `stationId` int(11) NOT NULL COMMENT '站点id',
  `cartype` int(11) NOT NULL COMMENT '车型',
  `num` int(11) NOT NULL COMMENT '数量',
  `olNum` int(11) DEFAULT NULL COMMENT '超限数量',
  PRIMARY KEY (`date`,`stationId`,`cartype`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='按车型统计通过数量、超限数量';

-- ----------------------------
-- Table structure for s_hour
-- ----------------------------
DROP TABLE IF EXISTS `s_hour`;
CREATE TABLE `s_hour` (
  `date` varchar(10) NOT NULL COMMENT '天，例如：2018-04-01',
  `stationId` int(11) NOT NULL COMMENT '站点id',
  `hour` int(11) NOT NULL COMMENT '时辰',
  `num` int(11) DEFAULT NULL COMMENT '数量',
  PRIMARY KEY (`date`,`stationId`,`hour`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='按时辰统计通过数量';

-- ----------------------------
-- Table structure for s_over
-- ----------------------------
DROP TABLE IF EXISTS `s_over`;
CREATE TABLE `s_over` (
  `date` varchar(10) NOT NULL COMMENT '天，例如：2018-04-01',
  `stationId` int(11) NOT NULL COMMENT '站点id',
  `overId` int(4) NOT NULL COMMENT '吨位区间id',
  `num` int(11) NOT NULL COMMENT '数量',
  PRIMARY KEY (`date`,`stationId`,`overId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='超限率统计表';

-- ----------------------------
-- Table structure for s_recg
-- ----------------------------
DROP TABLE IF EXISTS `s_recg`;
CREATE TABLE `s_recg` (
  `date` varchar(30) NOT NULL COMMENT '天，例如：2018-04-01',
  `stationId` int(11) NOT NULL COMMENT '站点id',
  `lane` int(4) NOT NULL COMMENT '车道号',
  `dayNum` int(11) DEFAULT NULL COMMENT '白天总数量',
  `nightNum` int(11) DEFAULT NULL COMMENT '夜晚总数量',
  `dayNum_r` int(11) DEFAULT NULL COMMENT '白天识别总数量',
  `nightNum_r` int(11) DEFAULT NULL COMMENT '夜晚识别总数量',
  PRIMARY KEY (`date`,`stationId`,`lane`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='车牌识别统计表';

-- ----------------------------
-- Table structure for s_step_mark
-- ----------------------------
DROP TABLE IF EXISTS `s_step_mark`;
CREATE TABLE `s_step_mark` (
  `fromDate` varchar(64) NOT NULL COMMENT '当前统计到的id',
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`fromDate`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='s_cartype表增量统计标记';

-- ----------------------------
-- Table structure for s_ton
-- ----------------------------
DROP TABLE IF EXISTS `s_ton`;
CREATE TABLE `s_ton` (
  `date` varchar(10) NOT NULL COMMENT '天，例如：2018-04-01',
  `stationId` int(11) NOT NULL COMMENT '站点id',
  `tonId` int(4) NOT NULL COMMENT '吨位区间id',
  `num` int(11) NOT NULL COMMENT '数量',
  PRIMARY KEY (`date`,`stationId`,`tonId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='按吨位统计表';

-- ----------------------------
-- Table structure for t_car
-- ----------------------------
DROP TABLE IF EXISTS `t_car`;
CREATE TABLE `t_car` (
  `id` tinyint(4) NOT NULL AUTO_INCREMENT,
  `outLane` tinyint(6) DEFAULT NULL,
  `goStation` varchar(50) DEFAULT NULL,
  `outStation` varchar(50) DEFAULT NULL,
  `goTime` varchar(30) DEFAULT NULL,
  `outTime` varchar(30) DEFAULT NULL,
  `raodCard` varchar(50) DEFAULT NULL COMMENT '道路运输证号',
  `agencyCard` varchar(50) DEFAULT NULL COMMENT '发证机构',
  `vinNumber` varchar(50) DEFAULT NULL COMMENT '车架号',
  `licenseNumber` varchar(50) DEFAULT NULL COMMENT '许可证号',
  `ownerName` varchar(50) DEFAULT '' COMMENT '业主名称',
  `ownerAddress` varchar(50) DEFAULT NULL COMMENT '业户地址',
  `telephone` varchar(50) DEFAULT NULL COMMENT '电话',
  `status` tinyint(10) DEFAULT NULL,
  `carNum` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_dict
-- ----------------------------
DROP TABLE IF EXISTS `t_dict`;
CREATE TABLE `t_dict` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(64) NOT NULL,
  `code` varchar(128) NOT NULL,
  `name` varchar(64) NOT NULL,
  `brief` text,
  `sq` int(11) DEFAULT '0',
  `createTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `spare1` varchar(45) DEFAULT NULL,
  `spare2` varchar(45) DEFAULT NULL,
  `spare3` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_emp
-- ----------------------------
DROP TABLE IF EXISTS `t_emp`;
CREATE TABLE `t_emp` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `jobNum` varchar(200) DEFAULT '' COMMENT '工号',
  `empName` varchar(200) CHARACTER SET utf8 DEFAULT '' COMMENT '用户名称',
  `passWord` varchar(200) CHARACTER SET utf8 DEFAULT '' COMMENT '密码',
  `isEmp` varchar(200) DEFAULT NULL COMMENT '是否在用（1是、0否）',
  `stationName` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '站点名称',
  `salt` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb4 COMMENT='二、用户表t_user';

-- ----------------------------
-- Table structure for t_fileidx
-- ----------------------------
DROP TABLE IF EXISTS `t_fileidx`;
CREATE TABLE `t_fileidx` (
  `id` varchar(36) NOT NULL COMMENT '主键id',
  `fileType` varchar(45) DEFAULT NULL COMMENT '图片类型',
  `relCode` varchar(45) DEFAULT NULL COMMENT '图片代码',
  `fileName` varchar(64) DEFAULT NULL COMMENT '图片名称',
  `physicalPath` varchar(255) DEFAULT NULL COMMENT '物理路径',
  `filePath` varchar(255) DEFAULT NULL COMMENT '文件相对路径',
  `createTime` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文件索引';

-- ----------------------------
-- Table structure for t_log
-- ----------------------------
DROP TABLE IF EXISTS `t_log`;
CREATE TABLE `t_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `data` blob,
  `date` timestamp NULL DEFAULT NULL,
  `createDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `fromId` varchar(64) DEFAULT NULL,
  `fromIp` varchar(64) DEFAULT NULL,
  `toId` varchar(64) DEFAULT NULL,
  `toIp` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_log_req
-- ----------------------------
DROP TABLE IF EXISTS `t_log_req`;
CREATE TABLE `t_log_req` (
  `id` varchar(36) NOT NULL,
  `userId` varchar(50) DEFAULT NULL,
  `module` varchar(32) DEFAULT NULL,
  `method` varchar(32) DEFAULT NULL,
  `useTime` varchar(32) DEFAULT NULL,
  `ip` varchar(32) DEFAULT NULL,
  `userAgent` varchar(256) DEFAULT NULL,
  `url` varchar(128) DEFAULT NULL,
  `param` varchar(512) DEFAULT NULL,
  `host` varchar(32) DEFAULT NULL,
  `port` int(11) DEFAULT NULL,
  `reqTime` varchar(32) DEFAULT NULL,
  `description` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE `t_menu` (
  `menuId` varchar(50) CHARACTER SET utf8 NOT NULL COMMENT '菜单Id',
  `parentId` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT '父类菜单',
  `menuLevel` int(11) DEFAULT NULL COMMENT '菜单级别',
  `sort` int(11) DEFAULT NULL COMMENT '菜单顺序',
  `name` varchar(200) DEFAULT '' COMMENT '菜单名称',
  `path` varchar(200) DEFAULT '#' COMMENT '菜单路由，没有则为#',
  `icon` varchar(45) DEFAULT '' COMMENT '图标',
  `addTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '添加时间',
  `updateTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`menuId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='一、视图菜单表t_menu';

-- ----------------------------
-- Table structure for t_overloadrate
-- ----------------------------
DROP TABLE IF EXISTS `t_overloadrate`;
CREATE TABLE `t_overloadrate` (
  `overLoadId` int(4) NOT NULL AUTO_INCREMENT,
  `overLoadRateRegion` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`overLoadId`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_permission`;
CREATE TABLE `t_permission` (
  `id` varchar(30) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `domain` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_physica_path
-- ----------------------------
DROP TABLE IF EXISTS `t_physica_path`;
CREATE TABLE `t_physica_path` (
  `id` int(30) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `pathName` varchar(100) DEFAULT NULL COMMENT '路径名称',
  `pathCode` varchar(50) DEFAULT NULL COMMENT '路径代号',
  `path` varchar(255) DEFAULT NULL COMMENT '磁盘路径',
  `status` varchar(5) DEFAULT '1' COMMENT '路径所处状态0：无效 1：正常',
  `createTime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='系统的磁盘路径表t_physica_path';

-- ----------------------------
-- Table structure for t_preview
-- ----------------------------
DROP TABLE IF EXISTS `t_preview`;
CREATE TABLE `t_preview` (
  `previewId` int(30) NOT NULL AUTO_INCREMENT,
  `dateTime` varchar(40) DEFAULT '' COMMENT '称重系统检测车的时间',
  `lane` int(4) DEFAULT NULL COMMENT '车道',
  `vehicleType` varchar(200) DEFAULT '' COMMENT '车型',
  `speed` int(4) DEFAULT NULL COMMENT '车速',
  `axleCnt` int(4) DEFAULT NULL COMMENT '轴数',
  `axleDis` int(4) DEFAULT NULL COMMENT '轴距',
  `direction` tinyint(2) DEFAULT '1' COMMENT '车向{1+正向} {0- 反向}',
  `sumWeight` double(10,3) DEFAULT NULL COMMENT '总重已经放大100倍',
  `carNum` varchar(200) DEFAULT '' COMMENT '车牌号',
  `station` varchar(200) DEFAULT '' COMMENT '站点',
  `length` int(11) DEFAULT NULL COMMENT '长（备用字段）',
  `width` int(11) DEFAULT NULL COMMENT '宽（备用字段）',
  `height` int(11) DEFAULT NULL COMMENT '高（备用字段）',
  `frontPic` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '26、车前图片文件',
  `backPic` varchar(100) DEFAULT NULL COMMENT '车后图片文件',
  `picbak` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '图片备用字段',
  `movie` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '视频',
  `tonId` int(20) DEFAULT NULL,
  `overLoadRate` int(4) DEFAULT NULL COMMENT '穿过来的超限率',
  `overLoadId` int(20) DEFAULT NULL COMMENT '超限率外键',
  `overRage` double(10,3) DEFAULT NULL COMMENT '超出多少吨',
  `recognition` int(4) DEFAULT NULL COMMENT '0 未识别  1  识别',
  `date` varchar(20) DEFAULT NULL,
  `venifyPreview` int(10) DEFAULT '0',
  `sidePic` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `upPic` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `limitWeight` int(100) DEFAULT NULL,
  `createTime` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `stationId` int(10) DEFAULT NULL COMMENT '站点id',
  `deleted` tinyint(4) NOT NULL DEFAULT '0',
  `year` int(11) DEFAULT NULL,
  `month` int(11) DEFAULT NULL,
  `day` int(11) DEFAULT NULL,
  `axleWeight1` double(10,3) DEFAULT NULL,
  `axleWeight2` double(10,3) DEFAULT NULL,
  `axleWeight3` double(10,3) DEFAULT NULL,
  `axleWeight4` double(10,3) DEFAULT NULL,
  `axleWeight5` double(10,3) DEFAULT NULL,
  `axleWeight6` double(10,3) DEFAULT NULL,
  `axleWeight7` double(10,3) DEFAULT NULL,
  `axleWeight8` double(10,3) DEFAULT NULL,
  `checkNo` varchar(64) DEFAULT NULL,
  `platePic` varchar(100) DEFAULT NULL,
  `vedioPath` varchar(100) DEFAULT NULL,
  `screenPic` varchar(100) DEFAULT NULL,
  `snapScreen` tinyint(4) DEFAULT NULL,
  `timeFlowNo` varchar(64) DEFAULT NULL,
  `relCode` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '图片代码',
  `stationMark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`previewId`)
) ENGINE=InnoDB AUTO_INCREMENT=3464 DEFAULT CHARSET=utf8mb4 COMMENT='五、预检数据表t_preview';

-- ----------------------------
-- Table structure for t_previewafter_preview
-- ----------------------------
DROP TABLE IF EXISTS `t_previewafter_preview`;
CREATE TABLE `t_previewafter_preview` (
  `previewId` int(11) NOT NULL DEFAULT '0',
  `dateTime` varchar(100) DEFAULT NULL,
  `lane` int(10) DEFAULT NULL,
  `vehicleType` varchar(50) DEFAULT NULL,
  `speed` int(10) DEFAULT NULL,
  `axleCnt` int(10) DEFAULT NULL,
  `axleDis` int(10) DEFAULT NULL,
  `direction` int(10) DEFAULT NULL,
  `sumWeight` double(10,3) DEFAULT NULL,
  `carNum` varchar(50) DEFAULT NULL,
  `stationMark` varchar(100) DEFAULT NULL COMMENT '站点编号',
  `stationId` int(30) DEFAULT NULL COMMENT 'stationId站点id',
  `station` varchar(20) DEFAULT NULL,
  `frontPic` varchar(100) DEFAULT NULL,
  `backPic` varchar(100) DEFAULT '',
  `picbak` varchar(50) DEFAULT NULL,
  `movie` varchar(50) DEFAULT NULL,
  `tonId` int(10) DEFAULT NULL,
  `overLoadRate` int(4) DEFAULT NULL,
  `overLoadId` int(10) DEFAULT NULL,
  `overRage` double(10,3) DEFAULT NULL,
  `recognition` int(10) DEFAULT '1',
  `date` varchar(30) DEFAULT NULL,
  `venifyTime` varchar(30) DEFAULT NULL,
  `limitWeight` int(10) DEFAULT NULL,
  `uploaded` tinyint(4) DEFAULT NULL,
  `uploadTime` varchar(30) DEFAULT NULL,
  `checkNo` varchar(64) DEFAULT NULL,
  `axleWeight1` double(10,3) DEFAULT NULL,
  `axleWeight2` double(10,3) DEFAULT NULL,
  `axleWeight3` double(10,3) DEFAULT NULL,
  `axleWeight4` double(10,3) DEFAULT NULL,
  `axleWeight5` double(10,3) DEFAULT NULL,
  `axleWeight6` double(10,3) DEFAULT NULL,
  `axleWeight7` double(10,3) DEFAULT NULL,
  `axleWeight8` double(10,3) DEFAULT NULL,
  `platePic` varchar(255) DEFAULT NULL,
  `vedioPath` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL,
  `screenPic` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL,
  `snapScreen` tinyint(4) DEFAULT NULL,
  `relCode` varchar(100) DEFAULT NULL COMMENT '抓拍图片编码',
  PRIMARY KEY (`previewId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_preview_2018_01
-- ----------------------------
DROP TABLE IF EXISTS `t_preview_2018_01`;
CREATE TABLE `t_preview_2018_01` (
  `previewId` int(30) NOT NULL AUTO_INCREMENT,
  `dateTime` varchar(40) DEFAULT '' COMMENT '称重系统检测车的时间',
  `lane` int(4) DEFAULT NULL COMMENT '车道',
  `vehicleType` varchar(200) DEFAULT '' COMMENT '车型',
  `speed` int(4) DEFAULT NULL COMMENT '车速',
  `axleCnt` int(4) DEFAULT NULL COMMENT '轴数',
  `axleDis` int(4) DEFAULT NULL COMMENT '轴距',
  `direction` tinyint(2) DEFAULT '1' COMMENT '车向{1+正向} {0- 反向}',
  `sumWeight` double(10,3) DEFAULT NULL COMMENT '总重已经放大100倍',
  `carNum` varchar(200) DEFAULT '' COMMENT '车牌号',
  `station` varchar(200) DEFAULT '' COMMENT '站点',
  `length` int(11) DEFAULT NULL COMMENT '长（备用字段）',
  `width` int(11) DEFAULT NULL COMMENT '宽（备用字段）',
  `height` int(11) DEFAULT NULL COMMENT '高（备用字段）',
  `frontPic` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '26、车前图片文件',
  `backPic` varchar(100) DEFAULT NULL COMMENT '车后图片文件',
  `picbak` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '图片备用字段',
  `movie` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '视频',
  `tonId` int(20) DEFAULT NULL,
  `overLoadRate` int(4) DEFAULT NULL COMMENT '穿过来的超限率',
  `overLoadId` int(20) DEFAULT NULL COMMENT '超限率外键',
  `overRage` double(10,3) DEFAULT NULL COMMENT '超出多少吨',
  `recognition` int(4) DEFAULT NULL COMMENT '0 未识别  1  识别',
  `date` varchar(20) DEFAULT NULL,
  `venifyPreview` int(10) DEFAULT '0',
  `sidePic` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `upPic` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `limitWeight` int(100) DEFAULT NULL,
  `createTime` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `stationId` int(10) DEFAULT NULL,
  `deleted` tinyint(4) NOT NULL DEFAULT '0',
  `year` int(11) DEFAULT NULL,
  `month` int(11) DEFAULT NULL,
  `day` int(11) DEFAULT NULL,
  PRIMARY KEY (`previewId`)
) ENGINE=InnoDB AUTO_INCREMENT=1094 DEFAULT CHARSET=utf8mb4 COMMENT='五、预检数据表t_preview';

-- ----------------------------
-- Table structure for t_preview_2018_07
-- ----------------------------
DROP TABLE IF EXISTS `t_preview_2018_07`;
CREATE TABLE `t_preview_2018_07` (
  `previewId` int(30) NOT NULL AUTO_INCREMENT,
  `dateTime` varchar(40) DEFAULT '' COMMENT '称重系统检测车的时间',
  `lane` int(4) DEFAULT NULL COMMENT '车道',
  `vehicleType` varchar(200) DEFAULT '' COMMENT '车型',
  `speed` int(4) DEFAULT NULL COMMENT '车速',
  `axleCnt` int(4) DEFAULT NULL COMMENT '轴数',
  `axleDis` int(4) DEFAULT NULL COMMENT '轴距',
  `direction` tinyint(2) DEFAULT '1' COMMENT '车向{1+正向} {0- 反向}',
  `sumWeight` double(10,3) DEFAULT NULL COMMENT '总重已经放大100倍',
  `carNum` varchar(200) DEFAULT '' COMMENT '车牌号',
  `station` varchar(200) DEFAULT '' COMMENT '站点',
  `length` int(11) DEFAULT NULL COMMENT '长（备用字段）',
  `width` int(11) DEFAULT NULL COMMENT '宽（备用字段）',
  `height` int(11) DEFAULT NULL COMMENT '高（备用字段）',
  `frontPic` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '26、车前图片文件',
  `backPic` varchar(100) DEFAULT NULL COMMENT '车后图片文件',
  `picbak` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '图片备用字段',
  `movie` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '视频',
  `tonId` int(20) DEFAULT NULL,
  `overLoadRate` int(4) DEFAULT NULL COMMENT '穿过来的超限率',
  `overLoadId` int(20) DEFAULT NULL COMMENT '超限率外键',
  `overRage` double(10,3) DEFAULT NULL COMMENT '超出多少吨',
  `recognition` int(4) DEFAULT NULL COMMENT '0 未识别  1  识别',
  `date` varchar(20) DEFAULT NULL,
  `venifyPreview` int(10) DEFAULT '0',
  `sidePic` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `upPic` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `limitWeight` int(100) DEFAULT NULL,
  `createTime` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `stationId` int(10) DEFAULT NULL,
  `deleted` tinyint(4) NOT NULL DEFAULT '0',
  `year` int(11) DEFAULT NULL,
  `month` int(11) DEFAULT NULL,
  `day` int(11) DEFAULT NULL,
  PRIMARY KEY (`previewId`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8mb4 COMMENT='五、预检数据表t_preview';

-- ----------------------------
-- Table structure for t_preview_2018_08
-- ----------------------------
DROP TABLE IF EXISTS `t_preview_2018_08`;
CREATE TABLE `t_preview_2018_08` (
  `previewId` int(30) NOT NULL AUTO_INCREMENT,
  `dateTime` varchar(40) DEFAULT '' COMMENT '称重系统检测车的时间',
  `lane` int(4) DEFAULT NULL COMMENT '车道',
  `vehicleType` varchar(200) DEFAULT '' COMMENT '车型',
  `speed` int(4) DEFAULT NULL COMMENT '车速',
  `axleCnt` int(4) DEFAULT NULL COMMENT '轴数',
  `axleDis` int(4) DEFAULT NULL COMMENT '轴距',
  `direction` tinyint(2) DEFAULT '1' COMMENT '车向{1+正向} {0- 反向}',
  `sumWeight` double(10,3) DEFAULT NULL COMMENT '总重已经放大100倍',
  `carNum` varchar(200) DEFAULT '' COMMENT '车牌号',
  `station` varchar(200) DEFAULT '' COMMENT '站点',
  `length` int(11) DEFAULT NULL COMMENT '长（备用字段）',
  `width` int(11) DEFAULT NULL COMMENT '宽（备用字段）',
  `height` int(11) DEFAULT NULL COMMENT '高（备用字段）',
  `frontPic` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '26、车前图片文件',
  `backPic` varchar(100) DEFAULT NULL COMMENT '车后图片文件',
  `picbak` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '图片备用字段',
  `movie` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '视频',
  `tonId` int(20) DEFAULT NULL,
  `overLoadRate` int(4) DEFAULT NULL COMMENT '穿过来的超限率',
  `overLoadId` int(20) DEFAULT NULL COMMENT '超限率外键',
  `overRage` double(10,3) DEFAULT NULL COMMENT '超出多少吨',
  `recognition` int(4) DEFAULT NULL COMMENT '0 未识别  1  识别',
  `date` varchar(20) DEFAULT NULL,
  `venifyPreview` int(10) DEFAULT '0',
  `sidePic` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `upPic` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `limitWeight` int(100) DEFAULT NULL,
  `createTime` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `stationId` int(10) DEFAULT NULL,
  `deleted` tinyint(4) NOT NULL DEFAULT '0',
  `year` int(11) DEFAULT NULL,
  `month` int(11) DEFAULT NULL,
  `day` int(11) DEFAULT NULL,
  PRIMARY KEY (`previewId`)
) ENGINE=InnoDB AUTO_INCREMENT=1030 DEFAULT CHARSET=utf8mb4 COMMENT='五、预检数据表t_preview';

-- ----------------------------
-- Table structure for t_preview_2018_09
-- ----------------------------
DROP TABLE IF EXISTS `t_preview_2018_09`;
CREATE TABLE `t_preview_2018_09` (
  `previewId` int(30) NOT NULL AUTO_INCREMENT,
  `dateTime` varchar(40) DEFAULT '' COMMENT '称重系统检测车的时间',
  `lane` int(4) DEFAULT NULL COMMENT '车道',
  `vehicleType` varchar(200) DEFAULT '' COMMENT '车型',
  `speed` int(4) DEFAULT NULL COMMENT '车速',
  `axleCnt` int(4) DEFAULT NULL COMMENT '轴数',
  `axleDis` int(4) DEFAULT NULL COMMENT '轴距',
  `direction` tinyint(2) DEFAULT '1' COMMENT '车向{1+正向} {0- 反向}',
  `sumWeight` double(10,3) DEFAULT NULL COMMENT '总重已经放大100倍',
  `carNum` varchar(200) DEFAULT '' COMMENT '车牌号',
  `station` varchar(200) DEFAULT '' COMMENT '站点',
  `length` int(11) DEFAULT NULL COMMENT '长（备用字段）',
  `width` int(11) DEFAULT NULL COMMENT '宽（备用字段）',
  `height` int(11) DEFAULT NULL COMMENT '高（备用字段）',
  `frontPic` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '26、车前图片文件',
  `backPic` varchar(100) DEFAULT NULL COMMENT '车后图片文件',
  `picbak` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '图片备用字段',
  `movie` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '视频',
  `tonId` int(20) DEFAULT NULL,
  `overLoadRate` int(4) DEFAULT NULL COMMENT '穿过来的超限率',
  `overLoadId` int(20) DEFAULT NULL COMMENT '超限率外键',
  `overRage` double(10,3) DEFAULT NULL COMMENT '超出多少吨',
  `recognition` int(4) DEFAULT NULL COMMENT '0 未识别  1  识别',
  `date` varchar(20) DEFAULT NULL,
  `venifyPreview` int(10) DEFAULT '0',
  `sidePic` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `upPic` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `limitWeight` int(100) DEFAULT NULL,
  `createTime` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `stationId` int(10) DEFAULT NULL,
  `deleted` tinyint(4) NOT NULL DEFAULT '0',
  `year` int(11) DEFAULT NULL,
  `month` int(11) DEFAULT NULL,
  `day` int(11) DEFAULT NULL,
  PRIMARY KEY (`previewId`)
) ENGINE=InnoDB AUTO_INCREMENT=1094 DEFAULT CHARSET=utf8mb4 COMMENT='五、预检数据表t_preview';

-- ----------------------------
-- Table structure for t_preview_2018_10
-- ----------------------------
DROP TABLE IF EXISTS `t_preview_2018_10`;
CREATE TABLE `t_preview_2018_10` (
  `previewId` int(30) NOT NULL AUTO_INCREMENT,
  `dateTime` varchar(40) DEFAULT '' COMMENT '称重系统检测车的时间',
  `lane` int(4) DEFAULT NULL COMMENT '车道',
  `vehicleType` varchar(200) DEFAULT '' COMMENT '车型',
  `speed` int(4) DEFAULT NULL COMMENT '车速',
  `axleCnt` int(4) DEFAULT NULL COMMENT '轴数',
  `axleDis` int(4) DEFAULT NULL COMMENT '轴距',
  `direction` tinyint(2) DEFAULT '1' COMMENT '车向{1+正向} {0- 反向}',
  `sumWeight` double(10,3) DEFAULT NULL COMMENT '总重已经放大100倍',
  `carNum` varchar(200) DEFAULT '' COMMENT '车牌号',
  `station` varchar(200) DEFAULT '' COMMENT '站点',
  `length` int(11) DEFAULT NULL COMMENT '长（备用字段）',
  `width` int(11) DEFAULT NULL COMMENT '宽（备用字段）',
  `height` int(11) DEFAULT NULL COMMENT '高（备用字段）',
  `frontPic` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '26、车前图片文件',
  `backPic` varchar(100) DEFAULT NULL COMMENT '车后图片文件',
  `picbak` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '图片备用字段',
  `movie` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '视频',
  `tonId` int(20) DEFAULT NULL,
  `overLoadRate` int(4) DEFAULT NULL COMMENT '穿过来的超限率',
  `overLoadId` int(20) DEFAULT NULL COMMENT '超限率外键',
  `overRage` double(10,3) DEFAULT NULL COMMENT '超出多少吨',
  `recognition` int(4) DEFAULT NULL COMMENT '0 未识别  1  识别',
  `date` varchar(20) DEFAULT NULL,
  `venifyPreview` int(10) DEFAULT '0',
  `sidePic` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `upPic` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `limitWeight` int(100) DEFAULT NULL,
  `createTime` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `stationId` int(10) DEFAULT NULL,
  `deleted` tinyint(4) NOT NULL DEFAULT '0',
  `year` int(11) DEFAULT NULL,
  `month` int(11) DEFAULT NULL,
  `day` int(11) DEFAULT NULL,
  PRIMARY KEY (`previewId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='五、预检数据表t_preview';

-- ----------------------------
-- Table structure for t_preview_img_upload
-- ----------------------------
DROP TABLE IF EXISTS `t_preview_img_upload`;
CREATE TABLE `t_preview_img_upload` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `previewId` int(30) NOT NULL,
  `picName` varchar(256) DEFAULT NULL,
  `res` int(4) DEFAULT NULL,
  `uploadTime` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2104 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_preview_record
-- ----------------------------
DROP TABLE IF EXISTS `t_preview_record`;
CREATE TABLE `t_preview_record` (
  `recordId` int(40) NOT NULL AUTO_INCREMENT,
  `previewId` int(30) DEFAULT NULL,
  `dateTime` varchar(40) DEFAULT '' COMMENT '称重系统检测车的时间',
  `lane` int(4) DEFAULT NULL COMMENT '车道',
  `vehicleType` varchar(200) DEFAULT '' COMMENT '车型',
  `speed` int(4) DEFAULT NULL COMMENT '车速',
  `axleCnt` int(4) DEFAULT NULL COMMENT '轴数',
  `axleDis` int(4) DEFAULT NULL COMMENT '轴距',
  `direction` tinyint(2) DEFAULT '1' COMMENT '车向{1+正向} {0- 反向}',
  `sumWeight` double(10,3) DEFAULT NULL COMMENT '总重已经放大100倍',
  `carNum` varchar(200) DEFAULT '' COMMENT '车牌号',
  `stationMark` varchar(100) DEFAULT NULL COMMENT '站点编号',
  `station` varchar(200) DEFAULT '' COMMENT '站点',
  `length` int(11) DEFAULT NULL COMMENT '长（备用字段）',
  `width` int(11) DEFAULT NULL COMMENT '宽（备用字段）',
  `height` int(11) DEFAULT NULL COMMENT '高（备用字段）',
  `frontPic` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '26、车前图片文件',
  `backPic` varchar(100) DEFAULT NULL COMMENT '车后图片文件',
  `picbak` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '图片备用字段',
  `movie` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '视频',
  `tonId` int(20) DEFAULT NULL,
  `overLoadRate` int(4) DEFAULT NULL COMMENT '穿过来的超限率',
  `overLoadId` int(20) DEFAULT NULL COMMENT '超限率外键',
  `overRage` double(10,3) DEFAULT NULL COMMENT '超出多少吨',
  `recognition` int(4) DEFAULT NULL COMMENT '0 未识别  1  识别',
  `date` varchar(20) DEFAULT NULL,
  `venifyPreview` int(10) DEFAULT '0',
  `sidePic` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `upPic` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `limitWeight` int(100) DEFAULT NULL,
  `createTime` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `stationId` int(10) DEFAULT NULL,
  `deleted` tinyint(4) DEFAULT '0',
  `year` int(11) DEFAULT NULL,
  `month` int(11) DEFAULT NULL,
  `day` int(11) DEFAULT NULL,
  `axleWeight1` double(10,3) DEFAULT NULL,
  `axleWeight2` double(10,3) DEFAULT NULL,
  `axleWeight3` double(10,3) DEFAULT NULL,
  `axleWeight4` double(10,3) DEFAULT NULL,
  `axleWeight5` double(10,3) DEFAULT NULL,
  `axleWeight6` double(10,3) DEFAULT NULL,
  `axleWeight7` double(10,3) DEFAULT NULL,
  `axleWeight8` double(10,3) DEFAULT NULL,
  `checkNo` varchar(64) DEFAULT NULL,
  `platePic` varchar(100) DEFAULT NULL,
  `vedioPath` varchar(100) DEFAULT NULL,
  `screenPic` varchar(100) DEFAULT NULL,
  `snapScreen` tinyint(4) DEFAULT NULL,
  `empId` int(40) DEFAULT NULL COMMENT '操作人id',
  `handleTime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`recordId`)
) ENGINE=InnoDB AUTO_INCREMENT=3504 DEFAULT CHARSET=utf8mb4 COMMENT='预检数据操作记录表t_preview_record';

-- ----------------------------
-- Table structure for t_preview_upload
-- ----------------------------
DROP TABLE IF EXISTS `t_preview_upload`;
CREATE TABLE `t_preview_upload` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `previewId` int(30) NOT NULL,
  `res` int(4) DEFAULT NULL,
  `uploadTime` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1028 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_reverse
-- ----------------------------
DROP TABLE IF EXISTS `t_reverse`;
CREATE TABLE `t_reverse` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `createTime` varchar(50) DEFAULT NULL,
  `snapTime` varchar(48) DEFAULT NULL,
  `lane` tinyint(10) DEFAULT NULL,
  `frontPic` varchar(256) DEFAULT NULL,
  `carNum` varchar(50) DEFAULT NULL,
  `stationName` varchar(50) DEFAULT NULL COMMENT '站点',
  `stationId` int(50) DEFAULT NULL COMMENT '站点id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4617 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` varchar(30) NOT NULL COMMENT '角色ID',
  `name` varchar(50) DEFAULT NULL COMMENT '角色名称',
  `domain` varchar(50) DEFAULT NULL COMMENT '域',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_role_menu`;
CREATE TABLE `t_role_menu` (
  `menuId` int(10) NOT NULL,
  `roleId` varchar(20) NOT NULL,
  PRIMARY KEY (`menuId`,`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_role_permission`;
CREATE TABLE `t_role_permission` (
  `role_id` varchar(30) NOT NULL,
  `permission_id` varchar(30) NOT NULL,
  PRIMARY KEY (`role_id`,`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_station
-- ----------------------------
DROP TABLE IF EXISTS `t_station`;
CREATE TABLE `t_station` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stationName` varchar(32) NOT NULL,
  `stationCode` varchar(50) DEFAULT NULL COMMENT '站点编码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_systemset
-- ----------------------------
DROP TABLE IF EXISTS `t_systemset`;
CREATE TABLE `t_systemset` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `key` varchar(64) DEFAULT '' COMMENT '属性',
  `val` varchar(512) DEFAULT NULL COMMENT '属性值',
  `comment` varchar(256) DEFAULT NULL COMMENT '说明',
  PRIMARY KEY (`id`),
  UNIQUE KEY `key_UNIQUE` (`key`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COMMENT='系统设置';

-- ----------------------------
-- Table structure for t_ton
-- ----------------------------
DROP TABLE IF EXISTS `t_ton`;
CREATE TABLE `t_ton` (
  `tonId` int(4) NOT NULL DEFAULT '0',
  `tonRegion` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`tonId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_user_node
-- ----------------------------
DROP TABLE IF EXISTS `t_user_node`;
CREATE TABLE `t_user_node` (
  `empId` varchar(100) NOT NULL COMMENT '用户id',
  `nodeCode` varchar(100) NOT NULL COMMENT '设备id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户设备权限表';

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `emp_name` varchar(100) NOT NULL,
  `role_id` varchar(100) NOT NULL,
  PRIMARY KEY (`emp_name`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_user_station
-- ----------------------------
DROP TABLE IF EXISTS `t_user_station`;
CREATE TABLE `t_user_station` (
  `userId` int(30) NOT NULL COMMENT '用户id',
  `stationId` int(30) NOT NULL COMMENT '站点Id',
  `stationCode` varchar(50) DEFAULT NULL COMMENT '站点编码'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
