INSERT INTO commons.mail(acronym, content, subject)
VALUES('ACNVRF',
'<div class="gc-welcome">
	<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=welcome-top.png" width="100%"/>
	<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=welcome-middle.png" width="100%"/>
	<a class="verify" href="{{link}}">
		<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=welcome-click.png"/>
	</a>
	<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=welcome-bottom.png" width="100%"/>
	<div class="networks">
		<a href="https://www.facebook.com/gameclub.ec/">
			<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=welcome-facebook.png"/>
		</a>
		<a href="https://my.playstation.com/gameclubec">
			<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=welcome-ps.svg" height="25px"/>
		</a>
		<a href="https://account.xbox.com/en-us/Profile?GamerTag=GameClubEC">
			<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=welcome-xbox.svg" height="25px"/>
		</a>
		<a href="http://www.nintendo.com/">
			<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=welcome-nintendo.svg" height="25px"/>
		</a>
	</div>
	<div class="links">
		<a href="mailto:info@gameclub.com.ec">
			<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=welcome-mail.png" height="30px"/>
		</a>
		<img class="separation" src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=welcome-separation.png"/>
		<a href="{{baseUrl}}">
			<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=welcome-web.png" height="30px"/>
		</a>
	</div>
</div>
<style>
	.gc-welcome img{margin-top:-1px;}
	.gc-welcome a.verify{cursor:pointer;position:absolute;transform:translate(-50%,5%);top:85%;left:50%;}
	.gc-welcome .networks{position:absolute;bottom:0%;width:40%;text-align:left;padding:1%;}
	.gc-welcome .networks a{margin:0% 1%;}
	.gc-welcome .links{position:absolute;bottom:0%;right:0%;width:60%;text-align:right;padding:1%;}
	.gc-welcome .links a{padding:0% 5%;}
	.gc-welcome .links img.separation{position:absolute;bottom:0%;height:60px;}
</style>',
'Bienvenido a GameClub');