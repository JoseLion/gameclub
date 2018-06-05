INSERT INTO commons.mail_template(code, content, subject)
VALUES('REQACP',
'<div style="width: 100%; text-align: center; background: #636363; background-image: url({{baseUrl}}:{{port}}/gameclub/open/util/getImage?name=mail-bg.png); background-size: 100%;">
	<div style="padding: 10% 10% 0% 10%;">
		<img src="{{baseUrl}}:{{port}}/gameclub/open/util/getImage?name=mail-head-logo.png" alt="mail-head-logo.png" />
		<h1 style="margin-top: 5%; text-transform: uppercase; font-family: jaapokki; font-size: 35px; color: #2CB4BF;">Tu Solicitud de Alquiler fue Aceptada</h1>
		<h2 style="text-align: left; margin-top: 5%; font-family: jaapokki; font-size: 25px; color: #E43345;">Hola {{name}},</h2>
		<div style="padding: 5%; background-color: rgba(255, 255, 255, 0.8); font-family: jaapokki; font-size: 17px; color: #071428;">
			<p>Tu solictud para alquilar {{game}} de {{console}} a {{lender}}. por {{weeks}} semana(s) ha sido aceptada!.</p>
			<p>Entra a <a href=" http://gameclub.com.ec/account/messages">GameClub</a> para proceder con el pago de tu alquiler. Recuerda que debes confirmar tu dirección para poder entregar tu juego alquilado.</p>
			<p></p>
			<p>Gracias</p>
			<p></p>
			<p>El Equipo de GameClub</p>
			<p></p>
			<small style="color: #636363; font-size: 14px;">Por favor no respondas a este correo. Si tienes alguna pregunta, encuentra la información en nuestra página de ayuda o contactános.</small>
		</div>
	</div>
	<div style="padding: 2%; margin-top: 5%; background: linear-gradient(transparent 0%, black 100%)">
		<table width="100%">
			<col width="35%">
			<col width="65%">

			<tbody>
				<tr>
					<td style="text-align: left;">
						<a href="https://www.facebook.com/gameclub.ec/" style="cursor: pointer; margin: 0% 0.5%;">
							<img src="{{baseUrl}}:{{port}}/gameclub/open/util/getImage?name=mail-fb.png" alt="mail-fb.png" />
						</a>
						<a href="https://my.playstation.com/gameclubec" style="margin: 0% 0.5%;">
							<img src="{{baseUrl}}:{{port}}/gameclub/open/util/getImage?name=mail-ps.png" alt="mail-ps.png" height="25px" />
						</a>
						<a href="https://account.xbox.com/en-us/Profile?GamerTag=GameClubEC" style="margin: 0% 0.5%;">
							<img src="{{baseUrl}}:{{port}}/gameclub/open/util/getImage?name=mail-xbox.png" alt="mail-xbox.png" height="25px" />
						</a>
						<a href="http://www.nintendo.com/" style="margin: 0% 0.5%;">
							<img src="{{baseUrl}}:{{port}}/gameclub/open/util/getImage?name=mail-nintendo.png" alt="mail-nintendo.png" height="25px" />
						</a>
					</td>

					<td style="text-align: right;">
						<a href="mailto:info@gameclub.com.ec" style="font-family: jaapokki; font-size: 17px; color: white; text-decoration: none;">
							<img src="{{baseUrl}}:{{port}}/gameclub/open/util/getImage?name=mail-plane.png" alt="mail-plane.png" height="25px" />
							<span>info@gameclub.com.ec</span>
						</a>
						<a href="https://www.gameclub.com.ec/" style="font-family: jaapokki; font-size: 17px; color: white; text-decoration: none; margin-left: 10px;">
							<img src="{{baseUrl}}:{{port}}/gameclub/open/util/getImage?name=mail-web.png" alt="mail-web.png" height="25px" />
							<span>gameclub.com.ec</span>
						</a>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>',
'Tu Solicitud de Alquiler fue Aceptada');