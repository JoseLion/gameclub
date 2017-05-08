INSERT INTO commons.mail(acronym, content, subject)
VALUES('CNCTUS',
	'<p>Sistema Game Club.</p>
	<p></p>
	<p>Un usuario se ha contactado con nosotros:</p>
	<p></p>
	<p><strong>Nombre:</strong> {{name}}</p>
	<p><strong>Correo:</strong> {{email}}</p>
	<p><strong>Teléfono:</strong> {{phone}}</p>
	<p><strong>Mensaje:</strong></p>
	<p>{{message}}</p>
	<p></p>
	<p></p>
	<p>Atentamente,</p>
	<p>El equipo de Game Club</p>',
'Contáctanos Game Club');