<?php
session_start();
define('IN_DIRECK',true);

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
if (isset($_GET["regId"]) && isset($_GET["message"])) {
    $regId = $_GET["regId"];
    $message = $_GET["message"];
    include_once('./controller/GCM.php');
    $gcm = new GCM;

    $registatoin_ids = array($regId);
    $message = array("direck_msg" => $message);

    $result = $gcm->send_notification($registatoin_ids, $message);

    echo $result;

}
?>
