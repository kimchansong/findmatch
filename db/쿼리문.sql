create database test;
use test;
CREATE TABLE IF NOT EXISTS `test`.`User` (
  `user_id` VARCHAR(45) NOT NULL,
  `user_name` VARCHAR(45) NOT NULL,
  `user_age` VARCHAR(45) NOT NULL,
  `user_phone` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `test`.`Team` (
  `team_name` VARCHAR(45) NOT NULL,
  `team_info` VARCHAR(500) NULL,
  PRIMARY KEY (`team_name`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `test`.`Team_has_User` (
  `Team_team_name` VARCHAR(45) NOT NULL,
  `User_user_id` VARCHAR(45) NOT NULL,
  `Team_auth` VARCHAR(45) NULL,
  PRIMARY KEY (`Team_team_name`, `User_user_id`),
  INDEX `fk_Team_has_User_User1_idx` (`User_user_id` ASC) VISIBLE,
  INDEX `fk_Team_has_User_Team_idx` (`Team_team_name` ASC) VISIBLE,
  CONSTRAINT `fk_Team_has_User_Team`
    FOREIGN KEY (`Team_team_name`)
    REFERENCES `test`.`Team` (`team_name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Team_has_User_User1`
    FOREIGN KEY (`User_user_id`)
    REFERENCES `test`.`User` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `test`.`matching` (
  `matching_num` INT NOT NULL AUTO_INCREMENT,
  `matching_status` VARCHAR(45) NULL,
  `matching_uid` VARCHAR(45) NULL,
  `home_team` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`matching_num`),
  INDEX `fk_matching_Team1_idx` (`home_team` ASC) VISIBLE,
  CONSTRAINT `fk_matching_Team1`
    FOREIGN KEY (`home_team`)
    REFERENCES `test`.`Team` (`team_name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `test`.`matching_away` (
  `matching_matching_num` INT NOT NULL,
  `away_team` VARCHAR(45) NOT NULL,
  INDEX `fk_matching_away_matching1_idx` (`matching_matching_num` ASC) VISIBLE,
  INDEX `fk_matching_away_Team1_idx` (`away_team` ASC) VISIBLE,
  PRIMARY KEY (`matching_matching_num`, `away_team`),
  CONSTRAINT `fk_matching_away_matching1`
    FOREIGN KEY (`matching_matching_num`)
    REFERENCES `test`.`matching` (`matching_num`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_matching_away_Team1`
    FOREIGN KEY (`away_team`)
    REFERENCES `test`.`Team` (`team_name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;
show tables;

insert into User values("test", "홍길동", "26", "010-1234-5678");
insert into User values("test1", "홍길동1", "26", "010-1234-5678");
insert into User values("test2", "홍길동2", "26", "010-1234-5678");
insert into User values("test3", "홍길동3", "26", "010-1234-5678");
insert into User values("test4", "홍길동4", "26", "010-1234-5678");
insert into Team values("기아타이거즈","광주 챔피언스 필드 홈구장");
insert into Team values("sk","문학 홈구장");

insert into Team_has_User values("기아타이거즈","test","팀장");
insert into Team_has_User values("sk","test","팀장");
insert into Team_has_User values("sk","test1","팀원");
insert into Team_has_User values("sk","test2","팀원");
insert into Team_has_User values("sk","test3","팀원");
insert into Team_has_User values("sk","test4","팀원");

insert into matching values(0,"매칭중","홍길동","기아타이거즈");
select * from matching;
insert into matching_away values(1,"sk");

(select User_user_id from Team_has_User WHERE Team_team_name="기아타이거즈");
select * from User WHERE user_id = (select User_user_id from Team_has_User WHERE Team_team_name="기아타이거즈");


select * from Team_has_User where User_user_id = "test";

select * from Team where team_name in (select Team_team_name from Team_has_User where User_user_id = "test");

select matching_num from matching where home_team = "기아타이거즈";

select away_team from matching_away where matching_matching_num = (select matching_num from matching where home_team = "기아타이거즈");
select away.away_team as team_name from matching_away as away where matching_matching_num = (select matching_num from matching where home_team = "기아타이거즈");

select * from Team_has_User where Team_team_name = (select away_team from matching_away where matching_matching_num = (select matching_num from matching where home_team = "기아타이거즈"));
