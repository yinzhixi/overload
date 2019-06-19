use jm;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO `jm`.`t_dict` (`id`, `type`, `code`, `name`, `sq`) VALUES ('1', 'direction', '1', '西向东', '1');
INSERT INTO `jm`.`t_dict` (`id`, `type`, `code`, `name`, `sq`) VALUES ('2', 'direction', '2', '东向西', '2');
INSERT INTO `jm`.`t_dict` (`id`, `type`, `code`, `name`, `sq`) VALUES ('3', 'vehicleType', '0', '未知', '1');
INSERT INTO `jm`.`t_dict` (`id`, `type`, `code`, `name`, `sq`) VALUES ('4', 'vehicleType', '1', '客车', '2');
INSERT INTO `jm`.`t_dict` (`id`, `type`, `code`, `name`, `sq`) VALUES ('5', 'vehicleType', '2', '货车', '3');
INSERT INTO `jm`.`t_dict` (`id`, `type`, `code`, `name`, `sq`) VALUES ('6', 'vehicleType', '3', '轿车', '4');
INSERT INTO `jm`.`t_dict` (`id`, `type`, `code`, `name`, `sq`) VALUES ('7', 'vehicleType', '4', '面包车', '5');
INSERT INTO `jm`.`t_dict` (`id`, `type`, `code`, `name`, `sq`) VALUES ('8', 'vehicleType', '5', '小货车', '6');

#2018-08-07
alter table t_previewafter_preview add column limitWeight int(10);

