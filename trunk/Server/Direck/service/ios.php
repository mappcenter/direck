<?php
#========== INCLUDE ===========#
include_once('./controller/AccountDA.php');
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
	echo $userName."::::".$userPhoneNumber;
	if(!(strlen($userName)>0 && strlen($userPhoneNumber)>0)){
		return Array("ErrorCode"=>3,"Message"=>"Invalid Name or PhoneNumber","Data"=>"");
	}

	$AccountController = new AccountDA;
	$resultValue = $AccountController->insert($userName, $userPhoneNumber);
	if(!$resultValue){
		return Array("ErrorCode"=>3,"Message"=>"Create Account Fail","Data"=>"");
	}

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
		default:		return getDefault();
	}
}
