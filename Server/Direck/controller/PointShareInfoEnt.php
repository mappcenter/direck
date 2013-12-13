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
	public function getListEnt($accountID){
	global $mysql, $tb_prefix;
		$data = array();
		$order_sql = "ORDER BY ID ASC";
		// $where_sql = "WHERE `AccountId` = ".$iAccountId." OR `FriendID` = ".$iAccountId;
		$where_sql = "WHERE `sin.AccountId` = ".$accountID;
		$query_sql = "select sin.ID, sin.AccountID,acc.name as AccountName,sin.PointId,
							pnt.address as PointAddress, pnt.LocX, pnt.LocY,pnt.name as PointName,pnt.ownerName, FROM_UNIXTIME(pnt.CreatedDate) as PointCreateDate, 
							 FROM_UNIXTIME(pnt.ModifiedDate) as PointModifiedDate,pnt.Status as PointStatus,
							sin.FriendID,frn.Name as FriendName,sin.Type,sin.ViewStatus, FROM_UNIXTIME(sin.CreatedDate) as SharedDate 
							from `".$tb_prefix."sharedinfo` sin inner join `".$tb_prefix."account` acc on sin.accountid = acc.id
							inner join `".$tb_prefix."account` frn on sin.friendid = frn.id
							inner join (select p.*,a.name as ownerName from `".$tb_prefix."point` p inner join `".$tb_prefix."account` a on p.owner = a.id)  pnt on sin.pointid = pnt.id WHERE sin.AccountId = ".$accountID;
							
		$query = $mysql->query($query_sql);
		while ($rs = $mysql->fetch_array($query)) {
			$tmpShareInfo = new PointShareInfoEnt;
			$tmpShareInfo->Id = $rs['ID'];
			$tmpShareInfo->AccountID = $rs['AccountID'];
			$tmpShareInfo->AccountName = $rs['AccountName'];
			$tmpShareInfo->PointID = $rs['PointId'];
			$tmpShareInfo->PointName = $rs['PointName'];
			$tmpShareInfo->PointAddress = $rs['PointAddress'];
			$tmpShareInfo->PointLocX = $rs['LocX'];
			$tmpShareInfo->PointLocY = $rs['LocY'];
			$tmpShareInfo->PointOwner = $rs['ownerName'];
			$tmpShareInfo->PointCreatedDate = $rs['PointCreateDate'];
			$tmpShareInfo->PointModifiedDate = $rs['PointModifiedDate'];
			$tmpShareInfo->PointStatus = $rs['PointStatus'];
			$tmpShareInfo->FriendID = $rs['FriendID'];
			$tmpShareInfo->FriendName = $rs['FriendName'];
			$tmpShareInfo->Type = $rs['Type'];
			$tmpShareInfo->ViewStatus = $rs['ViewStatus'];
			$tmpShareInfo->CreatedDate = $rs['SharedDate'];
			
			$data[] = $tmpShareInfo;
		}
		return $data;
	}

}

?>