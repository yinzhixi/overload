
INSERT INTO `jm`.`t_dict` (`type`, `code`, `name`, `sq`) VALUES ('direction', '0', '����', '0');
INSERT INTO `jm`.`t_dict` (`type`, `code`, `name`, `sq`) VALUES ('direction', '3', '����', '3');
INSERT INTO `jm`.`t_dict` (`type`, `code`, `name`, `sq`) VALUES ('direction', '4', '������', '4');
INSERT INTO `jm`.`t_dict` (`type`, `code`, `name`, `sq`) VALUES ('direction', '5', '����������', '5');
INSERT INTO `jm`.`t_dict` (`type`, `code`, `name`, `sq`) VALUES ('direction', '6', '��������', '6');
INSERT INTO `jm`.`t_dict` (`type`, `code`, `name`, `sq`) VALUES ('direction', '7', '����������', '7');
INSERT INTO `jm`.`t_dict` (`type`, `code`, `name`, `sq`) VALUES ('direction', '8', '�����򶫱�', '8');

alter table t_reverse add column snapTime varchar(48) null after createTime;
