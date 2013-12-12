<?php
if (!defined('IN_DIRECK')) die("Hack");

$mysql = new mysql;
$mysql->connect($config['db_host'],$config['db_user'],$config['db_pass'],$config['db_name']);

class ContactDA {
	public $Id = 0;
	public $AccountID = 0;
	public $ContactName = "";
	public $ContactNumber = "";
	public $FriendID = 0;
	public $ModifiedDate = 0;
	public $Status = 0;

	public function insert($iAccountID, $iContactName, $iContactNumber, $iFriendID){
		global $mysql, $tb_prefix;
		$mysql->query("INSERT INTO `".$tb_prefix."contact` (`AccountID`, `ContactName`, `ContactNumber`, `FriendID`, `Status`) VALUES ('".$iAccountID."', '".$iContactName."', '".$iContactNumber."', '".$iFriendID."', 0)");
		return true;
	}

	public function delete($inputId){
		global $mysql, $tb_prefix;
		$mysql->query("DELETE FROM `".$tb_prefix."contact` WHERE `ID` = '".$inputId."'");
		return true;
	}

	public function getListByAccountId($iAccountID){
		global $mysql, $tb_prefix;
		$data = array();
		$order_sql = "ORDER BY ID ASC";
		$where_sql = "WHERE `AccountID` = ".$iAccountID;

		$query = $mysql->query("SELECT * FROM `".$tb_prefix."contact` $where_sql $order_sql ;");
		while ($rs = $mysql->fetch_array($query)) {
			$tmpContact = new ContactDA;
			$tmpContact->Id = $rs['ID'];
			$tmpContact->AccountID = $rs['AccountID'];
			$tmpContact->ContactName = $rs['ContactName'];
			$tmpContact->ContactNumber = $rs['ContactNumber'];
			$tmpContact->FriendID = $rs['FriendID'];
			$tmpContact->ModifiedDate = $rs['ModifiedDate'];
			$tmpContact->Status = $rs['Status'];
			$data[] = $tmpContact;
		}
		return $data;
	}

	public function getById($InputId){
		global $mysql, $tb_prefix;
		$where_sql = "WHERE ID=".$InputId;
		$order_sql = "ORDER BY ID DESC";

		$query = $mysql->query("SELECT * FROM `".$tb_prefix."contact` $where_sql $order_sql ;");
		while ($rs = $mysql->fetch_array($query)) {
			$this->Id = $rs['ID'];
			$this->AccountID = $rs['AccountID'];
			$this->ContactName = $rs['ContactName'];
			$this->ContactNumber = $rs['ContactNumber'];
			$this->FriendID = $rs['FriendID'];
			$this->ModifiedDate = $rs['ModifiedDate'];
			$this->Status = $rs['Status'];
		}
		return $this;
	}
	
	public function checkPhoneNumber($InputPhoneNumber){
    	global $mysql, $tb_prefix;
		$data = 0;
		$order_sql = "ORDER BY ID ASC";
		$where_sql = "WHERE `ContactNumber` = '".$InputPhoneNumber."'";

		$query = $mysql->query("SELECT Count(ID) AS Total FROM `".$tb_prefix."contact` $where_sql $order_sql ;");
		while ($rs = $mysql->fetch_array($query)) {
			$data = $rs['Total'];
		}
		return ($data>0);
	}
}
?>
