INSERT INTO commons.mail_template(code, content, subject)
VALUES('TMPWRD',
	'<p>Sistema Game Club.</p>
	<p></p>
	<p>Su contraseña de ingreso temporal es <strong>{{password}}</strong></p>
	<p></p>
	<p>Atentamente,</p>
	<p>El equipo de Game Club</p>',
'Bienvenido al administrador de Game Club');