
INSERT INTO `t_dict` VALUES ('16', 'vehicleType', '6', '行人', null, '1', '2019-01-07 09:08:27', null, null, null);
INSERT INTO `t_dict` VALUES ('17', 'vehicleType', '7', '二轮车', null, '2', '2019-01-07 09:08:27', null, null, null);
INSERT INTO `t_dict` VALUES ('18', 'vehicleType', '8', '三轮车', null, '3', '2019-01-07 09:08:27', null, null, null);
INSERT INTO `t_dict` VALUES ('19', 'vehicleType', '9', 'SUV/MPV', null, '4', '2019-01-07 09:08:27', null, null, null);
INSERT INTO `t_dict` VALUES ('20', 'vehicleType', '10', '中型客车', null, '5', '2019-01-07 09:08:27', null, null, null);
INSERT INTO `t_dict` VALUES ('21', 'vehicleType', '11', '机动车', null, '6', '2019-01-07 09:08:27', null, null, null);
INSERT INTO `t_dict` VALUES ('22', 'vehicleType', '12', '非机动车', null, '3', '2019-01-07 09:08:27', null, null, null);
INSERT INTO `t_dict` VALUES ('23', 'vehicleType', '13', '小型轿车', null, '4', '2019-01-07 09:08:27', null, null, null);
INSERT INTO `t_dict` VALUES ('24', 'vehicleType', '14', '微型轿车', null, '5', '2019-01-07 09:08:27', null, null, null);
INSERT INTO `t_dict` VALUES ('25', 'vehicleType', '15', '皮卡车', null, '6', '2019-01-07 09:08:27', null, null, null);


INSERT INTO `t_systemset` VALUES ('17', 'print.view', '1', '打印页面模板');
INSERT INTO `t_systemset` VALUES ('18', 'verification', '轴重字20181101-2491', '检定证书编号');

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
) ENGINE=InnoDB AUTO_INCREMENT=3480 DEFAULT CHARSET=utf8mb4 COMMENT='预检数据操作记录表t_preview_record';
