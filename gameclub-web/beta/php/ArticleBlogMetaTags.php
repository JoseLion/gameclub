<?php include_once("classes/Util.class.php");?>
<?php include_once("classes/BlogCallRest.class.php")?>
<?php
	$facebook_id = Util::read_property("facebook.id");
	$id_blog = $_GET["id"];
	$blog_call_rest = new BlogCallRest;
	$meta_tags = $blog_call_rest->find_one_blog_meta_tags($id_blog);

?>
<!DOCTYPE html>
<html>
<head>
    <meta property="og:title" content="<?=$meta_tags["title"]?>" />
    <meta property="og:description" content="<?=$meta_tags["summary"]?>" />
    <meta property="og:image" content="<?=$meta_tags["banner"]?>" />
	<meta property="fb:app_id" content="<?=$facebook_id?>" /> 
</head>
 </html>