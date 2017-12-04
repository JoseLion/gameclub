INSERT INTO commons.mail_template(code, content, subject)
VALUES('ACNVRF',
'<div class="col-md-1 col-xs-0"></div>
<div class="col-md-10 col-xs-12" style="text-align:center;">
	<div>
		<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=welcome-top.png" width="100%" />
 	</div>
	<div style="margin-top:-1px;">
		<a href="{{link}}" style="cursor:pointer;">
			<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=welcome-verify.png" width="100%"/>
		</a>
	</div>
</div>
<div class="col-md-1 col-xs-0"></div>',
'Bienvenido a GameClub');