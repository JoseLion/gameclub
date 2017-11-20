<?php include_once("BaseCallRest.class.php")?>
<?php
class BlogCallRest extends BaseCallRest
{ 
	private static $url;
	
	public function find_one_blog_meta_tags($id){
			$params = array();
			$meta_tags = array();
			array_push($params,"findOneMetaTags");
			array_push($params,$id);
			$result = $this->call_rest_service(self::$url,$params);

			if (is_null($this->getError_code())){
				$meta_tags["title"] = $result->title;
				$meta_tags["summary"] = $result->summary;
				$meta_tags["banner"] = $result->banner;
			}
			return $meta_tags ;
	}

	 public function __construct() {
		if (is_null(self::$url)){
			self::$url = Util::read_property("blog.restws.url");
		}
   	 }

}


?>