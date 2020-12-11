-- 相关数据
INSERT INTO `user` (`username`, `age`) VALUES ('huihui', 18), ('xiaobei', 20), ('natie', 1);
ALTER TABLE `user` ADD COLUMN `birth_place` VARCHAR(64) NULL COMMENT '出生地' AFTER `age`;
UPDATE `user` SET birth_place = '洛阳' WHERE id = 1;
UPDATE `user` SET birth_place = '汝阳' WHERE id = 2;
UPDATE `user` SET birth_place = '北京' WHERE id = 3;