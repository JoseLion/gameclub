INSERT INTO commons.mail_template(code, content, subject)
VALUES('MSGREQ',
'<div style="width: 100%; text-align: center; background: #636363; background-image: url({{baseUrl}}:8090/gameclub/open/util/getImage?name=mail-bg.png); background-size: 100%;">
	<div style="padding: 10% 10% 0% 10%;">
		<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=mail-head-logo.png" alt="mail-head-logo.png" />
		<h1 style="margin-top: 5%; text-transform: uppercase; font-family: jaapokki; font-size: 35px; color: #2CB4BF;">Tienes una Solicitud de Alquiler</h1>
		<h2 style="text-align: left; margin-top: 5%; font-family: jaapokki; font-size: 25px; color: #E43345;">Hola {{name}},</h2>
		<div style="padding: 5%; background-color: rgba(255, 255, 255, 0.8); font-family: jaapokki; font-size: 17px; color: #071428;">
			<p>Tienes una nueva solicitud de {{user}} para alquiler tu juego {{game}} de {{console}} por {{weeks}} semanas. Recuerda que por este alquiler se te acreditará a tu saldo {{cost}}.</p>
			<p>Entra a <a href="{{baseUrl}}/gameclub/account/messages">GameClub</a> para aprobar o rechazar esta solicitud de alquiler. Recuerda que debes confirmar tu dirección para poder retirar tu juego.</p>
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
							<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=mail-fb.png" alt="mail-fb.png" />
						</a>
						<a href="https://my.playstation.com/gameclubec" style="margin: 0% 0.5%;">
							<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=mail-ps.png" alt="mail-ps.png" height="25px" />
						</a>
						<a href="https://account.xbox.com/en-us/Profile?GamerTag=GameClubEC" style="margin: 0% 0.5%;">
							<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=mail-xbox.png" alt="mail-xbox.png" height="25px" />
						</a>
						<a href="http://www.nintendo.com/" style="margin: 0% 0.5%;">
							<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=mail-nintendo.png" alt="mail-nintendo.png" height="25px" />
						</a>
					</td>

					<td style="text-align: right;">
						<a href="mailto:info@gameclub.com.ec" style="font-family: jaapokki; font-size: 17px; color: white; text-decoration: none;">
							<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=mail-plane.png" alt="mail-plane.png" height="25px" />
							<span>info@gameclub.com.ec</span>
						</a>
						<a href="https://www.gameclub.com.ec/" style="font-family: jaapokki; font-size: 17px; color: white; text-decoration: none; margin-left: 10px;">
							<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=mail-web.png" alt="mail-web.png" height="25px" />
							<span>info@gameclub.com.ec</span>
						</a>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>',
'Tienes una Solicitud de Alquiler'),

('MSGARM',
'<div style="width: 100%; text-align: center; background: #636363; background-image: url({{baseUrl}}:8090/gameclub/open/util/getImage?name=mail-bg.png); background-size: 100%;">
	<div style="padding: 10% 10% 0% 10%;">
		<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=mail-head-logo.png" alt="mail-head-logo.png" />
		<h1 style="margin-top: 5%; text-transform: uppercase; font-family: jaapokki; font-size: 35px; color: #2CB4BF;">Recordatorio Días Devolución</h1>
		<h2 style="text-align: left; margin-top: 5%; font-family: jaapokki; font-size: 25px; color: #E43345;">Recordatorio:</h2>
		<div style="padding: 5%; background-color: rgba(255, 255, 255, 0.8); font-family: jaapokki; font-size: 17px; color: #071428;">
			<p>Faltan <strong>{{days}}</strong> día(s) para finalización de alquiler:</p>
			<p><strong>Prestador:</strong> {{lender}}</p>
			<p><strong>Jugador:</strong> {{gamer}}</p>
			<p><strong>Juego:</strong> {{game}}</p>
			<p><strong>Consola:</strong> {{console}}</p>
			<p><strong>Fecha Devolución:</strong> {{returnDate}}</p>
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
							<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=mail-fb.png" alt="mail-fb.png" />
						</a>
						<a href="https://my.playstation.com/gameclubec" style="margin: 0% 0.5%;">
							<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=mail-ps.png" alt="mail-ps.png" height="25px" />
						</a>
						<a href="https://account.xbox.com/en-us/Profile?GamerTag=GameClubEC" style="margin: 0% 0.5%;">
							<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=mail-xbox.png" alt="mail-xbox.png" height="25px" />
						</a>
						<a href="http://www.nintendo.com/" style="margin: 0% 0.5%;">
							<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=mail-nintendo.png" alt="mail-nintendo.png" height="25px" />
						</a>
					</td>

					<td style="text-align: right;">
						<a href="mailto:info@gameclub.com.ec" style="font-family: jaapokki; font-size: 17px; color: white; text-decoration: none;">
							<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=mail-plane.png" alt="mail-plane.png" height="25px" />
							<span>info@gameclub.com.ec</span>
						</a>
						<a href="https://www.gameclub.com.ec/" style="font-family: jaapokki; font-size: 17px; color: white; text-decoration: none; margin-left: 10px;">
							<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=mail-web.png" alt="mail-web.png" height="25px" />
							<span>info@gameclub.com.ec</span>
						</a>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>',
'Recordatorio Días Devolución'),

('MSGLRM',
'<div style="width: 100%; text-align: center; background: #636363; background-image: url({{baseUrl}}:8090/gameclub/open/util/getImage?name=mail-bg.png); background-size: 100%;">
	<div style="padding: 10% 10% 0% 10%;">
		<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=mail-head-logo.png" alt="mai-head-logo.png" />
		<h1 style="margin-top: 5%; text-transform: uppercase; font-family: jaapokki; font-size: 35px; color: #2CB4BF;">Ya mismo te devolvemos tu juego alquilado</h1>
		<h2 style="text-align: left; margin-top: 5%; font-family: jaapokki; font-size: 25px; color: #E43345;">Hola {{lender}},</h2>
		<div style="padding: 5%; background-color: rgba(255, 255, 255, 0.8); font-family: jaapokki; font-size: 17px; color: #071428;">
			<p>Te recordamos que faltan {{days}} día(s) para que finalice el alquiler de tu juego {{game}} para {{console}}.</p>
			<p>No olvides ingresar a <a href="{{baseUrl}}/gameclub/account/messages">GameClub</a> y confirmar tu información para entregarte tu juego.</p>
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
							<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=mail-fb.png" alt="mail-fb.png" />
						</a>
						<a href="https://my.playstation.com/gameclubec" style="margin: 0% 0.5%;">
							<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=mail-ps.png" alt="mail-ps.png" height="25px" />
						</a>
						<a href="https://account.xbox.com/en-us/Profile?GamerTag=GameClubEC" style="margin: 0% 0.5%;">
							<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=mail-xbox.png" alt="mail-xbox.png" height="25px" />
						</a>
						<a href="http://www.nintendo.com/" style="margin: 0% 0.5%;">
							<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=mail-nintendo.png" alt="mail-nintendo.png" height="25px" />
						</a>
					</td>

					<td style="text-align: right;">
						<a href="mailto:info@gameclub.com.ec" style="font-family: jaapokki; font-size: 17px; color: white; text-decoration: none;">
							<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=mail-plane.png" alt="mail-plane.png" height="25px" />
							<span>info@gameclub.com.ec</span>
						</a>
						<a href="https://www.gameclub.com.ec/" style="font-family: jaapokki; font-size: 17px; color: white; text-decoration: none; margin-left: 10px;">
							<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=mail-web.png" alt="mail-web.png" height="25px" />
							<span>info@gameclub.com.ec</span>
						</a>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>',
'Ya mismo te devolvemos tu juego alquilado'),
	
('MSGGRM',
'<div style="width: 100%; text-align: center; background: #636363; background-image: url({{baseUrl}}:8090/gameclub/open/util/getImage?name=mail-bg.png); background-size: 100%;">
	<div style="padding: 10% 10% 0% 10%;">
		<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=mail-head-logo.png" alt="mai-head-logo.png" />
		<h1 style="margin-top: 5%; text-transform: uppercase; font-family: jaapokki; font-size: 35px; color: #2CB4BF;">RECUERDA que ya mismo se acaba tu alquiler</h1>
		<h2 style="text-align: left; margin-top: 5%; font-family: jaapokki; font-size: 25px; color: #E43345;">Hola {{gamer}},</h2>
		<div style="padding: 5%; background-color: rgba(255, 255, 255, 0.8); font-family: jaapokki; font-size: 17px; color: #071428;">
			<p>Te recordamos que faltan {{days}} día(s) para que finalice el alquiler del juego {{game}} para {{console}}.</p>
			<p>No olvides ingresar a <a href="{{baseUrl}}/gameclub/account/messages">GameClub</a> y confirmar tu información para retirar el juego.</p>
			<p>Recuerda que puedes tener multas si no entregas el juego a tiempo. Para más infomación revisa los <a href="{{baseUrl}}/gameclub/termsConditions">Términos y Condiciones de uso</a>.</p>
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
							<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=mail-fb.png" alt="mail-fb.png" />
						</a>
						<a href="https://my.playstation.com/gameclubec" style="margin: 0% 0.5%;">
							<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=mail-ps.png" alt="mail-ps.png" height="25px" />
						</a>
						<a href="https://account.xbox.com/en-us/Profile?GamerTag=GameClubEC" style="margin: 0% 0.5%;">
							<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=mail-xbox.png" alt="mail-xbox.png" height="25px" />
						</a>
						<a href="http://www.nintendo.com/" style="margin: 0% 0.5%;">
							<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=mail-nintendo.png" alt="mail-nintendo.png" height="25px" />
						</a>
					</td>

					<td style="text-align: right;">
						<a href="mailto:info@gameclub.com.ec" style="font-family: jaapokki; font-size: 17px; color: white; text-decoration: none;">
							<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=mail-plane.png" alt="mail-plane.png" height="25px" />
							<span>info@gameclub.com.ec</span>
						</a>
						<a href="https://www.gameclub.com.ec/" style="font-family: jaapokki; font-size: 17px; color: white; text-decoration: none; margin-left: 10px;">
							<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=mail-web.png" alt="mail-web.png" height="25px" />
							<span>info@gameclub.com.ec</span>
						</a>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>',
'RECUERDA que ya mismo se acaba tu alquiler'),
	
('MSGAUR',
'<div style="width: 100%; text-align: center; background: #636363; background-image: url({{baseUrl}}:8090/gameclub/open/util/getImage?name=mail-bg.png); background-size: 100%;">
	<div style="padding: 10% 10% 0% 10%;">
		<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=mail-head-logo.png" alt="mail-head-logo.png" />
		<h1 style="margin-top: 5%; text-transform: uppercase; font-family: jaapokki; font-size: 35px; color: #2CB4BF;">Dirección de Alquiler no Confirmada</h1>
		<div style="padding: 5%; background-color: rgba(255, 255, 255, 0.8); font-family: jaapokki; font-size: 17px; color: #071428;">
			<p>Un Alquiler finalizó y no se han confirmado el/los envíos. La información del préstamo es:</p>
			<p><strong>Prestador:</strong> {{lender}}</p>
			<p><strong>Jugador:</strong> {{gamer}}</p>
			<p><strong>Juego:</strong> {{game}}</p>
			<p><strong>Consola:</strong> {{console}}</p>
			<p><strong>Fecha Devolución:</strong> {{returnDate}}</p>
			<p><strong>Fecha Confirmación Prestador:</strong> {{lenderConfirmationDate}}</p>
			<p><strong>Fecha Confirmación Jugador:</strong> {{gamerConfirmationDate}}</p>
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
							<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=mail-fb.png" alt="mail-fb.png" />
						</a>
						<a href="https://my.playstation.com/gameclubec" style="margin: 0% 0.5%;">
							<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=mail-ps.png" alt="mail-ps.png" height="25px" />
						</a>
						<a href="https://account.xbox.com/en-us/Profile?GamerTag=GameClubEC" style="margin: 0% 0.5%;">
							<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=mail-xbox.png" alt="mail-xbox.png" height="25px" />
						</a>
						<a href="http://www.nintendo.com/" style="margin: 0% 0.5%;">
							<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=mail-nintendo.png" alt="mail-nintendo.png" height="25px" />
						</a>
					</td>

					<td style="text-align: right;">
						<a href="mailto:info@gameclub.com.ec" style="font-family: jaapokki; font-size: 17px; color: white; text-decoration: none;">
							<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=mail-plane.png" alt="mail-plane.png" height="25px" />
							<span>info@gameclub.com.ec</span>
						</a>
						<a href="https://www.gameclub.com.ec/" style="font-family: jaapokki; font-size: 17px; color: white; text-decoration: none; margin-left: 10px;">
							<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=mail-web.png" alt="mail-web.png" height="25px" />
							<span>info@gameclub.com.ec</span>
						</a>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>',
'Dirección de Alquiler no Confirmada'),

('MSGLUR',
'<div style="width: 100%; text-align: center; background: #636363; background-image: url({{baseUrl}}:8090/gameclub/open/util/getImage?name=mail-bg.png); background-size: 100%;">
	<div style="padding: 10% 10% 0% 10%;">
		<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=mail-head-logo.png" alt="mail-head-logo.png" />
		<h1 style="margin-top: 5%; text-transform: uppercase; font-family: jaapokki; font-size: 35px; color: #2CB4BF;">No Confirmaste la Dirección de Entrega</h1>
		<h2 style="text-align: left; margin-top: 5%; font-family: jaapokki; font-size: 25px; color: #E43345;">Hola {{lender}},</h2>
		<div style="padding: 5%; background-color: rgba(255, 255, 255, 0.8); font-family: jaapokki; font-size: 17px; color: #071428;">
			<p>El tiempo de alquiler de tu juego {{game}} para {{console}} ha finalizado y no confirmaste tu dirección de entrega.</p>
			<p>Enviaremos tu juego a la misma dirección en la que lo recogimos, te recordamos que si nadie recibe el juego se pueden generar multas a tu cuenta como se especifica en los <a href="{{baseUrl}}/gameclub/termsConditions">Términos y Condiciones de uso</a>.</p>
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
							<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=mail-fb.png" alt="mail-fb.png" />
						</a>
						<a href="https://my.playstation.com/gameclubec" style="margin: 0% 0.5%;">
							<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=mail-ps.png" alt="mail-ps.png" height="25px" />
						</a>
						<a href="https://account.xbox.com/en-us/Profile?GamerTag=GameClubEC" style="margin: 0% 0.5%;">
							<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=mail-xbox.png" alt="mail-xbox.png" height="25px" />
						</a>
						<a href="http://www.nintendo.com/" style="margin: 0% 0.5%;">
							<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=mail-nintendo.png" alt="mail-nintendo.png" height="25px" />
						</a>
					</td>

					<td style="text-align: right;">
						<a href="mailto:info@gameclub.com.ec" style="font-family: jaapokki; font-size: 17px; color: white; text-decoration: none;">
							<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=mail-plane.png" alt="mail-plane.png" height="25px" />
							<span>info@gameclub.com.ec</span>
						</a>
						<a href="https://www.gameclub.com.ec/" style="font-family: jaapokki; font-size: 17px; color: white; text-decoration: none; margin-left: 10px;">
							<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=mail-web.png" alt="mail-web.png" height="25px" />
							<span>info@gameclub.com.ec</span>
						</a>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>',
'No Confirmaste la Dirección de Entrega'),

('MSGGUR',
'<div style="width: 100%; text-align: center; background: #636363; background-image: url({{baseUrl}}:8090/gameclub/open/util/getImage?name=mail-bg.png); background-size: 100%;">
	<div style="padding: 10% 10% 0% 10%;">
		<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=mail-head-logo.png" alt="mail-head-logo.png" />
		<h1 style="margin-top: 5%; text-transform: uppercase; font-family: jaapokki; font-size: 35px; color: #2CB4BF;">No Confirmaste la Dirección de Retiro</h1>
		<h2 style="text-align: left; margin-top: 5%; font-family: jaapokki; font-size: 25px; color: #E43345;">Hola {{gamer}},</h2>
		<div style="padding: 5%; background-color: rgba(255, 255, 255, 0.8); font-family: jaapokki; font-size: 17px; color: #071428;">
			<p>El tiempo de alquiler del juego {{game}} para {{consola}} ha finalizado y no confirmaste tu dirección de retiro.</p>
			<p>Recogeremos tu juego a la misma dirección en la que lo entregamos, te recordamos que si nadie entrega el juego se pueden generar multas a tu cuenta como se especifica en los <a href="{{baseUrl}}/gameclub/termsConditions">Términos y Condiciones de uso</a>.</p>
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
							<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=mail-fb.png" alt="mail-fb.png" />
						</a>
						<a href="https://my.playstation.com/gameclubec" style="margin: 0% 0.5%;">
							<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=mail-ps.png" alt="mail-ps.png" height="25px" />
						</a>
						<a href="https://account.xbox.com/en-us/Profile?GamerTag=GameClubEC" style="margin: 0% 0.5%;">
							<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=mail-xbox.png" alt="mail-xbox.png" height="25px" />
						</a>
						<a href="http://www.nintendo.com/" style="margin: 0% 0.5%;">
							<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=mail-nintendo.png" alt="mail-nintendo.png" height="25px" />
						</a>
					</td>

					<td style="text-align: right;">
						<a href="mailto:info@gameclub.com.ec" style="font-family: jaapokki; font-size: 17px; color: white; text-decoration: none;">
							<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=mail-plane.png" alt="mail-plane.png" height="25px" />
							<span>info@gameclub.com.ec</span>
						</a>
						<a href="https://www.gameclub.com.ec/" style="font-family: jaapokki; font-size: 17px; color: white; text-decoration: none; margin-left: 10px;">
							<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=mail-web.png" alt="mail-web.png" height="25px" />
							<span>info@gameclub.com.ec</span>
						</a>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>',
'No Confirmaste la Dirección de Retiro');