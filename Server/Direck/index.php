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
$os = fGetFormData('os');
$token = fGetFormData('token');
$time = fGetFormData('time');
//echo $os."::".$iTime."::".$token."<br>";
#######################################
# OUT PUT
#######################################
if ($os == IOS) {
	include("service/ios.php");
	$result = getAction();
}else if ($os == ANDROID)
{
	include("service/android.php");
	$result = getAction();
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
