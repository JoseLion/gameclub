<?php include_once("classes/Util.class.php");?>
<?php include_once("classes/BlogCallRest.class.php")?>
<?php
	$blog_call_rest = new BlogCallRest;
	$facebook_id = Util::read_property("facebook.id");
	$id_blog = $_GET["id"];
	$meta_tags = $blog_call_rest->find_one_blog_meta_tags($id_blog);
?>
<!DOCTYPE html>
<html>
<head>
    <meta property="og:title" content="<?=$meta_tags["title"]?>" />
    <meta property="og:type" content="article" />
    <meta property="og:description" content="<?=$meta_tags["summary"]?>" />
    <meta property="og:image" content="" />
    <meta property="og:url" content="http://www.elcomercio.com/actualidad/maduro-alerta-militares-venezuela-caracas.html" />
	<meta property="fb:app_id" content="<?=$facebook_id?>" /> 
</head>
 </html>