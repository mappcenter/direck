<?php
session_start();
define('IN_DIRECK',true);
#######################################
# INCLUDE FILES
#######################################
include_once('config/_config.php');
#######################################
# GET INIT
#######################################
define("IOS","ios");
define("ANDROID", "android");

#######################################
# GET Params
#######################################
$os = isset($_GET['os'])?$_GET['os']:'';
$token = isset($_GET['token'])?$_GET['token']:'';
$time = isset($_GET['time'])?$_GET['time']:'';
#######################################
# OUT PUT
#######################################
include('controller/AccountDA.php');
$AccountControl = new AccountDA;

$listAllAccount = $AccountControl->getAll();

for($i=0;$i<count($listAllAccount);$i++){
	echo "<li><span>";
	echo "ID: ".$listAllAccount[$i]->Id." -> Name: ".$listAllAccount[$i]->Name." -> Phone: ".$listAllAccount[$i]->PhoneNumber." -> CreatedDate: ".date("Y/m/d H:i:s", $listAllAccount[$i]->CreatedDate)."";
	echo "</span></li>";
}
exit();

?>



?>
