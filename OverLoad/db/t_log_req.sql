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
  `port` int(11) DEFAULT NULL,
  `reqTime` varchar(32) DEFAULT NULL,
  `description` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
