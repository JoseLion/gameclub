<?php
	$content = file_get_contents("http://local.gameclub/gameclub/game/3/2/resident-evil-7-biohazard");
	sleep(10);
	$begin = strpos($content, '<head>');
	$end = strpos($content, '</head>');

	/*function get_url_contents($url){
		$crl = curl_init();
		$timeout = 5;
		curl_setopt ($crl, CURLOPT_URL,$url);
		curl_setopt ($crl, CURLOPT_RETURNTRANSFER, 1);
		curl_setopt ($crl, CURLOPT_CONNECTTIMEOUT, $timeout);
		$ret = curl_exec($crl);
		curl_close($crl);
		return $ret;
	}

	$content = get_url_contents('http://local.gameclub/gameclub/game/3/2/resident-evil-7-biohazard');*/
	sleep(5);

	echo "<script>console.log(" . substr($content, $begin, $end) . ")</script>";
?>