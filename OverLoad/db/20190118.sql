ALTER TABLE `jm`.`t_role_permission` 
CHANGE COLUMN `role_id` `role_id` VARCHAR(30) NOT NULL ;

UPDATE `jm`.`t_role` SET `id`='admin' WHERE `id`='1';
update t_role_permission set role_id='admin' where role_id='1';
update t_role_menu set roleId = 'admin' where roleId='1';
update t_user_role set role_id = 'admin' where role_id = '1';

INSERT INTO `jm`.`t_permission` (`id`, `name`, `domain`) VALUES ('reviewed:delete', '精简删除', 'admin');
INSERT INTO `jm`.`t_permission` (`id`, `name`, `domain`) VALUES ('reviewed:export', '精简导出', 'admin');
INSERT INTO `jm`.`t_permission` (`id`, `name`, `domain`) VALUES ('user:viewAll', '用户查询', 'admin');
