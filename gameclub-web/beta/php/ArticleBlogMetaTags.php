<?php include_once("classes/Util.class.php");?>
<?php include_once("classes/BlogCallRest.class.php")?>
<?php
	$blog_call_rest = new BlogCallRest;
	$facebook_id = Util::read_property("facebook.id");
	$id_blog = $_GET["id"];
	$meta_tags = $blog_call_rest->find_one_blog_meta_tags($id_blog);
	$files_url_base = Util::read_property("files.url");
	$image_url = $files_url_base . "/" .$meta_tags['banner']->module . "/" .rawurlencode($meta_tags['banner']->name);
?>
<!DOCTYPE html>
<html>
	<head>
		<title><?php echo $meta_tags['title'];?></title>
		<meta name="description" content="<?php echo $meta_tags['summary'];?>" />
		<meta property="og:url" content="" />
		<meta property="og:title" content="<?php echo $meta_tags['title'];?>" />
		<meta property="og:type" content="article" />
		<meta property="og:description" content="<?php echo $meta_tags['summary']?>" />
		<meta property="og:image" content="<?php echo $image_url;?>" />
		<meta property="fb:app_id" content="<?php echo $facebook_id;?>" /> 
	</head>
</html>
