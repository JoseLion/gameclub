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
	<p>Faltan <strong>{{days}}</strong> día(s) para que se cree la siguiente devolución:</p>
	<p><strong>Prestador:</strong> {{lender}}</p>
	<p><strong>Jugador:</strong> {{gamer}}</p>
	<p><strong>Juego:</strong> {{game}}</p>
	<p><strong>Consola:</strong> {{console}}</p>
	<p><strong>Fecha Devolución:</strong> {{returnDate}}</p>
	<p></p>
	<p>Atentamente,</p>
	<p>El equipo de Game Club</p>',
'Recordatorio de devolución de juego'),

('MSGLRM',
	'<p>Hola {{lender}},</p>
	<p></p>
	<p>Te recordamos que faltan <strong>{{days}}</strong> día(s) para que finalice el préstamo de tu juego <strong>{{game}} ({{console}})</strong>. No olvides confirmar tu información de entrega en https://gameclub.com.ec</p>
	<p></p>
	<p>Atentamente,</p>
	<p>El equipo de Game Club</p>',
'Recordatorio de devolución de juego'),
	
('MSGGRM',
	'<p>Hola {{gamer}},</p>
	<p></p>
	<p>Te recordamos que faltan <strong>{{days}}</strong> día(s) para que finalice el préstamo del juego <strong>{{game}} ({{console}})</strong>. No olvides confirmar tu información de entrega en https://gameclub.com.ec</p>
	<p></p>
	<p>Atentamente,</p>
	<p>El equipo de Game Club</p>',
'Recordatorio de devolución de juego'),
	
('MSGAUR',
	'<p>Sistema Game Club.</p>
	<p></p>
	<p>Un prétamo ha finalizado y no se han confirmado el/los envíos. La información del préstamo es:</p>
	<p><strong>Prestador:</strong> {{lender}}</p>
	<p><strong>Jugador:</strong> {{gamer}}</p>
	<p><strong>Juego:</strong> {{game}}</p>
	<p><strong>Consola:</strong> {{console}}</p>
	<p><strong>Fecha Devolución:</strong> {{returnDate}}</p>
	<p><strong>Fecha Confirmación Prestador:</strong> {{lenderConfirmationDate}}</p>
	<p><strong>Fecha Confirmación Jugador:</strong> {{gamerConfirmationDate}}</p>
	<p></p>
	<p>Atentamente,</p>
	<p>El equipo de Game Club</p>',
'Préstamo finalizado sin confirmar'),

('MSGLUR',
	'<p>Hola {{lender}},</p>
	<p></p>
	<p>El préstamo de tu juego <strong>{{game}} ({{console}})</strong> ha finalizado y todavía no has confirmado tu dirección de entrega.</p>
	<p>Te recordamos que estos retrasos pueden generar multas como se especifica en los términos y condiciones de uso.</p>
	<p>Para confirmar tu información de entrega, por favor dirigete a https://gameclub.com.ec</p>
	<p></p>
	<p>Atentamente,</p>
	<p>El equipo de Game Club</p>',
'Préstamo finalizado sin confirmar'),

('MSGGUR',
	'<p>Hola {{gamer}},</p>
	<p></p>
	<p>El préstamo del juego <strong>{{game}} ({{console}})</strong> ha finalizado y todavía no has confirmado tu dirección de retiro.</p>
	<p>Te recordamos que estos retrasos pueden generar multas como se especifica en los términos y condiciones de uso.</p>
	<p>Para confirmar tu información de retiro, por favor dirigete a https://gameclub.com.ec</p>
	<p></p>
	<p>Atentamente,</p>
	<p>El equipo de Game Club</p>',
'Préstamo finalizado sin confirmar');