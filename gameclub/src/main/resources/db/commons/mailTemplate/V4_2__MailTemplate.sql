INSERT INTO commons.mail_template(code, content, subject)
VALUES('ACNVRF',
'<div style="width: 100%; text-align: center;">
	<div>
		<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=welcome-top.png" width="100%" />
 	</div>
	<div style="margin-top:-1px;">
		<a href="{{link}}" style="cursor:pointer;">
			<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=welcome-verify.png" />
		</a>
	</div>
</div>',
'Bienvenido a GameClub');