
UPDATE `jm`.`t_overloadrate` SET `overLoadRateRegion`='100%-200%' WHERE `overLoadId`='5';
UPDATE `jm`.`t_overloadrate` SET `overLoadRateRegion`='>200%' WHERE `overLoadId`='6';
INSERT INTO `jm`.`t_overloadrate` (`overLoadId`, `overLoadRateRegion`) VALUES ('7', '合计');

update t_preview set overLoadId = 7 where overLoadId = 6;

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
  `port` int DEFAULT NULL,
  `reqTime` varchar(32) DEFAULT NULL,
  `description` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE `jm`.`t_preview` 
ADD COLUMN `deleted` TINYINT NOT NULL DEFAULT 0 AFTER `stationId`;
INSERT INTO `jm`.`t_menu` (`menuId`, `parentId`, `menuLevel`, `sort`, `name`, `path`) VALUES ('26', '13', '2', '101', '系統日志', '/system/log');

