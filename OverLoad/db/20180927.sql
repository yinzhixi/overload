ALTER TABLE `t_preview` 
ADD COLUMN `deleted` tinyint(4) NOT NULL DEFAULT '0',
ADD COLUMN `year` INT NULL AFTER `deleted`,
ADD COLUMN `month` INT NULL AFTER `year`,
ADD COLUMN `day` INT NULL AFTER `month`;

update t_preview set year = year(dateTime),month = month(dateTime),day=day(dateTime);
