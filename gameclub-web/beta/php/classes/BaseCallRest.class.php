<?php include_once("Util.class.php")?>
<?php
class  BaseCallRest
{ 
	private static $HTTP_CODE_200 = 200;
	private static $HTTP_CODE_404 = 404;

	private $error_code;

	public function getError_code(){
		return $this->error_code;
	}
	
	protected function call_rest_service($url,$params){
		
		$request = $url;
		foreach( $params as $param=>$value){
			$request .= "/".Util::replace_quotes($value);
		}
				
		$context = stream_context_create(array('http' => array('header'=>'Connection: close\r\n','ignore_errors' => true)));
 
		$result=@file_get_contents($request,false,$context);
		$header_http = $this->read_header_http($http_response_header);
		if ($header_http['response_code'] != self::$HTTP_CODE_200) {
			$this->$error_code = $header_http['response_code'];
		}
		return $this->decode_json($result);
	}
	
	private function read_header_http($headers)
	{
		$header = array();
		foreach( $headers as $k=>$v )
		{
			$t = explode( ':', $v, 2 );
			if( isset( $t[1] ) )
				$header[ trim($t[0]) ] = trim( $t[1] );
			else
			{
				$header[] = $v;
				if( preg_match( "#HTTP/[0-9\.]+\s+([0-9]+)#",$v, $out ) )
					$header['response_code'] = intval($out[1]);
			}
		}
		return $header;
	}
	
	private function decode_json($data) {
		$json = json_decode($data);
		return $json;		
	}

}
?>
