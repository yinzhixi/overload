CREATE TABLE `t_systemset` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `key` varchar(64) DEFAULT ''  ,
  `val` varchar(512) DEFAULT NULL  ,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
alter table t_systemset add column `comment` varchar(256) DEFAULT NULL;

ALTER TABLE `jm`.`t_systemset` 
ADD UNIQUE INDEX `key_UNIQUE` (`key` ASC);

INSERT INTO `jm`.`t_menu` (`menuId`, `parentId`, `menuLevel`, `sort`, `name`, `path`) VALUES ('27', '13', '2', '90', 'œµÕ≥…Ë÷√', '/system/set');
