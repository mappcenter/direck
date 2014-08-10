<?php
if (!defined('IN_DIRECK')) die("Hack");

$mysql = new mysql;
$mysql->connect($config['db_host'],$config['db_user'],$config['db_pass'],$config['db_name']);

class ShareInfoDA {
	public $Id = 0;
	public $AccountID = 0;
	public $PointID = 0;
	public $FriendID = 0;
	public $Type = 0;	
	public $ViewStatus = 0;
	public $CreatedDate = 0;

	public function insert($iAccountID, $iPointID, $iFriendID, $iType, $ViewStatus){
		global $mysql, $tb_prefix;
		$mysql->query("INSERT INTO `".$tb_prefix."sharedinfo` (`AccountID`, `PointID`, `FriendID`, `Type`, `ViewStatus`, `CreatedDate`) VALUES ('".$iAccountID."', '".$iPointID."', '".$iFriendID."', '".$iType."', '".$ViewStatus."', '".time()."')");
		return true;
	}
	
	public function updateViewStatus($shareID){
		global $mysql, $tb_prefix;
		$mysql->query("UPDATE `".$tb_prefix."sharedinfo` SET `ViewStatus`=1 where `ID`='".$shareID."'");
		return true;
	}

	public function delete($inputId){
		global $mysql, $tb_prefix;
		$mysql->query("DELETE FROM `".$tb_prefix."sharedinfo` WHERE `ID` = '".$inputId."'");
		return true;
	}

	public function getAll(){
		global $mysql, $tb_prefix;
		$data = array();
		$order_sql = "ORDER BY ID ASC";
		$where_sql = "WHERE 1 = 1";

		$query = $mysql->query("SELECT * FROM `".$tb_prefix."sharedinfo` $where_sql $order_sql ;");
		while ($rs = $mysql->fetch_array($query)) {
			$tmpShareInfo = new ShareInfoDA;
			$tmpShareInfo->Id = $rs['ID'];
			$tmpShareInfo->AccountID = $rs['AccountID'];
			$tmpShareInfo->PointID = $rs['PointID'];
			$tmpShareInfo->FriendID = $rs['FriendID'];
			$tmpShareInfo->Type = $rs['Type'];
			$tmpShareInfo->ViewStatus = $rs['ViewStatus'];			
			$tmpShareInfo->CreatedDate = $rs['CreatedDate'];
			$data[] = $tmpShareInfo;
		}
		return $data;
	}

	public function getListByAccountId($iAccountId){
		global $mysql, $tb_prefix;
		$data = array();
		$order_sql = "ORDER BY ID ASC";
		// $where_sql = "WHERE `AccountId` = ".$iAccountId." OR `FriendID` = ".$iAccountId;
		$where_sql = "WHERE `AccountId` = ".$iAccountId;

		$query = $mysql->query("SELECT * FROM `".$tb_prefix."sharedinfo` $where_sql $order_sql ;");
		while ($rs = $mysql->fetch_array($query)) {
			$tmpShareInfo = new ShareInfoDA;
			$tmpShareInfo->Id = $rs['ID'];
			$tmpShareInfo->AccountID = $rs['AccountID'];
			$tmpShareInfo->PointID = $rs['PointID'];
			$tmpShareInfo->FriendID = $rs['FriendID'];
			$tmpShareInfo->Type = $rs['Type'];
			$tmpShareInfo->ViewStatus = $rs['ViewStatus'];			
			$tmpShareInfo->CreatedDate = $rs['CreatedDate'];
			$data[] = $tmpShareInfo;
		}
		return $data;
	}

	public function getById($InputId){
		global $mysql, $tb_prefix;
		$where_sql = "WHERE ID=".$InputId;
		$order_sql = "ORDER BY ID DESC";

		$query = $mysql->query("SELECT * FROM `".$tb_prefix."sharedinfo` $where_sql $order_sql ;");
		while ($rs = $mysql->fetch_array($query)) {
			$this->Id = $rs['ID'];
			$this->AccountID = $rs['AccountID'];
			$this->PointID = $rs['PointID'];
			$this->FriendID = $rs['FriendID'];
			$this->Type = $rs['Type'];
			$this->ViewStatus = $rs['ViewStatus'];			
			$this->CreatedDate = $rs['CreatedDate'];
		}
		return $this;
	}
}
?>
