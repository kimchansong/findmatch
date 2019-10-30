drop database if exists test;
create database test;
use test;


-- -----------------------------------------------------
-- Table `test`.`Team`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test`.`Team` (
  `t_name` VARCHAR(45) NOT NULL,
  `t_info` VARCHAR(500) NULL,
  `t_locate` VARCHAR(45) NULL,
  PRIMARY KEY (`t_name`));


-- -----------------------------------------------------
-- Table `test`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test`.`User` (
  `u_id` VARCHAR(45) NOT NULL,
  `u_name` VARCHAR(45) NOT NULL,
  `u_age` INT NOT NULL,
  `u_phone` VARCHAR(45) NOT NULL,
  `u_point` INT NOT NULL,
  PRIMARY KEY (`u_id`));


-- -----------------------------------------------------
-- Table `test`.`Team_has_User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test`.`Team_has_User` (
  `Team_t_name` VARCHAR(45) NOT NULL,
  `User_u_id` VARCHAR(45) NOT NULL,
  `t_auth` VARCHAR(45) NULL,
   FOREIGN KEY (`User_u_id`)
    REFERENCES `test`.`User` (`u_id`),
    FOREIGN KEY (`Team_t_name`)
    REFERENCES `test`.`Team` (`t_name`));


-- -----------------------------------------------------
-- Table `test`.`Stadium`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test`.`Stadium` (
  `s_num` INT NOT NULL AUTO_INCREMENT,
  `s_name` VARCHAR(45) NULL,
  `s_phone` VARCHAR(45) NULL,
  `s_latitude` FLOAT NULL,
  `s_longitude` FLOAT NULL,
  `s_info` VARCHAR(500) NULL,
  PRIMARY KEY (`s_num`));


-- -----------------------------------------------------
-- Table `test`.`matching`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test`.`matching` (

  `m_num` INT NOT NULL AUTO_INCREMENT,
  `m_status` VARCHAR(45) NULL,
  `m_uid` VARCHAR(45) NULL,
  `m_hometeam` VARCHAR(45) NOT NULL,
  `Stadium_stadium_num` INT NULL,
  `m_location` VARCHAR(45) NULL,
  PRIMARY KEY (`m_num`),
    FOREIGN KEY (`m_hometeam`)
    REFERENCES `test`.`Team` (`t_name`),
    FOREIGN KEY (`Stadium_stadium_num`)
    REFERENCES `test`.`Stadium` (`s_num`));

-- -----------------------------------------------------
-- Table `test`.`matching_away`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test`.`matching_away` (
  `matching_m_num1` INT NOT NULL,
  `Team_t_name` VARCHAR(45) NOT NULL,
    FOREIGN KEY (`Team_t_name`)
    REFERENCES `test`.`Team` (`t_name`),
    FOREIGN KEY (`matching_m_num1`)
    REFERENCES `test`.`matching` (`m_num`));


-- -----------------------------------------------------
-- Table `test`.`board`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test`.`board` (
  `b_num` INT NOT NULL AUTO_INCREMENT,
  `User_u_id` VARCHAR(45) NOT NULL,
  `b_title` VARCHAR(45) NOT NULL,
  `b_content` VARCHAR(45) NULL,
  `b_type` INT NOT NULL,
  `b_date` DATETIME NOT NULL,
  PRIMARY KEY (`b_num`),
    FOREIGN KEY (`User_u_id`)
    REFERENCES `test`.`User` (`u_id`));


-- -----------------------------------------------------
-- Table `test`.`comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test`.`comment` (
  `c_num` INT NOT NULL,
  `board_b_num` INT NOT NULL,
  `user_u_id` VARCHAR(45) NOT NULL,
  `c_content` VARCHAR(45) NULL,
  `c_date` VARCHAR(45) NULL,
  PRIMARY KEY (`c_num`),
    FOREIGN KEY (`board_b_num`)
    REFERENCES `test`.`board` (`b_num`),
    FOREIGN KEY (`user_u_id`)
    REFERENCES `test`.`User` (`u_id`));




insert into User values("test", "홍길동", "26", "010-1234-5678", 0);
insert into User values("test1", "홍길동1", "26", "010-1234-5678", 0);
insert into User values("test2", "홍길동2", "26", "010-1234-5678", 0);
insert into User values("test3", "홍길동3", "26", "010-1234-5678", 0);
insert into User values("test4", "홍길동4", "26", "010-1234-5678", 0);
select * from user;
select * from Team;
insert into Team values("기아타이거즈","광주 챔피언스 필드 홈구장","광산구");
insert into Team values("sk","문학 홈구장","인천");
select * from Team_has_User;
insert into Team_has_User values("기아타이거즈","test","팀장");
insert into Team_has_User values("sk","test","팀장");
insert into Team_has_User values("sk","test1","팀원");
insert into Team_has_User values("sk","test2","팀원");
insert into Team_has_User values("sk","test3","팀원");
insert into Team_has_User values("sk","test4","팀원");
select * from matching;
insert into matching(m_num,m_status,m_uid,m_hometeam) values(0,"매칭중","홍길동","기아타이거즈");
insert into matching_away values(1,"sk");
select User_u_id from Team_has_User WHERE Team_t_name="기아타이거즈";
select * from board order by b_num desc;
