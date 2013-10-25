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
	
	public function insert($iName, $iPhoneNumber){
		global $mysql, $tb_prefix;
		$mysql->query("INSERT INTO `".$tb_prefix."account` (`Name`, `PhoneNumber`, `CreatedDate`, `Status`) VALUES ('".$iName."', '".$iPhoneNumber."', '".time()."', 1)");
		return true;
	}
	
	public function delete($inputId){
		global $mysql, $tb_prefix;
		$mysql->query("DELETE FROM `".$tb_prefix."category` WHERE `CatId` = '".$inputId."'");
		return $true;
	}

	public function update($inputId, $inputName){
		global $mysql, $tb_prefix;
		$mysql->query("UPDATE `".$tb_prefix."category` SET CatName = '$inputName' WHERE CatId = '".$inputId."'");
		return $true;
	}

	public function getAll(){
		global $mysql, $tb_prefix;
		$data = array();
		$order_sql = "ORDER BY CatId ASC";
		$where_sql = "WHERE CatStatus = 1";
		
		$query = $mysql->query("SELECT * FROM `".$tb_prefix."category` $where_sql $order_sql ;");
		while ($rs = $mysql->fetch_array($query)) {
			$tmpCategory = new CategoryDA;			
			$tmpCategory->Id = $rs['CatId'];
			$tmpCategory->Name = $rs['CatName'];
			$data[] = $tmpCategory;
		}
		return $data;
	}

	public function getAllName(){
		global $mysql, $tb_prefix;
		$data = array();
		$order_sql = "ORDER BY CatId ASC";
		$where_sql = "WHERE CatStatus = 1";
		
		$query = $mysql->query("SELECT * FROM `".$tb_prefix."category` $where_sql $order_sql ;");
		while ($rs = $mysql->fetch_array($query)) {			
			$catID = $rs['CatId'];
			$catName = $rs['CatName'];
			$data[$catID] = $catName;
		}
		return $data;
	}

	public function getById($InputId){
		global $mysql, $tb_prefix;
		$where_sql = "WHERE `Status` = 1 AND ID=".$InputId;
		$order_sql = "ORDER BY ID DESC";
		
		$query = $mysql->query("SELECT * FROM `".$tb_prefix."account` $where_sql $order_sql ;");
		while ($rs = $mysql->fetch_array($query)) {
			$this->Id = $rs['ID'];
			$this->Name = $rs['Name'];
			$this->PhoneNumber = $rs['PhoneNumber'];
			$this->CreatedDate = $rs['CreatedDate'];
			$this->ModifiedDate = $rs['ModifiedDate'];
			$this->Status = $rs['Status'];
		}
		return $this;
	}
}
?>

