-- phpMyAdmin SQL Dump
-- version 4.1.8
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Apr 17, 2014 at 05:05 PM
-- Server version: 5.5.36-cll
-- PHP Version: 5.4.23

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
  `Name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `PhoneNumber` varchar(15) CHARACTER SET latin1 NOT NULL,
  `CreatedDate` int(11) NOT NULL,
  `ModifiedDate` int(11) NOT NULL,
  `Status` tinyint(4) NOT NULL DEFAULT '1',
  `TokenKey` varchar(1000) CHARACTER SET latin1 NOT NULL,
  `DeviceId` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=4 ;

--
-- Dumping data for table `direck_account`
--

INSERT INTO `direck_account` (`ID`, `Name`, `PhoneNumber`, `CreatedDate`, `ModifiedDate`, `Status`, `TokenKey`, `DeviceId`) VALUES
(1, 'May', '15555215554', 1397569798, 0, 1, 'APA91bFo2wJRUBRsw9JPj9EdbIokQ3sQ4KC3gGu2eIQXR83NKEjnJYnklMrVAeGcqNGJEWUKLD7wgxYdVJj7EpU6GEd-BscB7eAUlMlRTJ0WgWJP7--gbab3asQcYDoM6J0Mr3-WPFh-', '1F5C45EB9AC38FF1BC1DFB5936878E76'),
(2, 'Nam', '0989102031', 1397569975, 0, 1, 'APA91bEQL13MmIyEf2ufosuyPAm6InCmR7jbO6WTZFpLA5LK2l3-GQPGdZnqpiuF6Nv8FGDFBsIerFqCA-TLgMjV1-H9iI81D8UkmOdRSeeWXUW7sA7AyTDGhaKXqK_HdfzX0vHJgn6wRb77IQq1Ovwz2Nc1GhUXwQ', 'AFFAB18A9CE537A4FF80591DBE996A25'),
(3, 'Nam fake', '0989102031', 1397570299, 0, 1, 'APA91bH0aREsR6uHlNwUQXEK-8ILwao0CzNLZxwt2QWKjC6VtabnlTCcrycp9I_psoQrT8OmaOjiruqa3TfGLhXObUrBYTLiBXyuFk-35IG9N0ZC6Zi-IYFxszAStdPnzPLQ0YONqut4', '2A0E00D328B0A82CC177EA91F5A2823E');

-- --------------------------------------------------------

--
-- Table structure for table `direck_contact`
--

CREATE TABLE IF NOT EXISTS `direck_contact` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `AccountID` int(11) NOT NULL,
  `ContactName` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `ContactNumber` varchar(15) CHARACTER SET latin1 NOT NULL,
  `FriendID` int(11) NOT NULL DEFAULT '0',
  `ModifiedDate` int(11) NOT NULL,
  `Status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0: Normal - 1: Deleted',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=199 ;

--
-- Dumping data for table `direck_contact`
--

INSERT INTO `direck_contact` (`ID`, `AccountID`, `ContactName`, `ContactNumber`, `FriendID`, `ModifiedDate`, `Status`) VALUES
(1, 1, 'Nam', '0989102031', 0, 0, 0),
(2, 2, 'Gẽ', '0925059639', 0, 0, 0),
(3, 2, 'Lý Dạ', '+88695223253', 0, 0, 0),
(4, 2, 'nguyen', '0902871819', 0, 0, 0),
(5, 2, 'An', '0909669031', 0, 0, 0),
(6, 2, 'Truc', '0983261903', 0, 0, 0),
(7, 2, 'nam', '0989102031', 0, 0, 0),
(8, 2, 'may', '15555215554', 0, 0, 0),
(9, 3, 'D Hoang Nga', '(090)3841407', 0, 0, 0),
(10, 3, 'D Hoang Nga', '(090)9223440', 0, 0, 0),
(11, 3, 'W Nam Nguyen, (ALT)', '(097)4084045', 0, 0, 0),
(12, 3, 'Y me Yen, (Y.H)', '0903715986', 0, 0, 0),
(13, 3, 'C Thang, (lop 2)', '0919573963', 0, 0, 0),
(14, 3, 'B be Quynh, (di Luu)', '0993307381', 0, 0, 0),
(15, 3, 'D Tam Nguyen', '01212805602', 0, 0, 0),
(16, 3, 'E Minh Trang, (luat su)', '01228825547', 0, 0, 0),
(17, 3, 'B Uyen, (cap 2)', '01268636568', 0, 0, 0),
(18, 3, 'A em An', '01285505099', 0, 0, 0),
(19, 3, 'C Quyen Thai', '01289179849', 0, 0, 0),
(20, 3, 'I di Luu', '01204588829', 0, 0, 0),
(21, 3, 'B Hong Vi', '01222942429', 0, 0, 0),
(22, 3, 'C Ha Nguyen Thi', '01223120385', 0, 0, 0),
(23, 3, 'F Phuong Pham', '01223197674', 0, 0, 0),
(24, 3, 'B co Thu', '01226135333', 0, 0, 0),
(25, 3, 'Y Yen Hung', '01234551139', 0, 0, 0),
(26, 3, 'A anh Hai', '01267757596', 0, 0, 0),
(27, 3, 'F Lam Nguyen', '01275550666', 0, 0, 0),
(28, 3, 'Y Yen Hung, (old)', '01289601386', 0, 0, 0),
(29, 3, 'Y Mami, (Y.H)', '01626807627', 0, 0, 0),
(30, 3, 'E Ha (cf)', '01672345764', 0, 0, 0),
(31, 3, 'I Thanh, (ban Hoc)', '01685230419', 0, 0, 0),
(32, 3, 'C My Doanh', '01668049094', 0, 0, 0),
(33, 3, 'A mo Cuc', '01687235091', 0, 0, 0),
(34, 3, 'A di Mai (mo Cuc)', '01687611138', 0, 0, 0),
(35, 3, 'C Thanh Lam', '01687784059', 0, 0, 0),
(36, 3, 'A chi Mong', '01699960768', 0, 0, 0),
(37, 3, 'A em An', '01882242857', 0, 0, 0),
(38, 3, 'A me (Home)', '01998570495', 0, 0, 0),
(39, 3, 'Taxi VINATAXI', '0838111111', 0, 0, 0),
(40, 3, 'Taxi VINASUN', '0838272727', 0, 0, 0),
(41, 3, 'Taxi MAI LINH', '0838383838', 0, 0, 0),
(42, 3, 'A di Tu', '0835123257', 0, 0, 0),
(43, 3, 'Y Yen Hung, (old)', '0838978067', 0, 0, 0),
(44, 3, 'A me (Home)', '0854093497', 0, 0, 0),
(45, 3, 'Y di Hiep', '087220030', 0, 0, 0),
(46, 3, 'B Thuy Do Thi Thanh', '087305807', 0, 0, 0),
(47, 3, 'Z SOS Vietnam', '088228772', 0, 0, 0),
(48, 3, 'B Thai Duong', '088975864', 0, 0, 0),
(49, 3, 'CIS a Huyen62', '089305279', 0, 0, 0),
(50, 3, 'F Bao Nguyen', '0902999074', 0, 0, 0),
(51, 3, 'B Tam Nguyen Van, (du lich)', '0903079719', 0, 0, 0),
(52, 3, 'Y di Hiep', '0903846322', 0, 0, 0),
(53, 3, 'D Van Nien', '0906610261', 0, 0, 0),
(54, 3, 'C Thu Le (lop 2)', '0906833396', 0, 0, 0),
(55, 3, 'B Thai Duong', '0907452184', 0, 0, 0),
(56, 3, 'W Viet, (ban Nhan)', '0907743352', 0, 0, 0),
(57, 3, 'F Nghia Ngo', '0908684006', 0, 0, 0),
(58, 3, 'B chi Hang, (th.Duong)', '0908698538', 0, 0, 0),
(59, 3, 'C Phat Huy', '0909308278', 0, 0, 0),
(60, 3, 'C Toan, (lop 2)', '0909424200', 0, 0, 0),
(61, 3, 'B Thanh Nguyen Tan', '0909680667', 0, 0, 0),
(62, 3, 'l Hanh (du lich)', '0909975538', 0, 0, 0),
(63, 3, 'C Minh Dung', '0902188172', 0, 0, 0),
(64, 3, 'A cau Duc', '0902277770', 0, 0, 0),
(65, 3, 'C Quoc Long', '0902326002', 0, 0, 0),
(66, 3, 'W Biet, (ITD)', '0902442408', 0, 0, 0),
(67, 3, 'E Hien Nguyen, (ban Hanh)', '0902613530', 0, 0, 0),
(68, 3, 'E Uyen Phan, (em Nhi)', '0902648776', 0, 0, 0),
(69, 3, 'E em Ngan, (ban Khanh)', '0902704814', 0, 0, 0),
(70, 3, 'C Quoc Khanh', '0902888531', 0, 0, 0),
(71, 3, 'D Xuan Tung', '0903073820', 0, 0, 0),
(72, 3, 'F Hung Nguyen', '0903257462', 0, 0, 0),
(73, 3, 'D Ngoc Cuong', '0903395233', 0, 0, 0),
(74, 3, 'A di Nam', '0903605608', 0, 0, 0),
(75, 3, 'C My Linh', '0903637017', 0, 0, 0),
(76, 3, 'C chi Chau', '0903698205', 0, 0, 0),
(77, 3, 'F Nam Vu', '0903701195', 0, 0, 0),
(78, 3, 'F Nghia Ngo', '0903800622', 0, 0, 0),
(79, 3, 'F Ngoc Bui', '0903869506', 0, 0, 0),
(80, 3, 'F Thanh Van', '0904433521', 0, 0, 0),
(81, 3, 'B Thanh Nguyen Tan', '0904434800', 0, 0, 0),
(82, 3, 'C Trung Ha', '0905355079', 0, 0, 0),
(83, 3, 'C Hang Hai', '0905496067', 0, 0, 0),
(84, 3, 'CIS a dang8', '0905577955', 0, 0, 0),
(85, 3, 'C My Ngoc', '0906099011', 0, 0, 0),
(86, 3, 'C Van Lam', '0906644022', 0, 0, 0),
(87, 3, 'C Giang Nguyen Hong', '0906833475', 0, 0, 0),
(88, 3, 'D Minh Man', '0906999125', 0, 0, 0),
(89, 3, 'B Hanh Vo Hong', '0907079884', 0, 0, 0),
(90, 3, 'F Tai Nguyen', '0907579602', 0, 0, 0),
(91, 3, 'D Minh Man', '0907612396', 0, 0, 0),
(92, 3, 'F Phu Le', '0907667510', 0, 0, 0),
(93, 3, 'F Tri Do', '0907676968', 0, 0, 0),
(94, 3, 'D Bang Trinh', '0907756457', 0, 0, 0),
(95, 3, 'C Thai Binh', '0907788325', 0, 0, 0),
(96, 3, 'C Tuan Anh', '0907788657', 0, 0, 0),
(97, 3, 'F Anh Nguyen', '0908006013', 0, 0, 0),
(98, 3, 'D Xuan Phong', '0908118123', 0, 0, 0),
(99, 3, 'C Cong Ngoc', '0908149106', 0, 0, 0),
(100, 3, 'F Thuy Vu', '0908203911', 0, 0, 0),
(101, 3, 'F Vinh Nguyen', '0908244683', 0, 0, 0),
(102, 3, 'C Bich Ha, (lop 2)', '0908290283', 0, 0, 0),
(103, 3, 'D Quynh Anh', '0908350569', 0, 0, 0),
(104, 3, 'A mo Cuc', '0908386705', 0, 0, 0),
(105, 3, 'D Quang Vinh', '0908585368', 0, 0, 0),
(106, 3, 'C Thuy, (lop 2)', '0908763682', 0, 0, 0),
(107, 3, 'D Thanh Phong', '0908777974', 0, 0, 0),
(108, 3, 'C Van Nghia', '0908883630', 0, 0, 0),
(109, 3, 'A anh Nhat, (Ninh Hoa)', '0908898546', 0, 0, 0),
(110, 3, 'C Thanh Tung, (khoa 2003)', '0909090028', 0, 0, 0),
(111, 3, 'F Tho Ho', '0909221627', 0, 0, 0),
(112, 3, 'D Hong Hanh', '0909302872', 0, 0, 0),
(113, 3, 'F Long Nguyen', '0909310348', 0, 0, 0),
(114, 3, 'D Duc Thong', '0909336097', 0, 0, 0),
(115, 3, 'F Loc Nguyen', '0909517021', 0, 0, 0),
(116, 3, 'Y Yen Hung, (old)', '0909633220', 0, 0, 0),
(117, 3, 'A An Vo', '0909669031', 0, 0, 0),
(118, 3, 'C Trong Duong', '0909691004', 0, 0, 0),
(119, 3, 'C Van Lam', '0909762669', 0, 0, 0),
(120, 3, 'E Cam Tu, (rolano)', '0909776747', 0, 0, 0),
(121, 3, 'C Nguyen Loc', '0909811086', 0, 0, 0),
(122, 3, 'F chi Minh', '0909865361', 0, 0, 0),
(123, 3, 'C Hong Ngoc', '0909911406', 0, 0, 0),
(124, 3, 'W Hung, (ban Nhan)', '0909943900', 0, 0, 0),
(125, 3, 'l Hanh (du lich)', '0913153855', 0, 0, 0),
(126, 3, 'A An Vo', '0913306994', 0, 0, 0),
(127, 3, 'F Ngoc Bui Anh', '0918762816', 0, 0, 0),
(128, 3, 'W Chung Do', '0918768234', 0, 0, 0),
(129, 3, 'W Kieu Nguyen, (Khaitien)', '0919084969', 0, 0, 0),
(130, 3, 'C Phuc, (lop 2)', '0912685570', 0, 0, 0),
(131, 3, 'A cau Thanh', '0913165701', 0, 0, 0),
(132, 3, 'D Thien Ha', '0913612301', 0, 0, 0),
(133, 3, 'CIS a Thang81', '0913856665', 0, 0, 0),
(134, 3, 'C Huyen Anh', '0914004904', 0, 0, 0),
(135, 3, 'C Cong Mua', '0914444686', 0, 0, 0),
(136, 3, 'CIS a Minh', '0914684584', 0, 0, 0),
(137, 3, 'C Trong Nghia', '0916567339', 0, 0, 0),
(138, 3, 'A di Tu', '0916787964', 0, 0, 0),
(139, 3, 'C Tra My', '0917524206', 0, 0, 0),
(140, 3, 'C Quoc Huy', '0917690099', 0, 0, 0),
(141, 3, 'Y Mami, (Y.H)', '0918010455', 0, 0, 0),
(142, 3, 'F Binh Luong', '0918100383', 0, 0, 0),
(143, 3, 'C Tuan Kiet', '0918383561', 0, 0, 0),
(144, 3, 'W Huy Vo, (ITD)', '0918464003', 0, 0, 0),
(145, 3, 'F Hien Nguyen', '0918926345', 0, 0, 0),
(146, 3, 'I di Lan', '0919037920', 0, 0, 0),
(147, 3, 'F Thuy Ngo', '0919040319', 0, 0, 0),
(148, 3, 'F Hanh Le', '0919300540', 0, 0, 0),
(149, 3, 'T thay Duc', '0919622862', 0, 0, 0),
(150, 3, 'C Thanh Nhan', '0919694722', 0, 0, 0),
(151, 3, 'C Tuan, (lop 2)', '0919883905', 0, 0, 0),
(152, 3, 'R Hanh, (oai  huong)', '0925742942', 0, 0, 0),
(153, 3, 'B Tam Nguyen Van, (du lich)', '0932759188', 0, 0, 0),
(154, 3, 'g.Le Dinh Nhat Vi (cf)', '0935359149', 0, 0, 0),
(155, 3, 'A co Tu, (Ninh Hoa)', '0935913952', 0, 0, 0),
(156, 3, 'F be Huong, (em Loc)', '0937982971', 0, 0, 0),
(157, 3, 'F Anh Nguyen', '0938020411', 0, 0, 0),
(158, 3, 'B Diep Chi', '0938329696', 0, 0, 0),
(159, 3, 'Y Mami, (Y.H)', '0938353508', 0, 0, 0),
(160, 3, 'D Trong Hoc', '0938791184', 0, 0, 0),
(161, 3, 'B chi Ngan', '0933553855', 0, 0, 0),
(162, 3, 'Y Mami, (Y.H)', '0934187468', 0, 0, 0),
(163, 3, 'F Tho Ho', '0934339679', 0, 0, 0),
(164, 3, 'B Nhan Tran The', '0936756014', 0, 0, 0),
(165, 3, 'C Khoan Nguyen Duc', '0937851132', 0, 0, 0),
(166, 3, 'B Vinh Vu Viet', '0938052154', 0, 0, 0),
(167, 3, 'C Phong Minh', '0938237145', 0, 0, 0),
(168, 3, 'C To Trinh', '0938529107', 0, 0, 0),
(169, 3, 'B Thao Tran Thi', '0938691083', 0, 0, 0),
(170, 3, 'B Khanh, (cap 2)', '0938892639', 0, 0, 0),
(171, 3, 'W Viet, (ban Nhan)', '0939343597', 0, 0, 0),
(172, 3, 'F Thuy Huynh', '0955467333', 0, 0, 0),
(173, 3, 'F Sa Nguyen', '0976179090', 0, 0, 0),
(174, 3, 'F Cam Nguyen', '0973945445', 0, 0, 0),
(175, 3, 'C Tai Hai', '0974111163', 0, 0, 0),
(176, 3, 'C Minh Ngoc', '0977717300', 0, 0, 0),
(177, 3, 'D Trong Hoc', '0979793988', 0, 0, 0),
(178, 3, 'F Loc Tran', '0983149143', 0, 0, 0),
(179, 3, 'I Dai Lam, (di Huong)', '0986427727', 0, 0, 0),
(180, 3, 'E Minh Trang, (luat su)', '0988026876', 0, 0, 0),
(181, 3, 'F Hieu Phan', '0983105107', 0, 0, 0),
(182, 3, 'D Tri An', '0983443119', 0, 0, 0),
(183, 3, 'D Quynh Trang', '0983842906', 0, 0, 0),
(184, 3, 'D Tan Tai', '0984746455', 0, 0, 0),
(185, 3, 'C Thien Nhan', '0985413613', 0, 0, 0),
(186, 3, 'F Nghia Doan', '0985715307', 0, 0, 0),
(187, 3, 'W chi Lam, (ITD)', '0985993547', 0, 0, 0),
(188, 3, 'B Truc Pham', '0987124178', 0, 0, 0),
(189, 3, 'C Van Lam', '0987381453', 0, 0, 0),
(190, 3, 'C Thuy Dung', '0987856385', 0, 0, 0),
(191, 3, 'C Minh Huong', '0988239642', 0, 0, 0),
(192, 3, 'C Thu Le (lop 2)', '0988281554', 0, 0, 0),
(193, 3, 'C My Ngoc', '0988683684', 0, 0, 0),
(194, 3, 'C Quoc Nam', '0989102031', 0, 0, 0),
(195, 3, 'F Vu Vuong', '0989799077', 0, 0, 0),
(196, 3, 'C Loan Tran', '0989882693', 0, 0, 0),
(197, 3, 'D Thanh Thuy', '0989959833', 0, 0, 0),
(198, 3, 'C Khoi Tran Kim', '0996579789', 0, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `direck_point`
--

CREATE TABLE IF NOT EXISTS `direck_point` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `Address` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `LocX` decimal(10,5) NOT NULL,
  `LocY` decimal(10,5) NOT NULL,
  `Owner` int(11) NOT NULL,
  `CreatedDate` int(11) NOT NULL,
  `ModifiedDate` int(11) NOT NULL,
  `Status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0: Normal - 1: Deleted',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=2 ;

--
-- Dumping data for table `direck_point`
--

INSERT INTO `direck_point` (`ID`, `Name`, `Address`, `LocX`, `LocY`, `Owner`, `CreatedDate`, `ModifiedDate`, `Status`) VALUES
(1, 'cafe', '', '10.78180', '106.66140', 2, 1397570029, 0, 0);

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
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=9 ;

--
-- Dumping data for table `direck_sharedinfo`
--

INSERT INTO `direck_sharedinfo` (`ID`, `AccountID`, `PointID`, `FriendID`, `Type`, `ViewStatus`, `CreatedDate`) VALUES
(1, 2, 1, 1, 0, 1, 1397570029),
(2, 1, 1, 2, 1, 0, 1397570029),
(3, 1, 1, 3, 0, 1, 1397571260),
(4, 3, 1, 1, 1, 0, 1397571260),
(5, 1, 1, 3, 0, 1, 1397571516),
(6, 3, 1, 1, 1, 0, 1397571516),
(7, 1, 1, 3, 0, 1, 1397571969),
(8, 3, 1, 1, 1, 0, 1397571969);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
