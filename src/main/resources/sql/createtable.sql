DROP TABLE IF EXISTS company;

CREATE TABLE company
(
  id INT UNSIGNED AUTO_INCREMENT,
  companyname VARCHAR(32),
  logo        VARCHAR(128),
  address     VARCHAR(128),
  tel         VARCHAR(16),
  joinhands   VARCHAR(2048),
  workmanship VARCHAR(2048),
  securitycode VARCHAR(32),
  content VARCHAR(2048),
  extend1 VARCHAR(128),
  extend2 VARCHAR(128),
  PRIMARY KEY (id)
);


DROP TABLE IF EXISTS series;

CREATE TABLE series
(
  id INT UNSIGNED AUTO_INCREMENT,
  seriescode    VARCHAR(32),
  seriesname    VARCHAR(32),
  seriescontent VARCHAR(512),
  extend1       VARCHAR(128),
  extend2       VARCHAR(128),
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS linings;

CREATE TABLE linings
(
  id INT UNSIGNED AUTO_INCREMENT,
  seriescode     VARCHAR(32),
  liningcode     VARCHAR(32),
  liningname     VARCHAR(32),
  liningcolor    VARCHAR(32),
  liningcolorurl VARCHAR(256),
  extend1        VARCHAR(128),
  extend2        VARCHAR(128),
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS designs;

CREATE TABLE designs
(
  id INT UNSIGNED AUTO_INCREMENT,
  liningcode VARCHAR(32),
  designcode VARCHAR(32),
  designname VARCHAR(32),
  extend1    VARCHAR(128),
  extend2    VARCHAR(128),
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS files;

CREATE TABLE files
(
  id  INT UNSIGNED AUTO_INCREMENT,
  designcode VARCHAR(32),
  type       VARCHAR(8),
  url        VARCHAR(256),
  urlfix     VARCHAR(256),
  content    VARCHAR(2046),
  extend1    VARCHAR(128),
  extend2    VARCHAR(128),
  PRIMARY KEY (id)
);

ALTER TABLE yiming.files ADD name VARCHAR(64) NULL;

ALTER TABLE yiming.company ADD advantage VARCHAR(2048) NULL;

ALTER TABLE yiming.company CHANGE COLUMN tel tel varchar(64) DEFAULT NULL;

ALTER TABLE `yiming`.`company` CHANGE COLUMN `advantage` `advantage` varchar(5000) DEFAULT NULL;