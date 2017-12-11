INSERT INTO commons.mail_template(code, content, subject)
VALUES('ACNVRF',
'<div style="width: 100%; text-align: center; background: #636363; background-image: url(http://local.gameclub:8090/gameclub/open/util/getImage?name=mail-welcome-bg.png); background-size: 100%;">
	<div style="padding-top: 30px;">
		<img src="http://local.gameclub:8090/gameclub/open/util/getImage?name=mail-head-logo.png" alt="mai-head-logo.png" height="80px" />
		<div style="background: linear-gradient(to right, #E43345 25%, #2CB4BF 75%); height: 5px; width: 100%; margin-top: 25px;"></div>
		<h1 style="text-transform: uppercase; font-family: jaapokki; font-size: 85px; line-height: 30px; color: #FFF; margin-top: 60px;">Bienvenido</h1>
		<h3 style="text-transform: uppercase; font-family: jaapokki; font-size: 29px; line-height: 30px; color: #FFF;">a la plataforma de videojuegos</h3>
		<h2 style="text-transform: uppercase; font-family: jaapokki; font-size: 48px; line-height: 30px; color: #2CB4BF;">más grande del país</h2>
		<img src="http://local.gameclub:8090/gameclub/open/util/getImage?name=mail-how-it-works.png" alt="mail-how-it-works.png" />
		<div style="background: rgba(7, 20, 40, 0.75); padding: 18px 0%; margin-top: 15px;">
			<a href="{{link}}" style="text-transform: uppercase; text-decoration: none; font-family: Josefin Sans; font-weight: bold; font-size: 16px; color: #FFF; border: 4px solid #E43345; border-radius: 12px; padding: 5px 3%;">Verifica tu cuenta</a>
		</div>
	</div>
	<div style="padding: 10px 2%; margin-top: 50px; background: linear-gradient(transparent 0%, black 90%)">
		<table width="100%">
			<col></col>
			<col></col>

			<tbody>
				<tr>
					<td style="text-align: left;">
						<a href="https://www.facebook.com/gameclub.ec/" style="cursor: pointer; margin: 0% 0.5%;">
							<img src="http://local.gameclub:8090/gameclub/open/util/getImage?name=mail-fb.png" alt="mail-fb.png" />
						</a>
						<a href="https://my.playstation.com/gameclubec" style="margin: 0% 0.5%;">
							<img src="http://local.gameclub:8090/gameclub/open/util/getImage?name=mail-ps.png" alt="mail-ps.png" height="25px" />
						</a>
						<a href="https://account.xbox.com/en-us/Profile?GamerTag=GameClubEC" style="margin: 0% 0.5%;">
							<img src="http://local.gameclub:8090/gameclub/open/util/getImage?name=mail-xbox.png" alt="mail-xbox.png" height="25px" />
						</a>
						<a href="http://www.nintendo.com/" style="margin: 0% 0.5%;">
							<img src="http://local.gameclub:8090/gameclub/open/util/getImage?name=mail-nintendo.png" alt="mail-nintendo.png" height="25px" />
						</a>
					</td>

					<td style="text-align: right;">
						<a href="mailto:info@gameclub.com.ec" style="font-family: jaapokki; font-size: 17px; color: white; text-decoration: none;">
							<img src="http://local.gameclub:8090/gameclub/open/util/getImage?name=mail-plane.png" alt="mail-plane.png" height="25px" />
							<span>info@gameclub.com.ec</span>
						</a>
						<a href="https://www.gameclub.com.ec/" style="font-family: jaapokki; font-size: 17px; color: white; text-decoration: none; margin-left: 10px;">
							<img src="http://local.gameclub:8090/gameclub/open/util/getImage?name=mail-web.png" alt="mail-web.png" height="25px" />
							<span>info@gameclub.com.ec</span>
						</a>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>',
'Bienvenido a GameClub');