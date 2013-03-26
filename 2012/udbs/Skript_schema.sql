-- phpMyAdmin SQL Dump
-- version 3.2.2
-- http://www.phpmyadmin.net
--
-- Počítač: localhost
-- Vygenerováno: Pátek 21. prosince 2012, 06:31
-- Verze MySQL: 5.5.22
-- Verze PHP: 5.4.7--pl0-gentoo



/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Databáze: `_d-skola_db1projekt`
--

-- --------------------------------------------------------

--
-- Struktura tabulky `predmet`
--

CREATE TABLE IF NOT EXISTS "predmet" (
  "id" int(11) unsigned NOT NULL,
  "nazev" varchar(64) NOT NULL,
  PRIMARY KEY ("id")
) AUTO_INCREMENT=39 ;

-- --------------------------------------------------------

--
-- Struktura tabulky `skplan`
--

CREATE TABLE IF NOT EXISTS "skplan" (
  "id" int(11) unsigned NOT NULL,
  "predmet_id" int(10) unsigned NOT NULL COMMENT 'FK: predmet::id',
  "pro_rocnik" varchar(8) NOT NULL COMMENT 'pro ktery rocnik je predmet urcen?',
  "volitelny" tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '0|1 je predmet volitelny?',
  "maturitni" tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '0|1 je predmet maturitni?',
  "skupina" varchar(8) DEFAULT NULL COMMENT 'kazdy volitelny predmet ma nazev skupiny (~seznam vice volitelnych predmetu)',
  "poznamka" text,
  PRIMARY KEY ("id")
) AUTO_INCREMENT=465 ;

-- --------------------------------------------------------

--
-- Struktura tabulky `skrok`
--

CREATE TABLE IF NOT EXISTS "skrok" (
  "datum" date NOT NULL,
  "skplan_id" int(11) unsigned NOT NULL COMMENT 'FK: skplan::id',
  "trida_id" int(11) unsigned NOT NULL COMMENT 'FK: trida::id',
  "student_id" int(11) unsigned NOT NULL COMMENT 'FK: student::id',
  "ucitel_id" int(11) unsigned NOT NULL COMMENT 'FK: ucitel::id',
  UNIQUE KEY "datum_trida_id_student_id_predmet_id_ucitel_id" ("datum","trida_id","student_id","skplan_id","ucitel_id"),
  KEY "skplan_id" ("skplan_id")
);

-- --------------------------------------------------------

--
-- Struktura tabulky `student`
--

CREATE TABLE IF NOT EXISTS "student" (
  "id" int(11) unsigned NOT NULL,
  "jmeno" varchar(32) NOT NULL,
  "prijmeni" varchar(32) NOT NULL,
  "datum_narozeni" date NOT NULL,
  "pohlavi" varchar(1) NOT NULL DEFAULT 'M' COMMENT 'M=muz, Z=zena',
  PRIMARY KEY ("id")
) AUTO_INCREMENT=328 ;

-- --------------------------------------------------------

--
-- Struktura tabulky `trida`
--

CREATE TABLE IF NOT EXISTS "trida" (
  "id" int(11) unsigned NOT NULL,
  "tridni_ucitel_id" int(11) unsigned DEFAULT NULL COMMENT 'FK: ucitel::id',
  "rocnik_4" tinyint(3) unsigned DEFAULT NULL COMMENT 'poradi trid u 4-leteho gymnazia',
  "rocnik_8" tinyint(3) unsigned DEFAULT NULL COMMENT 'poradi trid u viceleteho gymnazia',
  "oznaceni" varchar(16) NOT NULL,
  "predchozi_rocnik_trida_id" int(11) unsigned DEFAULT NULL COMMENT 'FK: trida_id (id tridy v predchozim skroce)',
  PRIMARY KEY ("id")
) AUTO_INCREMENT=47 ;

-- --------------------------------------------------------

--
-- Struktura tabulky `ucitel`
--

CREATE TABLE IF NOT EXISTS "ucitel" (
  "id" int(11) unsigned NOT NULL,
  "jmeno" varchar(32) NOT NULL,
  "prijmeni" varchar(32) NOT NULL,
  "datum_narozeni" date NOT NULL,
  "pohlavi" varchar(1) NOT NULL DEFAULT 'M' COMMENT 'M=muz, Z=zena',
  PRIMARY KEY ("id")
) AUTO_INCREMENT=24 ;

-- --------------------------------------------------------

--
-- Struktura tabulky `vysvedceni`
--

CREATE TABLE IF NOT EXISTS "vysvedceni" (
  "id" int(11) unsigned NOT NULL,
  "skrok_datum" date DEFAULT NULL COMMENT 'FK: skrok::datum',
  "pololeti" tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '0|1 pololeti znamka?',
  "maturitni" tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '0|1 maturitni znamka?',
  "znamka" tinyint(3) unsigned DEFAULT NULL COMMENT 'null=neklasifikovan, jinak 1-5',
  "skplan_id" int(11) unsigned NOT NULL COMMENT 'FK: skplan::id',
  "student_id" int(11) unsigned NOT NULL COMMENT 'FK: student::id',
  "poznamka" text,
  PRIMARY KEY ("id"),
  KEY "skplan_id" ("skplan_id")
) AUTO_INCREMENT=30218 ;

-- --------------------------------------------------------

--
-- Struktura tabulky `znamka`
--

CREATE TABLE IF NOT EXISTS "znamka" (
  "id" int(11) unsigned NOT NULL,
  "znamka" float NOT NULL COMMENT 'hodnota znamky: 1-5',
  "vaha" float NOT NULL DEFAULT '1' COMMENT 'vaha dane znamky',
  "datum" date NOT NULL,
  "student_id" int(11) NOT NULL COMMENT 'FK: student::id',
  "skplan_id" int(11) unsigned NOT NULL COMMENT 'FK: skplan::id',
  "poznamka" text,
  PRIMARY KEY ("id"),
  KEY "skplan_id" ("skplan_id")
) AUTO_INCREMENT=46419 ;
