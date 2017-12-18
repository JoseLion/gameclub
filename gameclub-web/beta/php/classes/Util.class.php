<?php include_once("Properties.class.php"); ?>
<?php class Util {
	private static $FILE_PROPERTIES ="/php/properties/gameclub.properties";
	
	public static function read_property($property) {	
		$properties = new Properties();	
		$properties->load(file_get_contents($_SERVER['DOCUMENT_ROOT'].self::$FILE_PROPERTIES));
		
		return $properties->getProperty($property);
	}	

	public static function replace_quotes($string) {
		return str_replace("'", "&#39;", $string);
	}
} ?>