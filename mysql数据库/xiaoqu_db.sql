/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50051
Source Host           : localhost:3306
Source Database       : xiaoqu_db

Target Server Type    : MYSQL
Target Server Version : 50051
File Encoding         : 65001

Date: 2019-09-03 17:34:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `username` varchar(20) NOT NULL default '',
  `password` varchar(32) default NULL,
  PRIMARY KEY  (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('a', 'a');

-- ----------------------------
-- Table structure for `t_building`
-- ----------------------------
DROP TABLE IF EXISTS `t_building`;
CREATE TABLE `t_building` (
  `buildingId` int(11) NOT NULL auto_increment COMMENT '楼栋id',
  `buildingName` varchar(20) NOT NULL COMMENT '楼栋名称',
  `buildingMemo` varchar(500) default NULL COMMENT '楼栋备注',
  PRIMARY KEY  (`buildingId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_building
-- ----------------------------
INSERT INTO `t_building` VALUES ('1', '6栋', '小区第6栋楼');
INSERT INTO `t_building` VALUES ('2', '7栋', '7栋居民楼');

-- ----------------------------
-- Table structure for `t_facility`
-- ----------------------------
DROP TABLE IF EXISTS `t_facility`;
CREATE TABLE `t_facility` (
  `facilityId` int(11) NOT NULL auto_increment COMMENT '设施id',
  `facilityName` varchar(20) NOT NULL COMMENT '设施名称',
  `facilityPhoto` varchar(60) NOT NULL COMMENT '设施照片',
  `purpose` varchar(500) NOT NULL COMMENT '设施用途',
  `bornDate` varchar(20) default NULL COMMENT '投放日期',
  `fuzeren` varchar(20) NOT NULL COMMENT '负责人',
  `telephone` varchar(20) NOT NULL COMMENT '联系电话',
  `facilityMemo` varchar(800) default NULL COMMENT '设施备注',
  PRIMARY KEY  (`facilityId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_facility
-- ----------------------------
INSERT INTO `t_facility` VALUES ('1', '小孩子游乐场', 'upload/a9624fb3-9a7e-41f3-aece-6fc8848a464e.jpg', '小孩子们，都来这里玩吧', '2019-08-30', '王涛', '13903083421', '刚建成的新场所');
INSERT INTO `t_facility` VALUES ('2', '小区游泳池', 'upload/118f5960-8367-4d4b-80d7-b5880aac45f6.jpg', '用于小区业主游泳健身', '2019-09-03', '李炳刚', '13408204293', '来游泳了');

-- ----------------------------
-- Table structure for `t_fee`
-- ----------------------------
DROP TABLE IF EXISTS `t_fee`;
CREATE TABLE `t_fee` (
  `feeId` int(11) NOT NULL auto_increment COMMENT '缴费id',
  `feeClassObj` int(11) NOT NULL COMMENT '费用类别',
  `year` varchar(20) NOT NULL COMMENT '费用年份',
  `month` varchar(20) NOT NULL COMMENT '费用月份',
  `userCount` varchar(20) NOT NULL COMMENT '使用量',
  `feeValue` float NOT NULL COMMENT '应缴金额',
  `userObj` varchar(30) NOT NULL COMMENT '缴费住户',
  `feeState` varchar(20) NOT NULL COMMENT '缴费状态',
  `feeMemo` varchar(500) default NULL COMMENT '缴费备注',
  PRIMARY KEY  (`feeId`),
  KEY `feeClassObj` (`feeClassObj`),
  KEY `userObj` (`userObj`),
  CONSTRAINT `t_fee_ibfk_1` FOREIGN KEY (`feeClassObj`) REFERENCES `t_feeclass` (`classId`),
  CONSTRAINT `t_fee_ibfk_2` FOREIGN KEY (`userObj`) REFERENCES `t_userinfo` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_fee
-- ----------------------------
INSERT INTO `t_fee` VALUES ('1', '1', '2019', '8', '8吨', '28', 'user1', '待缴费', '测试');
INSERT INTO `t_fee` VALUES ('2', '2', '2019', '8', '62度', '34.5', 'user1', '已缴费', '测试');
INSERT INTO `t_fee` VALUES ('3', '1', '2019', '8', '68度', '39', 'user2', '待缴费', 'test');

-- ----------------------------
-- Table structure for `t_feeclass`
-- ----------------------------
DROP TABLE IF EXISTS `t_feeclass`;
CREATE TABLE `t_feeclass` (
  `classId` int(11) NOT NULL auto_increment COMMENT '费用类别编号',
  `className` varchar(20) NOT NULL COMMENT '费用类别名称',
  PRIMARY KEY  (`classId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_feeclass
-- ----------------------------
INSERT INTO `t_feeclass` VALUES ('1', '水费');
INSERT INTO `t_feeclass` VALUES ('2', '电费');

-- ----------------------------
-- Table structure for `t_notice`
-- ----------------------------
DROP TABLE IF EXISTS `t_notice`;
CREATE TABLE `t_notice` (
  `noticeId` int(11) NOT NULL auto_increment COMMENT '公告id',
  `title` varchar(80) NOT NULL COMMENT '标题',
  `content` varchar(5000) NOT NULL COMMENT '公告内容',
  `publishDate` varchar(20) default NULL COMMENT '发布时间',
  PRIMARY KEY  (`noticeId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_notice
-- ----------------------------
INSERT INTO `t_notice` VALUES ('1', '社区平台成立了', '<p>小区的业主朋友们，可以来这里交流讨论，查询你家的水电费哈！</p><p>也可以反映违法的住户和上报维护信息哦！</p>', '2019-08-30 16:51:02');

-- ----------------------------
-- Table structure for `t_postinfo`
-- ----------------------------
DROP TABLE IF EXISTS `t_postinfo`;
CREATE TABLE `t_postinfo` (
  `postInfoId` int(11) NOT NULL auto_increment COMMENT '帖子id',
  `title` varchar(80) NOT NULL COMMENT '帖子标题',
  `content` varchar(5000) NOT NULL COMMENT '帖子内容',
  `hitNum` int(11) NOT NULL COMMENT '浏览量',
  `userObj` varchar(30) NOT NULL COMMENT '发帖人',
  `addTime` varchar(20) default NULL COMMENT '发帖时间',
  PRIMARY KEY  (`postInfoId`),
  KEY `userObj` (`userObj`),
  CONSTRAINT `t_postinfo_ibfk_1` FOREIGN KEY (`userObj`) REFERENCES `t_userinfo` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_postinfo
-- ----------------------------
INSERT INTO `t_postinfo` VALUES ('1', '小区是大家的小区', '<p>大家要一起爱护小区的环境，做一个有素质的人！</p>', '6', 'user1', '2019-08-30 16:50:04');
INSERT INTO `t_postinfo` VALUES ('2', '周末有一起来小区打乒乓球的吗', '<p>周末想锻炼下身体，本人爱好乒乓球，谁来和我一决高低！</p>', '4', 'user2', '2019-09-03 16:36:10');

-- ----------------------------
-- Table structure for `t_repair`
-- ----------------------------
DROP TABLE IF EXISTS `t_repair`;
CREATE TABLE `t_repair` (
  `repairId` int(11) NOT NULL auto_increment COMMENT '报修id',
  `repairClassObj` int(11) NOT NULL COMMENT '报修分类',
  `title` varchar(20) NOT NULL COMMENT '问题简述',
  `content` varchar(8000) NOT NULL COMMENT '问题详情',
  `handleState` varchar(20) NOT NULL COMMENT '处理状态',
  `handResult` varchar(5000) NOT NULL COMMENT '处理结果',
  `uploadTime` varchar(20) default NULL COMMENT '上报时间',
  `userObj` varchar(30) NOT NULL COMMENT '上报住户',
  PRIMARY KEY  (`repairId`),
  KEY `repairClassObj` (`repairClassObj`),
  KEY `userObj` (`userObj`),
  CONSTRAINT `t_repair_ibfk_1` FOREIGN KEY (`repairClassObj`) REFERENCES `t_repairclass` (`repairClassId`),
  CONSTRAINT `t_repair_ibfk_2` FOREIGN KEY (`userObj`) REFERENCES `t_userinfo` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_repair
-- ----------------------------
INSERT INTO `t_repair` VALUES ('1', '1', '我家电灯坏了', '<p>昨天晚上电灯闪了几下就熄火了，然后再也不亮了</p>', '待处理', '<p>--<br/></p>', '2019-09-03 11:55:53', 'user1');
INSERT INTO `t_repair` VALUES ('2', '2', '防盗窗的缝隙太紧关不上', '<p><img src=\"/JavaWebProject/upload/20190903/1567501097193044312.jpg\" title=\"1567501097193044312.jpg\" alt=\"1.jpg\" width=\"396\" height=\"400\"/><br/><br/>最近刚发现窗口很难关闭上，缝隙太紧了，怎么用力都不行，麻烦派人来看看！</p>', '处理中', '<p>已经安排处理人员</p>', '2019-09-03 13:10:52', 'user2');

-- ----------------------------
-- Table structure for `t_repairclass`
-- ----------------------------
DROP TABLE IF EXISTS `t_repairclass`;
CREATE TABLE `t_repairclass` (
  `repairClassId` int(11) NOT NULL auto_increment COMMENT '报修分类id',
  `repairClassName` varchar(20) NOT NULL COMMENT '报修分类名称',
  PRIMARY KEY  (`repairClassId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_repairclass
-- ----------------------------
INSERT INTO `t_repairclass` VALUES ('1', '水电问题');
INSERT INTO `t_repairclass` VALUES ('2', '门窗问题');

-- ----------------------------
-- Table structure for `t_reply`
-- ----------------------------
DROP TABLE IF EXISTS `t_reply`;
CREATE TABLE `t_reply` (
  `replyId` int(11) NOT NULL auto_increment COMMENT '回复id',
  `postInfoObj` int(11) NOT NULL COMMENT '被回帖子',
  `content` varchar(2000) NOT NULL COMMENT '回复内容',
  `userObj` varchar(30) NOT NULL COMMENT '回复人',
  `replyTime` varchar(20) default NULL COMMENT '回复时间',
  PRIMARY KEY  (`replyId`),
  KEY `postInfoObj` (`postInfoObj`),
  KEY `userObj` (`userObj`),
  CONSTRAINT `t_reply_ibfk_1` FOREIGN KEY (`postInfoObj`) REFERENCES `t_postinfo` (`postInfoId`),
  CONSTRAINT `t_reply_ibfk_2` FOREIGN KEY (`userObj`) REFERENCES `t_userinfo` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_reply
-- ----------------------------
INSERT INTO `t_reply` VALUES ('1', '1', '请大家都要自觉', 'user1', '2019-08-30 16:50:15');
INSERT INTO `t_reply` VALUES ('2', '1', '说得很对，爱护环境，从我做起', 'user2', '2019-09-03 16:10:31');
INSERT INTO `t_reply` VALUES ('3', '2', '我来和你大战一回吧', 'user1', '2019-09-03 16:36:48');

-- ----------------------------
-- Table structure for `t_userinfo`
-- ----------------------------
DROP TABLE IF EXISTS `t_userinfo`;
CREATE TABLE `t_userinfo` (
  `user_name` varchar(30) NOT NULL COMMENT 'user_name',
  `password` varchar(30) NOT NULL COMMENT '登录密码',
  `buildingObj` int(11) NOT NULL COMMENT '所在楼栋',
  `roomNo` varchar(50) NOT NULL COMMENT '房间号',
  `name` varchar(20) NOT NULL COMMENT '姓名',
  `gender` varchar(4) NOT NULL COMMENT '性别',
  `birthDate` varchar(20) default NULL COMMENT '出生日期',
  `userPhoto` varchar(60) NOT NULL COMMENT '业主照片',
  `telephone` varchar(20) NOT NULL COMMENT '联系电话',
  `memo` varchar(500) default NULL COMMENT '业主备注',
  `regTime` varchar(20) default NULL COMMENT '注册时间',
  PRIMARY KEY  (`user_name`),
  KEY `buildingObj` (`buildingObj`),
  CONSTRAINT `t_userinfo_ibfk_1` FOREIGN KEY (`buildingObj`) REFERENCES `t_building` (`buildingId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_userinfo
-- ----------------------------
INSERT INTO `t_userinfo` VALUES ('user1', '123', '1', '6栋-504', '李明涛', '女', '2019-08-05', 'upload/732a74d2-f0a9-45f6-99ca-9666683e2d13.jpg', '13508340934', '本地居民', '2019-08-30 16:46:31');
INSERT INTO `t_userinfo` VALUES ('user2', '123', '1', '6栋-203', '王兴霞', '女', '2019-09-03', 'upload/c503b0b8-cd50-4826-93ad-4a871d3de522.jpg', '13590840834', '外来居民', '2019-09-03 13:08:52');

-- ----------------------------
-- Table structure for `t_violation`
-- ----------------------------
DROP TABLE IF EXISTS `t_violation`;
CREATE TABLE `t_violation` (
  `violationId` int(11) NOT NULL auto_increment COMMENT '违规id',
  `userObj` varchar(30) NOT NULL COMMENT '违规住户',
  `title` varchar(20) NOT NULL COMMENT '违规简述',
  `content` varchar(8000) NOT NULL COMMENT '违规详情',
  `handleState` varchar(20) NOT NULL COMMENT '处理状态',
  `handleResult` varchar(5000) NOT NULL COMMENT '处理结果',
  `uploadTime` varchar(20) default NULL COMMENT '举报时间',
  `reportUserObj` varchar(30) NOT NULL COMMENT '举报人',
  PRIMARY KEY  (`violationId`),
  KEY `userObj` (`userObj`),
  KEY `reportUserObj` (`reportUserObj`),
  CONSTRAINT `t_violation_ibfk_1` FOREIGN KEY (`userObj`) REFERENCES `t_userinfo` (`user_name`),
  CONSTRAINT `t_violation_ibfk_2` FOREIGN KEY (`reportUserObj`) REFERENCES `t_userinfo` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_violation
-- ----------------------------
INSERT INTO `t_violation` VALUES ('1', 'user1', '电梯门口乱扔垃圾', '<p><img src=\"/JavaWebProject/upload/20190903/1567502322138072458.jpg\" title=\"1567502322138072458.jpg\" alt=\"1.jpg\"/><br/><br/>举报此户人家不爱护环境，乱扔垃圾</p>', '待处理', '<p>--<br/></p>', '2019-08-30 16:48:52', 'user2');
INSERT INTO `t_violation` VALUES ('2', 'user2', '举报他们家噪音大', '<p>他们家经常晚上凌晨2点还有音乐声音很大，不能入睡，影响休息</p>', '待处理', '<p>--</p>', '2019-09-03 14:39:28', 'user1');
INSERT INTO `t_violation` VALUES ('3', 'user1', '他们家不爱护卫生', '<p>他们家门口也很脏，管管吧</p>', '待处理', '<p>--</p>', '2019-09-03 14:41:51', 'user2');
