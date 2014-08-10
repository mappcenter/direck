<?php
if (!defined('IN_DIRECK')) die("Hack");
class mysql { 
	var $link_id;
	var $log_file = 'logs.txt';
	var $log_error = 1;
	function connect($db_host, $db_username, $db_password, $db_name) {
		$this->link_id = @mysql_connect($db_host, $db_username, $db_password);
		mysql_query("SET character_set_results=utf8", $this->link_id);
		mysql_query("SET character_set_client=utf8", $this->link_id);
		mysql_query("SET character_set_connection=utf8", $this->link_id);
		if ($this->link_id)
		{
			if (@mysql_select_db($db_name, $this->link_id)) return $this->link_id;
			else $this->show_error('Unable to select database. MySQL reported: '.mysql_error());
		}
		else $this->show_error('Unable to connect to MySQL server. MySQL reported: '.mysql_error());
	}
	
	function query($input){
		$q = @mysql_query($input) or $this->show_error("<b>Lỗi MySQL Query</b> : ".mysql_error(),$input);
		return $q;
	}
	
	function fetch_array($query_id, $type=MYSQL_BOTH){
		$fa = @mysql_fetch_array($query_id,$type);
		return $fa;
	}
	
	function num_rows($query_id) {
		$nr = @mysql_num_rows($query_id);
		return $nr;
	}
	
	function result($query_id, $row=0, $field) {
		$r = @mysql_result($query_id, $row, $field);
		return $r;
	}
	
	function insert_id() {
		return @mysql_insert_id($this->link_id);
	}
	function show_error($input,$q){
		if ($this->log_error) {
			$file_name = $this->log_file;
			$fp = fopen($file_name,'a');
			flock($fp,2);
			fwrite($fp,"### ".date('H:s:i d-m-Y',NOW)." ###\n");
			fwrite($fp,$input."\n");
			fwrite($fp,"QUERY : ".$q."\n");
			flock($fp,1);
			fclose($fp);
		}
		die($input);
	}
}
?>
