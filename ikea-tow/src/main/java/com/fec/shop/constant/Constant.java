package com.fec.shop.constant;

import org.apache.log4j.Logger;

public class Constant {
	public static final String tb_category_file = "resources/categroy/tb_categroy";
	public static final String ikea_category_file = "resources/categroy/ikea_categroy";
	public static final String split = "#######";

	public static final int num_product_per_file = 500;

	public static final String ikea_product_file = "resources/product/ikea_product";
	public static final String ikea_product_file_new_add = "resources/ikea_product_new";
	public static final String product_cvs_file = "e:/bikea/108/";

	public static String url = "http://gw.api.taobao.com/router/rest";
	public static String appkey = "21402582";
	public static String appSecret = "daf18bbfd74783b446f989f72f07de87";
	public static String nick = "charick";

	//系统日志
	public static Logger baseLoger = Logger.getLogger("baseLoger");//基本日志
	public static Logger dailyCheepLoger = Logger.getLogger("dailyCheepLoger");//每日优惠
	
	//SQL语句
	public static String Create_main_table="DROP TABLE IF EXISTS `main_table`;CREATE TABLE `main_table` (  `title` varchar(60) DEFAULT NULL,  `cid` varchar(255) DEFAULT '50006298' COMMENT '其他宜家代购',  `seller_cids` varchar(255) DEFAULT NULL COMMENT '店铺类目',  `stuff_status` varchar(255) DEFAULT '1' COMMENT '新旧程度',  `location_state` varchar(255) DEFAULT '北京',  `location_city` varchar(255) DEFAULT '北京',  `item_type` varchar(255) DEFAULT '1',  `price` double DEFAULT '0',  `auction_increment` varchar(255) DEFAULT '0.00',  `num` varchar(255) DEFAULT '58' COMMENT '宝贝数量',  `valid_thru` varchar(255) DEFAULT '7' COMMENT '有效期',  `freight_payer` varchar(255) DEFAULT '2' COMMENT '买家承担',  `post_fee` varchar(255) DEFAULT NULL,  `ems_fee` varchar(255) DEFAULT '0',  `express_fee` varchar(255) DEFAULT '0',  `has_invoice` varchar(255) DEFAULT '0',  `has_warranty` varchar(255) DEFAULT '1',  `approve_status` varchar(255) DEFAULT '1' COMMENT '放入仓库',  `has_showcase` varchar(255) DEFAULT '0' COMMENT '橱窗推荐',  `list_time` varchar(255) DEFAULT NULL COMMENT '开始时间',  `description` text COMMENT '宝贝描述',  `cateProps` varchar(255) DEFAULT NULL COMMENT '宝贝属性',  `postage_id` varchar(255) DEFAULT '755800881' COMMENT '运费模版',  `has_discount` varchar(255) DEFAULT '0' COMMENT '会员打折',  `modified` varchar(255) DEFAULT NULL COMMENT '修改时间',  `upload_fail_msg` varchar(255) DEFAULT '200' COMMENT '上传状态',  `picture_status` varchar(255) DEFAULT NULL COMMENT '图片状态',  `auction_point` varchar(255) DEFAULT '0' COMMENT '返点比例',  `picture` mediumtext,  `video` varchar(255) DEFAULT NULL,  `skuProps` varchar(255) DEFAULT NULL,  `inputPids` varchar(255) DEFAULT NULL,  `inputValues` varchar(255) DEFAULT NULL,  `outer_id` varchar(255) NOT NULL DEFAULT '00000000' COMMENT '商家编码',  `propAlias` varchar(255) DEFAULT NULL,  `auto_fill` varchar(255) DEFAULT '0' COMMENT '代充类型',  `num_id` varchar(255) DEFAULT '' COMMENT '数字ID',  `local_cid` varchar(255) DEFAULT NULL COMMENT '本地ID',  `navigation_type` varchar(255) DEFAULT '2' COMMENT '宝贝分类',  `user_name` varchar(255) DEFAULT 'charick' COMMENT '帐户名',  `syncStatus` varchar(255) DEFAULT '1' COMMENT '宝贝状态',  `is_lighting_consigment` varchar(255) DEFAULT '0' COMMENT '闪电发货',  `is_xinpin` varchar(255) DEFAULT NULL COMMENT '新品',  `foodparame` varchar(255) DEFAULT NULL,  `features` varchar(255) DEFAULT NULL,  `global_stock_type` varchar(255) DEFAULT '72944884' COMMENT '库存类型',  `sub_stock_type` varchar(255) DEFAULT '2' COMMENT '库存计数',  `item_size` varchar(255) DEFAULT NULL,  `item_weight` varchar(255) DEFAULT NULL,  PRIMARY KEY (`outer_id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;";

	//导出CSV
	public static String CSV_l1="version 1.00" + "\n";
	public static String CSV_l2="title	cid	seller_cids	stuff_status	location_state	location_city	item_type	price	auction_increment	num	valid_thru	freight_payer	post_fee	ems_fee	express_fee	has_invoice	has_warranty	approve_status	has_showcase	list_time	description	cateProps	postage_id	has_discount	modified	upload_fail_msg	picture_status	auction_point	picture	video	skuProps	inputPids	inputValues	outer_id	propAlias	auto_fill	num_id	local_cid	navigation_type	user_name	syncStatus	is_lighting_consigment	is_xinpin	foodparame	features	global_stock_type	sub_stock_type"+ "\n";
	public static String CSV_l3="宝贝名称	宝贝类目	店铺类目	新旧程度	省	城市	出售方式	宝贝价格	加价幅度	宝贝数量	有效期	运费承担	平邮	EMS	快递	发票	保修	放入仓库	橱窗推荐	开始时间	宝贝描述	宝贝属性	邮费模版ID	会员打折	修改时间	上传状态	图片状态	返点比例	新图片	视频	销售属性组合	用户输入ID串	用户输入名-值对	商家编码	销售属性别名	代充类型	数字ID	本地ID	宝贝分类	账户名称	宝贝状态	闪电发货	新品	食品专项	尺码库	库存类型	库存计数"+ "\n";
//淘宝标签
	public static String cid="50006298";//其他宜家代购
	public static String stuff_status="1";//新旧程度
	public static String location_state="北京";
	public static String location_city="北京";
	public static String item_type="1";//出售方式
	public static String auction_increment="0.00";
	public static String valid_thru="7";
	
}
