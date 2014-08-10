<?php
if (!defined('IN_DIRECK')) die("Hack");


class AppDA {
	public $Version = "";
	public $IsUpdate = 0; 
	public $MessageUpdate = "";
	public $LinkUpdate = "";
	public $ShowAdv = 1;
	public $AdvType = ""; // ADMOD :: HOME
	public $AdvLink = ""; //
	public $AdvImage = "";


	public function getAPPInfo(){
		$ArrayAdvType = array("ADMOD", "HOME");
		$this->Version = "1.1";
		$this->IsUpdate = constant("IS_UPDATE");
		$this->MessageUpdate = "Update new version 1.0.1";
		$this->LinkUpdate = "http://wwww.direck.com.vn";
		$this->ShowAdv = constant("SHOW_ADV");
		$this->AdvType = "HOME";
		if(constant("SHOW_ADV_ADMOD")==1){
			$this->AdvType = "ADMOD";
		}else if(constant("SHOW_ADV_ADMOD")==2){
			$this->AdvType = $ArrayAdvType[array_rand($ArrayAdvType)]; // ADMOD :: HOME
		}
		
		$this->AdvLink = "http://google.com.vn"; //
		$this->AdvImage = "http://stc.nas.nixcdn.com/upload/2014/04/21/517957e78d.jpg";
		
		return $this;
	}
	
	
	
}
?>
