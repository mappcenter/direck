<?php
session_start();
define('IN_DIRECK',true);
#######################################
# INCLUDE FILES
#######################################
include_once('config/_config.php');
include_once('./controller/GCM.php');
include_once('./controller/AccountDA.php');
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

if (isset($_GET["message"]) || isset($_GET["id"])) {
    
    $message = $_GET["message"];
    $accountId = $_GET["id"];
    
    $gcmController = new GCM();
    $AccountController = new AccountDA();

	$regId = $AccountController->getTokenKeyById($accountId);
	echo $regId."<br>";
    $registatoin_ids = array($regId);
    $message = array("price" => $message);
    echo $message."<br>";
    $result = $gcmController->send_notification($registatoin_ids, $message);

    echo $result;
}

?>
