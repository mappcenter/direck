<?php
if (!defined('IN_DIRECK')) die("Hack");

$mysql = new mysql;
$mysql->connect($config['db_host'],$config['db_user'],$config['db_pass'],$config['db_name']);


class AccountDA {
	public $Id = 0;
	public $Name = "";
	public $PhoneNumber = "";
	public $CreatedDate = "";
	public $ModifiedDate = "";
	public $Status = "";
	public $TokenKey="";

	public function insert($iName, $iPhoneNumber){
		global $mysql, $tb_prefix;
		$mysql->query("INSERT INTO `".$tb_prefix."account` (`Name`, `PhoneNumber`, `CreatedDate`, `Status`) VALUES ('".$iName."', '".$iPhoneNumber."', '".time()."', 1)");
		return true;
	}

	public function delete($inputId){
		global $mysql, $tb_prefix;
		$mysql->query("DELETE FROM `".$tb_prefix."account` WHERE `ID` = '".$inputId."'");
		return true;
	}

	public function updateTokenkey($iAccountId, $iTokenKey){
		global $mysql, $tb_prefix;
		$mysql->query("UPDATE `".$tb_prefix."account` SET `TokenKey`='".$iTokenKey."' WHERE `ID`='".$iAccountId."'");
		return true;
	}

    public function checkPhoneNumber($InputPhoneNumber){
    	global $mysql, $tb_prefix;
		$data = 0;
		$order_sql = "ORDER BY ID ASC";
		$where_sql = "WHERE `PhoneNumber` = '".$InputPhoneNumber."'";

		$query = $mysql->query("SELECT Count(ID) AS Total FROM `".$tb_prefix."account` $where_sql $order_sql ;");
		while ($rs = $mysql->fetch_array($query)) {
			$data = $rs['Total'];
		}
		return ($data>0);
	}

	public function getAll(){
		global $mysql, $tb_prefix;
		$data = array();
		$order_sql = "ORDER BY ID ASC";
		$where_sql = "WHERE 1 = 1";

		$query = $mysql->query("SELECT * FROM `".$tb_prefix."account` $where_sql $order_sql ;");
		while ($rs = $mysql->fetch_array($query)) {
			$tmpAccount = new AccountDA;
			$tmpAccount->Id = $rs['ID'];
			$tmpAccount->Name = $rs['Name'];
			$tmpAccount->PhoneNumber = $rs['PhoneNumber'];
			$tmpAccount->CreatedDate = $rs['CreatedDate'];
			$tmpAccount->ModifiedDate = $rs['ModifiedDate'];
			$tmpAccount->Status = $rs['Status'];
			$tmpAccount->TokenKey = $rs['TokenKey'];
			$data[] = $tmpAccount;
		}
		return $data;
	}
	
	public function getAllWithToken(){
		global $mysql, $tb_prefix;
		$data = array();
		$order_sql = "ORDER BY ID ASC";
		$where_sql = "WHERE TokenKey != ''";

		$query = $mysql->query("SELECT * FROM `".$tb_prefix."account` $where_sql $order_sql ;");
		/*
		while ($rs = $mysql->fetch_array($query)) {
			$tmpAccount = new AccountDA;
			$tmpAccount->Id = $rs['ID'];
			$tmpAccount->Name = $rs['Name'];
			$tmpAccount->PhoneNumber = $rs['PhoneNumber'];
			$tmpAccount->CreatedDate = $rs['CreatedDate'];
			$tmpAccount->ModifiedDate = $rs['ModifiedDate'];
			$tmpAccount->Status = $rs['Status'];
			$tmpAccount->TokenKey = $rs['TokenKey'];
			$data[] = $tmpAccount;
		}
		return $data;
		*/
		return $query;
	}

	public function getById($InputId){
		global $mysql, $tb_prefix;
		//$where_sql = "WHERE `Status` = 1 AND ID=".$InputId;
		$where_sql = "WHERE  ID=".$InputId;
		$order_sql = "ORDER BY ID DESC";

		$query = $mysql->query("SELECT * FROM `".$tb_prefix."account` $where_sql $order_sql ;");
		while ($rs = $mysql->fetch_array($query)) {
			$this->Id = $rs['ID'];
			$this->Name = $rs['Name'];
			$this->PhoneNumber = $rs['PhoneNumber'];
			$this->CreatedDate = $rs['CreatedDate'];
			$this->ModifiedDate = $rs['ModifiedDate'];
			$this->Status = $rs['Status'];
			$this->TokenKey = $rs['TokenKey'];
		}
		return $this;
	} 

	public function getByPhoneNumber($InputPhoneNumber){
		global $mysql, $tb_prefix;
		$where_sql = "WHERE `Status` = 1 AND `PhoneNumber`='".$InputPhoneNumber."'";
		$order_sql = "ORDER BY ID DESC";

		$query = $mysql->query("SELECT * FROM `".$tb_prefix."account` $where_sql $order_sql ;");
		while ($rs = $mysql->fetch_array($query)) {
			$this->Id = $rs['ID'];
			$this->Name = $rs['Name'];
			$this->PhoneNumber = $rs['PhoneNumber'];
			$this->CreatedDate = $rs['CreatedDate'];
			$this->ModifiedDate = $rs['ModifiedDate'];
			$this->Status = $rs['Status'];
			$this->TokenKey = $rs['TokenKey'];
		}
		return $this;
	}
}
?>
