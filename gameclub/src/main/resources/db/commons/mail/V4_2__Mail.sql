INSERT INTO commons.mail(acronym, content, subject)
VALUES('ACNVRF',
'<div>
	<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=welcome-top.png" width="100%"/>
	<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=welcome-middle.png" width="100%" style="margin-top:-1px;"/>
	<a href="{{link}}" style="cursor:pointer;position:absolute;transform:translate(-50%,5%);top:85%;left:50%;">
		<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=welcome-click.png" />
	</a>
	<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=welcome-bottom.png" width="100%" style="margin-top:-1px;"/>
	<div style="position:absolute;bottom:0%;width:40%;text-align:left;padding:1%;">
		<a href="https://www.facebook.com/gameclub.ec/" style="padding:0% 1%;">
			<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=welcome-facebook.png"/>
		</a>
		<a href="https://my.playstation.com/gameclubec" style="padding:0% 1%;">
			<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=welcome-ps.png" height="25px"/>
		</a>
		<a href="https://account.xbox.com/en-us/Profile?GamerTag=GameClubEC" style="padding:0% 1%;">
			<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=welcome-xbox.png" height="25px"/>
		</a>
		<a href="http://www.nintendo.com/" style="padding:0% 1%;">
			<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=welcome-nintendo.png" height="25px"/>
		</a>
	</div>
	<div style="position:absolute;bottom:0%;right:0%;width:60%;text-align:right;padding:1%;">
		<a href="mailto:info@gameclub.com.ec" style="padding:0% 5%;">
			<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=welcome-mail.png" height="30px"/>
		</a>
		<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=welcome-separation.png" style="position:absolute;bottom:0%;height:60px;"/>
		<a href="{{baseUrl}}" style="padding:0% 5%;">
			<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=welcome-web.png" height="30px"/>
		</a>
	</div>
</div>',
'Bienvenido a GameClub');