<?php

class PointShareInfoEnt {
	public $Id = 0;
	public $AccountID = 0;
	public $AccountName = "";
	public $PointID = 0;
	public $PointName = "";
	public $PointAddress = "";
	public $PointLocX = 0;
	public $PointLocY = 0;
	public $PointOwner = 0;
	public $PointCreatedDate = 0;
	public $PointModifiedDate = 0;
	public $PointStatus = 0;
	public $FriendID = 0;
	public $FriendName = "";
	public $Type = 0;	
	public $ViewStatus = 0;
	public $CreatedDate = 0;

	public function insertView($iPoint, $iShareInfo){
		$this->Id = $iShareInfo->Id;
		$this->AccountID = $iShareInfo->AccountID;
		$this->AccountName = "";
		$this->PointID = $iShareInfo->PointID;
		$this->PointName = $iPoint->Name;
		$this->PointAddress = $iPoint->Address;
		$this->PointLocX = $iPoint->LocX;
		$this->PointLocY = $iPoint->LocY;
		$this->PointOwner = $iPoint->Owner;
		$this->PointCreatedDate = $iPoint->CreatedDate;
		$this->PointModifiedDate = $iPoint->ModifiedDate;
		$this->PointStatus = $iPoint->Status;
		$this->FriendID = $iShareInfo->FriendID;
		$this->FriendName = "";
		$this->Type = $iShareInfo->Type;
		$this->ViewStatus = $iShareInfo->ViewStatus;
		$this->CreatedDate = $iShareInfo->CreatedDate;

		return $this;
	}

}

?>