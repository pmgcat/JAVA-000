## 这里只列出了一些基本的信息表，其他商品快照表，支付后产生的交易信息，账单信息等其他可能的相关表这里暂不一一列出

DROP TABLE IF EXISTS `logistics`;

CREATE TABLE `logistics` (
  `id` bigint(20) unsigned NOT NULL COMMENT '物流信息ID，统一生成',
  `logistics_code` varchar(32) DEFAULT NULL COMMENT '物流编码',
  `lat` smallint(6) NOT NULL COMMENT '经度，精确到小数点后三位',
  `lon` smallint(6) NOT NULL COMMENT '纬度，精确到小数点后三位',
  `status` tinyint(4) unsigned NOT NULL COMMENT '当前状态，1=已下单，2=已接单，3=配送中，4=已收货，5=退回中，6=已退回',
  `del_flag` tinyint(11) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除标记，0=未删除，大于0即已删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_logistics_code` (`logistics_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物流配送信息表';



# Dump of table product_base_info
# ------------------------------------------------------------

DROP TABLE IF EXISTS `product_base_info`;

CREATE TABLE `product_base_info` (
  `id` bigint(20) unsigned NOT NULL COMMENT '商品ID，统一生成',
  `user_id` bigint(20) unsigned NOT NULL COMMENT '所属用户（卖家）ID',
  `barcode` varchar(32) NOT NULL DEFAULT '' COMMENT '商品条码',
  `title` varchar(80) NOT NULL,
  `price` bigint(20) unsigned NOT NULL COMMENT '价格，单位分',
  `category` int(11) unsigned NOT NULL COMMENT '类别',
  `stock` int(11) unsigned NOT NULL COMMENT '库存',
  `status` tinyint(4) unsigned NOT NULL DEFAULT '1' COMMENT '商品状态，1=正常，2=售罄，3=预定中',
  `cover` varchar(128) NOT NULL DEFAULT '' COMMENT '封面地址，多个按逗号分隔',
  `off_tag` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '下架标记，0=上架，1=下架',
  `del_flag` tinyint(11) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除标记，0=未删除，大于0即已删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `uk_product_code_off_tag` (`barcode`,`off_tag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户配送信息表';



# Dump of table product_detail
# ------------------------------------------------------------

DROP TABLE IF EXISTS `product_detail`;

CREATE TABLE `product_detail` (
  `id` bigint(20) unsigned NOT NULL COMMENT 'product_base_info.id',
  `weight` int(10) unsigned NOT NULL COMMENT '重量，单位克',
  `length` int(10) unsigned NOT NULL COMMENT '长，单位mm',
  `width` int(10) unsigned NOT NULL COMMENT '宽，单位mm',
  `height` int(10) unsigned NOT NULL COMMENT '高，单位mm',
  `origin` int(10) unsigned NOT NULL COMMENT '产地',
  `detail_img_url` varchar(1024) NOT NULL DEFAULT '' COMMENT '详情图片地址，多个按逗号分隔',
  `del_flag` tinyint(11) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除标记，0=未删除，大于0即已删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品详情信息表';



# Dump of table product_order
# ------------------------------------------------------------

DROP TABLE IF EXISTS `product_order`;

CREATE TABLE `product_order` (
  `id` bigint(20) unsigned NOT NULL COMMENT '订单ID，统一生成',
  `user_id` bigint(20) unsigned NOT NULL COMMENT '买家ID',
  `product_id` bigint(20) unsigned NOT NULL COMMENT '商品ID',
  `delivery_info_snap_id` bigint(20) unsigned NOT NULL COMMENT '配送信息快照ID',
  `logistics_id` bigint(20) unsigned DEFAULT '1' COMMENT '物流信息ID',
  `status` tinyint(4) unsigned NOT NULL COMMENT '订单状态，1=未付款，2=取消中，3=已取消，4=配送中，5=已收货，6=已完成，7=退款待审核，8=退款审核中，9=退款中，10=已退款',
  `order_time` bigint(20) unsigned NOT NULL COMMENT '下单时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_buyer_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户订单信息表';



# Dump of table user_(seller_other_info)
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_(seller_other_info)`;

CREATE TABLE `user_(seller_other_info)` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'user_account.id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户卖家其他信息';



# Dump of table user_account
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_account`;

CREATE TABLE `user_account` (
  `id` bigint(20) unsigned NOT NULL COMMENT '用户ID，统一生成',
  `password` varchar(36) NOT NULL DEFAULT '' COMMENT '用户密码',
  `del_flag` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除标记，0=未删除，大于0即已删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户账户表';



# Dump of table user_account_bind
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_account_bind`;

CREATE TABLE `user_account_bind` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) unsigned NOT NULL COMMENT 'user_account.id',
  `account` varchar(40) NOT NULL DEFAULT '' COMMENT '账号',
  `account_type` tinyint(3) unsigned NOT NULL COMMENT '账号类型, 1=平台账号，2=手机号',
  `del_flag` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除标记，0=未删除，大于0即已删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_id` (`user_id`),
  UNIQUE KEY `uk_account_type_del_flag` (`account`,`account_type`,`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户账号绑定信息表';



# Dump of table user_base_info
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_base_info`;

CREATE TABLE `user_base_info` (
  `id` bigint(20) unsigned NOT NULL COMMENT 'user_account.id',
  `nickname` varchar(32) NOT NULL DEFAULT '' COMMENT '用户昵称',
  `avatar_url` varchar(128) NOT NULL DEFAULT '' COMMENT '头像地址',
  `gender` tinyint(1) NOT NULL COMMENT '性别，-1=未知，1=女，2=男',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `description` int(11) NOT NULL COMMENT '简介',
  `del_flag` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除标记，0=未删除，大于0即已删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户基本信息表';



# Dump of table user_delivery_info
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_delivery_info`;

CREATE TABLE `user_delivery_info` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` tinyint(20) unsigned NOT NULL COMMENT 'user_account.id',
  `receiver` varchar(24) NOT NULL DEFAULT '' COMMENT '收货人',
  `country` tinyint(4) NOT NULL DEFAULT '1' COMMENT '国家地区，1=中国',
  `province` tinyint(4) unsigned NOT NULL COMMENT '省，州',
  `city` smallint(6) unsigned NOT NULL COMMENT '市',
  `county` tinyint(4) unsigned NOT NULL COMMENT '县，区，郡',
  `address` varchar(72) NOT NULL DEFAULT '' COMMENT '详细地址',
  `phone` varchar(16) NOT NULL DEFAULT '' COMMENT '手机号，格式例如+86-18628129999',
  `del_flag` tinyint(11) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除标记，0=未删除，大于0即已删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户配送信息表';



# Dump of table user_delivery_info_snap
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_delivery_info_snap`;

CREATE TABLE `user_delivery_info_snap` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) unsigned NOT NULL COMMENT 'user.id',
  `receiver` varchar(24) NOT NULL DEFAULT '' COMMENT '收货人',
  `country` tinyint(4) unsigned NOT NULL DEFAULT '1' COMMENT '国家地区，1=中国',
  `province` tinyint(4) unsigned NOT NULL COMMENT '省，州',
  `city` smallint(6) unsigned NOT NULL COMMENT '市',
  `county` tinyint(4) unsigned NOT NULL COMMENT '县，区，郡',
  `address` varchar(72) NOT NULL DEFAULT '' COMMENT '详细地址',
  `phone` varchar(16) NOT NULL DEFAULT '' COMMENT '手机号，格式例如+86-18628129999',
  `del_flag` tinyint(11) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除标记，0=未删除，大于0即已删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户配送信息快照表';



# Dump of table user_role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) unsigned DEFAULT NULL COMMENT 'user_account.id',
  `type` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '角色类型，1=买家，2=普通卖家，255=平台自营',
  `del_flag` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除标记，0=未删除，大于0即已删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色表';



# Dump of table user_shopping
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_shopping`;

CREATE TABLE `user_shopping` (
  `id` bigint(20) unsigned NOT NULL COMMENT '购物车ID，统一生成',
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户ID',
  `product_id` bigint(20) unsigned NOT NULL COMMENT '商品ID',
  `count` smallint(5) unsigned NOT NULL COMMENT '选购数量',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_id_product_id` (`user_id`,`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户商品选购表（购物车）';




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
