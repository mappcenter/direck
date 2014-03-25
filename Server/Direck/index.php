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
$os = isset($_POST['os'])?$_POST['os']:'';
$token = isset($_POST['token'])?$_POST['token']:'';
$time = isset($_POST['time'])?$_POST['time']:'';
#######################################
# OUT PUT
#######################################
if ($os == IOS) {
	include("service/ios.php");
	$result = getaction();
}else if ($os == ANDROID)
{
	include("service/android.php");
	$result = getaction();
}

if (isset($result))
{
	echo  json_encode($result);
}else{
	echo "";
}
exit();

?>



?>
