ALTER TABLE `jm`.`t_previewafter_preview` 
ADD COLUMN `uploaded` TINYINT NULL AFTER `limitWeight`,
ADD COLUMN `uploadTime` VARCHAR(30) NULL AFTER `uploaded`;

ALTER TABLE `jm`.`t_preview` 
ADD COLUMN `axleWeight1` DOUBLE(10,3) NULL AFTER `day`,
ADD COLUMN `axleWeight2` DOUBLE(10,3) NULL AFTER `axleWeight1`,
ADD COLUMN `axleWeight3` DOUBLE(10,3) NULL AFTER `axleWeight2`,
ADD COLUMN `axleWeight4` DOUBLE(10,3) NULL AFTER `axleWeight3`,
ADD COLUMN `axleWeight5` DOUBLE(10,3) NULL AFTER `axleWeight4`,
ADD COLUMN `axleWeight6` DOUBLE(10,3) NULL AFTER `axleWeight5`,
ADD COLUMN `axleWeight7` DOUBLE(10,3) NULL AFTER `axleWeight6`,
ADD COLUMN `axleWeight8` DOUBLE(10,3) NULL AFTER `axleWeight7`;


CREATE TABLE `t_preview_upload` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `previewId` int(30) NOT NULL,
  `res` int(4) DEFAULT NULL ,
  `uploadTime` varchar(30) ,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

CREATE TABLE `t_preview_img_upload` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `previewId` int(30) NOT NULL,
  `picName` varchar(256) DEFAULT NULL ,
  `res` int(4) DEFAULT NULL ,
  `uploadTime` varchar(30) ,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

ALTER TABLE `jm`.`t_previewafter_preview` 
ADD COLUMN `checkNo` VARCHAR(64) NULL AFTER `uploadTime`;
ALTER TABLE `jm`.`t_preview` 
ADD COLUMN `checkNo` VARCHAR(64) NULL AFTER `axleWeight8`;


DELETE FROM `jm`.`t_systemset` WHERE `id`='4';
DELETE FROM `jm`.`t_systemset` WHERE `id`='5';
DELETE FROM `jm`.`t_systemset` WHERE `id`='6';
DELETE FROM `jm`.`t_systemset` WHERE `id`='7';


INSERT INTO `jm`.`t_systemset` (`key`, `val`) VALUES ('station.code', 'HA5028');
INSERT INTO `jm`.`t_systemset` (`key`, `val`) VALUES ('station.short', '非现场执法');
INSERT INTO `jm`.`t_systemset` (`key`, `val`) VALUES ('station.ip', '192.168.5.86');

ALTER TABLE `jm`.`t_previewafter_preview` 
ADD COLUMN `axleWeight1` DOUBLE(10,3) NULL AFTER `checkNo`,
ADD COLUMN `axleWeight2` DOUBLE(10,3) NULL AFTER `axleWeight1`,
ADD COLUMN `axleWeight3` DOUBLE(10,3) NULL AFTER `axleWeight2`,
ADD COLUMN `axleWeight4` DOUBLE(10,3) NULL AFTER `axleWeight3`,
ADD COLUMN `axleWeight5` DOUBLE(10,3) NULL AFTER `axleWeight4`,
ADD COLUMN `axleWeight6` DOUBLE(10,3) NULL AFTER `axleWeight5`,
ADD COLUMN `axleWeight7` DOUBLE(10,3) NULL AFTER `axleWeight6`,
ADD COLUMN `axleWeight8` DOUBLE(10,3) NULL AFTER `axleWeight7`;

