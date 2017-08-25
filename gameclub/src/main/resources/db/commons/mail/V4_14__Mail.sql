INSERT INTO commons.mail(acronym, content, subject)
VALUES('MSGREQ',
	'<p>Sistema Game Club.</p>
	<p></p>
	<p>Has recibido una nueva solicitud de préstamo:</p>
	<p><strong>Usuario:</strong> {{user}}</p>
	<p><strong>Juego:</strong> {{game}}</p>
	<p><strong>Consola:</strong> {{console}}</p>
	<p><strong>Costo:</strong> {{cost}}</p>
	<p></p>
	<p>Atentamente,</p>
	<p>El equipo de Game Club</p>',
'Solicitud de préstamo Game Club'),

('MSGARM',
	'<p>Sistema Game Club.</p>
	<p></p>
	<p>Faltan 3 días para que se cree la siguiente devolución:</p>
	<p><strong>Prestador:</strong> {{lender}}</p>
	<p><strong>Jugador:</strong> {{gamer}}</p>
	<p><strong>Juego:</strong> {{game}}</p>
	<p><strong>Consola:</strong> {{console}}</p>
	<p><strong>Fecha Devolución:</strong> {{returnDate}}</p>
	<p></p>
	<p>Atentamente,</p>
	<p>El equipo de Game Club</p>',
'Recordatorio de devolución de juego');