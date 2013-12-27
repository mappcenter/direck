-- phpMyAdmin SQL Dump
-- version 4.0.8
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Dec 28, 2013 at 01:33 AM
-- Server version: 5.5.34-cll
-- PHP Version: 5.3.17

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `vnponcom_direck`
--

-- --------------------------------------------------------

--
-- Table structure for table `direck_account`
--

CREATE TABLE IF NOT EXISTS `direck_account` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(100) NOT NULL,
  `PhoneNumber` varchar(15) NOT NULL,
  `CreatedDate` int(11) NOT NULL,
  `ModifiedDate` int(11) NOT NULL,
  `Status` tinyint(4) NOT NULL DEFAULT '1',
  `TokenKey` varchar(100) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=16 ;

--
-- Dumping data for table `direck_account`
--

INSERT INTO `direck_account` (`ID`, `Name`, `PhoneNumber`, `CreatedDate`, `ModifiedDate`, `Status`, `TokenKey`) VALUES
(1, 'an', '0989102031', 1386393639, 0, 1, 'APA91bEQL13MmIyEf2ufosuyPAm6InCmR7jbO6WTZFpLA5LK2l3-GQPGdZnqpiuF6Nv8FGDFBsIerFqCA-TLgMjV1-H9iI81D8Uk'),
(2, 'nam', '798', 1386393758, 0, 1, ''),
(3, 'liem', '123', 1386404315, 0, 1, ''),
(4, 'nam', '986', 1386405329, 0, 1, ''),
(5, 'abc', '456', 1386405400, 0, 1, ''),
(6, 'Liem2', '1234567', 1386405470, 0, 1, ''),
(7, 'nam', '9999', 1386405922, 0, 1, ''),
(8, 'nam', '8888', 1386406596, 0, 1, ''),
(9, 'n', '6666', 1386407140, 0, 1, ''),
(10, 'Liem2', '123456789', 1386563805, 0, 1, ''),
(11, 'r', '15555215554', 1386564235, 0, 1, ''),
(12, 'u', '155552167867', 1386581368, 0, 1, ''),
(13, 'giang', '0989102032', 1386646247, 0, 1, ''),
(14, 'nam', '9', 1386814461, 0, 1, ''),
(15, 'm', '15555215556', 1387601535, 0, 1, '');

-- --------------------------------------------------------

--
-- Table structure for table `direck_contact`
--

CREATE TABLE IF NOT EXISTS `direck_contact` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `AccountID` int(11) NOT NULL,
  `ContactName` varchar(100) NOT NULL,
  `ContactNumber` varchar(15) NOT NULL,
  `FriendID` int(11) NOT NULL DEFAULT '0',
  `ModifiedDate` int(11) NOT NULL,
  `Status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0: Normal - 1: Deleted',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=11 ;

--
-- Dumping data for table `direck_contact`
--

INSERT INTO `direck_contact` (`ID`, `AccountID`, `ContactName`, `ContactNumber`, `FriendID`, `ModifiedDate`, `Status`) VALUES
(1, 2, 'NAM', '0988666777', 0, 0, 0),
(2, 2, 'AN', '0999666777', 0, 0, 0),
(3, 11, 'Liem Pham', '090-816-9500', 0, 0, 0),
(4, 11, 'An Vo', '090-861-2345', 0, 0, 0),
(5, 11, 'Hung Le', '090-867-7586', 0, 0, 0),
(6, 11, 'Nam Ho', '090-910-2031', 0, 0, 0),
(7, 11, 'Nguyen Tran', '098-916-9500', 0, 0, 0),
(8, 11, '', '', 0, 0, 0),
(9, 1, 'LÃ½ Dáº¡', '+88695223253', 0, 0, 0),
(10, 1, 'nam', '0989102031', 0, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `direck_point`
--

CREATE TABLE IF NOT EXISTS `direck_point` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(100) NOT NULL,
  `Address` varchar(200) NOT NULL,
  `LocX` varchar(25) NOT NULL,
  `LocY` varchar(25) NOT NULL,
  `Owner` int(11) NOT NULL,
  `CreatedDate` int(11) NOT NULL,
  `ModifiedDate` int(11) NOT NULL,
  `Status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0: Normal - 1: Deleted',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=18 ;

--
-- Dumping data for table `direck_point`
--

INSERT INTO `direck_point` (`ID`, `Name`, `Address`, `LocX`, `LocY`, `Owner`, `CreatedDate`, `ModifiedDate`, `Status`) VALUES
(1, 'Ky Hoa 2e2e2 2', 'Cao Thang', '122223', '2223456', 2, 1386407342, 0, 0),
(2, 'Ky Hoa 2', 'Cao Thang', '123', '3456', 2, 1386553726, 0, 0),
(3, 'Ky Hoa 2', 'Cao Thang', '123', '3456', 2, 1386553726, 0, 0),
(4, 'Ky Hoa', 'Cao Thang', '123', '3456', 1, 1386644628, 0, 0),
(5, 'Ky Hoa', 'Cao Thang', '123', '3456', 1, 1386644634, 0, 0),
(6, 'a', '', '0', '0', 11, 1386816225, 0, 0),
(7, 's', '', '0', '0', 11, 1386816664, 0, 0),
(8, 'm', '', '0', '0', 11, 1386816881, 0, 0),
(9, 'm', '', '0', '0', 11, 1386816980, 0, 0),
(10, 'n', '', '0', '0', 11, 1386817755, 0, 0),
(11, 'v', '', '0', '0', 11, 1386824751, 0, 0),
(12, 'f', '', '10', '10', 11, 1386825010, 0, 0),
(13, 'f', '', '0', '0', 11, 1386825396, 0, 0),
(14, 'f', '', '10', '10', 11, 1386830589, 0, 0),
(15, 'f', '', '10', '10', 11, 1386837785, 0, 0),
(16, 'f', '', '10', '10', 11, 1386838590, 0, 0),
(17, 'cfe khong gian', '', '10.7817212', '106.6614804', 1, 1387600593, 0, 0);

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
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=37 ;

--
-- Dumping data for table `direck_sharedinfo`
--

INSERT INTO `direck_sharedinfo` (`ID`, `AccountID`, `PointID`, `FriendID`, `Type`, `ViewStatus`, `CreatedDate`) VALUES
(1, 2, 1, 2, 0, 0, 1386407342),
(2, 2, 1, 2, 1, 0, 1386407342),
(3, 2, 1, 3, 0, 0, 1386407342),
(4, 3, 1, 2, 1, 0, 1386407342),
(5, 2, 1, 4, 0, 0, 1386407342),
(6, 4, 1, 2, 1, 0, 1386407342),
(7, 2, 2, 2, 0, 0, 1386553726),
(8, 2, 2, 2, 1, 0, 1386553726),
(9, 2, 2, 3, 0, 0, 1386553726),
(10, 3, 2, 2, 1, 0, 1386553726),
(11, 2, 2, 4, 0, 0, 1386553726),
(12, 4, 2, 2, 1, 0, 1386553726),
(13, 2, 3, 2, 0, 0, 1386553726),
(14, 2, 3, 2, 1, 0, 1386553726),
(15, 2, 3, 3, 0, 0, 1386553726),
(16, 3, 3, 2, 1, 0, 1386553726),
(17, 2, 3, 4, 0, 0, 1386553726),
(18, 4, 3, 2, 1, 0, 1386553726),
(20, 2, 4, 1, 1, 0, 1386644628),
(22, 3, 4, 1, 1, 0, 1386644628),
(36, 1, 17, 1, 1, 1, 1387600593),
(24, 2, 5, 1, 1, 0, 1386644634),
(35, 1, 17, 1, 0, 1, 1387600593),
(26, 3, 5, 1, 1, 0, 1386644634),
(27, 11, 12, 2, 0, 0, 1386825010),
(28, 2, 12, 11, 1, 0, 1386825010),
(29, 11, 14, 2, 0, 0, 1386830589),
(30, 2, 14, 11, 1, 0, 1386830589),
(31, 11, 15, 2, 0, 0, 1386837785),
(32, 2, 15, 11, 1, 0, 1386837785),
(33, 11, 16, 2, 0, 0, 1386838590),
(34, 2, 16, 11, 1, 0, 1386838590);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
