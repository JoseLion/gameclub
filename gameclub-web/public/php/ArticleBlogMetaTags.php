<?php include_once("classes/Util.class.php"); ?>
<?php include_once("classes/Rest.class.php"); ?>
<?php
	$rest = new Rest;

	$facebook_id = Util::read_property("facebook.id");
	$blog = $rest->get("open/levelapBlog/findOneMetaTags/" . $_GET["id"]);
	$image_url = (isset($_SERVER['HTTPS']) ? "https" : "http") . "://$_SERVER[HTTP_HOST]:" . ($_SERVER['SERVER_PORT'] == 80 ? "8090" : "8390") . "/gameclub/open/archive/download/" . $blog->banner->id;
?>
<!DOCTYPE html>
<html>
	<head>
		<title><?php echo $blog->title;?></title>
		<meta name="description" content="<?php echo $blog->summary; ?>" />
		<meta name="keywords" content="<?php echo $blog->keywords; ?>" />
		<meta property="og:url" content="" />
		<meta property="og:title" content="<?php echo $blog->title; ?>" />
		<meta property="og:type" content="article" />
		<meta property="og:description" content="<?php echo $blog->summary; ?>" />
		<meta property="og:image" content="<?php echo $image_url; ?>" />
		<meta property="fb:app_id" content="<?php echo $facebook_id;?>" /> 
	</head>
</html>
