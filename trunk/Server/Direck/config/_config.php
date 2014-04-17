<?php
$config['db_host']	= 'localhost';
$config['db_name'] 	= 'vnponcom_direck';
$config['db_user']	= 'vnponcom_admin';
$config['db_pass']	= 'E7k6T9nt';

define("METHOD_POST", false);
define("CHECK_TOKEN", true);
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
