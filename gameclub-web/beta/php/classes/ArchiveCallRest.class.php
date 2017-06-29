<?php include_once("BaseCallRest.class.php")?>
<?php
class BlogCallRest extends BaseCallRest
{ 
	private static $url;
	
	public function download_file($name,$module){
			$params = array();
			$meta_tags = array();
			array_push($params,"downloadFile");
			array_push($params,$id);
			$result = $this->call_rest_service(self::$url,$params);

			if (is_null($this->getError_code())){
			}
			return $result;
	}

	 public function __construct() {
		if (is_null(self::$url)){
			self::$url = Util::read_property("archive.restws.url");
		}
   	 }

}


?>