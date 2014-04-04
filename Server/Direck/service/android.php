<?php
#========== INCLUDE ===========#
include_once('./controller/AccountDA.php');
include_once('./controller/ContactDA.php');
include_once('./controller/PointDA.php');
include_once('./controller/AppDA.php');
include_once('./controller/ShareInfoDA.php');
include_once('./controller/PointShareInfoEnt.php');
include_once('./controller/GCM.php');
#========== DEFINE ===========#
# ErrorCode = 0 : Success - No Message
# ErrorCode = 1 : Success - With Message
# ErrorCode = 2 : UnSuccess - No Message
# ErrorCode = 3 : UnSuccess - With Message

#========== FUNCTION ===========#
function fCheckToken($iFunction, $iParam, $iTime, $iToken){
	// echo $iFunction."::".$iParam."::".$iTime."::".$iToken."<br>";
	if(!constant("CHECK_TOKEN")){
		return true;
	}
	//list($usec, $sec) = explode(" ", microtime());
    //$currentTime = round(((float)$usec + (float)$sec) * 1000);

	$currentTime = time();
	//echo $currentTime."::".(round($iTime/1000) + constant("MD5_TIME_EXPIRED"))."<br>";
	//echo $currentTime."::".($iTime/1000)."<br>";
	$iTime = round($iTime/1000) + constant("MD5_TIME_EXPIRED");
	echo $currentTime."::".$iTime."<br>";
	if($currentTime > $iTime) {
		return false;
	}
	$ServerToken = strtoupper(md5($iFunction.constant("MD5_KEY").$iTime));
	$ClientToken = strtoupper($iToken);
	//echo "server:".$iFunction.constant("MD5_KEY").$iTime."<br>";
	//echo $ServerToken."::".$ClientToken."<br>";
	if($ServerToken==$ClientToken){
		return true;
	}

}

#========== HOME PROCEDUCE ===========#
function fCreateAccount(){
	$userName = fGetFormData('name');
	$userPhoneNumber = fGetFormData('phonenumber');

	//Check Token
	$clientAction = fGetFormData('action');
	$clientTime = fGetFormData('time');
	$clientToken= fGetFormData('token');
	if(!fCheckToken($clientAction, $userName, $clientTime, $clientToken)){
		return Array("ErrorCode"=>3,"Message"=>"Invalid Token","Data"=>"");
	}

	if(!(strlen($userName)>0 && strlen($userPhoneNumber)>0)){
		return Array("ErrorCode"=>3,"Message"=>"Invalid Name or PhoneNumber","Data"=>"");
	}

    $AccountController = new AccountDA;
    if(!$AccountController->checkPhoneNumber($userPhoneNumber)){
        $resultValue = $AccountController->insert($userName, $userPhoneNumber);
		if(!$resultValue){
			return Array("ErrorCode"=>3,"Message"=>"Create Account Fail","Data"=>"");
		}
    }else {
		return Array("ErrorCode"=>2,"Message"=>"Existed phone number","Data"=>"");
	}

	$userValue = $AccountController->getByPhoneNumber($userPhoneNumber);
	if(!$userValue){
		return Array("ErrorCode"=>3,"Message"=>"Create Account Fail","Data"=>"");
	}

	// return value
	return Array("ErrorCode"=>1,"Message"=>"Create Successful", "Data"=>$userValue);
}

function fUpdateTokenKey(){
	$accountId = fGetFormData('accountid');
	$tokenKey = fGetFormData('tokenkey');
	//Check Token
	$clientAction = fGetFormData('action');
	$clientTime = fGetFormData('time');
	$clientToken= fGetFormData('token');
	if(!fCheckToken($clientAction, $accountId, $clientTime, $clientToken)){
		return Array("ErrorCode"=>3,"Message"=>"Invalid Token","Data"=>"");
	}

	if(!(strlen($accountId)>0)){
		return Array("ErrorCode"=>3,"Message"=>"Invalid account", "Data"=>"");
	}
	$AccountController = new AccountDA;
	if (strlen($tokenKey)>0) {
		$AccountController->updateTokenkey($accountId, $tokenKey);
		
	}

    // return value
    return Array("ErrorCode"=>1,"Message"=>"Update Successful", "Data"=>"");
}

function fUploadContact(){
	$accountId = fGetFormData('accountid');
	$contactStr = fGetFormData('contact');

	//Check Token
	$clientAction = fGetFormData('action');
	$clientTime = fGetFormData('time');
	$clientToken= fGetFormData('token');
	if(!fCheckToken($clientAction, $accountId, $clientTime, $clientToken)){
		return Array("ErrorCode"=>3,"Message"=>"Invalid Token","Data"=>"");
	}

	if(!(strlen($accountId)>0)){
		return Array("ErrorCode"=>3,"Message"=>"Invalid account", "Data"=>"");
	}
	$ContactController = new ContactDA;
	if (strlen($contactStr)>0) {
		
		$contactItem = explode("|", $contactStr);
		if(count($contactItem)>0){
			for($i=0;$i<count($contactItem);$i++){
				if (strlen($contactItem[$i]) >0 ) {
					$contactItemDetail = explode("::", $contactItem[$i]);
					//Check Existed PhoneNumber
					if(!$ContactController->checkPhoneNumber($contactItemDetail[1],$accountId)){
						//Insert If OK
						$ContactController->insert($accountId, $contactItemDetail[0], $contactItemDetail[1], 0);
					}			
				}
			}
		}
	}
	$listContact = $ContactController->getListByAccountId($accountId);
	if(count($listContact)>0){
		return Array("ErrorCode"=>1,"Message"=>"Upload Successful", "Data"=>$listContact);
	}
    // return value
    return Array("ErrorCode"=>3,"Message"=>"Upload UnSuccessful", "Data"=>"");
}

function fGetListPoint(){
	$accountId = fGetFormData('accountid');

	//Check Token
	$clientAction = fGetFormData('action');
	$clientTime = fGetFormData('time');
	$clientToken= fGetFormData('token');
	if(!fCheckToken($clientAction, $accountId, $clientTime, $clientToken)){
		return Array("ErrorCode"=>3,"Message"=>"Invalid Token","Data"=>"");
	}

	if(!(strlen($accountId)>0)){
		return Array("ErrorCode"=>3,"Message"=>"Invalid Account","Data"=>"");
	}
	$PointShareInfoController = new PointShareInfoEnt;
	$listPointShareInfoEnt = array();
	$listPointShareInfoEnt = $PointShareInfoController->getListEnt($accountId);
	if(count($listPointShareInfoEnt)>0){
		return Array("ErrorCode"=>0,"Message"=>"Get List Successful", "Data"=>$listPointShareInfoEnt);
	}
    // return value
    return Array("ErrorCode"=>1,"Message"=>"No Point", "Data"=>"");
}

function fSharePoint(){
	$accountId = fGetFormData('accountid');
	$friendIds = fGetFormData('friendids');
	$pointId = fGetFormData('itemid');
	$pointName = fGetFormData('pointname');
	$pointLocX = fGetFormData('locx');
	$pointLocY = fGetFormData('locy');
	$pointAddress = fGetFormData('address');

	//Check Token
	$clientAction = fGetFormData('action');
	$clientTime = fGetFormData('time');
	$clientToken= fGetFormData('token');
	if(!fCheckToken($clientAction, $accountId, $clientTime, $clientToken)){
		return Array("ErrorCode"=>3,"Message"=>"Invalid Token","Data"=>"");
	}


	if(!(strlen($accountId)>0) || !(strlen($friendIds)>0)){
		return Array("ErrorCode"=>3,"Message"=>"Invalid Account or Invalid Friend","Data"=>"");
	}

	$ShareInfoController = new ShareInfoDA;
	$PointController = new PointDA;
	$newPointId = 0;
	if(intval($pointId)>0){
		$newPointId = intval($pointId);
	}else{
		if(!(strlen($pointName)>0)){
			return Array("ErrorCode"=>3,"Message"=>"Invalid Location","Data"=>"");
		}
		$newPointId = $PointController->insert($pointName, $pointAddress, $pointLocX, $pointLocY, $accountId);
		if(!($newPointId>0)){
			return Array("ErrorCode"=>3,"Message"=>"Create Point Fail","Data"=>"");
		}
	}
	$listFriendId = explode(",", $friendIds);
	if(count($listFriendId)>0){
		for($x=0;$x<count($listFriendId);$x++){
			$tmpFriendId = intval($listFriendId[$x]);
			if($tmpFriendId>0){
				//echo $newPointId."".$listFriendId[$x]."<br>";
				if ($x==0) { //insert only 1 record to account share
					if (count($listFriendId)>=2){
						$ShareInfoController->insert($accountId, $newPointId, 0, 0,1); //view status : 1 ,  view already 	
					}else {
						$ShareInfoController->insert($accountId, $newPointId, $tmpFriendId, 0,1); //view status : 1 ,  view already	
					}
				}
				$ShareInfoController->insert($tmpFriendId, $newPointId, $accountId, 1,0); //view status : 0 , not view yet
				$AccountController = new AccountDA;
				$AccountController1 = new AccountDA;
				$friendAccount = $AccountController->getById($tmpFriendId);
				$hostAccount = $AccountController1->getById($accountId);
				if($friendAccount){
					$gcm = new GCM;
					$registatoin_ids = array($friendAccount->TokenKey);
					$message = array("direck_msg" => $hostAccount->Name.' shared to you a location');
					$result = $gcm->send_notification($registatoin_ids, $message);
				}
				
			}
			
		}
	}
	
 
    // return value
    return Array("ErrorCode"=>1,"Message"=>"Share Successful", "Data"=>"");
}

function fDeletePoint(){
	$shareId = fGetFormData('shareid');

	//Check Token
	$clientAction = fGetFormData('action');
	$clientTime = fGetFormData('time');
	$clientToken= fGetFormData('token');
	if(!fCheckToken($clientAction, $shareId, $clientTime, $clientToken)){
		return Array("ErrorCode"=>3,"Message"=>"Invalid Token","Data"=>"");
	}

	if(!(strlen($shareId)>0)){
		return Array("ErrorCode"=>3,"Message"=>"Invalid Point ","Data"=>"");
	}

	$ShareInfoController = new ShareInfoDA;
	if(intval($shareId)>0){
		if($ShareInfoController->delete($shareId)){
			return Array("ErrorCode"=>1,"Message"=>"Delete Successful", "Data"=>"");
		}
	}
    // return value
    return Array("ErrorCode"=>3,"Message"=>"Delete Fail","Data"=>"");
}

function fBookmarkPoint(){
	$accountId = fGetFormData('accountid');
	$pointId = fGetFormData('itemid');
	$pointName = fGetFormData('pointname');
	$pointLocX = fGetFormData('locx');
	$pointLocY = fGetFormData('locy');
	$pointAddress = fGetFormData('address');

	//Check Token
	$clientAction = fGetFormData('action');
	$clientTime = fGetFormData('time');
	$clientToken= fGetFormData('token');
	if(!fCheckToken($clientAction, $accountId, $clientTime, $clientToken)){
		return Array("ErrorCode"=>3,"Message"=>"Invalid Token","Data"=>"");
	}


	if(!(strlen($accountId)>0)){
		return Array("ErrorCode"=>3,"Message"=>"Invalid Account ","Data"=>"");
	}

	$ShareInfoController = new ShareInfoDA;
	$PointController = new PointDA;
	$newPointId = 0;
	if(intval($pointId)>0){
		$newPointId = intval($pointId);
	}else{
		if(!(strlen($pointName)>0)){
			return Array("ErrorCode"=>3,"Message"=>"Invalid Location","Data"=>"");
		}
		$newPointId = $PointController->insert($pointName, $pointAddress, $pointLocX, $pointLocY, $accountId);
		if(!($newPointId>0)){
			return Array("ErrorCode"=>3,"Message"=>"Create Point Fail","Data"=>"");
		}
	}
	
	$ShareInfoController->insert($accountId, $newPointId, $accountId, 2,1); //view status : 1 is viewed already
	
    // return value
    return Array("ErrorCode"=>1,"Message"=>"Share Successful", "Data"=>"");
}


function fBookmarkLocation(){
	$accountId = fGetFormData('accountid');
	$pointId = fGetFormData('itemid');

	//Check Token
	$clientAction = fGetFormData('action');
	$clientTime = fGetFormData('time');
	$clientToken= fGetFormData('token');
	if(!fCheckToken($clientAction, $accountId, $clientTime, $clientToken)){
		return Array("ErrorCode"=>3,"Message"=>"Invalid Token","Data"=>"");
	}


	if(!(strlen($accountId)>0)){
		return Array("ErrorCode"=>3,"Message"=>"Invalid Account ","Data"=>"");
	}

	$ShareInfoController = new ShareInfoDA;
	if(intval($pointId)>0){
		$ShareInfoController->insert($accountId, $pointId, 0, 2);
	}
	
	$ShareInfoController->insert($accountId, $newPointId, $accountId, 2,1); //view status : 1 is viewed already
	
    // return value
    return Array("ErrorCode"=>1,"Message"=>"Bookmark Successful", "Data"=>"");
}


function fUpdateViewStatus(){
	$shareID = fGetFormData('shareid');

	//Check Token
	$clientAction = fGetFormData('action');
	$clientTime = fGetFormData('time');
	$clientToken= fGetFormData('token');
	if(!fCheckToken($clientAction, $shareID, $clientTime, $clientToken)){
		return Array("ErrorCode"=>3,"Message"=>"Invalid Token","Data"=>"");
	}
	
	if(!(strlen($shareID)>0)) {
		return Array("ErrorCode"=>3,"Message"=>"Invalid share ID","Data"=>"");
	}

	$ShareInfoController = new ShareInfoDA;
	$ShareInfoController->updateViewStatus($shareID);
    // return value
    return Array("ErrorCode"=>1,"Message"=>"Share Successful", "Data"=>"");
}

function fGetNotification(){

    // return value
    return Array("ErrorCode"=>1,"Message"=>"Create Successful", "Data"=>"");
}
function fGetAppInfo() {
	//Check Token
	$clientAction = fGetFormData('action');
	$clientTime = fGetFormData('time');
	$clientToken= fGetFormData('token');
	if(!fCheckToken($clientAction, "$shareID", $clientTime, $clientToken)){
		return Array("ErrorCode"=>3,"Message"=>"Invalid Token","Data"=>"");
	}

    $AppInfo = new AppDA;
	$AppInfo->getAPPInfo();
	return Array("ErrorCode"=>0,"Message"=>"Get Successful", "Data"=>$AppInfo);
}

function getDefault(){	
	return Array("ErrorCode"=>3,"Message"=>"Invalid Function","Data"=>"");
}

function getAction(){
	$action = fGetFormData('action');
	switch ($action)
	{
		case 'create-user':	return fCreateAccount();
		case 'update-tokenkey':	return fUpdateTokenKey();
        case 'upload-contact':    return fUploadContact();
        case 'get-list-point':    return fGetListPoint();
        case 'share-point':    return fSharePoint();
        case 'delete-point':    return fDeletePoint();
		case 'bookmark-point':    return fBookmarkPoint();
		case 'update-ViewStatus':    return fUpdateViewStatus();
        case 'get-notification':    return fGetNotification();
		case 'get-AppInfo': return fGetAppInfo();
		default:		return getDefault();
	}
}
