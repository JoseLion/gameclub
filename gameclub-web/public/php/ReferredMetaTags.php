<?php include_once("classes/Util.class.php"); ?>
<?php include_once("classes/Rest.class.php"); ?>
<?php
	$rest = new Rest;

	$facebook_id = Util::read_property("facebook.id");
	$reward = $rest->get("open/settings/findByCode/STGREFREW");
	$desc = "Oye, aquí tienes $" . $reward->value . " para que experimentes GameClub!";
	$imageUrl = (isset($_SERVER['HTTPS']) ? "https" : "http") . "://$_SERVER[HTTP_HOST]/img/gameclub-footer-logo.svg";
?>
<!DOCTYPE html>
<html>
	<head>
		<title>GameClub</title>
		<meta name="description" content="<?php echo $desc;?>" />
		<meta property="og:url" content="" />
		<meta property="og:title" content="GameClub" />
		<meta property="og:type" content="website" />
		<meta property="og:description" content="<?php echo $desc;?>" />
		<meta property="og:image" content="" />
		<meta property="fb:app_id" content="<?php echo $facebook_id;?>" />
	</head>
	<body>
		<?php echo $desc; ?>
	</body>
</html>