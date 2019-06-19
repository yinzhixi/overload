ALTER TABLE `jm`.`t_preview` 
ADD COLUMN `cardPic` VARCHAR(100) NULL AFTER `checkNo`,
ADD COLUMN `vedioPath` VARCHAR(100) NULL AFTER `cardPic`;
ALTER TABLE `jm`.`t_preview` 
CHANGE COLUMN `cardPic` `platePic` VARCHAR(100) NULL DEFAULT NULL ,
ADD COLUMN `screenPic` VARCHAR(100) NULL AFTER `vedioPath`;
ALTER TABLE `jm`.`t_preview` 
ADD COLUMN `snapScreen` TINYINT NULL AFTER `screenPic`;
