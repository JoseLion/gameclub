<?php include_once("BaseCallRest.class.php")?>
<?php class Rest extends BaseCallRest { 
	private static $url;

	public function get($path) {
		$response = $this->call_rest_service(self::$url . $path, null);
		return $response;
	}

	 public function __construct() {
		if (is_null(self::$url)) {
			self::$url = (isset($_SERVER['HTTPS']) ? "https" : "http") . "://$_SERVER[HTTP_HOST]:" . ($_SERVER['SERVER_PORT'] == 80 ? "8090" : "8390") . "/gameclub/";
		}
   	 }
} ?>