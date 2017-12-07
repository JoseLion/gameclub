INSERT INTO commons.mail_template(code, content, subject)
VALUES('TMPWRD',
'<div style="width: 100%; text-align: center; background: #636363; background-image: url({{baseUrl}}:8090/gameclub/open/util/getImage?name=mail-bg.png); background-size: 100%;">
	<div style="padding: 10% 10% 0% 10%;">
		<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=mail-head-logo.png" alt="mai-head-logo.png"/>
		<h1 style="margin-top: 5%; text-transform: uppercase; font-family: jaapokki; font-size: 35px; color: #2CB4BF;">Restablecimiento Contraseña</h1>
		<h2 style="text-align: left; margin-top: 5%; font-family: jaapokki; font-size: 25px; color: #E43345;">Hola {{name}},</h2>
		<div style="padding: 5%; background-color: rgba(255, 255, 255, 0.8); font-family: jaapokki; font-size: 17px; color: #071428;">
			<p>Hemos procesado tu solicitud para reestablecer tu contraseña. Tu contraseña temporal asignada es: {{password}}. Ingresa a gameclub.com.ec y utiliza esta clave temporal para ingresar a tu cuenta.</p>
			<p>Si tu no solicitaste que se reestablesca tu contraseña comunicate con GameClub lo antes posible.</p>
			<p></p>
			<p>Gracias</p>
			<p></p>
			<p>El Equipo de GameClub</p>
			<p></p>
			<small style="color: #636363; font-size: 14px;">Por favor no respondas a este correo. Si tienes alguna pregunta, encuentra la información en nuestra página de ayuda o contactános.</small>
		</div>
	</div>
	<div style="text-align: left; margin-top: 5%; padding: 2%; background: linear-gradient(transparent 0%, black 100%)">
		<a href="https://www.facebook.com/gameclub.ec/" style="cursor: pointer;">
			<img src="{{baseUrl}}:8090/gameclub/open/util/getImage?name=mail-fb.png" alt="mail-fb.png"/>
		</a>
	</div>
</div>',
'Restablecimiento Contraseña');