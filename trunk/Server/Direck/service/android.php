<?php
#========== INCLUDE ===========#
include_once('./controller/AccountDA.php');
include_once('./controller/ContactDA.php');
include_once('./controller/PointDA.php');
include_once('./controller/ShareInfoDA.php');
include_once('./controller/PointShareInfoEnt.php');

#========== DEFINE ===========#
# ErrorCode = 0 : Success - No Message
# ErrorCode = 1 : Success - With Message
# ErrorCode = 2 : UnSuccess - No Message
# ErrorCode = 3 : UnSuccess - With Message

#========== FUNCTION ===========#

#========== HOME PROCEDUCE ===========#
function fCreateAccount(){
	$userName = isset($_GET['name'])?$_GET['name']:'';
	$userPhoneNumber = isset($_GET['phonenumber'])?$_GET['phonenumber']:'';

	if(!(strlen($userName)>0 && strlen($userPhoneNumber)>0)){
		return Array("ErrorCode"=>3,"Message"=>"Invalid Name or PhoneNumber","Data"=>"");
	}

    $AccountController = new AccountDA;
    if(!$AccountController->checkPhoneNumber($userPhoneNumber)){
        $resultValue = $AccountController->insert($userName, $userPhoneNumber);
		if(!$resultValue){
			return Array("ErrorCode"=>3,"Message"=>"Create Account Fail","Data"=>"");
		}
    }

	$userValue = $AccountController->getByPhoneNumber($userPhoneNumber);
	if(!$userValue){
		return Array("ErrorCode"=>3,"Message"=>"Create Account Fail","Data"=>"");
	}

	// return value
	return Array("ErrorCode"=>1,"Message"=>"Create Successful", "Data"=>$userValue);
}

function fUploadContact(){
	$accountId = isset($_GET['accountid'])?$_GET['accountid']:'';
	$contactStr = isset($_GET['contact'])?$_GET['contact']:'';

	if(!(strlen($accountId)>0 && strlen($contactStr)>0)){
		return Array("ErrorCode"=>3,"Message"=>"Invalid Account","Data"=>"");
	}

	$ContactController = new ContactDA;

	$contactItem = explode("|", $contactStr);
	if(count($contactItem)>0){
		for($i=0;$i<count($contactItem);$i++){
			$contactItemDetail = explode("::", $contactItem[$i]);

			//Check Existed PhoneNumber
			if(!$ContactController->checkPhoneNumber($contactItemDetail[1])){
				//Insert If OK
				$ContactController->insert($accountId, $contactItemDetail[0], $contactItemDetail[1], 0);
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
	$accountId = isset($_GET['accountid'])?$_GET['accountid']:'';

	if(!(strlen($accountId)>0)){
		return Array("ErrorCode"=>3,"Message"=>"Invalid Account","Data"=>"");
	}
	$ShareInfoController = new ShareInfoDA;
	$PointController = new PointDA;
	$PointShareInfoController = new PointShareInfoEnt;
	$listShareInfo = $ShareInfoController->getListByAccountId($accountId);

	if(count($listShareInfo)>0){
		$strPointIDs = "0";
		$listPointShareInfoEnt = array();
		for($i=0;$i<count($listShareInfo);$i++){
			if(($listShareInfo[$i]->Id)>0){
				$strPointIDs = $strPointIDs.",".$listShareInfo[$i]->Id;
			}			
		}
		$listPoint = $PointController->getListByIds($strPointIDs);

		for($j=0;$j<count($listShareInfo);$j++){
			if(($listShareInfo[$j]->Id)>0){
				$tmpShareId = "'key_".$listShareInfo[$j]->Id."'";
				$tmpPointShareInfoEntX = new PointShareInfoEnt;
				$tmpPointShareInfoEnt = $tmpPointShareInfoEntX->insertView($listPoint[$tmpShareId], $listShareInfo[$j]);

				$listPointShareInfoEnt[] = $tmpPointShareInfoEnt;
			}			
		}

		return Array("ErrorCode"=>0,"Message"=>"Get List Successful", "Data"=>$listPointShareInfoEnt);
	}

    // return value
    return Array("ErrorCode"=>1,"Message"=>"No Point", "Data"=>"");
}

function fSharePoint(){
	$accountId = isset($_GET['accountid'])?$_GET['accountid']:'';
	$friendIds = isset($_GET['friendids'])?$_GET['friendids']:'';
	$pointId = isset($_GET['itemid'])?$_GET['itemid']:'';
	$pointName = isset($_GET['pointname'])?$_GET['pointname']:'';
	$pointLocX = isset($_GET['locx'])?$_GET['locx']:'';
	$pointLocY = isset($_GET['locy'])?$_GET['locy']:'';
	$pointAddress = isset($_GET['address'])?$_GET['address']:'';


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
				$ShareInfoController->insert($accountId, $newPointId, $tmpFriendId, 0);
				$ShareInfoController->insert($tmpFriendId, $newPointId, $accountId, 1);
			}
			
		}
	}
	

    // return value
    return Array("ErrorCode"=>1,"Message"=>"Share Successful", "Data"=>"");
}

function fGetNotification(){

    // return value
    return Array("ErrorCode"=>1,"Message"=>"Create Successful", "Data"=>"");
}

function getDefault(){
	return Array("ErrorCode"=>3,"Message"=>"Invalid Function","Data"=>"");
}

function getaction(){
	$action = isset($_GET['action'])?$_GET['action']:'';
	switch ($action)
	{
		case 'create-user':	return fCreateAccount();
        case 'upload-contact':    return fUploadContact();
        case 'get-list-point':    return fGetListPoint();
        case 'share-point':    return fSharePoint();
        case 'get-notification':    return fGetNotification();
		default:		return getDefault();
	}
}
