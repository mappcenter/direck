<?php
if (!defined('IN_DIRECK')) die("Hack");

$mysql = new mysql;
$mysql->connect($config['db_host'],$config['db_user'],$config['db_pass'],$config['db_name']);

class AppDA {
	/*
	public $Version = "";
	public $UpdateApp=0; //0: no update, 1: optional update, 2 : must update
	public $LinkUpdate="";
	public $AdType = 0; //0: no ad; 1:admod; 2:adapp
	public $URL_AD = "";
	public $URL_Link="";
	*/
	public $Version = "";
	public $IsUpdate = 0; //0: no update, 1: optional update, 2 : must update
	public $MessageUpdate = "";
	public $LinkUpdate = "";
	public $ShowAdv = 0;//0: no show; 1: show
	public $ArrayAdvType = array("ADMOD", "HOME");
	public $AdvType = ""; // ADMOD :: HOME
	public $AdvLink = ""; //
	public $AdvImage = "";


	public function getAPPInfo(){
	$ArrayAdvType = array("ADMOD", "HOME");
	$this->Version = "1.1";
	$this->IsUpdate = "0"; //0: no update, 1: optional update, 2 : must update
	$this->MessageUpdate = "Update new version 1.0.1";
	$this->LinkUpdate = "http://wwww.direck.com.vn";
	$this->ShowAdv = "1";
	$this->AdvType = $ArrayAdvType[array_rand($ArrayAdvType)]; // ADMOD :: HOME
	$this->AdvLink = "http://google.com.vn"; //
	$this->AdvImage = "http://www.creativefreedom.co.uk/images/uploads/custom-icon-design-banner-01.png";
	
	return $this;
	}
	
	
	
}
?>
