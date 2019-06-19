
INSERT INTO `jm`.`t_dict` (`type`, `code`, `name`, `sq`) VALUES ('direction', '0', '其它', '0');
INSERT INTO `jm`.`t_dict` (`type`, `code`, `name`, `sq`) VALUES ('direction', '3', '南向北', '3');
INSERT INTO `jm`.`t_dict` (`type`, `code`, `name`, `sq`) VALUES ('direction', '4', '北向南', '4');
INSERT INTO `jm`.`t_dict` (`type`, `code`, `name`, `sq`) VALUES ('direction', '5', '东南向西北', '5');
INSERT INTO `jm`.`t_dict` (`type`, `code`, `name`, `sq`) VALUES ('direction', '6', '西北向东南', '6');
INSERT INTO `jm`.`t_dict` (`type`, `code`, `name`, `sq`) VALUES ('direction', '7', '东北向西南', '7');
INSERT INTO `jm`.`t_dict` (`type`, `code`, `name`, `sq`) VALUES ('direction', '8', '西南向东北', '8');

alter table t_reverse add column snapTime varchar(48) null after createTime;
