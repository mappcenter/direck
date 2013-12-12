<?php
if (!defined('IN_DIRECK')) die("Hack");

$mysql = new mysql;
$mysql->connect($config['db_host'],$config['db_user'],$config['db_pass'],$config['db_name']);

class PointDA {
	public $Id = 0;
	public $Name = "";
	public $Address = "";
	public $LocX = 0;
	public $LocY = 0;
	public $Owner = 0;
	public $CreatedDate = 0;
	public $ModifiedDate = 0;
	public $Status = 0;

	public function insert($iName, $iAddress, $iLocX, $iLocY, $iOwner){
		global $mysql, $tb_prefix;
		$returnID = 0;
		$mysql->query("INSERT INTO `".$tb_prefix."point` (`Name`, `Address`, `LocX`, `LocY`, `Owner`, `CreatedDate`, `Status`) VALUES ('".$iName."', '".$iAddress."', '".$iLocX."', '".$iLocY."', '".$iOwner."', '".time()."', 0)");
		$queryGetId = $mysql->query("SELECT * FROM `".$tb_prefix."point` WHERE `ID` = LAST_INSERT_ID() ;");
		while ($rs = $mysql->fetch_array($queryGetId)) {
			$returnID = $rs['ID'];
		}
		return $returnID;
	}

	public function delete($inputId){
		global $mysql, $tb_prefix;
		$mysql->query("DELETE FROM `".$tb_prefix."point` WHERE `ID` = '".$inputId."'");
		return $true;
	}

	public function getById($InputId){
		global $mysql, $tb_prefix;
		$where_sql = "WHERE ID=".$InputId;
		$order_sql = "ORDER BY ID DESC";

		$query = $mysql->query("SELECT * FROM `".$tb_prefix."point` $where_sql $order_sql ;");
		while ($rs = $mysql->fetch_array($query)) {
			$this->Id = $rs['ID'];
			$this->Name = $rs['Name'];
			$this->Address = $rs['Address'];
			$this->LocX = $rs['LocX'];
			$this->LocY = $rs['LocY'];
			$this->Owner = $rs['Owner'];
			$this->CreatedDate = $rs['CreatedDate'];
			$this->ModifiedDate = $rs['ModifiedDate'];
			$this->Status = $rs['Status'];
		}
		return $this;
	}

	public function getListByIds($iAccountIDs){
		global $mysql, $tb_prefix;
		$data = array();
		$order_sql = "ORDER BY ID ASC";
		$where_sql = "WHERE `ID` IN (".$iAccountIDs.") ";

		//echo "SELECT * FROM `".$tb_prefix."point` $where_sql $order_sql ;";
		$query = $mysql->query("SELECT * FROM `".$tb_prefix."point` $where_sql $order_sql ;");
		while ($rs = $mysql->fetch_array($query)) {
			$tmpPoint = new PointDA;
			$tmpID = "'key_".$rs['ID']."'";
			$tmpPoint->Id = $rs['ID'];
			$tmpPoint->Name = $rs['Name'];
			$tmpPoint->Address = $rs['Address'];
			$tmpPoint->LocX = $rs['LocX'];
			$tmpPoint->LocY = $rs['LocY'];
			$tmpPoint->Owner = $rs['Owner'];
			$tmpPoint->CreatedDate = $rs['CreatedDate'];
			$tmpPoint->ModifiedDate = $rs['ModifiedDate'];
			$tmpPoint->Status = $rs['Status'];
			$data[$tmpID] = $tmpPoint;
		}
		return $data;
	}

	public function getListByOwnerId($iAccountID){
		global $mysql, $tb_prefix;
		$data = array();
		$order_sql = "ORDER BY ID ASC";
		$where_sql = "WHERE `Owner` = ".$iAccountID;

		$query = $mysql->query("SELECT * FROM `".$tb_prefix."point` $where_sql $order_sql ;");
		while ($rs = $mysql->fetch_array($query)) {
			$tmpPoint = new PointDA;
			$tmpPoint->Id = $rs['ID'];
			$tmpPoint->Name = $rs['Name'];
			$tmpPoint->Address = $rs['Address'];
			$tmpPoint->LocX = $rs['LocX'];
			$tmpPoint->LocY = $rs['LocY'];
			$tmpPoint->Owner = $rs['Owner'];
			$tmpPoint->CreatedDate = $rs['CreatedDate'];
			$tmpPoint->ModifiedDate = $rs['ModifiedDate'];
			$tmpPoint->Status = $rs['Status'];
			$data[] = $tmpPoint;
		}
		return $data;
	}
}
?>
