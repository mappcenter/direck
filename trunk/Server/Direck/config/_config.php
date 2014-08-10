<?php
$config['db_host']	= 'localhost';
$config['db_name'] 	= 'vnponcom_direck';
$config['db_user']	= 'vnponcom_admin';
$config['db_pass']	= 'E7k6T9nt';

define("METHOD_POST", true);
define("CHECK_TOKEN", true);
define("SHOW_ADV", 0);  // 0: OFF - 1: ON
define("SHOW_ADV_ADMOD", 0);  //0: OFF - 1: ON - 2: BOTH (ADMOD+HOME)
define("IS_UPDATE", 0);  //0: NO UPDATE - 1: OPTIONAL UPDATE - 2 : MUST UPDATE
define("MD5_KEY", "key@Direk");
define("MD5_TIME_EXPIRED", 36000);
define("GOOGLE_API_KEY", "AIzaSyDXbgQ70sWdR4XP8XxWGQhRsPa3wpxBujY"); // Place your Google API Key
$tb_prefix		= 'direck_';

include('_dbconnect.php');


#========== FUNCTION ===========#
function fGetFormData($iParam){
	
	if(constant("METHOD_POST")){
		return isset($_POST[$iParam]) ? $_POST[$iParam]: '';
	}
	
	return isset($_GET[$iParam]) ? $_GET[$iParam]: '';

}


?>
