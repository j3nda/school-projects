-- Adminer 3.3.3 MySQL dump

SET NAMES utf8;
SET foreign_key_checks = 0;
SET time_zone = 'SYSTEM';
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

DROP TABLE IF EXISTS `predmet`;
CREATE TABLE `predmet` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `nazev` varchar(64) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='seznam vsech predmetu';


DROP TABLE IF EXISTS `skplan`;
CREATE TABLE `skplan` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `predmet_id` int(10) unsigned NOT NULL COMMENT 'FK: predmet::id',
  `pro_rocnik` varchar(8) NOT NULL COMMENT 'pro ktery rocnik je predmet urcen?',
  `volitelny` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '0|1 je predmet volitelny?',
  `maturitni` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '0|1 je predmet maturitni?',
  `skupina` varchar(8) DEFAULT NULL COMMENT 'kazdy volitelny predmet ma nazev skupiny (~seznam vice volitelnych predmetu)',
  `poznamka` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='skolni plan vyuky ve skole';


DROP TABLE IF EXISTS `skrok`;
CREATE TABLE `skrok` (
  `datum` date NOT NULL,
  `skplan_id` int(11) unsigned NOT NULL COMMENT 'FK: skplan::id',
  `trida_id` int(11) unsigned NOT NULL COMMENT 'FK: trida::id',
  `student_id` int(11) unsigned NOT NULL COMMENT 'FK: student::id',
  `ucitel_id` int(11) unsigned NOT NULL COMMENT 'FK: ucitel::id',
  UNIQUE KEY `datum_trida_id_student_id_predmet_id_ucitel_id` (`datum`,`trida_id`,`student_id`,`skplan_id`,`ucitel_id`),
  KEY `skplan_id` (`skplan_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='sestaveni skolniho roku: trida x student x predmet x ucitel';


DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `jmeno` varchar(32) NOT NULL,
  `prijmeni` varchar(32) NOT NULL,
  `datum_narozeni` date NOT NULL,
  `pohlavi` varchar(1) NOT NULL DEFAULT 'M' COMMENT 'M=muz, Z=zena',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='seznam vsech studentu';


DROP TABLE IF EXISTS `trida`;
CREATE TABLE `trida` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tridni_ucitel_id` int(11) unsigned DEFAULT NULL COMMENT 'FK: ucitel::id',
  `rocnik_4` tinyint(3) unsigned DEFAULT NULL COMMENT 'poradi trid u 4-leteho gymnazia',
  `rocnik_8` tinyint(3) unsigned DEFAULT NULL COMMENT 'poradi trid u viceleteho gymnazia',
  `oznaceni` varchar(16) NOT NULL,
  `predchozi_rocnik_trida_id` int(11) unsigned DEFAULT NULL COMMENT 'FK: trida_id (id tridy v predchozim skroce)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='seznam vsech trid';


DROP TABLE IF EXISTS `ucitel`;
CREATE TABLE `ucitel` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `jmeno` varchar(32) NOT NULL,
  `prijmeni` varchar(32) NOT NULL,
  `datum_narozeni` date NOT NULL,
  `pohlavi` varchar(1) NOT NULL DEFAULT 'M' COMMENT 'M=muz, Z=zena',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='seznam vsech ucitelu';


DROP TABLE IF EXISTS `vysvedceni`;
CREATE TABLE `vysvedceni` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `skrok_datum` date DEFAULT NULL COMMENT 'FK: skrok::datum',
  `pololeti` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '0|1 pololeti znamka?',
  `maturitni` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '0|1 maturitni znamka?',
  `znamka` tinyint(3) unsigned DEFAULT NULL COMMENT 'null=neklasifikovan, jinak 1-5',
  `skplan_id` int(11) unsigned NOT NULL COMMENT 'FK: skplan::id',
  `student_id` int(11) unsigned NOT NULL COMMENT 'FK: student::id',
  `poznamka` text,
  PRIMARY KEY (`id`),
  KEY `skplan_id` (`skplan_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='seznam znamek na vysvedceni';


DROP TABLE IF EXISTS `znamka`;
CREATE TABLE `znamka` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `znamka` float NOT NULL COMMENT 'hodnota znamky: 1-5',
  `vaha` float NOT NULL DEFAULT '1' COMMENT 'vaha dane znamky',
  `datum` date NOT NULL,
  `student_id` int(11) NOT NULL COMMENT 'FK: student::id',
  `skplan_id` int(11) unsigned NOT NULL COMMENT 'FK: skplan::id',
  `poznamka` text,
  PRIMARY KEY (`id`),
  KEY `skplan_id` (`skplan_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='seznam vsech znamek';


-- 2012-12-21 07:29:27
