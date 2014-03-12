-- phpMyAdmin SQL Dump
-- version 4.0.4.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Mar 12, 2014 at 11:46 AM
-- Server version: 5.5.32
-- PHP Version: 5.4.19

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `direck`
--
CREATE DATABASE IF NOT EXISTS `direck` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `direck`;

-- --------------------------------------------------------

--
-- Table structure for table `direck_account`
--

CREATE TABLE IF NOT EXISTS `direck_account` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `PhoneNumber` varchar(15) CHARACTER SET latin1 NOT NULL,
  `CreatedDate` int(11) NOT NULL,
  `ModifiedDate` int(11) NOT NULL,
  `Status` tinyint(4) NOT NULL DEFAULT '1',
  `TokenKey` varchar(1000) CHARACTER SET latin1 NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=27 ;

-- --------------------------------------------------------

--
-- Table structure for table `direck_contact`
--

CREATE TABLE IF NOT EXISTS `direck_contact` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `AccountID` int(11) NOT NULL,
  `ContactName` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `ContactNumber` varchar(15) NOT NULL,
  `FriendID` int(11) NOT NULL DEFAULT '0',
  `ModifiedDate` int(11) NOT NULL,
  `Status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0: Normal - 1: Deleted',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=342 ;

-- --------------------------------------------------------

--
-- Table structure for table `direck_point`
--

CREATE TABLE IF NOT EXISTS `direck_point` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `Address` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `LocX` decimal(10,5) NOT NULL,
  `LocY` decimal(10,5) NOT NULL,
  `Owner` int(11) NOT NULL,
  `CreatedDate` int(11) NOT NULL,
  `ModifiedDate` int(11) NOT NULL,
  `Status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0: Normal - 1: Deleted',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=78 ;

-- --------------------------------------------------------

--
-- Table structure for table `direck_sharedinfo`
--

CREATE TABLE IF NOT EXISTS `direck_sharedinfo` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `AccountID` int(11) NOT NULL,
  `PointID` int(11) NOT NULL,
  `FriendID` int(11) NOT NULL,
  `Type` tinyint(1) NOT NULL COMMENT '0: shared 1: be shared 2: bookmark',
  `ViewStatus` tinyint(1) NOT NULL COMMENT '0: New - 1: be Viewed',
  `CreatedDate` int(11) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=161 ;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
